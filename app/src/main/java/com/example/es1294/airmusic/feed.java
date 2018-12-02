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


public class feed extends AppCompatActivity {

    private ArrayList<Friends> array = new ArrayList<>();
    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    User u;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        /*Friends a = new Friends("Cloud", "Radioactive", "Imagine Dragons");
        Friends b = new Friends("Dovahkin", "Sovngarde", "Jermey Soule");
        Friends c = new Friends("Lara Croft", "Cherry Bomb", "The Runaways");
        Friends d = new Friends("Thor ", "Immigrant Song", "Led Zeppelin");
        Friends e = new Friends("Iron man", "Highway to Hell", "AC/DC");
        Friends f = new Friends("Loki ", "Bitch Better Have My Money", "Rhianna");
        Friends g = new Friends("Daenerys", "Queen", "Janelle Monae");
        Friends h = new Friends("Avatar Korra", "Turn It Down For What", "DJ Snake, Lil Jon");
        Friends i = new Friends("Hella", "Chun - Li", "Nicki Minaj");
        Friends j = new Friends("Terry Mcginnis", "Batman Beyond Theme", "Kristopher Carter");
        Friends k = new Friends("Defenders", "We Will Rock You", "Queen");

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
        array.add(k);*/
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
