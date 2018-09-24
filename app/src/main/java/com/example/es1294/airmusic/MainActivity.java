package com.example.es1294.airmusic;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button play_button;
    SeekBar song_progress;
    SeekBar volume_control;
    TextView elapsed_time;
    TextView remaining_time;
    MediaPlayer mediaPlayer;
    int total_time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        play_button = (Button)findViewById(R.id.play_button);
        elapsed_time = (TextView) findViewById(R.id.elapsed_time);
        remaining_time = (TextView) findViewById(R.id.remaining_time);

        mediaPlayer = mediaPlayer.create(this, R.raw.blue_dream);
        mediaPlayer.setLooping(true);
        mediaPlayer.seekTo(0);
        mediaPlayer.setVolume(0.5f , 0.5f);
        total_time = mediaPlayer.getDuration();

        song_progress = (SeekBar) findViewById(R.id.song_progress);
        song_progress.setMax(total_time);

        volume_control = (SeekBar) findViewById(R.id.volume_control);
    }

    public void playButtonClick(View view){
        if(!mediaPlayer.isPlaying()){
            mediaPlayer.start();
            play_button.setBackgroundResource(R.drawable.pause_button);
        }
        else{
            mediaPlayer.pause();
            play_button.setBackgroundResource(R.drawable.play_button);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.drop_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.profile){
            Intent intent = new Intent(MainActivity.this, profile.class);
            startActivity(intent);
            return false;
        }
        else if (id == R.id.drop_menu){
            Intent intent = new Intent(MainActivity.this , MainActivity.class );
            startActivity(intent);
            return false;
        }
        else if (id == R.id.feed){
            Intent intent = new Intent(MainActivity.this, feed.class);
            startActivity(intent);
            return false;
        }
        else if(id == R.id.help){
            Intent intent = new Intent(MainActivity.this, help.class);
            startActivity(intent);
            return false;
        }

        return super.onOptionsItemSelected(item);
    }
}
