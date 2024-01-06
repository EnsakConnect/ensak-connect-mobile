package com.ensak.connect.presentation.notifications;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.ensak.connect.adapters.notifications.NotificationAdapter;
import com.ensak.connect.databinding.NotificationActivityBinding;
import com.ensak.connect.repository.notification.model.NotificationResponse;
import com.ensak.connect.service.retrofit.ApiRequest;
import com.ensak.connect.service.RetrofitService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private NotificationActivityBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = NotificationActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        recyclerView = binding.notificationsList;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        setSupportActionBar(binding.toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        loadNotifications();
    }

    private void loadNotifications(){
        RetrofitService retrofitRequest=new RetrofitService(getApplicationContext());
        ApiRequest apiRequest=retrofitRequest.getRetrofitInstance(getApplicationContext()).create(ApiRequest.class);
        Call<List<NotificationResponse>> call = apiRequest.getAllNotifications();
        call.enqueue(new Callback<List<NotificationResponse>>() {
            @Override
            public void onResponse(Call<List<NotificationResponse>> call, Response<List<NotificationResponse>> response) {
                // Traitement de la réponse ici
                if (response.isSuccessful()) {
                    populateListView(response.body());
                    // Faites quelque chose avec les données reçues
                } else {
                    // Gérer les erreurs de réponse
                }
            }

            @Override
            public void onFailure(Call<List<NotificationResponse>> call, Throwable t) {
                Toast.makeText(NotificationActivity.this, "Failed To get Notifications", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void populateListView(List<NotificationResponse> notificationslist){
        NotificationAdapter notificationAdapter=new NotificationAdapter(notificationslist);
        recyclerView.setAdapter(notificationAdapter);
    }
}