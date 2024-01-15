package com.ensak.connect.presentation.search;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;

import com.ensak.connect.R;
import com.ensak.connect.databinding.ActivitySearchBinding;
import com.ensak.connect.databinding.NotificationActivityBinding;

public class SearchActivity extends AppCompatActivity {

    private ActivitySearchBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySearchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding.ivFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(SearchActivity.this, binding.ivFilter);
                popupMenu.getMenuInflater().inflate(R.menu.search_menu, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        int itemId = item.getItemId();

                        if (itemId == R.id.search_blog) {
                            binding.searchOption.setText("Blog");
                            return true;
                        } else if (itemId == R.id.search_doctorate) {
                            binding.searchOption.setText("Doctorat");
                            return true;
                        } else if (itemId == R.id.search_intern_offer) {
                            binding.searchOption.setText("Offre de stage");
                            return true;
                        } else if (itemId == R.id.search_job_offer) {
                            binding.searchOption.setText("Offre d'emploi");
                            return true;
                        } else if (itemId == R.id.search_profile) {
                            binding.searchOption.setText("Profil");
                            return true;
                        } else if (itemId == R.id.search_QA) {
                           binding.searchOption.setText("Q&A");
                            return true;
                        }
                        else if(itemId == R.id.search_all){
                            binding.searchOption.setText("Touts");
                        }

                        return false;
                    }
                });
                popupMenu.show();
            }
        });
    }



}