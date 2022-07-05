package com.app.skillasessment

import android.content.Intent

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AnimationUtils
import com.app.skillasessment.databinding.ActivitySplashBinding
import java.util.*

class Splash : AppCompatActivity() {
    lateinit var binding: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val animZoomIn = AnimationUtils.loadAnimation(this, R.anim.zoom_in)
        binding.logo.startAnimation(animZoomIn)
        val tim= Timer()
        tim.schedule(object : TimerTask() {
            override fun run() {
               startActivity(Intent(this@Splash,MainActivity::class.java))
                finish()
            }
        }, 2000)
    }
}