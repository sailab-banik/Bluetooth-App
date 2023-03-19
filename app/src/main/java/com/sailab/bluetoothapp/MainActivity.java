package com.sailab.bluetoothapp;


import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    ImageView bluetooth_img;
    TextView my_device_name;
    Button on_btn, off_btn, devices_btn;
    ListView lv_paired_devices;
    BluetoothManager bluetoothManager;
    BluetoothAdapter bluetoothAdapter;
    Set<BluetoothDevice> bluetoothDevices;
    ArrayList<String> list = new ArrayList<>();

    private static final int REQUEST_ENABLE_BT = 0;

    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bluetooth_img = findViewById(R.id.bluetooth_img);
        my_device_name = findViewById(R.id.my_device_name);
        on_btn = findViewById(R.id.on_btn);
        off_btn = findViewById(R.id.off_btn);
        devices_btn = findViewById(R.id.devices_btn);
        lv_paired_devices = findViewById(R.id.lv_paired_devices);

        bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        bluetoothAdapter = bluetoothManager.getAdapter();

        if (bluetoothAdapter == null) {
            Toast.makeText(this, "Device doesn't support Bluetooth", Toast.LENGTH_SHORT).show();
        }

        if (bluetoothAdapter.isEnabled())
            bluetooth_img.setImageResource(R.drawable.ic_baseline_bluetooth_connected_24);
        else
            bluetooth_img.setImageResource(R.drawable.ic_baseline_bluetooth_disabled_24);

        my_device_name.setText(bluetoothAdapter.getName());

        on_btn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("MissingPermission")
            @Override
            public void onClick(View v) {
                if (!bluetoothAdapter.isEnabled()) {
                    Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
                    bluetooth_img.setImageResource(R.drawable.ic_baseline_bluetooth_connected_24);
                } else
                    Toast.makeText(MainActivity.this, "Bluetooth Already On", Toast.LENGTH_SHORT).show();
            }
        });

        off_btn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("MissingPermission")
            @Override
            public void onClick(View v) {
                bluetoothAdapter.disable();
                Toast.makeText(MainActivity.this, "Bluetooth Turned Off", Toast.LENGTH_SHORT).show();
                bluetooth_img.setImageResource(R.drawable.ic_baseline_bluetooth_disabled_24);
            }
        });

        devices_btn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("MissingPermission")
            @Override
            public void onClick(View v) {
                if(bluetoothAdapter.isEnabled()) {
                    bluetoothDevices = bluetoothAdapter.getBondedDevices();

                    for (BluetoothDevice bt : bluetoothDevices)
                        list.add(bt.getName());

                    ArrayAdapter<String> adapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, list);
                    lv_paired_devices.setAdapter(adapter);

                    lv_paired_devices.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Toast.makeText(MainActivity.this, "Ready to transfer data", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else
                    Toast.makeText(MainActivity.this, "Turn on Bluetooth", Toast.LENGTH_SHORT).show();
            }
        });
    }
}