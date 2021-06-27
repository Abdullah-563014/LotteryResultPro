package com.skithub.resultdear.utils

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.graphics.Insets
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.Build
import android.util.DisplayMetrics
import android.view.WindowInsets
import android.webkit.MimeTypeMap
import java.text.SimpleDateFormat
import java.util.*


object CommonMethod {



    fun increaseDecreaseDaysUsingValue(days: Int): String {
        val calendar: Calendar=Calendar.getInstance()
        val simpleDateFormat=SimpleDateFormat(Constants.dayFormat,Locale.getDefault())
        calendar.set(Calendar.DAY_OF_YEAR,calendar.get(Calendar.DAY_OF_YEAR)+days)
        return simpleDateFormat.format(calendar.time)
    }

    fun getCurrentTimeUsingFormat(format: String): String {
        val dateFormat: SimpleDateFormat = SimpleDateFormat(format, Locale.getDefault())
        return dateFormat.format(Date()).toString()
    }

    fun getCurrentTime(): String {
        val date = Date()
        return date.time.toString()
    }

    fun getDayNameUsingDate(date: String): String {
        try {
            val format=SimpleDateFormat(Constants.dayFormat, Locale.getDefault())
            val formattedDate=format.parse(date)
            val simpleDateFormat=SimpleDateFormat("EEEE", Locale.getDefault())
            return simpleDateFormat.format(formattedDate)
        } catch (e: Exception) {
            return "day name not found"
        }
    }

    fun getHoursDifBetweenToTime(startTime: String, endTime: String): String? {
        return try {
            val date1 = startTime.toLong()
            val date2 = endTime.toLong()
            val difference = date2 - date1
            val differenceDates = difference / (60 * 60 * 1000)
            differenceDates.toString()
        } catch (exception: Exception) {
            null
        }
    }

    fun getMinuteDifBetweenToTime(startTime: String, endTime: String): String? {
        return try {
            val date1 = startTime.toLong()
            val date2 = endTime.toLong()
            val difference = date2 - date1
            val differenceDates = difference / (60 * 1000)
            differenceDates.toString()
        } catch (exception: Exception) {
            null
        }
    }

    fun getMimeTypeFromUrl(url: String): String? {
        var type: String?=null
        val typeExtension: String=MimeTypeMap.getFileExtensionFromUrl(url)
        if (!typeExtension.isNullOrEmpty()) {
            type=MimeTypeMap.getSingleton().getMimeTypeFromExtension(typeExtension)
        }
        return type
    }

    fun openAppLink(context: Context) {
        val appPackageName: String=context.applicationContext.packageName
        try {
            val appIntent: Intent = Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$appPackageName"))
            appIntent.setPackage("com.android.vending")
            context.startActivity(appIntent)
        } catch (e: ActivityNotFoundException) {
            context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=$appPackageName")))
        }
    }

    fun shareAppLink(context: Context) {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=${context.applicationContext.packageName}")
            type = "text/plain"
        }
        val shareIntent = Intent.createChooser(sendIntent, null)
        context.startActivity(shareIntent)
    }

    fun haveInternet(connectivityManager: ConnectivityManager?): Boolean {
        return when {
            connectivityManager==null -> {
                false
            }
            Build.VERSION.SDK_INT >= 23 -> {
                val network = connectivityManager.activeNetwork
                val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) || capabilities.hasTransport(
                    NetworkCapabilities.TRANSPORT_WIFI
                )
            }
            else -> {
                (connectivityManager.activeNetworkInfo != null && connectivityManager.activeNetworkInfo!!.isAvailable
                        && connectivityManager.activeNetworkInfo!!.isConnected)
            }
        }
    }

    fun getScreenWidth(activity: Activity): Int {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val windowMetrics = activity.windowManager.currentWindowMetrics
            val insets: Insets = windowMetrics.windowInsets
                .getInsetsIgnoringVisibility(WindowInsets.Type.systemBars())
            windowMetrics.bounds.width() - insets.left - insets.right
        } else {
            val displayMetrics = DisplayMetrics()
            activity.windowManager.defaultDisplay.getMetrics(displayMetrics)
            displayMetrics.widthPixels
        }
    }

    fun getScreenHeight(activity: Activity): Int {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val windowMetrics = activity.windowManager.currentWindowMetrics
            val insets: Insets = windowMetrics.windowInsets
                .getInsetsIgnoringVisibility(WindowInsets.Type.systemBars())
            windowMetrics.bounds.width() - insets.left - insets.right
        } else {
            val displayMetrics = DisplayMetrics()
            activity.windowManager.defaultDisplay.getMetrics(displayMetrics)
            displayMetrics.heightPixels
        }
    }



}