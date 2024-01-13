
package com.ensak.connect.presentation.profile;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;


import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.ensak.connect.R;
import com.ensak.connect.adapters.Profile.EducationAdapter;
import com.ensak.connect.adapters.Profile.ExperienceAdapter;
import com.ensak.connect.adapters.Profile.SkillsAdapter;
import com.ensak.connect.constants.AppConstants;
import com.ensak.connect.databinding.ProfileActivityBinding;
import com.ensak.connect.service.GlideAuthUrl;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ProfileActivity extends AppCompatActivity {
    private final String TAG = getClass().getSimpleName();

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
        binding.btnModifyProfile.setOnClickListener(v -> {
            Log.d(TAG, "btnModifyProfile click");
            Intent updateProfileIntent = new Intent(ProfileActivity.this, ProfileEditActivity.class);
            Log.d(TAG, "btnModifyProfile Intent:" + updateProfileIntent.toString());
            startActivity(updateProfileIntent);
        });

        binding.experienceRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.educationRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.skillsRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        binding.modifyEducationButton.setOnClickListener(v -> {
            Intent intent = new Intent(ProfileActivity.this, EducationEditActivity.class);
            startActivity(intent);
        });

        binding.modifyExperienceButton.setOnClickListener(v -> {
            Intent intent = new Intent(ProfileActivity.this, ExperienceEditActivity.class);
            startActivity(intent);
        });

        binding.modifySkillsButton.setOnClickListener(v -> {
            Intent intent = new Intent(ProfileActivity.this, SkillsEditActivity.class);
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
                Log.d(TAG, "onCreate: " + userDetailsText);
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

                Glide.with(this)
                        .load(
                                GlideAuthUrl.getUrl(this, AppConstants.BASE_URL + "resources/" + profileResponse.getProfilePicture())
                        ).placeholder(R.drawable.profile_banner_placeholder)
                        .error(R.drawable.profile_picture_placeholder)
                        .centerCrop()
                        .into(binding.userProfileImage);

                Glide.with(this)
                        .load(
                                GlideAuthUrl.getUrl(this, AppConstants.BASE_URL + "resources/" + profileResponse.getBanner())
                        ).placeholder(R.drawable.profile_banner_placeholder)
                        .error(R.drawable.profile_banner_placeholder)
                        .centerCrop()
                        .into(binding.bannerImage);
            } else {
                Log.d(TAG, "onCreate: Profile response is null");
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        profileViewModel.fetchProfileData();
    }
}