package com.example.holotify.fragments

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.holotify.model.Song
import com.example.holotify.repository.SongRepository
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: SongRepository) : ViewModel() {

//    private val _songs = MutableLiveData<List<Song>>()
//    val songs: LiveData<List<Song>> = _songs
//
//    fun fetchSongs() {
//        viewModelScope.launch {
//            try {
//                _songs.value = repository.getAllSongs()
//            } catch (e: Exception) {
//                Log.e("HomeViewModel", "Error: ${e.message}")
//            }
//        }
//    }
}
