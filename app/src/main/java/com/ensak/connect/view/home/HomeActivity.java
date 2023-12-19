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
import com.ensak.connect.view.LoadingScreen.LoadingActivity;
import com.ensak.connect.view.Profile.ProfileActivity;
import com.ensak.connect.view.conversations.ConversationsActivity;
import com.ensak.connect.view.login.LoginActivity;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
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
    SessionManager sessionManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sessionManager = new SessionManager(this);
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
        binding.appBarMain.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), LoginActivity.class);
                view.getContext().startActivity(intent);
            }
        });
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
            startActivity(new Intent(this, ConversationsActivity.class));
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
                Intent intent = new Intent(this, ProfileActivity.class);
                startActivity(intent);
            } else if (itemId == R.id.nav_messages) {
                Toast.makeText(this, "nav_messages", Toast.LENGTH_SHORT).show();
            } else if (itemId == R.id.nav_notifications) {
                Toast.makeText(this, "nav_notifications", Toast.LENGTH_SHORT).show();
            } else if (itemId == R.id.nav_settings) {
                Toast.makeText(this, "nav_settings", Toast.LENGTH_SHORT).show();
            } else if (itemId == R.id.nav_about) {
                Toast.makeText(this, "nav_about", Toast.LENGTH_SHORT).show();
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
}