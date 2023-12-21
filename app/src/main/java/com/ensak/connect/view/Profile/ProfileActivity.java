
package com.ensak.connect.view.Profile;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ensak.connect.ModifyProfile;
import com.ensak.connect.R;
import com.ensak.connect.adapters.Profile.EducationAdapter;
import com.ensak.connect.adapters.Profile.ExperienceAdapter;
import com.ensak.connect.adapters.Profile.SkillsAdapter;
import com.ensak.connect.models.Experience;
import com.ensak.connect.view_model.ProfileViewModel.ProfileViewModel;

import java.util.List;

public class ProfileActivity extends AppCompatActivity {

    private RecyclerView experienceRecyclerView;
    private ExperienceAdapter experienceAdapter;

    private RecyclerView educationRecyclerView;
    private EducationAdapter educationAdapter;

    private RecyclerView skillsRecyclerView;
    private SkillsAdapter skillsAdapter;

    private ImageView backButton;
    private ImageView bannerImage;
    private ImageView userProfileImage;
    private TextView userName;
    private TextView userDetails;
    private Button btnModifyProfile;
    private Button resumebtn;
    private Button myButton;
    private Button modify_experience_btn;
    private Button modify_skills_button;
    private ProfileViewModel profileViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        experienceRecyclerView = findViewById(R.id.experienceRecyclerView);
        experienceRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        educationRecyclerView = findViewById(R.id.educationRecyclerView);
        educationRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        skillsRecyclerView = findViewById(R.id.skillsRecyclerView);
        skillsRecyclerView.setLayoutManager(new GridLayoutManager(this, 2)); // For a grid with 3 columns






        // Initialize views
        backButton = findViewById(R.id.backButton);
        bannerImage = findViewById(R.id.bannerImage);
        userProfileImage = findViewById(R.id.userProfileImage);
        userName = findViewById(R.id.userName);
        userDetails = findViewById(R.id.userDetails);
        btnModifyProfile = findViewById(R.id.btnModifyProfile);
        TextView descriptionTextView = findViewById(R.id.userDescription);

        resumebtn = findViewById(R.id.resumebtn);

        //experience professionels
        myButton = findViewById(R.id.myButton);
        myButton.setText(R.string.button_text);

        //parcours educatif
        modify_experience_btn = findViewById(R.id.modify_experience_button);
        modify_experience_btn.setText(R.string.button_text);





        // URLs for the images
        String bannerImageUrl = "https://www.schudio.com/wp-content/uploads/2017/05/banner-user-journey.png"; // Replace with your actual URL
        String backIconUrl = "https://example.com/path/to/back/icon.png"; // Replace with your actual URL
        String profileImageUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcStsRVE2OpWFMYeY5S1bXG5J4UXp-FkBHGpUM5YDpIsXVWPw2ZdmLUzIitofNwhB_7cahk&usqp=CAU"; // Replace with your actual URL


        // Load images from URLs using Glide
        Glide.with(this).load(bannerImageUrl).into(bannerImage);
        Glide.with(this).load(backIconUrl).into(backButton);
        Glide.with(this).load(profileImageUrl).into(userProfileImage);


        // Set up the back button click listener
        backButton.setOnClickListener(view -> finish());

        profileViewModel = new ViewModelProvider(this, new ViewModelProvider.Factory() {
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                return (T) new ProfileViewModel(getApplicationContext());
            }
        }).get(ProfileViewModel.class);

        // Observe LiveData
        profileViewModel.getProfileLiveData().observe(this, profileResponse -> {
            if (profileResponse != null) {
                String fullName = profileResponse.getFullName();
                userName.setText(fullName);
                String userTitle = profileResponse.getTitle();
                String userDetailsText = userTitle;
                Log.d("TAG", "onCreate: " + userDetailsText);
                userDetails.setText(userDetailsText);

                experienceAdapter = new ExperienceAdapter(profileResponse.getExperienceList());
                experienceRecyclerView.setAdapter(experienceAdapter);

                educationAdapter = new EducationAdapter(profileResponse.getEducationList());
                educationRecyclerView.setAdapter(educationAdapter);

                skillsAdapter = new SkillsAdapter(profileResponse.getSkillList());
                skillsRecyclerView.setAdapter(skillsAdapter);

                if(profileResponse.getResume() != null){
                    resumebtn.setText("Voir le CV");
                } else {
                    resumebtn.setText("Ajouter un CV");
                }
            } else {

            }
        });

        btnModifyProfile.setOnClickListener(v -> profileViewModel.fetchProfileData());

        modify_experience_btn.setOnClickListener(v -> {
            Intent intent = new Intent(ProfileActivity.this, ModifyProfile.class);
            startActivity(intent);
        });
    }
}