package com.example.es1294.airmusic;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Create_Account extends Activity {

    DatabaseHelper helper = new DatabaseHelper(this);

@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
    }


    public void onButtonClick(View v){
        //Toast genMessage = Toast.makeText(Create_Account.this, "Button Pushed?", Toast.LENGTH_SHORT);
        //genMessage.show();
        if(v.getId() == R.id.signin){
            Toast onClickMessage = Toast.makeText(Create_Account.this, "Button Pushed yes", Toast.LENGTH_SHORT);
            onClickMessage.show();
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

            if(!passstr.equals(confrimpassstr)){
                // error message
                Toast pass = Toast.makeText(Create_Account.this, "Passwords do not match",Toast.LENGTH_SHORT);
                pass.show();
            }
            if(!emailstr.equals(confirmemstr)){
                // error message
                Toast mail = Toast.makeText(Create_Account.this, "Email does not match", Toast.LENGTH_SHORT);
                mail.show();
            }
            else{
                //add to database
                Toast message = Toast.makeText(Create_Account.this, "Reached add to database", Toast.LENGTH_SHORT);
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
