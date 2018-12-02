package com.example.es1294.airmusic;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Stream extends AppCompatActivity {

    Button bluetoothON;
    Button bluetoothOFF;
    BluetoothAdapter bluetoothAdapter;
    Intent btEnabledIntent;
    int requestCodeForEnable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stream_activity);

        bluetoothOFF = (Button) findViewById(R.id.bluetooth_off);
        bluetoothON = (Button) findViewById(R.id.bluetooth_on);
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        btEnabledIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        requestCodeForEnable = 1;
        
        enableBluetooth();
        disablebluetooth();

    }

    private void disablebluetooth() {
        bluetoothOFF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(bluetoothAdapter.isEnabled()){
                    bluetoothAdapter.disable();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode == requestCodeForEnable){
            if(resultCode == RESULT_OK){

                Toast.makeText(getApplicationContext(),"Bluetooth Enabled", Toast.LENGTH_LONG);
            }
            else if(resultCode == RESULT_CANCELED){
                Toast.makeText(getApplicationContext(),"Bluetooth Enabling Canceled", Toast.LENGTH_LONG).show();
                
            }
        }
    }

    private void enableBluetooth() {
        bluetoothON.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(bluetoothAdapter == null){
                    Toast.makeText(getApplicationContext(),"Bluetooth not supported on device",Toast.LENGTH_LONG).show();
                }
                else{

                    if(!bluetoothAdapter.isEnabled()){

                        startActivityForResult(btEnabledIntent, requestCodeForEnable);
                    }
                }
            }
        });
    }

}
