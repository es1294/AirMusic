package com.example.es1294.airmusic;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class help extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
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
            Intent intent = new Intent(help.this, profile.class);
            startActivity(intent);
            return false;
        }
        else if (id == R.id.feed){
            Intent intent = new Intent(help.this, feed.class);
            startActivity(intent);
            return false;
        }
        else if (id == R.id.drop_menu){
            Intent intent = new Intent(help.this , MainActivity.class );
            startActivity(intent);
            return false;
        }
        else if(id == R.id.help){
            Intent intent = new Intent(help.this, help.class);
            startActivity(intent);
            return false;
        }

        return super.onOptionsItemSelected(item);
    }

}
