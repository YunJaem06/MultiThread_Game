package com.example.multithread_game

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.multithread_game.databinding.ActivityMainBinding
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnMainStart.setOnClickListener {
            var intent = Intent(this, GameActivity::class.java)
            startActivity(intent)
            finish()
        }
        binding.btnMainScore.setOnClickListener {
            var intent = Intent(this, ScoreActivity::class.java)
            startActivity(intent)
        }

        binding.btnMainExit.setOnClickListener {
            exitProcess(0)
        }
    }
}