package com.cs.nks.easycouriers.activity;

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
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cs.nks.easycouriers.R;
import com.cs.nks.easycouriers.dcdc.patient.DasboardFragmentAdmin;
import com.cs.nks.easycouriers.survey.Tab_Login_Register_Activity;
import com.cs.nks.easycouriers.util.ConnectionDetector;
import com.cs.nks.easycouriers.util.UTIL;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;


public class ActivityWithNavigationMenuAdmin extends AppCompatActivity
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
        myContext = ActivityWithNavigationMenuAdmin.this;
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

        if (new ConnectionDetector(ActivityWithNavigationMenuAdmin.this).isConnectingToInternet()) {
            new checkVersionUpdate().execute();
        }

        addHomeFragment();


    }


    public void addHomeFragment() {
        // DasboardFragment fragment = new DasboardFragment();
        DasboardFragmentAdmin fragment = new DasboardFragmentAdmin();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.frgmnt_placehodler, fragment,"1");
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

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
        if (id == R.id.nav_AssignedKilns) {
            replaceFragmnt(new DasboardFragmentAdmin());
     /*   } else if (id == R.id.nav_book) {
            replaceFragmnt(new AppointmentFragment());
        } else if (id == R.id.nav_AboutUs) {
            replaceFragmnt(new ScheduleFragment());
        } else if (id == R.id.nav_HowItWorks) {
            replaceFragmnt(new Reportfragment());*/
        }/* else if (id == R.id.nav_ChangePass) {
            startActivity(new Intent(ActivityWithNavigationMenuAdmin.this, ChangePassword.class));
        }*/

        else if (id == R.id.nav_logout) {
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
        //
        ActivityWithNavigationMenuAdmin.this.overridePendingTransition(R.anim.left_in, R.anim.left_out);
        //HomeActivity.this.overridePendingTransition( R.anim.slide_in_up, R.anim.slide_out_up );

    }


    public void manupulateDrawerItems() {
        /*set drawer menu programmatically*/
        ImageView imageView;
        TextView textviewUsr;
        String Uid = UTIL.getPref(ActivityWithNavigationMenuAdmin.this, UTIL.Key_UserId);
        if (true) {
            //if (Uid != null && (!Uid.equals(""))) {
            navigationView.inflateMenu(R.menu.menu_drawer_admin);
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
                PackageManager manager = ActivityWithNavigationMenuAdmin.this.getPackageManager();
                PackageInfo info = manager.getPackageInfo(ActivityWithNavigationMenuAdmin.this.getPackageName(), 0);
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
                UTIL.clearPref(ActivityWithNavigationMenuAdmin.this);
                Intent abc = new Intent(ActivityWithNavigationMenuAdmin.this, Tab_Login_Register_Activity.class);
                startActivity(abc);
                Toast.makeText(ActivityWithNavigationMenuAdmin.this, "Logout successfully", Toast.LENGTH_SHORT).show();
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

         /*   int i = getFragmentManager().getBackStackEntryCount();

            Toast.makeText(this, String.valueOf(i),
                    Toast.LENGTH_SHORT).show();
            Log.e("frgmnt",String.valueOf(i));*/


            DasboardFragmentAdmin myFragment =(DasboardFragmentAdmin) getSupportFragmentManager().findFragmentByTag("1");
            if (myFragment != null && myFragment.isVisible()) {
                // add your code here
                dialog_Exit();
            }else {
                addHomeFragment();
            }


  /*getSupportFragmentManager().popBackStack();
            int i = getFragmentManager().getBackStackEntryCount();
            Toast.makeText(this, String.valueOf(i),
                    Toast.LENGTH_SHORT).show();

            if(i==0){
                dialog_Exit();
            }*/


          /*  if (doubleBackToExitPressedOnce) {
                dialog_Exit();
                return;
            }

            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Please click BACK again to exit!",
                    Toast.LENGTH_SHORT).show();
       //     addHomeFragment();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
*/
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
                ActivityWithNavigationMenuAdmin.this.finishAffinity();


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
    private class checkVersionUpdate extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            progressDialog = new ProgressDialog(ActivityWithNavigationMenuAdmin.this);
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

                PackageManager manager = ActivityWithNavigationMenuAdmin.this.getPackageManager();
                PackageInfo info = manager.getPackageInfo(ActivityWithNavigationMenuAdmin.this.getPackageName(), 0);
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
}
