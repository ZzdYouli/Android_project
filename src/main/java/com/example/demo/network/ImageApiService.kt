package com.example.demo.network

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ImageApiService {

    // 示例API：动态获取不同高度的图片
    @GET("400/{length}")
    suspend fun getImage(@Path("length") length: Int): Response<ResponseBody>
}
