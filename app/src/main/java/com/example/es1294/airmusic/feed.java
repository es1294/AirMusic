package com.example.es1294.airmusic;

import android.content.Intent;
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

import java.util.ArrayList;


public class feed extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);
        ListView list = findViewById(R.id.feed_view);
        final ArrayList<Friends> array = new ArrayList<>();
        Friends a = new Friends("Cloud", "Radioactive", "Imagine Dragons", "drawable://" + R.drawable.cloud);
        Friends b = new Friends("Dovahkin", "Sovngarde", "Jermey Soule", "drawable://" + R.drawable.riften);
        Friends c = new Friends("Lara Croft", "Cherry Bomb", "The Runaways", "drawable://" + R.drawable.shadow);
        Friends d = new Friends("Thor ", "Immigrant Song", "Led Zeppelin", "drawable://" + R.drawable.godofhammers);
        Friends e = new Friends("Iron man", "Highway to Hell", "AC/DC", "drawable://" + R.drawable.ironman);
        Friends f = new Friends("Loki ", "Bitch Better Have My Money", "Rhianna", "drawable://" + R.drawable.lokiliesmith);
        Friends g = new Friends("Daenerys", "Queen", "Janelle Monae", "drawable://" + R.drawable.motherofdragons);
        Friends h = new Friends("Avatar Korra", "Turn It Down For What", "DJ Snake, Lil Jon", "drawable://" + R.drawable.avatarkorra);
        Friends i = new Friends("Hella", "Chun - Li", "Nicki Minaj", "drawable://" + R.drawable.hella);
        Friends j = new Friends("Terry Mcginnis", "Batman Beyond Theme", "Kristopher Carter", "drawable://" + R.drawable.terry);
        Friends k = new Friends("Defenders", "We Will Rock You", "Queen", "drawable://" + R.drawable.defenders);

        array.add(a);
        array.add(b);
        array.add(c);
        array.add(d);
        array.add(e);
        array.add(f);
        array.add(g);
        array.add(h);
        array.add(i);
        array.add(j);
        array.add(k);


        Feed_List_Adapter adapter = new Feed_List_Adapter(this, R.layout.adapter_view_layout, array);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("feed","onItemClick: array " + array.get(i).getName());
                Toast.makeText(feed.this, "You clicked on: " + array.get(i).getName(), Toast.LENGTH_SHORT).show();
            }
        });
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
            startActivity(intent);
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
        }

        return super.onOptionsItemSelected(item);
    }
}
