package com.example.threadexample

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.threadexample.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var runnable: Runnable
    private val mainHandler = Handler()

    @Volatile
    private var stopThread = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
    }

    fun startThread(view: View) {
        stopThread = false
        exampleRunnable(10)
        //val thread = ExampleThread(10)
        //thread.start()
    }

    fun stopThread(view: View) {
        stopThread = true
    }

    private fun exampleRunnable(seconds: Int) {
        runnable = Runnable {
            for (i in 0..seconds) {
                if (stopThread) {
                    return@Runnable
                }
                if (i == 5) {
                    /*//mainHandler.post { binding.buttonStartThread.text = "50%" }
                    val threadHandler = Handler(Looper.getMainLooper())
                    threadHandler.post { binding.buttonStartThread.text = "50%" }*/

                    //binding.buttonStartThread.post { binding.buttonStartThread.text = "50%" }

                    runOnUiThread { binding.buttonStartThread.text = "50%" }

                }
                Timber.d("startThread: $i")
                try {
                    Thread.sleep(1000)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
        }
        Thread(runnable).start()
    }

    class ExampleThread(private val seconds: Int) : Thread() {
        override fun run() {
            for (i in 0..seconds) {
                Timber.d("startThread: $i")
                try {
                    sleep(1000)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
        }
    }
}
