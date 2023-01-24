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
import androidx.appcompat.widget.AppCompatButton
import com.example.multithread_game.databinding.ActivityGameBinding
import kotlin.concurrent.thread
import java.util.*

class GameActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGameBinding

    private lateinit var bgmPlayer: MediaPlayer

    var timer = 0 // 시간 저장
    var started = false // 시작 체크
    var hp = 3 // 목숨
    var score = 0 // 점수

    val buttonList = ArrayList<AppCompatButton>()

    var number = mutableListOf<Int>()
    var number2 = mutableListOf<Int>()

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

        setNumberList()

        setRandomNumber()

        setFirstButton()

        scoreCount() // 점수 계산 방식

        setButtonClick()

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

        // 게임 클리어 스레료
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

    private fun setNumberList() {

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

    private fun setFirstButton() {

        for (i in 0..24) {
            buttonList[i].text = number[i].toString()
        }
    }

    private fun setRandomNumber() {
        val random = Random()
        var randInt = 0
        var redundantInt = ArrayList<Int>()  // 중복 체크 용도
        var i = 0
        // 자바의 case 문 ==> 코틀린의 when 문
        // 랜덤으로 뽑아올 때 0부터 뽑아오므로 원하는 숫자를 가져오기 위해 1, 26, 51, 76 더해서 저장
        // 보통 난이도일 때 랜덤 숫자 25개씩 2개 배열에 저장
        while (i < 25) {
            randInt = random.nextInt(25)

            if (!redundantInt.contains(randInt)) { // 랜덤으로 숫자 뽑아올 때 중복 방지
                redundantInt.add(randInt)
                number2.add(randInt + 26)
                i++
            }
        }
        i = 0
        redundantInt.clear()

        while (i < 25) {
            randInt = random.nextInt(25)

            if (!redundantInt.contains(randInt)) { // 랜덤으로 숫자 뽑아올 때 중복 방지
                redundantInt.add(randInt)
                number.add(randInt + 1)
                i++
            }
        }
        i = 0
        redundantInt.clear()
    }

    private fun setButtonClick() {
        for (i in 0..24) {
            buttonList[i].setOnClickListener {
                if (!started) {
                    Toast.makeText(this, "시작버튼을 눌러주세요", Toast.LENGTH_SHORT).show()
                } else if (nowNumber == number[i]) {
                    nowNumber++
                    buttonList[i].text = number2[i].toString()
                    binding.tvGameNownumber.text = nowNumber.toString()
                    count += 5
                    btnCheck = true
                } else if (nowNumber == number2[i]) {
                    nowNumber++
                    binding.tvGameNownumber.text = nowNumber.toString()
                    count += 5
                    btnCheck = true
                    buttonList[i].visibility = View.INVISIBLE
                } else {
                    hp--
                    if (score >= 1000) {
                        score -= 1000
                    }
                    Toast.makeText(this, "HP : $hp", Toast.LENGTH_SHORT).show()
                }
            }
        }

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
}