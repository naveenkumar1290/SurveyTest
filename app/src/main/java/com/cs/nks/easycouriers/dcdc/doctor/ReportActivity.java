package com.cs.nks.easycouriers.dcdc.doctor;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.cs.nks.easycouriers.R;
import com.cs.nks.easycouriers.util.UTIL;

import static com.cs.nks.easycouriers.activity.ActivityWithNavigationMenuPatient.getReportFragment;

public class ReportActivity extends AppCompatActivity {

    String patientID = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        setTitle("Reports");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            if (bundle.containsKey((UTIL.PATIENT_ID))) {
                patientID = bundle.getString((UTIL.PATIENT_ID));
            }
        }
        addReportFragment();
    }

    public void addReportFragment() {

        Fragment reportfragment = getReportFragment(patientID, "0");

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.frgmnt_placehodler, reportfragment, "1");
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }

    @Override
    public boolean onSupportNavigateUp() {
       // onBackPressed();
        finish();
        return false;
    }

    @Override
    public void onBackPressed() {
       finish();
    }
}
