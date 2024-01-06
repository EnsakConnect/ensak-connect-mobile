package com.ensak.connect.view.home;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ensak.connect.adapters.home.HomeAdapter;
import com.ensak.connect.databinding.FragmentPostCategoryBinding;
import com.ensak.connect.repository.feed.model.FeedResponse;
import com.ensak.connect.view_model.HomeViewModel;

import java.util.ArrayList;

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

    private FragmentPostCategoryBinding binding;
    private RecyclerView rvAllOffers;
    private HomeViewModel homeViewModel;
    private FeedResponse feed;
    private HomeAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPostCategoryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        requireActivity().setTitle(filter);

        feed = new FeedResponse();
        feed.content = new ArrayList<>();

        rvAllOffers = binding.rvAllOffers;
        adapter = new HomeAdapter(feed);
        rvAllOffers.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        rvAllOffers.setLayoutManager(layoutManager);

        getPosts(getContext(), 0, filter);

        return root;
    }

    private void getPosts(Context context, int page, String filter) {
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        try {
            homeViewModel.getFeed(page, "", filter).observe((LifecycleOwner) context, response -> {
                if (response != null) {

                    String message = String.valueOf(response.getPageNumber());
                    Log.d("Main Log", message);

                    feed.content.clear();
                    feed.content.addAll(response.getContent());
                    feed.setPageNumber(response.getPageNumber());
                    feed.setTotalPages(response.getTotalPages());
                    adapter.notifyDataSetChanged();


                }
            });
        } catch (Throwable ex) {
            Toast.makeText(context, "Error getting posts.", Toast.LENGTH_LONG);
        }

    }
}