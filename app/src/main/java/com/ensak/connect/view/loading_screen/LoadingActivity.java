package com.ensak.connect.view.loading_screen;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.ensak.connect.R;
import com.ensak.connect.databinding.ActivityLoadingBinding;

public class LoadingActivity extends AppCompatActivity {

    private ActivityLoadingBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoadingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}