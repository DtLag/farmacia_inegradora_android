package com.example.farmacia_inegradora_android.data

import com.example.farmacia_inegradora_android.api.PharmacyApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private const val URL_BASE = " https://sandee-tweediest-sasha.ngrok-free.dev"

    val myRetrofit by lazy {
        Retrofit.Builder().baseUrl(URL_BASE)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    val API : PharmacyApi by lazy {
        myRetrofit.create(PharmacyApi::class.java)
    }
}
