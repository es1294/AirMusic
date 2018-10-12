package com.example.es1294.airmusic;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class ListOfSongs extends AppCompatActivity {

    public static final String EXTRA_ID ="com.example.es1294.airmusic.EXTRA_ID";

    ListView listSongs;
    List<String> list;
    ListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_songs);

        //listSongs is the container that will list all songs
        //list is an array that will store the fields of the mp3 files

        listSongs = (ListView) findViewById(R.id.song_list);
        list = new ArrayList<>();

        //get the files in raw and put them in list array

        Field[] fields = R.raw.class.getFields();
        for(int i =0; i < fields.length; i++){
            list.add(fields[i].getName());
        }

        //go sets adapter to grab the things from list and preview them in the listView container

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
        listSongs.setAdapter(adapter);

        //once click it'll grab all the information from the specific mp3 file
        //it'll also call a function to open up the music player

        listSongs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                int resID = getResources().getIdentifier(list.get(i), "raw", getPackageName());

                openMusicPlayer(resID);
            }
        });
    }

    //resID = specific song that was click
    //creates a new intent of music player and passed the id of the song to the musicplayer activity

    public void openMusicPlayer(int resID){

        Intent intent = new Intent(this, musicPlayer.class);
        intent.putExtra(EXTRA_ID, resID);
        startActivity(intent);
    }
}
