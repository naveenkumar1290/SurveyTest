package com.cs.nks.easycouriers.dcdc.patient;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cs.nks.easycouriers.R;
import com.cs.nks.easycouriers.model.BranchLocation;
import com.cs.nks.easycouriers.model.DamageDetail;
import com.cs.nks.easycouriers.place_api.common.activities.SampleActivityBase_New;
import com.cs.nks.easycouriers.retrofit_multipart.ProgressRequestBody;
import com.cs.nks.easycouriers.util.AppConstants;
import com.cs.nks.easycouriers.util.CameraUtils;
import com.cs.nks.easycouriers.util.Utility;
import com.darsh.multipleimageselect.activities.AlbumSelectActivity;
import com.darsh.multipleimageselect.models.Image;
import com.wdullaer.materialdatetimepicker.Utils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;


public class DamageReportNew extends SampleActivityBase_New implements ProgressRequestBody.UploadCallbacks{


    public static final int MEDIA_TYPE_IMAGE = 1;
    private static String imageStoragePath;
    Adapter_DamageReport userAdapter;
    ArrayList<String> list_path = new ArrayList<>();

    ProgressDialog uploadProgressDialog;
    int Count_Image_Uploaded = 0;
    long totalSize = 0;
    AlertDialog alertDialog;
    private Context mContext;
    private RecyclerView recycler;
    AlertDialog alertDialog1;

    String text="";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.damage_report_new, container, false);
        getActivity().setTitle("Report Bonded Labor");
        mContext =  getActivity();
        init( rootView);
        setToolbar();
        return rootView;

    }
    private void init(View rootView) {
        ArrayList<DamageDetail> damageDetailList = new ArrayList<>();
        damageDetailList.add(DamageDetail.getInitDamageDetail());
        recycler = rootView.findViewById(R.id.recycler);
        recycler.setLayoutManager(new LinearLayoutManager(mContext));

        userAdapter = new Adapter_DamageReport(mContext, damageDetailList,this);
        recycler.setAdapter(userAdapter);
        Button btn_add = rootView.findViewById(R.id.btn_AddItem);
        Button btn_getData = rootView.findViewById(R.id.btn_FinishedWithReport);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userAdapter.addItemToList();
            }
        });
        btn_getData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<DamageDetail> list = userAdapter.returnData();
                if (list != null) {
                    PostDamageReport(list);
                }

            }
        });

    }

    private void PostDamageReport(ArrayList<DamageDetail> listdamageDetail) {
      /*  JSONArray jsonArray = new JSONArray();
        for (DamageDetail damageDetail : listdamageDetail) {
            try {
                JSONObject jsonObject_Input = new JSONObject();
                jsonObject_Input.put("swo_id", Shared_Preference.getSWO_ID(this));
                jsonObject_Input.put("emp_id", Shared_Preference.getLOGIN_USER_ID(this));
                jsonObject_Input.put("desc", "Damage Report to " + ItemType.getItemTypeByPosition(damageDetail.getSpinnerSelectPos()) + ": " + damageDetail.getItemDesc()
                        + ": " + damageDetail.getDamageDesc());
                jsonObject_Input.put("fname", damageDetail.getUploadedPhotoUrl());
                if (Shared_Preference.get_EnterTimesheetByAWO(DamageReportNew.this)) {
                    jsonObject_Input.put("Type", "2");
                } else {
                    jsonObject_Input.put("Type", "1");
                }
                JSONObject jsonObject1 = new JSONObject();
                jsonObject1.put(Api.API_add_item_descwithFileByType, jsonObject_Input);
                jsonArray.put(jsonObject1);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        if (new ConnectionDetector(DamageReportNew.this).isConnectingToInternet()) {
            new MyAsyncTask_MultiApiCall(this, this, jsonArray).execute();
        } else {
            Toast.makeText(DamageReportNew.this, Utility.NO_INTERNET, Toast.LENGTH_LONG).show();
        }*/
    }


    private void setToolbar() {
       /* TextView txtvw_CompanyName = (TextView) findViewById(R.id.textView1);
        ImageView imgvw_CompanyImg = (ImageView) findViewById(R.id.missing);
        String nam = Shared_Preference.getCLIENT_NAME(this);
        String imageloc = Shared_Preference.getCLIENT_IMAGE_LOGO_URL(this);
        if (imageloc.equals("") || imageloc.equalsIgnoreCase("")) {
            Shared_Preference.setCLIENT_IMAGE_LOGO_URL(this, "");
            imgvw_CompanyImg.setVisibility(View.GONE);
            txtvw_CompanyName.setText(nam);
        } else {
            Glide
                    .with(mContext)
                    .load(imageloc)     // Uri of the picture
                    .into(imgvw_CompanyImg);


        }
        ImageView img_Home = (ImageView) findViewById(R.id.homeacti);
        img_Home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();
            }
        });*/
    }

     public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            list_path.clear();
            if (requestCode == AppConstants.GALLERY_CAPTURE_IMAGE_REQUEST_CODE) {
                if (data != null) {
                    ArrayList<Image> images = data.getParcelableArrayListExtra(com.darsh.multipleimageselect.helpers.Constants.INTENT_EXTRA_IMAGES);
                    for (int i = 0; i < images.size(); i++) {
                        list_path.add(images.get(i).path);
                    }
                   // multipartImageUpload();
                } else {
                    File file1 = null;
                    Uri mImageCaptureUri = FileProvider.getUriForFile(mContext, mContext.getApplicationContext().getPackageName() + ".provider", file1);
                    try {
                        list_path.add(Utility.getPath(mImageCaptureUri,
                                getActivity()));
                       // multipartImageUpload();

                    } catch (Exception e) {
                        list_path.add(mImageCaptureUri.getPath());
                       // multipartImageUpload();
                    }
                }


            } else if (requestCode == AppConstants.CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
                int orientation = Utility.getExifOrientation(imageStoragePath);
                try {
                    if (orientation == 90) {
                        //   list_TempImagePath.add(path);
                        Bitmap bmp = Utility.getRotatedBitmap(imageStoragePath, 90);
                        imageStoragePath = Utility.saveImage(bmp);
                    }
                } catch (Exception e) {
                    e.getCause();
                }
                list_path.add(imageStoragePath);
              //  multipartImageUpload();
            }
        }

    }
/*
    private void multipartImageUpload() {
        final ArrayList<String> list_UploadImageName = new ArrayList<>();

        if (Count_Image_Uploaded < list_path.size())
            Count_Image_Uploaded++;
        uploadProgressDialog = new ProgressDialog(mContext);
        // uploadProgressDialog.setMessage("Uploading , Please wait..");
        uploadProgressDialog.setMessage("Uploading " + Count_Image_Uploaded + "/" + list_path.size() + ", Please wait..");
        uploadProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        uploadProgressDialog.setIndeterminate(false);
        uploadProgressDialog.setProgress(0);
        uploadProgressDialog.setMax(100);
        uploadProgressDialog.setCancelable(false);
        uploadProgressDialog.show();

        totalSize = 0;

        try {
            *//**//*
            MultipartBody.Part[] surveyImagesParts = new MultipartBody.Part[list_path.size()];
            for (int index = 0; index < list_path.size(); index++) {

                String path = list_path.get(index);
                File file = new File(path);
                ProgressRequestBody surveyBody = new ProgressRequestBody(file, this);
                surveyImagesParts[index] = MultipartBody.Part.createFormData("images[]", file.getName(), surveyBody);
                long Size = file.length();
                totalSize = totalSize + Size;

            }

            String jid = Shared_Preference.getJOB_ID_FOR_JOBFILES(this);
            String url = URL_EP2 + "/UploadFileHandler.ashx?jid=" + jid;
            *//**//*

            API_Interface APIInterface = REST_API_Client.getClient().create(API_Interface.class);

            Call<ResponseBody> req = APIInterface.uploadMedia(surveyImagesParts, url);
            req.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    Count_Image_Uploaded = 0;
                    if (String.valueOf(response.code()).equals("200")) {
                        try {
                            uploadProgressDialog.setProgress(100);
                            String responseStr = response.body().string();
                            if (!responseStr.contains("api_error")) {
                                if (responseStr.contains(",")) {
                                    String s[] = responseStr.split(",");
                                    List<String> stringList = new ArrayList<String>(Arrays.asList(s));
                                    list_UploadImageName.clear();
                                    list_UploadImageName.addAll(stringList);
                                } else {
                                    list_UploadImageName.add(responseStr);
                                }
                                //only one image will be use in api
                                if (list_UploadImageName.size() > 0) {
                                    userAdapter.setUploadedImageURL(list_UploadImageName.get(0));
                                } else {
                                    Toast.makeText(mContext, "No url found!", Toast.LENGTH_SHORT).show();
                                }

                            } else {
                                Toast.makeText(mContext, "Uploading failed!", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            e.getMessage();

                        }

                    }


                    for (int i = 0; i < list_path.size(); i++) {
                        String TempImagePath = list_path.get(i);
                        if (TempImagePath.contains("TempImage-")) {
                            Utility.delete(TempImagePath);
                        }
                    }

                    list_path.clear();

                    try {
                        uploadProgressDialog.dismiss();
                    } catch (Exception e) {
                        e.getMessage();
                    }

                    if (String.valueOf(response.code()).equals("200")) {
                        Toast.makeText(getApplicationContext(), "Photo uploaded successfully!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), " Upload Failed!", Toast.LENGTH_SHORT).show();
                        dialog_photo_upload_failed();
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
                    list_path.clear();
                    Toast.makeText(getApplicationContext(), "Upload failed!", Toast.LENGTH_SHORT).show();
                    t.printStackTrace();
                    dialog_photo_upload_failed();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

    public void opendilogforattachfileandimage() {
        final Dialog dialog = new Dialog(mContext);
        dialog.setContentView(R.layout.openattachmentdilog_new);

        dialog.getWindow().setBackgroundDrawable(
                new ColorDrawable(android.graphics.Color.TRANSPARENT));

        LinearLayout cameralayout = (LinearLayout) dialog
                .findViewById(R.id.cameralayout);
        LinearLayout gallarylayout = (LinearLayout) dialog
                .findViewById(R.id.gallarylayout);
        LinearLayout filelayout = (LinearLayout) dialog
                .findViewById(R.id.filelayout);
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

                if (CameraUtils.checkPermissions(mContext)) {

                    captureImage();

                } else {
                    Toast.makeText(mContext, "Camera & Write_External_Storage permission are disabled!", Toast.LENGTH_SHORT).show();
                }


            }
        });
        gallarylayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, AlbumSelectActivity.class);
                intent.putExtra(com.darsh.multipleimageselect.helpers.Constants.INTENT_EXTRA_LIMIT, 1);
              //  if (mContext instanceof DamageReportNew) {
                  getActivity().startActivityForResult(intent, AppConstants.GALLERY_CAPTURE_IMAGE_REQUEST_CODE);
              //  }
                dialog.dismiss();
            }
        });
        dialog.show();
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            public void onCancel(DialogInterface dialog) {
                // finish();
            }
        });
    }

    private void captureImage() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File file = CameraUtils.getOutputMediaFile(MEDIA_TYPE_IMAGE);
        if (file != null) {
            imageStoragePath = file.getAbsolutePath();
        }
        Uri fileUri = CameraUtils.getOutputMediaFileUri(mContext, file);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        //if (mContext instanceof DamageReportNew) {
            getActivity().startActivityForResult(intent, AppConstants.CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
       // }

    }

/*    private void dialog_photo_upload_failed() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(mContext);
        LayoutInflater inflater = LayoutInflater.from(mContext);
        final View dialogView = inflater.inflate(R.layout.dialog_yes_no, null);
        dialogView.setBackgroundDrawable(
                new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialogBuilder.setView(dialogView);
        final TextView title = dialogView.findViewById(R.id.textView1rr);
        final TextView message = dialogView.findViewById(R.id.texrtdesc);

        final Button positiveBtn = dialogView.findViewById(R.id.Btn_Yes);
        final Button negativeBtn = dialogView.findViewById(R.id.Btn_No);
        ImageView close = (ImageView) dialogView.findViewById(R.id.close);
        close.setVisibility(View.INVISIBLE);

        title.setText("Failed!");
        message.setText("Image(s) not uploaded!");
        positiveBtn.setText("Ok");
        negativeBtn.setText("No");
        negativeBtn.setVisibility(View.GONE);
        positiveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
        negativeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
        alertDialog = dialogBuilder.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setCancelable(false);
        alertDialog.show();
    }*/

    public void ScrollRecyclerviewToBottom(int height) {
        recycler.smoothScrollToPosition(height);
    }

    @Override
    public void onProgressUpdate(int percentage) {
        // textView.setText(percentage + "%");
        uploadProgressDialog.setProgress(percentage);
    }

    @Override
    public void onError() {

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

 /*   @Override
    public void handleResponse(JSONObject responseJsonObject) {
        Iterator iterator = responseJsonObject.keys();
        String result = "";
        while (iterator.hasNext()) {
            try {
                String Api = (String) iterator.next();
                String Response = responseJsonObject.getString(Api);
                JSONObject jsonObject = new JSONObject(Response);
                String str = jsonObject.getString("cds");
                if (str.contains("=")) {
                    String[] result_arr = str.split("=");
                    result = result_arr[1];
                }
            } catch (Exception e) {
                e.getMessage();
            }

            if (result.equals("1")) {
                Toast.makeText(DamageReportNew.this, "Report submitted successfully !", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(DamageReportNew.this, "Report submitting failed!", Toast.LENGTH_SHORT).show();
            }
            finish();
        }
    }*/


    public void showDialogNew(String dialog_title, final Context context, ArrayList<BranchLocation> list, final int Adapetr_position, final int type) {


        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.custom_dialog, null);
        dialogBuilder.setView(dialogView);

        final EditText autoText_TimeZone = (EditText) dialogView.findViewById(R.id.autoText_TimeZone);
        final ListView listvw = (ListView) dialogView.findViewById(R.id.listview);


        ArrayAdapter<BranchLocation> adapter = new ArrayAdapter<BranchLocation>(context,
                android.R.layout.simple_list_item_1, android.R.id.text1, list);
        listvw.setAdapter(adapter);

        listvw.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                BranchLocation spinner_ = (BranchLocation) listvw.getItemAtPosition(position);
               String text = spinner_.get_text();
               String Branch_id = spinner_.get_id();
              //  editText.setText(txt);
                userAdapter. setData(type,Adapetr_position,text);

                try {
                    alertDialog1.dismiss();
                } catch (Exception e) {
                    e.getMessage();
                }


            }
        });


        dialogBuilder.setTitle(dialog_title);

        alertDialog1 = dialogBuilder.create();
        if (!alertDialog1.isShowing()) {
            alertDialog1.show();
        }

    }

}