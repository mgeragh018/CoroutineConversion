package edu.temple.coroutineconversion

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Locale

class MainActivity : AppCompatActivity() {

    //TODO (Refactor to replace Thread code with coroutines)

    private val cakeImageView: ImageView by lazy {
        findViewById(R.id.imageView)
    }

    private val currentTextView: TextView by lazy {
        findViewById(R.id.currentTextView)
    }

    //val handler = Handler(Looper.getMainLooper(), Handler.Callback {

    //    currentTextView.text = String.format(Locale.getDefault(), "Current opacity: %d", it.what)
    //    cakeImageView.alpha = it.what / 100f
    //    true
    //})

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //findViewById<Button>(R.id.revealButton).setOnClickListener{
        //    Thread{
        //        repeat(100) {
        //            handler.sendEmptyMessage(it+1)
        //            Thread.sleep(40)
        //        }
        //    }.start()
        //}
        findViewById<Button>(R.id.revealButton).setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                revealImage()
            }
        }

    }
    suspend fun revealImage() = withContext(Dispatchers.IO) {
        repeat(100) { opacity ->
            delay(40)
            withContext(Dispatchers.Main) {
                currentTextView.text = String.format(Locale.getDefault(), "Current opacity: %d", opacity + 1)
                cakeImageView.alpha = (opacity + 1) / 100f
            }
        }
    }
}