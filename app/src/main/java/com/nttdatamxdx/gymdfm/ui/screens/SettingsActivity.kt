package com.nttdatamxdx.gymdfm.ui.screens

import android.content.Context
import android.os.Bundle
import android.view.WindowManager
import android.widget.ImageView
import android.widget.Switch
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.nttdatamxdx.gymdfm.R
import com.nttdatamxdx.gymdfm.core.Constants

class SettingsActivity : AppCompatActivity() {

    private lateinit var back : ImageView
    private lateinit var switch : Switch

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContentView(R.layout.activity_settings)
        initUI()
        setStatusBarColor()
        setSwitchStatus()
        initListeners()
    }

    private fun initUI(){
        back = findViewById(R.id.back_settings_icon)
        switch = findViewById(R.id.switch_foreground_background)
    }

    private fun setSwitchStatus(){
        switch.isChecked = getForegroundBackground()
    }

    private fun initListeners(){
        back.setOnClickListener { finish() }

        switch.setOnCheckedChangeListener { _, isChecked -> setUpForegroundBackground(isChecked) }
    }

    private fun setUpForegroundBackground(isForeground: Boolean){
        val sp = getSharedPreferences(Constants.SWITCH_FOREGROUND_BACKGROUND_SP, Context.MODE_PRIVATE)
        val editor = sp.edit()
        editor.putBoolean(Constants.SWITCH_FOREGROUND_BACKGROUND_FLAG, isForeground)
        editor.apply()
    }

    private fun getForegroundBackground() : Boolean {
        val sp = getSharedPreferences(Constants.SWITCH_FOREGROUND_BACKGROUND_SP, Context.MODE_PRIVATE)
        return sp.getBoolean(Constants.SWITCH_FOREGROUND_BACKGROUND_FLAG, false)
    }

    private fun setStatusBarColor(){
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, R.color.black_blue)
    }

}