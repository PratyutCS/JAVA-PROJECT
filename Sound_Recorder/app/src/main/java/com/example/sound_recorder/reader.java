package com.example.sound_recorder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class reader extends AppCompatActivity {
    private ListView content;
    private String n="",audioSavePath,playPath;
    private MediaPlayer mediaPlayer;
    private int j=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reader);
        content=(ListView)findViewById(R.id.listview);
        audioSavePath=Environment.getExternalStorageDirectory().getAbsolutePath().toString();

        String path = Environment.getExternalStorageDirectory().getAbsolutePath().toString();
        Log.d("Files", "Path: " + path);
        File directory = new File(path);
        File[] files = directory.listFiles();
        Log.d("Files", "Size: "+ files.length);
        ArrayList<String> arrayList=new ArrayList<>();
        arrayList.add("lol");
        for (int i = 0; i < files.length; i++)
        {
            Log.d("Files", "FileName:" + files[i].getName());
            String name=files[i].getName();
            int len =name.length();
            if(len>=9){
                String cut=name.substring(0,9);
                if(cut.equals("recording")){
                    n=n+name+"\n";
                    arrayList.add(name);
                    Log.d("FOUND","FOUND AT : "+name);
                }
            }
        }
        ArrayAdapter arrayAdapter=new ArrayAdapter(reader.this, android.R.layout.simple_list_item_1,arrayList);
        content.setAdapter(arrayAdapter);
        content.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(mediaPlayer!=null){
                    mediaPlayer.stop();
                    mediaPlayer.release();
                }
                playPath=audioSavePath+"/"+arrayList.get(i).toString();
                Toast.makeText(reader.this,"clicked item path is : "+playPath,Toast.LENGTH_SHORT).show();
                Log.i("path",playPath);
                mediaPlayer=new MediaPlayer();
                try {
                    mediaPlayer.setDataSource(playPath);
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                    //sbtn.setBackgroundResource(R.drawable.pause);
                    Toast.makeText(reader.this, "Playing Started", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    Toast.makeText(reader.this, "audio file not found", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        });
    }
}