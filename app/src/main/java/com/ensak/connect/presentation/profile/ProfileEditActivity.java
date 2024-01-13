package com.ensak.connect.presentation.profile;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.ensak.connect.R;
import com.ensak.connect.databinding.ProfileEditActivityBinding;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ProfileEditActivity extends AppCompatActivity {
    private ProfileEditActivityBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ProfileEditActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Toolbar setup
        setSupportActionBar(binding.toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
        binding.toolbar.setNavigationOnClickListener(v -> finish());

        initView();
        initViewModel();
    }
    private void initView() {

    }

    private void initViewModel() {

    }
}