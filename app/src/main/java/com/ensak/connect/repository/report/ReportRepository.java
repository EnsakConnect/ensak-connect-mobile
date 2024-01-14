package com.ensak.connect.repository.report;

import androidx.annotation.NonNull;

import com.ensak.connect.repository.shared.RepositoryCallBack;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ReportRepository {
    private ReportApi api;

    @Inject
    public  ReportRepository(Retrofit retrofit){
        this.api = retrofit.create(ReportApi.class);
    }

    public void sendReport(@NonNull ReportRequest reportRequest,@NonNull RepositoryCallBack<Boolean> callBack){
        api.CreateReport(reportRequest).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(!response.isSuccessful()){
                    callBack.onFailure((new Exception("Report Error")));
                    return;
                }
                callBack.onSuccess(true);
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                callBack.onFailure(t);
            }
        });
    }
}
