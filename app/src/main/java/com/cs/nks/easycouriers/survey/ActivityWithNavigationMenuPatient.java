package com.cs.nks.easycouriers.survey;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cs.nks.easycouriers.R;
import com.cs.nks.easycouriers.dcdc.patient.AppointmentFragment;
import com.cs.nks.easycouriers.dcdc.patient.DasboardFragmentNew;
import com.cs.nks.easycouriers.util.AppController;
import com.cs.nks.easycouriers.util.ConnectionDetector;
import com.cs.nks.easycouriers.util.UTIL;

import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.HashMap;
import java.util.Map;


public class ActivityWithNavigationMenuPatient extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Context myContext;
    // UTIL utill;
    AlertDialog alertDialog;
    NavigationView navigationView;
    // ArrayList<Item> Item_list = new ArrayList<>();
    boolean doubleBackToExitPressedOnce = false;
    int cnt = 0;

    public static Fragment getReportFragment(String PatientID, String is_PATIENT_LOGIN) {
        HomeFragment reportfragment = new HomeFragment();
        Bundle bundle = new Bundle();
        bundle.putString(UTIL.PATIENT_ID, PatientID);
        bundle.putString(UTIL.is_PATIENT_LOGIN, is_PATIENT_LOGIN);
        reportfragment.setArguments(bundle);
        return reportfragment;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_menu);
        myContext = ActivityWithNavigationMenuPatient.this;
        // utill = new UTIL(myContext);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        manupulateDrawerItems();

        if (new ConnectionDetector(ActivityWithNavigationMenuPatient.this).isConnectingToInternet()) {
      //      new checkVersionUpdate().execute();
        }
        addScheduleListFragment();
       // HandleIntent();
      //  UpdateToken();


    }

    private void HandleIntent() {



      /*  Bundle b = getIntent().getExtras();
        if (b != null) {
            String isFromPush = b.getString("push", "");
            String isFromMap = b.getString("map", "");
            if (isFromPush.equalsIgnoreCase("1")) {
                addScheduleListFragment();
            } else if (isFromMap.equalsIgnoreCase("1")) {
                add_BookAppointment_Fragment(b);
            } else {
                // addHomeFragment();
                // ATTENTION: This was auto-generated to handle app links.
                Intent appLinkIntent = getIntent();
                String appLinkAction = appLinkIntent.getAction();
                Uri appLinkData = appLinkIntent.getData();

            }
        } else {
            addHomeFragment();
        }
*/
    }

    private void UpdateToken() {
        String type = UTIL.getPref(ActivityWithNavigationMenuPatient.this, UTIL.Key_Type);
        if (type.equals("2")) { //patient
            String FCMToken = UTIL.getPref(ActivityWithNavigationMenuPatient.this, UTIL.Key_FCMTOken);
            if (!FCMToken.equals("")) {
                UpdateFCMTokenAtServer();
            }
        } else if (type.equals("1")) {  // admin
            // don't update token on server
        }

    }

    public void addHomeFragment() {
        // DasboardFragment fragment = new DasboardFragment();
        DasboardFragmentNew fragment = new DasboardFragmentNew();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.frgmnt_placehodler, fragment, "1");
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }

    public void add_BookAppointment_Fragment(Bundle bundle) {
        // DasboardFragment fragment = new DasboardFragment();
        AppointmentFragment fragment = new AppointmentFragment();
        fragment.setArguments(bundle);
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.frgmnt_placehodler, fragment, "1");
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }

    public void addScheduleListFragment() {
        replaceFragmnt(new AssignedKilnsFragment());

    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        /*
        Menu menu = navigationView.getMenu();
        menu.add(R.id.group_1, 123, Menu.NONE, "Title1");
        menu.add(R.id.group_1, 124, Menu.NONE, "Title2");
       */
        closeDrawer();
        int id = item.getItemId();
        // to not highlight when selected
        //  navigationView.getMenu().getItem(item).setChecked(false);

/***********************************************/

        if (id == R.id.nav_Dashboard) {
            replaceFragmnt(new HomeFragment());
        }
        else if (id == R.id.nav_AssignedKilns) {
            replaceFragmnt(new AssignedKilnsFragment());
        } else if (id == R.id.nav_ReportBondedLabor) {
            replaceFragmnt(new ReportBondedLabor());
        } else if (id == R.id.nav_ReportEnvironment) {
            replaceFragmnt(new ReportEnvironment());
        } else if (id == R.id.nav_FAQ) {
            replaceFragmnt(new FAQFragment());
        } else if (id == R.id.nav_ChangePass) {
            startActivity(
                    new Intent(ActivityWithNavigationMenuPatient.this, ChangePassword.class));

        } else if (id == R.id.nav_Map) {
            startActivity(
                    new Intent(ActivityWithNavigationMenuPatient.this, MapsActivity.class));
        } else if (id == R.id.nav_logout) {
            dialog_LOGOUT();
        }
        /******************************************/

        return false;
    }

    public void closeDrawer() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }


    public void replaceFragmnt(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        // fragmentTransaction.setCustomAnimations( R.anim.left_in, R.anim.left_out);
        fragmentTransaction.replace(R.id.frgmnt_placehodler, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        ActivityWithNavigationMenuPatient.this.overridePendingTransition(R.anim.left_in, R.anim.left_out);
        //HomeActivity.this.overridePendingTransition( R.anim.slide_in_up, R.anim.slide_out_up );
    }


    public void manupulateDrawerItems() {
        /*set drawer menu programmatically*/
        ImageView imageView;
        TextView textviewUsr;
        String Uid = UTIL.getPref(ActivityWithNavigationMenuPatient.this, UTIL.Key_UserId);
        if (true) {
            //if (Uid != null && (!Uid.equals(""))) {
            navigationView.inflateMenu(R.menu.menu_drawer);
            //  headerLayout.setVisibility(View.VISIBLE);

            View nav_header = LayoutInflater.from(this).inflate(R.layout.nav_header_main2, null);
            navigationView.addHeaderView(nav_header);
            View headerLayout = navigationView.getHeaderView(0);
            imageView = (ImageView) headerLayout.findViewById(R.id.imageView);
            textviewUsr = (TextView) headerLayout.findViewById(R.id.textUserName);
            imageView.setVisibility(View.VISIBLE);
//            String user = UTIL.getPref(ActivityWithNavigationMenuPatient.this,
//                    UTIL.Key_USERNAME);
            String user = "Guest";

            textviewUsr.setText(user);
            // imageView.setBackgroundResource(R.drawable.mannnnn);
//            if (UTIL.getPref(ActivityWithNavigationMenuPatient.this, UTIL.Key_GENDER).equalsIgnoreCase("M") || UTIL.getPref(ActivityWithNavigationMenuPatient.this, UTIL.Key_GENDER).equalsIgnoreCase("MALE")) {
//                imageView.setBackgroundResource(R.drawable.mannnnn);
//            } else {
//                imageView.setBackgroundResource(R.drawable.woman);
//            }
            headerLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    closeDrawer();
                    //   replaceFragmnt(new ProfileFragment());
                }
            });

            TextView navFooter1 = findViewById(R.id.txt_version);
            try {
                PackageManager manager = ActivityWithNavigationMenuPatient.this.getPackageManager();
                PackageInfo info = manager.getPackageInfo(ActivityWithNavigationMenuPatient.this.getPackageName(), 0);
                String versionName = info.versionName;
                navFooter1.setText("v" + versionName);
            } catch (Exception e) {
                e.getMessage();
            }


        } else {

            //    navigationView.inflateMenu(R.menu.menu_drawer_without_login);

        }


    }

    private void dialog_LOGOUT() {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.fancyalertdialog, null);
        dialogBuilder.setView(dialogView);


        final TextView title = dialogView.findViewById(R.id.title);
        final TextView message = dialogView.findViewById(R.id.message);
        final Button positiveBtn = dialogView.findViewById(R.id.positiveBtn);
        final Button negativeBtn = dialogView.findViewById(R.id.negativeBtn);


        // dialogBuilder.setTitle("Device Details");
        title.setText("Do you want to logout?");
        message.setText("Are you sure?");
        positiveBtn.setText("Yes");
        negativeBtn.setText("No");
        positiveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                UTIL.clearPref(ActivityWithNavigationMenuPatient.this);
                Intent abc = new Intent(ActivityWithNavigationMenuPatient.this, Tab_Login_Register_Activity.class);
                startActivity(abc);
                Toast.makeText(ActivityWithNavigationMenuPatient.this, "Logout successfully", Toast.LENGTH_SHORT).show();
                finish();

            }
        });
        negativeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });

        alertDialog = dialogBuilder.create();
        alertDialog.show();

    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            /*try {
                DasboardFragmentNew myFragment = (DasboardFragmentNew) getSupportFragmentManager().findFragmentByTag("1");
                if (myFragment != null && myFragment.isVisible()) {
                    dialog_Exit();
                } else {
                    addHomeFragment();
                }
            } catch (Exception e) {
                e.getMessage();
                addHomeFragment();
            }
*/

            finish();

        }
    }

    private void dialog_Exit() {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.fancyalertdialog, null);
        dialogBuilder.setView(dialogView);


        final TextView title = dialogView.findViewById(R.id.title);
        final TextView message = dialogView.findViewById(R.id.message);
        final Button positiveBtn = dialogView.findViewById(R.id.positiveBtn);
        final Button negativeBtn = dialogView.findViewById(R.id.negativeBtn);


        // dialogBuilder.setTitle("Device Details");
        title.setText("Do you want to exit?");
        message.setText("Are you sure?");
        positiveBtn.setText("Yes");
        negativeBtn.setText("No");
        positiveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                ActivityWithNavigationMenuPatient.this.finishAffinity();


            }
        });
        negativeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });

        alertDialog = dialogBuilder.create();
        alertDialog.setCancelable(false);
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }


    public void UpdateFCMTokenAtServer(
    ) {
        String URL = UTIL.Domain_DCDC + UTIL.FCMTokenUpdate_API;
        StringRequest postRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        try {
                            Log.d("Response", response);
                        } catch (Exception e) {
                            e.getMessage();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", String.valueOf(error));
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                String UserId = UTIL.getPref(ActivityWithNavigationMenuPatient.this, UTIL.Key_UserId);
                String FCMToken = UTIL.getPref(ActivityWithNavigationMenuPatient.this, UTIL.Key_FCMTOken);
                Map<String, String> params = new HashMap<String, String>();
                params.put("userid", UserId);
                params.put("fcm_token_id", FCMToken);

                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(ActivityWithNavigationMenuPatient.this);
        queue.add(postRequest);
    }


    private void UpdateFCMTokenAtServer_New() {


        String tag_string_req = "req_login";
        String URL = null;
        String UserId = UTIL.getPref(ActivityWithNavigationMenuPatient.this, UTIL.Key_UserId);
        String FCMToken = UTIL.getPref(ActivityWithNavigationMenuPatient.this, UTIL.Key_FCMTOken);

        Map<String, String> params = new HashMap<>();
        params.put("userid", UserId);
        params.put("fcm_token_id", FCMToken);
        URL = UTIL.Domain_DCDC + UTIL.FCMTokenUpdate_API;

        JsonObjectRequest request_json = new JsonObjectRequest(Request.Method.POST, URL, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //  utill.hideProgressDialog();
                        android.util.Log.e("FCM Token update res", String.valueOf(response));

                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("FCM Token update Error", String.valueOf(error));

            }
        }) {
        };

        request_json.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().addToRequestQueue(request_json, tag_string_req);


    }

    private void dialog_Update() {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.fancyalertdialog, null);
        dialogBuilder.setView(dialogView);


        final TextView title = dialogView.findViewById(R.id.title);
        final TextView message = dialogView.findViewById(R.id.message);
        final Button positiveBtn = dialogView.findViewById(R.id.positiveBtn);
        final Button negativeBtn = dialogView.findViewById(R.id.negativeBtn);


        // dialogBuilder.setTitle("Device Details");
        title.setText("New Update Available.");
        message.setText("Please update to the latest version!");
        positiveBtn.setText("OK");
        negativeBtn.setText("No");
        negativeBtn.setVisibility(View.GONE);
        positiveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                } catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                }

                finish();


            }
        });
        negativeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });

        alertDialog = dialogBuilder.create();
        alertDialog.setCancelable(false);
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();

    }

    private class checkVersionUpdate extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            progressDialog = new ProgressDialog(ActivityWithNavigationMenuPatient.this);
            progressDialog.setMessage(getString(R.string.Loading_text));
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);

            try {
                progressDialog.show();
            } catch (Exception e) {
                e.getMessage();
            }

        }

        @Override
        protected String doInBackground(String... params) {
            String val = "0.0";
            final String NAMESPACE = "https://tempuri.org/";
            final String URL = "https://www.exhibitpower2.com/WebService/techlogin_service.asmx";
            final String METHOD_NAME = "GetVersion";
            final String SOAP_ACTION = NAMESPACE + METHOD_NAME;
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

            try {

                final String appPackageName = getPackageName();
                String address = "https://play.google.com/store/apps/details?id=" + appPackageName;
                request.addProperty("address", address);

                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11); // put all required data into a soap
                envelope.dotNet = true;
                envelope.setOutputSoapObject(request);
                HttpTransportSE httpTransport = new HttpTransportSE(URL);
                httpTransport.call(SOAP_ACTION, envelope);
                Object results = (Object) envelope.getResponse();
                val = results.toString();


            } catch (Exception e) {
                e.getMessage();
            }
            return val;
        }

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            // String ver = result;
            super.onPostExecute(result);
            try {
                if (progressDialog != null && progressDialog.isShowing()) {
                    try {
                        progressDialog.dismiss();
                    } catch (Exception e) {
                        e.getMessage();
                    }
                }
            } catch (Exception e) {
                e.getMessage();
            }

            try {

                PackageManager manager = ActivityWithNavigationMenuPatient.this.getPackageManager();
                PackageInfo info = manager.getPackageInfo(ActivityWithNavigationMenuPatient.this.getPackageName(), 0);
                String VersionName = info.versionName;
                //String nversionName = result;
                if (result == null) {
                    result = "0";
                }
                Double PlayStoreVersion = Double.parseDouble(result);
                Double AppVersionName = Double.parseDouble(VersionName);

                if (AppVersionName < PlayStoreVersion) {
                    //  if (true) {
                    dialog_Update();
                }

            } catch (Exception err) {
                err.getMessage();
            }


        }


    }
}
