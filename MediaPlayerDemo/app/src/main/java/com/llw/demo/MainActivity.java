package com.llw.demo;

/**
 * author : Ls
 */
import android.Manifest;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Environment;
import android.os.Bundle;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.llw.mediaplayerdemo.R;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

//继承View.OnClickListener，是按钮放在一起更直观，用另一种方法来设置按钮点击监听
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    //定义三个按钮并实例化MediaPlayer
    private MediaPlayer mediaPlayer = new MediaPlayer();
    //其他信息
    protected TextView tv_start;
    protected TextView tv_end;
    protected SeekBar seekBar;
    private Timer timer;//定时器
    private boolean isSeekbarChaning;//互斥变量，防止进度条和定时器冲突。
    private Button play;
    private Button pause;
    private Button stop;
    //动画
    private ImageView discsmap;//唱片
    private ImageView zhizhenmap;//指针
    private ObjectAnimator animator;//唱片动画
    private ObjectAnimator animator1;//指针动画


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        play = (Button)findViewById(R.id.play);
        pause = (Button)findViewById(R.id.pause);
        stop = (Button)findViewById(R.id.stop);
        play.setOnClickListener(this);
        pause.setOnClickListener(this);
        stop.setOnClickListener(this);
        if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this,new String[]{
                    Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
        }else {
            initView();//初始化进度条
            initMediaPlayer();//初始化MediaPlayer
        }
        discsmap = (ImageView)findViewById(R.id.listen_changpian_img);//唱片
        zhizhenmap = (ImageView)findViewById(R.id.listen_zhizhen_iv);//指针

    }

    /*
     * 初始化
     * */
    private void initView(){
        tv_start = (TextView)findViewById(R.id.tv_start);
        tv_end = (TextView)findViewById(R.id.tv_end);
        seekBar = (SeekBar)findViewById(R.id.seekbar);
        //绑定监听器，监听拖动到指定位置
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int duration2 = mediaPlayer.getDuration() / 1000;//获取音乐总时长
                int position = mediaPlayer.getCurrentPosition();//获取当前播放的位置
                tv_start.setText(calculateTime(position / 1000));//开始时间
                tv_end.setText(calculateTime(duration2));//总时长
            }
            /*
             * 通知用户已经开始一个触摸拖动手势。
             * */
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                isSeekbarChaning = true;
            }
            /*
             * 当手停止拖动进度条时执行该方法
             * 首先获取拖拽进度
             * 将进度对应设置给MediaPlayer
             * */
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                isSeekbarChaning = false;
                mediaPlayer.seekTo(seekBar.getProgress());//在当前位置播放
                tv_start.setText(calculateTime(mediaPlayer.getCurrentPosition() / 1000));
            }
        });

        play.setOnClickListener(this);
        pause.setOnClickListener(this);
        stop.setOnClickListener(this);
    }

    //计算播放时间
    public String calculateTime(int time) {
        int minute;
        int second;
        if (time >= 60) {
            minute = time / 60;
            second = time % 60;
            //分钟在0~9
            if (minute < 10) {
                //判断秒
                if (second < 10) {
                    return "0" + minute + ":" + "0" + second;
                } else {
                    return "0" + minute + ":" + second;
                }
            } else {
                //分钟大于10再判断秒
                if (second < 10) {
                    return minute + ":" + "0" + second;
                } else {
                    return minute + ":" + second;
                }
            }
        } else {
            second = time;
            if (second >= 0 && second < 10) {
                return "00:" + "0" + second;
            } else {
                return "00:" + second;
            }
        }
    }
    /*
     * 初始化MediaPlayer
     * */
    private void initMediaPlayer(){
        try {
            File file = new File(Environment.getExternalStorageDirectory(),"music.mp3");
            mediaPlayer.setDataSource(file.getPath());//指定音频文件的路径
            mediaPlayer.prepare();//让MediaPlayer进入到准备状态
        }catch (Exception e){
            e.printStackTrace();
        }
        int duration2 = mediaPlayer.getDuration() / 1000;
        int position = mediaPlayer.getCurrentPosition();
        tv_start.setText(calculateTime(position / 1000));
        tv_end.setText(calculateTime(duration2));
    }

    /*
     * 打碟开始
     * */
    private void RecordRotation(){
        //唱片动画
        animator = ObjectAnimator.ofFloat(discsmap,"rotation",0f,360.0f);
        animator.setDuration(10000);//旋转时间
        animator.setInterpolator(new LinearInterpolator());//匀速
        animator.setRepeatCount(-1);//设置动画重复次数(-1代表一直转)
        animator.setRepeatMode(ValueAnimator.RESTART);//动画重复模式
        animator.start();//动画启动
        //指针动画
        animator1 = ObjectAnimator.ofFloat(zhizhenmap,"rotation",-60f,0.0f);
        animator1.setDuration(900);//旋转时间
        animator1.setRepeatCount(0);//就一下
        animator1.start();//动画启动
    }

    /*
     * 打碟停止
     * */
    private void StopRecordRotation(){
        animator.end();
        animator1.end();
    }


    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.play:
                if(!mediaPlayer.isPlaying()){
                    mediaPlayer.start();//开始播放
                    int duration = mediaPlayer.getDuration();//获取音乐总时间
                    seekBar.setMax(duration);//将音乐总时间设置为Seekbar的最大值
                    timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            if(!isSeekbarChaning){
                                seekBar.setProgress(mediaPlayer.getCurrentPosition());
                            }
                        }
                    },0,50);
                    RecordRotation();
                }
                break;
            case R.id.pause:
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.pause();//暂停播放
                }
                StopRecordRotation();
                break;
            case R.id.stop:
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.reset();//停止播放
                    initMediaPlayer();
                }
                StopRecordRotation();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        if(mediaPlayer != null){
            mediaPlayer.stop();
            mediaPlayer.release();
        }
    }
}
