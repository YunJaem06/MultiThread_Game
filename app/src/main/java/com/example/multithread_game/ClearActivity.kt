package com.example.multithread_game

import android.content.Intent
import android.graphics.drawable.LevelListDrawable
import android.os.Bundle
import android.util.Log
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
        val clearLevel = intent.getIntExtra("level", 0)

        val clearM = clearTime!!.toInt() / 60
        val clearS = clearTime.toInt() % 60

        binding.tvGameTime.text = "$clearM : $clearS"

        binding.tvClearScore.text = "점수 : "+ clearScore.toString()

        var bestScore = 0

        when (clearLevel) {
            1 -> {
                bestScore = GlobalApplication.pref.getInt("easyscore", 0)
            }
            2 -> {
                bestScore = GlobalApplication.pref.getInt("normalscore", 0)
            }
            3 -> {
                bestScore = GlobalApplication.pref.getInt("hardscore", 0)
            }
        }
        Log.d("최고 점수", "$bestScore")

        if (clearLevel == 1) {
            if (bestScore < clearScore!!.toInt()) {
                GlobalApplication.pref.edit().putInt("easyscore", clearScore.toInt()).apply()
                GlobalApplication.pref.edit().putString("easytime", clearTime.toString()).apply()
            }
        } else if (clearLevel == 2) {
            if (bestScore < clearScore!!.toInt()) {
                GlobalApplication.pref.edit().putInt("normalscore", clearScore.toInt()).apply()
                GlobalApplication.pref.edit().putString("normaltime", clearTime.toString()).apply()
            }
        } else if (clearLevel == 3) {
            if (bestScore < clearScore!!.toInt()){
                GlobalApplication.pref.edit().putInt("hardscore",clearScore.toInt()).apply()
                GlobalApplication.pref.edit().putString("hardtime", clearScore.toString()).apply()
            }
        }

        binding.ivClearHome.setOnClickListener {
            var intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        binding.ivClearReplay.setOnClickListener {
            var intent = Intent(this, LevelActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}