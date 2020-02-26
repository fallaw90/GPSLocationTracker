package com.fallntic.gpslocationtracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE = 125;
    TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextView = findViewById(R.id.textView);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                //Request location
                requestPermissions(new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
            }
            else {
                //Request location permission
                startService();
            }
        }else {
            //start the location service
            startService();
        }
    }

    private void startService(){
        Intent intent = new Intent(this, LocationService.class);
        startService(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case REQUEST_CODE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    startService();
                }else {
                    Toast.makeText(this, "Give me permissions", Toast.LENGTH_SHORT).show();
                }
        }
    }
}
