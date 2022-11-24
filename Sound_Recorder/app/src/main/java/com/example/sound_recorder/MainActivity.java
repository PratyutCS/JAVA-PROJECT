package com.example.sound_recorder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import java.text.SimpleDateFormat;
import java.util.Date;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private ImageButton srcg;
    private Button nxbtn;
    private MediaPlayer mediaPlayer;
    private MediaRecorder mediaRecorder;
    private String audioSavePath;
    private int i=0,j=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        srcg=findViewById(R.id.srcg);
        nxbtn=findViewById(R.id.nxbtn);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");

        srcg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(i==0){
                    if(permission())
                    {
                        mediaRecorder=new MediaRecorder();
                        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
                        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
                        String cdt = sdf.format(new Date());
                        Log.d("DATE","date is : "+cdt);
                        audioSavePath=Environment.getExternalStorageDirectory().getAbsolutePath().toString()+"/recording"+cdt+".mp3";
                        Log.d("PATH","path is : "+audioSavePath);
                        mediaRecorder.setOutputFile(audioSavePath);

                        try {
                            mediaRecorder.prepare();
                            mediaRecorder.start();
                            srcg.setBackgroundResource(R.drawable.stop);
                            Toast.makeText(MainActivity.this, "Recording Started", Toast.LENGTH_SHORT).show();
                        } catch (IOException e) {
                            Toast.makeText(MainActivity.this, "exception", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }

                    }
                    else{
                        Toast.makeText(MainActivity.this, "permission not granted", Toast.LENGTH_SHORT).show();
                        ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.RECORD_AUDIO,Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
                    }
                    i=1;
                }
                else{
                    try {
                        mediaRecorder.stop();
                        mediaRecorder.release();
                        srcg.setBackgroundResource(R.drawable.start);
                    }
                    catch(Exception e){
                        Toast.makeText(MainActivity.this, "exception caught", Toast.LENGTH_SHORT).show();
                    }
                    Toast.makeText(MainActivity.this, "Recording Stopped", Toast.LENGTH_SHORT).show();
                    i=0;
                }
            }
        });

        nxbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openReader();
            }
        });
    }

    private void openReader() {
        Intent intent=new Intent(this,reader.class);
        startActivity(intent);
    }

    private boolean permission()
    {
        int first= ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.RECORD_AUDIO);
        int second=ActivityCompat.checkSelfPermission(getApplicationContext(),Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if(first== PackageManager.PERMISSION_GRANTED && second==PackageManager.PERMISSION_GRANTED)
        {
            return true;
        }
        else {
            return false;
        }
    }
}