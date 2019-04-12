package com.example.runwithme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class HostActivity extends AppCompatActivity {

    ListView requestList;

    ArrayList<String> requests = new ArrayList<>();
    ArrayAdapter arrayAdapter;

    LocationManager locationManager;
    LocationListener locationListener;

    ArrayList<Double> requestLatitudes = new ArrayList<Double>();
    ArrayList<Double> requestLongitudes = new ArrayList<Double>();

    ArrayList<String> usernames = new ArrayList<String>();

    public void updateListView(Location location){
            if(location != null) {

                requestList = (ListView) findViewById(R.id.requestList);

                ParseQuery<ParseObject> query = ParseQuery.getQuery("Request");

                final ParseGeoPoint geoPointLocation = new ParseGeoPoint(location.getLatitude(), location.getLongitude());

                query.whereNear("Location",geoPointLocation);

                query.setLimit(10);

                query.findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(List<ParseObject> objects, ParseException e) {
                        if(e == null){
                            requests.clear();
                            requestLongitudes.clear();
                            requestLatitudes.clear();
                            if(objects.size() > 0){

                                for(ParseObject object : objects){


                                    ParseGeoPoint requestLocation = (ParseGeoPoint) object.get(("Location"));

                                    Double distanceKM = geoPointLocation.distanceInKilometersTo(requestLocation);
                                    if(requestLocation != null) {
                                        Double distanceOneDecimal = (double) Math.round(distanceKM * 10) / 10;

                                        requests.add(distanceOneDecimal.toString() + "Km");

                                        requestLatitudes.add(requestLocation.getLatitude());
                                        requestLongitudes.add(requestLocation.getLongitude());

                                        usernames.add(object.getString("username"));
                                    }

                                }

                                arrayAdapter.notifyDataSetChanged();
                            }
                            else{

                                requests.add("Currently no Buddies nearby....");

                            }
                            arrayAdapter.notifyDataSetChanged();
                        }
                    }
                });

                arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, requests);

                requestList.setAdapter(arrayAdapter);
            }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == 1){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED) {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
                    Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                    updateListView(lastKnownLocation);

                }
            }

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host);

        setTitle("Nearby Requests");

            requestList = (ListView) findViewById(R.id.requestList);

       // requests.add("Test");

        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, requests);

        requests.clear();

        requests.add("Searching for Nearby Buddies");

        requestList.setAdapter(arrayAdapter);



        requestList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {




                    Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                    if (requestLongitudes.size() > position && requestLongitudes.size() > position && usernames.size() > position && lastKnownLocation != null) {

                        Intent intent = new Intent(getApplicationContext(), HostLocationActivity.class);

                        intent.putExtra("requestLatitude", requestLatitudes.get(position));
                        intent.putExtra("requestLongitude", requestLongitudes.get(position));
                        intent.putExtra("hostLatitude", lastKnownLocation.getLatitude());
                        intent.putExtra("hostLongitude", lastKnownLocation.getLongitude());
                        intent.putExtra("username", usernames.get(position));
                        startActivity(intent);




                    }

            }
        });/// Checking get location need to send






        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {


                updateListView(location);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };

        if (Build.VERSION.SDK_INT < 23) {

            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        }else{

            if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

            }else{

                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

                Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if(lastKnownLocation != null){
                    updateListView(lastKnownLocation);
                }
            }
        }
    }


    }

