package com.example.es1294.airmusic;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class profile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

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

        if(id == R.id.help){
            Intent intent = new Intent(profile.this, help.class);
            startActivity(intent);
            return false;
        }
        else if (id == R.id.feed){
            Intent intent = new Intent(profile.this, feed.class);
            startActivity(intent);
            return false;
        }
        else if (id == R.id.drop_menu){
            Intent intent = new Intent(profile.this , MainActivity.class );
            startActivity(intent);
            return false;
        }
        else if(id == R.id.help){
            Intent intent = new Intent(profile.this, help.class);
            startActivity(intent);
            return false;
        }

        return super.onOptionsItemSelected(item);
    }
}
