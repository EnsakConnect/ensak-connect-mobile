package com.ensak.connect.presentation.search;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;

import com.ensak.connect.R;
import com.ensak.connect.databinding.ActivitySearchBinding;
import com.ensak.connect.databinding.NotificationActivityBinding;
import com.ensak.connect.presentation.home.FeedViewModel;

public class SearchActivity extends AppCompatActivity {

    private ActivitySearchBinding binding;
    String filter = "ALL";
    String searchText = "";
    FeedViewModel feedViewModel;
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

        binding.etName.addTextChangedListener(new TextWatcher() {


            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchText = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

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
                            filter = "BlogPost";
                            return true;
                        } else if (itemId == R.id.search_doctorate) {
                            binding.searchOption.setText("Doctorat");
                            filter = "DOCTORATE";
                            return true;
                        } else if (itemId == R.id.search_intern_offer) {
                            binding.searchOption.setText("Offre de stage");
                            filter = "PFE";
                            return true;
                        } else if (itemId == R.id.search_job_offer) {
                            binding.searchOption.setText("Offre d'emploi");
                            filter = "CDI";
                            return true;
                        } else if (itemId == R.id.search_profile) {
                            binding.searchOption.setText("Profile");

                            return true;
                        } else if (itemId == R.id.search_QA) {
                           binding.searchOption.setText("Q&A");
                           filter = "Q&A";
                            return true;
                        }
                        else if(itemId == R.id.search_all){
                            binding.searchOption.setText("Tout(Posts)");
                            filter = "ALL";
                        }

                        return false;
                    }
                });
                popupMenu.show();
            }

        });
        binding.cardSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (filter.equals("Profile")){
                    feedViewModel.fetchProfiles(0, searchText);
                }else{
                    feedViewModel.fetchFeed(0, searchText, filter);
                }

            }
        });
    }



}