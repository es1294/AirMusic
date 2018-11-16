package com.example.es1294.airmusic;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextClock;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class edit_profile extends AppCompatActivity {

    private Button saveProfileEditButton;
    private ImageView profilePhoto;
    private Button chooseProfilePhotoButton;
    private static final int PICK_IMAGE = 100;
    private Uri imageUri;

    private EditText fullName;
    private EditText about;
    private EditText artistOne;
    private EditText artistTwo;
    private EditText artistThree;
    private EditText artistFour;
    private EditText artistFive;
    private EditText genreOne;
    private EditText genreTwo;
    private EditText genreThree;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        fullName = (EditText) findViewById(R.id.profileNameInput);
        about = (EditText) findViewById(R.id.editProfileAboutMe);
        artistOne = (EditText) findViewById(R.id.editProfileArtist1);
        artistTwo = (EditText) findViewById(R.id.editProfileArtist2);
        artistThree = (EditText) findViewById(R.id.editProfileArtist3);
        artistFour = (EditText) findViewById(R.id.editProfileArtist4);
        artistFive = (EditText) findViewById(R.id.editProfileArtist5);
        genreOne = (EditText) findViewById(R.id.editProfileGenre1);
        genreTwo = (EditText) findViewById(R.id.editProfileGenre2);
        genreThree = (EditText) findViewById(R.id.editProfileGenre3);
        profilePhoto = (ImageView)findViewById(R.id.editProfilePhoto);

        chooseProfilePhotoButton = (Button) findViewById(R.id.editProfilePhotoButton);
        chooseProfilePhotoButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                openGallery();
            }
        });

        saveProfileEditButton = (Button) findViewById(R.id.saveChangesToProfile);
        saveProfileEditButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

               //reference to authenticator
                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                //reference to root of database
                DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();

                //Find the user we need to edit by their userID
                FirebaseUser currentUser = mAuth.getCurrentUser();
                String userID = currentUser.getUid();

                //Write a query to search for that user by their User ID (which is the key authID in db)
                Query getUserToEdit = mRootRef.child("User").orderByChild("authID").equalTo(userID);
                //be sure to use SINGLE VALUE EVENT so that it ONLY reads the data ONCE
                getUserToEdit.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        //gets the matching user (as a list, but only 1 element)
                        for(DataSnapshot userSnapShot: dataSnapshot.getChildren()){
                            FirebaseAuth mAuth = FirebaseAuth.getInstance();
                            FirebaseUser currentUser = mAuth.getCurrentUser();
                            //Exctract the info from the views
                            String nameString = fullName.getText().toString();
                            String aboutString = about.getText().toString();
                            String artOneString = artistOne.getText().toString();
                            String artTwoString = artistTwo.getText().toString();
                            String artThreeString = artistThree.getText().toString();
                            String artFourString = artistFour.getText().toString();
                            String artFiveString = artistFive.getText().toString();
                            String genreOneString = genreOne.getText().toString();
                            String genreTwoString = genreTwo.getText().toString();
                            String genreThreeString = genreThree.getText().toString();
                            //set the constant values (these never change)
                            String idString = currentUser.getUid();
                            String emailString = currentUser.getEmail();
                            //create new user
                            User user = new User();
                            user.setFullName(nameString);
                            user.setAbout(aboutString);
                            user.setArtistOne(artOneString);
                            user.setArtistTwo(artTwoString);
                            user.setArtistThree(artThreeString);
                            user.setArtistFour(artFourString);
                            user.setArtistFive(artFiveString);
                            user.setGenreOne(genreOneString);
                            user.setGenreTwo(genreTwoString);
                            user.setGenreThree(genreThreeString);
                            user.setAuthID(idString);
                            user.setEmail(emailString);
                            //store the new user in the db
                           DatabaseReference editThisUser = userSnapShot.getRef();
                           editThisUser.setValue(user);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                //open the profile
              openProfileView();
            }
        });
    }

    public void openProfileView(){
        Intent intent = new Intent (this, profile.class);
        startActivity(intent);
    }

    private void openGallery(){
        Intent gallery = new Intent (Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && requestCode == PICK_IMAGE){
            imageUri = data.getData();
            profilePhoto.setImageURI(imageUri);
        }
    }


}
