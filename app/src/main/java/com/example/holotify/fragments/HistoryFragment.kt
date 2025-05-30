package com.example.holotify.fragments

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.holotify.R
import com.example.holotify.adapters.SongAdapter
import com.example.holotify.api.ApiService
import com.example.holotify.api.RetrofitClient
import com.example.holotify.databinding.FragmentFavoriteBinding
import com.example.holotify.databinding.FragmentHistoryBinding
import kotlinx.coroutines.launch

class HistoryFragment : Fragment() {
    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!
    private lateinit var apiService: ApiService

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
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