package com.example.musicapp.presenter

import android.content.Context
import androidx.annotation.RawRes

interface PlayerContract {
    fun resume()
    fun pause()
    fun release()
    fun prepare()
    fun next()
    fun previous()
    fun setDataResource()
    fun playSound(context: Context, @RawRes rawResId: Int)
    fun getDuration(): Int
    fun seekTo(progress: Int)
    fun isLooping(): Boolean

    interface ViewCallback{
        fun onNext()
    }
}