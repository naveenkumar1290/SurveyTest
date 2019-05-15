package com.cs.nks.easycouriers.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cs.nks.easycouriers.R;
import com.cs.nks.easycouriers.util.ConnectionDetector;
import com.cs.nks.easycouriers.util.UTIL;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class UpdateScheduleActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {
    EditText et_AppointmentDate,
            et_TimingFrom, et_day, et_TimingTo;

    Button btnSearch;
    String appointmnetId = "";
    UTIL util;
    boolean clicked_TimeFrom = false;
    String AppointmentDate = "";
    String Timeing_From = "";
    String Timeing_To = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_booking_details);

        util = new UTIL(UpdateScheduleActivity.this);
        et_TimingTo = (EditText) findViewById(R.id.et_TimingTo); //day
        et_AppointmentDate = (EditText) findViewById(R.id.et_AppointmentDate);
        et_TimingFrom = (EditText) findViewById(R.id.et_TimingFrom);

        btnSearch = findViewById(R.id.btnSearch);

        try {
            appointmnetId = getIntent().getExtras().getString("appointment_Id");
            AppointmentDate =parseDateToddMMyyyy_1(getIntent().getExtras().getString("Date"));
            Timeing_From = getIntent().getExtras().getString("Timeing_From");
            Timeing_To = getIntent().getExtras().getString("Timeing_To");


            et_AppointmentDate.setText(AppointmentDate);
            et_TimingFrom.setText(Timeing_From);
            et_TimingTo.setText(Timeing_To);
        } catch (Exception e) {

        }
        //
      final   String flag = getIntent().getExtras().getString("flag");
        if (flag.equals("1")) {
            btnSearch.setText("Update");
            setTitle("Update Schedule");
        } else if (flag.equals("2")) {
            btnSearch.setText("Delete");
            setTitle(UTIL.getTitle("Delete Schedule"));
        }
        //
        et_TimingFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clicked_TimeFrom = true;
                Click_getTime("Timing From");
            }
        });
        et_TimingTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clicked_TimeFrom = false;
                Click_getTime("Timing To");
            }
        });
        et_AppointmentDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Click_getDate();
            }
        });
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String patientId = UTIL.getPref(UpdateScheduleActivity.this, UTIL.Key_UserId);
                String AppointmentDate = et_AppointmentDate.getText().toString().trim();
                String TimingFrom = et_TimingFrom.getText().toString().trim();
                String TimingTo = et_TimingTo.getText().toString().trim();

                if (AppointmentDate.length() == 0) {
                    Toast.makeText(UpdateScheduleActivity.this, "Please enter appointment date!", Toast.LENGTH_SHORT).show();
                } else if (TimingFrom.length() == 0) {
                    Toast.makeText(UpdateScheduleActivity.this, "Please enter timing from!", Toast.LENGTH_SHORT).show();
                } else if (TimingTo.length() == 0) {
                    Toast.makeText(UpdateScheduleActivity.this, "Please enter timing to!", Toast.LENGTH_SHORT).show();
                } else {
                    if (new ConnectionDetector(UpdateScheduleActivity.this).isConnectingToInternet()) {

                      if(flag.equals("1")){
                          Api_UpdateSchedule(patientId, appointmnetId, AppointmentDate,
                                  TimingFrom, TimingTo);
                      }
                      else {
                          Api_DeleteSchedule(patientId, appointmnetId);
                      }





                    } else {
                        Toast.makeText(UpdateScheduleActivity.this, UTIL.NoInternet, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date = +dayOfMonth + "/" + (++monthOfYear) + "/" + year;

        String dayOfMonthString = dayOfMonth < 10 ? "0" + dayOfMonth : "" + dayOfMonth;
        String monthOfYearString = monthOfYear < 10 ? "0" + monthOfYear : "" + monthOfYear;

        //2018-01-19%20

        // String  date_1 = year + "-" + monthOfYearString + "-" + dayOfMonthString;
        String date_1 = dayOfMonthString + "-" + monthOfYearString + "-" + year;


        et_AppointmentDate.setText(date_1);

    }

    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
        String hourString = hourOfDay < 10 ? "0" + hourOfDay : "" + hourOfDay;
        String minuteString = minute < 10 ? "0" + minute : "" + minute;
        String secondString = second < 10 ? "0" + second : "" + second;
        String time = "You picked the following time: " + hourString + "h" + minuteString + "m" + secondString + "s";
        String t = hourString + ":" + minuteString;
        //19:11:09.983
        //  et_Time.setText(t);
        String time_1 = hourString + ":" + minuteString;//+ ":" + secondString + "." + "000";

        // et_Time.setText(time_1);
        if (clicked_TimeFrom) {
            et_TimingFrom.setText(time_1);
        } else {
            et_TimingTo.setText(time_1);
        }



    }

    private void Click_getDate() {
        Date ndate = null;

        /**/
  /*      String inputPattern = "dd MMM yyyy";
        String outputPattern = "dd-MM-yyyy";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);
        Date date = null;
        String str = null;
        try {
            date = inputFormat.parse(AppointmentDate);
           String dt = outputFormat.format(date);
            ndate=outputFormat.parse(AppointmentDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        *//**/

        try {
            DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
            ndate = (Date) formatter.parse(AppointmentDate);
        } catch (ParseException e) {
            e.getCause();
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(ndate);

        DatePickerDialog dpd = DatePickerDialog.newInstance(
                UpdateScheduleActivity.this,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));

        dpd.setThemeDark(false);
        dpd.vibrate(false);
        dpd.dismissOnPause(false);
        dpd.showYearPickerFirst(false);
        dpd.setVersion(DatePickerDialog.Version.VERSION_1);
        dpd.setAccentColor(getResources().getColor(R.color.colorAccent));


        dpd.setTitle("Appointment Date");
        dpd.setYearRange(1985, 2028);
        dpd.setMinDate(calendar);
        dpd.show(UpdateScheduleActivity.this.getFragmentManager(), "dialog");


    }

    private void Click_getTime(String Title) {
        boolean is24HourMode = true;
        String time = "";
        if (clicked_TimeFrom) {
            time = Timeing_From;
        } else {
            time = Timeing_To;
        }

        Date ndate = null;
        try {
            DateFormat formatter = new SimpleDateFormat("HH:mm");
            ndate = (Date) formatter.parse(time);
        } catch (ParseException e) {
            e.getCause();
        }


        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(ndate);

        TimePickerDialog tpd = TimePickerDialog.newInstance(
                UpdateScheduleActivity.this,
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                true);
        tpd.setThemeDark(false);
        tpd.vibrate(false);
        tpd.dismissOnPause(false);
        tpd.enableSeconds(false);
        tpd.setVersion(TimePickerDialog.Version.VERSION_2);

        tpd.setAccentColor(getResources().getColor(R.color.colorAccent));
        tpd.setTitle(Title);


        tpd.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                Log.d("TimePicker", "Dialog was cancelled");
            }
        });
        tpd.show(UpdateScheduleActivity.this.getFragmentManager(), "Timepickerdialog");


    }

    public void Api_UpdateSchedule(final String p_id,
                                   final String appointment_id,
                                   final String appointment_date,
                                   final String from_time,
                                   final String to_time
    ) {

        String URL = UTIL.Domain_DCDC + UTIL.UpdateSchedule_API;
        util.showProgressDialog(UTIL.Progress_msg);
        StringRequest postRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
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
                                Toast.makeText(UpdateScheduleActivity.this, "Successful", Toast.LENGTH_SHORT).show();
                                finish();


                            } else {
                                Toast.makeText(UpdateScheduleActivity.this, msg, Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            e.getMessage();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        util.hideProgressDialog();
                        Toast.makeText(UpdateScheduleActivity.this, "Error!", Toast.LENGTH_SHORT).show();
                        Log.d("Error.Response", String.valueOf(error));
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("p_id", p_id);
                params.put("appointment_id", appointment_id);
                params.put("appointment_date",parseDateToddMMyyyy(appointment_date));
                params.put("from_time", from_time+":00");
                params.put("to_time", to_time+":00");


                return params;


            }
        };
        RequestQueue queue = Volley.newRequestQueue(UpdateScheduleActivity.this);
        queue.add(postRequest);
    }
    public void Api_DeleteSchedule(final String p_id,
                                   final String appointment_id

    ) {

        String URL = UTIL.Domain_DCDC + UTIL.DeleteSchedule_API;
        util.showProgressDialog(UTIL.Progress_msg);
        StringRequest postRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
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
                                Toast.makeText(UpdateScheduleActivity.this, "Successful", Toast.LENGTH_SHORT).show();
                                finish();


                            } else {
                                Toast.makeText(UpdateScheduleActivity.this, msg, Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            e.getMessage();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        util.hideProgressDialog();
                        Toast.makeText(UpdateScheduleActivity.this, "Error!", Toast.LENGTH_SHORT).show();
                        Log.d("Error.Response", String.valueOf(error));
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("p_id", p_id);
                params.put("appointment_id", appointment_id);

                return params;


            }
        };
        RequestQueue queue = Volley.newRequestQueue(UpdateScheduleActivity.this);
        queue.add(postRequest);
    }
    public String parseDateToddMMyyyy(String time) {
        String inputPattern = "dd-MM-yyyy";
        String outputPattern = "yyyy-MM-dd";
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
    public String parseDateToddMMyyyy_1(String time) {
        String inputPattern = "dd MMM yyyy";
        String outputPattern = "dd-MM-yyyy";
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
}
