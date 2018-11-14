package com.example.es1294.airmusic;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    Button login;     // button on music screen to play the current song
    Button create;
    EditText usernameInput;
    EditText passwordInput;
    //DatabaseHelper helper = new DatabaseHelper(this);
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private static final String TAG = "MainActivity";

    @Override
    public void onStart(){
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
       //updateUI(currentUser);
        if(currentUser != null){
            openProfile();
        }
    }

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


                mAuth.signInWithEmailAndPassword(userstr, passstr).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d(TAG, "signInWithEmail:success");
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    //Add the user to the database
                                    String id = user.getUid();
                                    openProfile();
                                    //updateUI(user);
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w(TAG, "signInWithEmail:failure", task.getException());
                                    Toast.makeText(MainActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                    //updateUI(null);
                                }
                            }
                        });

                /*String username = helper.doesUserExist(userstr);
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
                            Integer id = helper.getIDFromUsername(userstr);
                            String stringID = id.toString();
                            ManageUser manage = new ManageUser(getApplicationContext());
                            manage.startLogin(stringID);
                            openProfile();
                        }else{
                            //show popup message
                            Toast error = Toast.makeText(MainActivity.this, "The password you entered is incorrect" , Toast.LENGTH_SHORT);
                            error.show();
                        }
                    }
                }*/

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
