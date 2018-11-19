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

    //Buttons
    private Button saveProfileEditButton;
    private ImageView profilePhoto;
    private Button chooseProfilePhotoButton;
    private static final int PICK_IMAGE = 100;
    private Uri imageUri;

    //Text Fields
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

    //Text field inputs
    private String nameString;
    private String aboutString;
    private String artOneString;
    private String artTwoString;
    private String artThreeString;
    private String artFourString;
    private String artFiveString;
    private String genreOneString;
    private String genreTwoString;
    private String genreThreeString;

    //reference to authenticator
    private FirebaseAuth mAuth;
    //reference to root of database
    private DatabaseReference mRootRef;

    //Firebase info
    private FirebaseUser currentUser;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        //link to the EditTexts
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

        //reference to firebase authenticator
        mAuth = FirebaseAuth.getInstance();
        //reference to root of database
        mRootRef = FirebaseDatabase.getInstance().getReference();

        //Find the user we need to edit by their userID
        currentUser = mAuth.getCurrentUser();
        userID = currentUser.getUid();

        //Pull the user information from the database to auto-fill the fields
        //Write a query to get the user from the db
        Query getUserNotEdited = mRootRef.child("User").orderByChild("authID").equalTo(userID);
        //be sure to use SINGLE VALUE EVENT so that it ONLY reads the data ONCE
        getUserNotEdited.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot userSnapShot: dataSnapshot.getChildren()){
                    //get the current user object and fill the edittexts with its info
                    User u = userSnapShot.getValue(User.class);
                    fullName.setText(u.getFullName());
                    about.setText(u.getAbout());
                    artistOne.setText(u.getArtistOne());
                    artistTwo.setText(u.getArtistThree());
                    artistThree.setText(u.getArtistThree());
                    artistFour.setText(u.getArtistFour());
                    artistFive.setText(u.getArtistFive());
                    genreOne.setText(u.getGenreOne());
                    genreOne.setText(u.getGenreTwo());
                    genreThree.setText(u.getGenreThree());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //This chunk of code opens the gallery to select a photo
        chooseProfilePhotoButton = (Button) findViewById(R.id.editProfilePhotoButton);
        chooseProfilePhotoButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                openGallery();
            }
        });

        //Following code executes when save changes button is clicked
        saveProfileEditButton = (Button) findViewById(R.id.saveChangesToProfile);
        saveProfileEditButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //Write a query to search for that user by their User ID (which is the key authID in db)
                Query getUserToEdit = mRootRef.child("User").orderByChild("authID").equalTo(userID);
                //be sure to use SINGLE VALUE EVENT so that it ONLY reads the data ONCE
                getUserToEdit.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        //gets the matching user (as a list, but only 1 element)
                        for(DataSnapshot userSnapShot: dataSnapshot.getChildren()){

                            //Extract the String values from the EditText
                            nameString = fullName.getText().toString();
                            aboutString = about.getText().toString();
                            artOneString = artistOne.getText().toString();
                            artTwoString = artistTwo.getText().toString();
                            artThreeString = artistThree.getText().toString();
                            artFourString = artistFour.getText().toString();
                            artFiveString = artistFive.getText().toString();
                            genreOneString = genreOne.getText().toString();
                            genreTwoString = genreTwo.getText().toString();
                            genreThreeString = genreThree.getText().toString();

                            //set the constant values (these never change after profile creation)
                            String idString = currentUser.getUid();
                            String emailString = currentUser.getEmail();

                            //create new user with the new information
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
            //Edits complete - open the profile page
              openProfileView();
            }
        });
    }

    //Starts the profile intent
    public void openProfileView(){
        Intent intent = new Intent (this, profile.class);
        startActivity(intent);
    }

    //Opens the phone photo gallery
    private void openGallery(){
        Intent gallery = new Intent (Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }

    //Code that executes when a photo is selected from gallery (puts selected gallry image in the imageView)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && requestCode == PICK_IMAGE){
            imageUri = data.getData();
            profilePhoto.setImageURI(imageUri);
        }
    }


}
