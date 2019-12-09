package com.cs.nks.easycouriers.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.TimeZone;



import static android.content.Context.MODE_PRIVATE;
import static android.content.Context.NOTIFICATION_SERVICE;
import static android.text.Spanned.SPAN_INCLUSIVE_INCLUSIVE;

/**
 * Created by Admin on 2/20/2017.
 */

public class Utility {

    public static final String TIMER_STARTED_FROM_ADMIN_CLOCK_MODULE = "TimerStartedFromAdminClockModule";

    public static final String DEALER_ID = "DEALER_ID";

    public static final String NO_INTERNET = "Lost Internet!";
    public static final String CLOCK_START_TIME = "CLOCK_START_TIME";

    public static final String TIMEGAP_JOB_START_TIME = "TIMEGAP_JOB_START_TIME";
    public static final String TIMEGAP_JOB_END_TIME = "TIMEGAP_JOB_END_TIME";

    public static final String KEY_INCOMPLETE_ASYNC_ARRAY = "INCOMPLETE_ASYNC_ARRAY";


    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;
    // Bitmap sampling size
    public static final int BITMAP_SAMPLE_SIZE = 8;
    // Gallery directory name to store the images or videos
    public static final String GALLERY_DIRECTORY_NAME = "Hello Camera";
    // Image and Video file extensions
    public static final String IMAGE_EXTENSION = "jpg";
    public static final String VIDEO_EXTENSION = "mp4";
    /**/

    private static final int NOTIFICATION_ID = 9083150;
    private static final String STR_DATE = "dd-MM-yyyy";
    private static final String STR_TIME = "HH:mm:ss";
    //public static final String DATE_FORMAT = "dd-MM-yyyy HH:mm:ss";
    private static final String DATE_FORMAT = STR_DATE + " " + STR_TIME;
    public static String imgExt[] = {".JPG", ".jpg", ".jpeg", ".JPEG", ".png", ".PNG", ".bmp",
            ".BMP", ".gif", ".GIF", ".webp", ".WEBP"};
    public static String docExt[] = {".doc", ".DOC", ".psd", ".PSD", ".docx", ".PSD",
            ".docx", ".DOCX", ".pdf", ".PDF", ".xlsx", ".XLSX", ".pptx"};
    public static String mediaExt[] = {".aac", ".AAC", ".m4a", ".M4A",
            ".mp4", ".MP4", ".3gp", ".3GP", ".m4b", ".M4B", ".mp3", ".MP3",
            ".wave", ".WAVE"};
    public static String wordExt[] = {".doc", ".DOC", ".psd", ".PSD", ".docx", ".PSD",
            ".docx", ".DOCX"};
    public static String pdfExt[] = {".pdf", ".PDF"};
    public static String txtExt[] = {".txt", ".TXT"};
    public static String excelExt[] = {".xlsx", ".XLSX", ".xls", ".XLS"};

    /**/
    public static ArrayList<HashMap<String, String>> JSONEncoding(JSONArray result, ArrayList<String> listval) {
        ArrayList<String> al = new ArrayList<String>();
        ArrayList<HashMap<String, String>> al1 = new ArrayList<>();
        try {
            JSONArray array = result;
            for (int i = 0; i < array.length(); i++) {
                JSONObject row = array.getJSONObject(i);
                HashMap<String, String> h = new HashMap<>();
                for (int icount = 0; icount < listval.size(); icount++) {
                    String servicename = row.getString(listval.get(icount));
                    h.put(listval.get(icount), row.getString(listval.get(icount)));
                }
                al1.add(h);

            }
        } catch (Exception e) {
            String msg = e.getMessage();
        }
        return al1;
    }

    public static String getUniqueId() {
        return System.currentTimeMillis() + "";
    }

    public static String addTime(String startTime, String endTime) {
        String date3 = "";
        try {
            String time1 = startTime;
            String time2 = endTime;

            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
            timeFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

            Date date1 = timeFormat.parse(time1);
            Date date2 = timeFormat.parse(time2);

            long sum = date1.getTime() + date2.getTime();
            date3 = timeFormat.format(new Date(sum));
        } catch (Exception e) {
            e.getMessage();
        }
        return date3;
    }

    public static boolean isMyServiceRunning(Class<?> serviceClass, Context context) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());

    }

    public static int getExifOrientation(String filepath) {
        int degree = 0;
        ExifInterface exif = null;
        try {
            exif = new ExifInterface(filepath);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        if (exif != null) {
            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, -1);
            if (orientation != -1) {
                // We only recognise a subset of orientation tag values.
                switch (orientation) {
                    case ExifInterface.ORIENTATION_ROTATE_90:
                        degree = 90;
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_180:
                        degree = 180;
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_270:
                        degree = 270;
                        break;
                }

            }
        }

        return degree;
    }
    public static String getPath(Uri uri, Activity activity) {
        String[] projection = {MediaStore.MediaColumns.DATA};
        Cursor cursor = activity
                .managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    public static String getDealerID(Context context) {
        SharedPreferences.Editor ed;
        SharedPreferences sp;
        sp = context.getApplicationContext().getSharedPreferences("skyline", MODE_PRIVATE);
        return sp.getString(Utility.DEALER_ID, "");
    }

    public static String getUserID(Context context) {
        SharedPreferences.Editor ed;
        SharedPreferences sp;
        sp = context.getApplicationContext().getSharedPreferences("skyline", MODE_PRIVATE);
        return sp.getString("clientid", "");
    }


    public static boolean checkClockRunningForBillable(Context context) {

        boolean clockRunningForBillableTime = false;

        try {
            SharedPreferences sp;
            sp = context.getApplicationContext().getSharedPreferences("skyline", MODE_PRIVATE);
            boolean isTimerRunningFromAdminClockModule = sp.getBoolean(Utility.TIMER_STARTED_FROM_ADMIN_CLOCK_MODULE, false);
            if (isTimerRunningFromAdminClockModule) {

                clockRunningForBillableTime = false;
            } else {

                clockRunningForBillableTime = true;
            }
        } catch (Exception e) {
            e.getMessage();
        }
        return clockRunningForBillableTime;
    }

    public static Bitmap getRotatedBitmap(String IMAGE_PATH, float orientation) {
        //get Bitmap
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        Bitmap bitmap = BitmapFactory.decodeFile(IMAGE_PATH, options);
        //rotate bitmap
        Matrix matrix = new Matrix();
        matrix.postRotate(orientation);
        //create new rotated bitmap
        bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return bitmap;
    }

    public static String saveImage(Bitmap bmp) {
       /* FileOutputStream out = null;
        try {
            out = new FileOutputStream(filename);
            bmp.compress(Bitmap.CompressFormat.PNG, 100, out); // bmp is your Bitmap instance
            // PNG is a lossless format, the compression factor (100) is ignored
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
*/

        //////////

        String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
        File folder = new File(extStorageDirectory, "Exhibit Power");

        if (!folder.exists()) {
            folder.mkdir();
        }
        File folder1 = new File(folder, "Camera");
        if (!folder1.exists()) {
            folder1.mkdir();
        }

        String unique_id = Utility.getUniqueId();

        File file = new File(folder1, unique_id + "_CameraImage.jpg");


        /////////////






       /* String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/saved_images");
        myDir.mkdirs();
        Random generator = new Random();
        int n = 10000;
        n = generator.nextInt(n);
        String fname = "TempImage-" + n + ".jpg";

        File file = new File(myDir, fname);*/
        if (file.exists()) file.delete();
        try {
            FileOutputStream out = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 96, out);
            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return file.getAbsolutePath();
    }

    public static boolean delete(String path) {
        boolean deleted = false;
        File fdelete = new File(path);
        if (fdelete.exists()) {
            if (fdelete.delete()) {
                deleted = true;
            } else {
                deleted = false;
            }
        }
        return deleted;
    }

    public static Date getCurrentTime() {
        Date date = null;
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat(DATE_FORMAT);
        String strDate = mdformat.format(calendar.getTime());

        try {
            date = mdformat.parse(strDate);
            //   System.out.println(date);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return date;

    }

    public static String getCurrentTimeString(String DATE_FORMAT) {
        String strDate = null;
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat(DATE_FORMAT);
        strDate = mdformat.format(calendar.getTime());

        SimpleDateFormat mdformatTest = new SimpleDateFormat(DATE_FORMAT, Locale.US);
        String strDateTest = mdformatTest.format(calendar.getTime());


        return strDate;

    }

    public static String getDateTimeDifference(Date startDate, Date endDate) {
        //milliseconds
        String time = "";
        try {
            long different = endDate.getTime() - startDate.getTime();

            long secondsInMilli = 1000;
            long minutesInMilli = secondsInMilli * 60;
            long hoursInMilli = minutesInMilli * 60;
            long daysInMilli = hoursInMilli * 24;

            long elapsedDays = different / daysInMilli;
            different = different % daysInMilli;

            long elapsedHours = different / hoursInMilli;
            different = different % hoursInMilli;

            long elapsedMinutes = different / minutesInMilli;
            different = different % minutesInMilli;

            long elapsedSeconds = different / secondsInMilli;


            String hrs, second, minute;

            if (elapsedSeconds < 10) {
                second = "0" + elapsedSeconds;
            } else {
                second = "" + elapsedSeconds;
            }
            if (elapsedMinutes < 10) {
                minute = "0" + elapsedMinutes;
            } else {
                minute = "" + elapsedMinutes;
            }
            if (elapsedHours < 10) {
                hrs = "0" + elapsedHours;
            } else {
                hrs = "" + elapsedHours;
            }


            time = hrs + ":" + minute + ":" + second;
        } catch (Exception e) {
            e.getMessage();
        }

        return time;
    }

    public static long getDateTimeDifference_seconds(Date startDate, Date endDate) {
        //milliseconds
        long elapsedSeconds = 0;
        try {
            long different = endDate.getTime() - startDate.getTime();

            long secondsInMilli = 1000;
            long minutesInMilli = secondsInMilli * 60;
            long hoursInMilli = minutesInMilli * 60;
            long daysInMilli = hoursInMilli * 24;

          /*  long elapsedDays = different / daysInMilli;
            different = different % daysInMilli;

            long elapsedHours = different / hoursInMilli;
            different = different % hoursInMilli;

            long elapsedMinutes = different / minutesInMilli;
            different = different % minutesInMilli;*/

            elapsedSeconds = different / secondsInMilli;


        } catch (Exception e) {
            e.getMessage();
        }

        return elapsedSeconds;
    }

    public static String get_TotalClockTime(Context context) {
        Date CurrentTime = Utility.getCurrentTime();
        SharedPreferences sp;
        sp = context.getSharedPreferences("skyline", MODE_PRIVATE);
        String sTime = sp.getString(Utility.CLOCK_START_TIME, "");
        SimpleDateFormat mdformat = new SimpleDateFormat(Utility.DATE_FORMAT);
        Date startTime = null;
        try {
            startTime = mdformat.parse(sTime);
        } catch (Exception e) {
            e.getMessage();
        }
        String Total_time = Utility.getDateTimeDifference(startTime, CurrentTime);
        return Total_time;
    }




    public static String minus_seconds_fromCurrentTime(String DateTime, int seconds) {
        String strDate;

        //   String currentTime= DateTime;//"2017-10-19 22:00:00";
        SimpleDateFormat mdformat = new SimpleDateFormat(Utility.DATE_FORMAT);
        Date startTime = null;
        try {
            startTime = mdformat.parse(DateTime);
        } catch (Exception e) {
            e.getMessage();
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(startTime);
        cal.add(Calendar.SECOND, -seconds);
        strDate = mdformat.format(cal.getTime());
        return strDate;
    }

    public static String add_seconds_toCurrentTime(String DateTime, int seconds) {
        String strDate;

        //   String currentTime= DateTime;//"2017-10-19 22:00:00";
        SimpleDateFormat mdformat = new SimpleDateFormat(Utility.DATE_FORMAT);
        Date startTime = null;
        try {
            startTime = mdformat.parse(DateTime);
        } catch (Exception e) {
            e.getMessage();
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(startTime);
        cal.add(Calendar.SECOND, seconds);
        strDate = mdformat.format(cal.getTime());
        return strDate;
    }

    public static boolean IsTimeGapIssue(Context context) {

        boolean val = false;

        SharedPreferences sp = context.getSharedPreferences("skyline", MODE_PRIVATE);
        String Str_PrevJobEndTime = sp.getString(Utility.TIMEGAP_JOB_END_TIME, "");
        String Str_NewJobStartTime = sp.getString(Utility.TIMEGAP_JOB_START_TIME, "");

        SimpleDateFormat mdformat = new SimpleDateFormat(Utility.DATE_FORMAT);
        Date PrevJobEndTime = null;
        Date NewJobStartTime = null;
        try {
            PrevJobEndTime = mdformat.parse(Str_PrevJobEndTime);
            NewJobStartTime = mdformat.parse(Str_NewJobStartTime);
        } catch (Exception e) {
            e.getMessage();
        }

        if (PrevJobEndTime == null || NewJobStartTime == null) {
            val = false;
        } else {
            long Total_Elapsed_Seconds = Utility.getDateTimeDifference_seconds(PrevJobEndTime, NewJobStartTime);
            if (Total_Elapsed_Seconds <= (7 * 60) + 30) {  //7 min =(7*60) sec
                val = true;
            } else {
                val = false;
            }
        }
        return val;
    }

    public static long GetTimeGap(Context context) {
        long Total_Elapsed_Seconds = 0;

        SharedPreferences sp = context.getSharedPreferences("skyline", MODE_PRIVATE);
        String Str_PrevJobEndTime = sp.getString(Utility.TIMEGAP_JOB_END_TIME, "");
        String Str_NewJobStartTime = sp.getString(Utility.TIMEGAP_JOB_START_TIME, "");

        SimpleDateFormat mdformat = new SimpleDateFormat(Utility.DATE_FORMAT);
        Date PrevJobEndTime = null;
        Date NewJobStartTime = null;
        try {
            PrevJobEndTime = mdformat.parse(Str_PrevJobEndTime);
            NewJobStartTime = mdformat.parse(Str_NewJobStartTime);
        } catch (Exception e) {
            e.getMessage();
        }

        if (PrevJobEndTime == null || NewJobStartTime == null) {
            Total_Elapsed_Seconds = 0;
        } else {
            Total_Elapsed_Seconds = Utility.getDateTimeDifference_seconds(PrevJobEndTime, NewJobStartTime);

        }
        return Total_Elapsed_Seconds;


    }

    public static String TotalTimeDifference(String time1, String time2) {
        String time = "";

        //  String time1 = "16:06";       //HH:mm
        //  String time2 = "00:07";      //HH:mm
        try {

            String ar[] = time1.split(":");
            int minutes1 = (Integer.parseInt(ar[0])) * 60 + (Integer.parseInt(ar[1]));

            String ar1[] = time2.split(":");
            int minutes2 = (Integer.parseInt(ar1[0])) * 60 + (Integer.parseInt(ar1[1]));
            int Total_minutes = minutes1 - minutes2;

            int hrs = Total_minutes / 60;
            int minuts = Total_minutes % 60;

            if (minuts < 10) {
                time = String.valueOf(hrs) + ":" + "0" + String.valueOf(minuts);
            } else {
                time = String.valueOf(hrs) + ":" + String.valueOf(minuts);
            }

        } catch (Exception e) {
            e.getMessage();
        }
        return time;
    }

    public static String TotalTimeAdd(String time1, String time2) {

        String time = "";

        //  String time1 = "16:06";       //HH:mm
        //   String time2 = "00:07";      //HH:mm
        try {

            String ar[] = time1.split(":");
            int minutes1 = (Integer.parseInt(ar[0])) * 60 + (Integer.parseInt(ar[1]));

            String ar1[] = time2.split(":");
            int minutes2 = (Integer.parseInt(ar1[0])) * 60 + (Integer.parseInt(ar1[1]));
            int Total_minutes = minutes1 + minutes2;

            int hrs = Total_minutes / 60;
            int minuts = Total_minutes % 60;

            if (minuts < 10) {
                time = String.valueOf(hrs) + ":" + "0" + String.valueOf(minuts);
            } else {
                time = String.valueOf(hrs) + ":" + String.valueOf(minuts);
            }


        } catch (Exception e) {
            e.getMessage();
        }
        return time;
    }

    public static boolean IsSameDay(String firstDateTime, String SecondDateTime) {
        boolean val = false;

        String ar1[] = firstDateTime.split(" ");
        String date1 = ar1[0];

        String ar2[] = SecondDateTime.split(" ");
        String date2 = ar2[0];

        if (date1.equals(date2)) {
            val = true;
        }

        return val;


    }

    public static boolean isAppOnForeground(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
        if (appProcesses == null) {
            return false;
        }
        final String packageName = context.getPackageName();
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND && appProcess.processName.equals(packageName)) {
                //                Log.e("app",appPackageName);
                return true;
            }
        }
        return false;
    }

    public static boolean isWiFiConnected(Context context) {
        ConnectivityManager connManager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo current = connManager.getActiveNetworkInfo();
        boolean isWifi = current != null && current.getType() == ConnectivityManager.TYPE_WIFI;
        return isWifi;
    }

    public static void saveOfflineIncompleteAsynctask(Context context, String api_input, String unique_apiId, String JobType) {
        JSONArray jsonArray = new JSONArray();
        JSONObject final_jsonObject = new JSONObject();

        SharedPreferences sp = context.getApplicationContext().getSharedPreferences("skyline", MODE_PRIVATE);
        SharedPreferences.Editor ed = sp.edit();
        //   timesheetNonBillable{tech_id=28; jobId=605; start_time=11:04; end_time=11:05; description=yyyy; code=21; region=yyyy  (00:01:19); status=3; }

        String api_name = api_input.substring(0, api_input.indexOf("{"));
        String temp = api_input.substring(api_input.indexOf("{") + 1, api_input.indexOf("}"));
        String input_arr[] = temp.split(";");
        JSONObject jsonObject = new JSONObject();
        for (int i = 0; i < input_arr.length; i++) {
            String st = input_arr[i];
            if (st.contains("=")) {
                String req_ar[] = st.split("=");
                try {
                    jsonObject.put(req_ar[0].trim(), req_ar[1].trim());
                } catch (Exception e) {
                    e.getCause();
                }
            }
        }
        try {
            final_jsonObject.put("apiId", unique_apiId);
            final_jsonObject.put("api_name", api_name);
            final_jsonObject.put("job_type", JobType);
            final_jsonObject.put("apiInput", jsonObject);
        } catch (Exception e) {
            e.getCause();
        }

        if (sp.contains(KEY_INCOMPLETE_ASYNC_ARRAY)) {
            String IncompleteAsyncArray = sp.getString(KEY_INCOMPLETE_ASYNC_ARRAY, "");
            try {
                jsonArray = new JSONArray(IncompleteAsyncArray);
                jsonArray.put(final_jsonObject);
            } catch (Exception e) {
                e.getCause();
            }
            ed.putString(KEY_INCOMPLETE_ASYNC_ARRAY, jsonArray.toString()).apply();
        } else {
            jsonArray.put(final_jsonObject);
            ed.putString(KEY_INCOMPLETE_ASYNC_ARRAY, jsonArray.toString()).apply();
        }

        Log.e("AsyncArrAfterAdd", jsonArray.toString());

    }

    public static String getPath(final Context context, final Uri uri) {
        try {
            final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

            // DocumentProvider
            if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
                // ExternalStorageProvider
                if (isExternalStorageDocument(uri)) {
                    final String docId = DocumentsContract.getDocumentId(uri);
                    final String[] split = docId.split(":");
                    final String type = split[0];
                    if ("primary".equalsIgnoreCase(type)) {
                        return Environment.getExternalStorageDirectory() + "/" + split[1];
                    }

                }
                // DownloadsProvider
                else if (isDownloadsDocument(uri)) {
                    String id = "";
                    try {
                        id = DocumentsContract.getDocumentId(uri);
                        final Uri contentUri = ContentUris.withAppendedId(
                                Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
                        return getDataColumn(context, contentUri, null, null);
                    } catch (Exception e) {
                        e.getCause();
                        if (id.contains(":")) {
                            return id.substring(id.indexOf(":") + 1);
                        }
                    }
                }
                // MediaProvider
                else if (isMediaDocument(uri)) {
                    final String docId = DocumentsContract.getDocumentId(uri);
                    final String[] split = docId.split(":");
                    final String type = split[0];

                    Uri contentUri = null;
                    if ("image".equals(type)) {
                        contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                    } else if ("video".equals(type)) {
                        contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                    } else if ("audio".equals(type)) {
                        contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                    }

                    final String selection = "_id=?";
                    final String[] selectionArgs = new String[]{
                            split[1]
                    };

                    return getDataColumn(context, contentUri, selection, selectionArgs);
                }
            }
            // MediaStore (and general)
            else if ("content".equalsIgnoreCase(uri.getScheme())) {
                return getDataColumn(context, uri, null, null);
            }
            // File
            else if ("file".equalsIgnoreCase(uri.getScheme())) {
                return uri.getPath();
            }
        } catch (Exception e) {
            e.getCause();
        }
        return null;
    }
    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }


    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    public static String parseDateToMMddyyyy(String time) {
        String inputPattern = "dd-MM-yyyy HH:mm:ss";
        String outputPattern = "MM-dd-yyyy";
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
