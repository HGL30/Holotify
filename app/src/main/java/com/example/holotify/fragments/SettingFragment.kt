package com.example.holotify.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.example.holotify.R

class SettingFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_setting, container, false)
    }
}