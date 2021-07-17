package com.skithub.resultdear.ui

import android.app.Application
import android.content.Context
import android.content.Intent
import android.net.Uri
import com.google.firebase.FirebaseApp
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.ktx.Firebase
import com.onesignal.OSNotificationReceivedEvent
import com.onesignal.OneSignal
import com.skithub.resultdear.database.network.MyApi
import com.skithub.resultdear.ui.today_result.TodayResultActivity
import com.skithub.resultdear.utils.CommonMethod
import com.skithub.resultdear.utils.Constants

class MyApplication : Application() {

    lateinit var firebaseAnalytics: FirebaseAnalytics
//    val appDatabase by lazy { AppDatabase.getDatabase(this) }
    val myApi by lazy { MyApi.invoke() }



    override fun onCreate() {
        super.onCreate()


        FirebaseApp.initializeApp(this)
        firebaseAnalytics = Firebase.analytics
        FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(true)


        initOneSignal()


    }


    private fun initOneSignal() {
        OneSignal.initWithContext(this)
        OneSignal.setAppId(Constants.onesignalApplicationId)
        OneSignal.setNotificationWillShowInForegroundHandler { notificationReceivedEvent: OSNotificationReceivedEvent ->
            val notification = notificationReceivedEvent.notification
            val data = notification.additionalData
            notificationReceivedEvent.complete(notification)
        }
        OneSignal.setNotificationOpenedHandler { result ->
            result?.let {
                val launchUrl: String? = it.notification?.launchURL
                val notificationIntent: Intent
                if (launchUrl==null) {
                    notificationIntent = Intent(applicationContext, TodayResultActivity::class.java)
                } else {
                    notificationIntent= Intent(Intent.ACTION_VIEW, Uri.parse(launchUrl))
                }
                notificationIntent.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(notificationIntent)
            }
        }
        OneSignal.unsubscribeWhenNotificationsAreDisabled(true)
        OneSignal.pauseInAppMessages(true)
        OneSignal.setLocationShared(false)
    }

    override fun attachBaseContext(base: Context?) {
        if (base!=null) {
            super.attachBaseContext(CommonMethod.updateLanguage(base))
        } else {
            super.attachBaseContext(base)
        }
    }


}