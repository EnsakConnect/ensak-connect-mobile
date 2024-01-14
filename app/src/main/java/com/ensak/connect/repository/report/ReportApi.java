package com.ensak.connect.repository.report;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ReportApi {
    @POST("reports")
    Call<Void> CreateReport(@Body ReportRequest request);
}
