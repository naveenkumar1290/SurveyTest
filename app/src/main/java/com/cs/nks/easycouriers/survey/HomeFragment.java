package com.cs.nks.easycouriers.survey;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
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
import com.cs.nks.easycouriers.activity.FullscreenWebView;
import com.cs.nks.easycouriers.model.ClientUserAll;
import com.cs.nks.easycouriers.model.Dashboard;
import com.cs.nks.easycouriers.util.AppController;
import com.cs.nks.easycouriers.util.ConnectionDetector;
import com.cs.nks.easycouriers.util.UTIL;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;

import static com.android.volley.VolleyLog.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    Context myContext;
    UTIL utill;
    Timer timer;
    TextView tv_msg;
    ArrayList<ClientUserAll> list_ClientUser = new ArrayList<>();
    UTIL util;
    AlertDialog alertDialog;
    String patientId = "", is_PATIENT_LOGIN = "";
    ArrayList<Dashboard> dashboardArrayList = new ArrayList<>();
    private int currentPage = 0;
    private ViewPager mViewPager;
    private RecyclerView recyclerView;
    private MoviesAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.fragment_about_us, container, false);
        getActivity().setTitle("Dashboard");
        myContext = getActivity();
        util = new UTIL(getActivity());
        setView(rootView);
        try {
            Bundle bundle = this.getArguments();
            if (bundle != null) {
                patientId = bundle.getString(UTIL.PATIENT_ID, "");
                is_PATIENT_LOGIN = bundle.getString(UTIL.is_PATIENT_LOGIN, "0");
            }

        } catch (Exception e) {
            e.getMessage();
        }

        return rootView;

    }

    private void setView(View rootView) {
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);

        tv_msg = rootView.findViewById(R.id.tv_msg);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());


        dashboardArrayList.add(new Dashboard("kilns 1", "11 Dec 2018", "12 Dec 2018", "12 Dec 2018", false));
        dashboardArrayList.add(new Dashboard("kilns 2", "12 Dec 2018", "13 Dec 2018", "14 Dec 2018", true));
        dashboardArrayList.add(new Dashboard("kilns 3", "13 Dec 2018", "14 Dec 2018", "15 Dec 2018", false));
        dashboardArrayList.add(new Dashboard("kilns 4", "14 Dec 2018", "15 Dec 2018", "18 Dec 2018", true));
        dashboardArrayList.add(new Dashboard("kilns 5", "15 Dec 2018", "16 Dec 2018", "20 Dec 2018", true));
        dashboardArrayList.add(new Dashboard("kilns 6", "16 Dec 2018", "17 Dec 2018", "22 Dec 2018", true));
        dashboardArrayList.add(new Dashboard("kilns 7", "17 Dec 2018", "18 Dec 2018", "25 Dec 2018", false));
        dashboardArrayList.add(new Dashboard("kilns 8", "18 Dec 2018", "19 Dec 2018", "27 Dec 2018", true));
        dashboardArrayList.add(new Dashboard("kilns 1", "21 Dec 2018", "21 Dec 2018", "28 Dec 2018", true));
        mAdapter = new MoviesAdapter(getActivity(), dashboardArrayList);
        recyclerView.setAdapter(mAdapter);


        //  getSchedule();

    }

    @Override
    public void onResume() {
        super.onResume();
        if (new ConnectionDetector(getActivity()).isConnectingToInternet()) {
            //getSchedule();
        } else {
            Toast.makeText(getActivity(), "No internet", Toast.LENGTH_SHORT).show();
        }

    }

    public void getSchedule() {
        util.showProgressDialog(UTIL.Progress_msg);
        // String patientId = UTIL.getPref(myContext, UTIL.Key_UserId);
        String url = UTIL.Domain_DCDC + UTIL.ScheduleList_API + "p_id=" + patientId;
        String tag_json_obj = "json_obj_req";
        list_ClientUser.clear();

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString());
                        util.hideProgressDialog();

                        try {
                            JSONArray jsonArray = response.getJSONArray("schedule_details");

                            if (jsonArray.length() > 0) {
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    String appointment_id = jsonObject.getString("appointment_id");
                                    String p_id = jsonObject.getString("p_id");
                                    String branch_id = jsonObject.getString("branch_id");
                                    String appointment_date = jsonObject.getString("appointment_date");
                                    String from_time = jsonObject.getString("from_time");
                                    String to_time = jsonObject.getString("to_time");
                                    String remarks = jsonObject.getString("remarks");
                                    //  String status = jsonObject.getString("status");
                                    String day = jsonObject.getString("day");
                                    String city = jsonObject.getString("city_name");
                                    //  String address = "";

                                    String address = jsonObject.getString("address");
                                    String status_id = jsonObject.getString("status_id");
                                    String branch_name = jsonObject.getString("branch_name");

                                    String patient_reports = jsonObject.getString("patient_reports");


                                    if (is_PATIENT_LOGIN.equals("1")) {    // for Patient LOGIN
                                        if (status_id.equals("4")   // Appointment date EXPIRED
                                                || status_id.equals("5")) // Dialysis COMPLETED
                                        {
                                            list_ClientUser.add(new ClientUserAll(appointment_id,
                                                    parseDate(appointment_date),
                                                    parseTime(from_time), day,
                                                    branch_name, address, status_id,
                                                    remarks, parseTime(to_time), patient_reports, branch_id));
                                        }

                                    } else {    // for DOCTOR Login
                                        if (status_id.equals("5")) // Dialysis COMPLETED
                                        {
                                            list_ClientUser.add(new ClientUserAll(appointment_id,
                                                    parseDate(appointment_date),
                                                    parseTime(from_time), day,
                                                    branch_name, address, status_id,
                                                    remarks, parseTime(to_time), patient_reports, branch_id));
                                        }
                                    }


                                }
                            }


                        } catch (Exception e) {
                            e.getCause();
                        }
                       // mAdapter = new MoviesAdapter(getActivity(), list_ClientUser);
                        recyclerView.setAdapter(mAdapter);
                        if (list_ClientUser.size() < 1) {
                            tv_msg.setVisibility(View.VISIBLE);
                        } else {
                            tv_msg.setVisibility(View.GONE);
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

    public String parseDate(String time) {
        String inputPattern = "yyyy-MM-dd";
        String outputPattern = "dd MMM yyyy";
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

    public String parseTime(String time) {
        String str = "";
        String ar[] = time.split(":");
        str = ar[0] + ":" + ar[1];
        return str;
    }

    private void dialog_Report_type(final String appointmentID) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.report_type_dialog, null);
        dialogBuilder.setView(dialogView);


        final TextView title = dialogView.findViewById(R.id.title);
        final TextView message = dialogView.findViewById(R.id.message);
        final Button lab_report = dialogView.findViewById(R.id.lab_report);
        final Button OPD_report = dialogView.findViewById(R.id.OPD_report);
        final Button Dialysis_report = dialogView.findViewById(R.id.Dialysis_report);

        // dialogBuilder.setTitle("Device Details");
        // title.setText("Do you want to logout?");
        //   message.setText("Are you sure?");

        lab_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
/*
                String url=UTIL.Domain_DCDC+"dcdc_web_service/reports/"+ projectPhoto.getReport();
                Intent intent = new Intent(getActivity(), FullscreenWebView.class);
                intent.putExtra("url", url);
                startActivity(intent);*/
                getLABReport(appointmentID);
            }
        });
        OPD_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                getOPDReport(appointmentID);
            }
        });
        Dialysis_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                getDialysisReport(appointmentID);
            }
        });

        alertDialog = dialogBuilder.create();
        alertDialog.show();

    }

    public void getOPDReport(final String appointmentID) {
        String URL = UTIL.Domain_DCDC + UTIL.OPD_Report_API;
        util.showProgressDialog(UTIL.Progress_msg);
        StringRequest postRequest = new StringRequest(Request.Method.POST, URL,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        util.hideProgressDialog();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Log.d("Response", response);
                            //   Toast.makeText(getActivity(),response,Toast.LENGTH_SHORT).show();
                            String status = jsonObject.getString("status");
                            String msg = jsonObject.getString("msg");
                            if (status.equals("1")) {
                                String url = UTIL.Domain_DCDC + "dcdc_web_service/" + msg;
                                Intent intent = new Intent(getActivity(), FullscreenWebView.class);
                                intent.putExtra("url", url);
                                startActivity(intent);
                            } else {
                                Toast.makeText(getActivity(), "Report not found!", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            e.getMessage();
                        }
                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        util.hideProgressDialog();
                        Log.d("Error.Response", String.valueOf(error));
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {

                // String patientId = UTIL.getPref(getActivity(), UTIL.Key_UserId);
                Map<String, String> params = new HashMap<String, String>();
                params.put("p_id", patientId);
                params.put("a_id", appointmentID);


                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        queue.add(postRequest);
    }

    public void getLABReport(final String appointmentID) {
        String URL = UTIL.Domain_DCDC + UTIL.LAB_Report_API;
        util.showProgressDialog(UTIL.Progress_msg);
        StringRequest postRequest = new StringRequest(Request.Method.POST, URL,
                new com.android.volley.Response.Listener<String>() {
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
                                String url = UTIL.Domain_DCDC + "dcdc_web_service/" + msg;
                                Intent intent = new Intent(getActivity(), FullscreenWebView.class);
                                intent.putExtra("url", url);
                                startActivity(intent);
                            } else {
                                Toast.makeText(getActivity(), "Report not found!", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            e.getMessage();
                        }
                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        util.hideProgressDialog();
                        Log.d("Error.Response", String.valueOf(error));
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {

                //  String patientId = UTIL.getPref(getActivity(), UTIL.Key_UserId);
                Map<String, String> params = new HashMap<String, String>();
                params.put("p_id", patientId);
                params.put("a_id", appointmentID);
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        queue.add(postRequest);
    }

    public void getDialysisReport(final String appointmentID) {
        String URL = UTIL.Domain_DCDC + UTIL.DIALYSIS_Report_API;
        util.showProgressDialog(UTIL.Progress_msg);
        StringRequest postRequest = new StringRequest(Request.Method.POST, URL,
                new com.android.volley.Response.Listener<String>() {
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
                                String url = UTIL.Domain_DCDC + "dcdc_web_service/" + msg;
                                Intent intent = new Intent(getActivity(), FullscreenWebView.class);
                                intent.putExtra("url", url);
                                startActivity(intent);
                            } else {
                                Toast.makeText(getActivity(), "Report not found!", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            e.getMessage();
                        }
                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        util.hideProgressDialog();
                        Log.d("Error.Response", String.valueOf(error));
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {

                //    String patientId = UTIL.getPref(getActivity(), UTIL.Key_UserId);
                Map<String, String> params = new HashMap<String, String>();
                params.put("p_id", patientId);
                params.put("a_id", appointmentID);


                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        queue.add(postRequest);
    }

    public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MyViewHolder> {
        private List<Dashboard> moviesList;
        //   private HttpImageManager mHttpImageManager;

        public MoviesAdapter(Activity context, List<Dashboard> moviesList) {
            this.moviesList = moviesList;
            //     mHttpImageManager = ((AppController) context.getApplication()).getHttpImageManager();
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
          /*  View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.row_client_all_user, parent, false);*/
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.row_dashboard, parent, false);

            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, int position) {
            final Dashboard projectPhoto = moviesList.get(position);
            holder.index_no.setText(String.valueOf(position + 1));


            holder.AssignedKilns.setText(projectPhoto.getAssignedKilns()); //date
            holder.AssignedDate.setText(projectPhoto.getAssignedDate());//time
            holder.VisitDate.setText(projectPhoto.getVisitDate());//time
            holder.synced_date.setText(projectPhoto.getSyncedDate());//time
            if (projectPhoto.isSyncStatus()) {
                holder. ll_sync_now.setVisibility(View.GONE);
                holder. ll_synced.setVisibility(View.VISIBLE);
            }else {
                holder. ll_sync_now.setVisibility(View.VISIBLE);
                holder. ll_synced.setVisibility(View.GONE);
            }


            holder.ll_sync_now.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getActivity(),"Sync started!",Toast.LENGTH_SHORT).show();
                }
            });


        }

        @Override
        public int getItemCount() {
            return moviesList.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {


            TextView index_no;
            LinearLayout column;
            ImageView sync;
            TextView AssignedKilns, AssignedDate, VisitDate, synced_date;
            LinearLayout ll_sync_now, ll_synced;

            public MyViewHolder(View convertview) {
                super(convertview);

                sync = convertview.findViewById(R.id.sync);
                index_no = convertview.findViewById(R.id.serial_no);
                AssignedKilns = (TextView) convertview.findViewById(R.id.AssignedKilns);
                AssignedDate = (TextView) convertview.findViewById(R.id.AssignedDate);
                VisitDate = (TextView) convertview.findViewById(R.id.VisitDate);
                synced_date = convertview.findViewById(R.id.synced_date);
                ll_sync_now = convertview.findViewById(R.id.ll_sync_now);
                ll_synced = convertview.findViewById(R.id.ll_synced);
                column = convertview.findViewById(R.id.column);


            }
        }
    }
}
