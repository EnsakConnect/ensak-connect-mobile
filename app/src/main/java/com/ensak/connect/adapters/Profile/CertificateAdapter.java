package com.ensak.connect.adapters.Profile;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.ensak.connect.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ensak.connect.model.Education;
import com.ensak.connect.repository.profile.model.CertificateRequest;
import com.ensak.connect.repository.profile.model.CertificateResponse;
import com.ensak.connect.utils.DateUtil;
import com.ensak.connect.presentation.profile.EducationEditActivity;

import java.util.ArrayList;
import java.util.List;

public class CertificateAdapter extends RecyclerView.Adapter<CertificateAdapter.CertificateViewHolder> {

    private List<CertificateResponse> certificateResponseList;
    private Context context;

    public interface OnCertificateDeleteListener {
        void onCertificateDelete(int certificateId);
    }

    private OnCertificateDeleteListener deleteListener;
    public void setOnCertificateDeleteListener(OnCertificateDeleteListener listener) {
        this.deleteListener = listener;
    }


    public CertificateAdapter(Context context, List<CertificateResponse> certificateResponseList) {
        this.context = context;
        this.certificateResponseList = certificateResponseList != null ? certificateResponseList : new ArrayList<>();
    }


    @NonNull
    @Override
    public CertificateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.certificates_fragement, parent, false);
        return new CertificateViewHolder(itemView, context, this);
    }


    @Override
    public void onBindViewHolder(@NonNull CertificateViewHolder holder, int position) {
        CertificateResponse certificateResponse = certificateResponseList.get(position);
        holder.bind(certificateResponse, context);
    }

    @Override
    public int getItemCount() {
        return certificateResponseList.size();
    }

    static class CertificateViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView, linkTextView;

        ImageView  iconDelete;
        private OnCertificateDeleteListener deleteListener;
        private List<CertificateResponse> certificateResponseList;
        private final CertificateAdapter adapter;




        CertificateViewHolder(View itemView, Context context, CertificateAdapter  certificateAdapter){
            super(itemView);
            this.adapter = certificateAdapter;
            nameTextView = itemView.findViewById(R.id.certificationTitle);
            linkTextView = itemView.findViewById(R.id.certificateLink);
            iconDelete = itemView.findViewById(R.id.iconDelete);
        }

        void bind(CertificateResponse certificateResponse, Context context) {
            if (certificateResponse == null) {
                return;
            }
            nameTextView.setText(certificateResponse.getName());
            linkTextView.setText(certificateResponse.getLink());


            if (iconDelete != null) {
                iconDelete.setOnClickListener(v -> {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && adapter.deleteListener != null) {
                        CertificateResponse certificateToDelete = adapter.certificateResponseList.get(position);
                        adapter.deleteListener.onCertificateDelete(certificateToDelete.getId());
                    }
                });
            }

        }
    }

}