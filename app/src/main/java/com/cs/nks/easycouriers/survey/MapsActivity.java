package com.cs.nks.easycouriers.survey;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.animation.Animation;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.cs.nks.easycouriers.R;
import com.cs.nks.easycouriers.model.Geo;
import com.cs.nks.easycouriers.util.AppController;
import com.cs.nks.easycouriers.util.ConnectionDetector;
import com.cs.nks.easycouriers.util.UTIL;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, GoogleMap.OnInfoWindowClickListener, Animation.AnimationListener {
    static final int RequestPermissionCode = 101;
    static final int GPS_SETTING_REQUEST_CODE = 104;
    private static final long INTERVAL = 0;
    private static final long FASTEST_INTERVAL = 1;
    private static String result;
    private static double mCurrentlatitude, mCurrentlongitude;
    final Handler mHandler = new Handler();
    LocationRequest mLocationRequest;
    GoogleApiClient mGoogleApiClient;
    Location mCurrentLocation;
    ArrayList<Geo> list_geo = new ArrayList<>();
    UTIL util;
    private GoogleMap mMap;
    private boolean network_enabled = false;
    private boolean gps_enabled = false;
    private Marker marker1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        util = new UTIL(MapsActivity.this);


        if (ActivityCompat.checkSelfPermission(MapsActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            requestLocationPermission();

        } else {
            // Toast.makeText(AndroidGoogleMapsActivity.this,"Permission granted",Toast.LENGTH_SHORT).show();
            CheckGPS();
        }


    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        try {
            // Enabling MyLocation Layer of Google Map
            mMap.setMyLocationEnabled(true);
        } catch (SecurityException e) {
            e.getMessage();
        }


        //  getBranches();

    }

    private void getBranches() {


        // callApiGetBranch() ;
        getBranchesTest();
    }

    private void getBranchesTest() {

        list_geo.clear();
        list_geo.add(new Geo("28.578050", "77.173140", "address 1", "", ""));
        list_geo.add(new Geo("28.633110", "77.282650", "address 2", "", ""));
        list_geo.add(new Geo("28.646820", "77.288190", "address 3", "", ""));
        list_geo.add(new Geo("28.637817", "77.243148", "address 4", "", ""));
        list_geo.add(new Geo("28.633550", "77.139240", "address 5", "", ""));
        list_geo.add(new Geo("28.685630", "77.169840", "address 6", "", ""));

        showBranchesOnMap();
    }


    public void callApiGetBranch() {
        util.showProgressDialog(UTIL.Progress_msg);
        String url = UTIL.Domain_DCDC + UTIL.BranchList_API + "lat=" + mCurrentlatitude + "&long=" + mCurrentlongitude;

        if (mCurrentlatitude == 0 || mCurrentlongitude == 0) {
            url = UTIL.Domain_DCDC + UTIL.BranchList_API;
        }
        String tag_json_obj = "json_obj_req";
        Log.e("Brnch URL>>", url);

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Api Branches", response.toString());
                        util.hideProgressDialog();

                        try {
                            JSONArray jsonArray = response.getJSONArray("branch");

                            if (jsonArray.length() > 0) {
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    String branch_id = jsonObject.getString("branch_id");
                                    String branch_name = jsonObject.getString("branch_name");
                                    String contact_person_name = jsonObject.getString("contact_person_name");
                                    String branch_number = jsonObject.getString("branch_number");
                                    String mobile_number = jsonObject.getString("mobile_number");
                                    String email = jsonObject.getString("email");
                                    String address = jsonObject.getString("address");
                                    String city_name = jsonObject.getString("city_name");

                                    String lat = jsonObject.getString("latitude");
                                    String lng = jsonObject.getString("longitude");

                                    list_geo.add(new Geo(lat, lng, address, branch_id, branch_name));

                                }


                            }

                            if (list_geo.isEmpty()) {
                                Toast.makeText(MapsActivity.this, "No branches founds near by you!", Toast.LENGTH_SHORT).show();
                            }

                            showBranchesOnMap();
                        } catch (Exception e) {
                            e.getCause();
                            Toast.makeText(MapsActivity.this, "No branches found near you!", Toast.LENGTH_SHORT).show();

                        }

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("Api Branches", "Error: " + error.getMessage());
                util.hideProgressDialog();
            }
        });

        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);

    }

    private void CheckGPS() {
        LocationManager lm = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (new ConnectionDetector(this).isConnectingToInternet()) {

        } else {
            Toast.makeText(this, "Your Internet Connection seems to be disabled, Please enable it!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!gps_enabled) {
            displayPromptForEnablingGPS();
        } else {


            initilizeMap();
            getBranchesTest();
        }


    }

    void requestLocationPermission() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.ACCESS_FINE_LOCATION)) {
            ActivityCompat.requestPermissions(MapsActivity.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, RequestPermissionCode);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, RequestPermissionCode);
        }
    }

    public void displayPromptForEnablingGPS() {
        final AlertDialog.Builder builder =
                new AlertDialog.Builder(this);
        final String action = Settings.ACTION_SETTINGS;
        final String message = "Your GPS seems to be disabled, do you want to enable it?";

        builder.setMessage(message)
                .setPositiveButton("YES",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface d, int id) {
                                //  startActivityForResult(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS, GPS_SETTING_REQUEST_CODE));
                                startActivityForResult(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS), GPS_SETTING_REQUEST_CODE);
                                d.dismiss();
                            }
                        })
                .setNegativeButton("NO",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface d, int id) {
                                finish();
                            }
                        })
                .setCancelable(false);
        builder.create().show();
    }

    public void initilizeMap() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        mGoogleApiClient.connect();
        Log.e("mGoogleApiClient", "" + mGoogleApiClient.isConnected());
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        createLocationRequest();


    }

    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        //    Toast.makeText(getApplicationContext(), "dddd", Toast.LENGTH_LONG).show();
        return false;
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        try {
            mCurrentLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            if (mCurrentLocation != null) {
                mCurrentlatitude = mCurrentLocation.getLatitude();
                mCurrentlongitude = mCurrentLocation.getLongitude();
                Log.e("my lat_long", mCurrentlatitude + "," + mCurrentlongitude);
                getBranches();
            } else {
                Log.e("mCurrentLocation ", "null");
            }
        } catch (SecurityException e) {
            e.getMessage();
            Log.e("mCurrentLocation ", "SecurityException");
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        //   Toast.makeText(getApplicationContext(), "dd", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {

    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }

    private void showBranchesOnMap() {

        //   mHandler = new Handler();

        final Runnable r = new Runnable() {
            public void run() {

                for (int j = 0; j < list_geo.size(); j++) {

                    String _lat = list_geo.get(j).getLat();
                    String _lng = list_geo.get(j).getLng();
                    String _address = list_geo.get(j).getAddress();
                    String _branch_id = list_geo.get(j).getBranch_Id();
                    String _branch_name = list_geo.get(j).getBranch_Name();
                    double lat = Double.parseDouble(_lat);
                    double lng = Double.parseDouble(_lng);

                    Branch(lat, lng, _address, list_geo.get(j));

                }


                // mHandler.postDelayed(this, 1000);
            }
        };

        mHandler.postDelayed(r, 1000);


    }

    public void Branch(double latitude, double longitude, String branch_address, Geo geo) {
        LatLng branchLatLng = new LatLng(latitude, longitude);
        Log.d("branch add:", "" + branch_address);
        try {
            marker1 = mMap.addMarker(
                    new MarkerOptions()
                            .position(branchLatLng)
                            .title(branch_address)
                            .icon((BitmapDescriptorFactory

                                    .fromResource(R.drawable.branch))));
            marker1.setTag(geo);
            marker1.showInfoWindow();
        } catch (Exception e) {
            e.getMessage();
        }


        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {

            @Override
            public void onInfoWindowClick(Marker marker) {
                // TODO Auto-generated method stub

                Geo geo1 = (Geo) marker.getTag();

                LatLng position = marker.getPosition(); //
           /*     Intent i = new Intent(MapsActivity.this, ActivityWithNavigationMenuPatient.class);
                i.putExtra("map", "1");
                i.putExtra(UTIL.LAT, position.latitude + "");
                i.putExtra(UTIL.LNG, position.longitude + "");
                i.putExtra(UTIL.BRANCH_ID, geo1.getBranch_Id());
                i.putExtra(UTIL.BRANCH_NAME, geo1.getBranch_Name());

                startActivity(i);
                finish();
*/

                Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse("google.navigation:q=" + position.latitude + "," + position.longitude));
                startActivity(intent);


            }
        });

        LatLng CurrentlatLng = new LatLng(mCurrentlatitude, mCurrentlongitude);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(CurrentlatLng));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(CurrentlatLng, 13));
        drawline(latitude, longitude);
    }

    public void drawline(double latitude, double longitude) {
        try {
            LatLng CurrentlatLng = new LatLng(mCurrentlatitude, mCurrentlongitude);
            Polyline line = mMap.addPolyline(new PolylineOptions().add(CurrentlatLng).add(new LatLng(latitude, longitude)).width(5).color(Color.parseColor("#3e8e3e")));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {

            case RequestPermissionCode:

                if (grantResults.length > 0) {

                    //  boolean Location = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean location = grantResults[0] == PackageManager.PERMISSION_GRANTED;

                    if (location) { //&& Location ) {
                        CheckGPS();
                        //  Toast.makeText(HomeActivity.this, "Permission Granted", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(this, "Permission Denied", Toast.LENGTH_LONG).show();
                        finish();
                    }
                }

                break;
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (resultCode == RESULT_OK) {
            if (requestCode == GPS_SETTING_REQUEST_CODE) {
                CheckGPS();
            }
        } else if (resultCode == RESULT_CANCELED) {
            if (requestCode == GPS_SETTING_REQUEST_CODE) {
                CheckGPS();
            }
        }


    }
}
