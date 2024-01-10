
package com.ensak.connect.presentation.profile;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;


import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.ensak.connect.adapters.Profile.EducationAdapter;
import com.ensak.connect.adapters.Profile.ExperienceAdapter;
import com.ensak.connect.adapters.Profile.SkillsAdapter;
import com.ensak.connect.databinding.ProfileActivityBinding;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ProfileActivity extends AppCompatActivity {

    private ProfileActivityBinding binding;
    private ExperienceAdapter experienceAdapter;
    private EducationAdapter educationAdapter;
    private SkillsAdapter skillsAdapter;
    private ProfileViewModel profileViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ProfileActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
        binding.toolbar.setNavigationOnClickListener(v -> finish());

        initView();
        initViewModel();
        profileViewModel.fetchProfileData();
    }

    private void initView() {
        binding.experienceRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.educationRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.skillsRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        // URLs for the images
        String bannerImageUrl = "https://www.schudio.com/wp-content/uploads/2017/05/banner-user-journey.png";
        String backIconUrl = "https://example.com/path/to/back/icon.png";
        String profileImageUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcStsRVE2OpWFMYeY5S1bXG5J4UXp-FkBHGpUM5YDpIsXVWPw2ZdmLUzIitofNwhB_7cahk&usqp=CAU"; // Replace with your actual URL

        // Load images from URLs using Glide
        Glide.with(this).load(bannerImageUrl).into(binding.bannerImage);
        Glide.with(this).load(profileImageUrl).into(binding.userProfileImage);

        binding.btnModifyProfile.setOnClickListener(v -> profileViewModel.fetchProfileData());

        binding.modifyEducationButton.setOnClickListener(v -> {
            Intent intent = new Intent(ProfileActivity.this, EducationEditActivity.class);
            startActivity(intent);
        });

        binding.modifyExperienceButton.setOnClickListener(v -> {
            Intent intent = new Intent(ProfileActivity.this, ExperienceEditActivity.class);
            startActivity(intent);
        });
    }

    private void initViewModel() {
        profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);

        profileViewModel.getIsLoading().observe(this, isLoading -> {
            // TODO: show loading state
        });

        profileViewModel.getErrorMessage().observe(this, errorMessage -> {
            if(errorMessage.isEmpty()){ return; }
            // TODO: show error message/ state
            Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
        });

        profileViewModel.getProfile().observe(this, profileResponse -> {
            if (profileResponse != null) {
                String fullName = profileResponse.getFullName();
                binding.userName.setText(fullName);
                String userTitle = profileResponse.getTitle();
                String userDetailsText = userTitle;
                Log.d("TAG", "onCreate: " + userDetailsText);
                binding.userDetails.setText(userDetailsText);

                experienceAdapter = new ExperienceAdapter(this, profileResponse.getExperienceList());
                binding.experienceRecyclerView.setAdapter(experienceAdapter);

                educationAdapter = new EducationAdapter(this, profileResponse.getEducationList());
                binding.educationRecyclerView.setAdapter(educationAdapter);

                skillsAdapter = new SkillsAdapter(profileResponse.getSkillList());
                binding.skillsRecyclerView.setAdapter(skillsAdapter);

                if(profileResponse.getResume() != null){
                    binding.resumebtn.setText("Voir le CV");
                } else {
                    binding.resumebtn.setText("Ajouter un CV");
                }
            } else {
                Log.d("TAG", "onCreate: Profile response is null");
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        profileViewModel.fetchProfileData();
    }
}