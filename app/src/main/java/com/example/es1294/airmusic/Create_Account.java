package com.example.es1294.airmusic;

import android.app.Activity;
import android.content.Intent;
/*import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;*/
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.core.Tag;

//import java.io.ByteArrayOutputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.android.volley.VolleyLog.TAG;


public class Create_Account extends Activity {

    private static final String TAG = "Create_Account";

    DatabaseHelper helper = new DatabaseHelper(this);
    boolean userLengthFlag = false;
    boolean userTakenFlag = false;
    boolean passLengthFlag = false;
    boolean passMatchFlag = false;
    boolean passLowFlag = false;
    boolean passCapFlag = false;
    boolean passNumFlag = false;
    boolean emailMatchFlag = false;
    boolean emailFormatFlag = false;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference mUserRef = mRootRef.child("User");

@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
    }

    @Override
    public void onStart(){
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            openProfile();
        }
        //updateUI(currentUser);
    }

    public void onButtonClick(View v){
        //Toast genMessage = Toast.makeText(Create_Account.this, "Button Pushed?", Toast.LENGTH_SHORT);
        //genMessage.show();
        if(v.getId() == R.id.signin){
            //Toast onClickMessage = Toast.makeText(Create_Account.this, "Button Pushed yes", Toast.LENGTH_SHORT);
            //onClickMessage.show();
            EditText username = (EditText) findViewById(R.id.username);
            EditText password = (EditText) findViewById(R.id.password);
            EditText confrimpass = (EditText) findViewById(R.id.confirmpassword);
            EditText email = (EditText) findViewById(R.id.email);
            EditText confirmem = (EditText) findViewById(R.id.confirmemail);

            String userstr = username.getText().toString();
            String passstr = password.getText().toString();
            String confrimpassstr = confrimpass.getText().toString();
            String emailstr = email.getText().toString();
            String confirmemstr = confirmem.getText().toString();

            //check username length
            if((userstr.length() > 7) && (userstr.length() < 13)) {
                userLengthFlag = true;
                //check if username has been taken already
                /*if(userstr.equals(helper.doesUserExist(userstr))){
                    userTakenFlag = false;
                    Toast userTaken = Toast.makeText(Create_Account.this, "That username is taken!", Toast.LENGTH_SHORT);
                    userTaken.show();
                }else{*/
                    userTakenFlag = true;
                //}
            }else{
               userLengthFlag = false;
                if(userstr.length() == 0){
                    Toast blankUser = Toast.makeText(Create_Account.this, "enter a username!", Toast.LENGTH_SHORT);
                    blankUser.show();
                }else {
                    Toast userLimit = Toast.makeText(Create_Account.this, "username is too short!", Toast.LENGTH_SHORT);
                    userLimit.show();
                }
            }

            //check password match
            if(passstr.equals(confrimpassstr)){
                // error message
                passMatchFlag = true;

                //check password length
                if ((passstr.length() > 7) && (passstr.length() < 13)) {
                    passLengthFlag = true;

                    //using pattern matching to test for at least 1 number
                    Pattern p1 = Pattern.compile("[a-zA-Z0-9]*[0-9]+[a-zA-Z0-9]*");
                    Matcher m1 = p1.matcher(passstr);
                    boolean numberCheck = m1.matches();
                    if (numberCheck == false) {
                        passNumFlag = false;
                        Toast passNumber = Toast.makeText(Create_Account.this, "password needs a number!", Toast.LENGTH_SHORT);
                        passNumber.show();
                    }else{
                        passNumFlag = true;
                    }
                    //pattern matching to test for at least 1 capital letter
                    Pattern p2 = Pattern.compile("[a-zA-Z0-9]*[A-Z]+[a-zA-Z0-9]*");
                    Matcher m2 = p2.matcher(passstr);
                    boolean capitalCheck = m2.matches();
                    if (capitalCheck == false) {
                        passCapFlag = false;
                        Toast passCapital = Toast.makeText(Create_Account.this, "password needs a capital!", Toast.LENGTH_SHORT);
                        passCapital.show();
                    }else{
                        passCapFlag = true;
                    }
                    //pattern matching to test for at least 1 lowercase letter
                    Pattern p4 = Pattern.compile("[A-Za-z0-9]*[a-z]+[A-Za-z0-9]*");
                    Matcher m4 = p4.matcher(passstr);
                    boolean lowercaseCheck = m4.matches();
                    if (lowercaseCheck == false) {
                        passLowFlag = false;
                        Toast passLower = Toast.makeText(Create_Account.this, "password needs a lowercase!", Toast.LENGTH_SHORT);
                        passLower.show();
                    }else{
                        passLowFlag = true;
                    }
                } else {
                    passLengthFlag = false;
                    if(passstr.length() == 0){
                        Toast blankPass = Toast.makeText(Create_Account.this, "enter a password!", Toast.LENGTH_SHORT);
                        blankPass.show();
                    }else {
                        Toast passLimit = Toast.makeText(Create_Account.this, "password is too short!", Toast.LENGTH_SHORT);
                        passLimit.show();
                    }
                }
            }else {
                passMatchFlag = false;
                Toast pass = Toast.makeText(Create_Account.this, "passwords do not match",Toast.LENGTH_SHORT);
                pass.show();
            }

            if(emailstr.equals(confirmemstr)){
                // error message
                emailMatchFlag = true;

                //use pattern matching to check for the @ symbol in the email
                Pattern pE = Pattern.compile("[a-zA-z0-9]+@{1}[a-zA-Z]+.?[a-zA-Z0-9]*");
                Matcher m3 = pE.matcher(emailstr);
                boolean emailCheck = m3.matches();
                if (emailCheck == false) {
                    emailFormatFlag = false;
                    if(emailstr.length() == 0){
                        Toast blankMail = Toast.makeText(Create_Account.this, "enter an email!", Toast.LENGTH_SHORT);
                        blankMail.show();
                    }else {
                        Toast mailFormat = Toast.makeText(Create_Account.this, "invalid email!", Toast.LENGTH_SHORT);
                        mailFormat.show();
                    }
                }else{
                    emailFormatFlag = true;
                }
            } else {
                emailMatchFlag = false;
                Toast mail = Toast.makeText(Create_Account.this, "emails do not match", Toast.LENGTH_SHORT);
                mail.show();
            }

            //if all information was put in correctly, make the account!
            if(userLengthFlag && userTakenFlag && passMatchFlag && passLengthFlag && passNumFlag && passLowFlag && passCapFlag && emailMatchFlag && emailFormatFlag){
                //add to database

                mAuth.createUserWithEmailAndPassword(emailstr, passstr)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    Log.d(TAG, "createUserWithEmail:success");
                                    Toast success = Toast.makeText(Create_Account.this, "Account created!", Toast.LENGTH_SHORT);
                                    success.show();
                                    FirebaseUser currentUser = mAuth.getCurrentUser();
                                    //add to database
                                    mUserRef.setValue(currentUser.getUid());

                                    /*User user = new User();
                                    user.setUsername("default");
                                    user.setPassword("default Pass");
                                    user.setEmail("email");

                                    //set default strings for profile information
                                    user.setFullName("Default Name");
                                    user.setAbout("Write whatever you want here!");
                                    user.setArtistOne("Add an artist!");
                                    user.setArtistTwo("Add another artist!");
                                    user.setArtistThree("Add a third artist!");
                                    user.setArtistFour("Add a fourth artist!");
                                    user.setArtistFive("Add up to five artists!");
                                    user.setGenreOne("Add a genre!");
                                    user.setGenreTwo("Add another genre!");
                                    user.setGenreThree("Add up to three genres!");*/

                                }else{
                                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                    Toast.makeText(Create_Account.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                                    //updateUI(null);
                                }
                            }
                        });

                //Add a new child of "user" to realtime db using key = userID

             /*   FirebaseUser currentUser = mAuth.getCurrentUser();
                String uID = currentUser.getUid();
                mUserRef.setValue(uID);

                User user = new User();
                user.setUsername(userstr);
                user.setPassword(passstr);
                user.setEmail(emailstr);

                //set default strings for profile information
                user.setFullName("Default Name");
                user.setAbout("Write whatever you want here!");
                user.setArtistOne("Add an artist!");
                user.setArtistTwo("Add another artist!");
                user.setArtistThree("Add a third artist!");
                user.setArtistFour("Add a fourth artist!");
                user.setArtistFive("Add up to five artists!");
                user.setGenreOne("Add a genre!");
                user.setGenreTwo("Add another genre!");
                user.setGenreThree("Add up to three genres!");

                mUserRef.child(uID).setValue(user);*/

                openProfile();


                //try to store the default pic in the database
               /* Bitmap defaultPic = BitmapFactory.decodeResource(getResources(), R.drawable.default_profile_pic_png_9);

                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                defaultPic.compress(Bitmap.CompressFormat.PNG, 0, stream);
                byte[] photoData = stream.toByteArray();

                user.setProfilePhoto(photoData);

                helper.insertUser(user);*/



                //only after - return to login page
                //mUserRef.setValue(user)

            }
        }
    }

    public void openLoginActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void openProfile(){
        Intent intent = new Intent(this, profile.class);
        //intent.putExtra("idNumber", id);
        startActivity(intent);
    }
}
