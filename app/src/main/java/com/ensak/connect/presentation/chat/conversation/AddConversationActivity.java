package com.ensak.connect.presentation.chat.conversation;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.ensak.connect.adapters.conversations.UsersAdapter;
import com.ensak.connect.databinding.ActivityAddConversationBinding;
import com.ensak.connect.presentation.home.OnClickListener;
import com.ensak.connect.repository.chat.model.ConversationRequest;
import com.ensak.connect.repository.profile.model.ProfileResponseDTO;
import com.ensak.connect.repository.profile.model.ProfilesResponse;
import com.ensak.connect.service.SessionManagerService;

import java.util.ArrayList;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class AddConversationActivity extends AppCompatActivity implements OnClickListener {

    private ActivityAddConversationBinding binding;
    private EditText etName;
    private CardView cardSearch;
    private RecyclerView rvUsers;
    private UsersAdapter adapter;
    private AddConversationViewModel addConversationViewModel;
    private ArrayList<ProfileResponseDTO> profilesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddConversationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
        binding.toolbar.setNavigationOnClickListener(v -> finish());

        initViews();
    }

    private void initViews() {
        profilesList = new ArrayList<>();

        etName = binding.etName;
        cardSearch = binding.cardSearch;
        rvUsers = binding.rvUsers;

        adapter = new UsersAdapter(profilesList);
        rvUsers.setAdapter(adapter);
        rvUsers.setLayoutManager(new LinearLayoutManager(this));

        addConversationViewModel = new ViewModelProvider(this).get(AddConversationViewModel.class);

        cardSearch.setOnClickListener(v -> {
            String name = etName.getText().toString();
            if (!name.isEmpty()) {
                initSearchOperation(name);
            }
        });
    }

    private void initSearchOperation(String name) {
        // hide keyboard
        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
        etName.clearFocus();

        addConversationViewModel.searchProfiles(name);
        addConversationViewModel.getProfilesList().observe(this, list -> {
            profilesList.clear();
            profilesList.addAll(list);
            adapter.notifyDataSetChanged();
        });

        addConversationViewModel.getIsLoading().observe(this, isLoading -> {
            Toast.makeText(this, "Loading...", Toast.LENGTH_SHORT).show();
        });

        addConversationViewModel.getErrorMessage().observe(this, errorMessage -> {
            if (errorMessage.isEmpty()) {
                return;
            }
            Toast.makeText(this, "Loading...", Toast.LENGTH_SHORT).show();
        });
    }

    private void addConversation(ProfileResponseDTO receiver) {
        ConversationsViewModel conversationViewModel = new ViewModelProvider(this).get(ConversationsViewModel.class);

        SessionManagerService sessionManagerService = new SessionManagerService(this);
        int userId = sessionManagerService.getUserId();

        conversationViewModel.addConversation(new ConversationRequest(userId, receiver.getProfileId()));

        conversationViewModel.getConversations().observe(this, list -> {
            finish();
        });

        conversationViewModel.getIsLoading().observe(this, isLoading -> {
            Toast.makeText(this, "Loading...", Toast.LENGTH_SHORT).show();
        });

        conversationViewModel.getErrorMessage().observe(this, errorMessage -> {
            if (errorMessage.isEmpty()) {
                return;
            }
            Toast.makeText(this, "Loading...", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public void onClick(ProfileResponseDTO profile) {
        addConversation(profile);
    }


}