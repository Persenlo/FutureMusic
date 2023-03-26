package com.pslpro.futuremusic.api

import android.app.Application
import android.util.Log
import com.pslpro.futuremusic.BuildConfig
import com.pslpro.futuremusic.service.MusicPlayerManager
import com.pslpro.futuremusic.utils.cookieStore.CookieJarImpl
import com.pslpro.futuremusic.utils.cookieStore.CookieStore
import com.pslpro.futuremusic.utils.cookieStore.PersistentCookieStore
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {

    var BASE_URL = ""

    lateinit var mApplication: Application




    private val instance: Retrofit by lazy {

        //声明日志类
        val httpLoggingInterceptor = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger() {
            Log.d("NetWork","Network: $it")
        })
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        val okhttpClient = OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .cookieJar(CookieJarImpl(PersistentCookieStore(mApplication)))
            .connectTimeout(5, TimeUnit.SECONDS)//设置超时时间
            .retryOnConnectionFailure(true).build()

        Retrofit.Builder()
            .client(okhttpClient)
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }



    fun <T> createApi(clazz: Class<T>): T {
        return instance.create(clazz) as T
    }

    @JvmStatic
    fun init(application: Application) {
        mApplication = application
    }

    @JvmStatic
    fun setBaseUrl(url: String) {
        BASE_URL = url
    }
}


