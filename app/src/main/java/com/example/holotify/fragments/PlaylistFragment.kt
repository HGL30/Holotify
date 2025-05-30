package com.example.holotify.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.holotify.R
import com.example.holotify.adapters.PlaylistAdapter
import com.example.holotify.databinding.FragmentPlaylistBinding
import com.example.holotify.model.Playlist

class PlaylistFragment : Fragment() {

    private var _binding: FragmentPlaylistBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: PlaylistAdapter
    private lateinit var playlistList: List<Playlist>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPlaylistBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        playlistList = listOf(
            Playlist("Lo-fi Chill", 12, R.drawable.lofi),
            Playlist("Workout 🔥", 18, R.drawable.workout),
            Playlist("Học tập 🎓", 15, R.drawable.study),
            Playlist("Ballad Việt", 22, R.drawable.ballad),
        )

        adapter = PlaylistAdapter(playlistList)
        binding.rvPlaylists.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
