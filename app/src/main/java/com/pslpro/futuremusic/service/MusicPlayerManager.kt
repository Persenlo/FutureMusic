package com.pslpro.futuremusic.service

import android.app.Application
import android.content.Context
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.PlaybackException
import com.google.android.exoplayer2.Player
import com.pslpro.futuremusic.model.LocalMusicModel

object MusicPlayerManager {

    //播放列表
    private val playList = MutableLiveData<List<LocalMusicModel>>()
    //当前播放歌曲
    private val currentPlay = MutableLiveData<LocalMusicModel>()
    //当前播放状态
    private val playState = MutableLiveData(false)
    //歌曲长度
    private val musicDuration = MutableLiveData<Int>()
    //当前播放进度
    private val process = MutableLiveData<Int>()
    //当前歌曲在播放列表的下标
    private val listIndex = MutableLiveData<Int>(0)
    //当前播放模式
    private val playMode = MutableLiveData<Int>(Player.REPEAT_MODE_ALL)

    //获取播放列表
    fun getPlayList(): LiveData<List<LocalMusicModel>>{
        return playList
    }
    //获取当前播放歌曲
    fun getCurrentPlay(): LiveData<LocalMusicModel>{
        return currentPlay
    }
    //获取当前播放状态
    fun getPlayState(): LiveData<Boolean>{
        return playState
    }
    //获取当前歌曲长度
    fun getDuration(): LiveData<Int>{
        return musicDuration
    }
    //获取当前播放进度
    fun getProcess(): LiveData<Int>{
        return process
    }
    //获取当前所在下标
    fun getMusicIndex(): LiveData<Int>{
        return listIndex
    }
    //获取当前播放状态
    fun getPlayMode(): LiveData<Int>{
        return playMode
    }


    //创建EXO播放器实例
    private fun createExoPlayer(context: Context) = ExoPlayer.Builder(context).build().apply{
        addListener(object : Player.Listener{
            override fun onPlayerError(error: PlaybackException) {
                super.onPlayerError(error)
                skipToNext()
            }

            override fun onIsPlayingChanged(isPlaying: Boolean) {
                super.onIsPlayingChanged(isPlaying)
                playState.value = isPlaying
            }

            override fun onPlaybackStateChanged(playbackState: Int) {
                super.onPlaybackStateChanged(playbackState)
                when (playbackState) {
                    Player.STATE_READY -> {
                        musicDuration.value = this@apply.duration.toInt()
                        this@MusicPlayerManager.play()
                    }
                    Player.STATE_BUFFERING -> {

                    }
                    Player.STATE_ENDED -> {
                        skipToNext()
                    }
                    Player.STATE_IDLE -> {

                    }
                    else -> {}
                }
            }
        })
    }

    private val mUpdateCurrentPositionFun = object : Runnable {
        override fun run() {
            process.value = mMediaPlayer?.currentPosition?.toInt() ?: return
            mHandler.postDelayed(this, 1000)
        }
    }

    private val mHandler = Handler(Looper.myLooper()!!)

    private lateinit var mApplication: Application

    @Volatile
    @JvmStatic
    private var mMediaPlayer: ExoPlayer? = null
        get() {
            if (field == null) {
                field = createExoPlayer(mApplication)
            }
            return field
        }

    @JvmStatic
    fun init(application: Application) {
        mApplication = application
    }


    /**
     * 设置下一首播放的歌曲
     * @music: 歌曲
     */
    @Suppress("UNCHECKED_CAST")
    fun addNextPlay(music: LocalMusicModel) {
        val list = playList.value as ArrayList<LocalMusicModel>?
        list?.let {
            list.add(listIndex.value!! + 1, music)
            playList.value = it
        }
    }

    /**
     * 设置播放列表并播放指定歌曲
     * @list: 新的播放列表
     * @index: 播放指定下标的歌曲
     */
    @Suppress("UNCHECKED_CAST")
    fun play(list: List<LocalMusicModel>, index: Int) {
        playList.value = list
        updateIndex(index)
    }

    /**
     * 随机播放
     */
    fun shufflePlay() {
        playMode.value = 100
        mMediaPlayer!!.shuffleModeEnabled = true
    }
    fun stopShufflePlay() {
        playMode.value = Player.REPEAT_MODE_OFF
        mMediaPlayer!!.shuffleModeEnabled = false
    }

    /**
     * 开启循环播放
     */
    fun repeatModeOn(){
        playMode.value = Player.REPEAT_MODE_ALL
        mMediaPlayer!!.repeatMode = Player.REPEAT_MODE_ALL
    }

    /**
     * 开启单曲循环
     */
    fun repeatModeOne(){
        playMode.value = Player.REPEAT_MODE_ONE
        mMediaPlayer!!.repeatMode = Player.REPEAT_MODE_ONE
    }

    /**
     * 关闭循环播放，播完暂停
     */
    fun repeatModeOff(){
        playMode.value = Player.REPEAT_MODE_OFF
        mMediaPlayer!!.repeatMode = Player.REPEAT_MODE_OFF
    }

    /**
     * 跳转到指定的播放进度
     * @position: 目标播放进度
     */
    fun seekTo(position: Int) {
        process.value = position
        mMediaPlayer!!.seekTo(position.toLong())
    }

    /**
     * 跳转到指定歌曲
     * @index: 指定歌曲的下标
     */
    private fun updateIndex(index: Int) {
        //超出最大范围时停止播放歌曲
        if (!(index >= 0 && playList.value != null && index <= playList.value!!.size - 1)) {
            pause()
            return
        }
        listIndex.value = index
    }

    /**
     * 上一首
     */
    fun skipToPrevious() {
        updateIndex(listIndex.value!! - 1)
    }


    /**
     * 下一首
     */
    fun skipToNext() {
        updateIndex(listIndex.value!! + 1)
    }

    /**
     * 播放歌曲
     */
    fun play() {
        if (mMediaPlayer!!.isPlaying)
            return
        mHandler.post(mUpdateCurrentPositionFun)
        mMediaPlayer!!.play()
    }

    /**
     * 暂停播放音乐
     */
    fun pause() {
        if (!mMediaPlayer!!.isPlaying)
            return
        mHandler.removeCallbacks(mUpdateCurrentPositionFun)
        mMediaPlayer!!.pause()
    }

    /**
     * 播放指定音乐
     * @music: 需要播放的音乐
     */
    private fun playMusic(music: LocalMusicModel) {
        process.value = 0
        currentPlay.value = music
        mMediaPlayer?.run {
            setMediaItem(MediaItem.fromUri(music.contentUri))
            prepare()
        }
    }

    init {
        listIndex.observeForever {
            if (playList.value != null) {
                val song = playList.value!![it]
                playMusic(song)
            }
        }
    }

    fun stop() {
        mMediaPlayer?.run {
            stop()
            release()
        }
        mMediaPlayer = null
    }




}