package com.skithub.resultdear.model

import com.google.gson.annotations.SerializedName

data class LotteryPdfModel(
    @SerializedName("Id") val id : String?,
    @SerializedName("Title") val title : String?,
    @SerializedName("PdfUrl") val pdfUrl : String?,
    @SerializedName("ImageUrl") val imageUrl : String?,
    @SerializedName("DayName") val dayName : String?,
    @SerializedName("ResultTime") val resultTime : String?,
    @SerializedName("ResultDate") val resultDate : String?,
)
