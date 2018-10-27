package com.example.es1294.airmusic;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Create_Account extends Activity {

    DatabaseHelper helper = new DatabaseHelper(this);
    boolean passFlag = true;
    boolean emailFlag = true;

@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
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
            if(userstr.length() < 8 || userstr.length() > 12) {
                passFlag = false;
                Toast userLimit = Toast.makeText(Create_Account.this, "username is too short!", Toast.LENGTH_SHORT);
                userLimit.show();
            }

            //check password match
            if(!passstr.equals(confrimpassstr)){
                // error message
                passFlag = false;
                Toast pass = Toast.makeText(Create_Account.this, "passwords do not match",Toast.LENGTH_SHORT);
                pass.show();
            }else {
                //check password length
                if (passstr.length() < 8 || passstr.length() > 12) {
                    passFlag = false;
                    Toast passLimit = Toast.makeText(Create_Account.this, "password is too short!", Toast.LENGTH_SHORT);
                    passLimit.show();
                } else {
                    //using pattern matching to test for at least 1 number
                    Pattern p1 = Pattern.compile("[a-zA-Z0-9]*[0-9]+[a-zA-Z0-9]*");
                    Matcher m1 = p1.matcher(passstr);
                    boolean numberCheck = m1.matches();
                    if (numberCheck == false) {
                        passFlag = false;
                        Toast passNumber = Toast.makeText(Create_Account.this, "password needs a number!", Toast.LENGTH_SHORT);
                        passNumber.show();
                    }
                    //pattern matching to test for at least 1 capital letter
                    Pattern p2 = Pattern.compile("[a-zA-Z0-9]*[A-Z]+[a-zA-Z0-9]*");
                    Matcher m2 = p2.matcher(passstr);
                    boolean capitalCheck = m2.matches();
                    if (capitalCheck == false) {
                        passFlag = false;
                        Toast passCapital = Toast.makeText(Create_Account.this, "password needs a capital!", Toast.LENGTH_SHORT);
                        passCapital.show();
                    }
                    //pattern matching to test for at least 1 lowercase letter
                    Pattern p4 = Pattern.compile("[A-Za-z0-9]*[a-z]+[A-Za-z0-9]*");
                    Matcher m4 = p4.matcher(passstr);
                    boolean lowercaseCheck = m4.matches();
                    if (lowercaseCheck == false) {
                        passFlag = false;
                        Toast passLower = Toast.makeText(Create_Account.this, "password needs a lowercase!", Toast.LENGTH_SHORT);
                        passLower.show();
                    }
                }
            }

            if(!emailstr.equals(confirmemstr)){
                // error message
                emailFlag = false;
                Toast mail = Toast.makeText(Create_Account.this, "emails do not match", Toast.LENGTH_SHORT);
                mail.show();
            }else {
                Pattern pE = Pattern.compile("[a-zA-z]+@{1}[a-zA-Z]+.?[a-zA-Z]*");
                Matcher m3 = pE.matcher(emailstr);
                boolean emailCheck = m3.matches();
                if (emailCheck == false) {
                    emailFlag = false;
                    Toast mailFormat = Toast.makeText(Create_Account.this, "invalid email!", Toast.LENGTH_SHORT);
                    mailFormat.show();
                }
            }
            if((passFlag == true) && (emailFlag == true)){
                //add to database

                Toast message = Toast.makeText(Create_Account.this, "Added account to database", Toast.LENGTH_SHORT);
                message.show();
                User user = new User();
                user.setUsername(userstr);
                user.setPassword(passstr);
                user.setEmail(emailstr);

                helper.insertUser(user);

                //only after - return to login page
                openLoginActivity();

            }
        }
    }

    public void openLoginActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
