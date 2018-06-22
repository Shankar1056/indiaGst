package com.bigappcompany.gstindia.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigappcompany.gstindia.R;
import com.bigappcompany.gstindia.model.ChapterModel;

import java.util.ArrayList;

/**
 * @author Samuel Robert <sam@spotsoon.com>
 * @created on 2017-02-24 at 4:54 PM
 */
public class ChapterAdapter extends RecyclerView.Adapter<ChapterAdapter.ViewHolder> {
    private final ArrayList<ChapterModel> mItemList;
    private OnItemClickListener mListener;

    public ChapterAdapter(OnItemClickListener listener) {
        mListener = listener;
        mItemList = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gst_list, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ChapterModel item = mItemList.get(position);

        holder.titleTV.setText(item.getChapterName());
    }

    @Override
    public int getItemCount() {
        return mItemList.size();
    }

    public void addItem(ChapterModel item) {
        mItemList.add(item);
        notifyItemInserted(mItemList.size() - 1);
    }

    public interface OnItemClickListener {
        void onItemClick(ChapterModel chapterModel, int position);

        void onDownload(ChapterModel item, int position);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleTV;
        ImageView downloadIV;

        ViewHolder(View itemView) {
            super(itemView);

            titleTV = (TextView) itemView.findViewById(R.id.tv_title);
            downloadIV = (ImageView) itemView.findViewById(R.id.iv_download);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onItemClick(mItemList.get(getAdapterPosition()),
                            getAdapterPosition());
                }
            });
            downloadIV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onDownload(mItemList.get(getAdapterPosition()), getAdapterPosition());
                }
            });

        }
    }
}
