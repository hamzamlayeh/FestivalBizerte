package com.user.festivalbizerte.WebService;

import com.user.festivalbizerte.Model.RSResponse;
import com.user.festivalbizerte.Model.User;
import com.user.festivalbizerte.Model.UserInfos;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface API {
    @Headers({
            "Accept: application/json"
    })
    //***********************Login*******************
    @POST("Login.php")
    Call<RSResponse> loginUser(@Body User user);

    //***********************Inscription*******************
    @POST("Inscription.php")
    Call<RSResponse> inscrireUser(@Body UserInfos userInfos);
}
