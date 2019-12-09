package com.cs.nks.easycouriers.dcdc.doctor;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cs.nks.easycouriers.R;
import com.cs.nks.easycouriers.model.Patient;
import com.cs.nks.easycouriers.util.ConnectionDetector;
import com.cs.nks.easycouriers.util.UTIL;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.android.volley.VolleyLog.TAG;

public class PatientListActivity extends AppCompatActivity {
    Context myContext;
    UTIL utill;

    TextView tv_msg;
    ArrayList<Patient> list_Patient = new ArrayList<>();
    UTIL util;
    AlertDialog alertDialog;
    String Branch_id = "";
    private RecyclerView recyclerView;
    private MoviesAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_list);
        setTitle("Patient List");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            if (bundle.containsKey((UTIL.BRANCH_ID))) {
                Branch_id = bundle.getString((UTIL.BRANCH_ID));
            }
        }

        myContext = PatientListActivity.this;
        util = new UTIL(myContext);
        setView();


    }

    private void setView() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        tv_msg = findViewById(R.id.tv_msg);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(myContext);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());


    }


    @Override
    public boolean onSupportNavigateUp() {
        // onBackPressed();
        finish();
        return false;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (new ConnectionDetector(myContext).isConnectingToInternet()) {
            getPatientList();
        } else {
            Toast.makeText(myContext, "No internet", Toast.LENGTH_SHORT).show();
        }

    }

    public void getPatientList() {
        util.showProgressDialog(UTIL.Progress_msg);
        String dcotorId = UTIL.getPref(myContext, UTIL.Key_UserId);
        //http://dcdc.businesstowork.com/dcdc_web_service/patient_list_by_doctor_id.php?branch_id=4&user_id=34
        String url = UTIL.Domain_DCDC + UTIL.PatientList_API + "branch_id=" + Branch_id+"&user_id="+dcotorId;
        String tag_json_obj = "json_obj_req";
        list_Patient.clear();


        final RequestQueue requestQueue = Volley.newRequestQueue(myContext);
        // Initialize a new StringRequest
        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, response.toString());
                        util.hideProgressDialog();

                        try {

                            JSONArray jsonArray1 = new JSONArray(response);
                            JSONObject jsonObject1 = jsonArray1.getJSONObject(0);

                            String status = jsonObject1.getString("status");
                            if (status.equals("1")) {

                                if(jsonObject1.has("patient_list")){
                                JSONArray jsonArray = jsonObject1.getJSONArray("patient_list");
                                if (jsonArray.length() > 0) {
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                                        String p_id = jsonObject.getString("patient_id");
                                        String name = jsonObject.getString("name");
                                        String contact = jsonObject.getString("contact");
                                        String mail = jsonObject.getString("mail");
                                        String blood_group = jsonObject.getString("blood_group");
                                        String address = jsonObject.getString("address");
                                        list_Patient.add(new Patient(name, p_id, contact, mail, blood_group, address));

                                    }
                                }

                                }

                            }
                            mAdapter = new MoviesAdapter(PatientListActivity.this, list_Patient);
                            recyclerView.setAdapter(mAdapter);


                        } catch (Exception e) {
                            e.getCause();
                        }
                        if (list_Patient.size() < 1) {
                            tv_msg.setVisibility(View.VISIBLE);
                        } else {
                            tv_msg.setVisibility(View.GONE);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d(TAG, "Error: " + error.getMessage());
                        util.hideProgressDialog();
                    }
                }
        );

        // Add StringRequest to the RequestQueue
        requestQueue.add(stringRequest);


    }

    public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MyViewHolder> {
        private List<Patient> moviesList;
        //   private HttpImageManager mHttpImageManager;

        public MoviesAdapter(Activity context, List<Patient> moviesList) {
            this.moviesList = moviesList;
            //     mHttpImageManager = ((AppController) context.getApplication()).getHttpImageManager();
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.row_patient, parent, false);

            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, int position) {
            final Patient projectPhoto = moviesList.get(position);
            holder.index_no.setText(String.valueOf(position + 1));

            holder.Name.setText(projectPhoto.get_name());
            holder.mobile.setText(projectPhoto.get_contact());
            holder.email.setText(projectPhoto.get_mail());
            holder.address.setText(projectPhoto.get_address());
            holder.parentView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(myContext, ReportActivity.class);
                    i.putExtra(UTIL.PATIENT_ID, projectPhoto.get_id());
                    myContext.startActivity(i);

                }
            });


        }

        @Override
        public int getItemCount() {
            return moviesList.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            TextView Name, mobile, address, email;
            LinearLayout parentView;//, imgvw_delete, btn_Report,btn_feedback;
            TextView index_no;


            public MyViewHolder(View convertview) {
                super(convertview);

                parentView = convertview.findViewById(R.id.parentView);


                index_no = convertview.findViewById(R.id.serial_no);
                Name = (TextView) convertview.findViewById(R.id.Name);
                mobile = (TextView) convertview.findViewById(R.id.Mobile);
                email = (TextView) convertview.findViewById(R.id.mail);
                address = (TextView) convertview.findViewById(R.id.address);


            }
        }
    }

}
