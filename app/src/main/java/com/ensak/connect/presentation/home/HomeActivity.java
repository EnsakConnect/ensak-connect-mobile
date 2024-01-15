package com.ensak.connect.presentation.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.Menu;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.ensak.connect.R;
import com.ensak.connect.constants.AppConstants;
import com.ensak.connect.databinding.MainActivityBinding;

import com.ensak.connect.presentation.search.SearchActivity;
import com.ensak.connect.presentation.static_activities.AboutActivity;

import com.ensak.connect.databinding.MainNavHeaderBinding;
import com.ensak.connect.presentation.auth.login.LoginActivity;
import com.ensak.connect.service.GlideAuthUrl;

import com.ensak.connect.service.SessionManagerService;
import com.ensak.connect.presentation.auth.loading_screen.LoadingActivity;
import com.ensak.connect.presentation.profile.ProfileActivity;
import com.ensak.connect.presentation.settigns.SettingsActivity;
import com.ensak.connect.presentation.chat.conversation.ConversationsActivity;
import com.ensak.connect.presentation.job_post.create.JobPostCreateActivity;
import com.ensak.connect.presentation.question_post.create.CreateQuestionActivity;
import com.ensak.connect.presentation.notifications.NotificationActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import com.google.android.material.navigation.NavigationView;
import com.onesignal.OSPermissionObserver;
import com.onesignal.OSPermissionStateChanges;
import com.onesignal.OneSignal;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class HomeActivity extends AppCompatActivity {
    private final String TAG = getClass().getSimpleName();
    private AppBarConfiguration mAppBarConfiguration;
    private MainActivityBinding binding;
    private MainNavHeaderBinding headerBinding;
    private HomeViewModel homeViewModel;
    private NavigationView navigationView;
    private DrawerLayout drawer;
    SessionManagerService sessionManager;

    private FloatingActionButton btnAdd;
    private FloatingActionButton btnNewQuestion;
    private FloatingActionButton btnNewJobOffer;
    private FloatingActionButton btnNewBlogPost;
    private Boolean isFABMenuOpen = false;

    NavController navController;
    private static final String ONESIGNAL_APP_ID = "33340ee8-8b07-4a12-ad59-36f93ba2402b";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = MainActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        headerBinding = MainNavHeaderBinding.bind(binding.navView.getHeaderView(0));

        btnAdd = findViewById(R.id.btnAdd);
        btnNewQuestion = findViewById(R.id.btnNewQuestion);
        btnNewBlogPost = findViewById(R.id.btnNewBlogPost);
        btnNewJobOffer = findViewById(R.id.btnNewJobOffer);
        setupFABActions();

        sessionManager = new SessionManagerService(this);
        if (!sessionManager.isLoggedIn()) {
            Intent intent = new Intent(this, LoadingActivity.class);
            startActivity(intent);
            finish();
        }

        try {
            setSupportActionBar(binding.appBarMain.toolbar);
        } catch (Throwable ex) {
            ex.printStackTrace();
            Log.e(TAG, "Error: " + ex.getMessage());
        }
//        ActionBar actionBar = getSupportActionBar();
//        actionBar.setDisplayShowTitleEnabled(false);

        drawer = binding.drawerLayout;
        navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_work_offers, R.id.nav_inter_offers, R.id.nav_doctorat_offers,
                R.id.nav_blog, R.id.nav_qa, R.id.nav_profile, R.id.nav_messages, R.id.nav_notifications,
                R.id.nav_settings, R.id.nav_about, R.id.nav_logout, R.id.nav_user_posts)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        listenForDrawerItemSelection();

        configureOneSignal();

        headerBinding.crdUserData.setOnClickListener(v -> {
            openUserProfile();
        });

        initViewModel();
        homeViewModel.getAuthenticatedUser();
    }

    private void initViewModel() {
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        homeViewModel.getUserData().observe(this, user -> {
            headerBinding.txtHeaderFullName.setText(user.getFullName());
            headerBinding.txtHeaderTitle.setText(user.getTitle());

            Glide.with(this)
                    .load(
                            GlideAuthUrl.getUrl(this, AppConstants.BASE_URL + "resources/" + user.getProfilePicture())
                    )
                    .placeholder(R.drawable.profile_picture_placeholder)
                    .error(R.drawable.profile_picture_placeholder)
                    .centerCrop()
                    .into(headerBinding.imgHeaderPicture);
        });

        homeViewModel.getIsLoading().observe(this, isLoading -> {
            // TODO: do something when loading
        });

        homeViewModel.getIsError().observe(this, isError -> {
            if (isError) {
                Intent loginIntent = new Intent(this, LoginActivity.class);
                startActivity(loginIntent);
                finish();
                Toast.makeText(this, "Could not get logged in user", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void setupFABActions() {
        btnAdd.setOnClickListener(view -> {
            Log.d(TAG, "Is menu open: " + isFABMenuOpen);
            if (isFABMenuOpen) {
                this.isFABMenuOpen = false;
                this.closeFABMenu();
            } else {
                this.isFABMenuOpen = true;
                this.openFABMenu();
            }
            Log.d(TAG, "Is menu open (After): " + isFABMenuOpen);
        });
        btnNewQuestion.setOnClickListener(view -> {
            Intent createQuestionIntent = new Intent(this, CreateQuestionActivity.class);
            startActivity(createQuestionIntent);
            closeFABMenu();
        });
        btnNewJobOffer.setOnClickListener(view -> {
            Intent createJobPostIntent = new Intent(this, JobPostCreateActivity.class);
            startActivity(createJobPostIntent);
            closeFABMenu();
        });
        btnNewBlogPost.setOnClickListener(view -> {
            Toast.makeText(this, "Btn blog post", Toast.LENGTH_SHORT).show();
            closeFABMenu();
        });
    }

    private void openFABMenu() {
        isFABMenuOpen = true;
        btnNewQuestion.setElevation(6);
        btnNewQuestion.animate().translationY(-getResources().getDimension(R.dimen.fab_menu_item1_margin));
        btnNewJobOffer.setElevation(6);
        btnNewJobOffer.animate().translationY(-getResources().getDimension(R.dimen.fab_menu_item2_margin));
        btnNewBlogPost.setElevation(6);
        btnNewBlogPost.animate().translationY(-getResources().getDimension(R.dimen.fab_menu_item3_margin));
    }

    private void closeFABMenu() {
        isFABMenuOpen = false;
        btnNewQuestion.setElevation(0);
        btnNewQuestion.animate().translationY(0);
        btnNewJobOffer.setElevation(0);
        btnNewJobOffer.animate().translationY(0);
        btnNewBlogPost.setElevation(0);
        btnNewBlogPost.animate().translationY(0);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.action_search) {
            Intent intent = new Intent(this, SearchActivity.class);
            startActivity(intent);
            return true;
        } else if (itemId == R.id.action_notifications) {
            Intent intent = new Intent(this, NotificationActivity.class);
            startActivity(intent);
            return true;
        } else if (itemId == R.id.action_chat) {
            startActivity(new Intent(this, ConversationsActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    private void listenForDrawerItemSelection() {
        navigationView.setNavigationItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_home) {
            }
            else if(itemId == R.id.nav_user_posts){
                // TODO: start my posts activity
            } else if (itemId == R.id.nav_work_offers) {
                Bundle bundle = new Bundle();
                bundle.putString("filter", "CDI");
                navController.navigate(R.id.nav_post_category, bundle);
                binding.appBarMain.toolbar.setTitle("Offre d'emploi");
            } else if (itemId == R.id.nav_inter_offers) {
                Bundle bundle = new Bundle();
                bundle.putString("filter", "PFE");
                navController.navigate(R.id.nav_post_category, bundle);
                binding.appBarMain.toolbar.setTitle("Stages");
            } else if (itemId == R.id.nav_doctorat_offers) {
                Bundle bundle = new Bundle();
                bundle.putString("filter", "DOCTORATE");
                navController.navigate(R.id.nav_post_category, bundle);
                binding.appBarMain.toolbar.setTitle("Doctorats");
            } else if (itemId == R.id.nav_blog) {
                Bundle bundle = new Bundle();
                bundle.putString("filter", "BLOG");
                navController.navigate(R.id.nav_post_category, bundle);
                binding.appBarMain.toolbar.setTitle("Blogs");
            } else if (itemId == R.id.nav_qa) {
                Bundle bundle = new Bundle();
                bundle.putString("filter", "Q&A");
                navController.navigate(R.id.nav_post_category, bundle);
                binding.appBarMain.toolbar.setTitle("Q&A");
            } else if (itemId == R.id.nav_profile) {
                openUserProfile();
            } else if (itemId == R.id.nav_messages) {
                startActivity(new Intent(this, ConversationsActivity.class));
            } else if (itemId == R.id.nav_notifications) {
                startActivity(new Intent(this, NotificationActivity.class));
            } else if (itemId == R.id.nav_settings) {
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
            } else if (itemId == R.id.nav_about) {
                Intent intent = new Intent(this, AboutActivity.class);
                startActivity(intent);
            } else if (itemId == R.id.nav_logout) {
                sessionManager.logoutUser();
                Intent loadingScreenIntent = new Intent(this, LoadingActivity.class);
                startActivity(loadingScreenIntent);
                finish();
            }
            drawer.closeDrawers();
            return true;
        });
    }


    private void configureOneSignal() {
        OneSignal.initWithContext(this);
        OneSignal.setAppId(ONESIGNAL_APP_ID);
        OneSignal.promptForPushNotifications();

        SessionManagerService sessionManagerService = new SessionManagerService(this);
        int userId = sessionManagerService.getUserId();
        OneSignal.setExternalUserId(String.valueOf(userId));

        OneSignal.addPermissionObserver(new OSPermissionObserver() {
            @Override
            public void onOSPermissionChanged(OSPermissionStateChanges stateChanges) {
                OneSignal.setExternalUserId(String.valueOf(userId));
            }
        });



    }

    private void openUserProfile() {
        Intent intent = new Intent(this, ProfileActivity.class);
        intent.putExtra(ProfileActivity.KEY_USER_ID, sessionManager.getUserId());
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        homeViewModel.getAuthenticatedUser();
    }
}