package com.cs.nks.easycouriers.dcdc.patient;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cs.nks.easycouriers.R;
import com.cs.nks.easycouriers.model.ClientUserAll;
import com.cs.nks.easycouriers.model.OurServices;
import com.cs.nks.easycouriers.util.UTIL;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

/**
 * A simple {@link Fragment} subclass.
 */
public class ServicesFragmentNew extends Fragment {

    Context myContext;
    UTIL utill;
    Timer timer;
    TextView tv_msg;
    private int currentPage = 0;
    private ViewPager mViewPager;
    private RecyclerView recyclerView;
    private MoviesAdapter mAdapter;

    ArrayList<OurServices> list_ClientUser = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.fragment_services_1, container, false);
        getActivity().setTitle("Our Services");
        myContext = getActivity();
        setView(rootView);

        return rootView;

    }

    private void setView(View rootView) {
        recyclerView = (RecyclerView)rootView. findViewById(R.id.recycler_view);

        tv_msg = rootView.findViewById(R.id.tv_msg);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        list_ClientUser.add(new OurServices("1","PATIENT CARE",
                "Dialysis on Wheels as we call it is a specialized service designed for patients and nursing homes that do not have a dialysis setup of their own. A specially equipped Ambulance fitted with all the medical equipments is at your service to perform dialysis at an desired location in the presence of trained paramedical staff.",
                R.drawable.patient_care));

        list_ClientUser.add(new OurServices("1","HEMODIALYSIS",
                "Dialysis on Wheels as we call it is a specialized service designed for patients and nursing homes that do not have a dialysis setup of their own. A specially equipped Ambulance fitted with all the medical equipments is at your service to perform dialysis at an desired location in the presence of trained paramedical staff.",
                R.drawable.hemodialysis));


        list_ClientUser.add(new OurServices("1","PERITONEAL DIALYSIS",
                "Dialysis on Wheels as we call it is a specialized service designed for patients and nursing homes that do not have a dialysis setup of their own. A specially equipped Ambulance fitted with all the medical equipments is at your service to perform dialysis at an desired location in the presence of trained paramedical staff.",
                R.drawable.peritoneal));

        list_ClientUser.add(new OurServices("1","HOME DIALYSIS",
                "Dialysis on Wheels as we call it is a specialized service designed for patients and nursing homes that do not have a dialysis setup of their own. A specially equipped Ambulance fitted with all the medical equipments is at your service to perform dialysis at an desired location in the presence of trained paramedical staff.",
                R.drawable.homedialysis));


        list_ClientUser.add(new OurServices("1","TRANSPLANT CLINIC",
                "Dialysis on Wheels as we call it is a specialized service designed for patients and nursing homes that do not have a dialysis setup of their own. A specially equipped Ambulance fitted with all the medical equipments is at your service to perform dialysis at an desired location in the presence of trained paramedical staff.",
                R.drawable.transplant));

        list_ClientUser.add(new OurServices("1","EDUCATION & RESEARCH",
                "Dialysis on Wheels as we call it is a specialized service designed for patients and nursing homes that do not have a dialysis setup of their own. A specially equipped Ambulance fitted with all the medical equipments is at your service to perform dialysis at an desired location in the presence of trained paramedical staff.",
                R.drawable.education));


        list_ClientUser.add(new OurServices("1","DIALYSIS DIET",
                "Dialysis on Wheels as we call it is a specialized service designed for patients and nursing homes that do not have a dialysis setup of their own. A specially equipped Ambulance fitted with all the medical equipments is at your service to perform dialysis at an desired location in the presence of trained paramedical staff.",
                R.drawable.dylasis));

        list_ClientUser.add(new OurServices("1","PATHOLOGY",
                "Dialysis on Wheels as we call it is a specialized service designed for patients and nursing homes that do not have a dialysis setup of their own. A specially equipped Ambulance fitted with all the medical equipments is at your service to perform dialysis at an desired location in the presence of trained paramedical staff.",
                R.drawable.pathology));

        mAdapter = new MoviesAdapter(getActivity(), list_ClientUser);
        recyclerView.setAdapter(mAdapter);

    }
    public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MyViewHolder> {
        private List<OurServices> moviesList;
        //   private HttpImageManager mHttpImageManager;

        public MoviesAdapter(Activity context, List<OurServices> moviesList) {
            this.moviesList = moviesList;
            //     mHttpImageManager = ((AppController) context.getApplication()).getHttpImageManager();
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.row_services, parent, false);

            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, int position) {
            OurServices projectPhoto = moviesList.get(position);
          //  holder.index_no.setText(String.valueOf(position + 1));


            holder.title.setText(projectPhoto.getTitle()); //date
            holder.text.setText(projectPhoto.getText());//time
            holder.img.setImageResource(projectPhoto.getImg());//day


/*
            if (Descr == null || Descr.trim().equals("")) {
                holder.tv_status.setText("Not available");
            } else {
                holder.tv_status.setText(Descr);
            }*/

            holder.parentView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                  /*  Intent intent = new Intent(context, ClientFileDetailActivity.class);
                    // intent.putExtra("obj", projectPhoto);
                    intent.putExtra("FileId", FileId);
                    intent.putExtra("FileName", FileName);
                    intent.putExtra("jobID", jobID);
                    intent.putExtra("jobName", job);
                    startActivity(intent);*/
                }
            });
        }

        @Override
        public int getItemCount() {
            return moviesList.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            TextView title, text;
            ImageView img;

            LinearLayout parentView;

            public MyViewHolder(View convertview) {
                super(convertview);

                parentView =  convertview.findViewById(R.id.row_jobFile);
                title = (TextView) convertview.findViewById(R.id.title);
                text = (TextView) convertview.findViewById(R.id.text);
                img = (ImageView) convertview.findViewById(R.id.img);


            }
        }
    }

}
