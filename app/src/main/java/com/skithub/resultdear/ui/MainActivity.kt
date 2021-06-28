package com.skithub.resultdear.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.GridLayout
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.GravityCompat
import com.skithub.resultdear.R
import com.skithub.resultdear.databinding.ActivityMainBinding
import com.skithub.resultdear.ui.GridLayout.*
import com.skithub.resultdear.ui.GridLayout.old_result.OldResultActivity
import com.skithub.resultdear.ui.GridLayout.special_or_bumper.SPL_Or_BumperActivity
import com.skithub.resultdear.ui.GridLayout.wining_numbers.Winning_NumbersActivity

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityMainBinding
    private var tog: ActionBarDrawerToggle? = null
    private var mBackPressed: Long = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)



        initAll()

        setupNavigationBar()









    }


    private fun initAll() {
        binding.todayNumberCheck.setOnClickListener(this)
        binding.PrizeWinners.setOnClickListener (this)
        binding.LuckeyNumbers.setOnClickListener(this)
    }

    private fun setupNavigationBar() {
        tog = ActionBarDrawerToggle(
            this,
            binding.drawerLayout,
            binding.toolbar,
            R.string.open,
            R.string.close
        )
        binding.drawerLayout.addDrawerListener(tog!!)
        tog?.syncState()
        binding.navigationView.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.night_Mood -> {
                    Toast.makeText(this@MainActivity, "Night Mode", Toast.LENGTH_SHORT).show()
                }
                R.id.Setings -> {
                    Toast.makeText(this@MainActivity, "Settings", Toast.LENGTH_SHORT).show()
                }
                R.id.Share -> {
                    Toast.makeText(this@MainActivity, "Share", Toast.LENGTH_SHORT).show()
                }
                R.id.appPrivacy -> {
                    Toast.makeText(this@MainActivity, "App Privacy", Toast.LENGTH_SHORT).show()
                }
                R.id.contactUs -> {
                    Toast.makeText(this@MainActivity, "Contact Us", Toast.LENGTH_SHORT).show()
                }
                R.id.MoreApps -> {
                    Toast.makeText(this@MainActivity, "More Apps", Toast.LENGTH_SHORT).show()
                }
                R.id.home -> {
                    Toast.makeText(this@MainActivity, "Home", Toast.LENGTH_SHORT).show()
                }
            }
            if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
                binding.drawerLayout.closeDrawer(GravityCompat.START)
            }
            true
        }

        //gridLayout work part
        clickEvent(binding.gradLayout)
    }

    private fun clickEvent(gridLayout: GridLayout) {
        for (i in 0 until gridLayout.getChildCount()) {
            val cardView = gridLayout.getChildAt(i) as CardView
            cardView.setOnClickListener {
                if (i == 0) {
                    startActivity(Intent(this@MainActivity, TodayResultActivity::class.java))
                } else if (i == 1) {
                    startActivity(Intent(this@MainActivity, YesterdayResultActivity::class.java))
                } else if (i == 2) {
                    startActivity(Intent(this@MainActivity, YesVsPreActivity::class.java))
                } else if (i == 3) {
                    startActivity(Intent(this@MainActivity, OldResultActivity::class.java))
                } else if (i == 4) {
                    startActivity(Intent(this@MainActivity, SPL_Or_BumperActivity::class.java))
                } else {
                    startActivity(Intent(this@MainActivity, Winning_NumbersActivity::class.java))
                }
            }
        }
    }

    override fun onBackPressed() {
        if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis()) {
            super.onBackPressed()
            return
        } else {
            Toast.makeText(baseContext, "Press Back again to leave!", Toast.LENGTH_SHORT).show()
        }
        mBackPressed = System.currentTimeMillis()
    }

    override fun onClick(v: View?) {
        var gridIntent: Intent
        v?.let {
            when (it.id) {
                R.id.today_number_check -> {
                    gridIntent= Intent(applicationContext,Today_Number_CheckActivity::class.java)
                    startActivity(gridIntent)
                }

                R.id.Prize_Winners -> {
                    gridIntent= Intent(applicationContext,Fst_Prize_WinnersActivity::class.java)
                    startActivity(gridIntent)
                }

                R.id.LuckeyNumbers -> {
                    gridIntent= Intent(applicationContext,Your_Lucky_NumbersActivity::class.java)
                    startActivity(gridIntent)
                }
            }
        }
    }

    companion object {
        private const val TIME_INTERVAL = 2000
    }


}