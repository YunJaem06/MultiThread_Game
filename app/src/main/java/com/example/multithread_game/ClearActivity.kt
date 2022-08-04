package com.example.multithread_game

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.multithread_game.databinding.ActivityClearBinding
import com.example.multithread_game.databinding.ActivityGameBinding
import kotlin.system.exitProcess

class ClearActivity : AppCompatActivity() {

    private lateinit var binding: ActivityClearBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityClearBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val clearTime = intent.getStringExtra("time")
        val clearScore = intent.getStringExtra("score")

        val clearM = clearTime!!.toInt() / 60
        val clearS = clearTime.toInt() % 60

        binding.tvGameTime.text = "$clearM : $clearS"

        binding.tvClearScore.text = "점수 : "+ clearScore.toString()

        binding.ivClearHome.setOnClickListener {
            var intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        binding.ivClearReplay.setOnClickListener {
            var intent = Intent(this, GameActivity::class.java)
            startActivity(intent)
            finish()
        }
        binding.ivClearExit.setOnClickListener {
            exitProcess(0)
        }
    }
}