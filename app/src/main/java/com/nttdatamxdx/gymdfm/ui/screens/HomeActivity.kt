package com.nttdatamxdx.gymdfm.ui.screens

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.google.android.material.snackbar.Snackbar
import com.google.android.play.core.splitinstall.SplitInstallManager
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import com.google.android.play.core.splitinstall.SplitInstallRequest
import com.google.android.play.core.splitinstall.SplitInstallStateUpdatedListener
import com.google.android.play.core.splitinstall.model.SplitInstallSessionStatus
import com.nttdatamxdx.gymdfm.R
import com.nttdatamxdx.gymdfm.core.Constants
import com.nttdatamxdx.gymdfm.ui.dialogs.ProgressDialog


class HomeActivity : AppCompatActivity() {

    private lateinit var seeAll : TextView
    private lateinit var tryItButton : CardView
    private lateinit var seeInstalledModules : CardView
    private lateinit var uninstallPremiumModule : CardView
    private lateinit var settingsButton : ImageView
    private lateinit var dialog : ProgressDialog

    private lateinit var parentLayout : View
    private lateinit var splitInstallManager : SplitInstallManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContentView(R.layout.activity_home)
        initUI()
        initComponents()
        initListeners()
    }

    private fun initUI(){
        setStatusBarColor()
    }

    private fun initComponents(){
        seeAll = findViewById(R.id.see_all_textview)
        tryItButton = findViewById(R.id.try_it_card_view)
        settingsButton = findViewById(R.id.settings_button)
        seeInstalledModules = findViewById(R.id.see_installed_modules)
        uninstallPremiumModule = findViewById(R.id.uninstall_premium_module)
        parentLayout = findViewById(android.R.id.content)
        dialog = ProgressDialog(this)
        splitInstallManager = SplitInstallManagerFactory.create(this)
    }

    private fun setStatusBarColor(){
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, R.color.black_blue)
    }

    private fun goToPremiumClasses(){
        val intent = Intent(this, Class.forName("${Constants.PREMIUM_CLASSES_NAMESPACE}.PremiumActivity"))
        startActivity(intent)
    }

    private fun goToAllClasses(){
        val intent = Intent(this, Class.forName("${Constants.ALL_CLASSES_NAMESPACE}.ClassesActivity"))
        startActivity(intent)
    }

    private fun goToSettings(){
        val intent = Intent(this, SettingsActivity::class.java)
        startActivity(intent)
    }

    private fun initListeners(){
        seeAll.setOnClickListener { goToAllClasses() }

        tryItButton.setOnClickListener {
            if(!splitInstallManager.installedModules.contains(Constants.PREMIUM_MODULE)) {
                val isBackground = getForegroundBackground()
                if(isBackground) requestBackgroundPremiumModule() else requestForegroundPremiumModule()
                return@setOnClickListener
            }
            goToPremiumClasses()
        }

        settingsButton.setOnClickListener { goToSettings() }

        seeInstalledModules.setOnClickListener { checkInstalledModules() }

        uninstallPremiumModule.setOnClickListener { requestUninstallPremiumModule() }
    }

    private fun showError(error : String){
        Snackbar.make(parentLayout, error, Snackbar.LENGTH_LONG).show()
    }

    private fun getForegroundBackground() : Boolean {
        val sp = getSharedPreferences(Constants.SWITCH_FOREGROUND_BACKGROUND_SP, Context.MODE_PRIVATE)
        return sp.getBoolean(Constants.SWITCH_FOREGROUND_BACKGROUND_FLAG, false)
    }

    private fun requestBackgroundPremiumModule(){
        splitInstallManager.deferredInstall(listOf(Constants.PREMIUM_MODULE))
    }

    private fun requestUninstallPremiumModule(){
        splitInstallManager.deferredUninstall(listOf(Constants.PREMIUM_MODULE))
            .addOnSuccessListener {
                showError("The premium module was deleted successfully")
            }.addOnFailureListener { exception ->
                showError("Error to delete app: $exception")
            }
    }

    private fun checkInstalledModules(){
        val installedModules = splitInstallManager.installedModules.toList()
        showError(installedModules.toString())
    }

    private fun requestForegroundPremiumModule(){
        val request = SplitInstallRequest.newBuilder().addModule(Constants.PREMIUM_MODULE).build()

        val listener = SplitInstallStateUpdatedListener { splitInstallSessionState ->
            when(splitInstallSessionState.status()){
                SplitInstallSessionStatus.CANCELED -> { }
                SplitInstallSessionStatus.CANCELING -> { }
                SplitInstallSessionStatus.DOWNLOADED -> { }
                SplitInstallSessionStatus.FAILED -> { }
                SplitInstallSessionStatus.REQUIRES_USER_CONFIRMATION -> { }
                SplitInstallSessionStatus.UNKNOWN -> { }
                SplitInstallSessionStatus.PENDING -> {
                    dialog.setMessage("Pending...")
                    dialog.show()
                }
                SplitInstallSessionStatus.DOWNLOADING -> {
                    dialog.setMessage("Downloading...")
                }
                SplitInstallSessionStatus.INSTALLING -> {
                    dialog.setMessage("Installing...")
                }
                SplitInstallSessionStatus.INSTALLED -> {
                    dialog.dismiss()
                    goToPremiumClasses()
                }
            }
        }
        splitInstallManager.startInstall(request)
            .addOnFailureListener { exception -> showError("Exception: $exception") }
        splitInstallManager.registerListener(listener)
    }
}