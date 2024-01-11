package com.ensak.connect.presentation.notifications;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.ensak.connect.adapters.notifications.NotificationAdapter;
import com.ensak.connect.databinding.NotificationActivityBinding;
import com.ensak.connect.repository.notification.model.NotificationResponse;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class NotificationActivity extends AppCompatActivity {
    private NotificationActivityBinding binding;
    private NotificationViewModel notificationViewModel;
    private RecyclerView recyclerView;
    private NotificationAdapter notificationAdapter;
    private List<NotificationResponse> notifications = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = NotificationActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        recyclerView = binding.notificationsList;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        notificationAdapter=new NotificationAdapter(notifications);
        recyclerView.setAdapter(notificationAdapter);

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

        initViewModel();
        notificationViewModel.fetchNotifications();
    }
    private void initViewModel() {
        notificationViewModel = new ViewModelProvider(this).get(NotificationViewModel.class);

        notificationViewModel.getIsLoading().observe(this, isLoading -> {
            if(isLoading) {
                binding.prgLoading.setVisibility(View.VISIBLE);
                binding.notificationsList.setVisibility(View.GONE);
                binding.txtEmpty.setVisibility(View.GONE);
            } else {
                binding.prgLoading.setVisibility(View.GONE);
            }
        });

        notificationViewModel.getErrorMessage().observe(this, errorMessage -> {
            if(errorMessage == null || errorMessage.isEmpty()) return;
            Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
        });

        notificationViewModel.getNotifications().observe(this, notifs -> {
            notifications.clear();
            notifications.addAll(notifs);
            notificationAdapter.notifyDataSetChanged();
            if(notifs.size() == 0) {
                binding.txtEmpty.setVisibility(View.VISIBLE);
                binding.notificationsList.setVisibility(View.GONE);
            } else {
                binding.txtEmpty.setVisibility(View.GONE);
                binding.notificationsList.setVisibility(View.VISIBLE);
            }
        });
    }
}