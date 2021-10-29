package com.example.nisumtest.data.model

import com.google.gson.annotations.SerializedName

data class Abbreviation(
    @SerializedName("sf")
    val sf: String,
    @SerializedName("lfs")
    val lfs: List<Lf>
)
