package com.example.holotify.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.holotify.databinding.ItemSongBinding
import com.example.holotify.model.Song
import androidx.recyclerview.widget.ListAdapter
import com.example.holotify.ui.MusicPlayerManager
import com.example.holotify.ui.PlayerActivity

class SongAdapter(private val songs: List<Song>) : RecyclerView.Adapter<SongAdapter.SongViewHolder>() {

    inner class SongViewHolder(val binding: ItemSongBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        val binding = ItemSongBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SongViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        val song = songs[position]
        holder.binding.tvTitle.text = song.title
        holder.binding.tvArtist.text = song.artist
        Glide.with(holder.itemView.context).load(song.image_url).into(holder.binding.imgSong)

        holder.binding.root.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, PlayerActivity::class.java).apply {
                putExtra("song_title", song.title)
                putExtra("song_artist", song.artist)
                putExtra("audio_url", song.audio_url)
                putExtra("song_image", song.image_url)
                putExtra("song_id", song.id)
                putExtra("is_favorite", song.isFavorite)

                putParcelableArrayListExtra("song_list", ArrayList(songs))
                putExtra("current_index", position)
            }
            context.startActivity(intent)
        }
    }


    override fun getItemCount(): Int = songs.size
}


