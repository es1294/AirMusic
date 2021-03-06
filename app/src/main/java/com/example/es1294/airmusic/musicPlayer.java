package com.example.es1294.airmusic;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;


public class musicPlayer extends AppCompatActivity {

    List<Field> fields;     //the mp3 fields from the raw file
    float volumeNumber;     //sets how loud the music will be played at
    int resID;              //id of song that was clicked in ListOfSongs class
    ArrayList<Integer> allSongIDs;  //list of all the songs ids
    Button play_button;     // button on music screen to play the current song
    SeekBar song_progress;  //seekbar that allows user to fast foward or rewind
    SeekBar volume_control; //seekbar that allows user to set the volume
    TextView elapsed_time;  //text that shows the current time on the song
    TextView remaining_time;    //text that shows the remaining time on the song
    MediaPlayer mediaPlayer;    //object that plays the music
    int total_time; //sets the max of the song so we don't scroll pass the actual playing time of the song
    private FirebaseUser currentUser; //The current user signed in to the app
    private String userID; //Unique key of signed in user
    private User u; //user object for current user
    private int currentPosition;

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();  //reference to authenticator
    private DatabaseReference mRootRef  = FirebaseDatabase.getInstance().getReference();; //reference to root of database
    private DatabaseReference mCurrentSongRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //musicPlayer ma = this;

        //Find the currently logged in user
        currentUser = mAuth.getCurrentUser();
        userID = currentUser.getUid();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.music_player_activity);

        Intent intent = getIntent();
        resID = intent.getIntExtra(ListOfSongs.EXTRA_ID, 0);
        fields = Arrays.asList(R.raw.class.getFields());
        allSongIDs = new ArrayList<>();

        Query getUserNotEdited = mRootRef.child("User").orderByChild("authID").equalTo(userID);
        getUserNotEdited.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(mediaPlayer != null){
                    mediaPlayer.seekTo(0);
                    mediaPlayer.start();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //if user decides to go straight to music page
        //without choosing a song from the list
        //then play a random song

        if(resID == 0){

            Random random = new Random();
            int anysong = random.nextInt(fields.size());
            String songname = fields.get(anysong).getName();
            resID = getResources().getIdentifier(songname, "raw", getPackageName());
        }

        //instantiating the variables to the contents in the drawable files

        play_button = (Button)findViewById(R.id.play_button);
        elapsed_time = (TextView) findViewById(R.id.elapsed_time);
        remaining_time = (TextView) findViewById(R.id.remaining_time);

        //creates the mediaPlayer object to play the current song
        //sets the volume to half
        //and gets the duration of the song

        mediaPlayer = mediaPlayer.create(this,resID);
        mediaPlayer.setLooping(true);
        mediaPlayer.seekTo(0);
        volumeNumber = 0.5f;
        setConfigs();
        mediaPlayer.start();

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
                volumeNumber = progress /100f;
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

        //loads the list of songs in the background to be able to skip songs

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {

                for(int i = 0; i< fields.size();i++){
                    String songName = fields.get(i).getName();
                    int tempID = getResources().getIdentifier(songName, "raw", getPackageName());
                    allSongIDs.add(tempID);
                }

            }
        });


    }

    @Override
    protected void onStart(){
        super.onStart();

        //Write a query to get the user from the db
        Query getUserNotEdited = mRootRef.child("User").orderByChild("authID").equalTo(userID);
        //be sure to use SINGLE VALUE EVENT so that it ONLY reads the data ONCE
        getUserNotEdited.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot userSnapShot: dataSnapshot.getChildren()){
                    //get the current user object and fill the edittexts with its info
                    u = userSnapShot.getValue(User.class);
                    u.setCurrentSong(resID);
                    //store the new user in the db
                    DatabaseReference editThisUser = userSnapShot.getRef();
                    editThisUser.setValue(u);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private Handler handler = new Handler(){
        public void handleMessage( Message msg){
            currentPosition = msg.what;

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

    //when in music activity pressing play plays song.
    //then sets background of button to pause

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

    public void nextButtonClick(View view){

        int currentSong = allSongIDs.indexOf(resID);
       // mediaPlayer.reset();
        mediaPlayer.release();
        mediaPlayer = null;

            if(currentSong == allSongIDs.size()-1){
                resID = allSongIDs.get(0);
                mediaPlayer = mediaPlayer.create(this,resID);
                setConfigs();
                mediaPlayer.start();
                Query getUserNotEdited = mRootRef.child("User").orderByChild("authID").equalTo(userID);
                //be sure to use SINGLE VALUE EVENT so that it ONLY reads the data ONCE
                getUserNotEdited.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot userSnapShot: dataSnapshot.getChildren()){
                            //get the current user object and fill the edittexts with its info
                            u = userSnapShot.getValue(User.class);
                            u.setCurrentSong(resID);
                            //store the new user in the db
                            DatabaseReference editThisUser = userSnapShot.getRef();
                            editThisUser.setValue(u);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
            else{
                resID = allSongIDs.get(currentSong +1);
                mediaPlayer = mediaPlayer.create(this,resID);
                setConfigs();
                mediaPlayer.start();
                Query getUserNotEdited = mRootRef.child("User").orderByChild("authID").equalTo(userID);
                //be sure to use SINGLE VALUE EVENT so that it ONLY reads the data ONCE
                getUserNotEdited.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot userSnapShot: dataSnapshot.getChildren()){
                            //get the current user object and fill the edittexts with its info
                            u = userSnapShot.getValue(User.class);
                            u.setCurrentSong(resID);
                            //store the new user in the db
                            DatabaseReference editThisUser = userSnapShot.getRef();
                            editThisUser.setValue(u);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

    }

    public void prevButtonClick(View view){

        int currentSong = allSongIDs.indexOf(resID);
        mediaPlayer.release();
        mediaPlayer = null;

        if(currentSong == 0){
            resID = allSongIDs.get(allSongIDs.size()-1);
            mediaPlayer = mediaPlayer.create(this,resID);
            setConfigs();
            mediaPlayer.start();
            Query getUserNotEdited = mRootRef.child("User").orderByChild("authID").equalTo(userID);
            //be sure to use SINGLE VALUE EVENT so that it ONLY reads the data ONCE
            getUserNotEdited.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for(DataSnapshot userSnapShot: dataSnapshot.getChildren()){
                        //get the current user object and fill the edittexts with its info
                        u = userSnapShot.getValue(User.class);
                        u.setCurrentSong(resID);
                        //store the new user in the db
                        DatabaseReference editThisUser = userSnapShot.getRef();
                        editThisUser.setValue(u);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        else{
            resID = allSongIDs.get(currentSong -1);
            mediaPlayer = mediaPlayer.create(this,resID);
            setConfigs();
            mediaPlayer.start();
            Query getUserNotEdited = mRootRef.child("User").orderByChild("authID").equalTo(userID);
            //be sure to use SINGLE VALUE EVENT so that it ONLY reads the data ONCE
            getUserNotEdited.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for(DataSnapshot userSnapShot: dataSnapshot.getChildren()){
                        //get the current user object and fill the edittexts with its info
                        u = userSnapShot.getValue(User.class);
                        u.setCurrentSong(resID);
                        //store the new user in the db
                        DatabaseReference editThisUser = userSnapShot.getRef();
                        editThisUser.setValue(u);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }


    }

    public void setConfigs(){
        total_time = mediaPlayer.getDuration();
        mediaPlayer.setVolume(volumeNumber,volumeNumber);
        play_button.setBackgroundResource(R.drawable.pause_button);


    }

    public void stopPlaying(){
        if(mediaPlayer != null){
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        resID = 0;
        Query getUserNotEdited = mRootRef.child("User").orderByChild("authID").equalTo(userID);
        //be sure to use SINGLE VALUE EVENT so that it ONLY reads the data ONCE
        getUserNotEdited.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot userSnapShot: dataSnapshot.getChildren()){
                    //get the current user object and fill the edittexts with its info
                    u = userSnapShot.getValue(User.class);
                    u.setCurrentSong(resID);
                    //store the new user in the db
                    DatabaseReference editThisUser = userSnapShot.getRef();
                    editThisUser.setValue(u);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        stopPlaying();
    }

    //for all classes in menu, this makes the dropdown menu available

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.drop_menu, menu);
        return true;
    }

    //depending on which item was clicked in the menu, go to that activity and start it

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.profile){
            Intent intent = new Intent(this, profile.class);
            startActivity(intent);
            return false;
        }
        else if (id == R.id.drop_menu){
            Toast.makeText(this,"You're already in Music",Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (id == R.id.feed){
            Intent intent = new Intent(this, feed.class);
            startActivity(intent);
            return false;
        }
        else if(id == R.id.help){
            Intent intent = new Intent(this, help.class);
            startActivity(intent);
            return false;
        }
        else if(id == R.id.song_list){
            Intent intent= new Intent(this, ListOfSongs.class);
            startActivity(intent);
        } else if (id == R.id.logOut) {
            Intent intent = new Intent(this, MainActivity.class);
            FirebaseAuth.getInstance().signOut();
            startActivity(intent);
            finish();
            return false;
        }

        return super.onOptionsItemSelected(item);
    }
}
