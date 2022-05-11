package com.example.taskinstabug.ui

import android.app.AlertDialog
import android.app.SearchManager
import android.content.Context.SEARCH_SERVICE
import android.content.DialogInterface
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.instabugtask.pojo.WordsModel
import com.example.taskinstabug.connectivity.ConnectionLiveData
import com.example.taskinstabug.R
import com.example.taskinstabug.WordsAdapter
import kotlinx.android.synthetic.main.fragment_search.*


class SearchFragment : Fragment() {
    var searchText = ""
    val wordsViewModel: SearchFragmentViewModel by viewModels()

    //val wordsViewModel : SearchFragmentViewModel = ViewModelProvider(this).get(SearchFragmentViewModel::class.java)
    private lateinit var wordsAdapter: WordsAdapter
    private lateinit var rv: RecyclerView
    private lateinit var connectionLiveData: ConnectionLiveData

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val v = inflater.inflate(R.layout.fragment_search, container, false)
        callNetworkConnection()
        return v
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val wordsViewModel: SearchFragmentViewModel by viewModels()
        val progressBar = progress_bar
        val textViewError = text_view_error

        rv = rc_data
        rv.layoutManager = LinearLayoutManager(activity)
        wordsAdapter = WordsAdapter(mutableListOf())
        rv.adapter = wordsAdapter
        progressBar.visibility = View.VISIBLE
        wordsViewModel.wordsLiveData.observe(this, Observer {
            wordsAdapter.setData(it as MutableList<WordsModel>)
            progressBar.visibility = View.GONE

        })
        wordsViewModel.loadData(searchText)


        wordsViewModel.onlineLiveData.observe(this, Observer { connected ->
            if (connected) {
                textViewError.visibility = View.GONE

                wordsViewModel.loadData()
            } else {
                progressBar.visibility = View.GONE


            }

        })

        wordsViewModel.errorMessages.observe(this, Observer { errorMessage ->
            progressBar.visibility = View.GONE
            textViewError.visibility = View.VISIBLE
            textViewError.text = errorMessage
            rv.visibility = View.GONE

        })

    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
        val searchManager = context?.getSystemService(SEARCH_SERVICE) as SearchManager
        val searchView: SearchView =
            menu.findItem(R.id.search).actionView as SearchView
        val item = menu.findItem(R.id.search)
        searchView.setSearchableInfo(searchManager.getSearchableInfo(requireActivity().componentName))
        item.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionExpand(p0: MenuItem?): Boolean {
                return true
            }

            override fun onMenuItemActionCollapse(p0: MenuItem?): Boolean {
                if (wordsViewModel.wordsLiveData.value != null) {
                    wordsAdapter.setData(wordsViewModel.wordsLiveData.value!!)
                }
                return true
            }

        })


        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filter(newText!!)
                wordsAdapter.notifyDataSetChanged()
                return true
            }


        }

        )


    }



    fun filter(text: String) {
        val filteredlist: MutableList<WordsModel> = mutableListOf()
        for (item in wordsAdapter.apiList) {
            if (item.name.toLowerCase().contains(text.toLowerCase())) {

                filteredlist.add(item)

                print(filteredlist.size)
                //Log.d("bbs", "From Remote: $filteredlist")
            }
        }
        if (filteredlist.isEmpty()) {

            Toast.makeText(activity, "No Data Found..", Toast.LENGTH_SHORT).show()
        } else {
            wordsAdapter.setData(filteredlist)


            wordsAdapter.notifyDataSetChanged()
        }
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        val id = item.itemId
        if (id == R.id.sort){
            sortDialog()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun sortDialog() {
        val options = arrayOf("Ascending","Descending")
        val dialog = AlertDialog.Builder(activity)
        dialog.setTitle("Sort")
            .setItems(options){
                    dialogInterface , i ->
                if (i == 0){
                    dialogInterface.dismiss()
                    sortAescending()
                }
                else if (i==1){
                    dialogInterface.dismiss()
                    sortDescending()
                }
            }.show()
    }

    private fun sortDescending() {
        wordsViewModel.wordsLiveData.observe(this, Observer {
            it?.sortByDescending {
                it.quantity
            }
        })

        wordsAdapter.notifyDataSetChanged()
    }

    private fun sortAescending() {
        wordsViewModel.wordsLiveData.observe(this, Observer {
            it?.sortBy {
                it.quantity
            }
        })
        wordsAdapter.notifyDataSetChanged()
    }

    private fun callNetworkConnection() {
        connectionLiveData = ConnectionLiveData(activity!!.applicationContext)
        connectionLiveData.observe(this, { isConnected ->
            if (isConnected) {
            } else {
                val dialogBuilder = AlertDialog.Builder(activity)
                dialogBuilder.setMessage("there is no connection please open wifi or mobile data and try again later")
                    .setCancelable(false)
                    .setPositiveButton("End", DialogInterface.OnClickListener { dialog, id ->
                        activity!!.finish()
                    })
                    .setNegativeButton(
                        "cached data",
                        DialogInterface.OnClickListener { dialog, id ->

                            dialog.cancel()
                        })

                // create dialog box
                val alert = dialogBuilder.create()
                // set title for alert dialog box
                alert.setTitle("Welcome to our app")
                // show alert dialog
                alert.show()
            }

        })

    }


}
