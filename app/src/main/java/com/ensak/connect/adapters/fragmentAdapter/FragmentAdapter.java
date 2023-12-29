package com.ensak.connect.adapters.fragmentAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class FragmentAdapter extends FragmentPagerAdapter {

    private final ArrayList<Fragment> fragmentArrayList=new ArrayList<>();

    public FragmentAdapter(@NonNull FragmentManager fm, int behavior){
        super(fm,behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragmentArrayList.get(position);
    }

    @Override
    public int getCount(){
        return fragmentArrayList.size();
    }

    public void addFragment(Fragment fragment){
        fragmentArrayList.add(fragment);
    }




}
