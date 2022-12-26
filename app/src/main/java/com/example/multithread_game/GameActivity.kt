package com.example.multithread_game

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.example.multithread_game.databinding.ActivityGameBinding
import kotlin.concurrent.thread
import kotlin.random.Random

class GameActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGameBinding

    private lateinit var bgmPlayer: MediaPlayer

    var timer = 0 // 시간 저장
    var started = false // 시작 체크
    var hp = 3 // 목숨
    var score = 0 // 점수
    var number1to25 = ArrayList<Int>() // 1부터 25숫자 넣는곳
    var number26to50 = mutableListOf<Int>() // 26부터 50숫자 넣는곳
    var number51to75 = mutableListOf<Int>() // 51부터 75숫자 넣는곳
    var number76to100 = mutableListOf<Int>() // 76부터 100숫자 넣는곳

    var nowNumber = 0 // 현재 눌러야하는 번호
    var endNumber = 0
    var buttonList = ArrayList<AppCompatButton>()
    var count = 10 // 점수
    var btnCheck = false // 점수 체크용도

    var nowLevel = 0 // 난이도

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

        setBtn() // 버튼 배열 초기화

        setLevel() // 난이도

        setMain() // 숫자 랜덤배치
        scoreCount() // 점수 게산방식
        clickButton()
        setRandomNum()

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
        bgmPlayer.stop()
    }

    private fun setLevel() {
        var level = intent.extras?.get("level").toString().toInt()
        nowLevel = level
        Log.d("level", "$level")

        if (nowLevel == 1) {
            endNumber = 26
        } else if (nowLevel == 2) {
            endNumber = 51
        } else if (nowLevel == 3) {
            endNumber = 101
        }
    }

    private fun setBtn() {
        buttonList.add(binding.btnGame1)
        buttonList.add(binding.btnGame2)
        buttonList.add(binding.btnGame3)
        buttonList.add(binding.btnGame4)
        buttonList.add(binding.btnGame5)
        buttonList.add(binding.btnGame6)
        buttonList.add(binding.btnGame7)
        buttonList.add(binding.btnGame8)
        buttonList.add(binding.btnGame9)
        buttonList.add(binding.btnGame10)
        buttonList.add(binding.btnGame11)
        buttonList.add(binding.btnGame12)
        buttonList.add(binding.btnGame13)
        buttonList.add(binding.btnGame14)
        buttonList.add(binding.btnGame15)
        buttonList.add(binding.btnGame16)
        buttonList.add(binding.btnGame17)
        buttonList.add(binding.btnGame18)
        buttonList.add(binding.btnGame19)
        buttonList.add(binding.btnGame20)
        buttonList.add(binding.btnGame21)
        buttonList.add(binding.btnGame22)
        buttonList.add(binding.btnGame23)
        buttonList.add(binding.btnGame24)
        buttonList.add(binding.btnGame25)
    }

    private fun setMain() {

        nowNumber = 1

        for (i in 0..24) {
            buttonList[i].text = number1to25[i].toString()
        }
    }

    private fun setRandomNum() {
        val random = Random
        var ranNum = 0
        var randomList = ArrayList<Int>()
        var n = 0

        when (nowLevel) {
            3 -> {
                while (n < 25) {
                    ranNum = random.nextInt(25)

                    if (!randomList.contains(ranNum)) {
                        randomList.add(ranNum)
                        number76to100.add(ranNum + 76)
                    }
                }
                n = 0
                randomList.clear()

                while (n < 25) {
                    ranNum = random.nextInt(25)

                    if (!randomList.contains(ranNum)) {
                        randomList.add(ranNum)
                        number51to75.add(ranNum + 51)
                    }
                }
                n = 0
                randomList.clear()

                while (n < 25) {
                    ranNum = random.nextInt(25)

                    if (!randomList.contains(ranNum)) {
                        randomList.add(ranNum)
                        number26to50.add(ranNum + 26)
                    }
                }
                n = 0
                randomList.clear()

                while (n < 25) {
                    ranNum = random.nextInt(25)

                    if (!randomList.contains(ranNum)) {
                        randomList.add(ranNum)
                        number1to25.add(ranNum + 1)
                    }
                }
                n = 0
                randomList.clear()
            }
            1 -> {
                while (n < 25) {
                    ranNum = random.nextInt(25)

                    if (!randomList.contains(ranNum)) {
                        randomList.add(ranNum)
                        number1to25.add(ranNum + 1)
                        n++
                    }
                }
                n = 0
                randomList.clear()
            }
             else -> {
                 nowLevel = 1
                 setRandomNum()
             }
        }
    }

    private fun clickButton() {
        for (i in 0..24) {
            buttonList[i].setOnClickListener {
                if (nowLevel == 1) {
                    if (!started) {
                        Toast.makeText(this, "시작버튼을 눌러주세요", Toast.LENGTH_SHORT).show()
                    } else if (started && number1to25[i].toString() != nowNumber.toString()) {
                        hp--
                        if (score >= 100) {
                            score -= 100
                        }
                        Toast.makeText(this, "HP : $hp", Toast.LENGTH_SHORT).show()
                    } else {
                        nowNumber++
                        binding.tvGameNownumber.text = nowNumber.toString()
                        count += 2
                        buttonList[i].visibility = View.INVISIBLE
                    }
                }
            }
        }
    }
}