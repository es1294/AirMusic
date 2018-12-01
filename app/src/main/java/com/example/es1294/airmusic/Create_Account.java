package com.example.es1294.airmusic;

import android.app.Activity;
import android.content.Intent;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static com.android.volley.VolleyLog.TAG;


public class Create_Account extends Activity {

    private static final String TAG = "Create_Account";

    //Some flags used to check requirements
    boolean passLengthFlag = false;
    boolean passMatchFlag = false;
    boolean passLowFlag = false;
    boolean passCapFlag = false;
    boolean passNumFlag = false;
    boolean emailMatchFlag = false;
    boolean emailFormatFlag = false;

    //Firebase references:
    //For authentication
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    //Realtime database reference
    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();


@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
    }

    @Override
    public void onStart(){
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //This checks if the user is already logged in - skips login page if logged in already
        if(currentUser != null){
            openProfile();
        }
    }

    public void onButtonClick(View v){
        if(v.getId() == R.id.signin){
            //Getting the inputs from the editTexts
            EditText username = (EditText) findViewById(R.id.username);
            EditText password = (EditText) findViewById(R.id.password);
            EditText confrimpass = (EditText) findViewById(R.id.confirmpassword);
            EditText email = (EditText) findViewById(R.id.email);
            EditText confirmem = (EditText) findViewById(R.id.confirmemail);

            //Converting the inputs to strings
            final String userstr = username.getText().toString();
            String passstr = password.getText().toString();
            String confrimpassstr = confrimpass.getText().toString();
            final String emailstr = email.getText().toString();
            String confirmemstr = confirmem.getText().toString();

            //Checking if Name field is empty
            if(userstr.length() == 0){
                Toast blankUser = Toast.makeText(Create_Account.this, "enter you name", Toast.LENGTH_SHORT);
                blankUser.show();
            }

            //check passwords match
            if(passstr.equals(confrimpassstr)){
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

            //Check emails match
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
            if(passMatchFlag && passLengthFlag && passNumFlag && passLowFlag && passCapFlag && emailMatchFlag && emailFormatFlag){
                //Doing the Firebase Authentication (email password method for android)
                mAuth.createUserWithEmailAndPassword(emailstr, passstr)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                //email is valid - continue to make the account
                                if(task.isSuccessful()){
                                    Log.d(TAG, "createUserWithEmail:success");
                                    Toast success = Toast.makeText(Create_Account.this, "Account created!", Toast.LENGTH_SHORT);
                                    success.show();
                                    FirebaseUser currentUser = mAuth.getCurrentUser();

                                    //adding a new user to realtime database

                                    User defaultUser = new User();
                                    defaultUser.setAuthID(currentUser.getUid());
                                    defaultUser.setEmail(emailstr);
                                    defaultUser.setFullName(userstr);
                                    //set default strings for profile information
                                    defaultUser.setAbout("Write whatever you want here!");
                                    defaultUser.setArtistOne("Add an artist!");
                                    defaultUser.setArtistTwo("Add another artist!");
                                    defaultUser.setArtistThree("Add a third artist!");
                                    defaultUser.setArtistFour("Add a fourth artist!");
                                    defaultUser.setArtistFive("Add up to five artists!");
                                    defaultUser.setGenreOne("Add a genre!");
                                    defaultUser.setGenreTwo("Add another genre!");
                                    defaultUser.setGenreThree("Add up to three genres!");
                                    defaultUser.setProfilePhotoStorageName("defaultphoto.png");
                                    int i = 0;
                                    defaultUser.setCurrentSong(i);
                                    mRootRef.child("User").push().setValue(defaultUser);
                                    openProfile();

                                }else{
                                    //bad email, report problem to user
                                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                    Toast.makeText(Create_Account.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

            }
        }
    }

    //Not used at the moment
  /*  public void openLoginActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }*/

    public void openProfile(){
        Intent intent = new Intent(this, profile.class);
        startActivity(intent);
    }
}
