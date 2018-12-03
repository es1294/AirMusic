package com.example.es1294.airmusic;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
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

import java.util.ArrayList;
import java.util.Map;

import static android.content.Intent.FLAG_ACTIVITY_REORDER_TO_FRONT;
import static com.example.es1294.airmusic.ListOfSongs.EXTRA_ID;


public class feed extends AppCompatActivity {

    private ArrayList<Friends> array = new ArrayList<>();
    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    User u;
    //Authentication
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseUser currentUser;
    private String userID;

    private int feedSong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        //find the current user
        currentUser = mAuth.getCurrentUser();
        userID = currentUser.getUid();

        Query getUserNotEdited = mRootRef.child("User");
        //be sure to use SINGLE VALUE EVENT so that it ONLY reads the data ONCE
        getUserNotEdited.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot userSnapShot: dataSnapshot.getChildren()){
                    User u = userSnapShot.getValue(User.class);
                    String fullName = u.getFullName();
                    Friends f = new Friends(fullName);
                    array.add(f);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        ListView list = findViewById(R.id.feed_view);
        Feed_List_Adapter adapter = new Feed_List_Adapter(this, R.layout.adapter_view_layout, array);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("feed","onItemClick: array " + array.get(i).getName());
                Toast.makeText(feed.this, "You clicked on: " + array.get(i).getName(), Toast.LENGTH_SHORT).show();
                String clickedUser = array.get(i).getName();
                //Get the song the other user is listening to
                Query getClickedUserSong = mRootRef.child("User").orderByChild("fullName").equalTo(clickedUser);
                //be sure to use SINGLE VALUE EVENT so that it ONLY reads the data ONCE
                getClickedUserSong.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot userSnapShot: dataSnapshot.getChildren()){
                            u = userSnapShot.getValue(User.class);
                            feedSong = u.getCurrentSong();
                            openMusicPlayer(feedSong);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                //Set the current user song to the clicked user
               /* Query setCurrentSong = mRootRef.child("User").orderByChild("authID").equalTo(userID);
                //be sure to use SINGLE VALUE EVENT so that it ONLY reads the data ONCE
                setCurrentSong.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot userSnapShot: dataSnapshot.getChildren()){
                            //get the current user object and fill the edittexts with its info
                            u = userSnapShot.getValue(User.class);
                            u.setCurrentSong(feedSong);
                            DatabaseReference editThisUser = userSnapShot.getRef();
                            editThisUser.setValue(u);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });*/

            }
        });

    }

    public void openMusicPlayer(int resID){

        Intent intent = new Intent(this, musicPlayer.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(EXTRA_ID, resID);
        startActivity(intent);
        finish();
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

   /* private void collectData(Map<String, Object> users){
        for(Map.Entry<String, Object> entry : users.entrySet()){

            Map singleUser = (Map) entry.getValue();
            Friends f = new Friends((String) singleUser.get("fullName"));
            array.add(f);
        }
    }*/
}
