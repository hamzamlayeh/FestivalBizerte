package com.user.festivalbizerte.WebService;

import com.user.festivalbizerte.Model.RSResponse;
import com.user.festivalbizerte.Model.User;
import com.user.festivalbizerte.Model.UserInfos;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface API {
    @Headers({
            "Accept: application/json"
    })
    //***********************Login*******************
    @POST("Login.php")
    Call<RSResponse> loginUser(@Body User user);

    //***********************Inscription*******************
    @Multipart
    @POST("Inscription.php")
    Call<RSResponse> inscrireUser(
            @Part MultipartBody.Part part,
            @Part("nom") RequestBody nom,
            @Part("prenom") RequestBody prenom,
            @Part("tel") RequestBody tel,
            @Part("email") RequestBody email,
            @Part("password") RequestBody password
    );
}
