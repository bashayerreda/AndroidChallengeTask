package com.example.taskinstabug.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import com.example.taskinstabug.R
import kotlinx.android.synthetic.main.activity_for_splash_screen.*


class ForSplashScreen : AppCompatActivity() {
    var top_down: Animation? = null
    var down_top: Animation? = null
    var imageView: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_for_splash_screen)
        top_down = AnimationUtils.loadAnimation(this, R.anim.top_down)
        down_top = AnimationUtils.loadAnimation(this, R.anim.down_top)
        imageView = splash_img
        imageView?.animation = top_down

        val secondsDelayed = 3000
        Handler().postDelayed({
            val intent = Intent(
                this, MainActivity::class.java
            )
            startActivity(intent)
           finishAffinity()
        }, secondsDelayed.toLong())
}}