
package com.ensak.connect.view.Profile;
import android.app.Activity;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.ensak.connect.R;
public class ProfileActivity extends Activity {


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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


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


        // Set up the modify profile button click listener
        btnModifyProfile.setOnClickListener(view -> {
        });


    }
}
