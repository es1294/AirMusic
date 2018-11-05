package com.example.es1294.airmusic;

import android.content.Intent;
import android.graphics.Bitmap;
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

    DatabaseHelper helper = new DatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

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
              //Get all of the Views
                EditText fullName = (EditText) findViewById(R.id.profileNameInput);
                EditText about = (EditText) findViewById(R.id.editProfileAboutMe);
                EditText artistOne = (EditText) findViewById(R.id.editProfileArtist1);
                EditText artistTwo = (EditText) findViewById(R.id.editProfileArtist2);
                EditText artistThree = (EditText) findViewById(R.id.editProfileArtist3);
                EditText artistFour = (EditText) findViewById(R.id.editProfileArtist4);
                EditText artistFive = (EditText) findViewById(R.id.editProfileArtist5);
                EditText genreOne = (EditText) findViewById(R.id.editProfileGenre1);
                EditText genreTwo = (EditText) findViewById(R.id.editProfileGenre2);
                EditText genreThree = (EditText) findViewById(R.id.editProfileGenre3);
                ImageView photo = (ImageView) findViewById(R.id.editProfilePhoto);

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
                Drawable photoDrawable = photo.getDrawable();


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
                BitmapDrawable bitmapDrawable = ((BitmapDrawable) photoDrawable);
                Bitmap bitmap = bitmapDrawable.getBitmap();
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
                byte[] photoData = stream.toByteArray();
                user.setProfilePhoto(photoData);

                //get the user ID
                ManageUser manage = new ManageUser(getApplicationContext());
                HashMap<String,String> idPair = manage.getUserId();
                String idString = idPair.get("userId");
                Integer id = Integer.parseInt(idString);
                //edit the user
                helper.editUserEntry(user, id);

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
