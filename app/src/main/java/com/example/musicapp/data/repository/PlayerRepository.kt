package com.example.musicapp.data.repository

import com.example.musicapp.R
import com.example.musicapp.data.model.PlayerDetail

object PlayerRepository {
    fun loadData(): List<PlayerDetail>{
        return listOf(
            PlayerDetail(1, "Chuyện đời", "https://i.ytimg.com/vi/gUtCfwXgDjI/maxresdefault.jpg", R.raw.chuyendoi, "JGKid"),
            PlayerDetail(2, "Đừng xin lỗi nữa", "https://i1.sndcdn.com/artworks-000287392175-czaxfi-t500x500.jpg", R.raw.dungxinloinua, "Erik"),
            PlayerDetail(3, "Phía sau em", "https://i.ytimg.com/vi/LklFoy_a3bA/maxresdefault.jpg", R.raw.phiasauem,"Kay Trần")
        )
    }
}