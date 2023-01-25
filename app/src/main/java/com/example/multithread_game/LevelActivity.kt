package com.example.multithread_game

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.multithread_game.databinding.ActivityLevelBinding

class LevelActivity : AppCompatActivity() {

    private lateinit var binding : ActivityLevelBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLevelBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ivLevelHome.setOnClickListener {
            var intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.btnLevelEasy.setOnClickListener {
            var intent = Intent(this, GameActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.btnLevelNormal.setOnClickListener {
            var intent = Intent(this, GameActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.btnLevelHard.setOnClickListener {
            var intent = Intent(this, GameActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}