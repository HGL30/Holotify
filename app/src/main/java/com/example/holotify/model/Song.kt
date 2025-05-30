package com.example.holotify.model
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Song(
    var id: Int,
    var title: String,
    var artist: String,
    var album: String,
    var genre: String,
    var audio_url: String,
    var image_url: String,
    var isFavorite: Boolean = false
) : Parcelable
