package com.kaustubh.medrug;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

import java.util.List;

import androidx.annotation.RequiresApi;
import retrofit2.Call;
import retrofit2.http.GET;

public interface interface_proc {

    @GET("doctors")
    Call<List<doctors>> getdocs();

    @GET("medicines")
    Call<List<medicines>> getmeds();

    @POST("login/?format=json")
    Call<login> postlogin(@Body login a);

    @POST("register/?format=json")
    Call <reg> postreg(@Body reg register);

    @GET("schedule/{id}/{day}/?format=json")
    Call<List<schedule>> get_schedule(@Path("id") int id, @Path("day") String day);

    @POST("appointment/")
    Call<appointment> seeres(@Body appointment app);

}
