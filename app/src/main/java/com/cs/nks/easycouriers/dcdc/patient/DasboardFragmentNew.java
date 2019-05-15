package com.cs.nks.easycouriers.dcdc.patient;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.cs.nks.easycouriers.R;
import com.cs.nks.easycouriers.activity.ActivityWithNavigationMenu;
import com.cs.nks.easycouriers.activity.ChangePassword;
import com.cs.nks.easycouriers.util.UTIL;

import java.util.Timer;
import java.util.TimerTask;

import static com.android.volley.VolleyLog.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class DasboardFragmentNew extends Fragment {

    final long DELAY_MS = 0;//delay in milliseconds before task is to be executed
    final long PERIOD_MS = 3000; // time in milliseconds between successive task executions.
    final int NUM_PAGES = 4;
    Context myContext;
    UTIL utill;
    LinearLayout ll_reports, ll_Appointment, ll_Schedule;
    LinearLayout ll_share_app, ll_cntct_us, ll_about_us,ll_Change_Password;
    Timer timer;
    private int currentPage = 0;
    private ViewPager mViewPager;
    private SectionsPagerAdapter mSectionsPagerAdapter;
     Handler handler;
      Runnable Update;
    public DasboardFragmentNew() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.dasboard_new_1, container, false);
        getActivity().setTitle("Home");
        myContext = getActivity();

       /* ll_reports
        ll_Appointment
        ll_Schedule*/
        setView(rootView);
        return rootView;

    }

    @Override
    public void onResume() {
        super.onResume();
        currentPage=0;

    }

    private void setView(View rootView) {

        ll_share_app = rootView.findViewById(R.id.ll_share_app);
        ll_cntct_us = rootView.findViewById(R.id.ll_cntct_us);
        ll_about_us = rootView.findViewById(R.id.ll_about_us);
        ll_Change_Password = rootView.findViewById(R.id.ll_Change_Password);

        ll_Appointment = rootView.findViewById(R.id.ll_Appointment);
        ll_Schedule = rootView.findViewById(R.id.ll_Schedule);

        ll_reports = rootView.findViewById(R.id.ll_reports);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getFragmentManager());
        mViewPager = rootView.findViewById(R.id.view_pager);
       // mViewPager.setAdapter(mSectionsPagerAdapter);
        CustomPagerAdapter mCustomPagerAdapter = new CustomPagerAdapter(getActivity());
        mViewPager.setAdapter(mCustomPagerAdapter);




        TabLayout tabLayout = (TabLayout) rootView.findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(mViewPager, true);
      //  autoSwipeViewPager();
        setupAutoPager();


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
        });
    }

    private void autoSwipeViewPager() {
        /*After setting the adapter use the timer */
        handler = new Handler();
       Update = new Runnable() {
            public void run() {
                if (currentPage == NUM_PAGES) {
                    currentPage = 0;
                }
                mViewPager.setCurrentItem(currentPage++, true);
            }
        };

        timer = new Timer(); // This will create a new Thread
        timer.schedule(new TimerTask() { // task to be scheduled
            @Override
            public void run() {
                handler.post(Update);
            }
        }, DELAY_MS, PERIOD_MS);
    }

    @Override
    public void onStop() {
        super.onStop();
        stopTimerThread();
    }

    public void stopTimerThread() {
        handler.removeCallbacks(Update);
        Log.e(TAG, "Thread Stopped: ");
    }
    private void setupAutoPager()
    {

       // mViewPager.setCurrentItem(currentPage, true);
        handler = new Handler();

        Update = new Runnable() {
            public void run()
            {

                mViewPager.setCurrentItem(currentPage, true);
                if(currentPage == NUM_PAGES)
                {
                    currentPage = 0;
                }
                else
                {
                    ++currentPage ;
                }
            }
        };


        timer = new Timer();
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                handler.post(Update);
            }
        }, 500, 2500);
    }
    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_test_view_pager, container, false);
            ImageView img = rootView.findViewById(R.id.imgvw);
            //  textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));

            int pageNumber = getArguments().getInt(ARG_SECTION_NUMBER);
            if (pageNumber == 0)
                img.setImageResource(R.drawable.dcdc_1);
            else if (pageNumber == 1)
                img.setImageResource(R.drawable.dcdc_2);
            else if (pageNumber == 2)
                img.setImageResource(R.drawable.dcdc_3);
            else if (pageNumber == 3)
                img.setImageResource(R.drawable.dcdc_4);


            return rootView;
        }
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return NUM_PAGES;
        }
    }

    class CustomPagerAdapter extends PagerAdapter {

        Context mContext;
        LayoutInflater mLayoutInflater;
        int[] mResources = {
                R.drawable.dcdc_1,
                R.drawable.dcdc_2,
                R.drawable.dcdc_3,
                R.drawable.dcdc_4};

        public CustomPagerAdapter(Context context) {
            mContext = context;
            mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return mResources.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == ((LinearLayout) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View itemView = mLayoutInflater.inflate(R.layout.fragment_test_view_pager, container, false);

            ImageView imageView = (ImageView) itemView.findViewById(R.id.imgvw);
            imageView.setImageResource(mResources[position]);

            container.addView(itemView);

            return itemView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((LinearLayout) object);
        }
    }

}
