package com.example.es1294.airmusic;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;

import static android.content.Intent.FLAG_ACTIVITY_REORDER_TO_FRONT;

public class profile extends AppCompatActivity {

    //Textviews, buttons, etc.
    private Button profileEditButton;
    TextView nameView;
    TextView viewAbout;
    TextView artistsView;
    TextView genresView;

    //Firebase references
    //Database
    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference mUserRef = mRootRef.child("User");
    //Authentication
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseUser currentUser;
    private String userID;
    //current user object
    private User u;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        //Hardcoding the profile picture
        ImageView profileView = findViewById(R.id.viewProfilePicture);
        profileView.setImageResource(R.drawable.avatarkorra);

        //linking the textviews
        nameView = (TextView) findViewById(R.id.viewFullName);
        viewAbout = (TextView) findViewById(R.id.viewAboutContent);
        artistsView = (TextView) findViewById(R.id.viewFavoriteArtistsContent);
        genresView = (TextView) findViewById(R.id.viewFavoriteGenreContent);

        //Find the user we need to edit by their userID
        currentUser = mAuth.getCurrentUser();
        userID = currentUser.getUid();

        //listener for the edit button
        profileEditButton = (Button) findViewById(R.id.profileEditButton);
        profileEditButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                openProfileEditActivity();
            }
        });

        //This part is populating the "most recently listened" horizonal scrollbar
        LinearLayout recentSongs = findViewById(R.id.recentSongs);
        LayoutInflater inflater = LayoutInflater.from(this);

        for(int i=1;i<6;i++){
            View view = inflater.inflate(R.layout.recent_songs, recentSongs, false);

            TextView textView = view.findViewById(R.id.textView8);
            textView.setText("Song " + i);

            ImageView imageView = view.findViewById(R.id.defaultNote);
            imageView.setImageResource(R.drawable.default_note);

            recentSongs.addView(view);
        }
        //done populating the horizontal list
    }


    @Override
    protected void onStart(){
        super.onStart();

        //Pull the user information from the database to auto-fill the fields
        //Write a query to get the user from the db
        Query getUserNotEdited = mRootRef.child("User").orderByChild("authID").equalTo(userID);
        //be sure to use SINGLE VALUE EVENT so that it ONLY reads the data ONCE
        getUserNotEdited.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot userSnapShot: dataSnapshot.getChildren()){
                    //get the current user object and fill the edittexts with its info
                    u = userSnapShot.getValue(User.class);
                    nameView.setText(u.getFullName());
                    viewAbout.setText(u.getAbout());
                    //Format the artist string
                    String artistString = u.getArtistOne() + "\n" + u.getArtistTwo() + "\n" +  u.getArtistThree() + "\n" +  u.getArtistFour() + "\n" + u.getArtistFive();
                    artistsView.setText(artistString);
                    String genresString = u.getGenreOne() + "\n" + u.getGenreTwo() + "\n" + u.getGenreThree();
                    genresView.setText(genresString);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void openProfileEditActivity(){
        Intent intent = new Intent(this, edit_profile.class);
        startActivity(intent);
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
            Intent intent = new Intent(this, profile.class);
            startActivity(intent);
            finish();
            return false;
        }
        else if (id == R.id.drop_menu){
            Intent intent = new Intent(this , musicPlayer.class );
            intent.setFlags( FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(intent);
            finish();
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
            finish();
            return false;
        }
        else if(id == R.id.song_list){
            Intent intent= new Intent(this, ListOfSongs.class);
            startActivity(intent);
            finish();
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
