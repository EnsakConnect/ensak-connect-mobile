package com.ensak.connect.adapters.notifications;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ensak.connect.R;

public class NotificationHolder extends RecyclerView.ViewHolder {

    TextView author,message,status,date,category;

    public NotificationHolder(@NonNull View itemView){
        super(itemView);
        author=itemView.findViewById(R.id.author_notif);
        message=itemView.findViewById(R.id.notification_message);
        status=itemView.findViewById(R.id.notification_status);
        date=itemView.findViewById(R.id.notification_date);
        category=itemView.findViewById(R.id.notification_category);


    }
}
