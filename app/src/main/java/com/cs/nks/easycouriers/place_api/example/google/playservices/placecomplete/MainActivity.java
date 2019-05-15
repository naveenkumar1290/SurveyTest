/*
 * Copyright (C) 2015 Google Inc. All Rights Reserved.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.cs.nks.easycouriers.place_api.example.google.playservices.placecomplete;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.content.res.Resources;

import android.location.Address;

import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.cs.nks.easycouriers.R;
import com.cs.nks.easycouriers.place_api.example.android.common.activities.SampleActivityBase;
import com.cs.nks.easycouriers.place_api.example.android.common.logger.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;

import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;


import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;



public class MainActivity extends SampleActivityBase
        implements GoogleApiClient.OnConnectionFailedListener {

    /**
     * GoogleApiClient wraps our service connection to Google Play Services and provides access
     * to the user's sign in state as well as the Google's APIs.
     */
    //  protected GoogleApiClient mGoogleApiClient;

    private PlaceAutocompleteAdapter mAdapter;

    private AutoCompleteTextView mAutocompleteView;

    private TextView mPlaceDetailsText;

    private TextView mPlaceDetailsAttribution;
    private Button  button_select_address;
   /* private static final LatLngBounds BOUNDS_GREATER_SYDNEY = new LatLngBounds(
            new LatLng(-34.041458, 150.790100), new LatLng(-33.682247, 151.383362));
*/

    private static final LatLngBounds BOUNDS_INDIA = new LatLngBounds(new LatLng(23.63936, 68.14712), new LatLng(28.20453, 97.34466));
    private LinearLayout tv_location;

    /*location*/

   /* private final static int PLAY_SERVICES_REQUEST = 1000;
    private final static int REQUEST_CHECK_SETTINGS = 2000;

    private Location mLastLocation;

    // Google client to interact with Google API
*/
     private GoogleApiClient mGoogleApiClient;

    double latitude;
    double longitude;

    // list of permissions

   /* ArrayList<String> permissions=new ArrayList<>();
    PermissionUtils permissionUtils;

    boolean isPermissionGranted;*/

    private Location mLastLocation;
    static final Integer LOCATION = 0x1;
   // GoogleApiClient client;
    LocationRequest mLocationRequest;
    PendingResult<LocationSettingsResult> result;
    static final Integer GPS_SETTINGS = 0x7;
    private LocationManager locationManager;
    private String provider;
ImageView img_back;
    int RequestCodeLocation=0;
    @Override
    public void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    public void onStop() {
        super.onStop();
        mGoogleApiClient.disconnect();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Construct a GoogleApiClient for the {@link Places#GEO_DATA_API} using AutoManage
        // functionality, which automatically sets up the API client to handle Activity lifecycle
        // events. If your activity does not extend FragmentActivity, make sure to call connect()
        // and disconnect() explicitly.

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, 0 /* clientId */, this)
                .addApi(Places.GEO_DATA_API)       //for geo data adapter
                .addApi(LocationServices.API)      //for current loc(gps)
                .build();
        setContentView(R.layout.activity_main);






        /******************************for gps current location***********************************************/
        //location
      /*  client = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, 0 *//* clientId *//*, this)
                .addApi(LocationServices.API)
                .build();
*/
        // Get the location manager
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        // Define the criteria how to select the locatioin provider -> use
        // default
        Criteria criteria = new Criteria();
        provider = locationManager.getBestProvider(criteria, false);
        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
              //  Toast.makeText(getApplicationContext(), "onLocationChanged", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
                //Toast.makeText(getApplicationContext(), "onStatusChanged", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onProviderEnabled(String provider) {
               // Toast.makeText(getApplicationContext(), "onProviderEnabled", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onProviderDisabled(String provider) {
               // Toast.makeText(getApplicationContext(), "onProviderDisabled", Toast.LENGTH_SHORT).show();
            }
        };
        try {
            locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER, 1000, 1, locationListener);

         //   locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
            //
        } catch (SecurityException e) {
        }

       // getIntent().getExtras().getString("");

        tv_location =  findViewById(R.id.tv_location);
        tv_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
                boolean enabled = locationManager
                        .isProviderEnabled(LocationManager.GPS_PROVIDER);
                if (!enabled) {
                    askForPermission(Manifest.permission.ACCESS_FINE_LOCATION, LOCATION);

                } else {
                    new AsyncGetLocation().execute();

                }


            }
        });


      button_select_address = (Button) findViewById(R.id.button_select_address);
        button_select_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String location=mAutocompleteView.getText().toString().trim();
                Intent intent=new Intent();
                intent.putExtra("location",location);
                setResult(1,intent);
                finish();
            }
        });
        img_back =  findViewById(R.id.img_back);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              onBackPressed();
            }
        });



        /******************************for google places***********************************************/
        // Retrieve the AutoCompleteTextView that will display Place suggestions.
        mAutocompleteView = (AutoCompleteTextView)
                findViewById(R.id.autocomplete_places);

        // Register a listener that receives callbacks when a suggestion has been selected
        mAutocompleteView.setOnItemClickListener(mAutocompleteClickListener);

        // Retrieve the TextViews that will display details and attributions of the selected place.
        mPlaceDetailsText = (TextView) findViewById(R.id.place_details);
        mPlaceDetailsAttribution = (TextView) findViewById(R.id.place_attribution);

        // Set up the adapter that will retrieve suggestions from the Places Geo Data API that cover
        // the entire world.
        mAdapter = new PlaceAutocompleteAdapter(this, mGoogleApiClient, BOUNDS_INDIA,
                null);
        mAutocompleteView.setAdapter(mAdapter);

        // Set up the 'clear text' button that clears the text in the autocomplete view
        Button clearButton = (Button) findViewById(R.id.button_clear);
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAutocompleteView.setText("");
            }
        });

    }

    /******************************Methods for gps current location***********************************************/
    private void askForPermission(String permission, Integer requestCode) {
        if (ContextCompat.checkSelfPermission(MainActivity.this, permission) != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, permission)) {

                //This is called if user has denied the permission before
                //In this case I am just asking the permission again
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{permission}, requestCode);

            } else {

                ActivityCompat.requestPermissions(MainActivity.this, new String[]{permission}, requestCode);
            }
        } else {
            //  Toast.makeText(this, "" + permission + " is already granted.", Toast.LENGTH_SHORT).show();
            askForGPS();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (ActivityCompat.checkSelfPermission(this, permissions[0]) == PackageManager.PERMISSION_GRANTED) {
            switch (requestCode) {
                //Location
                case 1:
                    askForGPS();
                    break;

            }

         //   Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show();
        } else {
          //  Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
        }
    }

    private void askForGPS() {
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(30 * 1000);
        mLocationRequest.setFastestInterval(5 * 1000);
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(mLocationRequest);
        builder.setAlwaysShow(true);
        result = LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient, builder.build());
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult result) {
                final Status status = result.getStatus();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        // NO need to show the dialog;
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        //  GPS turned off, Show the user a dialog
                        // Show the dialog by calling startResolutionForResult(), and check the result
                        // in onActivityResult().
                        try {
                            status.startResolutionForResult(MainActivity.this, GPS_SETTINGS);
                        } catch (IntentSender.SendIntentException e) {

                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        // Location settings are unavailable so not possible to show any dialog now
                        break;
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GPS_SETTINGS) {

            if (resultCode == RESULT_OK) {
                Toast.makeText(getApplicationContext(), "GPS enabled", Toast.LENGTH_LONG).show();
                /*nks*/
                new AsyncGetLocation().execute();
                /*nks*/
            } else {

                Toast.makeText(getApplicationContext(), "GPS is not enabled", Toast.LENGTH_LONG).show();
            }

        }
    }


    public Address getAddress(double latitude, double longitude) {
        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(this, Locale.getDefault());

        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            return addresses.get(0);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;

    }


    public void getAddress() {

        Address locationAddress = getAddress(latitude, longitude);

        if (locationAddress != null) {
            String address = locationAddress.getAddressLine(0);
            String address1 = locationAddress.getAddressLine(1);
            String city = locationAddress.getLocality();
            String state = locationAddress.getAdminArea();
            String country = locationAddress.getCountryName();
            String postalCode = locationAddress.getPostalCode();

            String currentLocation;

            if (!TextUtils.isEmpty(address)) {
                currentLocation = address;

                if (!TextUtils.isEmpty(address1))
                    currentLocation += "\n" + address1;

                if (!TextUtils.isEmpty(city)) {
                    currentLocation += "\n" + city;

                    if (!TextUtils.isEmpty(postalCode))
                        currentLocation += " - " + postalCode;
                } else {
                    if (!TextUtils.isEmpty(postalCode))
                        currentLocation += "\n" + postalCode;
                }

                if (!TextUtils.isEmpty(state))
                    currentLocation += "\n" + state;

                if (!TextUtils.isEmpty(country))
                    currentLocation += "\n" + country;

                // tvEmpty.setVisibility(View.GONE);
                mAutocompleteView.setText(currentLocation);
                //tvAddress.setVisibility(View.VISIBLE);

                //if(!btnProceed.isEnabled())
                //  btnProceed.setEnabled(true);


            }

        }

    }


    class AsyncGetLocation extends AsyncTask<String, Void, String> {
        ProgressDialog progressDoalog;
        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            progressDoalog = new ProgressDialog(MainActivity.this);
            progressDoalog.setMessage("Kindly Wait");
            progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDoalog.setCancelable(true);
            progressDoalog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            //Wait 10 seconds to see if we can get a location from either network or GPS, otherwise stop
            Long t = Calendar.getInstance().getTimeInMillis();
            try {
                mLastLocation = LocationServices.FusedLocationApi
                        .getLastLocation(mGoogleApiClient);
            }
            catch (SecurityException e){
                e.getMessage();
            }
            while (mLastLocation==null && Calendar.getInstance().getTimeInMillis() - t < 15000) {
                try {
                   // Thread.sleep(1000);
                    Thread.sleep(500);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            try {
                //    mLastLocation = locationManager.getLastKnownLocation(provider);
                mLastLocation = LocationServices.FusedLocationApi
                        .getLastLocation(mGoogleApiClient);
                if (mLastLocation != null) {
                    latitude = mLastLocation.getLatitude();
                    longitude = mLastLocation.getLongitude();
                    getAddress();

                }
                else
                {
                    //Couldn't find location, do something like show an alert dialog
                }

            } catch (SecurityException e) {
                e.printStackTrace();
            }

            if(progressDoalog.isShowing()){
                progressDoalog.dismiss();
            }
        }


    }


    /******************************Methods for google places***********************************************/

    /**
     * Listener that handles selections from suggestions from the AutoCompleteTextView that
     * displays Place suggestions.
     * Gets the place id of the selected item and issues a request to the Places Geo Data API
     * to retrieve more details about the place.
     *

     */
/*

 */
/* Request updates at startup *//*

    @Override
    protected void onResume() {
        super.onResume();

       // locationManager.requestLocationUpdates(provider, 400, 1, this);
    }

    */
/* Remove the locationlistener updates when Activity is paused *//*

    @Override
    protected void onPause() {
        super.onPause();
        locationManager.removeUpdates(this);
    }


*/



    private AdapterView.OnItemClickListener mAutocompleteClickListener
            = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            /*
             Retrieve the place ID of the selected item from the Adapter.
             The adapter stores each Place suggestion in a AutocompletePrediction from which we
             read the place ID and title.
              */
            final AutocompletePrediction item = mAdapter.getItem(position);
            final String placeId = item.getPlaceId();
            final CharSequence primaryText = item.getPrimaryText(null);

            Log.i(TAG, "Autocomplete item selected: " + primaryText);

            /*
             Issue a request to the Places Geo Data API to retrieve a Place object with additional
             details about the place.
              */
            PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi
                    .getPlaceById(mGoogleApiClient, placeId);
            placeResult.setResultCallback(mUpdatePlaceDetailsCallback);

            Toast.makeText(getApplicationContext(), "Clicked: " + primaryText,
                    Toast.LENGTH_SHORT).show();
            Log.i(TAG, "Called getPlaceById to get Place details for " + placeId);
        }
    };

    /**
     * Callback for results from a Places Geo Data API query that shows the first place result in
     * the details view on screen.
     */
    private ResultCallback<PlaceBuffer> mUpdatePlaceDetailsCallback
            = new ResultCallback<PlaceBuffer>() {
        @Override
        public void onResult(PlaceBuffer places) {
            if (!places.getStatus().isSuccess()) {
                // Request did not complete successfully
                Log.e(TAG, "Place query did not complete. Error: " + places.getStatus().toString());
                places.release();
                return;
            }
            // Get the Place object from the buffer.
            final Place place = places.get(0);

            // Format details of the place for display and show it in a TextView.
            mPlaceDetailsText.setText(formatPlaceDetails(getResources(), place.getName(),
                    place.getId(), place.getAddress(), place.getPhoneNumber(),
                    place.getWebsiteUri()));

            // Display the third party attributions if set.
            final CharSequence thirdPartyAttribution = places.getAttributions();
            if (thirdPartyAttribution == null) {
                mPlaceDetailsAttribution.setVisibility(View.GONE);
            } else {
                mPlaceDetailsAttribution.setVisibility(View.VISIBLE);
                mPlaceDetailsAttribution.setText(Html.fromHtml(thirdPartyAttribution.toString()));
            }

            Log.i(TAG, "Place details received: " + place.getName());

            places.release();
        }
    };

    private static Spanned formatPlaceDetails(Resources res, CharSequence name, String id,
                                              CharSequence address, CharSequence phoneNumber, Uri websiteUri) {
        Log.e(TAG, res.getString(R.string.place_details, name, id, address, phoneNumber,
                websiteUri));
        return Html.fromHtml(res.getString(R.string.place_details, name, id, address, phoneNumber,
                websiteUri));

    }

    /**
     * Called when the Activity could not connect to Google Play services and the auto manager
     * could resolve the error automatically.
     * In this case the API is not available and notify the user.
     *
     * @param connectionResult can be inspected to determine the cause of the failure
     */
    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

        Log.e(TAG, "onConnectionFailed: ConnectionResult.getErrorCode() = "
                + connectionResult.getErrorCode());

        // TODO(Developer): Check error code and notify the user of error state and resolution.
        Toast.makeText(this,
                "Could not connect to Google API Client: Error " + connectionResult.getErrorCode(),
                Toast.LENGTH_SHORT).show();
    }


}
