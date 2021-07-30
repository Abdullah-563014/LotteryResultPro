package com.skithub.resultdear.services

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.media.RingtoneManager
import android.net.Uri
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.skithub.resultdear.R
import com.skithub.resultdear.ui.MyApplication
import com.skithub.resultdear.ui.main.MainActivity
import com.skithub.resultdear.utils.Constants

class MyFirebaseMessagingService: FirebaseMessagingService() {

    private var notificationManager: NotificationManager?=null

    override fun onNewToken(p0: String) {
        super.onNewToken(p0)
    }

    override fun onMessageReceived(p0: RemoteMessage) {
        super.onMessageReceived(p0)

        try {
            if (notificationManager==null) {
                notificationManager=getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            }
            if (p0.notification?.title.isNullOrEmpty()) {
                showDataNotification(p0.data)
            } else if (p0.notification!=null) {
                showPayloadNotification(p0.notification!!)
            }
        } catch (e: Exception) {}
    }


    private fun showPayloadNotification(notification: RemoteMessage.Notification) {
        val notificationIntent: Intent= Intent(this,MainActivity::class.java)
        val notificationPendingIntent: PendingIntent=PendingIntent.getActivity(this,Constants.notificationRequestCode,notificationIntent,PendingIntent.FLAG_ONE_SHOT)
        val defaultNotificationUri=RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder: NotificationCompat.Builder=NotificationCompat
            .Builder(this,(applicationContext as MyApplication).MY_NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle(notification.title)
            .setContentText(notification.body)
            .setAutoCancel(true)
            .setSound(defaultNotificationUri)
            .setContentIntent(notificationPendingIntent)
        notificationManager?.notify(Constants.notificationRequestCode,notificationBuilder.build())
    }

    private fun showDataNotification(notificationData: MutableMap<String,String>) {
        val notificationIntent: Intent
        if (notificationData[Constants.notificationTargetUrlKey].isNullOrEmpty()) {
            notificationIntent= Intent(this,MainActivity::class.java)
        } else {
            notificationIntent= Intent(Intent.ACTION_VIEW, Uri.parse(notificationData[Constants.notificationTargetUrlKey]))
        }
        val notificationPendingIntent: PendingIntent=PendingIntent.getActivity(this,Constants.notificationRequestCode,notificationIntent,PendingIntent.FLAG_ONE_SHOT)
        val defaultNotificationUri=RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder: NotificationCompat.Builder=NotificationCompat
            .Builder(this,(applicationContext as MyApplication).MY_NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle(notificationData[Constants.notificationTitleKey])
            .setContentText(notificationData[Constants.notificationBodyKey])
            .setAutoCancel(notificationData[Constants.notificationCancelableKey].equals("true",true))
            .setSound(defaultNotificationUri)
            .setContentIntent(notificationPendingIntent)
        notificationManager?.notify(Constants.notificationRequestCode,notificationBuilder.build())
    }




}