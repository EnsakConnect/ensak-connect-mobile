package com.ensak.connect.presentation.profile;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.widget.Toast;

import com.ensak.connect.R;
import com.ensak.connect.databinding.ProfileEditActivityBinding;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ProfileEditActivity extends AppCompatActivity {
    private ProfileEditActivityBinding binding;
    private ProfileEditViewModel profileEditViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ProfileEditActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initView();
        initViewModel();
        profileEditViewModel.refresh();
    }
    private void initView() {
        // Toolbar setup
        setSupportActionBar(binding.toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
        binding.toolbar.setNavigationOnClickListener(v -> finish());


    }

    private void initViewModel() {
        profileEditViewModel = new ViewModelProvider(this).get(ProfileEditViewModel.class);

        profileEditViewModel.getInformation().observe(this, information -> {
            binding.txtFullName.setText(information.getFullName());
            binding.txtProfileTitle.setText(information.getTitle());
            binding.txtDescription.setText(information.getDescription());
        });

        profileEditViewModel.getIsLoading().observe(this, isLoading -> {
            if(isLoading) {
                binding.txtFullName.setEnabled(false);
                binding.txtProfileTitle.setEnabled(false);
                binding.txtDescription.setEnabled(false);
            } else {
                binding.txtFullName.setEnabled(true);
                binding.txtProfileTitle.setEnabled(true);
                binding.txtDescription.setEnabled(true);
            }
        });

        profileEditViewModel.getErrorMessage().observe(this, errorMessage -> {
            if(errorMessage == null || errorMessage.isEmpty()){
                return;
            }
            Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
        });
    }
}