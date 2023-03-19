package com.sailab.bluetoothapp;


import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    ImageView bluetooth_img;
    Button on_btn, off_btn, discover_btn, devices_btn;
    BluetoothManager bluetoothManager;
    BluetoothAdapter bluetoothAdapter;

    private static final int REQUEST_ENABLE_BT = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bluetooth_img = findViewById(R.id.bluetooth_img);
        on_btn = findViewById(R.id.on_btn);
        off_btn = findViewById(R.id.off_btn);
        discover_btn = findViewById(R.id.discover_btn);
        devices_btn = findViewById(R.id.devices_btn);

        bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        bluetoothAdapter = bluetoothManager.getAdapter();

        if (bluetoothAdapter == null) {
            Toast.makeText(this, "Device doesn't support Bluetooth", Toast.LENGTH_SHORT).show();
        }


        if (bluetoothAdapter.isEnabled())
            bluetooth_img.setImageResource(R.drawable.ic_baseline_bluetooth_connected_24);
        else
            bluetooth_img.setImageResource(R.drawable.ic_baseline_bluetooth_disabled_24);


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
                bluetooth_img.setImageResource(R.drawable.ic_baseline_bluetooth_disabled_24);
            }
        });



    }
}