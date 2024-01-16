package com.ensak.connect.adapters.notifications;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ensak.connect.R;
import com.ensak.connect.repository.notification.model.NotificationResponse;
import java.text.SimpleDateFormat;

import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationHolder> {

    private List<NotificationResponse> notificationslist;

    public NotificationAdapter(List<NotificationResponse> notificationslist) {
        this.notificationslist = notificationslist;
    }

    @NonNull
    @Override
    public NotificationHolder onCreateViewHolder(@NonNull ViewGroup parent,int viewType){
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.notification_item,parent,false);

        return new NotificationHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationHolder holder,int position){
        NotificationResponse notificationResponse =notificationslist.get(position);

        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM, HH:mm");
       String dates =sdf.format(notificationResponse.getDate());

        holder.author.setText(notificationResponse.getUser().getFirstname());
        holder.status.setText(notificationResponse.getStatus());
        holder.date.setText(dates);
        holder.message.setText(notificationResponse.getMessage());
        holder.category.setText(notificationResponse.getCategory());
    }

    @Override
    public int getItemCount(){
        return notificationslist.size();
    }
}
