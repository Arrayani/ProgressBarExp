package com.learntocode.progressbarexp

import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    private var progressStatus = 0
    private val handler = Handler()
    var pb: ProgressBar? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Get the widgets reference from XML layout
        val rl = findViewById<View>(R.id.rl) as RelativeLayout
        val btn = findViewById<View>(R.id.btn) as Button
        val btn_start = findViewById<View>(R.id.btn_start) as Button
        val tv = findViewById<View>(R.id.tv) as TextView
        btn.setOnClickListener { //ProgressBar(Context context, AttributeSet attrs, int defStyleAttr)
            // Initialize a new Progressbar instance
            // Create a new progress bar programmatically
            pb = ProgressBar(applicationContext, null, android.R.attr.progressBarStyleHorizontal)

            // Create new layout parameters for progress bar
            val lp = RelativeLayout.LayoutParams(
                550,  // Width in pixels
                RelativeLayout.LayoutParams.WRAP_CONTENT // Height of progress bar
            )

            // Apply the layout parameters for progress bar
            pb!!.layoutParams = lp

            // Get the progress bar layout parameters
            val params = pb!!.layoutParams as RelativeLayout.LayoutParams

            // Set a layout position rule for progress bar
            params.addRule(RelativeLayout.BELOW, tv.id)

            // Apply the layout rule for progress bar
            pb!!.layoutParams = params

            // Set the progress bar color
            pb!!.progressDrawable.setColorFilter(
                Color.BLUE,
                PorterDuff.Mode.SRC_IN
            )

            // Finally,  add the progress bar to layout
            rl.addView(pb)
        }
        btn_start.setOnClickListener {
            // Set the progress status zero on each button click
            progressStatus = 0
            // Start the lengthy operation in a background thread
            Thread {
                while (progressStatus < 100) {
                    // Update the progress status
                    progressStatus += 1

                    // Try to sleep the thread for 20 milliseconds
                    try {
                        Thread.sleep(20)
                    } catch (e: InterruptedException) {
                        e.printStackTrace()
                    }

                    // Update the progress bar
                    handler.post {
                        pb!!.progress = progressStatus
                        // Show the progress on TextView
                        tv.text = progressStatus.toString() + ""
                    }
                }
            }.start() // Start the operation
        }
    }
}