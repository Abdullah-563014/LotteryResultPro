package com.skithub.resultdear.ui.main

import android.content.DialogInterface
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.skithub.resultdear.R
import com.skithub.resultdear.databinding.ActivityMainBinding
import com.skithub.resultdear.ui.first_prize_winner.Fst_Prize_WinnersActivity
import com.skithub.resultdear.ui.GridLayout.*
import com.skithub.resultdear.ui.GridLayout.old_result.OldResultActivity
import com.skithub.resultdear.ui.GridLayout.special_or_bumper.SPL_Or_BumperActivity
import com.skithub.resultdear.ui.GridLayout.wining_numbers.Winning_NumbersActivity
import com.skithub.resultdear.ui.lucky_number.Your_Lucky_NumbersActivity
import com.skithub.resultdear.ui.privacy_policy.PrivacyPolicyActivity
import com.skithub.resultdear.ui.today_lottery_number_check.TodayLotteryNumberCheckActivity
import com.skithub.resultdear.utils.CommonMethod
import com.skithub.resultdear.utils.Constants
import com.skithub.resultdear.utils.MyExtensions.shortToast

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
        binding.todayNumberCheck.setOnClickListener(this)
        binding.PrizeWinners.setOnClickListener (this)
        binding.LuckeyNumbers.setOnClickListener(this)
        binding.claimFormCardView.setOnClickListener(this)
        binding.getHelpCardView.setOnClickListener(this)
        binding.privacyPolicyCardView.setOnClickListener(this)
        binding.moreAppsCardView.setOnClickListener(this)
    }

    private fun setupNavigationBar() {
        tog=ActionBarDrawerToggle(this,binding.drawerLayout,R.string.open,R.string.close)
        binding.drawerLayout.addDrawerListener(tog!!)
        tog?.syncState()
        binding.navigationView.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.Share -> {
                    CommonMethod.shareAppLink(this)
                }
                R.id.appPrivacy -> {
                    startActivity(Intent(this,PrivacyPolicyActivity::class.java))
                }
                R.id.contactUs -> {
                    startActivity(Intent(this,To_Get_HelpActivity::class.java))
                }
                R.id.MoreApps -> {
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

                R.id.winingNumberCardView -> {
                    gridIntent= Intent(applicationContext, Winning_NumbersActivity::class.java)
                    startActivity(gridIntent)
                }

                R.id.today_number_check -> {
                    gridIntent= Intent(applicationContext, TodayLotteryNumberCheckActivity::class.java)
                    startActivity(gridIntent)
                }

                R.id.Prize_Winners -> {
                    gridIntent= Intent(applicationContext, Fst_Prize_WinnersActivity::class.java)
                    startActivity(gridIntent)
                }

                R.id.LuckeyNumbers -> {
                    gridIntent= Intent(applicationContext, Your_Lucky_NumbersActivity::class.java)
                    startActivity(gridIntent)
                }

                R.id.claimFormCardView -> {
                    gridIntent= Intent(applicationContext,Claim_formActivity::class.java)
                    startActivity(gridIntent)
                }

                R.id.getHelpCardView -> {
                    gridIntent= Intent(applicationContext,To_Get_HelpActivity::class.java)
                    startActivity(gridIntent)
                }

                R.id.privacyPolicyCardView -> {
                    gridIntent= Intent(applicationContext,PrivacyPolicyActivity::class.java)
                    startActivity(gridIntent)
                }

                R.id.moreAppsCardView -> {
                    CommonMethod.openConsoleLink(this,Constants.consoleId)
                }
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
    }

    companion object {
        private const val TIME_INTERVAL = 2000
    }




}