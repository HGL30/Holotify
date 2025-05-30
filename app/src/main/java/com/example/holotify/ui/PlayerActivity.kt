package com.example.holotify.ui

import android.graphics.Color
import android.media.MediaPlayer
import android.os.Bundle
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.holotify.databinding.ActivityPlayerBinding
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.example.holotify.R
import com.example.holotify.api.FavoriteRequest
import com.example.holotify.api.RetrofitClient
import com.example.holotify.model.Song
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PlayerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPlayerBinding
    private var mediaPlayer: MediaPlayer? = null
    private var isPlaying = false
    private var songDuration = 0
    private lateinit var tvCurrentTime: TextView
    private lateinit var tvDuration: TextView
    private val handler = Handler(Looper.getMainLooper())
    private lateinit var currentSong: Song
    private lateinit var songList: ArrayList<Song>
    private var currentIndex: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val songUrl = intent.getStringExtra("audio_url")
        val imageUrl = intent.getStringExtra("song_image")
        val songTitle = intent.getStringExtra("song_title")
        val songArtist = intent.getStringExtra("song_artist")
        val songId = intent.getIntExtra("song_id", 0)
        val isFavorite = intent.getBooleanExtra("is_favorite", false)

        @Suppress("DEPRECATION") // Nếu bạn vẫn target < 33
        songList = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableArrayListExtra("song_list", Song::class.java) ?: arrayListOf()
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableArrayListExtra("song_list") ?: arrayListOf()
        }
        currentIndex = intent.getIntExtra("current_index", 0)

        if (songList.isEmpty()) {
            Toast.makeText(this, "Danh sách bài hát trống", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

//        currentSong = Song(
//            id = intent.getIntExtra("song_id", 0),
//            title = songTitle ?: "",
//            artist = songArtist ?: "",
//            audio_url = songUrl ?: "",
//            image_url = imageUrl ?: "",
//            album = "",
//            genre = "",
//            isFavorite = isFavorite
//        )

        // Set thông tin bài hát
        binding.tvSongTitle.text = songTitle
        binding.tvSongArtist.text = songArtist
        Glide.with(this).load(imageUrl).into(binding.imgCover)

        // SeekBar màu trắng
        binding.seekBar.progressTintList = android.content.res.ColorStateList.valueOf(Color.WHITE)
        binding.seekBar.thumbTintList = android.content.res.ColorStateList.valueOf(Color.WHITE)

        // Set up media player
//        if (songUrl != null) {
//            setupPlayer(songUrl)
//        } else {
//            Toast.makeText(this, "Không tìm thấy URL bài hát!", Toast.LENGTH_SHORT).show()
//            finish()
//        }

        loadSongAt(currentIndex)

        // Nút phát / dừng
        binding.btnPlayPause.setOnClickListener {
            if (isPlaying) {
                mediaPlayer?.pause()
                isPlaying = false
                binding.btnPlayPause.setImageResource(R.drawable.ic_play)
            } else {
                mediaPlayer?.start()
                isPlaying = true
                binding.btnPlayPause.setImageResource(R.drawable.ic_pause)
                startUpdatingSeekBar()
            }
        }

        // Nút quay lại
        binding.btnBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        // Xử lý tua nhạc
        binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser && songDuration > 0) {
                    val newPos = (songDuration * progress) / 100
                    mediaPlayer?.seekTo(newPos)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        binding.btnNext.setOnClickListener {
            if (currentIndex < songList.size - 1) {
                currentIndex++
                loadSongAt(currentIndex)
            } else {
                Toast.makeText(this, "Đã tới bài cuối cùng", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnPrevious.setOnClickListener {
            if (currentIndex > 0) {
                currentIndex--
                loadSongAt(currentIndex)
            } else {
                Toast.makeText(this, "Đây là bài đầu tiên rồi", Toast.LENGTH_SHORT).show()
            }
        }

        updateFavoriteIcon()

//        binding.ivFavorite.setOnClickListener {
//            toggleFavorite(currentSong)
//        }
    }

    private fun updateFavoriteIcon() {
        binding.ivFavorite.setImageResource(
            if (currentSong.isFavorite) R.drawable.ic_favorite_filled
            else R.drawable.ic_favorite_border
        )
    }

    private fun loadSongAt(index: Int) {
        val song = songList[index]
        currentSong = song

        binding.tvSongTitle.text = song.title
        binding.tvSongArtist.text = song.artist
        Glide.with(this).load(song.image_url).into(binding.imgCover)

        mediaPlayer?.release()
        mediaPlayer = null
        isPlaying = false

        setupPlayer(song.audio_url)
        updateFavoriteIcon()
    }

//    private fun toggleFavorite(song: Song) {
//        val userId = getUserIdFromSharedPreferences() // Lấy user_id
//        val request = FavoriteRequest(currentSong.id, userId)
//
//        CoroutineScope(Dispatchers.IO).launch {
//            Log.d("API Request", "URL: ${RetrofitClient.instance.toggleFavorite(request)}")
//            try {
//                RetrofitClient.instance.toggleFavorite(request)
//
//                withContext(Dispatchers.Main) {
//                    song.isFavorite = !song.isFavorite
//                    updateFavoriteIcon()
//                    Toast.makeText(
//                        this@PlayerActivity,
//                        if (song.isFavorite) "Đã thêm vào yêu thích" else "Đã bỏ yêu thích",
//                        Toast.LENGTH_SHORT
//                    ).show()
//                }
//            } catch (e: Exception) {
//                Log.e("API Error", e.message.toString())
//                withContext(Dispatchers.Main) {
//                    Toast.makeText(this@PlayerActivity, "Lỗi mạng rồi bro!", Toast.LENGTH_SHORT).show()
//                }
//            }
//        }
//    }

//    private fun getUserIdFromSharedPreferences(): Int {
//        val sharedPref = getSharedPreferences("MyPrefs", MODE_PRIVATE)
//        return sharedPref.getInt("user_id", 0)
//    }

    private fun setupPlayer(url: String) {
        mediaPlayer = MediaPlayer().apply {
            setDataSource(url)
            prepareAsync()
            setOnPreparedListener { player ->
                songDuration = player.duration
                player.start()
                this@PlayerActivity.isPlaying = true

                val minutes = songDuration / 1000 / 60
                val seconds = (songDuration / 1000) % 60
                binding.tvDuration.text = String.format("%02d:%02d", minutes, seconds)

                startUpdatingSeekBar()
            }
            setOnErrorListener { _, _, _ ->
                Toast.makeText(this@PlayerActivity, "Lỗi phát nhạc!", Toast.LENGTH_SHORT).show()
                true
            }
            setOnCompletionListener {
                if (currentIndex < songList.size - 1) {
                    currentIndex++
                    loadSongAt(currentIndex)
                } else {
                    Toast.makeText(this@PlayerActivity, "Hết danh sách bài hát!", Toast.LENGTH_SHORT).show()
                    binding.btnPlayPause.setImageResource(R.drawable.ic_play)
                    this@PlayerActivity.isPlaying = false
                }
                true
            }
        }
    }

    private fun startUpdatingSeekBar() {
        handler.postDelayed(object : Runnable {
            override fun run() {
                if (mediaPlayer != null && isPlaying) {
                    val currentPosition = mediaPlayer?.currentPosition ?: 0
                    val progress = if (songDuration > 0) (currentPosition * 100 / songDuration) else 0
                    binding.seekBar.progress = progress

                    // Update current time TextView
                    val minutes = currentPosition / 1000 / 60
                    val seconds = (currentPosition / 1000) % 60
                    binding.tvCurrentTime.text = String.format("%02d:%02d", minutes, seconds)

                    // Keep updating every second
                    handler.postDelayed(this, 1000)
                }
            }
        }, 1000)
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.release()
        mediaPlayer = null
        handler.removeCallbacksAndMessages(null)
    }
}
