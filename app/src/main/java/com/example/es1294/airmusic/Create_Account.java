package com.example.es1294.airmusic;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Create_Account extends Activity {
@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
    }


    public void onButtonClick(View v){
        if(v.getId() == R.id.signin){
            EditText username = (EditText) findViewById(R.id.username);
            EditText password = (EditText) findViewById(R.id.password);
            EditText confrimpass = (EditText) findViewById(R.id.confirmpassword);
            EditText email = (EditText) findViewById(R.id.email);
            EditText confrimem = (EditText) findViewById(R.id.confirmpassword);

            String userstr = username.getText().toString();
            String passstr = password.getText().toString();
            String confrimpassstr = confrimpass.getText().toString();
            String emailstr = email.getText().toString();
            String confrimemstr = confrimem.getText().toString();

            if(!passstr.equals(confrimpassstr)){
                // error message
                Toast pass = Toast.makeText(Create_Account.this, "Passwords do not match",Toast.LENGTH_SHORT);
                pass.show();
            }
            if(!emailstr.equals(confrimemstr)){
                // error message
                Toast mail = Toast.makeText(Create_Account.this, "Email does not match", Toast.LENGTH_SHORT);
                mail.show();
            }
        }
    }
}
