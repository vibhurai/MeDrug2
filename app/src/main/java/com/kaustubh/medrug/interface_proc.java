package com.kaustubh.medrug;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface interface_proc {
    @GET("medicines")
    Call<List<meds_intr>> getmeds();
}
