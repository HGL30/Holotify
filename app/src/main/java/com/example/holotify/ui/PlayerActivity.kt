package com.example.holotify.ui

import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.holotify.databinding.ActivityPlayerBinding
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem

class PlayerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPlayerBinding
    private var player: ExoPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val songUrl = intent.getStringExtra("SONG_URL") ?: return

        player = ExoPlayer.Builder(this).build()
        binding.playerView.player = player

        val mediaItem = MediaItem.fromUri(Uri.parse(songUrl))
        player?.setMediaItem(mediaItem)
        player?.prepare()
        player?.play()

        binding.btnPlay.setOnClickListener { player?.play() }
        binding.btnPause.setOnClickListener { player?.pause() }
    }

    override fun onDestroy() {
        super.onDestroy()
        player?.release()
    }
}