package com.example.multithread_game

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.multithread_game.databinding.ActivityGameBinding
import kotlin.concurrent.thread

class GameActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGameBinding

    private lateinit var bgmPlayer: MediaPlayer

    var timer = 0 // 시간 저장
    var started = false // 시작 체크
    var hp = 3 // 목숨
    var score = 0 // 점수
    var number = mutableListOf<Int>()

    var nowNumber = 1 // 현재 눌러야하는 번호
    var count = 10 // 점수
    var btnCheck = false // 점수 체크용도

    var gameClear = true // 게임 클리어
    var gameOver = true // 게임종료

    var timerhandler = object : Handler(Looper.getMainLooper()) { //타이머 핸들러
        override fun handleMessage(msg: Message) {
            val minute = String.format("%02d", timer / 60)
            val second = String.format("%02d", timer % 60)
            binding.tvGameTimer.text = "$minute : $second"
        }
    }
    var scorehandler = object : Handler(Looper.getMainLooper()) { // 점수 핸들러
        override fun handleMessage(msg: Message) {
            binding.tvGameScore.text = "$score"
        }
    }
    var hphandler = object : Handler(Looper.getMainLooper()) { // 목숨 핸들러
        override fun handleMessage(msg: Message) {
            when (hp) {
                3 -> {
                    binding.ivGameLife1.visibility = View.VISIBLE
                    binding.ivGameLife2.visibility = View.VISIBLE
                    binding.ivGameLife3.visibility = View.VISIBLE
                }
                2 -> {
                    binding.ivGameLife1.visibility = View.INVISIBLE
                    binding.ivGameLife2.visibility = View.VISIBLE
                    binding.ivGameLife3.visibility = View.VISIBLE
                }
                1 -> {
                    binding.ivGameLife1.visibility = View.INVISIBLE
                    binding.ivGameLife2.visibility = View.INVISIBLE
                    binding.ivGameLife3.visibility = View.VISIBLE
                }
                else -> {
                    binding.ivGameLife1.visibility = View.INVISIBLE
                    binding.ivGameLife2.visibility = View.INVISIBLE
                    binding.ivGameLife3.visibility = View.INVISIBLE
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bgmPlayer = MediaPlayer.create(this, R.raw.rinne_stopped_time)

        randomNumber() // 숫자 랜덤배치
        scoreCount() // 점수 게산방식

        binding.ivGameStartBtn.setOnClickListener {
            bgmPlayer.isLooping = true
            bgmPlayer.start()
            started = true

            thread(start = true) {
                while (started) {
                    Thread.sleep(1000)
                    if (started) {
                        timer += 1
                        timerhandler.sendEmptyMessage(0)
                        scorehandler.sendEmptyMessage(0)
                        hphandler.sendEmptyMessage(0)
                    }
                }
            }
        }

        // 클릭이 늦어질수록 낮은 점수를 곱하게 된다.
        Thread() {
            while (true) {
                Thread.sleep(1000)
                count -= 1
                Log.d("점수 확인", "$count")
                if (count <= 0) {
                    count = 1
                    Log.d("곱해야할 점수", "$count")
                }
            }
        }.start()

        binding.btnGame1.setOnClickListener {
            if (!started) {
                Toast.makeText(this, "시작버튼을 눌러주세요", Toast.LENGTH_SHORT).show()
            } else if (started && binding.btnGame1.text.toString() != nowNumber.toString()) {
                hp--
                if(score >= 100){
                    score -= 100
                }
                Toast.makeText(this, "HP : $hp", Toast.LENGTH_SHORT).show()
            } else {
                nowNumber++
                count += 10
                btnCheck = true
                binding.btnGame1.visibility = View.INVISIBLE
            }
        }
        binding.btnGame2.setOnClickListener {
            if (!started) {
                Toast.makeText(this, "시작버튼을 눌러주세요", Toast.LENGTH_SHORT).show()
            } else if (started && binding.btnGame2.text.toString() != nowNumber.toString()) {
                hp--
                if(score >= 100){
                    score -= 100
                }
                Toast.makeText(this, "HP : $hp", Toast.LENGTH_SHORT).show()
            } else {
                nowNumber++
                count += 10
                btnCheck = true
                binding.btnGame2.visibility = View.INVISIBLE
            }

        }
        binding.btnGame3.setOnClickListener {
            if (!started) {
                Toast.makeText(this, "시작버튼을 눌러주세요", Toast.LENGTH_SHORT).show()
            } else if (started && binding.btnGame3.text.toString() != nowNumber.toString()) {
                hp--
                if(score >= 100){
                    score -= 100
                }
                Toast.makeText(this, "HP : $hp", Toast.LENGTH_SHORT).show()
            } else {
                nowNumber++
                count += 10
                btnCheck = true
                binding.btnGame3.visibility = View.INVISIBLE
            }

        }
        binding.btnGame4.setOnClickListener {
            if (!started) {
                Toast.makeText(this, "시작버튼을 눌러주세요", Toast.LENGTH_SHORT).show()
            } else if (started && binding.btnGame4.text.toString() != nowNumber.toString()) {
                hp--
                if(score >= 100){
                    score -= 100
                }
                Toast.makeText(this, "HP : $hp", Toast.LENGTH_SHORT).show()
            } else {
                nowNumber++
                count += 10
                btnCheck = true
                binding.btnGame4.visibility = View.INVISIBLE
            }

        }
        binding.btnGame5.setOnClickListener {
            if (!started) {
                Toast.makeText(this, "시작버튼을 눌러주세요", Toast.LENGTH_SHORT).show()
            } else if (started && binding.btnGame5.text.toString() != nowNumber.toString()) {
                hp--
                if(score >= 100){
                    score -= 100
                }
                Toast.makeText(this, "HP : $hp", Toast.LENGTH_SHORT).show()
            } else {
                nowNumber++
                count += 10
                btnCheck = true
                binding.btnGame5.visibility = View.INVISIBLE
            }

        }
        binding.btnGame6.setOnClickListener {
            if (!started) {
                Toast.makeText(this, "시작버튼을 눌러주세요", Toast.LENGTH_SHORT).show()
            } else if (started && binding.btnGame6.text.toString() != nowNumber.toString()) {
                hp--
                if(score >= 100){
                    score -= 100
                }
                Toast.makeText(this, "HP : $hp", Toast.LENGTH_SHORT).show()
            } else {
                nowNumber++
                count += 10
                btnCheck = true
                binding.btnGame6.visibility = View.INVISIBLE
            }

        }
        binding.btnGame7.setOnClickListener {
            if (!started) {
                Toast.makeText(this, "시작버튼을 눌러주세요", Toast.LENGTH_SHORT).show()
            } else if (started && binding.btnGame7.text.toString() != nowNumber.toString()) {
                hp--
                if(score >= 100){
                    score -= 100
                }
                Toast.makeText(this, "HP : $hp", Toast.LENGTH_SHORT).show()
            } else {
                nowNumber++
                count += 10
                btnCheck = true
                binding.btnGame7.visibility = View.INVISIBLE
            }

        }
        binding.btnGame8.setOnClickListener {
            if (!started) {
                Toast.makeText(this, "시작버튼을 눌러주세요", Toast.LENGTH_SHORT).show()
            } else if (started && binding.btnGame8.text.toString() != nowNumber.toString()) {
                hp--
                if(score >= 100){
                    score -= 100
                }
                Toast.makeText(this, "HP : $hp", Toast.LENGTH_SHORT).show()
            } else {
                nowNumber++
                count += 10
                btnCheck = true
                binding.btnGame8.visibility = View.INVISIBLE
            }

        }
        binding.btnGame9.setOnClickListener {
            if (!started) {
                Toast.makeText(this, "시작버튼을 눌러주세요", Toast.LENGTH_SHORT).show()
            } else if (started && binding.btnGame9.text.toString() != nowNumber.toString()) {
                hp--
                if(score >= 100){
                    score -= 100
                }
                Toast.makeText(this, "HP : $hp", Toast.LENGTH_SHORT).show()
            } else {
                nowNumber++
                count += 10
                btnCheck = true
                binding.btnGame9.visibility = View.INVISIBLE
            }

        }
        binding.btnGame10.setOnClickListener {
            if (!started) {
                Toast.makeText(this, "시작버튼을 눌러주세요", Toast.LENGTH_SHORT).show()
            } else if (started && binding.btnGame10.text.toString() != nowNumber.toString()) {
                hp--
                if(score >= 100){
                    score -= 100
                }
                Toast.makeText(this, "HP : $hp", Toast.LENGTH_SHORT).show()
            } else {
                nowNumber++
                count += 10
                btnCheck = true
                binding.btnGame10.visibility = View.INVISIBLE
            }

        }
        binding.btnGame11.setOnClickListener {
            if (!started) {
                Toast.makeText(this, "시작버튼을 눌러주세요", Toast.LENGTH_SHORT).show()
            } else if (started && binding.btnGame11.text.toString() != nowNumber.toString()) {
                hp--
                if(score >= 100){
                    score -= 100
                }
                Toast.makeText(this, "HP : $hp", Toast.LENGTH_SHORT).show()
            } else {
                nowNumber++
                count += 10
                btnCheck = true
                binding.btnGame11.visibility = View.INVISIBLE
            }

        }
        binding.btnGame12.setOnClickListener {
            if (!started) {
                Toast.makeText(this, "시작버튼을 눌러주세요", Toast.LENGTH_SHORT).show()
            } else if (started && binding.btnGame12.text.toString() != nowNumber.toString()) {
                hp--
                if(score >= 100){
                    score -= 100
                }
                Toast.makeText(this, "HP : $hp", Toast.LENGTH_SHORT).show()
            } else {
                nowNumber++
                count += 10
                btnCheck = true
                binding.btnGame12.visibility = View.INVISIBLE
            }

        }
        binding.btnGame13.setOnClickListener {
            if (!started) {
                Toast.makeText(this, "시작버튼을 눌러주세요", Toast.LENGTH_SHORT).show()
            } else if (started && binding.btnGame13.text.toString() != nowNumber.toString()) {
                hp--
                if(score >= 100){
                    score -= 100
                }
                Toast.makeText(this, "HP : $hp", Toast.LENGTH_SHORT).show()
            } else {
                nowNumber++
                count += 10
                btnCheck = true
                binding.btnGame13.visibility = View.INVISIBLE
            }

        }
        binding.btnGame14.setOnClickListener {
            if (!started) {
                Toast.makeText(this, "시작버튼을 눌러주세요", Toast.LENGTH_SHORT).show()
            } else if (started && binding.btnGame14.text.toString() != nowNumber.toString()) {
                hp--
                if(score >= 100){
                    score -= 100
                }
                Toast.makeText(this, "HP : $hp", Toast.LENGTH_SHORT).show()
            } else {
                nowNumber++
                count += 10
                btnCheck = true
                binding.btnGame14.visibility = View.INVISIBLE
            }

        }
        binding.btnGame15.setOnClickListener {
            if (!started) {
                Toast.makeText(this, "시작버튼을 눌러주세요", Toast.LENGTH_SHORT).show()
            } else if (started && binding.btnGame15.text.toString() != nowNumber.toString()) {
                hp--
                if(score >= 100){
                    score -= 100
                }
                Toast.makeText(this, "HP : $hp", Toast.LENGTH_SHORT).show()
            } else {
                nowNumber++
                count += 10
                btnCheck = true
                binding.btnGame15.visibility = View.INVISIBLE
            }

        }
        binding.btnGame16.setOnClickListener {
            if (!started) {
                Toast.makeText(this, "시작버튼을 눌러주세요", Toast.LENGTH_SHORT).show()
            } else if (started && binding.btnGame16.text.toString() != nowNumber.toString()) {
                hp--
                if(score >= 100){
                    score -= 100
                }
                Toast.makeText(this, "HP : $hp", Toast.LENGTH_SHORT).show()
            } else {
                nowNumber++
                count += 10
                btnCheck = true
                binding.btnGame16.visibility = View.INVISIBLE
            }

        }
        binding.btnGame17.setOnClickListener {
            if (!started) {
                Toast.makeText(this, "시작버튼을 눌러주세요", Toast.LENGTH_SHORT).show()
            } else if (started && binding.btnGame17.text.toString() != nowNumber.toString()) {
                hp--
                if(score >= 100){
                    score -= 100
                }
                Toast.makeText(this, "HP : $hp", Toast.LENGTH_SHORT).show()
            } else {
                nowNumber++
                count += 10
                btnCheck = true
                binding.btnGame17.visibility = View.INVISIBLE
            }

        }
        binding.btnGame18.setOnClickListener {
            if (!started) {
                Toast.makeText(this, "시작버튼을 눌러주세요", Toast.LENGTH_SHORT).show()
            } else if (started && binding.btnGame18.text.toString() != nowNumber.toString()) {
                hp--
                if(score >= 100){
                    score -= 100
                }
                Toast.makeText(this, "HP : $hp", Toast.LENGTH_SHORT).show()
            } else {
                nowNumber++
                count += 10
                btnCheck = true
                binding.btnGame18.visibility = View.INVISIBLE
            }

        }
        binding.btnGame19.setOnClickListener {
            if (!started) {
                Toast.makeText(this, "시작버튼을 눌러주세요", Toast.LENGTH_SHORT).show()
            } else if (started && binding.btnGame19.text.toString() != nowNumber.toString()) {
                hp--
                if(score >= 100){
                    score -= 100
                }
                Toast.makeText(this, "HP : $hp", Toast.LENGTH_SHORT).show()
            } else {
                nowNumber++
                count += 10
                btnCheck = true
                binding.btnGame19.visibility = View.INVISIBLE
            }

        }
        binding.btnGame20.setOnClickListener {
            if (!started) {
                Toast.makeText(this, "시작버튼을 눌러주세요", Toast.LENGTH_SHORT).show()
            } else if (started && binding.btnGame20.text.toString() != nowNumber.toString()) {
                hp--
                if(score >= 100){
                    score -= 100
                }
                Toast.makeText(this, "HP : $hp", Toast.LENGTH_SHORT).show()
            } else {
                nowNumber++
                count += 10
                btnCheck = true
                binding.btnGame20.visibility = View.INVISIBLE
            }

        }
        binding.btnGame21.setOnClickListener {
            if (!started) {
                Toast.makeText(this, "시작버튼을 눌러주세요", Toast.LENGTH_SHORT).show()
            } else if (started && binding.btnGame21.text.toString() != nowNumber.toString()) {
                hp--
                if(score >= 100){
                    score -= 100
                }
                Toast.makeText(this, "HP : $hp", Toast.LENGTH_SHORT).show()
            } else {
                nowNumber++
                count += 10
                btnCheck = true
                binding.btnGame21.visibility = View.INVISIBLE
            }

        }
        binding.btnGame22.setOnClickListener {
            if (!started) {
                Toast.makeText(this, "시작버튼을 눌러주세요", Toast.LENGTH_SHORT).show()
            } else if (started && binding.btnGame22.text.toString() != nowNumber.toString()) {
                hp--
                if(score >= 100){
                    score -= 100
                }
                Toast.makeText(this, "HP : $hp", Toast.LENGTH_SHORT).show()
            } else {
                nowNumber++
                count += 10
                btnCheck = true
                binding.btnGame22.visibility = View.INVISIBLE
            }

        }
        binding.btnGame23.setOnClickListener {
            if (!started) {
                Toast.makeText(this, "시작버튼을 눌러주세요", Toast.LENGTH_SHORT).show()
            } else if (started && binding.btnGame23.text.toString() != nowNumber.toString()) {
                hp--
                if(score >= 100){
                    score -= 100
                }
                Toast.makeText(this, "HP : $hp", Toast.LENGTH_SHORT).show()
            } else {
                nowNumber++
                count += 10
                btnCheck = true
                binding.btnGame23.visibility = View.INVISIBLE
            }

        }
        binding.btnGame24.setOnClickListener {
            if (!started) {
                Toast.makeText(this, "시작버튼을 눌러주세요", Toast.LENGTH_SHORT).show()
            } else if (started && binding.btnGame24.text.toString() != nowNumber.toString()) {
                hp--
                if(score >= 100){
                    score -= 100
                }
                Toast.makeText(this, "HP : $hp", Toast.LENGTH_SHORT).show()
            } else {
                nowNumber++
                count += 10
                btnCheck = true
                binding.btnGame24.visibility = View.INVISIBLE
            }

        }
        binding.btnGame25.setOnClickListener {
            if (!started) {
                Toast.makeText(this, "시작버튼을 눌러주세요", Toast.LENGTH_SHORT).show()
            } else if (started && binding.btnGame25.text.toString() != nowNumber.toString()) {
                hp--
                if(score >= 100){
                    score -= 100
                }
                Toast.makeText(this, "HP : $hp", Toast.LENGTH_SHORT).show()
            } else {
                nowNumber++
                count += 10
                btnCheck = true
                binding.btnGame25.visibility = View.INVISIBLE
            }
        }

        Thread() {
            while (gameOver){
                if (hp == 0){
                    var intent = Intent(this, OverActivity::class.java)
                    intent.putExtra("time", timer.toString())
                    intent.putExtra("score", score.toString())
                    bgmPlayer.stop()
                    gameOver = false // false로 바꾸면 스레드 종료함
                    startActivity(intent)
                    finish()
                }
            }
        }.start()

        Thread(){
            while (gameClear){
                if (nowNumber == 26){
                    var intent = Intent(this, ClearActivity::class.java)
                    intent.putExtra("time", timer.toString())
                    intent.putExtra("score", score.toString())
                    bgmPlayer.stop()
                    gameClear = false
                    startActivity(intent)
                    finish()
                }
            }
        }.start()
    }

    // 점수 계산
    private fun scoreCount() {
        Thread() {
            while (true){
                if (btnCheck) {
                    score += count * nowNumber
                    Log.d("점수확인", "$score $count $nowNumber")
                    btnCheck = false
                    println(score)
                }
            }
        }.start()
    }

    private fun randomNumber() {

        for (i in 1..25) {
            number.add(i)
        }
        number.shuffle()
        Log.d("섞인 숫자 확인", "$number")

        binding.btnGame1.text = number[0].toString()
        binding.btnGame2.text = number[1].toString()
        binding.btnGame3.text = number[2].toString()
        binding.btnGame4.text = number[3].toString()
        binding.btnGame5.text = number[4].toString()
        binding.btnGame6.text = number[5].toString()
        binding.btnGame7.text = number[6].toString()
        binding.btnGame8.text = number[7].toString()
        binding.btnGame9.text = number[8].toString()
        binding.btnGame10.text = number[9].toString()
        binding.btnGame11.text = number[10].toString()
        binding.btnGame12.text = number[11].toString()
        binding.btnGame13.text = number[12].toString()
        binding.btnGame14.text = number[13].toString()
        binding.btnGame15.text = number[14].toString()
        binding.btnGame16.text = number[15].toString()
        binding.btnGame17.text = number[16].toString()
        binding.btnGame18.text = number[17].toString()
        binding.btnGame19.text = number[18].toString()
        binding.btnGame20.text = number[19].toString()
        binding.btnGame21.text = number[20].toString()
        binding.btnGame22.text = number[21].toString()
        binding.btnGame23.text = number[22].toString()
        binding.btnGame24.text = number[23].toString()
        binding.btnGame25.text = number[24].toString()
    }
}