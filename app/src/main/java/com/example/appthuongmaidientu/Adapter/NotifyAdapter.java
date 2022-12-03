package com.example.appthuongmaidientu.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appthuongmaidientu.R;
import com.example.appthuongmaidientu.model.NotifyList;

import java.util.List;

public class NotifyAdapter extends RecyclerView.Adapter<NotifyAdapter.ViewHolder> {
    List<NotifyList> notifyLists;
    Context context;

    public NotifyAdapter(List<NotifyList> notifyLists, Context context) {
        this.notifyLists = notifyLists;
        this.context = context;
    }

    @NonNull
    @Override
    public NotifyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.notify_item_layout,null));
    }

    @Override
    public void onBindViewHolder(@NonNull NotifyAdapter.ViewHolder holder, int position) {
        NotifyList notifyList = notifyLists.get(position);
        holder.content.setText(notifyList.getContent());
    }
    public void update(List<NotifyList> notifyLists){
        this.notifyLists=notifyLists;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return notifyLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout itemlayout;
        TextView content,time;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemlayout=itemView.findViewById(R.id.layout_notifyitem);
            content=itemView.findViewById(R.id.ndNotify);
            time=itemView.findViewById(R.id.timeNotify);
        }
    }
}
