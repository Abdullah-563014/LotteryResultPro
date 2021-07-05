package com.skithub.resultdear.ui.pdf_info

import android.Manifest
import android.app.DownloadManager
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.webkit.URLUtil
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.github.chrisbanes.photoview.PhotoView
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.leinardi.android.speeddial.SpeedDialActionItem
import com.leinardi.android.speeddial.SpeedDialView
import com.skithub.resultdear.R
import com.skithub.resultdear.databinding.ActivityPdfInfoBinding
import com.skithub.resultdear.model.LotteryPdfModel
import com.skithub.resultdear.ui.MyApplication
import com.skithub.resultdear.utils.CommonMethod
import com.skithub.resultdear.utils.Constants
import com.skithub.resultdear.utils.Coroutines
import com.skithub.resultdear.utils.MyExtensions.shortToast
import com.squareup.picasso.Picasso
import java.io.BufferedInputStream
import java.net.HttpURLConnection
import java.net.URL


class PdfInfoActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityPdfInfoBinding
    private lateinit var viewModel: PdfInfoViewModel
    private var resultDate: String=CommonMethod.increaseDecreaseDaysUsingValue(0)
    private var resultTime: String=Constants.eveningTime
    private var resultDateTwo: String=CommonMethod.increaseDecreaseDaysUsingValue(-2)
    private var list: MutableList<LotteryPdfModel> = arrayListOf()
    private lateinit var circularZoomImageView: PhotoView
    private var zoomImageAlertDialog: AlertDialog?=null
    private var isVersusResult: Boolean=false
    private var downloadingFileName: String="image"



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityPdfInfoBinding.inflate(layoutInflater)
        val factory: PdfInfoViewModelFactory= PdfInfoViewModelFactory((application as MyApplication).myApi)
        viewModel=ViewModelProvider(this,factory).get(PdfInfoViewModel::class.java)
        setContentView(binding.root)


        val bundle=intent.extras
        if (bundle!=null) {
            resultDate=bundle.getString(Constants.resultDateKey,CommonMethod.increaseDecreaseDaysUsingValue(0))
            resultTime=bundle.getString(Constants.resultTimeKey,Constants.morningTime)
            isVersusResult=bundle.getBoolean(Constants.isVersusResultKey,false)
            if (!isVersusResult) {
                resultDateTwo=resultDate
            }
        }

        initAll()

        initZoomImageAlertDialog()

        setupFloatingActionButton()

        loadImageInfoUsingDateAndTime()








    }


    private fun initAll() {
        binding.pdfViewSwipeRefreshLayout.setOnRefreshListener {
            binding.pdfViewSwipeRefreshLayout.isRefreshing=false
            loadImageInfoUsingDateAndTime()
        }
        binding.resultOneImageView.setOnClickListener(this)
        binding.resultTwoImageView.setOnClickListener(this)
    }

    private fun setupFloatingActionButton() {
        binding.speedDial.addActionItem(SpeedDialActionItem.Builder(R.id.download_type_pdf,R.drawable.ic_download)
            .setFabBackgroundColor(ResourcesCompat.getColor(resources,R.color.red,theme))
            .setFabImageTintColor(ResourcesCompat.getColor(resources,R.color.white,theme))
            .setLabel(R.string.download_pdf)
            .setLabelColor(ResourcesCompat.getColor(resources,R.color.red,theme))
            .setLabelBackgroundColor(ResourcesCompat.getColor(resources,R.color.white,theme))
            .setLabelClickable(true)
            .create())
        binding.speedDial.addActionItem(SpeedDialActionItem.Builder(R.id.download_type_image,R.drawable.ic_download)
            .setFabBackgroundColor(ResourcesCompat.getColor(resources,R.color.red,theme))
            .setFabImageTintColor(ResourcesCompat.getColor(resources,R.color.white,theme))
            .setLabel(R.string.download_image)
            .setLabelColor(ResourcesCompat.getColor(resources,R.color.red,theme))
            .setLabelBackgroundColor(ResourcesCompat.getColor(resources,R.color.white,theme))
            .setLabelClickable(true)
            .create())
        binding.speedDial.setOnActionSelectedListener(object : SpeedDialView.OnActionSelectedListener{
            override fun onActionSelected(actionItem: SpeedDialActionItem?): Boolean {
                actionItem?.let {
                    when (it.id) {
                        R.id.download_type_pdf -> {
                            downloadingFileName="pdf"
                            if (Build.VERSION.SDK_INT>=23 && (ActivityCompat.checkSelfPermission(this@PdfInfoActivity,Manifest.permission.WRITE_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED)) {
                                showPermissionDialog()
                                return true
                            }
                            if (list.size > 0) {
                                for (i in 0 until list.size) {
                                    downloadFile(list[i].pdfUrl!!)
                                }
                            }
                            binding.speedDial.close()
                            return true
                        }
                        R.id.download_type_image -> {
                            downloadingFileName="image"
                            if (Build.VERSION.SDK_INT>=23 && (ActivityCompat.checkSelfPermission(this@PdfInfoActivity,Manifest.permission.WRITE_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED)) {
                                showPermissionDialog()
                                return true
                            }
                            if (list.size > 0) {
                                for (i in 0 until list.size) {
                                    downloadFile(list[i].imageUrl!!)
                                }
                            }
                            binding.speedDial.close()
                            return true
                        }
                        else -> return false
                    }
                }
                return false
            }
        })
    }

    private fun downloadFile(fileUrl: String) {
        val fileName =URLUtil.guessFileName(fileUrl,null,CommonMethod.getMimeTypeFromUrl(fileUrl))
        val downloadManager= getSystemService(DOWNLOAD_SERVICE) as DownloadManager
        val request: DownloadManager.Request=DownloadManager.Request(Uri.parse(fileUrl))
        if (Build.VERSION.SDK_INT < 29) {
            request.allowScanningByMediaScanner()
        }
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,fileName)
        request.setTitle(fileName)
        request.setDescription(fileName)
        request.setMimeType(CommonMethod.getMimeTypeFromUrl(fileUrl))
        downloadManager.enqueue(request)
        shortToast("downloading started")
    }

    private fun loadImageInfoUsingDateAndTime() {
        Coroutines.main {
            binding.spinKit.visibility=View.VISIBLE
            binding.resultRootLayout.visibility=View.GONE
            binding.waitingRootLayout.visibility=View.GONE
            binding.speedDial.visibility=View.GONE

            val response=viewModel.lotteryInfoByDateAndTime(resultDate,resultTime, resultDateTwo)
            if (response.isSuccessful && response.code()==200) {
                if (response.body()!=null) {
                    if (response.body()?.status.equals("success")) {
                        list.clear()
                        list.addAll(response.body()?.data!!)
                        if (list[0]!=null && list[0].imageUrl!=null) {
                            showImageOne(list[0].imageUrl!!)
                        } else {
                            binding.resultRootLayout.visibility=View.GONE
                            binding.spinKit.visibility=View.GONE
                        }
                        if (isVersusResult && list[1]!=null && list[1].imageUrl!=null) {
                            showImageTwo(list[1].imageUrl!!)
                        } else {
                            binding.resultTwoRootLayout.visibility=View.GONE
                        }
                    } else {
                        binding.spinKit.visibility=View.GONE
                        binding.resultRootLayout.visibility=View.GONE
                        binding.waitingRootLayout.visibility=View.VISIBLE
                        shortToast("${response.body()?.message}")
                    }
                } else {
                    binding.spinKit.visibility=View.GONE
                    binding.resultRootLayout.visibility=View.GONE
                    binding.waitingRootLayout.visibility=View.VISIBLE
                    shortToast("Sorry, Unknown error occurred.")
                }
            } else {
                binding.spinKit.visibility=View.GONE
                binding.resultRootLayout.visibility=View.GONE
                binding.waitingRootLayout.visibility=View.VISIBLE
                shortToast("failed for:- ${response.errorBody()?.string()}")
            }
        }
    }

    private fun showImageOne(imageUrl: String) {
        binding.spinKit.visibility=View.GONE
        binding.speedDial.visibility=View.VISIBLE
        binding.resultRootLayout.visibility=View.VISIBLE
        binding.resultOneRootLayout.visibility=View.VISIBLE
        binding.waitingRootLayout.visibility=View.GONE
        binding.resultOneTitleTextView.text="Result $resultDate ${CommonMethod.getDayNameUsingDate(resultDate)}"
        Glide.with(this).load(imageUrl).fitCenter().into(binding.resultOneImageView)
    }

    private fun showImageTwo(imageUrl: String) {
        binding.speedDial.visibility=View.VISIBLE
        binding.resultRootLayout.visibility=View.VISIBLE
        binding.resultTwoRootLayout.visibility=View.VISIBLE
        binding.waitingRootLayout.visibility=View.GONE
        binding.resultTwoTitleTextView.text="Result $resultDateTwo ${CommonMethod.getDayNameUsingDate(resultDateTwo)}"
        Glide.with(this).load(imageUrl).fitCenter().into(binding.resultTwoImageView)
    }

    private fun initZoomImageAlertDialog() {
        val view: View = LayoutInflater.from(this).inflate(R.layout.image_zoom_custom_layout, null, false)
        val closeImageView: ImageView = view.findViewById(R.id.circularZoomCloseImageView)
        circularZoomImageView = view.findViewById<PhotoView>(R.id.circularZoomImageView)
        closeImageView.setOnClickListener(this)
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
            .setCancelable(true)
            .setView(view)
        zoomImageAlertDialog = builder.create()
    }

    private fun showZoomImageAlertDialog(imageUrl: String) {
        if (zoomImageAlertDialog != null && !isFinishing) {
            Picasso.get().load(imageUrl).into(circularZoomImageView)
            zoomImageAlertDialog?.show()
            val displayWidth: Int = CommonMethod.getScreenWidth(this)
            val displayHeight: Int = CommonMethod.getScreenHeight(this)
            val lp = WindowManager.LayoutParams()
            lp.copyFrom(zoomImageAlertDialog?.window?.attributes)
            lp.width = displayWidth
            lp.height = displayHeight
            zoomImageAlertDialog?.window?.attributes = lp
            val layoutParams: ViewGroup.LayoutParams = circularZoomImageView.layoutParams
            layoutParams.height = (displayHeight * 0.80).toInt()
            circularZoomImageView.layoutParams = layoutParams
        }
    }

    private fun showPermissionDialog() {
        Dexter.withContext(this)
            .withPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            .withListener(object : MultiplePermissionsListener{
                override fun onPermissionsChecked(p0: MultiplePermissionsReport?) {
                    if (p0!=null && p0.areAllPermissionsGranted()) {
                        if (downloadingFileName.equals("pdf",true)) {
                            if (list.size > 0) {
                                for (i in 0 until list.size) {
                                    downloadFile(list[i].pdfUrl!!)
                                }
                            }
                        } else {
                            if (list.size > 0) {
                                for (i in 0 until list.size) {
                                    downloadFile(list[i].imageUrl!!)
                                }
                            }
                        }
                    }
                }

                override fun onPermissionRationaleShouldBeShown(
                    p0: MutableList<PermissionRequest>?,
                    p1: PermissionToken?
                ) {
                    p1?.continuePermissionRequest()
                }

            })
            .check()
    }

    override fun onClick(v: View?) {
        v?.let {
            when (it.id) {
                R.id.circularZoomCloseImageView -> zoomImageAlertDialog?.dismiss()
                R.id.resultOneImageView -> showZoomImageAlertDialog(list[0].imageUrl!!)
                R.id.resultTwoImageView -> showZoomImageAlertDialog(list[1].imageUrl!!)
                else -> return
            }
        }
    }


}