package com.example.nisumtest.network

import com.example.nisumtest.data.model.Abbreviation
import com.example.nisumtest.data.model.Some
import com.google.gson.JsonArray
import retrofit2.http.GET
import retrofit2.http.Query

interface AbbreviationClient {

    @GET("software/acromine/dictionary.py")
    suspend fun getAbbreviation(@Query("sf") search: String): JsonArray
}