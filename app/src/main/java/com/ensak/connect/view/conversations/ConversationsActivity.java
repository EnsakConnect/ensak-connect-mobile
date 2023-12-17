package com.ensak.connect.view.conversations;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.ensak.connect.R;
import com.ensak.connect.databinding.ActivityConversationsBinding;

public class ConversationsActivity extends AppCompatActivity {

    private ActivityConversationsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversations);
    }
}