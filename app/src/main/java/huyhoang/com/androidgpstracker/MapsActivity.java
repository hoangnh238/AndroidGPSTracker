package huyhoang.com.androidgpstracker;

import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Location;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    private GPSTracker gpsTracker;
    private Location mLocation;
    double latitude, longitude;

    ImageView imageHinh;
    Button ButtonHinh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        try {
            gpsTracker = new GPSTracker(getApplicationContext());
        } catch (IOException e) {
            e.printStackTrace();
        }
        mLocation = gpsTracker.getLocation();

        latitude = mLocation.getLatitude();
        longitude = mLocation.getLongitude();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        imageHinh=(ImageView) findViewById(R.id.imageView1);
        ButtonHinh=(Button)findViewById(R.id.button1);
    }

    public void onClick(View v){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,0);
    }
    protected void onActivityResult(int requestCode, int resultCode,Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        Bitmap bitmap = (Bitmap) data.getExtras().get("data");
        imageHinh.setImageBitmap(bitmap);
        // imageHinh.setLatitude(latitude);
        // imageHinh.setLongitude(longitude);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in here and move the camera
        LatLng sydney = new LatLng(latitude, longitude);
        mMap.addMarker(new MarkerOptions().position(sydney).title("i'm here"));
        float zoom =16;

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,zoom));
        //mMap.getUiSettings().setScrollGesturesEnabled(false);
        //mMap.getUiSettings().setZoomGesturesEnabled(false);

    }
}