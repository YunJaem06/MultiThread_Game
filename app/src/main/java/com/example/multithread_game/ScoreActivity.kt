package com.example.multithread_game

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.multithread_game.databinding.ActivityScoreBinding

class ScoreActivity : AppCompatActivity() {

    var easyRecord : String? = ""
    var commonRecord : String? = ""
    var hardRecord : String? = ""
    var easyScore = 0
    var normalScore = 0
    var hardScore = 0
    var easyTime : Long = 0
    var normalTime : Long = 0
    var hardTime : Long = 0

    private lateinit var binding : ActivityScoreBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityScoreBinding.inflate(layoutInflater)
        setContentView(binding.root)

        easyRecord = GlobalApplication.pref.getString("easy", easyRecord)
        commonRecord = GlobalApplication.pref.getString("normal", commonRecord)
        hardRecord = GlobalApplication.pref.getString("hard", hardRecord)
    }
}