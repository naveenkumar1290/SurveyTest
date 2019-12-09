package com.cs.nks.easycouriers.dcdc.patient;


import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cs.nks.easycouriers.R;
import com.cs.nks.easycouriers.activity.ActivityWithNavigationMenuPatient;
import com.cs.nks.easycouriers.model.BranchLocation;
import com.cs.nks.easycouriers.place_api.common.activities.SampleActivityBase_New;
import com.cs.nks.easycouriers.util.AppController;
import com.cs.nks.easycouriers.util.ConnectionDetector;
import com.cs.nks.easycouriers.util.UTIL;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.tasks.OnSuccessListener;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Timer;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.LOCATION_SERVICE;

//import android.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class EnvironmentFragment extends SampleActivityBase_New implements TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener, GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks, LocationListener {

    public static final Integer GPS_SETTINGS = 0x7;
    static final Integer LOCATION = 0x1;
    static final int RequestPermissionCode = 100;
    private static double map_latitude, map_longitude;
    private static String map_branchId, map_branchName;
    Context myContext;
    UTIL utill;
    Timer timer;
    EditText
            et_AppointmentDate,
            et_TimingFrom,
            et_TimingTo,
    //  et_Status,
    et_Remarks,
            et_DoctorName;//
            //PatientName;
    int RequestCodePickUp = 1;
    int RequestCodeDrop = 2;
    //   Spinner et_category;
    Button btnSearch;
    AlertDialog alertDialog1;
    Boolean clicked_TimeFrom = false;
    ArrayList<BranchLocation> list_branch = new ArrayList<>();
 //   ArrayList<BranchLocation> list_patient = new ArrayList<>();
    ArrayList<BranchLocation> list_status = new ArrayList<>();
    UTIL util;
    // GoogleApiClient client;
    LocationRequest mLocationRequest;
    PendingResult<LocationSettingsResult> result;
    double latitude;
    double longitude;
    String city = "";
    String Branch_id;
    private int currentPage = 0;
    private ViewPager mViewPager;
    /*location*/
    private GoogleApiClient mGoogleApiClient;
    private LocationManager locationManager;
    private Location mLastLocation;
    private String provider;
    private FusedLocationProviderClient mFusedLocationClient;
    //boolean ApiCalledSecondTime=false;
    public EnvironmentFragment() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
        if (mGoogleApiClient.isConnected()) {
            //  startLocationUpdates();
            Log.d(TAG, "Location update resumed .....................");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mGoogleApiClient.isConnected()) {
            //   startLocationUpdates();
            Log.d(TAG, "Location update resumed .....................");
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        mGoogleApiClient.disconnect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.e(TAG, "onConnectionFailed: ConnectionResult.getErrorCode() = "
                + connectionResult.getErrorCode());
        // TODO(Developer): Check error code and notify the user of error state and resolution.
        Toast.makeText(getActivity(),
                "Could not connect to Google API Client: Error " + connectionResult.getErrorCode(),
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myContext = getActivity();

      /*  if (!isGooglePlayServicesAvailable()) {
            finish();
        }
*/
        View rootView = inflater.inflate(R.layout.fragment_environment, container, false);


        mGoogleApiClient = new GoogleApiClient.Builder(myContext)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        mGoogleApiClient.connect();

        getActivity().setTitle(UTIL.getTitle("Report Environment"));

        //setViewPager(rootView);
        util = new UTIL(getActivity());
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());


        setView(rootView);
    //    getLocFromFused();
       // getIntentData();
        //  callApiGetBranch();
        getBranchesTest();
        return rootView;


    }
    private void getBranchesTest() {

        list_branch.clear();
        list_branch.add(new BranchLocation("address 1", "","28.578050", "77.173140"  ));
        list_branch.add(new BranchLocation("address 2", "", "28.633110", "77.282650" ));
        list_branch.add(new BranchLocation("address 3", "", "28.646820", "77.288190" ));
        list_branch.add(new BranchLocation("address 4", "","28.637817", "77.243148"  ));
        list_branch.add(new BranchLocation("address 5", "", "28.633550", "77.139240" ));
        list_branch.add(new BranchLocation("address 6", "","28.685630", "77.169840"));




    }
    public void getIntentData() {
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            String lat = bundle.getString(UTIL.LAT, "0");
            String lng = bundle.getString(UTIL.LNG, "0");
            map_latitude = Double.parseDouble(lat);
            map_longitude = Double.parseDouble(lng);
            map_branchId = bundle.getString(UTIL.BRANCH_ID, "0");
            map_branchName = bundle.getString(UTIL.BRANCH_NAME, "0");

        }
    }

    public void getLocFromFused() {

        locationManager = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);
        boolean enabled = locationManager
                .isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (!enabled) {
            askForPermission(Manifest.permission.ACCESS_FINE_LOCATION, LOCATION);

        } else {


            try {
                mFusedLocationClient.getLastLocation()
                        .addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
                            @Override
                            public void onSuccess(Location location) {
                                // Got last known location. In some rare situations this can be null.
                                if (location != null) {
                                    // Logic to handle location object
                                    latitude = location.getLatitude();
                                    longitude = location.getLongitude();
                                    callApiGetBranch(latitude,longitude);
                                    // getAddress();
                                } else {
                                    callApiGetBranch(0.000000,0.000000);
                                }
                            }

                        });
            } catch (SecurityException e) {
                e.getCause();
            }

        }


    }

    private void setView(View rootView) {

        et_AppointmentDate = (EditText) rootView.findViewById(R.id.et_AppointmentDate);
        et_TimingFrom = (EditText) rootView.findViewById(R.id.et_TimingFrom);
        et_TimingTo = (EditText) rootView.findViewById(R.id.et_TimingTo);
        //  et_Status = (EditText) rootView.findViewById(R.id.et_Status);
        et_Remarks = (EditText) rootView.findViewById(R.id.et_Remarks);
        et_DoctorName = (EditText) rootView.findViewById(R.id.et_DoctorName);
       // PatientName = (EditText) rootView.findViewById(R.id.PatientName);

        btnSearch = (Button) rootView.findViewById(R.id.btnsubmitAppointment);


        et_AppointmentDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Click_getDate();
            }
        });
        et_TimingFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clicked_TimeFrom = true;
                Click_getTime("Timing From");
            }
        });
        et_TimingTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clicked_TimeFrom = false;
                Click_getTime("Timing To");
            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  getActivity().startActivity(new Intent(getActivity(), AvailableRides.class));
                String patientId = UTIL.getPref(myContext, UTIL.Key_UserId);
                String CenterName = et_DoctorName.getText().toString().trim();
                String AppointmentDate = et_AppointmentDate.getText().toString().trim();
                String TimingFrom = et_TimingFrom.getText().toString().trim();
                String TimingTo = et_TimingTo.getText().toString().trim();
                String Remarks = et_Remarks.getText().toString().trim();

               /* if (CenterName.length() == 0) {
                    Toast.makeText(getActivity(), "Please select a center!", Toast.LENGTH_SHORT).show();
                } else*/ if (AppointmentDate.length() == 0) {
                    Toast.makeText(getActivity(), "Please enter appointment date!", Toast.LENGTH_SHORT).show();
                } else if (TimingFrom.length() == 0) {
                    Toast.makeText(getActivity(), "Please enter timing from!", Toast.LENGTH_SHORT).show();
                } else if (TimingTo.length() == 0) {
                    Toast.makeText(getActivity(), "Please enter timing to!", Toast.LENGTH_SHORT).show();
                } else if (Remarks.length() == 0) {
                    Toast.makeText(getActivity(), "Please enter remarks!", Toast.LENGTH_SHORT).show();
                } else {
                    if (new ConnectionDetector(getActivity()).isConnectingToInternet()) {
                        Api_Regisetration(patientId, Branch_id, CenterName, AppointmentDate,
                                TimingFrom, TimingTo, Remarks);
                        /*Api_Regisetration(patientId, "1", "test", AppointmentDate,
                                TimingFrom, TimingTo, Remarks);*/
                    } else {
                        Toast.makeText(getActivity(), UTIL.NoInternet, Toast.LENGTH_SHORT).show();
                    }
                }


            }
        });




        et_DoctorName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogNew("Center", getActivity(), et_DoctorName, list_branch);
            }
        });



    }

    private void callApiGetBranch(double latitude,double longitude) {
        if (new ConnectionDetector(getActivity()).isConnectingToInternet()) {
            getBranch(latitude,longitude);
        } else {
            Toast.makeText(getActivity(), "No internet", Toast.LENGTH_SHORT).show();
        }
    }

    private void Click_getDate() {

        Calendar calendar = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                EnvironmentFragment.this,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        dpd.setThemeDark(false);
        dpd.vibrate(false);
        dpd.dismissOnPause(false);
        dpd.showYearPickerFirst(false);
        dpd.setVersion(DatePickerDialog.Version.VERSION_1);
        dpd.setAccentColor(getResources().getColor(R.color.colorAccent));


        dpd.setTitle("Appointment Date");
        dpd.setYearRange(1985, 2028);
        dpd.setMinDate(calendar);
        dpd.show(getActivity().getFragmentManager(), "dialog");


    }

    private void Click_getTime(String Title) {
        boolean is24HourMode = true;
        final Calendar calendar = Calendar.getInstance();
        TimePickerDialog tpd = TimePickerDialog.newInstance(
                EnvironmentFragment.this,
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                true);
        tpd.setThemeDark(false);
        tpd.vibrate(false);
        tpd.dismissOnPause(false);
        tpd.enableSeconds(false);
        tpd.setVersion(TimePickerDialog.Version.VERSION_2);

        tpd.setAccentColor(getResources().getColor(R.color.colorAccent));
        tpd.setTitle(Title);


        tpd.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                Log.d("TimePicker", "Dialog was cancelled");
            }
        });
        tpd.show(getActivity().getFragmentManager(), "Timepickerdialog");


    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date = +dayOfMonth + "/" + (++monthOfYear) + "/" + year;

        String dayOfMonthString = dayOfMonth < 10 ? "0" + dayOfMonth : "" + dayOfMonth;
        String monthOfYearString = monthOfYear < 10 ? "0" + monthOfYear : "" + monthOfYear;

        String date_1 = dayOfMonthString + "-" + monthOfYearString + "-" + year;
        et_AppointmentDate.setText(date_1);

    }

    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
        String hourString = hourOfDay < 10 ? "0" + hourOfDay : "" + hourOfDay;
        String minuteString = minute < 10 ? "0" + minute : "" + minute;
        String secondString = second < 10 ? "0" + second : "" + second;
        String time = "You picked the following time: " + hourString + "h" + minuteString + "m" + secondString + "s";
        String t = hourString + ":" + minuteString;
        //19:11:09.983
        //  et_Time.setText(t);
        String time_1 = hourString + ":" + minuteString;//+ ":" + secondString + "." + "000";

        // et_Time.setText(time_1);
        if (clicked_TimeFrom) {
            et_TimingFrom.setText(time_1);
        } else {
            et_TimingTo.setText(time_1);
        }


    }

    public void showDialogNew(String dialog_title, final Context context, final EditText editText, ArrayList<BranchLocation> list) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.custom_dialog, null);
        dialogBuilder.setView(dialogView);

        final EditText autoText_TimeZone = (EditText) dialogView.findViewById(R.id.autoText_TimeZone);
        final ListView listvw = (ListView) dialogView.findViewById(R.id.listview);


        ArrayAdapter<BranchLocation> adapter = new ArrayAdapter<BranchLocation>(context,
                android.R.layout.simple_list_item_1, android.R.id.text1, list);
        listvw.setAdapter(adapter);

        listvw.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                BranchLocation spinner_ = (BranchLocation) listvw.getItemAtPosition(position);
                String txt = spinner_.get_text();
                Branch_id = spinner_.get_id();
                editText.setText(txt);


                try {
                    alertDialog1.dismiss();
                } catch (Exception e) {
                    e.getMessage();
                }


            }
        });


        dialogBuilder.setTitle(dialog_title);

        alertDialog1 = dialogBuilder.create();
        if (!alertDialog1.isShowing()) {
            alertDialog1.show();
        }
    }

    public void getBranch(double lat,double lng) {



        util.showProgressDialog(UTIL.Progress_msg);
        String url = UTIL.Domain_DCDC + UTIL.BranchList_API + "lat=" + String.valueOf(lat) + "&long=" + String.valueOf(lng);

        if (latitude == 0 || longitude == 0) {
            url = UTIL.Domain_DCDC + UTIL.BranchList_API;
        }
        String tag_json_obj = "json_obj_req";
        Log.e("Brnch URL>>", url);

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString());
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
                                //    String address = jsonObject.getString("address");
                                    String city_name = jsonObject.getString("city_name");

                                    String lat = jsonObject.getString("latitude");
                                    String lng = jsonObject.getString("longitude");


                                    list_branch.add(new BranchLocation(branch_name, branch_id, lat, lng));
                                }

                                setBranchFromMap();
                            }



                        } catch (Exception e) {
                            e.getCause();
                        }

                        if (list_branch.isEmpty()) {
                            callApiGetBranch(0.000000,0.000000);
                        }


                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                util.hideProgressDialog();
            }
        });

        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);

    }

    public void setBranchFromMap() {

        if (!map_branchName.equals("")) {
            Branch_id = map_branchId;
            et_DoctorName.setText(map_branchName);
        }
//        for(BranchLocation doctor:list_branch){
//           String branchID= doctor.get_id();
//           if(map_branchId.equals(branchID)){
//
//           }
//        }


    }

    public void Api_Regisetration(final String p_id,
                                  final String branch_id,
                                  final String center_name,
                                  final String appointment_date,
                                  final String from_time,
                                  final String to_time,
                                  final String remarks
    ) {

        String URL = UTIL.Domain_DCDC + UTIL.AppointmentReg_API;
        util.showProgressDialog(UTIL.Progress_msg);
        StringRequest postRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        util.hideProgressDialog();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Log.d("Response", response);
                            String status = jsonObject.getString("status");
                            String msg = jsonObject.getString("msg");
                            if (status.equals("1")) {
                                Toast.makeText(getActivity(), "Successful", Toast.LENGTH_SHORT).show();
                                //  getActivity().finish();
                              /*  Intent i = new Intent(getActivity(), ActivityWithNavigationMenuPatient.class);
                                startActivity(i);*/
                                ((ActivityWithNavigationMenuPatient) getActivity()).replaceFragmnt(new ScheduleFragment());

                            } else {
                                Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            e.getMessage();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        util.hideProgressDialog();
                        Log.d("Error.Response", String.valueOf(error));
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("p_id", p_id);
                params.put("branch_id", branch_id);
                params.put("center_name", center_name);
                params.put("appointment_date", parseDateToddMMyyyy(appointment_date));
                params.put("from_time", from_time);
                params.put("to_time", to_time);
                params.put("remarks", remarks);
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        queue.add(postRequest);
    }

    public String parseDateToddMMyyyy(String time) {
        String inputPattern = "dd-MM-yyyy";
        String outputPattern = "yyyy-MM-dd";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);
        Date date = null;
        String str = null;
        try {
            date = inputFormat.parse(time);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }


    /******************************Methods for gps current location***********************************************/
    private void askForPermission(String permission, Integer requestCode) {
        if (ContextCompat.checkSelfPermission(getActivity(), permission) != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), permission)) {

                //This is called if user has denied the permission before
                //In this case I am just asking the permission again
                ActivityCompat.requestPermissions(getActivity(), new String[]{permission}, requestCode);

            } else {

                ActivityCompat.requestPermissions(getActivity(), new String[]{permission}, requestCode);
            }
        } else {
            //  Toast.makeText(this, "" + permission + " is already granted.", Toast.LENGTH_SHORT).show();
            askForGPS();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (ActivityCompat.checkSelfPermission(getActivity(), permissions[0]) == PackageManager.PERMISSION_GRANTED) {
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
                            status.startResolutionForResult(getActivity(), GPS_SETTINGS);
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GPS_SETTINGS) {

            if (resultCode == RESULT_OK) {
                Toast.makeText(getActivity(), "GPS enabled", Toast.LENGTH_LONG).show();
                /*nks*/
                new AsyncGetLocation().execute();
                /*nks*/
            } else {

                Toast.makeText(getActivity(), "GPS is not enabled", Toast.LENGTH_LONG).show();
            }

        }
    }

    public void getAddressNew() {

    }

    public void getAddress() {

        Address locationAddress = getAddress(latitude, longitude);

        try {
            Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            city = addresses.get(0).getAddressLine(0);
            String stateName = addresses.get(0).getAddressLine(1);
            String countryName = addresses.get(0).getAddressLine(2);
            Toast.makeText(getActivity(), city, Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.getCause();
        }


        if (locationAddress != null) {
            String address = locationAddress.getAddressLine(0);
            //  String address1 = locationAddress.getAddressLine(1);
            //   city = locationAddress.getLocality();
            //  String state = locationAddress.getAdminArea();
            //   String country = locationAddress.getCountryName();
            //  String postalCode = locationAddress.getPostalCode();
            //  String currentLocation;


            if (!TextUtils.isEmpty(address)) {
                //   currentLocation = address;
/*

                if (!TextUtils.isEmpty(address1))
                    currentLocation += "\n" + address1;
*/

               /* if (!TextUtils.isEmpty(city)) {
                    currentLocation += "\n" + city;

                    if (!TextUtils.isEmpty(postalCode))
                        currentLocation += " - " + postalCode;
                } else {
                    if (!TextUtils.isEmpty(postalCode))
                        currentLocation += "\n" + postalCode;
                }*/

            /*    if (!TextUtils.isEmpty(state))
                    currentLocation += "\n" + state;

                if (!TextUtils.isEmpty(country))
                    currentLocation += "\n" + country;*/

                // tvEmpty.setVisibility(View.GONE);
                //   mAutocompleteView.setText(currentLocation);
                //tvAddress.setVisibility(View.VISIBLE);

                //if(!btnProceed.isEnabled())
                //  btnProceed.setEnabled(true);


            }

        }

    }

    public Address getAddress(double latitude, double longitude) {
        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(getActivity(), Locale.getDefault());

        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            return addresses.get(0);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;

    }

    @Override
    public void onConnected(Bundle bundle) {
        Log.d(TAG, "onConnected - isConnected ...............: " + mGoogleApiClient.isConnected());
        // startLocationUpdates();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d(TAG, "Firing onLocationChanged..............................................");
        //  mCurrentLocation = location;
        //  mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());
        // updateUI();
    }

    private void updateUI() {
        Log.d(TAG, "UI update initiated .............");
       /* if (null != mCurrentLocation) {
            String lat = String.valueOf(mCurrentLocation.getLatitude());
            String lng = String.valueOf(mCurrentLocation.getLongitude());
            tvLocation.setText("At Time: " + mLastUpdateTime + "\n" +
                    "Latitude: " + lat + "\n" +
                    "Longitude: " + lng + "\n" +
                    "Accuracy: " + mCurrentLocation.getAccuracy() + "\n" +
                    "Provider: " + mCurrentLocation.getProvider());
        } else {
            Log.d(TAG, "location is null ...............");
        }*/
    }

    protected void startLocationUpdates() {
        try {
            PendingResult<Status> pendingResult = LocationServices.FusedLocationApi.requestLocationUpdates(
                    mGoogleApiClient, mLocationRequest, this);
            Log.d(TAG, "Location update started ..............: ");
        } catch (SecurityException e) {
            e.getMessage();
        }
    }
  /*  private boolean isGooglePlayServicesAvailable() {
        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getActivity());
        if (ConnectionResult.SUCCESS == status) {
            return true;
        } else {
            GooglePlayServicesUtil.getErrorDialog(status, getActivity(), 0).show();
            return false;
        }
    }*/
    public class AsyncGetLocation extends AsyncTask<String, Void, String> {
        ProgressDialog progressDoalog;

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            progressDoalog = new ProgressDialog(getActivity());
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
            } catch (SecurityException e) {
                e.getMessage();
            }
            while (mLastLocation == null && Calendar.getInstance().getTimeInMillis() - t < 15000) {
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
                    callApiGetBranch(latitude,longitude);
                    //    getAddress();

                } else {
                    //Couldn't find location, do something like show an alert dialog
                    callApiGetBranch(0.000000,0.000000);
                }

            } catch (SecurityException e) {
                e.printStackTrace();
            }

            if (progressDoalog.isShowing()) {
                progressDoalog.dismiss();
            }
        }


    }
}
