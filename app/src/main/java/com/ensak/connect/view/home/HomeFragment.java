package com.ensak.connect.view.home;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ensak.connect.adapters.home.HomeAdapter;
import com.ensak.connect.adapters.home.RecommandedOffersAdapter;
import com.ensak.connect.databinding.FragmentHomeBinding;
import com.ensak.connect.reponse.PostResponse;
import com.ensak.connect.view_model.HomeViewModel;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private RecyclerView rvAllOffers, rvRecommendedOffers;
    private HomeViewModel homeViewModel;
    private ArrayList<PostResponse> posts;
    private HomeAdapter adapter;
    private RecommandedOffersAdapter recommandedOffersAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        posts = new ArrayList<>();

        rvRecommendedOffers = binding.rvRecommendedOffers;
        recommandedOffersAdapter = new RecommandedOffersAdapter(posts);
        rvRecommendedOffers.setAdapter(recommandedOffersAdapter);
        rvRecommendedOffers.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        rvAllOffers = binding.rvAllOffers;
        adapter = new HomeAdapter(posts);
        rvAllOffers.setAdapter(adapter);
        rvAllOffers.setLayoutManager(new LinearLayoutManager(getContext()));

        getPosts(getContext());


//        final TextView textView = binding.textHome;
//        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    private void getPosts(Context context) {
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        try {
            homeViewModel.getPosts().observe((LifecycleOwner) context, responses -> {
                if (responses != null) {

                    String message = responses.get(0).getUser().getFirstname();
                    Log.d("Main Log", message);

                    posts.clear();
                    posts.addAll(responses);
                    adapter.notifyDataSetChanged();
                    recommandedOffersAdapter.notifyDataSetChanged();


                    Toast.makeText(context, "API Response: " + message, Toast.LENGTH_SHORT).show();

                }
            });
        } catch (Throwable ex) {
            Toast.makeText(context, "Error getting posts.", Toast.LENGTH_LONG);
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}