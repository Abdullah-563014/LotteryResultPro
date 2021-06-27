package com.skithub.resultdear.ui

import android.app.Application
import android.content.Intent
import com.google.firebase.FirebaseApp
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.ktx.Firebase
import com.onesignal.OSNotificationReceivedEvent
import com.onesignal.OneSignal
import com.skithub.resultdear.database.network.MyApi
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
                val launchUrl: String = it.notification.launchURL
//            val notificationIntent: Intent =
//                Intent(applicationContext, SplashActivity::class.java)
////            notificationIntent.putExtra(Constants.targetUrl, launchUrl)
//            notificationIntent.flags =
//                Intent.FLAG_ACTIVITY_REORDER_TO_FRONT or Intent.FLAG_ACTIVITY_NEW_TASK
//            startActivity(notificationIntent)
            }
        }
        OneSignal.unsubscribeWhenNotificationsAreDisabled(true)
        OneSignal.pauseInAppMessages(true)
        OneSignal.setLocationShared(false)
    }


}