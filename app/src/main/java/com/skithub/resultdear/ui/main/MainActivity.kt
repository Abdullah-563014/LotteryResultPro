package com.skithub.resultdear.ui.main

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Bundle
import android.telephony.TelephonyManager
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.database.*
import com.google.firebase.messaging.FirebaseMessaging
import com.skithub.resultdear.R
import com.skithub.resultdear.databinding.ActivityMainBinding
import com.skithub.resultdear.databinding.PhoneNumberInputDialogCustomLayoutBinding
import com.skithub.resultdear.model.AdsImageModel
import com.skithub.resultdear.ui.MyApplication
import com.skithub.resultdear.ui.common_number.CommonNumberActivity
import com.skithub.resultdear.ui.get_help.To_Get_HelpActivity
import com.skithub.resultdear.ui.lottery_number_check.LotteryNumberCheckActivity
import com.skithub.resultdear.ui.middle_number.MiddleNumberActivity
import com.skithub.resultdear.ui.old_result.OldResultActivity
import com.skithub.resultdear.ui.privacy_policy.PrivacyPolicyActivity
import com.skithub.resultdear.ui.special_or_bumper.SplOrBumperActivity
import com.skithub.resultdear.ui.today_result.TodayResultActivity
import com.skithub.resultdear.ui.winning_number.WinningNumberActivity
import com.skithub.resultdear.ui.yesterday_result.YesterdayResultActivity
import com.skithub.resultdear.utils.CommonMethod
import com.skithub.resultdear.utils.Constants
import com.skithub.resultdear.utils.Coroutines
import com.skithub.resultdear.utils.MyExtensions.shortToast
import com.skithub.resultdear.utils.SharedPreUtils
import java.util.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private var tog: ActionBarDrawerToggle? = null
    private var mBackPressed: Long = 0
    private lateinit var connectivityManager: ConnectivityManager
    private var rainbowColors: IntArray= intArrayOf(Color.parseColor("#FF2B22"),Color.parseColor("#FF7F22"),Color.parseColor("#EDFF22"),Color.parseColor("#22FF22"),Color.parseColor("#05EEFA"),Color.parseColor("#08B4BD"),Color.parseColor("#056B70"))
    private lateinit var telephonyManager: TelephonyManager
    private var tutorialInfo: AdsImageModel?=null
    private lateinit var phoneNumberDialogBinding: PhoneNumberInputDialogCustomLayoutBinding
    private var phoneNumberAlertDialog: AlertDialog?=null
    private lateinit var inputMethodManager: InputMethodManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val factory= MainViewModelFactory((application as MyApplication).myApi)
        viewModel= ViewModelProvider(this,factory).get(MainViewModel::class.java)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)





        initAll()

        if (savedInstanceState==null) {
            checkBeginning()
        }

        setupNavigationBar()

        loadTutorialInfo()

        getPremiumStatus()






    }


    private fun initAll() {
        connectivityManager=getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        telephonyManager=getSystemService(TELEPHONY_SERVICE) as TelephonyManager
        inputMethodManager=getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        binding.todayResultCardView.setOnClickListener(this)
        binding.yesterDayResultCardView.setOnClickListener(this)
        binding.yesterDayVsPreResultCardView.setOnClickListener(this)
        binding.oldResultCardView.setOnClickListener(this)
        binding.specialOrBumperResultCardView.setOnClickListener(this)
        binding.winingNumberCardView.setOnClickListener(this)
        binding.lotteryNumberCheck.setOnClickListener(this)
        binding.tutorialImageView.setOnClickListener(this)
        binding.commonNumberButton.setOnClickListener(this)
        binding.middleNumberButton.setOnClickListener(this)
        binding.englishLanguageTextView.setOnClickListener(this)
        binding.banglaLanguageTextView.setOnClickListener(this)
        binding.hindiLanguageTextView.setOnClickListener(this)
        Glide.with(this).load(R.drawable.new_text_animation).fitCenter().into(binding.newTextAnimationImageView)
        binding.pickTicketDescriptionTextView.setColors(*rainbowColors)
        binding.spinKit.visibility=View.GONE
    }

    private fun hideKeyBoard() {
        inputMethodManager.hideSoftInputFromWindow(phoneNumberDialogBinding.phoneNumberEditText.windowToken,0)
    }

    private fun showSupportCallDialog() {
        val builder=AlertDialog.Builder(this)
            .setCancelable(true)
            .setTitle(resources.getString(R.string.active_premium_service))
            .setMessage(resources.getString(R.string.premium_active_message))
            .setPositiveButton(resources.getString(R.string.ok)) { dialog, which ->
                dialog?.dismiss()
                try {
                    val dialerIntent: Intent = Intent(Intent.ACTION_DIAL)
                    dialerIntent.data = Uri.parse("tel:${Constants.supportTeamMobileNumber}")
                    startActivity(dialerIntent)
                } catch (e: Exception) {
                    shortToast("failed to call for:- ${e.message}")
                }
            }
            .setNegativeButton(resources.getString(R.string.cancel)
            ) { dialog, which -> dialog?.dismiss() }

        val alertDialog=builder.create()
        if (alertDialog.window!=null) {
            alertDialog.window!!.attributes.windowAnimations=R.style.DialogTheme
        }
        if (!isFinishing) {
            alertDialog.show()
        }
    }

    private fun getPremiumStatus() {
        Coroutines.main {
            val oldToken=SharedPreUtils.getStringFromStorage(applicationContext,Constants.fcmTokenKey,null)
            if (oldToken.isNullOrEmpty()) {
                phoneNumberDialogBinding= PhoneNumberInputDialogCustomLayoutBinding.inflate(layoutInflater)
                phoneNumberDialogBinding.submitButton.setOnClickListener(this)
                val builder=AlertDialog.Builder(this)
                    .setCancelable(false)
                    .setView(phoneNumberDialogBinding.root)
                phoneNumberAlertDialog=builder.create()
                if (phoneNumberAlertDialog?.window!=null) {
                    phoneNumberAlertDialog?.window!!.attributes.windowAnimations=R.style.DialogTheme
                }
                if (!isFinishing) {
                    phoneNumberAlertDialog?.show()
                }
            } else {
                binding.spinKit.visibility=View.VISIBLE
                val response=viewModel.getUserInfoByToken(oldToken)
                binding.spinKit.visibility=View.GONE
                if (response.isSuccessful && response.code()==200) {
                    if (response.body()!=null) {
                        if (response.body()?.status.equals("success",true)) {
                            val userInfoModel=response.body()?.data!![0]
                            userInfoModel?.let {
                                Constants.premiumActivationStatus="${it.activeStatus}"
                                Constants.registrationDate="${it.registrationDate}"
                                Constants.phone="${it.phone}"
                            }
                        } else {
                            shortToast("${response.body()?.message}")
                        }
                    }
                }
            }
        }
    }

    private fun updateUserProfile() {
        try {
            hideKeyBoard()
            FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    return@OnCompleteListener
                }
                val token = task.result
                val phone: String=phoneNumberDialogBinding.phoneNumberEditText.text.toString().trim()
                val registrationDate: String=CommonMethod.increaseDecreaseDaysUsingValue(0, Locale.ENGLISH)
                val activeStatus: String="false"
                if (phone.length!=10) {
                    shortToast(resources.getString(R.string.input_phone_number))
                    return@OnCompleteListener
                }
                phoneNumberAlertDialog?.dismiss()

                if (token!=null) {
                    Coroutines.main {
                        val response=viewModel.uploadUserInfo(token,phone,registrationDate,activeStatus)
                        if (response.isSuccessful && response.code()==200) {
                            if (response.body()!=null) {
                                if (response.body()?.status.equals("success")) {
                                    SharedPreUtils.setStringToStorage(applicationContext,Constants.fcmTokenKey,token)
                                    Constants.premiumActivationStatus="false"
                                    Constants.phone=phone
                                    Constants.registrationDate=registrationDate

                                    getPremiumStatus()
                                }
                            }
                        }
                        FirebaseMessaging.getInstance().subscribeToTopic(Constants.userTypeFree)
                    }
                }
            })
        } catch (e: Exception) {

        }
    }

    private fun loadTutorialInfo() {
        Coroutines.main {
            try {
                val response=viewModel.getHomeTutorialInfo()
                if (response.isSuccessful && response.code()==200) {
                    if (response.body()!=null) {
                        if (response.body()?.status.equals("success")) {
                            try {
                                tutorialInfo=response.body()?.data!![0]
                                Glide.with(this)
                                    .load(tutorialInfo?.imageUrl)
                                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                                    .skipMemoryCache(true)
                                    .placeholder(R.drawable.loading_placeholder)
                                    .error(R.drawable.tutorial_thumb)
                                    .into(binding.tutorialImageView)
                                binding.tutorialImageView.visibility=View.VISIBLE
                            } catch (e: Exception) {
                                binding.tutorialImageView.visibility=View.GONE
                            }
                        }
                    }
                }
            } catch (e: Exception) {
                binding.tutorialImageView.visibility=View.GONE
            }
        }
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
                if (!lanStatus) {
//                    changeLocale(lanCode)
                    showChangeLanguageDialog()
                }
            }
        }
    }

    private fun setupNavigationBar() {
        tog=ActionBarDrawerToggle(this,binding.drawerLayout,binding.toolbar,R.string.open,R.string.close)
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

    private fun checkMandatoryUpdate() {
        val versionRef: DatabaseReference = FirebaseDatabase.getInstance().reference.child("LotteryMasterProVersionName")
        versionRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var versionName: String = "1.0"
                if (snapshot.exists() && snapshot.getValue(String::class.java)!=null) {
                    versionName = snapshot.getValue(String::class.java)!!
                    val databaseVersionName: Double=versionName.toDouble()
                    val appVersionNameInString: String=applicationContext.packageManager.getPackageInfo(applicationContext.packageName,0).versionName
                    val appVersionName: Double=appVersionNameInString.toDouble()
                    if (appVersionName<databaseVersionName) {
                        val builder: AlertDialog.Builder=AlertDialog.Builder(this@MainActivity)
                            .setCancelable(false)
                            .setTitle(resources.getString(R.string.found_new_version))
                            .setMessage(resources.getString(R.string.app_update_message))
                            .setPositiveButton(resources.getString(R.string.ok)) { p0, p1 ->
                                cacheDir.deleteRecursively()
                                CommonMethod.openAppLink(this@MainActivity)
                            }
                        val alertDialog: AlertDialog=builder.create()
                        if (!isFinishing) {
                            alertDialog.show()
                        }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
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
                    gridIntent= Intent(applicationContext, OldResultActivity::class.java)
                    startActivity(gridIntent)
                }

                R.id.oldResultCardView -> {
                    gridIntent= Intent(applicationContext, OldResultActivity::class.java)
                    startActivity(gridIntent)
                }

                R.id.specialOrBumperResultCardView -> {
                    gridIntent= Intent(applicationContext, SplOrBumperActivity::class.java)
                    startActivity(gridIntent)
                }

                R.id.lottery_number_check -> {
                    gridIntent= Intent(applicationContext, LotteryNumberCheckActivity::class.java)
                    startActivity(gridIntent)
                }

                R.id.winingNumberCardView -> {
                    gridIntent= Intent(applicationContext, WinningNumberActivity::class.java)
                    startActivity(gridIntent)
                }

                R.id.commonNumberButton -> {
//                    if (Constants.premiumActivationStatus.equals("true",true)) {
//                        gridIntent= Intent(applicationContext, CommonNumberActivity::class.java)
//                        startActivity(gridIntent)
//                    } else {
//                        showSupportCallDialog()
//                    }

                    gridIntent= Intent(applicationContext, CommonNumberActivity::class.java)
                    startActivity(gridIntent)

                }

                R.id.middleNumberButton -> {
                    gridIntent= Intent(applicationContext, MiddleNumberActivity::class.java)
                    startActivity(gridIntent)
                }

                R.id.englishLanguageTextView -> changeLocale("en_US")

                R.id.banglaLanguageTextView -> changeLocale("bn")

                R.id.hindiLanguageTextView -> changeLocale("hi")

                R.id.tutorialImageView -> {
                    tutorialInfo?.let {
                        if (!it.targetUrl.isNullOrEmpty()) {
                            CommonMethod.openLink(this,it.targetUrl)
                        }
                    }
                    return
                }

                R.id.submitButton -> {
                    updateUserProfile()
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
        binding.particleView.resume()
        checkMandatoryUpdate()
    }

    override fun onPause() {
        super.onPause()
        binding.particleView.pause()
    }

    override fun attachBaseContext(newBase: Context?) {
        if (newBase!=null) {
            super.attachBaseContext(CommonMethod.updateLanguage(newBase))
        } else {
            super.attachBaseContext(newBase)
        }
    }

    companion object {
        private const val TIME_INTERVAL = 2000
    }




}