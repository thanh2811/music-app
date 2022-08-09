package com.example.musicapp.ui

import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.musicapp.MainActivity
import com.example.musicapp.R
import com.example.musicapp.data.model.PlayerDetail
import com.example.musicapp.data.repository.PlayerRepository
import com.example.musicapp.databinding.FragmentPlayerBinding
import com.example.musicapp.presenter.MediaPlayerPresenter
import com.example.musicapp.presenter.PlayerContract
import com.example.musicapp.utils.Utils
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class PlayerFragment : Fragment() {

    private lateinit var binding: FragmentPlayerBinding
    private var isPlaying = false
    private var currentIndex = 0
    private val mediaPlayerPresenter = MediaPlayerPresenter()
    private lateinit var mediaList: List<PlayerDetail>

    private val viewCallback = object : PlayerContract.ViewCallback{

        // auto next when player is complete
        override fun onNext() {
            if(currentIndex + 1 < 3)
                playItemAt(++currentIndex)
            else {
                currentIndex = 0
                playItemAt(0)
            }
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPlayerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
        initializeMediaPlayer()
        initView()
        initListener()
    }

    private fun initData() {
        mediaList = PlayerRepository.loadData()
    }

    private fun initView() {
        playItemAt(0)
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayerPresenter.release()
    }

    private fun initializeMediaPlayer() {
        mediaPlayerPresenter.mediaPlayer = MediaPlayer()
        mediaPlayerPresenter.viewCallback = viewCallback
    }

    private fun initListener() {

        binding.btPlay.setOnClickListener {
            Log.d("isPlaying", isPlaying.toString())
            if (!isPlaying) {
                mediaPlayerPresenter.resume()
                binding.btPlay.setImageResource(R.drawable.ic_baseline_pause_24)
            } else {
                mediaPlayerPresenter.pause()
                binding.btPlay.setImageResource(R.drawable.ic_baseline_play_arrow_24)
            }
            isPlaying = !isPlaying
        }

        binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(p0: SeekBar?, progress: Int, fromUser: Boolean) {
                if(fromUser)
                    mediaPlayerPresenter.seekTo(progress * 1000)
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {

            }

            override fun onStopTrackingTouch(p0: SeekBar?) {

            }

        })

        binding.btLoop.setOnClickListener {
            if(mediaPlayerPresenter.isLooping()) {
                mediaPlayerPresenter.mediaPlayer!!.isLooping = false
                binding.btLoop.background = null
            }
            else {
                mediaPlayerPresenter.mediaPlayer!!.isLooping = true
                binding.btLoop.background = resources.getDrawable(R.drawable.bt_enable, requireContext().theme)
            }
        }

        binding.btQueue.setOnClickListener {
            (activity as MainActivity).navigate(R.layout.fragment_media_list)
        }
        binding.btNext.setOnClickListener {
            if(currentIndex + 1 < 3)
               playItemAt(++currentIndex)
            else {
                currentIndex = 0
                playItemAt(0)
            }
        }

        binding.btPrevious.setOnClickListener {
            if(currentIndex - 1 >= 0)
                playItemAt(--currentIndex)
            else {
                currentIndex = 2
                playItemAt(2)
            }

        }

        viewLifecycleOwner.lifecycleScope.launch {
            while (true){
                val currentPosition = mediaPlayerPresenter.mediaPlayer?.currentPosition!! / 1000
                binding.seekBar.progress = currentPosition
                binding.tvCurrentPosition.text = Utils.formatDuration(currentPosition * 1000)
                delay(1000)
            }
        }

    }

    fun playItemAt(index: Int) {
        mediaPlayerPresenter.playSound(requireContext(), mediaList[index].resource)
        binding.tvDuration.text = Utils.formatDuration(mediaPlayerPresenter.getDuration())
        binding.btPlay.setImageResource(R.drawable.ic_baseline_pause_24)
        isPlaying = true
        binding.seekBar.max = mediaPlayerPresenter.getDuration()/1000

        Glide.with(requireContext())
            .load(mediaList[index].thumb)
            .into(binding.ivThumb)
        binding.tvSoundName.text = mediaList[index].name
        binding.tvSoundAuthor.text = mediaList[index].singer
    }

}