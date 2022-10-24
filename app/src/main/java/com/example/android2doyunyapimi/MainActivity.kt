package com.example.android2doyunyapimi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.android2doyunyapimi.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var tasarim:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tasarim = DataBindingUtil.setContentView(this,R.layout.activity_main)

        tasarim.buttonBasla.setOnClickListener {
            startActivity(Intent(this@MainActivity,OyunEkraniActivity::class.java))
        }
    }
}