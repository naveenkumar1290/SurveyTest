package com.cs.nks.easycouriers.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
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
import com.cs.nks.easycouriers.dcdc.patient.MapsActivity;
import com.cs.nks.easycouriers.dcdc.patient.Reportfragment;
import com.cs.nks.easycouriers.dcdc.patient.ScheduleFragment;
import com.cs.nks.easycouriers.util.AppController;
import com.cs.nks.easycouriers.util.UTIL;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class ActivityWithNavigationMenu extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Context myContext;
    // UTIL utill;
    AlertDialog alertDialog;
    NavigationView navigationView;
    // ArrayList<Item> Item_list = new ArrayList<>();
    boolean doubleBackToExitPressedOnce = false;
    int cnt = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_menu);
        myContext = ActivityWithNavigationMenu.this;
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

        HandleIntent();

        UpdateToken();



    }


    private void HandleIntent(){
        Bundle b = getIntent().getExtras();
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

    }

    private void UpdateToken(){
        String type = UTIL.getPref(ActivityWithNavigationMenu.this, UTIL.Key_Type);
        if (type.equals("2")) { //patient
            String FCMToken = UTIL.getPref(ActivityWithNavigationMenu.this, UTIL.Key_FCMTOken);
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
        replaceFragmnt(new ScheduleFragment());

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
        if (id == R.id.nav_Home) {
            //   replaceFragmnt(new DasboardFragment());
            replaceFragmnt(new DasboardFragmentNew());
        } else if (id == R.id.nav_book) {
            replaceFragmnt(new AppointmentFragment());
        } else if (id == R.id.nav_AboutUs) {
            replaceFragmnt(new ScheduleFragment());
        } else if (id == R.id.nav_HowItWorks) {
            // replaceFragmnt(new ReportsFragment());
            replaceFragmnt(new Reportfragment());
        } else if (id == R.id.nav_ChangePass) {
            startActivity(new Intent(ActivityWithNavigationMenu.this, ChangePassword.class));

        } else if (id == R.id.nav_Center) {
            startActivity(new Intent(ActivityWithNavigationMenu.this, MapsActivity.class));
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
        ActivityWithNavigationMenu.this.overridePendingTransition(R.anim.left_in, R.anim.left_out);
        //HomeActivity.this.overridePendingTransition( R.anim.slide_in_up, R.anim.slide_out_up );
    }


    public void manupulateDrawerItems() {
        /*set drawer menu programmatically*/
        ImageView imageView;
        TextView textviewUsr;
        String Uid = UTIL.getPref(ActivityWithNavigationMenu.this, UTIL.Key_UserId);
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
//            String user = UTIL.getPref(ActivityWithNavigationMenu.this,
//                    UTIL.Key_USERNAME);
            String user = "Guest";

            textviewUsr.setText(user);
            // imageView.setBackgroundResource(R.drawable.mannnnn);
//            if (UTIL.getPref(ActivityWithNavigationMenu.this, UTIL.Key_GENDER).equalsIgnoreCase("M") || UTIL.getPref(ActivityWithNavigationMenu.this, UTIL.Key_GENDER).equalsIgnoreCase("MALE")) {
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
                PackageManager manager = ActivityWithNavigationMenu.this.getPackageManager();
                PackageInfo info = manager.getPackageInfo(ActivityWithNavigationMenu.this.getPackageName(), 0);
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
                UTIL.clearPref(ActivityWithNavigationMenu.this);
                Intent abc = new Intent(ActivityWithNavigationMenu.this, Tab_Login_Register_Activity.class);
                startActivity(abc);
                Toast.makeText(ActivityWithNavigationMenu.this, "Logout successfully", Toast.LENGTH_SHORT).show();
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
            try {
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
                ActivityWithNavigationMenu.this.finishAffinity();


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
                String UserId = UTIL.getPref(ActivityWithNavigationMenu.this, UTIL.Key_UserId);
                String FCMToken = UTIL.getPref(ActivityWithNavigationMenu.this, UTIL.Key_FCMTOken);
                Map<String, String> params = new HashMap<String, String>();
                params.put("userid", UserId);
                params.put("fcm_token_id", FCMToken);

                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(ActivityWithNavigationMenu.this);
        queue.add(postRequest);
    }


    private void UpdateFCMTokenAtServer_New() {


        String tag_string_req = "req_login";
        String URL = null;
        String UserId = UTIL.getPref(ActivityWithNavigationMenu.this, UTIL.Key_UserId);
        String FCMToken = UTIL.getPref(ActivityWithNavigationMenu.this, UTIL.Key_FCMTOken);

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


}
