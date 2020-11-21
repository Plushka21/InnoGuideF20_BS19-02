package com.example.innoguidesjava;

import com.directions.route.AbstractRouting;
import com.directions.route.Route;
import com.directions.route.RouteException;
import com.directions.route.Routing;
import com.directions.route.RoutingListener;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMyLocationButtonClickListener;
import com.google.android.gms.maps.GoogleMap.OnMyLocationClickListener;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.material.snackbar.Snackbar;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.view.View;
import android.widget.Toast;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainMap extends AppCompatActivity
        implements
        OnMyLocationButtonClickListener,
        OnMyLocationClickListener,
        OnMapReadyCallback,
        ActivityCompat.OnRequestPermissionsResultCallback,
        GoogleApiClient.OnConnectionFailedListener,
        RoutingListener {

    /**
     * Request code for location permission request.
     *
     * @see #onRequestPermissionsResult(int, String[], int[])
     */
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    /**
     * Flag indicating whether a requested permission has been denied after returning in
     * {@link #onRequestPermissionsResult(int, String[], int[])}.
     */
    private boolean permissionDenied = false;
    private GoogleMap map;

    private List<Place> places;
    private HashMap<String, Place> mapPlaces;
    private List<Event> events;

    //current and destination location objects
    Location myLocation = null;
    Location destinationLocation = null;
    protected LatLng start = null;
    protected LatLng end = null;
    //polyline object
    private List<Polyline> polylines = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        places = new ArrayList<>();
        events = new ArrayList<>();
        try {
//            places = JSONHelper.readPlaceJSONFile(this);
//            events = JSONHelper.readEventsJSONFile(this);
            mapPlaces = JSONHelper.MapPlaceJSONFile(this);
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map);

        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.setOnMyLocationButtonClickListener(this);
        map.setOnMyLocationClickListener(this);

        //DELETIN GOOGLE STANDARD ICONS
        map.setMapStyle(
                MapStyleOptions.loadRawResourceStyle(
                        this, R.raw.map_style));

        enableMyLocation();
        getMyLocation();
//        addMarker(places);
        addMapMarker(mapPlaces);

        // POP UP WINDOW WITH INFORMATION
        map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                String name = marker.getTitle();
//                Place p = places.get(Math.round(marker.getZIndex()));
                Place p = mapPlaces.get(name);
                AlertDialog.Builder builder = new AlertDialog.Builder(MainMap.this);
                assert p != null;
                builder
                        .setTitle(p.getName())
                        .setPositiveButton("OK", null)
                        .setNegativeButton("Get more info", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(MainMap.this, DetailedInfoWindow.class);

                                //TAKING DATA FROM THIS MARKER AND PASSING IT TO ScrollingActivity.class
                                intent.putExtra("Rating", String.valueOf(p.getRating()));
                                intent.putExtra("Address", p.getAddress());
                                intent.putExtra("Phone Number", p.getNumber());
                                intent.putExtra("Name", marker.getTitle());
                                intent.putExtra("Time", p.getWorking_time());
                                intent.putExtra("Photos", p.getPhotos());

                                MainMap.this.startActivity(intent);
                            }
                        })

                        .setNeutralButton("Build route", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                end = new LatLng(p.getC1(), p.getC2());

                                map.clear();

                                start = new LatLng(myLocation.getLatitude(), myLocation.getLongitude());
                                //start route finding
                                findRoutes(start, end);
                            }
                        })
                        .setMessage("\nAddress: " + p.getAddress() +
                                "\n\nRating: " + p.getRating());
                AlertDialog ald = builder.create();
                ald.show();
                return false;
            }
        });

        LatLng inno = new LatLng(55.755, 48.74);
        map.moveCamera(CameraUpdateFactory.newLatLng(inno));
        moveCamera(inno);
    }

    public void addMarker(List<Place> places) {
        for (int i = 0; i < places.size(); i++) {
            String name = places.get(i).getName();
            String category = places.get(i).getCategory();

            if (!category.equals("")) {
                map.addMarker(new MarkerOptions()
                        .position(new LatLng(places.get(i).getC1(), places.get(i).getC2()))
                        .title(name)
                        .zIndex(i)
                        .icon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons(category, 100, 100))));
            } else {
                map.addMarker(new MarkerOptions()
                        .position(new LatLng(places.get(i).getC1(), places.get(i).getC2()))
                        .title(name)
                        .zIndex(i));
            }
        }
    }

    public void addMapMarker(HashMap<String, Place> mapPlaces) {
        int i = 0;
        for (Map.Entry entry: mapPlaces.entrySet()) {
            String name = (String) entry.getKey();
            Place place = (Place) entry.getValue();
            String category = place.getCategory();

            if (!category.equals("")) {
                map.addMarker(new MarkerOptions()
                        .position(new LatLng(place.getC1(), place.getC2()))
                        .title(name)
                        .zIndex(i)
                        .icon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons(category, 100, 100))));
            } else {
                map.addMarker(new MarkerOptions()
                        .position(new LatLng(place.getC1(), place.getC2()))
                        .title(name)
                        .zIndex(i));
            }
            i++;
        }
    }

    /**
     * Enables the My Location layer if the fine location permission has been granted.
     */
    private void enableMyLocation() {
        // [START maps_check_location_permission]
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            if (map != null) {
                map.setMyLocationEnabled(true);
            }
        } else {
            // Permission to access the location is missing. Show rationale and request permission
            PermissionUtils.requestPermission(this, LOCATION_PERMISSION_REQUEST_CODE,
                    Manifest.permission.ACCESS_FINE_LOCATION, true);
        }
        // [END maps_check_location_permission]
    }

    @Override
    public boolean onMyLocationButtonClick() {
        // Return false so that we don't consume the event and the default behavior still occurs
        // (the camera animates to the user's current position).
        return false;
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {

    }

    // [START maps_check_location_permission_result]
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode != LOCATION_PERMISSION_REQUEST_CODE) {
            return;
        }

        if (PermissionUtils.isPermissionGranted(permissions, grantResults, Manifest.permission.ACCESS_FINE_LOCATION)) {
            enableMyLocation();
        } else {
            // Permission was denied. Display an error message
            // [START_EXCLUDE]
            // Display the missing permission error dialog when the fragments resume.
            permissionDenied = true;
            // [END_EXCLUDE]
        }
    }
    // [END maps_check_location_permission_result]

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        if (permissionDenied) {
            // Permission was not granted, display error dialog.
            showMissingPermissionError();
            permissionDenied = false;
        }
    }

    /**
     * Displays a dialog with error message explaining that the location permission is missing.
     */
    private void showMissingPermissionError() {
        PermissionUtils.PermissionDeniedDialog
                .newInstance(true).show(getSupportFragmentManager(), "dialog");
    }

    void moveCamera(LatLng latLng) {
        float zoom = 16f;
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
    }

    public Bitmap resizeMapIcons(String iconName, int width, int height) {
        Bitmap imageBitmap = BitmapFactory.decodeResource(getResources(), getResources().getIdentifier(iconName, "drawable", getPackageName()));
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(imageBitmap, width, height, false);
        return resizedBitmap;
    }


    // ROUTING PART !!!!!
    public void findRoutes(LatLng Start, LatLng End) {
        if (Start == null || End == null) {
            Toast.makeText(MainMap.this, "Unable to get location", Toast.LENGTH_LONG).show();
        } else {

            Routing routing = new Routing.Builder()
                    .travelMode(AbstractRouting.TravelMode.DRIVING)
                    .withListener(this)
                    .alternativeRoutes(true)
                    .waypoints(Start, End)
                    .key("AIzaSyDJhMM-N-_onyee0mybDFEaeFT3PfMnY78")  //also define your api key here.
                    .build();
            routing.execute();
        }
    }

    //Routing call back functions.
    @Override
    public void onRoutingFailure(RouteException e) {
        View parentLayout = findViewById(android.R.id.content);
        Snackbar snackbar = Snackbar.make(parentLayout, e.toString(), Snackbar.LENGTH_LONG);
        snackbar.show();
        addMarker(places);
    }

    @Override
    public void onRoutingStart() {
        Toast.makeText(MainMap.this, "Finding Route...", Toast.LENGTH_LONG).show();
        addMarker(places);
    }

    //If Route finding success..
    @Override
    public void onRoutingSuccess(ArrayList<Route> route, int shortestRouteIndex) {

        CameraUpdate center = CameraUpdateFactory.newLatLng(start);
        CameraUpdate zoom = CameraUpdateFactory.zoomTo(16);
        if (polylines != null) {
            polylines.clear();
        }
        PolylineOptions polyOptions = new PolylineOptions();
        LatLng polylineStartLatLng = null;
        LatLng polylineEndLatLng = null;


        polylines = new ArrayList<>();
        //add route(s) to the map using polyline
        for (int i = 0; i < route.size(); i++) {

            if (i == shortestRouteIndex) {
                polyOptions.color(getResources().getColor(R.color.purple_700));
                polyOptions.width(7);
                polyOptions.addAll(route.get(shortestRouteIndex).getPoints());
                Polyline polyline = map.addPolyline(polyOptions);
                polylineStartLatLng = polyline.getPoints().get(0);
                int k = polyline.getPoints().size();
                polylineEndLatLng = polyline.getPoints().get(k - 1);
                polylines.add(polyline);

            }

        }

        //Add Marker on route starting position
        MarkerOptions startMarker = new MarkerOptions();
        startMarker.position(polylineStartLatLng);
        startMarker.title("My Location");
        map.addMarker(startMarker);

        //Add Marker on route ending position
        MarkerOptions endMarker = new MarkerOptions();
        endMarker.position(polylineEndLatLng);
        endMarker.title("Destination");
        map.addMarker(endMarker);

        addMarker(places);
    }

    @Override
    public void onRoutingCancelled() {
        findRoutes(start, end);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        findRoutes(start, end);

    }

    private void getMyLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        map.setMyLocationEnabled(true);
        map.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(Location location) {

                myLocation = location;
                /*LatLng ltlng = new LatLng(location.getLatitude(), location.getLongitude());
                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(
                        ltlng, 16f);
                map.animateCamera(cameraUpdate);*/
            }
        });
    }
}
