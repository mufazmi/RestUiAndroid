package com.socialcodia.restapi.api;

import com.socialcodia.restapi.model.DefaultResponse;
import com.socialcodia.restapi.model.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Api {

    @FormUrlEncoded
    @POST("createUser")
    Call<DefaultResponse> createUser(
        @Field("name") String name,
        @Field("email") String email,
        @Field("password") String password
    );

    @FormUrlEncoded
    @POST("login")
    Call<LoginResponse> login(
            @Field("email") String email,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("forgotPassword")
    Call<DefaultResponse> forgotPassword(
            @Field("email") String email
    );

    @FormUrlEncoded
    @POST("resetPassword")
    Call<DefaultResponse> resetPassword(
            @Field("email") String email,
            @Field("otp") String otp,
            @Field("newPassword") String newPassword
    );

    //Very Unsecure api, Full 100% vulnerable, need to use token here to communicate with server.
    @FormUrlEncoded
    @POST("updatePassword")
    Call<DefaultResponse> updatePassword(
            @Field("email") String email,
            @Field("password") String password,
            @Field("newPassword") String newPassword
    );
}
