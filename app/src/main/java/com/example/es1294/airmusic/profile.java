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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import static android.content.Intent.FLAG_ACTIVITY_REORDER_TO_FRONT;

public class profile extends AppCompatActivity {

    private Button profileEditButton;
    DatabaseHelper helper = new DatabaseHelper(this);
    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference mConditionRef = mRootRef.child("Profile");
    TextView nameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        profileEditButton = (Button) findViewById(R.id.profileEditButton);
        profileEditButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                openProfileEditActivity();
            }
        });

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

        //SETTING ALL VIEWS TO SHOW DATABASE INFORMATION
       //get the id from MainActivity intent
       /* Bundle bundle = getIntent().getExtras();
        int id = bundle.getInt("idNumber");*/

      ManageUser manage = new ManageUser(getApplicationContext());
        HashMap<String,String> idPair = manage.getUserId();
        String idString = idPair.get("userId");
        Integer id = Integer.parseInt(idString);

        Toast idMessage = Toast.makeText(profile.this, "ID: "+ id, Toast.LENGTH_SHORT);
        idMessage.show();

       User user = helper.getUserFromDB(id);

       byte[] bytePhoto = user.getProfilePhoto();
       Bitmap bitmap = BitmapFactory.decodeByteArray(bytePhoto, 0, bytePhoto.length);

       ImageView profileView = findViewById(R.id.viewProfilePicture);
       profileView.setImageBitmap(bitmap);

       String fullName = user.getFullName();
       nameView = (TextView) findViewById(R.id.viewFullName);
 //      nameView.setText(fullName);

        String about = user.getAbout();
        TextView viewAbout = (TextView) findViewById(R.id.viewAboutContent);
        viewAbout.setText(about);

        //format the artists
        String artOne = user.getArtistOne();
        String artTwo = user.getArtistTwo();
        String artThree= user.getArtistThree();
        String artFour = user.getArtistFour();
        String artFive = user.getArtistFive();
        String formatArtistsString = artOne+"\n"+artTwo+"\n"+artThree+"\n"+artFour+"\n"+artFive+"\n";

        TextView artistsView = (TextView) findViewById(R.id.viewFavoriteArtistsContent);
        artistsView.setText(formatArtistsString);

        //format the genres
        String genOne = user.getGenreOne();
        String genTwo = user.getGenreTwo();
        String genThree = user.getGenreThree();
        String formatGenresString = genOne+"\n"+genTwo+"\n"+genThree+"\n";

        TextView genresView = (TextView) findViewById(R.id.viewFavoriteGenreContent);
        genresView.setText(formatGenresString);

    }

    @Override
    protected void onStart(){
        super.onStart();

        mConditionRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String text = dataSnapshot.getValue(String.class);
                nameView.setText(text);
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
        }

        return super.onOptionsItemSelected(item);
    }
}
