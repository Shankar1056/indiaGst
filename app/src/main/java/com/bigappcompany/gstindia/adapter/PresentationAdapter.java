package com.bigappcompany.gstindia.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bigappcompany.gstindia.R;
import com.bigappcompany.gstindia.model.PresentationModel;

import java.util.ArrayList;

/**
 * @author Samuel Robert <sam@spotsoon.com>
 * @created on 01 Mar 2017 at 3:00 PM
 */
public class PresentationAdapter extends RecyclerView.Adapter<PresentationAdapter.ViewHolder> {
	private final ArrayList<PresentationModel> mItemList;
	private final OnItemClickListener mListener;

	public PresentationAdapter(OnItemClickListener listener) {
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
		PresentationModel item = mItemList.get(position);

		holder.titleTV.setText(item.getTitle());
	}

	@Override
	public int getItemCount() {
		return mItemList.size();
	}

	public void addItem(PresentationModel item) {
		mItemList.add(item);
		notifyItemInserted(mItemList.size() - 1);
	}

	public interface OnItemClickListener {
		void onItemClick(PresentationModel item);
	}

	class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
		TextView titleTV;

		ViewHolder(View itemView) {
			super(itemView);

			titleTV = (TextView) itemView.findViewById(R.id.tv_title);
			itemView.findViewById(R.id.iv_download).setVisibility(View.GONE);

			itemView.setOnClickListener(this);
		}

		@Override
		public void onClick(View v) {
			mListener.onItemClick(mItemList.get(getAdapterPosition()));
		}
	}
}
