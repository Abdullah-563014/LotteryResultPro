package com.skithub.resultdear.ui.GridLayout.get_help

import android.content.ClipboardManager
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.skithub.resultdear.R
import com.skithub.resultdear.databinding.ActivityToGetHelpBinding

class To_Get_HelpActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityToGetHelpBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityToGetHelpBinding.inflate(layoutInflater)
        setContentView(binding.root)



        initAll()



    }


    private fun initAll() {
        binding.mailAddressTextView.text="Email:- ${resources.getString(R.string.support_email)}"
        binding.sendMailButton.setOnClickListener(this)
    }

    private fun sendMail() {
        val mailingIntent=Intent(Intent.ACTION_SEND)
        mailingIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(resources.getString(R.string.support_email)))
        mailingIntent.putExtra(Intent.EXTRA_SUBJECT,resources.getString(R.string.support_email_subject))
        mailingIntent.putExtra(Intent.EXTRA_TEXT,resources.getString(R.string.support_email_message))
        mailingIntent.type="message/rfc822"
        startActivity(Intent.createChooser(mailingIntent,"Choose an Email client :"))
    }

    override fun onClick(v: View?) {
        v?.let {
            when (it.id) {
                R.id.sendMailButton -> sendMail()
            }
        }
    }


}