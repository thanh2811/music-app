package com.example.musicapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.musicapp.MainActivity
import com.example.musicapp.R
import com.example.musicapp.data.model.PlayerDetail
import com.example.musicapp.data.repository.PlayerRepository
import com.example.musicapp.databinding.FragmentMediaListBinding
import com.example.musicapp.presenter.MediaPlayerPresenter
import com.example.musicapp.presenter.PlayerChangeListener

class MediaListFragment : Fragment() {

    private lateinit var binding: FragmentMediaListBinding


    private val playerChangeListener = object : PlayerChangeListener{
        override fun onMediaItemChange(id: Int) {
            (activity as MainActivity).changeMediaItem(id)
        }

    }
    private lateinit var mediaListAdapter: MediaListAdapter
    private lateinit var mediaList: List<PlayerDetail>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentMediaListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
        initView()
    }

    private fun initData() {
        mediaList = PlayerRepository.loadData()
        mediaListAdapter = MediaListAdapter(requireContext(), mediaList, playerChangeListener)
    }

    private fun initView() {
        binding.rvMedia.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.rvMedia.adapter = mediaListAdapter
    }



}