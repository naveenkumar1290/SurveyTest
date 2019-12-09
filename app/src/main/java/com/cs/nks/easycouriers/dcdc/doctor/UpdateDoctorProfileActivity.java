package com.cs.nks.easycouriers.dcdc.doctor;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
import com.bumptech.glide.request.RequestOptions;
import com.cs.nks.easycouriers.R;
import com.cs.nks.easycouriers.model.ImagePath;
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

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static com.cs.nks.easycouriers.util.Utility.MEDIA_TYPE_IMAGE;

public class UpdateDoctorProfileActivity extends AppCompatActivity implements ProgressRequestBody.UploadCallbacks {
    private static String imageStoragePath;
    ImageView image;
    //  String path;
    int Count_Image_Uploaded = 0;
    ProgressDialog uploadProgressDialog;
    ArrayList<ImagePath> list_path = new ArrayList<>();
    long totalSize = 0;
    ApiService apiService;
    de.hdodenhof.circleimageview.CircleImageView profile_image;

    ArrayList<String> list_UploadImageName = new ArrayList<>();
    UTIL util;
    EditText et_first_name,
            et_last_name,
            et_mail,
            et_phone,
            et_address,
            et_city,
            et_pin;

    String branch_id,
            first_name,
            last_name,
            gender,
            contact_no,
            email,
            dob,
            profile_image_url,
            address,
            city,
            zip_code;
    Button btnUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_doctor_profile);

        setTitle("Update Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        util = new UTIL(UpdateDoctorProfileActivity.this);
        getIntentData();
        setView();

        setData();


    }

    private void setView() {
        et_first_name = findViewById(R.id.et_first_name);
        et_last_name = findViewById(R.id.et_last_name);
        et_mail = findViewById(R.id.et_mail);
        et_phone = findViewById(R.id.et_phone);
        et_address = findViewById(R.id.et_address);
        et_city = findViewById(R.id.et_city);
        et_pin = findViewById(R.id.et_pin);
        image = findViewById(R.id.image);
        profile_image = findViewById(R.id.profile_image);
        btnUpdate = findViewById(R.id.btnUpdate);


        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog_attach_file();
            }
        });


        btnUpdate.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (et_first_name.getText().toString().isEmpty()) {
                            Toast.makeText(getApplicationContext(), "Please enter first name!", Toast.LENGTH_SHORT).show();

                        } else if (et_mail.getText().toString().isEmpty() ||
                                !Utility.isValidEmail(et_mail.getText().toString().trim())
                        ) {
                            Toast.makeText(getApplicationContext(), "Please enter valid mail id!", Toast.LENGTH_SHORT).show();

                        } else if (et_phone.getText().toString().isEmpty()
                                || et_phone.getText().length() != 10
                        ) {
                            Toast.makeText(getApplicationContext(), "Please enter 10 digit mobile number!", Toast.LENGTH_SHORT).show();

                        } else if (et_address.getText().toString().isEmpty()) {
                            Toast.makeText(getApplicationContext(), "Please enter address!", Toast.LENGTH_SHORT).show();

                        } else if (et_city.getText().toString().isEmpty()) {
                            Toast.makeText(getApplicationContext(), "Please enter city name!", Toast.LENGTH_SHORT).show();

                        } else if (et_pin.getText().toString().isEmpty()) {
                            Toast.makeText(getApplicationContext(), "Please enter 6 digit zip code!", Toast.LENGTH_SHORT).show();

                        } else if (et_first_name.getText().toString().isEmpty()) {
                            Toast.makeText(getApplicationContext(), "Please enter first name!", Toast.LENGTH_SHORT).show();

                        } else {
                            UpdateApiCall();
                        }

                    }
                });
    }

    private void UpdateApiCall() {
        String URL = UTIL.Domain_DCDC + UTIL.Update_Profile_Update_API;
        final String doctorId = UTIL.getPref(UpdateDoctorProfileActivity.this, UTIL.Key_UserId);
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
                                Toast.makeText(UpdateDoctorProfileActivity.this, "Successful", Toast.LENGTH_SHORT).show();
                                onBackPressed();
                            } else {
                                Toast.makeText(UpdateDoctorProfileActivity.this, msg, Toast.LENGTH_SHORT).show();
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


                Map<String, String> params = new HashMap<String, String>();
                params.put("user_id", doctorId);
                params.put("first_name", et_first_name.getText().toString().trim());
                params.put("last_name", et_last_name.getText().toString().trim());
                params.put("profile_image", profile_image_url);
                params.put("contact_no", et_phone.getText().toString().trim());
                params.put("email", et_mail.getText().toString().trim());
                params.put("address", et_address.getText().toString().trim());
                params.put("city", et_city.getText().toString().trim());
                params.put("zip_code", et_pin.getText().toString().trim());
                return params;


            }
        };
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(postRequest);
    }

    private void getIntentData() {
        Bundle b = getIntent().getExtras();
        if (b != null) {


            branch_id = b.getString("branch_id");
            first_name = b.getString("first_name");
            last_name = b.getString("last_name");
            gender = b.getString("gender");
            contact_no = b.getString("contact_no");
            email = b.getString("email");
            dob = b.getString("dob");
            profile_image_url = b.getString("profile_image_url");
            address = b.getString("address");
            city = b.getString("city");
            zip_code = b.getString("zip_code");

        }
    }

    private void setData() {
        et_first_name.setText(first_name);
        et_last_name.setText(last_name);
        et_mail.setText(email);
        et_phone.setText(contact_no);
        et_address.setText(address);
        et_city.setText(city);
        et_pin.setText(zip_code);

        setProfileImage();


    }

    public void dialog_attach_file() {
        final Dialog dialog = new Dialog(UpdateDoctorProfileActivity.this);
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
        filelayout.setVisibility(View.GONE);
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
                list_path.clear();
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
                list_path.clear();
                Intent intent = new Intent(UpdateDoctorProfileActivity.this, AlbumSelectActivity.class);
                intent.putExtra(com.darsh.multipleimageselect.helpers.Constants.INTENT_EXTRA_LIMIT, 1);
                startActivityForResult(intent, AppConstants.GALLERY_CAPTURE_IMAGE_REQUEST_CODE);

                dialog.dismiss();

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
                        CameraUtils.openSettings(UpdateDoctorProfileActivity.this);
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
                        String path = images.get(0).path;

                    }
                    //     multipartImageUpload();
                }
            } else if (requestCode == AppConstants.CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {

                String path = imageStoragePath;
                list_path.add(new ImagePath(String.valueOf(System.currentTimeMillis()), path));

            }


            multipartImageUpload();
            //  setProfileImage();
        }
    }

    private void multipartImageUpload() {


        if (Count_Image_Uploaded < list_path.size())
            Count_Image_Uploaded++;
        uploadProgressDialog = new ProgressDialog(UpdateDoctorProfileActivity.this);
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
                surveyImagesParts[index] = MultipartBody.Part.createFormData("file", file.getName(), surveyBody);
                long Size = file.length();
                totalSize = totalSize + Size;

            }

            //String patientId = UTIL.getPref(UpdateDoctorProfileActivity.this, UTIL.Key_UserId);
            String url = UTIL.Domain_DCDC + "dcdc_web_service/upload_file.php";
        //    String url = "http://staging.ep2.businesstowork.com/UploadFileHandler.ashx?jid=14078";
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

                                String url = UTIL.Domain_DCDC + "dcdc_web_service" + jsonObject.getString("url");
                                profile_image_url = url;

                                /*  for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                    String name = jsonObject1.getString("name");
                                    list_UploadImageName.add(name);
                                }*/

                             /*   String res = jsonArray.toString();
                                res = res.replaceAll("\"", "");
                                String feedback_files = res.substring(res.indexOf("[") + 1, res.indexOf("]"));

                                String imagesIds[] = res.split(",");
                                list_UploadImageName = new ArrayList<String>(Arrays.asList(imagesIds));
                                if (list_UploadImageName.size() > 0) {
                                    profile_image_url = list_UploadImageName.get(0);
                                    setProfileImage();
                                }*/
                                Toast.makeText(getApplicationContext(), "Profile picture updated successfully!", Toast.LENGTH_SHORT).show();
                                //   callSubmitFeedback();
                                setProfileImage();
                                UpdateApiCall();
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

    private void setProfileImage() {

        Glide.with(this).
                load(profile_image_url)
                .apply(new RequestOptions()
                        .placeholder(R.drawable.ic_person_white_24dp)
                )
                .into(profile_image);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        finish();
        return true;
    }

}
