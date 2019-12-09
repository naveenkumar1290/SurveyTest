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
import android.support.v4.view.ViewPager;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.cs.nks.easycouriers.R;
import com.cs.nks.easycouriers.model.feedback;
import com.cs.nks.easycouriers.retrofit_multipart.ApiService;
import com.cs.nks.easycouriers.retrofit_multipart.ProgressRequestBody;
import com.cs.nks.easycouriers.util.AppConstants;
import com.cs.nks.easycouriers.util.CameraUtils;
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

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
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
public class MonthWiseFeedbackActivityNew extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, ProgressRequestBody.UploadCallbacks {

    private static String imageStoragePath;
    Context myContext;
    UTIL utill;
    Timer timer;
    EditText et_AppointmentDate, description;
    ArrayList<feedback> list_ClientUser = new ArrayList<>();
    String path;
    ProgressDialog uploadProgressDialog;
    int Count_Image_Uploaded = 0;
    ApiService apiService;
    ArrayList<String> list_path = new ArrayList<>();
    long totalSize = 0;
    // TextView tv_msg;
    private int currentPage = 0;
    private ViewPager mViewPager;
    private RecyclerView recyclerView;
    private MoviesAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle("Feedback");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setContentView(R.layout.activity_month_wise_feedback);
        myContext = MonthWiseFeedbackActivityNew.this;
        setView();
    }


    private void setView() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        //  tv_msg = findViewById(R.id.txt);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(myContext);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        list_ClientUser.add(new feedback("1", "Environment", false, true, false, false, false));
        list_ClientUser.add(new feedback("2", "Cleanliness", false, true, false, false, false));
        list_ClientUser.add(new feedback("3", "Staff Behaviour", false, true, false, false, false));


        list_ClientUser.add(new feedback("4", "Procedure Explained", false, true, false, false, false));
        list_ClientUser.add(new feedback("5", "Dialysis Started On Time", false, true, false, false, false));
        list_ClientUser.add(new feedback("6", "Dialysis Received for 4 hrs", false, true, false, false, false));

        mAdapter = new MoviesAdapter(MonthWiseFeedbackActivityNew.this, list_ClientUser);
        recyclerView.setAdapter(mAdapter);


        et_AppointmentDate = (EditText) findViewById(R.id.et_AppointmentDate);
        description = (EditText) findViewById(R.id.description);
        et_AppointmentDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Click_getDate();
            }
        });

        RadioGroup radio = findViewById(R.id.radio);
        RadioButton rb_active = findViewById(R.id.radioActive);
        RadioButton rb_inactive = findViewById(R.id.radioInactive);
        // radio.getCheckedRadioButtonId();
        if (rb_active.isChecked()) {

        }
        Button btn = findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog_attach_file();
            }
        });

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

            multipartImageUpload();


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
                MonthWiseFeedbackActivityNew.this,
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
        dpd.show(MonthWiseFeedbackActivityNew.this.getFragmentManager(), "dialog");


    }

    public void dialog_attach_file() {
        final Dialog dialog = new Dialog(MonthWiseFeedbackActivityNew.this);
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
                Intent intent = new Intent(MonthWiseFeedbackActivityNew.this, AlbumSelectActivity.class);
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
                        CameraUtils.openSettings(MonthWiseFeedbackActivityNew.this);
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
                        list_path.add(images.get(i).path);
                        // path = images.get(0).path;

                    }

                }
            } else if (requestCode == AppConstants.CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {

                path = imageStoragePath;
                list_path.add(path);

            } else if (requestCode == AppConstants.ANY_TYPE_FILE_REQUEST_CODE) {
                Uri videoUri = data.getData();
                Log.e("File URI---", videoUri.toString());

                try {
                    path = Utility.getPath(this, videoUri);
                    if (path != null) {
                        Log.e("File Path---", path);
                        list_path.add(path);
                    } else {
                        Log.e("File Path---", null);
                        Toast.makeText(getApplicationContext(), "Unable to add this file!", Toast.LENGTH_SHORT).show();

                    }
                } catch (Exception e) {
                    e.getCause();
                }


            }
        }

    }

    private void multipartImageUpload() {
        if (Count_Image_Uploaded < list_path.size())
            Count_Image_Uploaded++;
        uploadProgressDialog = new ProgressDialog(MonthWiseFeedbackActivityNew.this);
        // uploadProgressDialog.setMessage("Uploading , Please wait..");
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
                File file = new File(list_path.get(index));
                ProgressRequestBody surveyBody = new ProgressRequestBody(file, this);
                surveyImagesParts[index] = MultipartBody.Part.createFormData("file[]", file.getName(), surveyBody);
                long Size = file.length();
                totalSize = totalSize + Size;
            }


            String patientId = UTIL.getPref(myContext, UTIL.Key_UserId);
          //  String url = "http://dcdc.businesstowork.com/dcdc_web_service/upload_feedback_files_api.php?p_id=" + patientId + "&branch_id=" + "1";
            String url = UTIL.Domain_DCDC + UTIL.Upload_Feedback_Files_API+"p_id=" + patientId + "&branch_id=" + "1";

            Call<ResponseBody> req = apiService.uploadMedia(surveyImagesParts, url);
            req.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    String result = "";
                    Count_Image_Uploaded = 0;


                    if (String.valueOf(response.code()).equals("200")) {
                        try {
                            uploadProgressDialog.setProgress(100);
                            String responseStr = response.body().string();
                            if (!responseStr.contains("api_error")) {
                                String s[] = responseStr.split(",");
                                List<String> stringList = new ArrayList<String>(Arrays.asList(s));
                                ArrayList<String> list_UploadImageName = new ArrayList<String>(stringList);

                                result = "1";
                            } else {
                                result = "0";
                            }
                        } catch (Exception e) {
                            e.getMessage();
                            result = "0";
                        }
                        if (result.equalsIgnoreCase("1")) {
                            //new async_LinkUploadedFiles().execute();
                        }

                    } else {
                        Toast.makeText(getApplicationContext(), " Upload Failed!", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(getApplicationContext(), "Upload failed!", Toast.LENGTH_SHORT).show();
                    t.printStackTrace();
                    //  dialog_photo_upload_failed();
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

    public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MyViewHolder> {
        private List<feedback> moviesList;
        //   private HttpImageManager mHttpImageManager;

        public MoviesAdapter(Activity context, List<feedback> moviesList) {
            this.moviesList = moviesList;
            //     mHttpImageManager = ((AppController) context.getApplication()).getHttpImageManager();
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.row_feedback, parent, false);

            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {
            feedback projectPhoto = moviesList.get(position);

            //   holder.index_no.setText(String.valueOf(position + 1));
            holder.txt.setText(projectPhoto.getText()); //date
            holder.its_okay.setChecked(projectPhoto.isOkay());//time
            holder.Good.setChecked(projectPhoto.isGud());//time
            holder.VeryGood.setChecked(projectPhoto.isVeryGud());//time
            holder.Bad.setChecked(projectPhoto.isBad());//time
            holder.VeryBad.setChecked(projectPhoto.isVeryBad());//time


            holder.its_okay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //  rating="It's Ok";
                    holder.Good.setChecked(false);
                    holder.VeryGood.setChecked(false);
                    holder.Bad.setChecked(false);
                    holder.VeryBad.setChecked(false);
                    moviesList.get(position).setOkay(true);
                }
            });
            holder.Good.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //  rating="Good";

                    holder.its_okay.setChecked(false);
                    holder.VeryGood.setChecked(false);
                    holder.Bad.setChecked(false);
                    holder.VeryBad.setChecked(false);
                    moviesList.get(position).setGud(true);
                }
            });

            holder.VeryGood.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //  rating="Very Good";
                    holder.its_okay.setChecked(false);
                    holder.Good.setChecked(false);
                    holder.Bad.setChecked(false);
                    holder.VeryBad.setChecked(false);
                    moviesList.get(position).setVeryGud(true);
                }
            });
            holder.Bad.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // rating="Bad";

                    holder.its_okay.setChecked(false);
                    holder.Good.setChecked(false);
                    holder.VeryGood.setChecked(false);
                    holder.VeryBad.setChecked(false);
                    moviesList.get(position).setBad(true);
                }
            });
            holder.VeryBad.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // rating="Very Bad";
                    holder.its_okay.setChecked(false);
                    holder.Good.setChecked(false);
                    holder.VeryGood.setChecked(false);
                    holder.Bad.setChecked(false);
                    moviesList.get(position).setVeryBad(true);
                }
            });

/*
            if (Descr == null || Descr.trim().equals("")) {
                holder.tv_status.setText("Not available");
            } else {
                holder.tv_status.setText(Descr);
            }*/

           /* holder.parentView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });*/
        }

        @Override
        public int getItemCount() {
            return moviesList.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            //TextView name, user_type, master, phone, email;
            //  ImageView imgvw_edit;
            //   Button index_no;
            // LinearLayout parentView;
            TextView txt;

            RadioButton its_okay, Good, VeryGood, Bad, VeryBad;

            public MyViewHolder(View convertview) {
                super(convertview);

                //    parentView =  convertview.findViewById(R.id.row_jobFile);


                its_okay = convertview.findViewById(R.id.its_okay_En);
                Good = convertview.findViewById(R.id.Good_En);
                VeryGood = convertview.findViewById(R.id.VeryGood_En);
                Bad = convertview.findViewById(R.id.Bad_En);
                VeryBad = convertview.findViewById(R.id.VeryBad_En);
                txt = convertview.findViewById(R.id.txt);
                //   index_no = (Button) convertview.findViewById(R.id.serial_no);

           /*     name = (TextView) convertview.findViewById(R.id.name);
               user_type = (TextView) convertview.findViewById(R.id.user_type);
                master = (TextView) convertview.findViewById(R.id.master);
            *//*  phone = (TextView) convertview.findViewById(R.id.phone);
               email = (TextView) convertview.findViewById(R.id.email);*//*
            imgvw_edit = (ImageView) convertview.findViewById(R.id.imgvw_edit);*/


            }
        }
    }

}
