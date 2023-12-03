package com.ensak.connect.view_model.LoginViewModel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ensak.connect.models.LoginRequest;
import com.ensak.connect.reponse.LoginResponse;
import com.ensak.connect.reponse.NameResponse;
import com.ensak.connect.repositories.LoginRepository;
import com.ensak.connect.repositories.NameRepository;
import com.ensak.connect.retrofit.ApiRequest;
import com.ensak.connect.retrofit.RetrofitRequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

//public class NameViewModel extends AndroidViewModel {
//
//    private NameRepository testRepository;
//    private LiveData<NameResponse> testResponseLiveData;
//
//    public NameViewModel(@NonNull Application application) {
//        super(application);
//
//        testRepository = new NameRepository();
//        this.testResponseLiveData = testRepository.getTestMessage();
//    }
//
//    public LiveData<NameResponse> getTestResponseLiveData() {
//        return testResponseLiveData;
//    }
//}


public class LoginViewModel extends AndroidViewModel {

    private LoginRepository loginRepository;
    private MutableLiveData<LoginResponse> loginResponseLiveData;
    private ApiRequest apiRequest;

    public LoginViewModel(@NonNull Application application) {
        super(application);
        apiRequest = RetrofitRequest.getRetrofitInstance().create(ApiRequest.class);
        loginRepository = new LoginRepository(apiRequest);
        loginResponseLiveData = new MutableLiveData<>();
    }

    public LiveData<LoginResponse> getLoginResponseLiveData() {
        return loginResponseLiveData;
    }

    public void login(String email, String password) {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail(email);
        loginRequest.setPassword(password);


        apiRequest.loginUser(loginRequest).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                Log.d("LoginRepository", "inResponse response:: " + response);
                if (response.isSuccessful()) {
                    loginResponseLiveData.postValue(response.body());
                } else {
                    loginResponseLiveData.postValue(null); // Or some error object
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                loginResponseLiveData.postValue(null); // Or some error object
            }
        });
    }
}

