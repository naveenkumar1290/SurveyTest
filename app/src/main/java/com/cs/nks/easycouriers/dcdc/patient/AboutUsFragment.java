package com.cs.nks.easycouriers.dcdc.patient;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cs.nks.easycouriers.R;
import com.cs.nks.easycouriers.util.UTIL;

import java.util.Timer;

/**
 * A simple {@link Fragment} subclass.
 */
public class AboutUsFragment extends Fragment {

    Context myContext;
    UTIL utill;
    Timer timer;
    private int currentPage = 0;
    private ViewPager mViewPager;

    public AboutUsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.activity_about_us, container, false);
        getActivity().setTitle("About Us");
        myContext = getActivity();


        return rootView;

    }


}
