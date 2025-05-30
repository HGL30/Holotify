package com.example.holotify.fragments

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.holotify.R
import com.example.holotify.adapters.GenreAdapter
import com.example.holotify.adapters.SongAdapter
import com.example.holotify.api.ApiService
import com.example.holotify.api.RetrofitClient
import com.example.holotify.databinding.FragmentFavoriteBinding
import com.example.holotify.databinding.FragmentHomeBinding
import com.example.holotify.repository.SongRepository
import kotlinx.coroutines.launch

class FavoriteFragment : Fragment() {
    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!
    private lateinit var apiService: ApiService

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        apiService = RetrofitClient.instance

        lifecycleScope.launch {
            try {
                val songList = apiService.getAllSongs()
                binding.rvSongs.layoutManager = LinearLayoutManager(requireContext())
                binding.rvSongs.adapter = SongAdapter(songList)
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(requireContext(), "Lỗi khi load dữ liệu!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
