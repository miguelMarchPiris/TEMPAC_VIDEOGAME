package edu.ub.pis2019.pis_16.tempac.Model

import android.app.Activity
import android.widget.Toast
import android.media.MediaPlayer
import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import edu.ub.pis2019.pis_16.tempac.R

class MusicService {

    companion object MusicPlayer{

        var btnPlayer : MediaPlayer? = null
        var mPlayer : MediaPlayer? = null
        var length = 0

        fun destroyReproducer() {
            if (mPlayer != null) {
                try {
                    mPlayer!!.stop()
                    mPlayer!!.release()
                } finally {
                    mPlayer = null
                }
            }
        }

        fun startMusicMenu(activity: Activity) {
            mPlayer = MediaPlayer.create(activity, R.raw.astro_force_long)

            if (mPlayer != null) {
                mPlayer!!.isLooping = true
                mPlayer!!.setVolume(50f, 50f)
                mPlayer!!.start()
            }
        }

        fun startMusicGame(activity: Activity) {
            mPlayer = MediaPlayer.create(activity, R.raw.net_bots_long)

            if (mPlayer != null) {
                mPlayer!!.isLooping = true
                mPlayer!!.setVolume(50f, 50f)
                mPlayer!!.start()
            }
        }

        fun stopMusic() {
            if (mPlayer != null) {
                mPlayer!!.stop()
                mPlayer!!.release()
                mPlayer = null
            }
        }

        fun resumeMusic() {
            if (mPlayer != null) {
                if (!mPlayer!!.isPlaying) {
                    mPlayer!!.seekTo(length)
                    mPlayer!!.start()
                }
            }
        }

        fun pauseMusic() {
            if (mPlayer != null) {
                if (mPlayer!!.isPlaying) {
                    mPlayer!!.pause()
                    length = mPlayer!!.currentPosition
                }
            }
        }

        fun buttonSoundPlay(activity: Activity) {
            btnPlayer = MediaPlayer.create(activity, R.raw.btn_sound)
            btnPlayer?.start()
        }

        fun buttonPlayerDestroyer() {
            btnPlayer?.release()
        }
    }
}

/*
class MusicService : Service(), MediaPlayer.OnErrorListener {


    private val mBinder = ServiceBinder()
    internal var mPlayer: MediaPlayer? = null
    private var length = 0

    inner class ServiceBinder : Binder() {
        internal val service: MusicService
            get() = this@MusicService
    }

    override fun onBind(arg0: Intent): IBinder {
        return mBinder
    }

    override fun onCreate() {
        super.onCreate()

        mPlayer = MediaPlayer.create(this, R.raw.astro_force_long)
        mPlayer!!.setOnErrorListener(this)

        if (mPlayer != null) {
            mPlayer!!.isLooping = true
            mPlayer!!.setVolume(50f, 50f)
        }


        mPlayer!!.setOnErrorListener(object : MediaPlayer.OnErrorListener {

            override fun onError(mp: MediaPlayer?, what: Int, extra: Int): Boolean {

                onError(mPlayer, what, extra)
                return true
            }
        })
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        if (mPlayer != null) {
            mPlayer!!.start()
        }
        return START_NOT_STICKY
    }

    fun pauseMusic() {
        if (mPlayer != null) {
            if (mPlayer!!.isPlaying) {
                mPlayer!!.pause()
                length = mPlayer!!.currentPosition
            }
        }
    }

    fun resumeMusic() {
        if (mPlayer != null) {
            if (!mPlayer!!.isPlaying) {
                mPlayer!!.seekTo(length)
                mPlayer!!.start()
            }
        }
    }

    fun startMusic() {
        mPlayer = MediaPlayer.create(this, R.raw.astro_force_long)
        mPlayer!!.setOnErrorListener(this)

        if (mPlayer != null) {
            mPlayer!!.isLooping = true
            mPlayer!!.setVolume(50f, 50f)
            mPlayer!!.start()
        }

    }

    fun stopMusic() {
        if (mPlayer != null) {
            mPlayer!!.stop()
            mPlayer!!.release()
            mPlayer = null
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mPlayer != null) {
            try {
                mPlayer!!.stop()
                mPlayer!!.release()
            } finally {
                mPlayer = null
            }
        }
    }

    override fun onError(mp: MediaPlayer, what: Int, extra: Int): Boolean {

        Toast.makeText(this, "Music player failed", Toast.LENGTH_SHORT).show()
        if (mPlayer != null) {
            try {
                mPlayer!!.stop()
                mPlayer!!.release()
            } finally {
                mPlayer = null
            }
        }
        return false
    }
}*/

