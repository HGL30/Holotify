package com.example.holotify.api

import android.webkit.CookieManager
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "http://10.0.2.2:5000" // Android emulator dùng 10.0.2.2 để gọi localhost PC
    private const val LARAVEL_URL = "" // IP máy chạy Laravel

    val api: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    // Lấy CSRF Token từ cookie
    private val csrfInterceptor = Interceptor { chain ->
        val cookies = CookieManager.getInstance().getCookie("")
        val csrfToken = cookies?.split(";")?.firstOrNull { it.contains("XSRF-TOKEN") }
            ?.split("=")?.get(1)

        val request = chain.request().newBuilder()
            .addHeader("X-XSRF-TOKEN", csrfToken ?: "") // Thêm CSRF token vào header
            .build()

        chain.proceed(request)
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(csrfInterceptor)  // Thêm interceptor vào OkHttpClient
        .build()

    val instance: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(LARAVEL_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}
