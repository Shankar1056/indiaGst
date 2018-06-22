package com.bigappcompany.gstindia.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigappcompany.gstindia.R;
import com.bigappcompany.gstindia.model.ActModel;

import java.util.ArrayList;

/**
 * @author Samuel Robert <sam@spotsoon.com>
 * @created on 2017-02-24 at 4:07 PM
 */
public class ActAdapter extends RecyclerView.Adapter<ActAdapter.ViewHolder> {

	private final ArrayList<ActModel> mItemList;
	private final OnItemClickListener mListener;

	public ActAdapter(OnItemClickListener listener) {
		this.mListener = listener;

		mItemList = new ArrayList<>();
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gst_list, parent, false);
		return new ViewHolder(itemView);
	}

	@Override
	public void onBindViewHolder(ViewHolder holder, int position) {
		ActModel item = mItemList.get(position);

		holder.titleTV.setText(item.getActName());
	}

	@Override
	public int getItemCount() {
		return mItemList.size();
	}

	public void addItem(ActModel item) {
		mItemList.add(item);
		notifyItemInserted(mItemList.size() - 1);
	}

	public void clearItems() {
		mItemList.clear();
		notifyDataSetChanged();
	}

	public interface OnItemClickListener {
		void onItemClick(ActModel item, int position);

		void onDownload(ActModel item, int position);
	}

	class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
		TextView titleTV;
		ImageView downloadIV;

		ViewHolder(View itemView) {
			super(itemView);

			titleTV = (TextView) itemView.findViewById(R.id.tv_title);
			downloadIV = (ImageView) itemView.findViewById(R.id.iv_download);

			itemView.setOnClickListener(this);
			downloadIV.setOnClickListener(this);
		}

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
				case R.id.iv_download:
					mListener.onDownload(mItemList.get(getAdapterPosition()), getAdapterPosition());
					break;

				default:
					mListener.onItemClick(mItemList.get(getAdapterPosition()), getAdapterPosition());
					break;
			}
		}
	}
}
