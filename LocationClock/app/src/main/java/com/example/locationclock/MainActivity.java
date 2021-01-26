package com.example.locationclock;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.locationclock.Model.CustomTextClock;

public class MainActivity extends AppCompatActivity {

    Button button;
    CustomTextClock clock;
    TextView longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        LocationListener locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                editPresentation(location);
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {}

            public void onProviderEnabled(String provider) {}

            public void onProviderDisabled(String provider) {}
        };

        button = findViewById(R.id.btn);
        clock = findViewById(R.id.clock);
        longitude = findViewById(R.id.longitude);

        clock.changeFormat();
        button.setText(clock.getFormatString());

        if (ContextCompat.checkSelfPermission( this, android.Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED) {
            longitude.setText("Location permission not granted, this app will not work correctly");
        } else {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clock.changeFormat();
                button.setText(clock.getFormatString());
            }
        });
    }

    public void editPresentation(Location location){
        clock.setTimeZone(location);
        longitude.setText(clock.getTimeZone());
    }
}


