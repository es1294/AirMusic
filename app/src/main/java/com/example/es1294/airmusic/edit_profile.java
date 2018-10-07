package com.example.es1294.airmusic;

import android.content.Intent;
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

import java.util.ArrayList;
import java.util.List;

public class edit_profile extends AppCompatActivity {

    private Button saveProfileEditButton;
    private ImageView profilePhoto;
    private Button chooseProfilePhotoButton;
    private static final int PICK_IMAGE = 100;
    private Uri imageUri;

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
