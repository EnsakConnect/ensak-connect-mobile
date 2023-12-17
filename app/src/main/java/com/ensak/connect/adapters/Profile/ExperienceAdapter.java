package com.ensak.connect.adapters.Profile;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.ensak.connect.R;
import com.ensak.connect.models.Experience;
import com.ensak.connect.utils.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ExperienceAdapter extends RecyclerView.Adapter<ExperienceAdapter.ExperienceViewHolder> {

    private List<Experience> experienceList;

    public ExperienceAdapter(List<Experience> experienceList) {
        this.experienceList = experienceList;
    }

    @NonNull
    @Override
    public ExperienceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.expereince_fragment, parent, false);
        return new ExperienceViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ExperienceViewHolder holder, int position) {
        Experience experience = experienceList.get(position);
        holder.bind(experience);
    }

    @Override
    public int getItemCount() {
        return experienceList.size();
    }

    static class ExperienceViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView,

                locationTextView, periodTextView, descriptionTextView;

        ExperienceViewHolder(View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.experienceTitle);
//            companyTextView = itemView.findViewById(R.id.companyLocation);
            locationTextView = itemView.findViewById(R.id.companyLocation);
            periodTextView = itemView.findViewById(R.id.experiencePeriod);
            descriptionTextView = itemView.findViewById(R.id.experienceDescription);
        }

        void bind(Experience experience) {
            titleTextView.setText(experience.getPositionTitle());
//            companyTextView.setText(experience.getCompanyName());
            locationTextView.setText(experience.getLocation());
            periodTextView.setText(Utils.formatPeriod(experience.getStartDate(), experience.getEndDate()));
<<<<<<< HEAD
            descriptionTextView.setText(experience.getDescription());
        }

            periodTextView.setText(formatPeriod(experience.getStartDate(), experience.getEndDate()));
=======
>>>>>>> 67b5a5b (Education Adapter)
            descriptionTextView.setText(experience.getDescription());
        }

    }
}


