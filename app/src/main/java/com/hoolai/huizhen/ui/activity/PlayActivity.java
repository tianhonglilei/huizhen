package com.hoolai.huizhen.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.lzyzsd.circleprogress.DonutProgress;
import com.hoolai.huizhen.R;
import com.hoolai.huizhen.model.ModelAudio;
import com.hoolai.huizhen.ui.activity.view.PlayView;
import com.hoolai.huizhen.util.AssetAndRawUtil;
import com.hoolai.huizhen.widget.ListVideoView;
import com.shuyu.gsyvideoplayer.model.GSYVideoModel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;


public class PlayActivity extends Activity implements PlayView {
    private static final String TAG = "PlayActivity";


    @BindView(R.id.video)
    ListVideoView video;
    @BindView(R.id.play_video_progress)
    DonutProgress playVideoProgress;
    @BindView(R.id.rl_play_count_down)
    RelativeLayout rlPlayCountDown;
    @BindView(R.id.rest_video_progress)
    DonutProgress restVideoProgress;
    @BindView(R.id.rl_rest_count_down)
    RelativeLayout rlRestCountDown;
    @BindView(R.id.txt_rest_title)
    TextView txtRestTitle;
    @BindView(R.id.ly_rest_title)
    LinearLayout lyRestTitle;
    @BindView(R.id.play_center_count_down)
    TextView playCenterCountDown;
    @BindView(R.id.play_subtitle)
    TextView playSubtitle;
    @BindView(R.id.play_tilte)
    TextView playTilte;
    @BindView(R.id.rl_play_surface)
    RelativeLayout rlPlaySurface;
    @BindView(R.id.play_last)
    ImageView playLast;
    @BindView(R.id.pause)
    ImageView pause;
    @BindView(R.id.play_next)
    ImageView playNext;
    @BindView(R.id.ly_play_center_control)
    LinearLayout lyPlayCenterControl;
    @BindView(R.id.btn_curtain_close)
    Button btnCurtainClose;
    @BindView(R.id.txt_curtain_train_time)
    TextView txtCurtainTrainTime;
    @BindView(R.id.btn_curtain_play)
    ImageButton btnCurtainPlay;
    @BindView(R.id.txt_curtain_pause)
    TextView txtCurtainPause;
    @BindView(R.id.txt_curtain_title)
    TextView txtCurtainTitle;
    @BindView(R.id.rl_play_control)
    RelativeLayout rlPlayControl;

    MediaPlayer player1, playerbgm, audioTick, audioMain,audioCD;
    @BindView(R.id.rl_pause_control)
    RelativeLayout rlPauseControl;

    //正常
    public static final int VIDEO_STATE_NORMAL = 0;
    //准备中
    public static final int VIDEO_STATE_PREPAREING = 1;
    //播放中
    public static final int VIDEO_STATE_PLAYING = 2;
    //开始缓冲
    public static final int VIDEO_STATE_PLAYING_BUFFERING_START = 3;
    //暂停
    public static final int VIDEO_STATE_PAUSE = 5;
    //自动播放结束
    public static final int VIDEO_STATE_AUTO_COMPLETE = 6;
    //错误状态
    public static final int VIDEO_STATE_ERROR = 7;

    public int videoState;

    private Context context;
    AssetManager assetManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        ButterKnife.bind(this);
        context = this;
        assetManager = this.getAssets();
        AssetFileDescriptor afd = null;
        String mp4FilePath = "http://source.hotbody.cn/KVTnPV6i-6BfI-EnRm-PdOD-TnQOIZ21u382.mp4";
        final List<GSYVideoModel> pathList = new ArrayList<>();
        pathList.add(new GSYVideoModel(mp4FilePath, ""));
        pathList.add(new GSYVideoModel("http://source.hotbody.cn/KVTnPV6i-6BfI-EnRm-PdOD-TnQOIZ21u382.mp4", ""));

//        initAudio1(assetManager);
        initAudioTick();
        initAudioBgm();
        initAudioCD();
//        GSYVideoHelper helper = new GSYVideoHelper(this,video);
//        GSYVideoHelper.GSYVideoHelperBuilder builder = new GSYVideoHelper.GSYVideoHelperBuilder();
//        builder.setIsTouchWiget(false)
//                .setIsTouchWigetFull(false)
//                .setNeedShowWifiTip(false);
//        helper.setGsyVideoOptionBuilder(builder);
        video.setIsTouchWigetFull(false);
        video.setIsTouchWiget(false);
        video.setUp(pathList, true, 0);
        video.setLooping(true);
        video.hideWidget();
        video.initAllWidget(this);
        video.setContextView(this);
        video.startPlayLogic();
//        video.startCountDownTimer(60000);

        audioMain = new MediaPlayer();
        video.setAudioMain(audioMain);
        initJson();
        video.setAudios(audioList);
        video.startAllAudio();

    }

    private void initAudioBgm() {
        playerbgm = new MediaPlayer();
        video.setAudioBgm(playerbgm);
    }

    private void initAudioTick() {
        audioTick = new MediaPlayer();
        video.setAudioTick(audioTick);
    }

    private void initAudioCD(){
        audioCD = new MediaPlayer();
        video.setAudioCD(audioCD);
    }

    private void initAudio1(AssetManager assetManager) {
        AssetFileDescriptor afd;
        player1 = new MediaPlayer();
        try {
            afd = assetManager.openFd("vxRMWFIE-DcCJ-4gBr-N6Oz-yNxT43bTiQcC.m4a");
            player1.setDataSource(afd.getFileDescriptor(),
                    afd.getStartOffset(), afd.getLength());
            player1.setLooping(true);//循环播放
            player1.prepare();
            player1.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    List<ModelAudio> audioList;


    private void initJson() {
        JSONObject object = JSONObject.parseObject(getJson());
        JSONArray jsonArray = object.getJSONArray("audio");
        audioList = jsonArray.toJavaList(ModelAudio.class);
        for (ModelAudio m : audioList) {
            String url = m.getUrl().substring(m.getUrl().lastIndexOf("/") + 1);
            m.setUrl(url);
//
//            Log.e(TAG, "initJson: " + url);
//            Timer timer = new Timer();
//            timer.schedule(new TimerTask() {
//                @Override
//                public void run() {
//                    try {
//                        MediaPlayer audio = new MediaPlayer();
//                        AssetFileDescriptor afd = AssetAndRawUtil.getAssetsPath(assetManager, url);
//                        if (afd != null) {
//                            audio.setDataSource(afd.getFileDescriptor(),
//                                    afd.getStartOffset(), afd.getLength());
//                            audio.prepare();
//                            audio.start();
//                        }
//                        Log.e(TAG, "run: " + url);
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }, m.getStart_at() + 1000);
        }
    }

    private String getJson() {
        //将json数据变成字符串
        StringBuilder stringBuilder = new StringBuilder();
        try {
            //获取assets资源管理器
            AssetManager assetManager = this.getAssets();
            //通过管理器打开文件并读取
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    assetManager.open("hltest.json")));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
//            Log.e(TAG, "initPersonListData: 0");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (playerbgm != null) {
            playerbgm.stop();
        }
        if (player1 != null) {
            player1.stop();
        }
        if (video != null) {
            video.release();
        }
    }


    @Override
    public void showPlayControl() {
        rlPlayControl.setVisibility(View.VISIBLE);
    }

    @Override
    public void hidePlayControl() {
        rlPlayControl.setVisibility(View.GONE);
    }

    @Override
    public void showPauseControl() {
        rlPauseControl.setVisibility(View.VISIBLE);
    }

    @Override
    public void hidePauseControl() {
        rlPauseControl.setVisibility(View.GONE);
    }

    @Override
    public void showRestView() {

    }

    @Override
    public void hideRestView() {

    }


}
