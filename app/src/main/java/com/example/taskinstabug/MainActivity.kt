package com.example.taskinstabug
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders

class MainActivity : AppCompatActivity() {
   // val model: SearchFragmentViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val memeFragment = SearchFragment()
        val fragmentManager : FragmentManager = supportFragmentManager
        fragmentManager.beginTransaction().add(R.id.main_layout, memeFragment).commit()
    }


}