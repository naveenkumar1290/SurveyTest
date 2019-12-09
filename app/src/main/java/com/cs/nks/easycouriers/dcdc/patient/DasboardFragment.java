package com.cs.nks.easycouriers.dcdc.patient;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.cs.nks.easycouriers.R;
import com.cs.nks.easycouriers.activity.ActivityWithNavigationMenuPatient;
import com.cs.nks.easycouriers.util.UTIL;

import java.util.Timer;

/**
 * A simple {@link Fragment} subclass.
 */
public class DasboardFragment extends Fragment {

    Context myContext;
    UTIL utill;
    Timer timer;
    private int currentPage = 0;
    private ViewPager mViewPager;
LinearLayout ll_reports,ll_Appointment,ll_Schedule,ll_log;
    public DasboardFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.dasboard, container, false);
        getActivity().setTitle("Home");
        myContext = getActivity();
       /* ll_reports
        ll_Appointment
        ll_Schedule*/
        setView(rootView);
        return rootView;

    }
    private void setView(View rootView) {


        ll_Appointment=rootView.findViewById(R.id.ll_Appointment);
        ll_Schedule=rootView.findViewById(R.id.ll_Schedule);
        ll_log=rootView.findViewById(R.id.ll_cntct_us);
        ll_reports=rootView.findViewById(R.id.ll_reports);

        ll_Appointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ((ActivityWithNavigationMenuPatient) getActivity()).replaceFragmnt(new AppointmentFragment());
            }
        });
        ll_Schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ActivityWithNavigationMenuPatient) getActivity()).replaceFragmnt(new ScheduleFragment());
            }
        });
        ll_log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ActivityWithNavigationMenuPatient) getActivity()).replaceFragmnt(new ContactUsFragment());
            }
        });
        ll_reports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String patientId = UTIL.getPref(myContext, UTIL.Key_UserId);
                Fragment fragment = ActivityWithNavigationMenuPatient.getReportFragment(patientId, "1");
                ((ActivityWithNavigationMenuPatient) getActivity()).replaceFragmnt(fragment);
            }
        });
    }

}
