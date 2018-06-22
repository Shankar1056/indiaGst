package com.bigappcompany.gstindia.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigappcompany.gstindia.R;
import com.bigappcompany.gstindia.model.NotificationModel;

import java.util.ArrayList;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {
    private final OnItemClickListener mListener;
    ArrayList<NotificationModel> mItemList;

    public NotificationAdapter(OnItemClickListener listener) {
        mListener = listener;
        mItemList = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notification_list, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        NotificationModel item = mItemList.get(position);
        holder.date_txt.setText(item.getNotification_no());
        holder.subject_txt.setText(item.getSubject());
    }

    @Override
    public int getItemCount() {
        return mItemList.size();
    }

    public interface OnItemClickListener {
        void onItemClick(NotificationModel item, int position);

    }

    public void addItem(NotificationModel item) {
        mItemList.add(item);
        notifyItemInserted(mItemList.size() - 1);
    }

    public void clear() {
        mItemList.clear();
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView date_txt,english_txt,hindi_txt,subject_txt;

        ViewHolder(View itemView) {
            super(itemView);
            date_txt=itemView.findViewById(R.id.date_txt);
            english_txt=itemView.findViewById(R.id.english_txt);
            hindi_txt=itemView.findViewById(R.id.hindi_txt);
            subject_txt=itemView.findViewById(R.id.subject_txt);

            english_txt.setOnClickListener(this);
            hindi_txt.setOnClickListener(this);

        }


        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.english_txt:
                    mListener.onItemClick(mItemList.get(getAdapterPosition()),
                            getAdapterPosition());
                    break;
                case R.id.hindi_txt:
                    mListener.onItemClick(mItemList.get(getAdapterPosition()),
                            getAdapterPosition());
                    break;
            }
        }

    }
}