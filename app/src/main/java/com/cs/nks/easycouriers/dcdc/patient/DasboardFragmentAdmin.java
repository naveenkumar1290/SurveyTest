package com.cs.nks.easycouriers.dcdc.patient;


import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.cs.nks.easycouriers.R;
import com.cs.nks.easycouriers.util.AppController;
import com.cs.nks.easycouriers.util.ConnectionDetector;
import com.cs.nks.easycouriers.util.UTIL;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Timer;

/**
 * A simple {@link Fragment} subclass.
 */
public class DasboardFragmentAdmin extends Fragment {

    final long DELAY_MS = 0;//delay in milliseconds before task is to be executed
    final long PERIOD_MS = 3000; // time in milliseconds between successive task executions.
    final int NUM_PAGES = 4;
    Context myContext;
    UTIL utill;
    LinearLayout ll_reports, ll_Appointment, ll_Schedule;
    LinearLayout ll_share_app, ll_cntct_us, ll_about_us, ll_Change_Password;
    Timer timer;
    Handler handler;
    Runnable Update;
    private int currentPage = 0;
    private ViewPager mViewPager;



  TextView TotalDialysis,  TotalBillings,
    TotalActivePatient  ,BillingRevenue;

    public DasboardFragmentAdmin() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.activity_admin_dashboard, container, false);
        getActivity().setTitle("Dashboard");
        myContext = getActivity();

        utill = new UTIL(getActivity());
        setView(rootView);
        return rootView;

    }

    @Override
    public void onResume() {
        super.onResume();

        if (new ConnectionDetector(getActivity()).isConnectingToInternet()) {
            getDashboardApiCall();
        } else {
            Toast.makeText(getActivity(), "No internet", Toast.LENGTH_SHORT).show();
        }
    }

    private void setView(View rootView) {
        TotalDialysis = rootView.findViewById(R.id.TotalDialysis);
        TotalBillings = rootView.findViewById(R.id.TotalBillings);
        TotalActivePatient = rootView.findViewById(R.id.TotalActivePatient);
        BillingRevenue = rootView.findViewById(R.id.BillingRevenue);

/*
        ll_Appointment = rootView.findViewById(R.id.ll_Appointment);
        ll_Schedule = rootView.findViewById(R.id.ll_Schedule);

        ll_reports = rootView.findViewById(R.id.ll_reports);

        ll_Appointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ((ActivityWithNavigationMenu) getActivity()).replaceFragmnt(new AppointmentFragment());
            }
        });
        ll_Schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ActivityWithNavigationMenu) getActivity()).replaceFragmnt(new ScheduleFragment());
            }
        });

        ll_reports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ActivityWithNavigationMenu) getActivity()).replaceFragmnt(new Reportfragment());
            }
        });
        ll_share_app.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT,
                        "Hey check DCDC app at: https://play.google.com/store/apps/details?id=" + getActivity().getApplicationContext().getPackageName());
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
            }
        });
        ll_cntct_us.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ActivityWithNavigationMenu) getActivity()).replaceFragmnt(new ContactUsFragment());

            }
        });
        ll_about_us.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //((ActivityWithNavigationMenu) getActivity()).replaceFragmnt(new AboutUsFragment());
                ((ActivityWithNavigationMenu) getActivity()).replaceFragmnt(new ServicesFragmentNew());


            }
        });
        ll_Change_Password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), ChangePassword.class));
            }
        });*/
    }

    void getDashboardApiCall() {
        String tag_string_req = "req_login";
        utill.showProgressDialog("");

        String URL_LOGIN = null;

        try {
            URL_LOGIN = UTIL.Domain_DCDC + UTIL.Admin_dash_API;
            URL_LOGIN = URL_LOGIN.replaceAll(" ", "%20");
        } catch (Exception e) {
            e.printStackTrace();
        }


        StringRequest strReq = new StringRequest(Request.Method.GET,
                URL_LOGIN, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                utill.hideProgressDialog();
                //  {"total_active_patient":2,"Total_dialysis":0,"Total_billing":0,"Billing_revenue":null}

                try {
                    JSONObject jObj = new JSONObject(response);
                    String total_active_patient = jObj.getString("total_active_patient");
                    String Total_dialysis = jObj.getString("Total_dialysis");
                    String Total_billing = jObj.getString("Total_billing");
                    String Billing_revenue = jObj.getString("Billing_revenue");
                    String Total_Active_patient_till_current_year = jObj.getString("Total_Active_patient_till_current_year");



//                    if (total_active_patient != null) {
//                        TotalActivePatient.setText(Total_Active_patient_till_current_year);
//                    }
                    if (Total_dialysis != null && !Total_dialysis.equals("null")) {
                        TotalDialysis.setText(Total_dialysis);
                    }
                    if (Total_billing != null && !Total_billing.equals("null")) {
                        TotalBillings.setText(Total_billing);
                    }
                    if (Billing_revenue != null && !Billing_revenue.equals("null")) {
                        BillingRevenue.setText(Billing_revenue);
                    }
                    if (Total_Active_patient_till_current_year != null && !Total_Active_patient_till_current_year.equals("null")) {
                        TotalActivePatient.setText(Total_Active_patient_till_current_year);
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    Toast.makeText(myContext, "Json error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(myContext,
                        "Volley Error!", Toast.LENGTH_SHORT).show();
                utill.hideProgressDialog();

            }
        }) {


        };

        strReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);

    }

}
