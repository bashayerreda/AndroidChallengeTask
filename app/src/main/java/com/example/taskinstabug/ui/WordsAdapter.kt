package com.example.taskinstabug

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.instabugtask.pojo.WordsModel
import kotlinx.android.synthetic.main.row_items.view.*

class WordsAdapter(var apiList: MutableList<WordsModel>) : RecyclerView.Adapter<WordsViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordsViewHolder {
        return WordsViewHolder(
            LayoutInflater.from(parent.getContext()).inflate(
                R.layout.row_items,
                parent,
                false
            )
        )
    }
    fun setData(apilist: MutableList<WordsModel>) {
        this.apiList = apilist

        notifyDataSetChanged()
        Log.d("ba", "api: $apiList")
    }


    override fun onBindViewHolder(holder: WordsViewHolder, position: Int) {
        holder.onBind(apiList[position])
    }

    override fun getItemCount(): Int {
        return apiList.size
    }

    }


class WordsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val word_name = itemView.searched_words
    val word_count = itemView.rep_numbers

    fun onBind(wordsModel: WordsModel) {
        word_name.text = wordsModel.name
        word_count.text = wordsModel.quantity.toString()
    }



}
