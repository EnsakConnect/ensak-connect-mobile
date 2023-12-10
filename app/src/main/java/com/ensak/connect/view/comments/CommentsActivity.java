package com.ensak.connect.view.comments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ensak.connect.adapters.comments.CommentsAdapter;
import com.ensak.connect.databinding.ActivityCommentsBinding;
import com.ensak.connect.reponse.CommentResponse;
import com.ensak.connect.view_model.CommentViewModel;

import java.util.ArrayList;

public class CommentsActivity extends AppCompatActivity {

    private ActivityCommentsBinding binding;
    private CommentViewModel commentViewModel;

    private ArrayList<CommentResponse> comments;
    private CommentsAdapter adapter;
    private RecyclerView rvComments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCommentsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);

        Bundle extras = getIntent().getExtras();
        String postId = "1";
        if (extras != null) {
            postId = extras.getString("postId");
        }

        initRecyclerView();
        loadComments(this, postId);
    }

    private void initRecyclerView() {
        comments = new ArrayList<>();

        rvComments = binding.rvComments;
        adapter = new CommentsAdapter(comments);
        rvComments.setAdapter(adapter);
        rvComments.setLayoutManager(new LinearLayoutManager(this));
    }

    private void loadComments(Context context, String postId) {
        commentViewModel = ViewModelProviders.of(this).get(CommentViewModel.class);
        try {
            commentViewModel.getPosts(postId).observe((LifecycleOwner) context, responses -> {
                if (responses != null) {

//                    String message = responses.get(0).getUser().getFirstname();
//                    Log.d("Main Log", message);

                    comments.clear();
                    comments.addAll(responses);
                    adapter.notifyDataSetChanged();
                    adapter.notifyDataSetChanged();
                }
            });
        } catch (Throwable ex) {
            Toast.makeText(context, "Error getting posts.", Toast.LENGTH_LONG);
        }

    }
}