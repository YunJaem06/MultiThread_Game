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
    }
}