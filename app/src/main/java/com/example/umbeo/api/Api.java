package com.example.umbeo.api;

import com.example.umbeo.response_data.LoginResponse;
import com.example.umbeo.response_data.SignUpResponse;
import com.example.umbeo.response_data.SignUpResquest;
import com.example.umbeo.response_data.UserGetProfileResponse;
import com.example.umbeo.response_data.forgetpassword_response;

import org.json.JSONArray;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface Api {

    @FormUrlEncoded
    @POST("signup")
    Call<ResponseBody> Signup(
            @Field("name") String name,
            @Field("phone") String number,
            @Field("email") String mail,
            @Field("password") String password,
            @Field("gps") String gps,
            @Field("latlng") String latlong,
            @Field("delivery_addresses") JSONArray address,
            @Field("shop") String shop,
            @Field("profile_pic") String profilePic
    );



    @POST("/api/v1/users/signup")
    Call<SignUpResponse> SignUp (@Header("Content-Type") String content_type,@Body SignUpResquest requestBody);

    @FormUrlEncoded
    @POST("login")
    Call<LoginResponse> userLogin(
            @Field("email") String email,
            @Field("password") String password
    );
    @FormUrlEncoded
    @POST("forgot-password")
    Call<forgetpassword_response> forgetPassword(
            @Field("email") String email
    );

    @FormUrlEncoded
    @POST("reset-password")
    Call<forgetpassword_response> resetPassword(
            @Field("email") String email,
            @Field("password") String password
    );



    @GET("/api/v1/users/me")
    Call<UserGetProfileResponse> getProfile(@Header("Authorization")String token);

    @POST("/api/v1/users/update-me")
    Call<ResponseBody> updateUser(
            @Header("Content-Type") String content_type,
            @Header("Authorization")String token,
            @Body SignUpResquest request
    );
}
