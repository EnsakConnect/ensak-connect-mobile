package com.ensak.connect.view.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ensak.connect.adapters.home.HomeAdapter;
import com.ensak.connect.adapters.home.RecommandedOffersAdapter;
import com.ensak.connect.databinding.FragmentHomeBinding;
import com.ensak.connect.models.HomeModel;
import com.ensak.connect.view_model.HomeViewModel;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private RecyclerView rvAllOffers, rvRecommendedOffers;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        rvRecommendedOffers = binding.rvRecommendedOffers;
        RecommandedOffersAdapter recommandedOffersAdapter = new RecommandedOffersAdapter();
        rvRecommendedOffers.setAdapter(recommandedOffersAdapter);
        rvRecommendedOffers.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        rvAllOffers = binding.rvAllOffers;
        HomeAdapter adapter = new HomeAdapter();
        rvAllOffers.setAdapter(adapter);
        rvAllOffers.setLayoutManager(new LinearLayoutManager(getContext()));

//        new Timer().schedule(new TimerTask() {
//            @Override
//            public void run() {
//                adapter.notifyDataSetChanged();
//            }
//        }, 3000);


//        final TextView textView = binding.textHome;
//        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}