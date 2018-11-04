package com.example.es1294.airmusic;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    Button login;     // button on music screen to play the current song
    Button create;
    EditText usernameInput;
    EditText passwordInput;
    DatabaseHelper helper = new DatabaseHelper(this);
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        create = (Button) findViewById(R.id.newAccountButton);
        create.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                openNewAccountActivity();
            }
        });

        login = (Button) findViewById(R.id.login_button);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                usernameInput = (EditText) findViewById(R.id.username_editText);
                String userstr = usernameInput.getText().toString();
                passwordInput = (EditText) findViewById(R.id.password_editText);
                String passstr = passwordInput.getText().toString();

                String username = helper.doesUserExist(userstr);
                if(!userstr.equals(username)){
                    Toast usernameExistsError = Toast.makeText(MainActivity.this, "No account with that username!" , Toast.LENGTH_SHORT);
                    usernameExistsError.show();
                }else if(helper.doesUserExist(userstr).equals("")){
                    Toast blank = Toast.makeText(MainActivity.this, "Enter a username" , Toast.LENGTH_SHORT);
                    blank.show();
                }else{
                    String password = helper.searchPass(userstr);
                    if(userstr.equals(username)){
                        if(passstr.equals(password)) {
                            Toast loginSuccess = Toast.makeText(MainActivity.this, "Login Successful!" , Toast.LENGTH_SHORT);
                            loginSuccess.show();
                            /*id = helper.getIDFromUsername(userstr);
                            Toast loginID = Toast.makeText(MainActivity.this, id , Toast.LENGTH_LONG);
                            loginID.show();*/
                            openProfile();
                        }else{
                            //show popup message
                            Toast error = Toast.makeText(MainActivity.this, "The password you entered is incorrect" , Toast.LENGTH_SHORT);
                            error.show();
                        }
                    }
                }

            }
        });
    }

    public void openProfile(){
        Intent intent = new Intent(this, profile.class);
        //intent.putExtra("idNumber", id);
        startActivity(intent);
    }

    public void openNewAccountActivity(){
        Intent intent = new Intent (this, Create_Account.class);
        startActivity(intent);
       /*Toast reportClick = Toast.makeText(MainActivity.this, "button clicked", Toast.LENGTH_SHORT);;
       reportClick.show();*/
    }

}
