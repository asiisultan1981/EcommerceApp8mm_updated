package com.example.a8mm.Retrofit;


import com.example.a8mm.Models.User;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface  ApiInterface {


    @FormUrlEncoded
    @POST("email_registration.php")
    Call<Object> performEmailRegistration(@Field("name") String user_name,
                                        @Field("email") String user_email,
                                        @Field("password") String user_password
    );


    @FormUrlEncoded
    @POST("email_login.php")
    Call<Object> performEmailLogin(
                                        @Field("email") String user_email,
                                        @Field("password") String user_password
    );


    @FormUrlEncoded
    @POST("phone_registration.php")
    Call<Object> performPhoneRegistration(
                                        @Field("phone") String user_phone

    );


    @FormUrlEncoded
    @POST("phone_login.php")
    Call<Object> performPhoneLogin(
            @Field("phone") String user_phone

    );
}
