package com.example.holotify.api

import retrofit2.http.Body
import retrofit2.http.POST
import com.example.holotify.model.RegisterRequest
import com.example.holotify.model.LoginRequest
import com.example.holotify.model.ApiResponse
import com.example.holotify.model.GenreGroup
import com.example.holotify.model.LoginResponse
import com.example.holotify.model.Song
import retrofit2.Call
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query


interface ApiService {
    @POST("/auth/register")
    suspend fun register(@Body request: RegisterRequest): Response<ApiResponse>

    @POST("/auth/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    @GET("/songs")
    suspend fun getAllSongs(): List<Song>

    @GET("/songs/grouped")
    suspend fun getSongsByGenre(): List<GenreGroup>

    @POST("/favorites")
    suspend fun toggleFavorite(
        @Header("X-XSRF-TOKEN") csrfToken: String,
        @Body request: FavoriteRequest
    ): ResponseBody

    @GET("/favorite-songs")
    suspend fun getFavoriteSongs(@Query("user_id") userId: Int): List<Song>
}


data class FavoriteRequest(
    val song_id: Int,
    val user_id: Int
)
