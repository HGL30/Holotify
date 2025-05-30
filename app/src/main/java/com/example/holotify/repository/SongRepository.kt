package com.example.holotify.repository

import com.example.holotify.api.ApiService
import com.example.holotify.api.RetrofitClient
import com.example.holotify.model.Song
import retrofit2.Response
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SongRepository(private var apiService: ApiService) {
//    suspend fun getAllSongs(): List<Song> = apiService.getAllSongs()
//    {
//        try {
//            val response = apiService.getAllSongs() // trả về SongResponse
//            response.songs
//        } catch (e: Exception) {
//            e.printStackTrace()
//            emptyList() // hoặc throw e nếu muốn bắt lỗi ở ViewModel
//        }
//    }
}
