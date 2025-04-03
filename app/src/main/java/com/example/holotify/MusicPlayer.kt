package com.example.holotify

import android.content.Context
import android.net.Uri
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.ExoPlayer

class MusicPlayer(private val context: Context) {
    private var player: ExoPlayer? = null

    fun initializePlayer(url: String) {
        player = ExoPlayer.Builder(context).build()
        val mediaItem = MediaItem.fromUri(Uri.parse(url))
        player?.setMediaItem(mediaItem)
        player?.prepare()
        player?.play()
    }

    fun releasePlayer() {
        player?.release()
        player = null
    }
}