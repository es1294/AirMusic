package com.example.es1294.airmusic;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import android.widget.Toolbar;

public class musicPlayer extends AppCompatActivity {

    Button play_button;     // button on music screen to play the current song
    SeekBar song_progress;  //seekbar that allows user to fast foward or rewind
    SeekBar volume_control; //seekbar that allows user to set the volume
    TextView elapsed_time;  //text that shows the current time on the song
    TextView remaining_time;    //text that shows the remaining time on the song
    MediaPlayer mediaPlayer;    //object that plays the music
    int total_time; //sets the max of the song so we don't scroll pass the actual playing time of the song

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.music_player_activity);

        //instantiating the variables to the contents in the drawable files

        play_button = (Button)findViewById(R.id.play_button);
        elapsed_time = (TextView) findViewById(R.id.elapsed_time);
        remaining_time = (TextView) findViewById(R.id.remaining_time);

        //creates the mediaPlayer object to play the current song
        //sets the volume to half
        //and gets the duration of the song

        mediaPlayer = mediaPlayer.create(this, R.raw.blue_dream);
        mediaPlayer.setLooping(true);
        mediaPlayer.seekTo(0);
        mediaPlayer.setVolume(0.5f , 0.5f);
        total_time = mediaPlayer.getDuration();

        //implements seong progress seekbar
        //instantiates the variable song_progress to the seekbar in the MainActivity.xml

        song_progress = (SeekBar) findViewById(R.id.song_progress);
        song_progress.setMax(total_time);
        song_progress.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser){
                    mediaPlayer.seekTo(progress);
                    song_progress.setProgress(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        //Volume Bar

        volume_control = (SeekBar) findViewById(R.id.volume_control);

        //changes volumes on volume seekbar

        volume_control.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                float volumeNumber = progress /100f;
                mediaPlayer.setVolume(volumeNumber, volumeNumber);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        //Thread to update position bar & time labels

        new Thread(new Runnable() {
            @Override
            public void run() {

                while(mediaPlayer!= null){
                    try{
                        Message msg = new Message();
                        msg.what = mediaPlayer.getCurrentPosition();
                        handler.sendMessage(msg);

                        Thread.sleep(1000);
                    }catch (InterruptedException e){}
                }
            }
        }).start();
    }

    private Handler handler = new Handler(){
        public void handleMessage( Message msg){
            int currentPosition = msg.what;

            //update position bar

            song_progress.setProgress(currentPosition);

            //update labels

            String elapsedTime = createTimeLabel(currentPosition);
            elapsed_time.setText(elapsedTime);

            String remainingTime = createTimeLabel(total_time - currentPosition);
            remaining_time.setText("- " +remainingTime);
        }
    };

    //creates timelabels to display on song
    public String createTimeLabel(int time){
        String timeLabel ="";
        int minute = time /1000 /60;
        int seconds = time / 1000 % 60;

        timeLabel = minute + ":";

        if(seconds < 10){
            timeLabel += "0";
        }
        timeLabel += seconds;

        return timeLabel;
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
            Intent intent = new Intent(musicPlayer.this, profile.class);
            startActivity(intent);
            return false;
        }
        else if (id == R.id.drop_menu){
            Intent intent = new Intent(musicPlayer.this , musicPlayer.class );
            startActivity(intent);
            return false;
        }
        else if (id == R.id.feed){
            Intent intent = new Intent(musicPlayer.this, feed.class);
            startActivity(intent);
            return false;
        }
        else if(id == R.id.help){
            Intent intent = new Intent(musicPlayer.this, help.class);
            startActivity(intent);
            return false;
        }

        return super.onOptionsItemSelected(item);
    }
}