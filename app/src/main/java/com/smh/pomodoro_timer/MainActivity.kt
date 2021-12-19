package com.smh.pomodoro_timer

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.SeekBar
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    private val remainMinutesTextView: TextView by lazy {
        findViewById<TextView>(R.id.remainMinutesTextView)
    }

    private val remainSecondsTextView: TextView by lazy {
        findViewById<TextView>(R.id.remainSecondsTextView)
    }

    private val seekBar: SeekBar by lazy {
        findViewById<SeekBar>(R.id.seekBar)
    }

    private var currentCountDownTimer: CountDownTimer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bindViews()
    }

    private fun bindViews() {
        seekBar.setOnSeekBarChangeListener(
            object: SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                    if (p2) {
                        updateRemainTime(p1 * 60 * 1000L)
                    }
                }

                override fun onStartTrackingTouch(p0: SeekBar?) {
                    currentCountDownTimer?.cancel()
                    currentCountDownTimer = null
                }

                override fun onStopTrackingTouch(p0: SeekBar?) {
                    seekBar ?: return
                    currentCountDownTimer = createCountDownTimer(seekBar.progress * 60 * 1000L)
                    currentCountDownTimer?.start()
                }
            }

        )
    }

    private fun createCountDownTimer(initialMillis: Long): CountDownTimer {
        return object: CountDownTimer(initialMillis, 1000L) {
            override fun onTick(p0: Long) { // 1초마다 한번씩 불림.
                updateRemainTime(p0)
                updateSeekBar(p0)
            }

            override fun onFinish() {
                updateRemainTime(0)
                updateSeekBar(0)
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun updateRemainTime(remainMillis: Long) {
        val remainSeconds = remainMillis / 1000

        remainMinutesTextView.text = "%02d".format(remainSeconds / 60)
        remainSecondsTextView.text = "%02d".format(remainSeconds % 60)
    }

    private fun updateSeekBar(remainMillis: Long) {
        seekBar.progress = (remainMillis / 1000 / 60).toInt()
    }
}