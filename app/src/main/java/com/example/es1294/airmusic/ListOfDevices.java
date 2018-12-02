package com.example.es1294.airmusic;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.Set;

public class ListOfDevices extends AppCompatActivity {

    Button showButton;
    ListView deviceList;
    BluetoothAdapter bluetoothAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_devices);

        showButton = (Button) findViewById(R.id.show_devices);
        deviceList = (ListView) findViewById(R.id.device_list);
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        
        executeButton();
    }

    private void executeButton() {
        showButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Set<BluetoothDevice> allDevices = bluetoothAdapter.getBondedDevices();
                String[] deviceName = new String[allDevices.size()];
                int index = 0;

                if(allDevices.size() > 0){

                    for(BluetoothDevice device:allDevices){
                        deviceName[index] = device.getName();
                        index++;
                    }

                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1, deviceName);

                    deviceList.setAdapter(arrayAdapter);
                }
            }
        });
    }
}
