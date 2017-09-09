package com.thirty.java.newsapp;

/**
 * Created by 321yy on 2017/9/9.
 */

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MyFilterAdapter extends RecyclerView.Adapter<MyFilterAdapter.ViewHolder> implements View.OnClickListener {
    public String[] mFilterDataSet;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mFilterWord;
        public LinearLayout mFilterView;

        public ViewHolder(LinearLayout v) {
            super(v);
            mFilterView = v;
            mFilterWord = (TextView) v.findViewById(R.id.filter_word);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyFilterAdapter(String[] myDataset) {
        mFilterDataSet = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyFilterAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                         int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_filter_word_view, parent, false);
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
        holder.mFilterWord.setText(mFilterDataSet[position]);
        holder.itemView.setTag(position);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mFilterDataSet.length;
    }

    private OnItemClickListener mOnItemClickListener = null;

    public static interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取position
            mOnItemClickListener.onItemClick(v, (int) v.getTag());
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }
}
