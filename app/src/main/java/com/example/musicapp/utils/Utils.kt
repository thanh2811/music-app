package com.example.musicapp.utils

object Utils {
    fun formatDuration(duration: Int): String{
        val min = duration / (60*1000)
        val sec = (duration -  min * 60 * 1000) / 1000
        return min.toString().padStart(2, '0') + ':' + sec.toString().padStart(2, '0')
    }
}