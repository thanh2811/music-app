package com.example.musicapp.ui

import android.content.Context
import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.musicapp.R
import com.example.musicapp.data.model.PlayerDetail
import com.example.musicapp.presenter.PlayerChangeListener
import com.example.musicapp.ui.MediaListAdapter.MediaViewHolder

class MediaListAdapter(
    private val context: Context,
    private val mediaList: List<PlayerDetail>,
    private val playerChangeListener: PlayerChangeListener,
    private var playingIndex: Int = 0
): RecyclerView.Adapter<MediaViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MediaViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_sound, parent, false)
        return MediaViewHolder(view)
    }

    override fun onBindViewHolder(holder: MediaViewHolder, position: Int) {
        holder.bind(mediaList[position], context)
        holder.itemView.setOnClickListener {
            playerChangeListener.onMediaItemChange(position)
            it.isSelected = true
            playingIndex = position
            notifyDataSetChanged()
        }

        holder.itemView.isSelected = playingIndex == position


    }

    override fun getItemCount(): Int = mediaList.size

    class MediaViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val tvName = itemView.findViewById<TextView>(R.id.tv_sound_name)
        private val tvAuthor = itemView.findViewById<TextView>(R.id.tv_sound_author)
        private val ivThumb = itemView.findViewById<ImageView>(R.id.iv_thumb)

        fun bind(playerDetail: PlayerDetail, context: Context) {
            tvName.text = playerDetail.name
            tvAuthor.text = playerDetail.singer
            Glide.with(context)
                .load(playerDetail.thumb)
                .into(ivThumb)
        }
    }
}