package com.example.multithread_game

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.multithread_game.databinding.ActivityClearBinding
import com.example.multithread_game.databinding.ActivityGameBinding

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

        binding.tvGameTime.text = "시간 : $clearM : $clearS"

        binding.tvClearScore.text = "점수 : "+ clearScore.toString()

    }
}