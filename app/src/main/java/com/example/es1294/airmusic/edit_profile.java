package com.example.es1294.airmusic;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class edit_profile extends AppCompatActivity {

    private Button saveProfileEditButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

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
}
