package com.skithub.resultdear.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.skithub.resultdear.R
import com.skithub.resultdear.databinding.OldResultRecyclerViewModelLayoutBinding
import com.skithub.resultdear.model.LotteryPdfModel
import com.skithub.resultdear.ui.result_image_info.ResultImageInfoActivity
import com.skithub.resultdear.utils.Constants

class OldResultRecyclerAdapter(val context: Context, val list: MutableList<LotteryPdfModel>): RecyclerView.Adapter<OldResultRecyclerAdapter.OldResultRecyclerViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OldResultRecyclerViewHolder {
        val binding=OldResultRecyclerViewModelLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return OldResultRecyclerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OldResultRecyclerViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }


    inner class OldResultRecyclerViewHolder(val binding: OldResultRecyclerViewModelLayoutBinding): RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        fun bind(item: LotteryPdfModel) {
            binding.dateTextView.text=item.resultDate
            binding.timeTextView.text=item.resultTime
            binding.dayNameTextView.text=item.dayName
            binding.oldResultRootLayout.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            v?.let {
                val oldResultIntent= Intent(context,ResultImageInfoActivity::class.java)
                when (it.id) {
                    R.id.oldResultRootLayout -> {
                        oldResultIntent.putExtra(Constants.resultTimeKey,list[adapterPosition].resultTime)
                        oldResultIntent.putExtra(Constants.resultDateKey,list[adapterPosition].resultDate)
                        oldResultIntent.putExtra(Constants.isVersusResultKey,false)
                        context.startActivity(oldResultIntent)
                    }
                }
            }
        }
    }



}