package com.ensak.connect.view.loading_screen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.ensak.connect.R;
import com.ensak.connect.databinding.ActivityLoadingBinding;
import com.ensak.connect.view_model.LoginViewModel.LoginViewModel;

public class LoadingActivity extends AppCompatActivity {
    private final String TAG = getClass().getSimpleName();
    private ActivityLoadingBinding binding;
    private LoadingViewModel loadingViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoadingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initViewModel();
        initViewActions();
    }

    private void initViewModel() {
        loadingViewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(getApplication()))
                .get(LoadingViewModel.class);

        // Progress bar visibility
        loadingViewModel.getIsLoading().observe(this, isLoading -> {
            Log.d(TAG, "initViewModel isLoading: " + isLoading);
            if(isLoading) {
                binding.progressLoading.setVisibility(View.VISIBLE);
                Toast.makeText(this, "Loading started", Toast.LENGTH_SHORT).show();
            } else {
                binding.progressLoading.setVisibility(View.INVISIBLE);
                Toast.makeText(this, "Loading stopped", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initViewActions() {
        binding.btnStopLoading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopLoading();
            }
        });
    }

    private void stopLoading() {
    loadingViewModel.setIsLoading(false);
    }
}