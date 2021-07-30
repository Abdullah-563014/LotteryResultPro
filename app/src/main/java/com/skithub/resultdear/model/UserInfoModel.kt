package com.skithub.resultdear.model

import com.google.gson.annotations.SerializedName

data class UserInfoModel(
    @SerializedName("Id") val id : String?,
    @SerializedName("Token") val token : String?,
    @SerializedName("Phone") val phone : String?,
    @SerializedName("RegistrationDate") val registrationDate : String?,
    @SerializedName("ActiveStatus") val activeStatus : String?
)
