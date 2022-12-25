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

        binding.btnLevelEasy.setOnClickListener {
            val intent = Intent(this,GameActivity::class.java)
            intent.putExtra("level",1)
            startActivity(intent)
            finish()
        }
        binding.btnLevelNormal.setOnClickListener {
            val intent = Intent(this,GameActivity::class.java)
            intent.putExtra("level",2)
            startActivity(intent)
            finish()
        }
        binding.btnLevelHard.setOnClickListener {
            val intent = Intent(this,GameActivity::class.java)
            intent.putExtra("level",3)
            startActivity(intent)
            finish()
        }
        binding.btnLevelBack.setOnClickListener {
            finish()
        }
    }
}