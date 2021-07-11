package com.skithub.resultdear.ui.privacy_policy

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.net.http.SslError
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.webkit.*
import androidx.annotation.RequiresApi
import com.skithub.resultdear.R
import com.skithub.resultdear.databinding.ActivityPrivacyPolicyBinding
import com.skithub.resultdear.utils.CommonMethod

class PrivacyPolicyActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPrivacyPolicyBinding
    private val privacyPolicyUrl:String="https://privacypolicy2019forandroid.blogspot.com/2021/06/lotterysambad-pro.html"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityPrivacyPolicyBinding.inflate(layoutInflater)
        setContentView(binding.root)



        initAll()

        setupWebView()




    }


    private fun initAll() {
        binding.spinKit.visibility=View.GONE
        binding.swipeRefreshLayout.setOnRefreshListener {
            binding.webView.reload()
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setupWebView() {
        binding.webView.settings.setJavaScriptEnabled(true)
        binding.webView.settings.setSupportMultipleWindows(false)
        binding.webView.settings.setJavaScriptCanOpenWindowsAutomatically(true)
        binding.webView.settings.setDomStorageEnabled(true)
        binding.webView.settings.setAppCacheEnabled(true)
        binding.webView.settings.setDatabaseEnabled(true)
        binding.webView.settings.setUseWideViewPort(true)
        binding.webView.settings.setSupportZoom(true)
        binding.webView.settings.setLoadsImagesAutomatically(true)
        binding.webView.settings.setGeolocationEnabled(true)
        binding.webView.settings.setBuiltInZoomControls(true)
        binding.webView.settings.setDisplayZoomControls(false)
        binding.webView.settings.setLoadWithOverviewMode(false)
        binding.webView.isLongClickable = false
        binding.webView.settings.setAllowFileAccess(true)
        binding.webView.settings.setAllowContentAccess(true)
        binding.webView.settings.setAllowFileAccessFromFileURLs(true)
        binding.webView.settings.setAllowUniversalAccessFromFileURLs(true)
        binding.webView.webChromeClient = WebChromeClient()
        binding.webView.webViewClient = MyWebViewClient()
        binding.webView.loadUrl(privacyPolicyUrl)
    }

    inner class MyWebViewClient : WebViewClient() {

        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
            binding.spinKit.visibility = View.GONE
        }

        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)
            binding.spinKit.visibility = View.VISIBLE
        }

        override fun doUpdateVisitedHistory(view: WebView?, url: String?, isReload: Boolean) {
            super.doUpdateVisitedHistory(view, url, isReload)
        }

        override fun onReceivedSslError(view: WebView?, handler: SslErrorHandler?, error: SslError?) {
            super.onReceivedSslError(view, handler, error)
            handler?.proceed()
        }

        @RequiresApi(api = Build.VERSION_CODES.M)
        override fun onReceivedError(
            view: WebView?,
            request: WebResourceRequest?,
            error: WebResourceError?
        ) {
            super.onReceivedError(view, request, error)

        }

        override fun onReceivedError(
            view: WebView?,
            errorCode: Int,
            description: String?,
            failingUrl: String?
        ) {
            super.onReceivedError(view, errorCode, description, failingUrl)

        }
    }

    override fun onBackPressed() {
        if (binding.webView.canGoBack()) {
            binding.webView.goBack()
        } else {
            super.onBackPressed()
        }
    }

    override fun attachBaseContext(newBase: Context?) {
        if (newBase!=null) {
            super.attachBaseContext(CommonMethod.updateLanguage(newBase))
        } else {
            super.attachBaseContext(newBase)
        }
    }





}

