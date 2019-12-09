package com.cs.nks.easycouriers.dcdc.patient;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.cs.nks.easycouriers.R;
import com.cs.nks.easycouriers.activity.UpdateScheduleActivity;
import com.cs.nks.easycouriers.model.ClientUserAll;
import com.cs.nks.easycouriers.util.AppController;
import com.cs.nks.easycouriers.util.UTIL;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;

import static com.android.volley.VolleyLog.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class ScheduleFragment extends Fragment {

    Context myContext;
    UTIL utill;
    Timer timer;
    TextView tv_msg;
    ArrayList<ClientUserAll> list_ClientUser = new ArrayList<>();
    UTIL util;
    private int currentPage = 0;
    private ViewPager mViewPager;
    private RecyclerView recyclerView;
    private MoviesAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.fragment_about_us, container, false);
        getActivity().setTitle("Assigned Kilns");
        myContext = getActivity();
        util = new UTIL(getActivity());

        setView(rootView);

        return rootView;

    }

    @Override
    public void onResume() {
        super.onResume();
      //  getSchedule();
        addData();
    }

    private void setView(View rootView) {
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);

        tv_msg = rootView.findViewById(R.id.tv_msg);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());


        //   list_ClientUser.add(new ClientUserAll("1", "30 Dec 2018", "12:55 AM", "Today", "Delhi", "Rohtak Road,Peeragarhi,Delhi,110041", "1", "", ""));
        //  list_ClientUser.add(new ClientUserAll("1", "25 Dec 2018", "01:05 PM", "Tuesday", "Noida", "Sector-8, Industrial Area,Noida,201301", "1", "", ""));
        //  list_ClientUser.add(new ClientUserAll("1", "01 Nov 2018", "02:10 AM", "Wednesday", "Gurgaon", "DLF Tower,Gurgaon,Haryana,110038", "2", "", ""));
        //  list_ClientUser.add(new ClientUserAll("1", "28 Oct 2018", "02:10 PM", "Thursday", "Gurgaon", "DLF Tower,Gurgaon,Haryana,110038", "2", "", ""));
        //  list_ClientUser.add(new ClientUserAll("1", "02 Feb 2018", "02:10 PM", "Thursday", "Gurgaon", "DLF Tower,Gurgaon,Haryana,110038", "3", "", ""));

       /* mAdapter = new MoviesAdapter(getActivity(), list_ClientUser);
        recyclerView.setAdapter(mAdapter);
*/
    }


    private void addData(){
        list_ClientUser.clear();
        list_ClientUser.add(new ClientUserAll("1", "17-05-2019","","","Location 1","Address 1","2","test","258714525","28.578050,77.173140","77.173140"));
        list_ClientUser.add(new ClientUserAll("1", "18-05-2019","","","Location 2","Address 2","2","test","258714525","28.633110,77.282650","77.282650"));
        list_ClientUser.add(new ClientUserAll("1", "19-05-2019","","","Location 3","Address 3","2","test","258714525","28.646820,77.288190","77.288190"));
        list_ClientUser.add(new ClientUserAll("1", "20-05-2019","","","Location 4","Address 4","2","test","258714525","28.637817,77.243148","77.243148"));

        list_ClientUser.add(new ClientUserAll("1", "19-05-2019","","","Location 5","Address 5","2","test","258714525","28.633550,77.139240","77.139240"));
        list_ClientUser.add(new ClientUserAll("1", "20-05-2019","","","Location 6","Address 6","2","test","258714525","28.685630,77.169840","77.169840"));

        mAdapter = new MoviesAdapter(getActivity(), list_ClientUser);
        recyclerView.setAdapter(mAdapter);

    }

    public void getSchedule() {
        util.showProgressDialog(UTIL.Progress_msg);
        String patientId = UTIL.getPref(myContext, UTIL.Key_UserId);
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
                                    String address = jsonObject.getString("address");
                                    //String address = "";
                                    String status_id = jsonObject.getString("status_id");
                                    String branch_name = jsonObject.getString("branch_name");
                                    String patient_reports = jsonObject.getString("patient_reports");




                                    if (! (status_id.equals("5") || status_id.equals("4"))) {
                                        list_ClientUser.add(new ClientUserAll(appointment_id,
                                                parseDate(appointment_date),
                                                parseTime(from_time), day,
                                                branch_name, address, status_id,
                                                remarks, parseTime( to_time),patient_reports,branch_id));
                                    }

                                }
                            }

                        } catch (Exception e) {
                            e.getCause();
                        }

                        mAdapter = new MoviesAdapter(getActivity(), list_ClientUser);
                        recyclerView.setAdapter(mAdapter);

                        if(list_ClientUser.size()<1){
                            tv_msg.setVisibility(View.VISIBLE);
                        }
                        else {
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
        str = ar[0] + ":" +ar[1];
        return str;
    }

    public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MyViewHolder> {
        private List<ClientUserAll> moviesList;
        //   private HttpImageManager mHttpImageManager;

        public MoviesAdapter(Activity context, List<ClientUserAll> moviesList) {
            this.moviesList = moviesList;
            //     mHttpImageManager = ((AppController) context.getApplication()).getHttpImageManager();
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
          /*  View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.row_client_all_user, parent, false);*/
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.row_schedule_1, parent, false);

            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, int position) {
            final ClientUserAll projectPhoto = moviesList.get(position);
            holder.index_no.setText(String.valueOf(position + 1));


            holder.date.setText(projectPhoto.getTxt_Mail()); //date
            holder.time.setText(projectPhoto.getCompID());//time
            //   holder.day.setText(projectPhoto.getCompName());//day
            holder.center.setText(projectPhoto.getUserName());//center
            holder.address.setText(projectPhoto.getUserCategory());//center
            holder.day1.setText(projectPhoto.getCompName());//center
            if(projectPhoto.getMasterName()==null ||
                    projectPhoto.getMasterName().equalsIgnoreCase("null") ||
                    projectPhoto.getMasterName().trim().equals("")) {
                holder.remarks.setText("N/A");
            }else {
                holder.remarks.setText(projectPhoto.getMasterName());
            }
            String status = projectPhoto.getCaType();
          /*  if (status.equals("2")) {   // Approved
                holder.column.setBackgroundColor(getResources().getColor(R.color.green_material2));
                holder.Status.setText("Approved");
            } else if (status.equals("1")) { // Under Process
                holder.column.setBackgroundColor(getResources().getColor(R.color.yellow_material));
                holder.Status.setText("Under Process");
            } else if (status.equals("3")) { // Rejected
                holder.column.setBackgroundColor(getResources().getColor(R.color.red_material));
                holder.Status.setText("Rejected");
            } else if (status.equals("4")) {// Date expired
                holder.column.setBackgroundColor(getResources().getColor(R.color.orange_material));
                holder.Status.setText("Schedule Expired");
            } else if (status.equals("5")) {// Dialysis Done
                holder.column.setBackgroundColor(getResources().getColor(R.color.green_material2));
                holder.Status.setText("Completed");
            }*/
            /*if (status.equals("5")) {// Dialysis Done
                holder.btn_Report.setVisibility(View.VISIBLE);
                holder.imgvw_delete.setVisibility(View.GONE);
                holder.imgvw_edit.setVisibility(View.GONE);
                holder.btn_feedback.setVisibility(View.GONE);
            } else {
                holder.btn_Report.setVisibility(View.GONE);
                holder.imgvw_delete.setVisibility(View.VISIBLE);
                holder.imgvw_edit.setVisibility(View.VISIBLE);
                holder.btn_feedback.setVisibility(View.GONE);
            }*/


/*
            if (Descr == null || Descr.trim().equals("")) {
                holder.tv_status.setText("Not available");
            } else {
                holder.tv_status.setText(Descr);
            }*/

            holder.imgvw_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                  /*  Intent intent = new Intent(getActivity(), UpdateScheduleActivity.class);
                    intent.putExtra("flag", "1");
                    intent.putExtra("appointment_Id", projectPhoto.getUserID());
                    intent.putExtra("Date", projectPhoto.getTxt_Mail());
                    intent.putExtra("Timeing_From", projectPhoto.getCompID());
                    intent.putExtra("Timeing_To", projectPhoto.gettxt_Mobile());
                    startActivity(intent);*/


                        Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse("google.navigation:q=" + projectPhoto.getReport()));
                        myContext.startActivity(intent);


                }
            });


            holder.parentView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse("google.navigation:q=" + projectPhoto.getReport()));
                    myContext.startActivity(intent);


                }
            });


            holder.imgvw_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(getActivity(), UpdateScheduleActivity.class);
                    intent.putExtra("flag", "2");
                    intent.putExtra("appointment_Id", projectPhoto.getUserID());
                    intent.putExtra("Date", projectPhoto.getTxt_Mail());
                    intent.putExtra("Timeing_From", projectPhoto.getCompID());
                    intent.putExtra("Timeing_To", projectPhoto.gettxt_Mobile());
                    startActivity(intent);

                }
            });
            holder.btn_Report.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), ViewReportActivity.class);
                    intent.putExtra("flag", "2");
                    intent.putExtra("appointment_Id", projectPhoto.getUserID());
                    intent.putExtra("Date", projectPhoto.getTxt_Mail());
                    intent.putExtra("Timeing_From", projectPhoto.getCompID());
                    intent.putExtra("Timeing_To", projectPhoto.gettxt_Mobile());
                    startActivity(intent);
                }
            });


        }

        @Override
        public int getItemCount() {
            return moviesList.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            TextView date, time, phone, email, center, address, day1, Status,remarks;
            LinearLayout imgvw_edit, imgvw_delete, btn_Report,btn_feedback;
            TextView index_no;

            //  Button   btn_Report;
            LinearLayout parentView, column;

            public MyViewHolder(View convertview) {
                super(convertview);

                parentView = convertview.findViewById(R.id.row_jobFile);


                index_no = convertview.findViewById(R.id.serial_no);
                remarks = (TextView) convertview.findViewById(R.id.remarks);
                date = (TextView) convertview.findViewById(R.id.name);
                time = (TextView) convertview.findViewById(R.id.user_type);
                //    day = (TextView) convertview.findViewById(R.id.masterl);
            /*  phone = (TextView) convertview.findViewById(R.id.phone);
               email = (TextView) convertview.findViewById(R.id.email);*/
                imgvw_edit = convertview.findViewById(R.id.imgvw_edit);

                imgvw_delete = convertview.findViewById(R.id.imgvw_delete);

                center = (TextView) convertview.findViewById(R.id.center);
                address = (TextView) convertview.findViewById(R.id.address);
                day1 = (TextView) convertview.findViewById(R.id.day);

                column = convertview.findViewById(R.id.column);
                btn_Report = convertview.findViewById(R.id.btn_Report);
                Status = (TextView) convertview.findViewById(R.id.Status);
                btn_feedback = convertview.findViewById(R.id.btn_feedback);

            }
        }
    }
}
