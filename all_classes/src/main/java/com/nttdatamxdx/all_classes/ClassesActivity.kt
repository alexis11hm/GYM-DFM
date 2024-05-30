package com.nttdatamxdx.all_classes

import android.os.Bundle
import android.view.WindowManager
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen

class ClassesActivity : AppCompatActivity() {

    private lateinit var backButton : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContentView(R.layout.activity_classes)
        initUI()
        initComponents()
        initListeners()
    }

    private fun initUI(){
        setStatusBarColor()
    }

    private fun initComponents(){
        backButton = findViewById(R.id.back_arrow_icon)
    }

    private fun initListeners(){
        backButton.setOnClickListener {
            backToPreviousPage()
        }
    }

    private fun setStatusBarColor(){
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, com.nttdatamxdx.gymdfm.R.color.black_blue)
    }

    private fun backToPreviousPage(){
        finish()
    }
}