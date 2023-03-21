package com.sailab.bluetoothapp;


import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
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
    TextView my_device_name, devices_tv;
    Button on_btn, off_btn, scan_discover_btn;
    ListView lv_paired_devices, lv_scanned_devices;
    BluetoothAdapter bluetoothAdapter;
    Set<BluetoothDevice> bluetoothDevices;
    ArrayList<String> scanList;
    ArrayAdapter<String> arrayAdapter, scanAdapter;

    private static final int REQUEST_ENABLE_BT = 0;
    private static final int REQUEST_DISCOVERABLE = 1;

    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bluetooth_img = findViewById(R.id.bluetooth_img);
        my_device_name = findViewById(R.id.my_device_name);
        on_btn = findViewById(R.id.on_btn);
        off_btn = findViewById(R.id.off_btn);
        devices_tv = findViewById(R.id.devices_tv);
        scan_discover_btn = findViewById(R.id.scan_discover_btn);
        lv_paired_devices = findViewById(R.id.lv_paired_devices);
        lv_scanned_devices = findViewById(R.id.lv_scanned_devices);

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        scanList = new ArrayList<>();
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        scanAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_2, scanList);
        lv_scanned_devices.setAdapter(scanAdapter);

        if (bluetoothAdapter == null) {
            Toast.makeText(this, "Device doesn't support Bluetooth", Toast.LENGTH_SHORT).show();
        }

        // Bluetooth Avatar
        if (bluetoothAdapter.isEnabled())
            bluetooth_img.setImageResource(R.drawable.ic_baseline_bluetooth_connected_24);
        else
            bluetooth_img.setImageResource(R.drawable.ic_baseline_bluetooth_disabled_24);

        // Device Name
        my_device_name.setText(bluetoothAdapter.getName());

        // Paired Devices
        bluetoothDevices = bluetoothAdapter.getBondedDevices();
        for (BluetoothDevice device : bluetoothDevices)
            arrayAdapter.add(device.getName());
        lv_paired_devices.setAdapter(arrayAdapter);


        // On Button
        on_btn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("MissingPermission")
            @Override
            public void onClick(View v) {
                if (!bluetoothAdapter.isEnabled()) {
                    Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
                    bluetooth_img.setImageResource(R.drawable.ic_baseline_bluetooth_connected_24);
                } else {
                    Toast.makeText(MainActivity.this, "Bluetooth Already On", Toast.LENGTH_SHORT).show();

                }
            }
        });

        // Off Button
        off_btn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("MissingPermission")
            @Override
            public void onClick(View v) {
                bluetoothAdapter.disable();
                Toast.makeText(MainActivity.this, "Bluetooth Turned Off", Toast.LENGTH_SHORT).show();
                bluetooth_img.setImageResource(R.drawable.ic_baseline_bluetooth_disabled_24);
            }
        });

        // Pair New Devices Button
        scan_discover_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bluetoothAdapter.isEnabled()) {
                    // Discover
                    Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
                    discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
                    startActivityForResult(discoverableIntent, REQUEST_DISCOVERABLE);
                    Toast.makeText(MainActivity.this, "Your device is discoverable as " + bluetoothAdapter.getName(), Toast.LENGTH_LONG).show();
                } else
                    Toast.makeText(MainActivity.this, "Turn on Bluetooth", Toast.LENGTH_SHORT).show();
            }
        });
    }
}