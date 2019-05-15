package com.cs.nks.easycouriers.retrofit_multipart;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Url;


public interface ApiService {
    @Multipart
    @POST()
  //  Call<ResponseBody> uploadMedia(@Part MultipartBody.Part[] image,@Path(value = "fullUrl", encoded = true) String fullUrl);
  //  Call<ResponseBody> uploadMedia(@Part MultipartBody.Part[] image,@Path(value = "fullUrl", encoded = true) String fullUrl);
    Call<ResponseBody> uploadMedia(@Part MultipartBody.Part[] image, @Url String url);
}
