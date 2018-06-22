package com.bigappcompany.gstindia.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigappcompany.gstindia.R;
import com.bigappcompany.gstindia.model.ProcedureModel;

import java.util.ArrayList;

/**
 * @author Samuel Robert <sam@spotsoon.com>
 * @created on 11 Mar 2017 at 12:56 PM
 */

public class ProcedureAdapter extends RecyclerView.Adapter<ProcedureAdapter.ViewHolder> {
	private final ArrayList<ProcedureModel> mItemList;
	private final OnItemClickListener mListener;
	
	public ProcedureAdapter(OnItemClickListener listener) {
		mListener = listener;
		mItemList = new ArrayList<>();
	}
	
	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_forms, parent, false);
		return new ViewHolder(itemView);
	}
	
	@Override
	public void onBindViewHolder(ViewHolder holder, int position) {
		ProcedureModel item = mItemList.get(position);
		
		holder.titleTV.setText(item.getTitle());
	}
	
	@Override
	public int getItemCount() {
		return mItemList.size();
	}
	
	public void addItem(ProcedureModel item) {
		mItemList.add(item);
		notifyItemInserted(mItemList.size() - 1);
	}
	
	public interface OnItemClickListener {
		void onItemClick(ProcedureModel item);
		
		void onDownload(ProcedureModel item);
	}
	
	class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
		TextView titleTV;
		ImageView downloadIV;
		
		ViewHolder(View itemView) {
			super(itemView);
			
			titleTV = (TextView) itemView.findViewById(R.id.tv_title);
			downloadIV = (ImageView) itemView.findViewById(R.id.iv_download);
			
			downloadIV.setOnClickListener(this);
			itemView.setOnClickListener(this);
		}
		
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
				case R.id.iv_download:
					mListener.onDownload(mItemList.get(getAdapterPosition()));
					break;
				
				default:
					mListener.onItemClick(mItemList.get(getAdapterPosition()));
			}
		}
	}
}
