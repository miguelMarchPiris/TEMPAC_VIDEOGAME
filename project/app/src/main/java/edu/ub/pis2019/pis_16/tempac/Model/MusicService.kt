package edu.ub.pis2019.pis_16.tempac.Model

import android.app.Activity
import android.widget.Toast
import android.media.MediaPlayer
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import edu.ub.pis2019.pis_16.tempac.R

class MusicService {

    companion object MusicPlayer{

        var btnPlayer : MediaPlayer? = null
        var mPlayer : MediaPlayer? = null
        var length = 0
        var isPaused = false

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

        fun startMusicMenu(context: Context?) {
            mPlayer = MediaPlayer.create(context, R.raw.astro_force_long)

            if (mPlayer != null) {
                mPlayer!!.isLooping = true
                mPlayer!!.setVolume(50f, 50f)
                mPlayer!!.start()
            }
        }

        fun startMusicGame(context: Context?) {
            mPlayer = MediaPlayer.create(context, R.raw.net_bots_long)

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

        fun buttonSoundPlay(context: Context?) {
            btnPlayer = MediaPlayer.create(context, R.raw.btn_sound)
            btnPlayer?.start()
        }

        fun buttonPlayerDestroyer() {
            btnPlayer?.release()
        }
    }
}