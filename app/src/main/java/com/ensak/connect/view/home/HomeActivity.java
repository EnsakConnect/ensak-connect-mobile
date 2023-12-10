package com.ensak.connect.view.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.Toast;

import com.ensak.connect.R;
import com.ensak.connect.core.SessionManager;
import com.ensak.connect.view.loading_screen.LoadingActivity;
import com.ensak.connect.view.login.LoginActivity;
import com.ensak.connect.view_model.HomeViewModel;
import com.ensak.connect.view_model.NameViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.ensak.connect.databinding.ActivityMainBinding;

public class HomeActivity extends AppCompatActivity {
    private final String TAG = getClass().getSimpleName();
    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    private NavigationView navigationView;
    private DrawerLayout drawer;
//    SessionManager sessionManager;

    private FloatingActionButton btnAdd;
    private FloatingActionButton btnNewQuestion;
    private FloatingActionButton btnNewJobOffer;
    private FloatingActionButton btnNewBlogPost;
    private Boolean isFABMenuOpen = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        btnAdd = findViewById(R.id.btnAdd);
        btnNewQuestion = findViewById(R.id.btnNewQuestion);
        btnNewBlogPost = findViewById(R.id.btnNewBlogPost);
        btnNewJobOffer = findViewById(R.id.btnNewJobOffer);
        setupFABActions();

//        sessionManager = new SessionManager(this);
//        if (!sessionManager.isLoggedIn()) {
//            Intent intent = new Intent(this, LoadingActivity.class);
//            startActivity(intent);
//            finish();
//        }
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
                R.id.nav_settings, R.id.nav_about, R.id.nav_logout)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        listenForDrawerItemSelection();
    }

    private void setupFABActions() {
        btnAdd.setOnClickListener(view -> {
            Log.d(TAG, "Is menu open: " + isFABMenuOpen);
            if(isFABMenuOpen){
                this.isFABMenuOpen = false;
                this.closeFABMenu();
            } else {
                this.isFABMenuOpen = true;
                this.openFABMenu();
            }
            Log.d(TAG, "Is menu open (After): " + isFABMenuOpen);
        });
        btnNewQuestion.setOnClickListener(view -> {
            Toast.makeText(this, "Btn question", Toast.LENGTH_SHORT).show();
        });
        btnNewJobOffer.setOnClickListener(view -> {
            Toast.makeText(this, "Btn job offer", Toast.LENGTH_SHORT).show();
        });
        btnNewBlogPost.setOnClickListener(view -> {
            Toast.makeText(this, "Btn blog post", Toast.LENGTH_SHORT).show();
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
            Toast.makeText(this, "action_search", Toast.LENGTH_SHORT).show();
            return true;
        } else if (itemId == R.id.action_notifications) {
            Toast.makeText(this, "action_notifications", Toast.LENGTH_SHORT).show();
            return true;
        } else if (itemId == R.id.action_chat) {
            Toast.makeText(this, "action_chat", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    private void listenForDrawerItemSelection() {
        navigationView.setNavigationItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_home) {
                Toast.makeText(this, "nav_home", Toast.LENGTH_SHORT).show();
            } else if (itemId == R.id.nav_work_offers) {
                Toast.makeText(this, "nav_work_offers", Toast.LENGTH_SHORT).show();
            } else if (itemId == R.id.nav_inter_offers) {
                Toast.makeText(this, "nav_inter_offers", Toast.LENGTH_SHORT).show();
            } else if (itemId == R.id.nav_doctorat_offers) {
                Toast.makeText(this, "nav_doctorat_offers", Toast.LENGTH_SHORT).show();
            } else if (itemId == R.id.nav_blog) {
                Toast.makeText(this, "nav_blog", Toast.LENGTH_SHORT).show();
            } else if (itemId == R.id.nav_qa) {
                Toast.makeText(this, "nav_qa", Toast.LENGTH_SHORT).show();
            } else if (itemId == R.id.nav_profile) {
                Toast.makeText(this, "nav_profile", Toast.LENGTH_SHORT).show();
            } else if (itemId == R.id.nav_messages) {
                Toast.makeText(this, "nav_messages", Toast.LENGTH_SHORT).show();
            } else if (itemId == R.id.nav_notifications) {
                Toast.makeText(this, "nav_notifications", Toast.LENGTH_SHORT).show();
            } else if (itemId == R.id.nav_settings) {
                Toast.makeText(this, "nav_settings", Toast.LENGTH_SHORT).show();
            } else if (itemId == R.id.nav_about) {
                Toast.makeText(this, "nav_about", Toast.LENGTH_SHORT).show();
            } else if (itemId == R.id.nav_logout) {
                Toast.makeText(this, "nav_logout", Toast.LENGTH_SHORT).show();
            }
            drawer.closeDrawers();
            return true;
        });
    }
}