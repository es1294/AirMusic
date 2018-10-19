package com.example.es1294.airmusic;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    Button login;     // button to log in
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
//        listView = (ListView) findViewById(R.id.song_list);
//        ArrayList<File> mysongs = findMusic(Environment.getExternalStorageDirectory());
//
//        for(int i=0; i<mysongs.size();i++){
//            toast(mysongs.get(i).toString());
//        }

        login = (Button) findViewById(R.id.login_button);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openProfile();
            }
        });

    }

//    public ArrayList<File> findMusic (File root){
//        ArrayList<File> arrayList = new ArrayList<File>();
//        File[] files =  root.listFiles();
//
//        for(File singleFile : files){       //Checks for files in directory
//            if(singleFile.isDirectory()){
//                arrayList.addAll(findMusic(singleFile));
//            }
//            else
//                if(singleFile.getName().endsWith(".mp3")){
//                arrayList.add(singleFile);
//                }
//
//
//
//        }
//        return arrayList;
//    }
//
//    public void toast (String SongName){
//        Toast.makeText(getApplicationContext(),SongName,Toast.LENGTH_SHORT).show();
//    }

    public void openProfile(){
        Intent intent = new Intent(this, profile.class);
        startActivity(intent);
        finish();
    }
}
