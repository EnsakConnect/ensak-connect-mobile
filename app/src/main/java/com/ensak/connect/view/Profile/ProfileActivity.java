
package com.ensak.connect.view.Profile;


import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


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
            // Handle modify profile action
            // Intent to navigate to the profile modification screen could go here
        });


        // Set up user details text views (assuming these details are passed to this activity,
        // for example through intent extras or from a ViewModel/Singleton data source)
        // userName.setText(getIntent().getStringExtra("USER_NAME"));
        // userDetails.setText(getIntent().getStringExtra("USER_DETAILS"));


        // Or if you have static text, you can set it directly or via string resources
        // userName.setText(getResources().getString(R.string.nom_utilisateur));
        // userDetails.setText(getResources().getString(R.string.user_details));
    }
}
