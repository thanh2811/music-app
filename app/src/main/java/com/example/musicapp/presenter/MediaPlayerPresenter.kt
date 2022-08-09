package com.example.musicapp.presenter

import android.content.Context
import android.media.MediaPlayer
import android.util.Log
import androidx.annotation.RawRes

class MediaPlayerPresenter
    : PlayerContract {

    var viewCallback: PlayerContract.ViewCallback? = null
        set(value){
            field = value
        }

    var mediaPlayer: MediaPlayer? = null
        set(value){
            field = value
            value?.apply {
                setOnPreparedListener {
                    Log.d("OnPreparedListener", it.toString() )
                    start()
                }
                setOnCompletionListener {
                    Log.d("OnCompletionListener", it.toString())
                    viewCallback?.onNext()
                }
            }
        }

    override fun resume() {
        mediaPlayer?.seekTo(mediaPlayer!!.currentPosition)
        mediaPlayer?.start()
    }

    override fun pause() {
        mediaPlayer?.pause()
    }

    override fun release() {
        mediaPlayer?.release()
    }

    override fun prepare() {
        mediaPlayer?.prepare()
    }

    override fun next() {

    }

    override fun previous() {

    }

    override fun setDataResource() {

    }

    override fun playSound(context: Context, @RawRes rawResId: Int) {
        val assetFileDescriptor = context.resources.openRawResourceFd(rawResId) ?: return
        mediaPlayer?.run {
            reset()
            setDataSource(assetFileDescriptor.fileDescriptor, assetFileDescriptor.startOffset, assetFileDescriptor.declaredLength)
            prepare()
        }
    }

    override fun getDuration(): Int {
        return mediaPlayer!!.duration
    }

    override fun seekTo(progress: Int) {
        mediaPlayer?.seekTo(progress)
    }

    override fun isLooping(): Boolean {
        return mediaPlayer!!.isLooping
    }
}