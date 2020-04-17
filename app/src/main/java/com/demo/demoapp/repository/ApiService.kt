package com.demo.demoapp.repository


import com.demo.demoapp.listview.model.Response
import io.reactivex.Single
import retrofit2.http.GET


interface ApiService {
    @GET("facts.json")
    fun getDataCall(): Single<Response?>?
}