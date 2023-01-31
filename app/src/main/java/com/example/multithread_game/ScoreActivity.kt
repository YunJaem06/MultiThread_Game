package com.example.multithread_game

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.multithread_game.GlobalApplication.Companion.pref
import com.example.multithread_game.databinding.ActivityScoreBinding

class ScoreActivity : AppCompatActivity() {

    var easyRecord : String? = ""
    var normalRecord : String? = ""
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

        easyRecord = pref.getString("easytime", easyRecord)
        normalRecord = pref.getString("normaltime", normalRecord)
        hardRecord = pref.getString("hardtime", hardRecord)
        easyScore = pref.getInt("easyscore", easyScore)
        normalScore = pref.getInt("normalscore", normalScore)
        hardScore = pref.getInt("hardscore", hardScore)

        binding.apply {
            if (easyScore == 0) {
                tvScoreEasyTime.text = "00 : 00"
                tvScoreEasyScore.text = "0"
            } else {
                easyTime = easyRecord!!.toLong()
                tvScoreEasyTime.text = "${easyTime / 60} : ${easyTime % 60}"
                tvScoreEasyScore.text = easyScore.toString()
            }
            if (normalScore == 0) {
                tvScoreNormalTime.text = "00 : 00"
                tvScoreNormalScore.text = "0"
            } else {
                normalTime = normalRecord!!.toLong()
                tvScoreNormalTime.text = "${normalTime / 60} : ${normalTime % 60}"
                tvScoreNormalScore.text = normalScore.toString()
            }
            if (hardScore == 0) {
                tvScoreHardTime.text = "00 : 00"
                tvScoreHardScore.text = "0"
            } else {
                hardTime = hardRecord!!.toLong()
                tvScoreHardTime.text = "${hardTime / 60} : ${hardTime % 60}"
                tvScoreHardScore.text = hardScore.toString()
            }

            btnScoreHome.setOnClickListener {
                finish()
            }
        }


    }
}