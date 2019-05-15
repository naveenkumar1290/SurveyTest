package com.cs.nks.easycouriers.dcdc.patient;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.cs.nks.easycouriers.R;
import com.cs.nks.easycouriers.util.UTIL;

public class ViewReportActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_report);
        setTitle(UTIL.getTitle("Report"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
