package com.cs.nks.easycouriers.activity;


import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.cs.nks.easycouriers.R;
import com.cs.nks.easycouriers.model.ImagePath;
import com.cs.nks.easycouriers.retrofit_multipart.ApiService;
import com.cs.nks.easycouriers.retrofit_multipart.ProgressRequestBody;
import com.cs.nks.easycouriers.survey.ActivityWithNavigationMenuPatient;
import com.cs.nks.easycouriers.util.AppConstants;
import com.cs.nks.easycouriers.util.CameraUtils;
import com.cs.nks.easycouriers.util.ConnectionDetector;
import com.cs.nks.easycouriers.util.UTIL;
import com.cs.nks.easycouriers.util.Utility;
import com.darsh.multipleimageselect.activities.AlbumSelectActivity;
import com.darsh.multipleimageselect.models.Image;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.concurrent.TimeUnit;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static com.cs.nks.easycouriers.util.Utility.MEDIA_TYPE_IMAGE;

/**
 * A simple {@link Fragment} subclass.
 */
public class MonthWiseFeedbackActivityNew2 extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, ProgressRequestBody.UploadCallbacks {

    private static String imageStoragePath;
    Context myContext;
    UTIL util;
    Timer timer;
    EditText et_AppointmentDate, description, environment,
            cleanliness,
            staff_behave,
            Procedure,
            DialysisStarted,
            DialysisReceived;
    String path;
    ImageView add_image;
    ProgressDialog uploadProgressDialog;
    int Count_Image_Uploaded = 0;
    ApiService apiService;
    ArrayList<ImagePath> list_path = new ArrayList<>();
    long totalSize = 0;
    //  ArrayList<String> list_UploadImageName = new ArrayList<String>();
    String branchId;
    String str_feedbackDate, str_remarks, str_environment,
            str_cleanliness,
            str_staff_behave,
            str_Procedure,
            str_DialysisStarted,
            str_DialysisReceived;
    // TextView tv_msg;
    String feedback_files;
    private RecyclerView recyclerView;
    private MoviesAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle("Feedback");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setContentView(R.layout.activity_month_wise_feedback2);
        myContext = MonthWiseFeedbackActivityNew2.this;
        setView();
    }


    private void setView() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        util = new UTIL(MonthWiseFeedbackActivityNew2.this);


        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(myContext);
        // recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new MoviesAdapter(MonthWiseFeedbackActivityNew2.this);
        recyclerView.setAdapter(mAdapter);

        environment = (EditText) findViewById(R.id.environment);
        cleanliness = (EditText) findViewById(R.id.cleanliness);
        staff_behave = (EditText) findViewById(R.id.staff_behave);
        Procedure = (EditText) findViewById(R.id.Procedure);
        DialysisStarted = (EditText) findViewById(R.id.DialysisStarted);
        DialysisReceived = (EditText) findViewById(R.id.DialysisReceived);
        et_AppointmentDate = (EditText) findViewById(R.id.et_AppointmentDate);
        description = (EditText) findViewById(R.id.description);
        add_image = findViewById(R.id.add_image);
        add_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog_attach_file();
            }
        });

        try {
            Bundle b = getIntent().getExtras();
            if (b != null) {
                //  String appointmentId = b.getString("appointment_Id");
                branchId = b.getString("Branch_Id");
            }

        } catch (Exception e) {
            e.getMessage();
        }


        et_AppointmentDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Click_getDate();
            }
        });

     /*   RadioGroup radio = findViewById(R.id.radio);
        RadioButton rb_active = findViewById(R.id.radioActive);
        RadioButton rb_inactive = findViewById(R.id.radioInactive);*/
        // radio.getCheckedRadioButtonId();
        /*if (rb_active.isChecked()) {

        }*/


    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        finish();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_feedback, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.download) {
          /*  if(! et_description.getText().toString().trim().equals("")){
                callSubmitFeedback();
            }else {
                et_description.setError("Please enter description!");
            }*/

            if (ValidateFields()) {
                multipartImageUpload();
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
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

    private void Click_getDate() {
        Date ndate = null;


        /*try {
            DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
            ndate = (Date) formatter.parse(AppointmentDate);
        } catch (ParseException e) {
            e.getCause();
        }*/
        Date c = Calendar.getInstance().getTime();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(c);

        DatePickerDialog dpd = DatePickerDialog.newInstance(
                MonthWiseFeedbackActivityNew2.this,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));

        dpd.setThemeDark(false);
        dpd.vibrate(false);
        dpd.dismissOnPause(false);
        dpd.showYearPickerFirst(false);
        dpd.setVersion(DatePickerDialog.Version.VERSION_1);
        dpd.setAccentColor(getResources().getColor(R.color.colorAccent));


        dpd.setTitle("Feedback Date");
        dpd.setYearRange(1985, 2028);
        dpd.setMinDate(calendar);
        dpd.show(MonthWiseFeedbackActivityNew2.this.getFragmentManager(), "dialog");


    }

    public void dialog_attach_file() {
        final Dialog dialog = new Dialog(MonthWiseFeedbackActivityNew2.this);
        // dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.openattachmentdilog_new);
        dialog.getWindow().setBackgroundDrawable(
                new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCancelable(false);
        //  dialog.setTitle("Attach");
        LinearLayout cameralayout = (LinearLayout) dialog
                .findViewById(R.id.cameralayout);
        LinearLayout gallarylayout = (LinearLayout) dialog
                .findViewById(R.id.gallarylayout);
        LinearLayout filelayout = (LinearLayout) dialog
                .findViewById(R.id.filelayout);
        filelayout.setVisibility(View.VISIBLE);
        ImageView crosse = (ImageView) dialog
                .findViewById(R.id.close);

        crosse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });

        cameralayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

                if (CameraUtils.checkPermissions(getApplicationContext())) {
                    captureImage();
                } else {
                    requestCameraPermission(MEDIA_TYPE_IMAGE);
                }

            }
        });
        gallarylayout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MonthWiseFeedbackActivityNew2.this, AlbumSelectActivity.class);
                intent.putExtra(com.darsh.multipleimageselect.helpers.Constants.INTENT_EXTRA_LIMIT, 50);
                startActivityForResult(intent, AppConstants.GALLERY_CAPTURE_IMAGE_REQUEST_CODE);

                dialog.dismiss();

            }
        });
        filelayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Intent intent = new Intent();
                intent.setType("*/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, AppConstants.ANY_TYPE_FILE_REQUEST_CODE);

            }
        });

        dialog.show();
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {

            public void onCancel(DialogInterface dialog) {
            }
        });


    }

    private void requestCameraPermission(final int type) {
        try {
            Dexter.withActivity(this)
                    .withPermissions(android.Manifest.permission.CAMERA,
                            android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    // Manifest.permission.RECORD_AUDIO)
                    .withListener(new MultiplePermissionsListener() {
                        @Override
                        public void onPermissionsChecked(MultiplePermissionsReport report) {
                            if (report.areAllPermissionsGranted()) {

                                if (type == MEDIA_TYPE_IMAGE) {
                                    // capture picture
                                    captureImage();
                                } else {
                                    // captureVideo();
                                }

                            } else if (report.isAnyPermissionPermanentlyDenied()) {
                                showPermissionsAlert();
                            }
                        }

                        @Override
                        public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                            token.continuePermissionRequest();
                        }
                    }).check();
        } catch (Exception e) {
            e.getMessage();
        }


    }

    private void showPermissionsAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Permissions required!")
                .setMessage("Camera needs few permissions to work properly. Grant them in settings.")
                .setPositiveButton("GOTO SETTINGS", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        CameraUtils.openSettings(MonthWiseFeedbackActivityNew2.this);
                    }
                })
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
    }

    private void captureImage() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        File file = CameraUtils.getOutputMediaFile(MEDIA_TYPE_IMAGE);
        if (file != null) {
            imageStoragePath = file.getAbsolutePath();
        }

        Uri fileUri = CameraUtils.getOutputMediaFileUri(getApplicationContext(), file);

        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

        // start the image capture Intent
        startActivityForResult(intent, AppConstants.CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == AppConstants.GALLERY_CAPTURE_IMAGE_REQUEST_CODE) {
                if (data != null) {
                    //The array list has the image paths of the selected images
                    ArrayList<Image> images = data.getParcelableArrayListExtra(com.darsh.multipleimageselect.helpers.Constants.INTENT_EXTRA_IMAGES);
                    for (int i = 0; i < images.size(); i++) {
                        list_path.add(new ImagePath(String.valueOf(System.currentTimeMillis()), images.get(i).path));
                        // path = images.get(0).path;
                    }
                    //     multipartImageUpload();
                }
            } else if (requestCode == AppConstants.CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {

                path = imageStoragePath;
                list_path.add(new ImagePath(String.valueOf(System.currentTimeMillis()), path));

            } else if (requestCode == AppConstants.ANY_TYPE_FILE_REQUEST_CODE) {
                Uri videoUri = data.getData();
                Log.e("File URI---", videoUri.toString());

                try {
                    path = Utility.getPath(this, videoUri);
                    if (path != null) {
                        Log.e("File Path---", path);
                        list_path.add(new ImagePath(String.valueOf(System.currentTimeMillis()), path));
                    } else {
                        Log.e("File Path---", null);
                        Toast.makeText(getApplicationContext(), "Unable to add this file!", Toast.LENGTH_SHORT).show();

                    }
                } catch (Exception e) {
                    e.getCause();
                }


            }


            mAdapter = new MoviesAdapter(MonthWiseFeedbackActivityNew2.this);
            recyclerView.setAdapter(mAdapter);
        }

    }









    private void multipartImageUpload() {
        if (Count_Image_Uploaded < list_path.size())
            Count_Image_Uploaded++;
        uploadProgressDialog = new ProgressDialog(MonthWiseFeedbackActivityNew2.this);
        uploadProgressDialog.setMessage("Uploading " + Count_Image_Uploaded + "/" + list_path.size() + ", Please wait..");
        uploadProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        uploadProgressDialog.setIndeterminate(false);
        uploadProgressDialog.setProgress(0);
        uploadProgressDialog.setMax(100);
        uploadProgressDialog.setCancelable(false);
        uploadProgressDialog.show();

        initRetrofitClient();

        try {
            MultipartBody.Part[] surveyImagesParts = new MultipartBody.Part[list_path.size()];
            for (int index = 0; index < list_path.size(); index++) {
                File file = new File(list_path.get(index).getPath());
                ProgressRequestBody surveyBody = new ProgressRequestBody(file, this);
                surveyImagesParts[index] = MultipartBody.Part.createFormData("file[]", file.getName(), surveyBody);
                long Size = file.length();
                totalSize = totalSize + Size;

            }

            String patientId = UTIL.getPref(myContext, UTIL.Key_UserId);
         //   String url = UTIL.Domain_DCDC+"dcdc_web_service/upload_feedback_files_api.php?" + "p_id=" + patientId + "&branch_id=" + branchId;

            String url = UTIL.Domain_DCDC + UTIL.Upload_Feedback_Files_API + "p_id=" + patientId + "&branch_id=" + branchId;
          //  String url = UTIL.Domain_DCDC + "dcdc_web_service/upload_file.php";
            Call<ResponseBody> req = apiService.uploadMedia(surveyImagesParts, url);
            req.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    //  list_UploadImageName.clear();
                    String result = "";
                    Count_Image_Uploaded = 0;
                    if (String.valueOf(response.code()).equals("200")) {
                        try {
                            uploadProgressDialog.setProgress(100);
                            String responseStr = response.body().string();
                            JSONObject jsonObject = new JSONObject(responseStr);
                            String status = jsonObject.getString("status");


                            if (status.equals("1")) {
                               // JSONArray jsonArray = jsonObject.getJSONArray("files");
                                 feedback_files = jsonObject.getString("filename");




                              /*  for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                    String name = jsonObject1.getString("name");
                                    list_UploadImageName.add(name);
                                }*/

                              /*  String res = jsonArray.toString();
                                res = res.replaceAll("\"", "");
                                feedback_files = res.substring(res.indexOf("[") + 1, res.indexOf("]"));

                                String imagesIds[] = res.split(",");
                                //  list_UploadImageName = new ArrayList<String>(Arrays.asList(imagesIds));
*/

                                callSubmitFeedback();
                            } else {
                                Toast.makeText(getApplicationContext(), "Failed!", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            e.getMessage();

                        }


                    } else {
                        Toast.makeText(getApplicationContext(), "Failed!", Toast.LENGTH_SHORT).show();
                        //    dialog_photo_upload_failed();

                    }
                    try {
                        if (uploadProgressDialog.isShowing())
                            uploadProgressDialog.dismiss();
                    } catch (Exception e) {
                        e.getMessage();
                    }

                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    try {
                        if (uploadProgressDialog.isShowing())
                            uploadProgressDialog.dismiss();
                    } catch (Exception e) {
                        e.getMessage();
                    }
                    Count_Image_Uploaded = 0;
                    Toast.makeText(getApplicationContext(), "failed!", Toast.LENGTH_SHORT).show();
                    t.printStackTrace();

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initRetrofitClient() {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(100, TimeUnit.SECONDS)
                .readTimeout(100, TimeUnit.SECONDS)
                .writeTimeout(1000, TimeUnit.SECONDS)
                .build();
        try {
            apiService = new Retrofit.Builder().baseUrl(UTIL.Domain_DCDC).client(client).build().create(ApiService.class);
        } catch (Exception e) {
            e.getMessage();
        }
    }

    @Override
    public void onProgressUpdate(int percentage) {
        // textView.setText(percentage + "%");
        uploadProgressDialog.setProgress(percentage);
    }

    @Override
    public void onError() {
        //  textView.setText("Uploaded Failed!");
    }

    @Override
    public void onFinish() {
        if (Count_Image_Uploaded < list_path.size())
            Count_Image_Uploaded++;
        uploadProgressDialog.setMessage("Uploading " + Count_Image_Uploaded + "/" + list_path.size() + ", Please wait..");

    }

    @Override
    public void uploadStart() {
        //  textView.setText("0%");
    }

    private void callSubmitFeedback() {

        /*str_feedbackDate = et_AppointmentDate.getText().toString().trim();
        str_remarks = description.getText().toString().trim();
        str_environment = environment.getText().toString().trim();
        str_cleanliness = cleanliness.getText().toString().trim();
        str_staff_behave = staff_behave.getText().toString().trim();
        str_Procedure = Procedure.getText().toString().trim();
        str_DialysisStarted = DialysisStarted.getText().toString().trim();
        str_DialysisReceived = DialysisReceived.getText().toString().trim();

        if (str_feedbackDate.length() == 0) {
            Toast.makeText(getApplicationContext(), "Select feedback date!", Toast.LENGTH_SHORT).show();
        } else if (str_remarks.length() == 0) {
            Toast.makeText(getApplicationContext(), "Please enter remarks!", Toast.LENGTH_SHORT).show();
        } else if (str_environment.length() == 0) {
            Toast.makeText(getApplicationContext(), "Please enter rating for environment!", Toast.LENGTH_SHORT).show();
        } else if (str_cleanliness.length() == 0) {
            Toast.makeText(getApplicationContext(), "Please enter rating for cleanliness!", Toast.LENGTH_SHORT).show();
        } else if (str_staff_behave.length() == 0) {
            Toast.makeText(getApplicationContext(), "Please enter rating for staff behaviour!", Toast.LENGTH_SHORT).show();
        } else if (str_Procedure.length() == 0) {
            Toast.makeText(getApplicationContext(), "Please enter rating for procedure explained!", Toast.LENGTH_SHORT).show();
        } else if (str_DialysisStarted.length() == 0) {
            Toast.makeText(getApplicationContext(), "Please enter rating for dialysis started in time !", Toast.LENGTH_SHORT).show();
        } else if (str_DialysisReceived.length() == 0) {
            Toast.makeText(getApplicationContext(), "Please enter rating for dialysis received on time!", Toast.LENGTH_SHORT).show();
        } else if (list_UploadImageName.size() < 1) {
            Toast.makeText(getApplicationContext(), "Please select images!", Toast.LENGTH_SHORT).show();
        } else if (list_UploadImageName.size() > 2) {
            Toast.makeText(getApplicationContext(), "Please select only 2 images!", Toast.LENGTH_SHORT).show();
        } else {*/
        if (new ConnectionDetector(MonthWiseFeedbackActivityNew2.this).isConnectingToInternet()) {
            submitFeedback();
        } else {
            Toast.makeText(MonthWiseFeedbackActivityNew2.this, "No internet", Toast.LENGTH_SHORT).show();
        }
        //  }
    }

    public void submitFeedback(
    ) {
        String URL = UTIL.Domain_DCDC + UTIL.Add_monthly_feedback_API;
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
                                Toast.makeText(MonthWiseFeedbackActivityNew2.this, "Thank you for your feedback!", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(MonthWiseFeedbackActivityNew2.this, msg, Toast.LENGTH_SHORT).show();
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

                String patientId = UTIL.getPref(MonthWiseFeedbackActivityNew2.this, UTIL.Key_UserId);
                Map<String, String> params = new HashMap<String, String>();
                params.put("p_id", patientId);
                params.put("branch_id", branchId);//change here
                //params.put("branch_id", branchId);//change here
                params.put("environment", str_environment);
                params.put("cleanliness", str_cleanliness);
                params.put("staff_behav", str_staff_behave);
                params.put("procedures", str_Procedure);
                params.put("ds_on_time", str_DialysisStarted);
                params.put("overall_score", str_DialysisReceived);
                params.put("remarks", str_remarks);
                params.put("feed_date", str_feedbackDate);
                params.put("feedback_files", feedback_files);


                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(MonthWiseFeedbackActivityNew2.this);
        queue.add(postRequest);
    }

    private boolean ValidateFields() {

    //    str_feedbackDate = et_AppointmentDate.getText().toString().trim();
        str_feedbackDate=Utility.getCurrentTimeString("dd-MM-yyyy");
        str_remarks = description.getText().toString().trim();
        str_environment = environment.getText().toString().trim();
        str_cleanliness = cleanliness.getText().toString().trim();
        str_staff_behave = staff_behave.getText().toString().trim();
        str_Procedure = Procedure.getText().toString().trim();
        str_DialysisStarted = DialysisStarted.getText().toString().trim();
        str_DialysisReceived = DialysisReceived.getText().toString().trim();




        if (str_environment.length() == 0) {
            Toast.makeText(getApplicationContext(), "Please enter valid rating for environment!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (str_cleanliness.length() == 0) {
            Toast.makeText(getApplicationContext(), "Please enter valid rating for cleanliness!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (str_staff_behave.length() == 0) {
            Toast.makeText(getApplicationContext(), "Please enter valid rating for staff behaviour!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (str_Procedure.length() == 0) {
            Toast.makeText(getApplicationContext(), "Please enter valid rating for procedure explained!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (str_DialysisStarted.length() == 0) {
            Toast.makeText(getApplicationContext(), "Please enter valid rating for dialysis started on time !", Toast.LENGTH_SHORT).show();
            return false;
        } else if (str_DialysisReceived.length() == 0) {
            Toast.makeText(getApplicationContext(), "Please enter valid rating for dialysis received on time!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (str_feedbackDate.length() == 0) {
            Toast.makeText(getApplicationContext(), "Select feedback date!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (str_remarks.length() == 0) {
            Toast.makeText(getApplicationContext(), "Please enter remarks!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (list_path.size() < 1) {
            Toast.makeText(getApplicationContext(), "Please add images!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (list_path.size() > 2) {
            Toast.makeText(getApplicationContext(), "Please select only 2 images!", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MyViewHolder> {
        // private List<String> moviesList;

        public MoviesAdapter(Activity context) {
            //    this.moviesList = moviesList;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.row_feedback_image, parent, false);
            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {
            //String image_Uri = moviesList.get(position);
            String image_Uri = list_path.get(position).getPath();

            holder.cross.setId(position);


            String fileExt = "";
            if (image_Uri.contains(".")) {
                fileExt = image_Uri.substring(image_Uri.lastIndexOf("."));
            }

            boolean isImage = Arrays.asList(Utility.imgExt).contains(fileExt);
            boolean isDoc = Arrays.asList(Utility.docExt).contains(fileExt);
            boolean isMedia = Arrays.asList(Utility.mediaExt).contains(fileExt);
            boolean isWord = Arrays.asList(Utility.wordExt).contains(fileExt);
            boolean isPdf = Arrays.asList(Utility.pdfExt).contains(fileExt);
            boolean isExcel = Arrays.asList(Utility.excelExt).contains(fileExt);
            boolean isText = Arrays.asList(Utility.txtExt).contains(fileExt);
            if (isImage) {
                Glide
                        .with(MonthWiseFeedbackActivityNew2.this)
                        .load(new File(image_Uri))     // Uri of the picture
                        .into(holder.img);

            } else if (isWord) {

                holder.img.setImageResource(R.drawable.doc);
            } else if (isPdf) {

                holder.img.setImageResource(R.drawable.pdf);
            } else if (isExcel) {

                holder.img.setImageResource(R.drawable.excel);
            } else if (isText) {

                holder.img.setImageResource(R.drawable.txt_file_icon);
            }


            holder.cross.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    list_path.remove(position);
                    mAdapter = new MoviesAdapter(MonthWiseFeedbackActivityNew2.this);
                    recyclerView.setAdapter(mAdapter);
                }
            });
        }

        @Override
        public int getItemCount() {
            //  return moviesList.size();
            return list_path.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {

            ImageView img;
            LinearLayout cross;

            public MyViewHolder(View convertview) {
                super(convertview);
                img = convertview.findViewById(R.id.img);
                cross = convertview.findViewById(R.id.cross);
            }
        }
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(MonthWiseFeedbackActivityNew2.this, ActivityWithNavigationMenuPatient.class);
        startActivity(i);
        finish();
    }
}
