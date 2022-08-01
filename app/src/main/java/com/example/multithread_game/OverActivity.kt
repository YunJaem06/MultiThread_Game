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
    }
}