package com.demo.demoapp.repository

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.demo.demoapp.R
import com.demo.demoapp.listview.model.Response
import com.google.gson.Gson
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

open class ApiServiceFactory private constructor(var mContext: Context) {
   // var instance: BaseActivity? = null
    var apiService: ApiService? = null
    lateinit var retrofit: Retrofit

    init{
    try {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .readTimeout(30, TimeUnit.SECONDS)
                .connectTimeout(30, TimeUnit.SECONDS)
                //.sslSocketFactory(getSSLSocketFactory(mContext))
                .build()
        retrofit = Retrofit.Builder()
                .client(client)
                .baseUrl(ApiURL.BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .addConverterFactory(GsonConverterFactory.create(Gson()))
                .build()
        apiService = retrofit.create(ApiService::class.java)
      } catch (e: Exception) {
        e.printStackTrace()
        Log.d("Exception", e.message.toString())
     }
    }
    companion object {
        private var repository: ApiServiceFactory? = null
        @Synchronized
        fun getInstance(mContext: Context): ApiServiceFactory {
            if (repository == null) {
                repository = ApiServiceFactory(mContext)
            }
            return repository as ApiServiceFactory
        }
        fun relodInstance(mContext: Context) {
            repository = null
            getInstance(mContext)
        }
    }
    fun getData(): LiveData<Response?>? {
        val data: MutableLiveData<Response> = MutableLiveData()
        apiService?.getDataCall()
                  ?.subscribeOn(Schedulers.io())
                  ?.observeOn(AndroidSchedulers.mainThread())
                  ?.subscribe(Consumer<Response?> { call ->
                  //  CustomeProgressDialog(mContext).dismiss()
                    data!!.setValue(call)
                   val gson = Gson()
                      val jsonInString = gson.toJson(call)
                      Log.e("getLogincall", "In onNext()$jsonInString")
                  // disposable.dispose();
                }, Consumer<Throwable> { throwable ->
                    Log.e("throwable=", "" + throwable.message)
                    Toast.makeText(mContext, mContext.getString(R.string.something_went_wrong), Toast.LENGTH_SHORT
                    ).show()
                 //   CustomeProgressDialog(mContext).dismiss()
                    Log.e("getLogincall", "In onError()")
                })
        return data
    }
}