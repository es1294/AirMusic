package com.example.es1294.airmusic;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
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
        //get the user ID
       /* ManageUser manage = new ManageUser(getApplicationContext());
        HashMap<String,String> idPair = manage.getUserId();
        String idString = idPair.get("userId");
        Integer id = Integer.parseInt(idString);
        User user = helper.getUserFromDB(id);

        fullName.setText(user.getFullName());
        about.setText(user.getAbout());
        artistOne.setText(user.getArtistOne());
        artistTwo.setText(user.getArtistTwo());
        artistThree.setText(user.getArtistThree());
        artistFour.setText(user.getArtistFour());
        artistFive.setText(user.getArtistFive());
        genreOne.setText(user.getGenreOne());
        genreTwo.setText(user.getGenreTwo());
        genreThree.setText(user.getGenreThree());

        byte[] bytePhoto = user.getProfilePhoto();
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytePhoto, 0, bytePhoto.length);
        profilePhoto.setImageBitmap(bitmap);

        */

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
            //All of the following code is to update the database info

            //Exctract the info from the views
                String nnameString = fullName.getText().toString();
                String naboutString = about.getText().toString();
                String nartOneString = artistOne.getText().toString();
                String nartTwoString = artistTwo.getText().toString();
                String nartThreeString = artistThree.getText().toString();
                String nartFourString = artistFour.getText().toString();
                String nartFiveString = artistFive.getText().toString();
                String ngenreOneString = genreOne.getText().toString();
                String ngenreTwoString = genreTwo.getText().toString();
                String ngenreThreeString = genreThree.getText().toString();
               // Drawable photoDrawable = profilePhoto.getDrawable();
            //replace apostrophe
                String nameString = formatIntoSQL(nnameString);
                String aboutString = formatIntoSQL(naboutString);
                String artOneString = formatIntoSQL(nartOneString);
                String artTwoString = formatIntoSQL(nartTwoString);
                String artThreeString = formatIntoSQL(nartThreeString);
                String artFourString = formatIntoSQL(nartFourString);
                String artFiveString = formatIntoSQL(nartFiveString);
                String genreOneString = formatIntoSQL(ngenreOneString);
                String genreTwoString = formatIntoSQL(ngenreTwoString);
                String genreThreeString = formatIntoSQL(ngenreThreeString);

                //Create a new user object with the values in current fields
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
            //convert drawable into a bitmap into a byte array
               /* BitmapDrawable bitmapDrawable = ((BitmapDrawable) photoDrawable);
                Bitmap bitmap = bitmapDrawable.getBitmap();
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                //changed compress to JPEG format, with high enough quality that it looks ok
                //this fixes the bug :)
                bitmap.compress(Bitmap.CompressFormat.JPEG, 30, stream);
                byte[] photoData = stream.toByteArray();
                user.setProfilePhoto(photoData);*/

               /* //get the user ID
                ManageUser manage = new ManageUser(getApplicationContext());
                HashMap<String,String> idPair = manage.getUserId();
                String idString = idPair.get("userId");
                Integer id = Integer.parseInt(idString);
                //edit the user
                helper.editUserEntry(user, id);*/

               //edit the user in the Firebase Realtime Database

              openProfileView();
            }
        });
    }

    public String formatIntoSQL(String string){
        String newString = string.replaceAll("'", "''");
        return newString;
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
