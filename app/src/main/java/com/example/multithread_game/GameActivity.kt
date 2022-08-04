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
    var number = mutableListOf<Int>() // 1부터 25숫자 넣는곳
    var number2 = mutableListOf<Int>() // 26부터 50숫자 넣는곳

    var nowNumber = 1 // 현재 눌러야하는 번호
    var count = 10 // 점수
    var btnCheck = false // 점수 체크용도

    var gameClear = true // 게임 클리어
    var gameOver = true // 게임종료

    var threadStop = true

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

        randomNumber25() // 숫자 랜덤배치
        randomNumber50()
        scoreCount() // 점수 게산방식

        binding.ivGameStartBtn.setOnClickListener {
            bgmPlayer.isLooping = true
            bgmPlayer.start()
            started = true
            threadStop = true

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

            // 클릭이 늦어질수록 낮은 점수를 곱하게 된다.
            Thread() {
                while (threadStop) {
                    Thread.sleep(1000)
                    count -= 1
                    Log.d("점수 확인", "$count")
                    if (count <= 0) {
                        count = 1
                        Log.d("곱해야할 점수", "$count")
                    }
                }
            }.start()
        }

        binding.btnGame1.setOnClickListener {
            if (!started) {
                Toast.makeText(this, "시작버튼을 눌러주세요", Toast.LENGTH_SHORT).show()
            } else if (started && binding.btnGame1.text.toString() != nowNumber.toString()) {
                hp--
                if (score >= 100) {
                    score -= 100
                }
                Toast.makeText(this, "HP : $hp", Toast.LENGTH_SHORT).show()
            } else {
                nowNumber++
                binding.tvGameNownumber.text = nowNumber.toString()
                count += 2
                btnCheck = true
                binding.btnGame1.visibility = View.INVISIBLE
            }
        }
        binding.btnGame2.setOnClickListener {
            if (!started) {
                Toast.makeText(this, "시작버튼을 눌러주세요", Toast.LENGTH_SHORT).show()
            } else if (started && binding.btnGame2.text.toString() != nowNumber.toString()) {
                hp--
                if (score >= 100) {
                    score -= 100
                }
                Toast.makeText(this, "HP : $hp", Toast.LENGTH_SHORT).show()
            } else {
                nowNumber++
                binding.tvGameNownumber.text = nowNumber.toString()
                count += 2
                btnCheck = true
                binding.btnGame2.visibility = View.INVISIBLE
            }

        }
        binding.btnGame3.setOnClickListener {
            if (!started) {
                Toast.makeText(this, "시작버튼을 눌러주세요", Toast.LENGTH_SHORT).show()
            } else if (started && binding.btnGame3.text.toString() != nowNumber.toString()) {
                hp--
                if (score >= 100) {
                    score -= 100
                }
                Toast.makeText(this, "HP : $hp", Toast.LENGTH_SHORT).show()
            } else {
                nowNumber++
                binding.tvGameNownumber.text = nowNumber.toString()
                count += 2
                btnCheck = true
                binding.btnGame3.visibility = View.INVISIBLE
            }

        }
        binding.btnGame4.setOnClickListener {
            if (!started) {
                Toast.makeText(this, "시작버튼을 눌러주세요", Toast.LENGTH_SHORT).show()
            } else if (started && binding.btnGame4.text.toString() != nowNumber.toString()) {
                hp--
                if (score >= 100) {
                    score -= 100
                }
                Toast.makeText(this, "HP : $hp", Toast.LENGTH_SHORT).show()
            } else {
                nowNumber++
                binding.tvGameNownumber.text = nowNumber.toString()
                count += 5
                btnCheck = true
                binding.btnGame4.visibility = View.INVISIBLE
            }

        }
        binding.btnGame5.setOnClickListener {
            if (!started) {
                Toast.makeText(this, "시작버튼을 눌러주세요", Toast.LENGTH_SHORT).show()
            } else if (started && binding.btnGame5.text.toString() != nowNumber.toString()) {
                hp--
                if (score >= 100) {
                    score -= 100
                }
                Toast.makeText(this, "HP : $hp", Toast.LENGTH_SHORT).show()
            } else {
                nowNumber++
                binding.tvGameNownumber.text = nowNumber.toString()
                count += 5
                btnCheck = true
                binding.btnGame5.visibility = View.INVISIBLE
            }

        }
        binding.btnGame6.setOnClickListener {
            if (!started) {
                Toast.makeText(this, "시작버튼을 눌러주세요", Toast.LENGTH_SHORT).show()
            } else if (started && binding.btnGame6.text.toString() != nowNumber.toString()) {
                hp--
                if (score >= 100) {
                    score -= 100
                }
                Toast.makeText(this, "HP : $hp", Toast.LENGTH_SHORT).show()
            } else {
                nowNumber++
                binding.tvGameNownumber.text = nowNumber.toString()
                count += 5
                btnCheck = true
                binding.btnGame6.visibility = View.INVISIBLE
            }

        }
        binding.btnGame7.setOnClickListener {
            if (!started) {
                Toast.makeText(this, "시작버튼을 눌러주세요", Toast.LENGTH_SHORT).show()
            } else if (started && binding.btnGame7.text.toString() != nowNumber.toString()) {
                hp--
                if (score >= 100) {
                    score -= 100
                }
                Toast.makeText(this, "HP : $hp", Toast.LENGTH_SHORT).show()
            } else {
                nowNumber++
                binding.tvGameNownumber.text = nowNumber.toString()
                count += 5
                btnCheck = true
                binding.btnGame7.visibility = View.INVISIBLE
            }

        }
        binding.btnGame8.setOnClickListener {
            if (!started) {
                Toast.makeText(this, "시작버튼을 눌러주세요", Toast.LENGTH_SHORT).show()
            } else if (started && binding.btnGame8.text.toString() != nowNumber.toString()) {
                hp--
                if (score >= 100) {
                    score -= 100
                }
                Toast.makeText(this, "HP : $hp", Toast.LENGTH_SHORT).show()
            } else {
                nowNumber++
                binding.tvGameNownumber.text = nowNumber.toString()
                count += 5
                btnCheck = true
                binding.btnGame8.visibility = View.INVISIBLE
            }

        }
        binding.btnGame9.setOnClickListener {
            if (!started) {
                Toast.makeText(this, "시작버튼을 눌러주세요", Toast.LENGTH_SHORT).show()
            } else if (started && binding.btnGame9.text.toString() != nowNumber.toString()) {
                hp--
                if (score >= 100) {
                    score -= 100
                }
                Toast.makeText(this, "HP : $hp", Toast.LENGTH_SHORT).show()
            } else {
                nowNumber++
                binding.tvGameNownumber.text = nowNumber.toString()
                count += 5
                btnCheck = true
                binding.btnGame9.visibility = View.INVISIBLE
            }

        }
        binding.btnGame10.setOnClickListener {
            if (!started) {
                Toast.makeText(this, "시작버튼을 눌러주세요", Toast.LENGTH_SHORT).show()
            } else if (started && binding.btnGame10.text.toString() != nowNumber.toString()) {
                hp--
                if (score >= 100) {
                    score -= 100
                }
                Toast.makeText(this, "HP : $hp", Toast.LENGTH_SHORT).show()
            } else {
                nowNumber++
                binding.tvGameNownumber.text = nowNumber.toString()
                count += 5
                btnCheck = true
                binding.btnGame10.visibility = View.INVISIBLE
            }

        }
        binding.btnGame11.setOnClickListener {
            if (!started) {
                Toast.makeText(this, "시작버튼을 눌러주세요", Toast.LENGTH_SHORT).show()
            } else if (started && binding.btnGame11.text.toString() != nowNumber.toString()) {
                hp--
                if (score >= 100) {
                    score -= 100
                }
                Toast.makeText(this, "HP : $hp", Toast.LENGTH_SHORT).show()
            } else {
                nowNumber++
                binding.tvGameNownumber.text = nowNumber.toString()
                count += 5
                btnCheck = true
                binding.btnGame11.visibility = View.INVISIBLE
            }

        }
        binding.btnGame12.setOnClickListener {
            if (!started) {
                Toast.makeText(this, "시작버튼을 눌러주세요", Toast.LENGTH_SHORT).show()
            } else if (started && binding.btnGame12.text.toString() != nowNumber.toString()) {
                hp--
                if (score >= 100) {
                    score -= 100
                }
                Toast.makeText(this, "HP : $hp", Toast.LENGTH_SHORT).show()
            } else {
                nowNumber++
                binding.tvGameNownumber.text = nowNumber.toString()
                count += 5
                btnCheck = true
                binding.btnGame12.visibility = View.INVISIBLE
            }

        }
        binding.btnGame13.setOnClickListener {
            if (!started) {
                Toast.makeText(this, "시작버튼을 눌러주세요", Toast.LENGTH_SHORT).show()
            } else if (started && binding.btnGame13.text.toString() != nowNumber.toString()) {
                hp--
                if (score >= 100) {
                    score -= 100
                }
                Toast.makeText(this, "HP : $hp", Toast.LENGTH_SHORT).show()
            } else {
                nowNumber++
                binding.tvGameNownumber.text = nowNumber.toString()
                count += 5
                btnCheck = true
                binding.btnGame13.visibility = View.INVISIBLE
            }

        }
        binding.btnGame14.setOnClickListener {
            if (!started) {
                Toast.makeText(this, "시작버튼을 눌러주세요", Toast.LENGTH_SHORT).show()
            } else if (started && binding.btnGame14.text.toString() != nowNumber.toString()) {
                hp--
                if (score >= 100) {
                    score -= 100
                }
                Toast.makeText(this, "HP : $hp", Toast.LENGTH_SHORT).show()
            } else {
                nowNumber++
                binding.tvGameNownumber.text = nowNumber.toString()
                count += 5
                btnCheck = true
                binding.btnGame14.visibility = View.INVISIBLE
            }

        }
        binding.btnGame15.setOnClickListener {
            if (!started) {
                Toast.makeText(this, "시작버튼을 눌러주세요", Toast.LENGTH_SHORT).show()
            } else if (started && binding.btnGame15.text.toString() != nowNumber.toString()) {
                hp--
                if (score >= 100) {
                    score -= 100
                }
                Toast.makeText(this, "HP : $hp", Toast.LENGTH_SHORT).show()
            } else {
                nowNumber++
                binding.tvGameNownumber.text = nowNumber.toString()
                count += 5
                btnCheck = true
                binding.btnGame15.visibility = View.INVISIBLE
            }

        }
        binding.btnGame16.setOnClickListener {
            if (!started) {
                Toast.makeText(this, "시작버튼을 눌러주세요", Toast.LENGTH_SHORT).show()
            } else if (started && binding.btnGame16.text.toString() != nowNumber.toString()) {
                hp--
                if (score >= 100) {
                    score -= 100
                }
                Toast.makeText(this, "HP : $hp", Toast.LENGTH_SHORT).show()
            } else {
                nowNumber++
                binding.tvGameNownumber.text = nowNumber.toString()
                count += 5
                btnCheck = true
                binding.btnGame16.visibility = View.INVISIBLE
            }

        }
        binding.btnGame17.setOnClickListener {
            if (!started) {
                Toast.makeText(this, "시작버튼을 눌러주세요", Toast.LENGTH_SHORT).show()
            } else if (started && binding.btnGame17.text.toString() != nowNumber.toString()) {
                hp--
                if (score >= 100) {
                    score -= 100
                }
                Toast.makeText(this, "HP : $hp", Toast.LENGTH_SHORT).show()
            } else {
                nowNumber++
                binding.tvGameNownumber.text = nowNumber.toString()
                count += 5
                btnCheck = true
                binding.btnGame17.visibility = View.INVISIBLE
            }

        }
        binding.btnGame18.setOnClickListener {
            if (!started) {
                Toast.makeText(this, "시작버튼을 눌러주세요", Toast.LENGTH_SHORT).show()
            } else if (started && binding.btnGame18.text.toString() != nowNumber.toString()) {
                hp--
                if (score >= 100) {
                    score -= 100
                }
                Toast.makeText(this, "HP : $hp", Toast.LENGTH_SHORT).show()
            } else {
                nowNumber++
                binding.tvGameNownumber.text = nowNumber.toString()
                count += 5
                btnCheck = true
                binding.btnGame18.visibility = View.INVISIBLE
            }

        }
        binding.btnGame19.setOnClickListener {
            if (!started) {
                Toast.makeText(this, "시작버튼을 눌러주세요", Toast.LENGTH_SHORT).show()
            } else if (started && binding.btnGame19.text.toString() != nowNumber.toString()) {
                hp--
                if (score >= 100) {
                    score -= 100
                }
                Toast.makeText(this, "HP : $hp", Toast.LENGTH_SHORT).show()
            } else {
                nowNumber++
                binding.tvGameNownumber.text = nowNumber.toString()
                count += 5
                btnCheck = true
                binding.btnGame19.visibility = View.INVISIBLE
            }

        }
        binding.btnGame20.setOnClickListener {
            if (!started) {
                Toast.makeText(this, "시작버튼을 눌러주세요", Toast.LENGTH_SHORT).show()
            } else if (started && binding.btnGame20.text.toString() != nowNumber.toString()) {
                hp--
                if (score >= 100) {
                    score -= 100
                }
                Toast.makeText(this, "HP : $hp", Toast.LENGTH_SHORT).show()
            } else {
                nowNumber++
                binding.tvGameNownumber.text = nowNumber.toString()
                count += 5
                btnCheck = true
                binding.btnGame20.visibility = View.INVISIBLE
            }

        }
        binding.btnGame21.setOnClickListener {
            if (!started) {
                Toast.makeText(this, "시작버튼을 눌러주세요", Toast.LENGTH_SHORT).show()
            } else if (started && binding.btnGame21.text.toString() != nowNumber.toString()) {
                hp--
                if (score >= 100) {
                    score -= 100
                }
                Toast.makeText(this, "HP : $hp", Toast.LENGTH_SHORT).show()
            } else {
                nowNumber++
                binding.tvGameNownumber.text = nowNumber.toString()
                count += 5
                btnCheck = true
                binding.btnGame21.visibility = View.INVISIBLE
            }

        }
        binding.btnGame22.setOnClickListener {
            if (!started) {
                Toast.makeText(this, "시작버튼을 눌러주세요", Toast.LENGTH_SHORT).show()
            } else if (started && binding.btnGame22.text.toString() != nowNumber.toString()) {
                hp--
                if (score >= 100) {
                    score -= 100
                }
                Toast.makeText(this, "HP : $hp", Toast.LENGTH_SHORT).show()
            } else {
                nowNumber++
                binding.tvGameNownumber.text = nowNumber.toString()
                count += 5
                btnCheck = true
                binding.btnGame22.visibility = View.INVISIBLE
            }

        }
        binding.btnGame23.setOnClickListener {
            if (!started) {
                Toast.makeText(this, "시작버튼을 눌러주세요", Toast.LENGTH_SHORT).show()
            } else if (started && binding.btnGame23.text.toString() != nowNumber.toString()) {
                hp--
                if (score >= 100) {
                    score -= 100
                }
                Toast.makeText(this, "HP : $hp", Toast.LENGTH_SHORT).show()
            } else {
                nowNumber++
                binding.tvGameNownumber.text = nowNumber.toString()
                count += 5
                btnCheck = true
                binding.btnGame23.visibility = View.INVISIBLE
            }

        }
        binding.btnGame24.setOnClickListener {
            if (!started) {
                Toast.makeText(this, "시작버튼을 눌러주세요", Toast.LENGTH_SHORT).show()
            } else if (started && binding.btnGame24.text.toString() != nowNumber.toString()) {
                hp--
                if (score >= 100) {
                    score -= 100
                }
                Toast.makeText(this, "HP : $hp", Toast.LENGTH_SHORT).show()
            } else {
                nowNumber++
                binding.tvGameNownumber.text = nowNumber.toString()
                count += 5
                btnCheck = true
                binding.btnGame24.visibility = View.INVISIBLE
            }

        }
        binding.btnGame25.setOnClickListener {
            if (!started) {
                Toast.makeText(this, "시작버튼을 눌러주세요", Toast.LENGTH_SHORT).show()
            } else if (started && binding.btnGame25.text.toString() != nowNumber.toString()) {
                hp--
                if (score >= 100) {
                    score -= 100
                }
                Toast.makeText(this, "HP : $hp", Toast.LENGTH_SHORT).show()
            } else {
                nowNumber++
                binding.tvGameNownumber.text = nowNumber.toString()
                count += 5
                btnCheck = true
                binding.btnGame25.visibility = View.INVISIBLE
            }
        }
        binding.btnGame26.setOnClickListener {
            if (!started) {
                Toast.makeText(this, "시작버튼을 눌러주세요", Toast.LENGTH_SHORT).show()
            } else if (started && binding.btnGame26.text.toString() != nowNumber.toString()) {
                hp--
                if (score >= 100) {
                    score -= 10000
                }
                Toast.makeText(this, "HP : $hp", Toast.LENGTH_SHORT).show()
            } else {
                nowNumber++
                binding.tvGameNownumber.text = nowNumber.toString()
                count += 10
                btnCheck = true
                binding.btnGame26.visibility = View.INVISIBLE
            }
        }
        binding.btnGame27.setOnClickListener {
            if (!started) {
                Toast.makeText(this, "시작버튼을 눌러주세요", Toast.LENGTH_SHORT).show()
            } else if (started && binding.btnGame27.text.toString() != nowNumber.toString()) {
                hp--
                if (score >= 100) {
                    score -= 10000
                }
                Toast.makeText(this, "HP : $hp", Toast.LENGTH_SHORT).show()
            } else {
                nowNumber++
                binding.tvGameNownumber.text = nowNumber.toString()
                count += 10
                btnCheck = true
                binding.btnGame27.visibility = View.INVISIBLE
            }
        }
        binding.btnGame28.setOnClickListener {
            if (!started) {
                Toast.makeText(this, "시작버튼을 눌러주세요", Toast.LENGTH_SHORT).show()
            } else if (started && binding.btnGame28.text.toString() != nowNumber.toString()) {
                hp--
                if (score >= 100) {
                    score -= 10000
                }
                Toast.makeText(this, "HP : $hp", Toast.LENGTH_SHORT).show()
            } else {
                nowNumber++
                binding.tvGameNownumber.text = nowNumber.toString()
                count += 10
                btnCheck = true
                binding.btnGame28.visibility = View.INVISIBLE
            }
        }
        binding.btnGame29.setOnClickListener {
            if (!started) {
                Toast.makeText(this, "시작버튼을 눌러주세요", Toast.LENGTH_SHORT).show()
            } else if (started && binding.btnGame29.text.toString() != nowNumber.toString()) {
                hp--
                if (score >= 100) {
                    score -= 10000
                }
                Toast.makeText(this, "HP : $hp", Toast.LENGTH_SHORT).show()
            } else {
                nowNumber++
                binding.tvGameNownumber.text = nowNumber.toString()
                count += 10
                btnCheck = true
                binding.btnGame29.visibility = View.INVISIBLE
            }
        }
        binding.btnGame30.setOnClickListener {
            if (!started) {
                Toast.makeText(this, "시작버튼을 눌러주세요", Toast.LENGTH_SHORT).show()
            } else if (started && binding.btnGame30.text.toString() != nowNumber.toString()) {
                hp--
                if (score >= 100) {
                    score -= 10000
                }
                Toast.makeText(this, "HP : $hp", Toast.LENGTH_SHORT).show()
            } else {
                nowNumber++
                binding.tvGameNownumber.text = nowNumber.toString()
                count += 10
                btnCheck = true
                binding.btnGame30.visibility = View.INVISIBLE
            }
        }
        binding.btnGame31.setOnClickListener {
            if (!started) {
                Toast.makeText(this, "시작버튼을 눌러주세요", Toast.LENGTH_SHORT).show()
            } else if (started && binding.btnGame31.text.toString() != nowNumber.toString()) {
                hp--
                if (score >= 100) {
                    score -= 10000
                }
                Toast.makeText(this, "HP : $hp", Toast.LENGTH_SHORT).show()
            } else {
                nowNumber++
                binding.tvGameNownumber.text = nowNumber.toString()
                count += 10
                btnCheck = true
                binding.btnGame31.visibility = View.INVISIBLE
            }
        }
        binding.btnGame32.setOnClickListener {
            if (!started) {
                Toast.makeText(this, "시작버튼을 눌러주세요", Toast.LENGTH_SHORT).show()
            } else if (started && binding.btnGame32.text.toString() != nowNumber.toString()) {
                hp--
                if (score >= 100) {
                    score -= 10000
                }
                Toast.makeText(this, "HP : $hp", Toast.LENGTH_SHORT).show()
            } else {
                nowNumber++
                binding.tvGameNownumber.text = nowNumber.toString()
                count += 10
                btnCheck = true
                binding.btnGame32.visibility = View.INVISIBLE
            }
        }
        binding.btnGame33.setOnClickListener {
            if (!started) {
                Toast.makeText(this, "시작버튼을 눌러주세요", Toast.LENGTH_SHORT).show()
            } else if (started && binding.btnGame33.text.toString() != nowNumber.toString()) {
                hp--
                if (score >= 100) {
                    score -= 10000
                }
                Toast.makeText(this, "HP : $hp", Toast.LENGTH_SHORT).show()
            } else {
                nowNumber++
                binding.tvGameNownumber.text = nowNumber.toString()
                count += 10
                btnCheck = true
                binding.btnGame33.visibility = View.INVISIBLE
            }
        }
        binding.btnGame34.setOnClickListener {
            if (!started) {
                Toast.makeText(this, "시작버튼을 눌러주세요", Toast.LENGTH_SHORT).show()
            } else if (started && binding.btnGame34.text.toString() != nowNumber.toString()) {
                hp--
                if (score >= 100) {
                    score -= 10000
                }
                Toast.makeText(this, "HP : $hp", Toast.LENGTH_SHORT).show()
            } else {
                nowNumber++
                binding.tvGameNownumber.text = nowNumber.toString()
                count += 10
                btnCheck = true
                binding.btnGame34.visibility = View.INVISIBLE
            }
        }
        binding.btnGame35.setOnClickListener {
            if (!started) {
                Toast.makeText(this, "시작버튼을 눌러주세요", Toast.LENGTH_SHORT).show()
            } else if (started && binding.btnGame35.text.toString() != nowNumber.toString()) {
                hp--
                if (score >= 100) {
                    score -= 10000
                }
                Toast.makeText(this, "HP : $hp", Toast.LENGTH_SHORT).show()
            } else {
                nowNumber++
                binding.tvGameNownumber.text = nowNumber.toString()
                count += 10
                btnCheck = true
                binding.btnGame35.visibility = View.INVISIBLE
            }
        }
        binding.btnGame36.setOnClickListener {
            if (!started) {
                Toast.makeText(this, "시작버튼을 눌러주세요", Toast.LENGTH_SHORT).show()
            } else if (started && binding.btnGame36.text.toString() != nowNumber.toString()) {
                hp--
                if (score >= 100) {
                    score -= 10000
                }
                Toast.makeText(this, "HP : $hp", Toast.LENGTH_SHORT).show()
            } else {
                nowNumber++
                binding.tvGameNownumber.text = nowNumber.toString()
                count += 10
                btnCheck = true
                binding.btnGame36.visibility = View.INVISIBLE
            }
        }
        binding.btnGame37.setOnClickListener {
            if (!started) {
                Toast.makeText(this, "시작버튼을 눌러주세요", Toast.LENGTH_SHORT).show()
            } else if (started && binding.btnGame37.text.toString() != nowNumber.toString()) {
                hp--
                if (score >= 100) {
                    score -= 10000
                }
                Toast.makeText(this, "HP : $hp", Toast.LENGTH_SHORT).show()
            } else {
                nowNumber++
                binding.tvGameNownumber.text = nowNumber.toString()
                count += 10
                btnCheck = true
                binding.btnGame37.visibility = View.INVISIBLE
            }
        }
        binding.btnGame38.setOnClickListener {
            if (!started) {
                Toast.makeText(this, "시작버튼을 눌러주세요", Toast.LENGTH_SHORT).show()
            } else if (started && binding.btnGame38.text.toString() != nowNumber.toString()) {
                hp--
                if (score >= 100) {
                    score -= 10000
                }
                Toast.makeText(this, "HP : $hp", Toast.LENGTH_SHORT).show()
            } else {
                nowNumber++
                binding.tvGameNownumber.text = nowNumber.toString()
                count += 10
                btnCheck = true
                binding.btnGame38.visibility = View.INVISIBLE
            }
        }
        binding.btnGame39.setOnClickListener {
            if (!started) {
                Toast.makeText(this, "시작버튼을 눌러주세요", Toast.LENGTH_SHORT).show()
            } else if (started && binding.btnGame39.text.toString() != nowNumber.toString()) {
                hp--
                if (score >= 100) {
                    score -= 10000
                }
                Toast.makeText(this, "HP : $hp", Toast.LENGTH_SHORT).show()
            } else {
                nowNumber++
                binding.tvGameNownumber.text = nowNumber.toString()
                count += 10
                btnCheck = true
                binding.btnGame39.visibility = View.INVISIBLE
            }
        }
        binding.btnGame40.setOnClickListener {
            if (!started) {
                Toast.makeText(this, "시작버튼을 눌러주세요", Toast.LENGTH_SHORT).show()
            } else if (started && binding.btnGame40.text.toString() != nowNumber.toString()) {
                hp--
                if (score >= 100) {
                    score -= 10000
                }
                Toast.makeText(this, "HP : $hp", Toast.LENGTH_SHORT).show()
            } else {
                nowNumber++
                binding.tvGameNownumber.text = nowNumber.toString()
                count += 10
                btnCheck = true
                binding.btnGame40.visibility = View.INVISIBLE
            }
        }
        binding.btnGame41.setOnClickListener {
            if (!started) {
                Toast.makeText(this, "시작버튼을 눌러주세요", Toast.LENGTH_SHORT).show()
            } else if (started && binding.btnGame41.text.toString() != nowNumber.toString()) {
                hp--
                if (score >= 100) {
                    score -= 10000
                }
                Toast.makeText(this, "HP : $hp", Toast.LENGTH_SHORT).show()
            } else {
                nowNumber++
                binding.tvGameNownumber.text = nowNumber.toString()
                count += 10
                btnCheck = true
                binding.btnGame41.visibility = View.INVISIBLE
            }
        }
        binding.btnGame42.setOnClickListener {
            if (!started) {
                Toast.makeText(this, "시작버튼을 눌러주세요", Toast.LENGTH_SHORT).show()
            } else if (started && binding.btnGame42.text.toString() != nowNumber.toString()) {
                hp--
                if (score >= 100) {
                    score -= 10000
                }
                Toast.makeText(this, "HP : $hp", Toast.LENGTH_SHORT).show()
            } else {
                nowNumber++
                binding.tvGameNownumber.text = nowNumber.toString()
                count += 10
                btnCheck = true
                binding.btnGame42.visibility = View.INVISIBLE
            }
        }
        binding.btnGame43.setOnClickListener {
            if (!started) {
                Toast.makeText(this, "시작버튼을 눌러주세요", Toast.LENGTH_SHORT).show()
            } else if (started && binding.btnGame43.text.toString() != nowNumber.toString()) {
                hp--
                if (score >= 100) {
                    score -= 10000
                }
                Toast.makeText(this, "HP : $hp", Toast.LENGTH_SHORT).show()
            } else {
                nowNumber++
                binding.tvGameNownumber.text = nowNumber.toString()
                count += 10
                btnCheck = true
                binding.btnGame43.visibility = View.INVISIBLE
            }
        }
        binding.btnGame44.setOnClickListener {
            if (!started) {
                Toast.makeText(this, "시작버튼을 눌러주세요", Toast.LENGTH_SHORT).show()
            } else if (started && binding.btnGame44.text.toString() != nowNumber.toString()) {
                hp--
                if (score >= 100) {
                    score -= 10000
                }
                Toast.makeText(this, "HP : $hp", Toast.LENGTH_SHORT).show()
            } else {
                nowNumber++
                binding.tvGameNownumber.text = nowNumber.toString()
                count += 10
                btnCheck = true
                binding.btnGame44.visibility = View.INVISIBLE
            }
        }
        binding.btnGame45.setOnClickListener {
            if (!started) {
                Toast.makeText(this, "시작버튼을 눌러주세요", Toast.LENGTH_SHORT).show()
            } else if (started && binding.btnGame45.text.toString() != nowNumber.toString()) {
                hp--
                if (score >= 100) {
                    score -= 10000
                }
                Toast.makeText(this, "HP : $hp", Toast.LENGTH_SHORT).show()
            } else {
                nowNumber++
                binding.tvGameNownumber.text = nowNumber.toString()
                count += 10
                btnCheck = true
                binding.btnGame45.visibility = View.INVISIBLE
            }
        }
        binding.btnGame46.setOnClickListener {
            if (!started) {
                Toast.makeText(this, "시작버튼을 눌러주세요", Toast.LENGTH_SHORT).show()
            } else if (started && binding.btnGame46.text.toString() != nowNumber.toString()) {
                hp--
                if (score >= 100) {
                    score -= 10000
                }
                Toast.makeText(this, "HP : $hp", Toast.LENGTH_SHORT).show()
            } else {
                nowNumber++
                binding.tvGameNownumber.text = nowNumber.toString()
                count += 10
                btnCheck = true
                binding.btnGame46.visibility = View.INVISIBLE
            }
        }
        binding.btnGame47.setOnClickListener {
            if (!started) {
                Toast.makeText(this, "시작버튼을 눌러주세요", Toast.LENGTH_SHORT).show()
            } else if (started && binding.btnGame47.text.toString() != nowNumber.toString()) {
                hp--
                if (score >= 100) {
                    score -= 10000
                }
                Toast.makeText(this, "HP : $hp", Toast.LENGTH_SHORT).show()
            } else {
                nowNumber++
                binding.tvGameNownumber.text = nowNumber.toString()
                count += 10
                btnCheck = true
                binding.btnGame47.visibility = View.INVISIBLE
            }
        }
        binding.btnGame48.setOnClickListener {
            if (!started) {
                Toast.makeText(this, "시작버튼을 눌러주세요", Toast.LENGTH_SHORT).show()
            } else if (started && binding.btnGame48.text.toString() != nowNumber.toString()) {
                hp--
                if (score >= 100) {
                    score -= 10000
                }
                Toast.makeText(this, "HP : $hp", Toast.LENGTH_SHORT).show()
            } else {
                nowNumber++
                binding.tvGameNownumber.text = nowNumber.toString()
                count += 10
                btnCheck = true
                binding.btnGame48.visibility = View.INVISIBLE
            }
        }
        binding.btnGame49.setOnClickListener {
            if (!started) {
                Toast.makeText(this, "시작버튼을 눌러주세요", Toast.LENGTH_SHORT).show()
            } else if (started && binding.btnGame49.text.toString() != nowNumber.toString()) {
                hp--
                if (score >= 100) {
                    score -= 10000
                }
                Toast.makeText(this, "HP : $hp", Toast.LENGTH_SHORT).show()
            } else {
                nowNumber++
                binding.tvGameNownumber.text = nowNumber.toString()
                count += 10
                btnCheck = true
                binding.btnGame49.visibility = View.INVISIBLE
            }
        }
        binding.btnGame50.setOnClickListener {
            if (!started) {
                Toast.makeText(this, "시작버튼을 눌러주세요", Toast.LENGTH_SHORT).show()
            } else if (started && binding.btnGame50.text.toString() != nowNumber.toString()) {
                hp--
                if (score >= 100) {
                    score -= 10000
                }
                Toast.makeText(this, "HP : $hp", Toast.LENGTH_SHORT).show()
            } else {
                nowNumber++
                binding.tvGameNownumber.text = nowNumber.toString()
                count += 10
                btnCheck = true
                binding.btnGame50.visibility = View.INVISIBLE
            }
        }

        // 게임 종료 스레드
        Thread() {
            while (gameOver) {
                if (hp == 0) {
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

        // 게임 클리어시
        Thread() {
            while (gameClear) {
                if (nowNumber == 51) {
                    var intent = Intent(this, ClearActivity::class.java)
                    intent.putExtra("time", timer.toString())
                    intent.putExtra("score", score.toString())
                    bgmPlayer.stop()
                    gameClear = false // false로 바꾸면 스레드 종료함
                    startActivity(intent)
                    finish()
                }
            }
        }.start()
    }

    // 점수 계산
    private fun scoreCount() {
        Thread() {
            val countHandler = Handler(Looper.getMainLooper())
            while (true) {
                if (btnCheck) {
                    score += count * nowNumber
                    Log.d("점수확인", "$score $count $nowNumber")
                    btnCheck = false
                    countHandler.post {
                        binding.tvGameScore.text = score.toString()
                    }
                }
            }
        }.start()
    }

    override fun onStop() {
        super.onStop()
        started = false
        threadStop = false
        bgmPlayer.pause()
    }

    override fun onDestroy() {
        super.onDestroy()
        threadStop = false
    }

    private fun randomNumber25() {

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

    private fun randomNumber50() {

        for (i in 26..50) {
            number2.add(i)
        }
        number2.shuffle()
        Log.d("25번부터", "$number2")

        binding.btnGame26.text = number2[0].toString()
        binding.btnGame27.text = number2[1].toString()
        binding.btnGame28.text = number2[2].toString()
        binding.btnGame29.text = number2[3].toString()
        binding.btnGame30.text = number2[4].toString()
        binding.btnGame31.text = number2[5].toString()
        binding.btnGame32.text = number2[6].toString()
        binding.btnGame33.text = number2[7].toString()
        binding.btnGame34.text = number2[8].toString()
        binding.btnGame35.text = number2[9].toString()
        binding.btnGame36.text = number2[10].toString()
        binding.btnGame37.text = number2[11].toString()
        binding.btnGame38.text = number2[12].toString()
        binding.btnGame39.text = number2[13].toString()
        binding.btnGame40.text = number2[14].toString()
        binding.btnGame41.text = number2[15].toString()
        binding.btnGame42.text = number2[16].toString()
        binding.btnGame43.text = number2[17].toString()
        binding.btnGame44.text = number2[18].toString()
        binding.btnGame45.text = number2[19].toString()
        binding.btnGame46.text = number2[20].toString()
        binding.btnGame47.text = number2[21].toString()
        binding.btnGame48.text = number2[22].toString()
        binding.btnGame49.text = number2[23].toString()
        binding.btnGame50.text = number2[24].toString()
    }
}