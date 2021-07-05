package com.skithub.resultdear.ui.GridLayout.claim_form

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.skithub.resultdear.R
import com.skithub.resultdear.databinding.ActivityClaimFormBinding
import com.skithub.resultdear.ui.GridLayout.old_result.OldResultViewModel
import com.skithub.resultdear.ui.GridLayout.old_result.OldResultViewModelFactory
import com.skithub.resultdear.ui.MyApplication

class Claim_formActivity : AppCompatActivity() {

    private lateinit var binding: ActivityClaimFormBinding
    private lateinit var viewModel: ClaimFormViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityClaimFormBinding.inflate(layoutInflater)
        val factory: ClaimFormViewModelFactory = ClaimFormViewModelFactory((application as MyApplication).myApi)
        viewModel= ViewModelProvider(this,factory).get(ClaimFormViewModel::class.java)
        setContentView(binding.root)







    }













}