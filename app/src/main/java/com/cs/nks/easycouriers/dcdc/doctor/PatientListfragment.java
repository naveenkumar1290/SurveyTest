package com.cs.nks.easycouriers.dcdc.doctor;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
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
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import static com.android.volley.VolleyLog.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class PatientListfragment extends Fragment {

    Context myContext;
    UTIL utill;

    TextView tv_msg;
    ArrayList<Patient> list_Patient = new ArrayList<>();
    UTIL util;
    AlertDialog alertDialog;
    private RecyclerView recyclerView;
    private MoviesAdapter mAdapter;


    public static ArrayList<Patient> removeDuplicates(ArrayList<Patient> patients) {
        Set<Object> s = new TreeSet<Object>(new Comparator<Object>() {

            @Override
            public int compare(Object o1, Object o2) {
                Patient patient1 = (Patient) o1;
                Patient patient2 = (Patient) o2;
                if (patient1.get_id().equalsIgnoreCase(patient2.get_id())) {
                    return 0;
                }
                return 1;
            }
        });
        s.addAll(patients);
        List<Object> res = Arrays.asList(s.toArray());
        ArrayList<Patient> patients1 = new ArrayList<>();
        for (Object ob : res) {
            Patient patient = (Patient) ob;
            if (!(patient.get_contact().equals("") && patient.get_name().equals("") && patient.get_mail().equals("")))
                patients1.add(patient);
        }
        return patients1;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.fragment_patient_list, container, false);
        getActivity().setTitle("Patient List");
        myContext = getActivity();
        util = new UTIL(getActivity());
        setView(rootView);

        return rootView;

    }

    private void setView(View rootView) {
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);

        tv_msg = rootView.findViewById(R.id.tv_msg);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());


    }

    @Override
    public void onResume() {
        super.onResume();
        if (new ConnectionDetector(getActivity()).isConnectingToInternet()) {
            getPatientList();
        } else {
            Toast.makeText(getActivity(), "No internet", Toast.LENGTH_SHORT).show();
        }

    }

    public void getPatientList() {
        util.showProgressDialog(UTIL.Progress_msg);
        String dcotorId = UTIL.getPref(myContext, UTIL.Key_UserId);
        String url = UTIL.Domain_DCDC + UTIL.PatientList_API + "doctor_id=" + dcotorId;
        String tag_json_obj = "json_obj_req";
        list_Patient.clear();


        final RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
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
                            JSONArray jsonArray = jsonObject1.getJSONArray("patient_list");
                            if (jsonArray.length() > 0) {
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                                    String p_id = jsonObject.getString("p_id");
                                    String name = jsonObject.getString("name");
                                    String contact = jsonObject.getString("contact");
                                    String mail = jsonObject.getString("mail");
                                    String blood_group = jsonObject.getString("blood_group");
                                    String address = jsonObject.getString("address");
                                    list_Patient.add(new Patient(name, p_id, contact, mail, blood_group, address));

                                }

                                ArrayList<Patient> patients = removeDuplicates(list_Patient);
                                list_Patient.clear();
                                list_Patient.addAll(patients);

                            }


                            mAdapter = new MoviesAdapter(getActivity(), list_Patient);
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
