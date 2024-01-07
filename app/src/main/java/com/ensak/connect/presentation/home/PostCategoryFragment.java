package com.ensak.connect.presentation.home;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ensak.connect.adapters.feed.FeedAdapter;
import com.ensak.connect.databinding.MainPostCategoryFragmentBinding;
import com.ensak.connect.repository.feed.model.FeedResponse;

import java.util.ArrayList;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class PostCategoryFragment extends Fragment {

    private static final String ARG_PARAM1 = "filter";

    // TODO: Rename and change types of parameters
    private String filter;

    public PostCategoryFragment() {
        // Required empty public constructor
    }

    public static PostCategoryFragment newInstance(String filter) {
        PostCategoryFragment fragment = new PostCategoryFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, filter);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            filter = getArguments().getString(ARG_PARAM1);
        }
    }

    private MainPostCategoryFragmentBinding binding;
    private FeedViewModel feedViewModel;
    private FeedResponse feed;
    private FeedAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = MainPostCategoryFragmentBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        requireActivity().setTitle(filter);


        adapter = new FeedAdapter();
        binding.rvAllOffers.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        binding.rvAllOffers.setLayoutManager(layoutManager);

        initViewModel();

        feedViewModel.fetchFeed(0, null, filter);

        return root;
    }

    private void initViewModel() {
        feedViewModel = new ViewModelProvider(this).get(FeedViewModel.class);

        feedViewModel.getFeed().observe(getViewLifecycleOwner(), feedResponse -> {
            adapter.setItems(feedResponse);
        });
    }
}