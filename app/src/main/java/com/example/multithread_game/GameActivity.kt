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
import com.example.multithread_game.GlobalApplication.Companion.pref
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
    var level = 0 // 난이도

    val buttonList = ArrayList<AppCompatButton>()

    var number = mutableListOf<Int>()
    var number2 = mutableListOf<Int>()
    var number3 = mutableListOf<Int>()
    var number4 = mutableListOf<Int>()

    var nowNumber = 1 // 현재 눌러야하는 번호
    var endNumber = 0

    var count = 10 // 점수 증가량
    var btnCheck = false // 점수 체크 용도
    var gameClear = true // 게임 클리어
    var gameOver = true // 게임 종료

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

        scoreCount() // 점수 계산 방식

        setNumberList() // 버튼 추가

        setLevel() // 난이도 설정

        setRandomNumber() // 버튼 랜덤 배치

        setFirstButton() // 버튼 설정

        setButtonClick() // 버튼 클릭시 동작 확인

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

            // 클릭이 늦어 질수록 낮은 점수를 곱하게 된다.
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
                if (nowNumber == endNumber) {
                    var intent = Intent(this, ClearActivity::class.java)
                    intent.putExtra("time", timer.toString())
                    intent.putExtra("score", score.toString())
                    intent.putExtra("level", level)
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

    // 난이도 설정
    private fun setLevel() {
        val levelInt = intent.getIntExtra("level", 0)
        level = levelInt

        // 끝나는 숫자 설정
        when (level) {
            1 -> {
                endNumber = 26
            }
            2 -> {
                endNumber = 51
            }
            3 -> {
                endNumber = 101
            }
        }

    }

    private fun setRandomNumber() {
        val random = Random()
        var num = 0
        var randomInt = ArrayList<Int>()
        var i = 0

        when (level) {
            3 -> { // 난이도 hard 일때, 랜덤 숫자 25개씩 4개 배열에 저장
                while (i < 25) {
                    num = random.nextInt(25) // 랜덤 숫자 저장

                    if (!randomInt.contains(num)) {  // 그 숫자 중복일 경우 다시 뽑기
                        randomInt.add(num)
                        number4.add(num + 76)
                        i++
                    }
                }
                i = 0
                randomInt.clear() // 뽑은 숫자 배열 정리

                while (i < 25) {
                    num = random.nextInt(25)

                    if (!randomInt.contains(num)) {
                        randomInt.add(num)
                        number3.add(num + 51)
                        i++
                    }
                }
                i = 0
                randomInt.clear()

                while (i < 25) {
                    num = random.nextInt(25)

                    if (!randomInt.contains(num)) {
                        randomInt.add(num)
                        number2.add(num + 26)
                        i++
                    }
                }
                i = 0
                randomInt.clear()

                while (i < 25) {
                    num = random.nextInt(25)

                    if (!randomInt.contains(num)) {
                        randomInt.add(num)
                        number.add(num + 1)
                        i++
                    }
                }
                i = 0
                randomInt.clear()

            }
            2 -> { // 난이도 normal 일때, 랜덤 숫자 25개씩 2개 배열에 저장
                while (i < 25) {
                    num = random.nextInt(25)

                    if (!randomInt.contains(num)) {
                        randomInt.add(num)
                        number2.add(num + 26)
                        i++
                    }
                }
                i = 0
                randomInt.clear()

                while (i < 25) {
                    num = random.nextInt(25)

                    if (!randomInt.contains(num)) {
                        randomInt.add(num)
                        number.add(num + 1)
                        i++
                    }
                }
                i = 0
                randomInt.clear()

            }
            1 -> { // 난이도 easy 일때 랜덤 숫자 25개 배열에 저장
                while (i < 25) {
                    num = random.nextInt(25)

                    if (!randomInt.contains(num)) {
                        randomInt.add(num)
                        number.add(num + 1)
                        i++
                    }
                }
                i = 0
                randomInt.clear()

            }
            else -> level = 2
        }
    }

    private fun setButtonClick() {
        for (i in 0..24) {
            buttonList[i].setOnClickListener {
                if (level == 1) {
                    if (!started) {
                        Toast.makeText(this, "시작버튼을 눌러주세요", Toast.LENGTH_SHORT).show()
                    } else if (nowNumber == number[i]) {
                        nowNumber++
                        binding.tvGameNownumber.text = nowNumber.toString()
                        count += 3
                        btnCheck = true
                        buttonList[i].visibility = View.INVISIBLE
                    } else {
                        hp--
                        if (score >= 1000) {
                            score -= 1000
                        }
                        Toast.makeText(this, "HP : $hp", Toast.LENGTH_SHORT).show()
                    }
                } else if (level == 2) {
                    if (!started) {
                        Toast.makeText(this, "시작버튼을 눌러주세요", Toast.LENGTH_SHORT).show()
                    } else if (nowNumber == number[i]) {
                        nowNumber++
                        buttonList[i].text = number2[i].toString() // 숫자가 지워 지면 다음 배열에서 숫자 가져와 대입
                        binding.tvGameNownumber.text = nowNumber.toString()
                        count += 3
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
                } else if (level == 3) {
                    if (!started) {
                        Toast.makeText(this, "시작버튼을 눌러주세요", Toast.LENGTH_SHORT).show()
                    } else if (nowNumber == number[i]) {
                        nowNumber++
                        buttonList[i].text = number2[i].toString()
                        binding.tvGameNownumber.text = nowNumber.toString()
                        count += 3
                        btnCheck = true
                    } else if (nowNumber == number2[i]) {
                        nowNumber++
                        buttonList[i].text = number3[i].toString()
                        binding.tvGameNownumber.text = nowNumber.toString()
                        count += 5
                        btnCheck = true
                    } else if (nowNumber == number3[i]) {
                        nowNumber++
                        buttonList[i].text = number4[i].toString()
                        binding.tvGameNownumber.text = nowNumber.toString()
                        count += 5
                        btnCheck = true
                    } else if (nowNumber == number4[i]) {
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