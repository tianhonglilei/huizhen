package com.hoolai.huizhen.widget;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.lzyzsd.circleprogress.DonutProgress;
import com.hoolai.huizhen.R;
import com.hoolai.huizhen.model.ModelAudio;
import com.hoolai.huizhen.ui.activity.PlayActivity;
import com.hoolai.huizhen.ui.activity.view.PlayView;
import com.hoolai.huizhen.util.AssetAndRawUtil;
import com.shuyu.gsyvideoplayer.model.GSYVideoModel;
import com.shuyu.gsyvideoplayer.utils.Debuger;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;
import com.shuyu.gsyvideoplayer.video.base.GSYBaseVideoPlayer;
import com.shuyu.gsyvideoplayer.video.base.GSYVideoPlayer;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import moe.codeest.enviews.ENDownloadView;

import static com.shuyu.gsyvideoplayer.utils.CommonUtil.hideNavKey;

/**
 * Author: lilei
 * Date: 2018/5/23
 * Comment: //TODO
 */

public class ListVideoView extends StandardGSYVideoPlayer {

    private static final String TAG = "ListVideoView";

    //播放情况

    //前奏
    public static final int PLAY_STATE_PRE = 0;
    //动作
    public static final int PLAY_STATE_PLAY = 1;
    //休息
    public static final int PLAY_STATE_REST = 2;

    protected int mPlayStateIndex = -1;

    Context context;
    protected List<GSYVideoModel> mUriList = new ArrayList<>();
    protected int mPlayPosition;

    DonutProgress playVideoProgress;
    RelativeLayout rlPlayCountDown;
    DonutProgress restVideoProgress;
    RelativeLayout rlRestCountDown;
    TextView txtRestTitle;
    LinearLayout lyRestTitle;
    TextView playCenterCountDown;
    TextView playSubtitle;
    TextView playTilte;
    RelativeLayout rlPlaySurface;
    ImageView playLast;
    ImageView pause;
    ImageView playNext;
    LinearLayout lyPlayCenterControl;
    Button btnCurtainClose;
    TextView txtCurtainTrainTime;
    ImageButton btnCurtainPlay;
    TextView txtCurtainPause;
    TextView txtCurtainTitle;
    RelativeLayout rlPlayControl;
    RelativeLayout rlPauseControl;


    MediaPlayer audioBgm;
    MediaPlayer audioMain;

    public MediaPlayer getAudioCD() {
        return audioCD;
    }

    public void setAudioCD(MediaPlayer audioCD) {
        this.audioCD = audioCD;
    }

    MediaPlayer audioCD;
    int audioNum;

    public MediaPlayer getAudioBgm() {
        return audioBgm;
    }

    public void setAudioBgm(MediaPlayer audioBgm) {
        this.audioBgm = audioBgm;
    }

    public MediaPlayer getAudioMain() {
        return audioMain;
    }

    public void setAudioMain(MediaPlayer audioMain) {
        this.audioMain = audioMain;
    }

    MediaPlayer audioTick;

    public MediaPlayer getAudioTick() {
        return audioTick;
    }

    public void setAudioTick(MediaPlayer audioTick) {
        this.audioTick = audioTick;
        try {
            audioTick.setDataSource(context, AssetAndRawUtil.getRawPath(context, R.raw.new_beat_tick_sound));
            audioTick.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public List<ModelAudio> getAudios() {
        return audios;
    }

    public void setAudios(List<ModelAudio> audios) {
        this.audios = audios;
    }

    List<ModelAudio> audios;

    PlayView playView;

    AssetManager assetsManager;

    public ListVideoView(Context context, Boolean fullFlag) {
        super(context, fullFlag);
    }

    public ListVideoView(Context context) {
        super(context);
        this.context = context;
//        initAllWidget();
    }

    public ListVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
//        initAllWidget();
    }

    public void setContextView(PlayView playView) {
        this.playView = playView;
    }


    public void initAllWidget(Activity view) {

        rlPlayControl = (RelativeLayout) view.findViewById(R.id.rl_play_control);
        rlPauseControl = view.findViewById(R.id.rl_pause_control);
        txtCurtainTitle = (TextView) view.findViewById(R.id.txt_curtain_title);
        txtCurtainPause = (TextView) view.findViewById(R.id.txt_curtain_pause);
        btnCurtainPlay = (ImageButton) view.findViewById(R.id.btn_curtain_play);
        txtCurtainTrainTime = (TextView) view.findViewById(R.id.txt_curtain_train_time);
        btnCurtainClose = (Button) view.findViewById(R.id.btn_curtain_close);
        lyPlayCenterControl = (LinearLayout) view.findViewById(R.id.ly_play_center_control);
        playNext = (ImageView) view.findViewById(R.id.play_next);
        pause = (ImageView) view.findViewById(R.id.pause);
        playLast = (ImageView) view.findViewById(R.id.play_last);
        rlPlaySurface = (RelativeLayout) view.findViewById(R.id.rl_play_surface);
        playTilte = (TextView) view.findViewById(R.id.play_tilte);
        playSubtitle = (TextView) view.findViewById(R.id.play_subtitle);
        playCenterCountDown = (TextView) view.findViewById(R.id.play_center_count_down);
        lyRestTitle = (LinearLayout) view.findViewById(R.id.ly_rest_title);
        txtRestTitle = (TextView) view.findViewById(R.id.txt_rest_title);
        rlRestCountDown = (RelativeLayout) view.findViewById(R.id.rl_rest_count_down);
        restVideoProgress = (DonutProgress) view.findViewById(R.id.rest_video_progress);
        rlPlayCountDown = (RelativeLayout) view.findViewById(R.id.rl_play_count_down);
        playVideoProgress = (DonutProgress) view.findViewById(R.id.play_video_progress);


        rlPlayControl.setOnClickListener(this);
        rlPauseControl.setOnClickListener(this);
        pause.setOnClickListener(this);
        btnCurtainPlay.setOnClickListener(this);
        assetsManager = context.getAssets();

        musicHandler = new MusicHandler();
    }

    /**
     * 设置播放URL
     *
     * @param url           播放url
     * @param position      需要播放的位置
     * @param cacheWithPlay 是否边播边缓存
     * @return
     */
    public boolean setUp(List<GSYVideoModel> url, boolean cacheWithPlay, int position) {
        return setUp(url, cacheWithPlay, position, null, new HashMap<String, String>());
    }

    /**
     * 设置播放URL
     *
     * @param url           播放url
     * @param cacheWithPlay 是否边播边缓存
     * @param position      需要播放的位置
     * @param cachePath     缓存路径，如果是M3U8或者HLS，请设置为false
     * @return
     */
    public boolean setUp(List<GSYVideoModel> url, boolean cacheWithPlay, int position, File cachePath) {
        return setUp(url, cacheWithPlay, position, cachePath, new HashMap<String, String>());
    }

    /**
     * 设置播放URL
     *
     * @param url           播放url
     * @param cacheWithPlay 是否边播边缓存
     * @param position      需要播放的位置
     * @param cachePath     缓存路径，如果是M3U8或者HLS，请设置为false
     * @param mapHeadData   http header
     * @return
     */
    public boolean setUp(List<GSYVideoModel> url, boolean cacheWithPlay, int position, File cachePath, Map<String, String> mapHeadData) {
        return setUp(url, cacheWithPlay, position, cachePath, mapHeadData, true);
    }

    /**
     * 设置播放URL
     *
     * @param url           播放url
     * @param cacheWithPlay 是否边播边缓存
     * @param position      需要播放的位置
     * @param cachePath     缓存路径，如果是M3U8或者HLS，请设置为false
     * @param mapHeadData   http header
     * @param changeState   切换的时候释放surface
     * @return
     */
    protected boolean setUp(List<GSYVideoModel> url, boolean cacheWithPlay, int position, File cachePath, Map<String, String> mapHeadData, boolean changeState) {
        mUriList = url;
        mPlayPosition = position;
        mMapHeadData = mapHeadData;
        GSYVideoModel gsyVideoModel = url.get(position);
        boolean set = setUp(gsyVideoModel.getUrl(), cacheWithPlay, cachePath, gsyVideoModel.getTitle(), changeState);
        if (!TextUtils.isEmpty(gsyVideoModel.getTitle())) {
            mTitleTextView.setText(gsyVideoModel.getTitle());
        }
        return set;
    }

    @Override
    protected void cloneParams(GSYBaseVideoPlayer from, GSYBaseVideoPlayer to) {
        super.cloneParams(from, to);
        ListVideoView sf = (ListVideoView) from;
        ListVideoView st = (ListVideoView) to;
        st.mPlayPosition = sf.mPlayPosition;
        st.mUriList = sf.mUriList;
    }

    @Override
    public GSYBaseVideoPlayer startWindowFullscreen(Context context, boolean actionBar, boolean statusBar) {
        GSYBaseVideoPlayer gsyBaseVideoPlayer = super.startWindowFullscreen(context, actionBar, statusBar);
        if (gsyBaseVideoPlayer != null) {
            ListVideoView listVideoView = (ListVideoView) gsyBaseVideoPlayer;
            GSYVideoModel gsyVideoModel = mUriList.get(mPlayPosition);
            if (!TextUtils.isEmpty(gsyVideoModel.getTitle())) {
                listVideoView.mTitleTextView.setText(gsyVideoModel.getTitle());
            }
        }
        return gsyBaseVideoPlayer;
    }

    @Override
    protected void resolveNormalVideoShow(View oldF, ViewGroup vp, GSYVideoPlayer gsyVideoPlayer) {
        if (gsyVideoPlayer != null) {
            ListVideoView listVideoView = (ListVideoView) gsyVideoPlayer;
            GSYVideoModel gsyVideoModel = mUriList.get(mPlayPosition);
            if (!TextUtils.isEmpty(gsyVideoModel.getTitle())) {
                mTitleTextView.setText(gsyVideoModel.getTitle());
            }
        }
        super.resolveNormalVideoShow(oldF, vp, gsyVideoPlayer);
    }

    @Override
    public void onCompletion() {
        if (mPlayPosition < (mUriList.size())) {
            return;
        }
        super.onCompletion();
    }

    @Override
    public void onAutoCompletion() {
        if (playNext()) {
            return;
        }
        super.onAutoCompletion();
    }


    /**
     * 开始状态视频播放，prepare时不执行  addTextureView();
     */
    @Override
    protected void prepareVideo() {
        super.prepareVideo();
    }


    @Override
    public void onPrepared() {
        super.onPrepared();
    }

    @Override
    protected void changeUiToNormal() {
        if (mHadPlay && mPlayPosition < (mUriList.size())) {
            setViewShowState(mThumbImageViewLayout, GONE);
            setViewShowState(mTopContainer, GONE);
            setViewShowState(mBottomContainer, GONE);
            setViewShowState(mStartButton, GONE);
            setViewShowState(mLoadingProgressBar, GONE);
            setViewShowState(mBottomProgressBar, GONE);
            setViewShowState(mLockScreen, GONE);
        }
    }

    /**
     * 播放下一集
     *
     * @return true表示还有下一集
     */
    public boolean playNext() {
        if (mPlayPosition < (mUriList.size() - 1)) {
            mPlayPosition += 1;
            GSYVideoModel gsyVideoModel = mUriList.get(mPlayPosition);
            mSaveChangeViewTIme = 0;
            setUp(mUriList, mCache, mPlayPosition, null, mMapHeadData, false);
            if (!TextUtils.isEmpty(gsyVideoModel.getTitle())) {
                mTitleTextView.setText(gsyVideoModel.getTitle());
            }
            startPlayLogic();
            return true;
        }
        return false;
    }

    public boolean playLast() {
        if (mPlayPosition > 0) {
            mPlayPosition -= 1;
            GSYVideoModel gsyVideoModel = mUriList.get(mPlayPosition);
            mSaveChangeViewTIme = 0;
            setUp(mUriList, mCache, mPlayPosition, null, mMapHeadData, false);
            if (!TextUtils.isEmpty(gsyVideoModel.getTitle())) {
                mTitleTextView.setText(gsyVideoModel.getTitle());
            }
            startPlayLogic();
            return true;
        }
        return false;
    }


    @Override
    protected void hideAllWidget() {
        super.hideAllWidget();
        setViewShowState(mTopContainer, GONE);
        setViewShowState(mBottomContainer, GONE);
        setViewShowState(mStartButton, GONE);
        setViewShowState(mLoadingProgressBar, GONE);
        setViewShowState(mThumbImageViewLayout, GONE);
        setViewShowState(mBottomProgressBar, GONE);
    }

    public void hideWidget() {
        hideAllWidget();
    }

    @Override
    protected void onClickUiToggle() {
        if (mCurrentState == CURRENT_STATE_PREPAREING) {
//            if (mBottomContainer != null) {
//                if (mBottomContainer.getVisibility() == View.VISIBLE) {
//                    changeUiToPrepareingClear();
//                } else {
//                    changeUiToPreparingShow();
//                }
//            }
        } else if (mCurrentState == CURRENT_STATE_PLAYING) {
            if (rlPlayControl != null) {
                if (rlPlayControl.getVisibility() != VISIBLE) {
                    showPlayControl();
                } else {
                    hidePlayControl();
                }
            }
        } else if (mCurrentState == CURRENT_STATE_PAUSE) {
//            if (mBottomContainer != null) {
//                if (mBottomContainer.getVisibility() == View.VISIBLE) {
//                    changeUiToPauseClear();
//                } else {
//                    changeUiToPauseShow();
//                }
//            }
        } else if (mCurrentState == CURRENT_STATE_AUTO_COMPLETE) {
//            if (mBottomContainer != null) {
//                if (mBottomContainer.getVisibility() == View.VISIBLE) {
//                    changeUiToCompleteClear();
//                } else {
//                    changeUiToCompleteShow();
//                }
//            }
        } else if (mCurrentState == CURRENT_STATE_PLAYING_BUFFERING_START) {
//            if (mBottomContainer != null) {
//                if (mBottomContainer.getVisibility() == View.VISIBLE) {
//                    changeUiToPlayingBufferingClear();
//                } else {
//                    changeUiToPlayingBufferingShow();
//                }
//            }
        }
    }

    @Override
    protected void touchDoubleUp() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pause:
                clickStartIcon();
                hidePlayControl();
                showPauseControl();
                pauseAudio(audioBgm);
                pauseAudio(audioMain);
                if (audioCD.isPlaying()) {
                    pauseAudio(audioCD);
                } else {
                    pauseCDTask(6560);
                }
                break;
            case R.id.btn_curtain_play:
                hidePauseControl();
                clickStartIcon();
                pauseAudio(audioBgm);
                pauseAudio(audioMain);
                startCDTask(cdTime);
            default:
                onClickUiToggle();
                break;
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        return false;
    }

    @Override
    protected void changeUiToPlayingShow() {
    }

    @Override
    protected void changeUiToPauseShow() {
    }

    @Override
    protected void changeUiToError() {
    }

    @Override
    protected void changeUiToPreparingShow() {
    }

    @Override
    protected void changeUiToCompleteShow() {
    }

    @Override
    protected void changeUiToClear() {
    }

    @Override
    protected void changeUiToPlayingBufferingShow() {
    }

    @Override
    protected void changeUiToCompleteClear() {
    }

    @Override
    protected void changeTextureViewShowType() {
    }

    @Override
    protected void changeUiToPauseClear() {
    }

    @Override
    protected void changeUiToPlayingBufferingClear() {
    }

    @Override
    protected void changeUiToPlayingClear() {
    }

    @Override
    protected void changeUiToPrepareingClear() {
    }


    @Override
    public void startPlayLogic() {
        if (mVideoAllCallBack != null) {
            Debuger.printfLog("onClickStartThumb");
            mVideoAllCallBack.onClickStartThumb(mOriginUrl, mTitle, ListVideoView.this);
        }
        prepareVideo();
//        clickStartIcon();
    }

    @Override
    public void release() {
        super.release();
        if (playTimer != null) {
            playTimer.cancel();
        }
    }

    private class BottomProgressTimerTask extends TimerTask {
        @Override
        public void run() {
            if (mCurrentState == CURRENT_STATE_PLAYING || mCurrentState == CURRENT_STATE_PAUSE) {
                post(new Runnable() {
                    @Override
                    public void run() {

                    }
                });
            }
        }
    }

    private class PlayProgressTimerTask extends TimerTask {
        @Override
        public void run() {
            if (mCurrentState == CURRENT_STATE_PLAYING || mCurrentState == CURRENT_STATE_PAUSE) {
                post(new Runnable() {
                    @Override
                    public void run() {

                    }
                });
            }
        }
    }

    private class REstProgressTimerTask extends TimerTask {
        @Override
        public void run() {
            if (mCurrentState == CURRENT_STATE_PLAYING || mCurrentState == CURRENT_STATE_PAUSE) {
                post(new Runnable() {
                    @Override
                    public void run() {

                    }
                });
            }
        }
    }

    private class DismissControlViewTimerTask extends TimerTask {

        @Override
        public void run() {
            if (mCurrentState != CURRENT_STATE_NORMAL
                    && mCurrentState != CURRENT_STATE_ERROR
                    && mCurrentState != CURRENT_STATE_AUTO_COMPLETE) {
                if (getActivityContext() != null) {
                    ((Activity) getActivityContext()).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            hideAllWidget();
                            setViewShowState(mLockScreen, GONE);
                            if (mHideKey && mIfCurrentIsFullscreen && mShowVKey) {
                                hideNavKey(mContext);
                            }
                        }
                    });
                }
            }
        }
    }


    private void showPlayControl() {
        rlPlayControl.setVisibility(VISIBLE);
        rlPlayControl.setAlpha(0f);
        ObjectAnimator animator = ObjectAnimator.ofFloat(rlPlayControl, "alpha", 0f, 1f);
        animator.setDuration(300);
        animator.start();
    }

    private void hidePlayControl() {
        rlPlayControl.setAlpha(1f);
        ObjectAnimator animator = ObjectAnimator.ofFloat(rlPlayControl, "alpha", 1f, 0f);
        animator.setDuration(300);
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                rlPlayControl.setVisibility(GONE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animator.start();
    }

    private void showPauseControl() {
        if (rlPauseControl.getVisibility() != VISIBLE) {
            rlPauseControl.setVisibility(VISIBLE);
        }
    }

    private void hidePauseControl() {
        if (rlPauseControl.getVisibility() == VISIBLE) {
            rlPauseControl.setVisibility(GONE);
        }
    }

    public void showPreView() {
        if (rlPlaySurface.getVisibility() != VISIBLE) {
            rlPlaySurface.setVisibility(VISIBLE);
        }
    }

    public void hidePreView() {
        if (rlPlaySurface.getVisibility() == VISIBLE) {
            rlPlaySurface.setVisibility(GONE);
        }
    }


    int playProgress = 0;
    int countNum = 0;

//    MyCountDownTimer playTimer;

    public void startCountDownTimer(int time) {
        rlPlayCountDown.setVisibility(VISIBLE);
        playVideoProgress.setProgress(playProgress);
        playVideoProgress.setMax(time / 250);
        playVideoProgress.setText("" + time / 1000);
//        playTimer = new MyCountDownTimer(time, 250) {
//            @Override
//            public void onTick(long millisUntilFinished) {
//                playVideoProgress.setProgress(playProgress += 1);
//                countNum += 1;
//                if (countNum == 4) {
//                    progressText(millisUntilFinished);
//                    countNum = 0;
//                }
//                Log.e(TAG, "onFinish: " + playVideoProgress.getProgress());
//            }
//
//            @Override
//            public void onFinish() {
//                playVideoProgress.setText("0" + 0);
//                playVideoProgress.setProgress(playVideoProgress.getMax());
////                clickStartIcon();
//            }
//        };
//        playTimer.start();

    }

    private void progressText(long time) {
        audioTick.start();
        if (time / 1000 < 10) {
            playVideoProgress.setText("0" + (int) (time / 1000));
        } else {
            playVideoProgress.setText("" + (int) (time / 1000));
        }
    }

    Timer playTimer, cdTimer;
    int i, due;
    MusicHandler musicHandler;

    public void startCountDown(final ModelAudio audio) {
        if (audio.getType() != null) {
            if (audio.getType().equals("video")) {
                hidePreView();
                rlPlayCountDown.setVisibility(VISIBLE);
                due = audio.getDue();
                playVideoProgress.setMax(due / 200);
                playVideoProgress.setProgress(playProgress);
                playTimer = new Timer();
                playTimer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        if (audioMain.isPlaying()) {
                            musicHandler.sendEmptyMessage(1);
                        }
                    }
                }, 0, 200);
            }
        }
    }

    class MusicHandler extends Handler {

        public MusicHandler() {
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    i += 1;
                    playProgress += 1;
                    playVideoProgress.setProgress(playProgress);
                    if (i == 5) {
                        int time = audioMain.getCurrentPosition();
                        progressText(due - time);
                        i = 0;
                    }
                    break;
                case 2:
                    switch (cdnum) {
                        case 3:
                            playCenterCountDown.setVisibility(VISIBLE);
                            break;
                        case 2:
                            playCenterCountDown.setBackgroundResource(R.drawable.two);
                            break;
                        case 1:
                            playCenterCountDown.setBackgroundResource(R.drawable.one);
                            break;
                        case 0:
                            stopAudio(audioCD);
                            playCenterCountDown.setVisibility(GONE);
                            if (cdAnimTimer != null) {
                                cdAnimTimer.cancel();
                                cdAnimTimer = null;
                            }
                            break;
                    }
                    break;
            }
        }
    }

    public void startAllAudio() {
        startAudio(audioMain, audios.get(audioNum));
        startRawAudio(audioBgm, R.raw.video_music_0);
        showPreView();
        startCDTask(6560);
    }


    public void startRawAudio(MediaPlayer player, int id) {
        player.reset(); //重置多媒体
        Uri notification = AssetAndRawUtil.getRawPath(context, id);
//        Ringtone ringtone = RingtoneManager.getRingtone(getApplicationContext(), notification);
        try {
            player.setDataSource(context, notification);
            player.setLooping(true);
            player.prepare();
            player.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startRestAudio(final MediaPlayer player, final ModelAudio audio) {
        try {
            player.reset(); //重置多媒体
//            String dataSource = audios.get(songNum);//得到当前播放音乐的路径
//            setPlayName(dataSource);//截取歌名
            AssetFileDescriptor afd;
            afd = assetsManager.openFd(audio.getUrl());
            player.setDataSource(afd.getFileDescriptor(),
                    afd.getStartOffset(), afd.getLength());
            player.prepare();//准备播放
            player.start();//开始播放
            //setOnCompletionListener 当当前多媒体对象播放完成时发生的事件
            player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                public void onCompletion(MediaPlayer arg0) {

                }
            });
        } catch (Exception e) {
            Log.v("MusicService", e.getMessage());
        }
    }

    public void startAudio(final MediaPlayer player, final ModelAudio audio) {
        startCountDown(audio);
        try {
            player.reset(); //重置多媒体
//            String dataSource = audios.get(songNum);//得到当前播放音乐的路径
//            setPlayName(dataSource);//截取歌名
            AssetFileDescriptor afd;
            afd = assetsManager.openFd(audio.getUrl());
            player.setDataSource(afd.getFileDescriptor(),
                    afd.getStartOffset(), afd.getLength());
            player.prepare();//准备播放
            player.start();//开始播放
            //setOnCompletionListener 当当前多媒体对象播放完成时发生的事件
            player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                public void onCompletion(MediaPlayer arg0) {
                    nextAudio(player, audio);//如果当前歌曲播放完毕,自动播放下一首.
                }
            });
        } catch (Exception e) {
            Log.v("MusicService", e.getMessage());
        }
    }

    public void nextAudio(MediaPlayer player, final ModelAudio audio) {
        if (playTimer != null) {
            playTimer.cancel();
            playTimer = null;
        }

        if (audioNum == audios.size() - 1) {
            stopAudio(player);
        } else {
            audioNum += 1;
            if (!audio.isIs_rest()) {
                startAudio(player, audios.get(audioNum));
            } else {
                //休息处理
                startRestAudio(audioBgm,R.raw.);
            }
        }
    }

    public void lastAudio(MediaPlayer player, final ModelAudio audio) {
//        songNum = songNum == 0 ? musicList.size() - 1 : songNum - 1;
//        startAudio();
    }

    public void pauseAudio(MediaPlayer player) {
        if (player.isPlaying())
            player.pause();
        else
            player.start();
    }

    public void stopAudio(MediaPlayer player) {
        if (player.isPlaying()) {
            player.stop();
        }
    }

    long cdTime;

    //3秒倒计时
    public void startCDTask(long time) {
        if (time > 0) {
            cdTime = new Date().getTime();
            cdTimer = new Timer();
            cdTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    startRawAudio(audioCD, R.raw.new_video_countdown);
                    startCDAnim();
                }
            }, time);
        }
    }

    Timer cdAnimTimer;
    int cdnum = 4;

    public void startCDAnim() {
        cdAnimTimer = new Timer();
        cdAnimTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (audioCD.isPlaying()) {
                    cdnum -= 1;
                    musicHandler.sendEmptyMessage(2);
                }
            }
        }, 100, 1000);
    }

    //暂停3秒倒计时任务
    public void pauseCDTask(long time) {
        if (cdTimer != null) {
            cdTimer.cancel();
            cdTimer = null;
        }
        cdTime = time - (new Date().getTime() - cdTime);
    }


}
