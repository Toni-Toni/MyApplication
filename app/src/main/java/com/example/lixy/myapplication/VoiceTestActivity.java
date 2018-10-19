package com.example.lixy.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

/**
 * @version V1.0
 *          Copyright©2015 ihalma All Rights Reserved.
 * @Description:
 * @author: li.xy
 * @date: 2017/10/1315:08
 */
public class VoiceTestActivity extends Activity {
    private Button buttonLocalAmr,buttonNetAmr,buttonLocalMp3,buttonNet3;

    VoicePlayUtil voicePlayUtil;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_voicetest);

        voicePlayUtil = VoicePlayUtil.getInstance(this);

        buttonLocalAmr = findViewById(R.id.btn_amr_local);
        buttonNetAmr = findViewById(R.id.btn_amr_net);
        buttonLocalMp3 = findViewById(R.id.btn_mp3_local);
        buttonNet3 = findViewById(R.id.btn_mp3_net);

        buttonLocalAmr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                voicePlayUtil.play(VoiceTestActivity.this,R.raw.describe);
            }
        });

        buttonNetAmr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                voicePlayUtil.playMusic("http://120.76.234.30/group1/M00/04/BA/ChrQhlngasuACrNCAAAjps9FQCA815.amr");
            }
        });

        buttonLocalMp3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                voicePlayUtil.play(VoiceTestActivity.this,R.raw.music);
            }
        });

        buttonNet3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                voicePlayUtil.playMusic("http://202.43.91.114/group1/M00/00/0E/yitbclngcWOATQDlADepWhMDoJ48893607");
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (voicePlayUtil != null){
            voicePlayUtil.destory();
        }
    }
}
