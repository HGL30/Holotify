package com.example.holotify.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.holotify.databinding.ItemGenreBinding
import com.example.holotify.model.GenreGroup

class GenreAdapter(private val genreList: List<GenreGroup>) :
    RecyclerView.Adapter<GenreAdapter.GenreViewHolder>() {

    inner class GenreViewHolder(val binding: ItemGenreBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreViewHolder {
        val binding = ItemGenreBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GenreViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GenreViewHolder, position: Int) {
        val genreGroup = genreList[position]
        holder.binding.tvGenre.text = genreGroup.genre

        val songAdapter = SongAdapter(genreGroup.songs)
        holder.binding.rvSongs.layoutManager = LinearLayoutManager(holder.itemView.context)
        holder.binding.rvSongs.adapter = songAdapter
    }

    override fun getItemCount(): Int = genreList.size
}

