package com.java.thirty.newsapp;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> implements View.OnClickListener {
    public BriefNews[] mDataset;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mNewTitle;
        public TextView mNewsText;
        public LinearLayout mNewsView;

        public ViewHolder(LinearLayout v) {
            super(v);
            mNewsView = v;
            mNewTitle = (TextView) v.findViewById(R.id.info_text);
            mNewsText = (TextView) v.findViewById(R.id.news_text);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(BriefNews[] myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_card_view, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder((LinearLayout) v);
        v.setOnClickListener(this);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.mNewTitle.setText(mDataset[position].newsTitle);
        holder.mNewsText.setText(mDataset[position].newsIntro);
        boolean isRead = DatabaseApi.isRead(mDataset[position].newsID);
        if(!isRead){
            boolean night_mode = MyApplication.night_mode;
            if (night_mode){
                holder.mNewTitle.setTextColor(Color.argb(255, 185, 185, 185));
                holder.mNewsText.setTextColor(Color.argb(255, 185, 185, 185));
            }
            else {
                holder.mNewTitle.setTextColor(Color.BLACK);
                holder.mNewsText.setTextColor(Color.BLACK);
            }
        }
        else{
            holder.mNewTitle.setTextColor(Color.GRAY);
            holder.mNewsText.setTextColor(Color.GRAY);
        }
        holder.itemView.setTag(position);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.length;
    }

    private OnItemClickListener mOnItemClickListener = null;

    public static interface OnItemClickListener {
        void onItemClick(View view , int position);
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取position
            mOnItemClickListener.onItemClick(v,(int)v.getTag());
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

}
