package com.ensak.connect.adapters.Profile;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.ensak.connect.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ensak.connect.models.Education;
import com.ensak.connect.utils.Utils;

import java.util.List;

public class EducationAdapter extends RecyclerView.Adapter<EducationAdapter.EducationViewHolder> {

    private List<Education> educationList;

    public EducationAdapter(List<Education> educationList) {
        this.educationList = educationList;
    }

    @NonNull
    @Override
    public EducationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.expereince_fragment, parent, false);
        return new EducationViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull EducationViewHolder holder, int position) {
        Education education = educationList.get(position);
        holder.bind(education);
    }

    @Override
    public int getItemCount() {
        return educationList.size();
    }

    static class EducationViewHolder extends RecyclerView.ViewHolder {
        TextView fieldTextView, degreeTextView, schoolTextView,locationTextView, descriptionTextView,periodTextView;

        EducationViewHolder(View itemView) {
            super(itemView);
            fieldTextView = itemView.findViewById(R.id.experienceTitle);
            schoolTextView = itemView.findViewById(R.id.companyLocation);
//            locationTextView = itemView.findViewById(R.id.companyLocation);
            periodTextView = itemView.findViewById(R.id.experiencePeriod);
            descriptionTextView = itemView.findViewById(R.id.experienceDescription);
        }

        void bind(Education education) {
            fieldTextView.setText(education.getField());
            schoolTextView.setText(education.getSchool());
//            locationTextView.setText(education.getSchool());
            periodTextView.setText(Utils.formatPeriod(education.getStartDate(), education.getEndDate()));
            descriptionTextView.setText(education.getDescription());
        }
    }
}
