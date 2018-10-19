package com.example.lixy.myapplication;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.graphics.drawable.AnimationDrawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.text.TextUtils;
import android.util.Log;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;


/**
 * @version V1.0
 *          Copyright©2015 ihalma All Rights Reserved.
 * @Description:
 * @author: li.xy
 * @date: 2016/8/3117:31
 */
public class VoicePlayUtil {
    private final static String TAG = VoicePlayUtil.class.getSimpleName();
    private MediaPlayer mediaPlayer;
    /**当前播放 */
    private TextView voiceText;
    private AnimationDrawable animDrawable;


    private Animation rotateAnim;
    private ImageView voiceImage;
    private Context context;
    private VoicePlayUtil(Context context){
        this.context = context;
        //AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        //Log.d(TAG,audioManager.getStreamVolume(AudioManager.STREAM_MUSIC) + " " + audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC));
        //audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,100,0);
    }

    public static VoicePlayUtil getInstance(Context context){
        return new VoicePlayUtil(context);
    }

    public synchronized void start(TextView view, String path){
        if (TextUtils.isEmpty(path) || view == null) {
            return;
        }
        //先停掉上一个播放
        if (((animDrawable != null && animDrawable.isRunning()) || (mediaPlayer != null && mediaPlayer.isPlaying())) && view.equals(voiceText)) {
            stopPlay();
        } else {
            stopPlay();
            this.voiceText = view;
            animDrawable = (AnimationDrawable) ((view.getCompoundDrawables())[0]);
            animDrawable.start();
            playMusic(path);
        }
    }

    public synchronized void start(ImageView view, String path){
        if (TextUtils.isEmpty(path) || view == null) {
            return;
        }
        //先停掉上一个播放
        if (((animDrawable != null && animDrawable.isRunning()) || (mediaPlayer != null && mediaPlayer.isPlaying())) && view.equals(voiceText)) {
            stopPlay();
        } else {
            stopPlay();
            this.voiceImage = view;
            animDrawable = (AnimationDrawable) ((view.getDrawable()));
            animDrawable.start();
            playMusic(path);
        }
    }


    /**
     * 测试
     * @param context
     * @param resId
     */
    public void play(final Context context, int resId){
        if (mediaPlayer != null) {
            try {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                }
            } catch (IllegalStateException e) {
                e.printStackTrace();
            }
            mediaPlayer.release();
            mediaPlayer = null;
        }
        /*AssetManager am = context.getAssets();
        try {
            AssetFileDescriptor afd = am.openFd("music.mp3");
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(afd.getFileDescriptor());
            mediaPlayer.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setVolume(0.2f,0.2f);
        AssetFileDescriptor file = context.getResources().openRawResourceFd(resId);
        try {
            mediaPlayer.setDataSource(file.getFileDescriptor(), file.getStartOffset(),
                    file.getLength());
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.prepare();
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
            ToastUtil.toasts(context, "播放失败：" + e.getMessage());
        }
        //mediaPlayer.setVolume(1.0f, 1.0f);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();

        /*mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                ToastUtil.toasts(context, "onPrepared");
                mediaPlayer.start();
                mediaPlayer.setLooping(true);
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_VOICE_CALL);

            }
        });
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

            @Override
            public void onCompletion(MediaPlayer mp) {
                //stopPlay();
                //mediaPlayer.start();
            }
        });*/
        mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {

            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                ToastUtil.toasts(context, "播放失败：" + what + "/" + extra);
                //stopPlay();
                return false;
            }
        });

    }

    private synchronized void stopPlay() {
        if (mediaPlayer != null) {
            try {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                }
            } catch (IllegalStateException e) {
                e.printStackTrace();
            } finally {
                mediaPlayer.release();
                mediaPlayer = null;
            }
        }
        if (animDrawable != null && animDrawable.isRunning()) {
            animDrawable.stop();
            animDrawable.selectDrawable(0);
        }
        if (voiceImage != null){
            voiceImage.clearAnimation();
        }
    }

    public void playMusic(String voiceUrl) {
        if (TextUtils.isEmpty(voiceUrl)) {
            ToastUtil.toasts(context, "没有音频文件");
            return;
        }

        if (mediaPlayer != null) {
            stopPlay();
        }
        mediaPlayer = new MediaPlayer();

        try {
            Log.d(TAG,"voiceUrl=====" + voiceUrl);
            mediaPlayer.reset();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setDataSource(voiceUrl);
            mediaPlayer.prepareAsync();
            mediaPlayer.setVolume(1.0f,1.0f);

            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mediaPlayer.setLooping(true);
                    mediaPlayer.start();
                }
            });
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

                @Override
                public void onCompletion(MediaPlayer mp) {
                    stopPlay();
                }
            });
            mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {

                @Override
                public boolean onError(MediaPlayer mp, int what, int extra) {
                    ToastUtil.toasts(context, "播放失败" + what + "/" + extra);
                    stopPlay();
                    return false;
                }
            });
        } catch (Exception e) {
            ToastUtil.toasts(context, "播放失败：" + e.getMessage());
            stopPlay();
            e.printStackTrace();
        }

    }

    public void destory(){
        stopPlay();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
        animDrawable = null;
        rotateAnim = null;
        voiceImage = null;
    }

}
