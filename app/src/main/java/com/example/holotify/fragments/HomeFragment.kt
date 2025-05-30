package com.example.holotify.fragments

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.holotify.R
import com.example.holotify.adapters.SongAdapter
import com.example.holotify.databinding.FragmentHomeBinding
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.holotify.adapters.GenreAdapter
import com.example.holotify.api.ApiService
import com.example.holotify.api.RetrofitClient
import com.example.holotify.repository.SongRepository
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

//class HomeFragment : Fragment() {
//
//    private lateinit var binding: FragmentHomeBinding
//    private lateinit var viewModel: HomeViewModel
//    private lateinit var adapter: SongAdapter
//
//    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
//        binding = FragmentHomeBinding.inflate(inflater, container, false)
//        return binding.root
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        val repository = SongRepository(RetrofitClient.instance)
//        val factory = HomeViewModelFactory(repository)
//        viewModel = ViewModelProvider(this, factory)[HomeViewModel::class.java]
//
//        setupRecyclerView()
//
//        viewModel.songs.observe(viewLifecycleOwner) { songs ->
//            adapter.submitList(songs)
//        }
//
//        viewModel.fetchSongs()
//    }
//
//    private fun setupRecyclerView() {
//        adapter = SongAdapter()
//        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
//        binding.recyclerView.adapter = adapter
//    }
//}

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var apiService: ApiService

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        apiService = RetrofitClient.instance

        lifecycleScope.launch {
            try {
                val genreList = apiService.getSongsByGenre()
                binding.rvGenres.layoutManager = LinearLayoutManager(requireContext())
                binding.rvGenres.adapter = GenreAdapter(genreList)
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

