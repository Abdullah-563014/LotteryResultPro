package com.skithub.resultdear.ui.main

import android.content.DialogInterface
import android.content.Intent
import android.content.res.Configuration
import android.net.ConnectivityManager
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.bumptech.glide.Glide
import com.skithub.resultdear.R
import com.skithub.resultdear.databinding.ActivityMainBinding
import com.skithub.resultdear.ui.GridLayout.get_help.To_Get_HelpActivity
import com.skithub.resultdear.ui.GridLayout.old_result.OldResultActivity
import com.skithub.resultdear.ui.GridLayout.special_or_bumper.SPL_Or_BumperActivity
import com.skithub.resultdear.ui.GridLayout.today_result.TodayResultActivity
import com.skithub.resultdear.ui.GridLayout.yes_vs_pre.YesVsPreActivity
import com.skithub.resultdear.ui.GridLayout.yesterday_result.YesterdayResultActivity
import com.skithub.resultdear.ui.privacy_policy.PrivacyPolicyActivity
import com.skithub.resultdear.ui.today_lottery_number_check.TodayLotteryNumberCheckActivity
import com.skithub.resultdear.utils.CommonMethod
import com.skithub.resultdear.utils.Constants
import com.skithub.resultdear.utils.Coroutines
import com.skithub.resultdear.utils.SharedPreUtils
import java.util.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityMainBinding
    private var tog: ActionBarDrawerToggle? = null
    private var mBackPressed: Long = 0
    private lateinit var connectivityManager: ConnectivityManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)





        initAll()

        if (savedInstanceState==null) {
            checkBeginning()
        }

        setupNavigationBar()










    }


    private fun initAll() {
        connectivityManager=getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        binding.todayResultCardView.setOnClickListener(this)
        binding.yesterDayResultCardView.setOnClickListener(this)
        binding.yesterDayVsPreResultCardView.setOnClickListener(this)
        binding.oldResultCardView.setOnClickListener(this)
        binding.specialOrBumperResultCardView.setOnClickListener(this)
        binding.winingNumberCardView.setOnClickListener(this)
        binding.lotteryNumberCheck.setOnClickListener(this)
        binding.tutorialImageView.setOnClickListener(this)
        Glide.with(this).load(R.drawable.tutorial_thumb).fitCenter().into(binding.tutorialImageView)
    }

    private fun showChangeLanguageDialog() {
        val lanList: Array<String> = arrayOf("বাংলা","English","हिंदी")
        val builder=AlertDialog.Builder(this)
            .setCancelable(false)
            .setTitle(resources.getString(R.string.choose_your_language))
            .setSingleChoiceItems(lanList,-1) { dialog, which ->
                if (which == 0) {
                    changeLocale("bn")
                } else if (which == 1) {
                    changeLocale("en_US")
                } else if (which == 2) {
                    changeLocale("hi")
                }
                dialog?.dismiss()
            }
        val alertDialog=builder.create()
        if (!isFinishing) {
            alertDialog.show()
        }
    }

    private fun changeLocale(lanCode: String) {
        val local: Locale= Locale(lanCode)
        Locale.setDefault(local)
        resources.configuration.setLocale(local)
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.N) {
            applicationContext.createConfigurationContext(resources.configuration)
        } else {
            resources.updateConfiguration(resources.configuration,resources.displayMetrics)
        }
        Coroutines.io {
            SharedPreUtils.setStringToStorage(applicationContext,Constants.appLanguageKey,lanCode)
            SharedPreUtils.setBooleanToStorage(applicationContext,Constants.appLanguageStatusKey,true)
        }
        recreate()
    }

    private fun checkBeginning() {
        Coroutines.io {
            val lanCode: String=SharedPreUtils.getStringFromStorage(applicationContext,Constants.appLanguageKey,Constants.appDefaultLanCode)!!
            val lanStatus: Boolean=SharedPreUtils.getBooleanFromStorage(applicationContext,Constants.appLanguageStatusKey,false)
            Coroutines.main {
                if (lanStatus) {
                    changeLocale(lanCode)
                } else {
                    showChangeLanguageDialog()
                }
            }
        }
    }

    private fun setupNavigationBar() {
        tog=ActionBarDrawerToggle(this,binding.drawerLayout,R.string.open,R.string.close)
        binding.drawerLayout.addDrawerListener(tog!!)
        tog?.syncState()
        binding.navigationView.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.changeLanguage -> {
                    showChangeLanguageDialog()
                }
                R.id.share -> {
                    CommonMethod.shareAppLink(this)
                }
                R.id.appPrivacy -> {
                    startActivity(Intent(this,PrivacyPolicyActivity::class.java))
                }
                R.id.contactUs -> {
                    startActivity(Intent(this, To_Get_HelpActivity::class.java))
                }
                R.id.moreApps -> {
                    CommonMethod.openConsoleLink(this,Constants.consoleId)
                }
            }
            if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
                binding.drawerLayout.closeDrawer(GravityCompat.START)
            }
            true
        }

    }

    private fun noInternetDialog() {
        val builder=AlertDialog.Builder(this)
            .setCancelable(false)
            .setTitle("Internet Detection")
            .setMessage("No valid internet connection detected. Please check your internet connection and try again.")
            .setIcon(R.drawable.ic_warning)
            .setPositiveButton("ok") { dialog, which ->
                dialog?.dismiss()
                finishAffinity()
            }
        val alertDialog=builder.create()
        if (!isFinishing) {
            alertDialog.show()
        }
    }

    override fun onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis()) {
                super.onBackPressed()
                return
            } else {
                Toast.makeText(baseContext, "Press Back again to leave!", Toast.LENGTH_SHORT).show()
            }
            mBackPressed = System.currentTimeMillis()
        }
    }

    override fun onClick(v: View?) {
        var gridIntent: Intent
        v?.let {
            when (it.id) {
                R.id.todayResultCardView -> {
                    gridIntent= Intent(applicationContext, TodayResultActivity::class.java)
                    startActivity(gridIntent)
                }

                R.id.yesterDayResultCardView -> {
                    gridIntent= Intent(applicationContext, YesterdayResultActivity::class.java)
                    startActivity(gridIntent)
                }

                R.id.yesterDayVsPreResultCardView -> {
                    gridIntent= Intent(applicationContext, YesVsPreActivity::class.java)
                    startActivity(gridIntent)
                }

                R.id.oldResultCardView -> {
                    gridIntent= Intent(applicationContext, OldResultActivity::class.java)
                    startActivity(gridIntent)
                }

                R.id.specialOrBumperResultCardView -> {
                    gridIntent= Intent(applicationContext, SPL_Or_BumperActivity::class.java)
                    startActivity(gridIntent)
                }

                R.id.lottery_number_check -> {
                    gridIntent= Intent(applicationContext, TodayLotteryNumberCheckActivity::class.java)
                    startActivity(gridIntent)
                }

                R.id.tutorialImageView -> CommonMethod.openVideo(this,Constants.tutorialVideoId)
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    binding.drawerLayout.closeDrawer(GravityCompat.START)
                } else {
                    binding.drawerLayout.openDrawer(GravityCompat.START)
                }
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()
        if (!CommonMethod.haveInternet(connectivityManager)) {
            noInternetDialog()
        }
        binding.particleView.resume()
    }

    override fun onPause() {
        super.onPause()
        binding.particleView.pause()
    }

    companion object {
        private const val TIME_INTERVAL = 2000
    }




}