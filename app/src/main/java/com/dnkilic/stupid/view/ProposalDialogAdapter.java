package com.dnkilic.stupid.view;

import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import android.app.Activity;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.dnkilic.stupid.R;

public class ProposalDialogAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {

    private static final int TYPE_DIALOG_SENT = 0;

    private ArrayList<Proposal> mItems;
    private DisplayMetrics mMetrics;
    private ProposalTouchListener mListener;

    public ProposalDialogAdapter(ArrayList<Proposal> items, Activity activity, ProposalTouchListener listener) {
        mItems = items;
        mListener = listener;
        mMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(mMetrics);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        view = inflater.inflate(R.layout.dialog_item_proposal, parent, false);
        LinearLayout.LayoutParams lpSent = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1f);
        lpSent.setMargins(10, 0, 10, 0);
        view.setLayoutParams(lpSent);
        viewHolder = new ProposalViewHolder(view, mListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ProposalViewHolder sentHolder = (ProposalViewHolder) holder;
        sentHolder.setProposal(mItems.get(position));
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    @Override
    public int getItemViewType(int position) {

        int currentRowType = 0;
        currentRowType = TYPE_DIALOG_SENT;
        return currentRowType;
    }
}
