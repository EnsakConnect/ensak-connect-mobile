import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ProfileActivity extends Activity {

    private ImageView backButton;
    private ImageView bannerImage;
    private ImageView userProfileImage;
    private TextView userName;
    private TextView userDetails;
    private Button btnModifyProfile;

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

        // URLs for the images
        String bannerImageUrl = "https://example.com/path/to/banner/image.jpg"; // Replace with your actual URL
        String backIconUrl = "https://example.com/path/to/back/icon.png"; // Replace with your actual URL
        String profileImageUrl = "https://example.com/path/to/profile/image.jpg"; // Replace with your actual URL

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