package com.ensak.connect.view.loading_screen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.ensak.connect.databinding.ActivityLoadingBinding;
import com.ensak.connect.view.home.HomeActivity;
import com.ensak.connect.view.login.LoginActivity;

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
        loadingViewModel.startChecks();
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

        // On Error change
        loadingViewModel.getIsError().observe(this, isError -> {
            Log.d(TAG, "initViewModel isError: " + isError);
            if(isError){
                binding.txtErrorMessage.setVisibility(View.VISIBLE);
                binding.txtActionMessage.setVisibility(View.GONE);
            } else {
                binding.txtErrorMessage.setVisibility(View.GONE);
                binding.txtActionMessage.setVisibility(View.VISIBLE);
            }
        });

        // On Error message change
        loadingViewModel.getErrorMessage().observe(this, errorMessage -> {
            Log.d(TAG, "initViewModel: errorMessage" + errorMessage);
            binding.txtErrorMessage.setText(errorMessage);
        });

        // on Action message change
        loadingViewModel.getCurrentAction().observe(this, currentMessage -> {
            Log.d(TAG, "initViewModel: action message" + currentMessage);
            binding.txtActionMessage.setText(currentMessage);
        });

        // Redirect to
        loadingViewModel.getRedirectionTo().observe(this, redirectTo -> {
            if(redirectTo == null || redirectTo.equals(null)) return;
            if(redirectTo.equals(LoadingViewModel.REDIRECT_TO.HOME)){
                Intent homeIntent = new Intent(this, HomeActivity.class);
                startActivity(homeIntent);
                finish();
            } else if(redirectTo.equals(LoadingViewModel.REDIRECT_TO.LOGIN)){
                Intent loginIntent = new Intent(this, LoginActivity.class);
                startActivity(loginIntent);
                finish();
            }
        });
    }

    private void stopLoading() {
    loadingViewModel.setIsLoading(false);
    }
}