package com.skithub.resultdear.adapter

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.skithub.resultdear.R
import com.skithub.resultdear.databinding.AdsImageViewLayoutBinding
import com.skithub.resultdear.databinding.LotteryResultRecyclerViewModelLayoutBinding
import com.skithub.resultdear.model.LotteryNumberModel
import com.skithub.resultdear.model.LotteryResultRecyclerModel
import com.skithub.resultdear.utils.Constants

class LotteryResultRecyclerAdapter(val context: Context, val list: MutableList<LotteryResultRecyclerModel>, val adsImageUrl: String, val adsTargetUrl: String): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val lotteryViewType: Int=0
    private val imageViewType: Int=1
    private val adsImagePosition: Int=3

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType==lotteryViewType) {
            val binding=LotteryResultRecyclerViewModelLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
            return LotteryResultRecyclerViewHolder(binding)
        } else {
            val binding=AdsImageViewLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
            return LotteryResultRecyclerImageViewHolder(binding)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (position<adsImagePosition) {
            (holder as LotteryResultRecyclerViewHolder).bind(list[position])
        } else if (position==adsImagePosition) {
            (holder as LotteryResultRecyclerImageViewHolder).bind(adsImageUrl,adsTargetUrl)
        } else if (position>adsImagePosition){
            (holder as LotteryResultRecyclerViewHolder).bind(list[position-1])
        }
    }

    override fun getItemCount(): Int {
        if (list.size>0) {
            return list.size+1
        }
        return list.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (position==3) {
            imageViewType
        } else {
            lotteryViewType
        }
    }

    inner class LotteryResultRecyclerViewHolder(val binding: LotteryResultRecyclerViewModelLayoutBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(item: LotteryResultRecyclerModel) {
            try {
                if (item.winType.equals(Constants.winTypeFirst)) {
                    binding.resultTypeTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP,20.0f)
                    binding.resultTypeTextView.setTextColor(Color.parseColor("#07E0FB"))
                }
                binding.resultTypeTextView.text="${item.winType} Prize \u20B9 ${getPrizeAmount(item.winType)}"
                val layoutManager: GridLayoutManager= GridLayoutManager(context,7)
                val childList: MutableList<LotteryNumberModel> =item.data!!
                childList.sortBy {
                    it.lotteryNumber
                }
                val adapter: LotteryResultChildRecyclerAdapter=LotteryResultChildRecyclerAdapter(context,childList)
                layoutManager.spanSizeLookup= object : GridLayoutManager.SpanSizeLookup() {
                    override fun getSpanSize(position: Int): Int {
                        return if (adapter.getItemViewType(position)==0) {
                            7
                        } else {
                            1
                        }
                    }
                }
                binding.resultChildRecyclerView.layoutManager=layoutManager
                binding.resultChildRecyclerView.adapter=adapter
            } catch (e: Exception) {

            }
        }
    }

    inner class LotteryResultRecyclerImageViewHolder(val binding: AdsImageViewLayoutBinding): RecyclerView.ViewHolder(binding.root),
        View.OnClickListener {

        fun bind(imageUrl: String, targetUrl: String) {
            try {
                if (adsImageUrl.isNullOrEmpty()) {
                    binding.lotteryImageView.visibility=View.GONE
                } else {
                    binding.lotteryImageView.visibility=View.VISIBLE
                    Glide.with(context).load(imageUrl).centerCrop().placeholder(R.drawable.loading_placeholder).into(binding.lotteryImageView)
                    binding.lotteryImageView.setOnClickListener(this)
                }
            } catch (e: Exception) {

            }
        }

        override fun onClick(v: View?) {
            v?.let {
                when (it.id) {
                    R.id.lotteryImageView -> {
                        val targetIntent=Intent(Intent.ACTION_VIEW, Uri.parse(adsTargetUrl))
                        context.startActivity(Intent.createChooser(targetIntent,"Choose one:"))
                    }
                }
            }
        }
    }


    private fun getPrizeAmount(type: String?): String {
        if (type.isNullOrEmpty()) {
            return "Amount not detectable"
        } else {
            if (type.equals(Constants.winTypeFirst)) {
                return "1 Crore"
            } else if (type.equals(Constants.winTypeSecond)) {
                return "9,000/-"
            } else if (type.equals(Constants.winTypeThird)) {
                return "500/-"
            } else if (type.equals(Constants.winTypeFourth)) {
                return "250/-"
            } else if (type.equals(Constants.winTypeFifth)) {
                return "120/-"
            }
        }
        return "Amount not detectable"
    }


}