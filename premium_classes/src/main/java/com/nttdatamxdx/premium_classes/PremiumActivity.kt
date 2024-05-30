package com.nttdatamxdx.premium_classes

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.WindowManager
import android.widget.MediaController
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.google.android.play.core.splitcompat.SplitCompat

class PremiumActivity : AppCompatActivity() {


    private lateinit var videoPlayer : VideoView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContentView(R.layout.activity_premium)
        initComponents()
        setStatusBarColor()
        showBottomDialog()
        setVideo()
    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(newBase)
        SplitCompat.install(this)
    }

    private fun setStatusBarColor(){
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, com.nttdatamxdx.gymdfm.R.color.black_blue)
    }

    private fun initComponents(){
        videoPlayer = findViewById(R.id.video_player_view)
    }

    private fun showBottomDialog(){
        val dialog = PremiumSheetFragment(::onTapStart)
        dialog.show(supportFragmentManager, "")
    }

    private fun onTapStart(){
        videoPlayer.start()
    }

    private fun setVideo(){
        val videoPath = "android.resource://$packageName/${R.raw.workout_video}"
        videoPlayer.setVideoURI(Uri.parse(videoPath))
        val mediaController = MediaController(this)
        mediaController.setAnchorView(videoPlayer)
        videoPlayer.setMediaController(mediaController)
    }
}