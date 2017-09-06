package com.thirty.java.newsapp;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MyInterestAdapter extends RecyclerView.Adapter<MyInterestAdapter.ViewHolder> {
    private String[] mInterestDataset;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mInterestText;
        public LinearLayout mInterestView;

        public ViewHolder(LinearLayout v) {
            super(v);
            mInterestView = v;
            mInterestText = (TextView) v.findViewById(R.id.interest_button);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyInterestAdapter(String[] myDataset) {
        mInterestDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyInterestAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                           int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_interest_card_view, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder((LinearLayout) v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.mInterestText.setText(mInterestDataset[position]);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mInterestDataset.length;
    }
}
