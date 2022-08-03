package com.example.multithread_game

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.multithread_game.databinding.ActivityGameBinding
import com.example.multithread_game.databinding.ActivityOverBinding

class OverActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOverBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityOverBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val clearTime = intent.getStringExtra("time")
        val clearScore = intent.getStringExtra("score")

        val clearM = clearTime!!.toInt() / 60
        val clearS = clearTime.toInt() % 60

        binding.tvOverTime.text = "시간 : $clearM : $clearS"

        binding.tvOverScore.text = "점수 : "+ clearScore.toString()
    }
}