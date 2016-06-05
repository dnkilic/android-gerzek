package com.dnkilic.application41.view;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.dnkilic.application41.R;

import java.util.ArrayList;

public class DialogAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_DIALOG_SENT = 0;
    private static final int TYPE_DIALOG_RECEIVED = 1;
    private static final int TYPE_MESSAGE_ERROR = 2;
    private static final int TYPE_MESSAGE_INFORMATION = 3;
    private static final int TYPE_MESSAGE_NOT_IMPLEMENTED = 4;

    private ArrayList<Dialog> mItems;
    private DisplayMetrics mMetrics;

    public DialogAdapter(ArrayList<Dialog> items, Activity activity) {
        mItems = items;

        mMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(mMetrics);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {

            case TYPE_DIALOG_SENT:
                view = inflater.inflate(R.layout.dialog_item_sent, parent, false);
                LinearLayout.LayoutParams lpSent = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1f);
                lpSent.setMargins(mMetrics.widthPixels/3, mMetrics.widthPixels/100, 0, 0);
                view.setLayoutParams(lpSent);
                viewHolder = new DialogViewHolderSent(view);
                break;
            case TYPE_DIALOG_RECEIVED:
                view = inflater.inflate(R.layout.dialog_item_received, parent, false);
                LinearLayout.LayoutParams lpReceived = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1f);
                lpReceived.setMargins(0, mMetrics.widthPixels/100, mMetrics.widthPixels/3, 0);
                view.setLayoutParams(lpReceived);
                viewHolder = new DialogViewHolderReceived(view);
                break;
            case TYPE_MESSAGE_ERROR:
                view = inflater.inflate(R.layout.dialog_item_error, parent, false);
                viewHolder = new DialogViewHolderError(view);
                LinearLayout.LayoutParams lpError = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                lpError.setMargins(mMetrics.widthPixels/6,  mMetrics.widthPixels/100, mMetrics.widthPixels/6, 0);
                view.setLayoutParams(lpError);
                break;
            case TYPE_MESSAGE_INFORMATION:
                view = inflater.inflate(R.layout.dialog_item_information, parent, false);
                viewHolder = new DialogViewHolderInformation(view);
                LinearLayout.LayoutParams lpInfo = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                lpInfo.setMargins(mMetrics.widthPixels/6,  mMetrics.widthPixels/100, mMetrics.widthPixels/6, 0);
                view.setLayoutParams(lpInfo);
                break;
            case TYPE_MESSAGE_NOT_IMPLEMENTED:
                view = inflater.inflate(R.layout.dialog_item_custom, parent, false);
                viewHolder = new DialogViewHolderCustom(view);
                LinearLayout.LayoutParams lpCustom = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                lpCustom.setMargins(mMetrics.widthPixels/6,  mMetrics.widthPixels/100, mMetrics.widthPixels/6, 0);
                view.setLayoutParams(lpCustom);
                break;
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        switch (holder.getItemViewType())
        {
            case TYPE_DIALOG_SENT:
                DialogViewHolderSent sentHolder = (DialogViewHolderSent) holder;
                sentHolder.getTextView().setText(mItems.get(position).getDialogText());
                break;
            case TYPE_DIALOG_RECEIVED:
                DialogViewHolderReceived receivedHolder = (DialogViewHolderReceived) holder;
                receivedHolder.getTextView().setText(mItems.get(position).getDialogText());
                break;
            case TYPE_MESSAGE_ERROR:
                DialogViewHolderError errorHolder = (DialogViewHolderError) holder;
                errorHolder.getTextView().setText(mItems.get(position).getDialogText());
                break;
            case TYPE_MESSAGE_INFORMATION:
                DialogViewHolderInformation informationHolder = (DialogViewHolderInformation) holder;
                informationHolder.getTextView().setText(mItems.get(position).getDialogText());
                break;
            case TYPE_MESSAGE_NOT_IMPLEMENTED:
                DialogViewHolderCustom customHolder = (DialogViewHolderCustom) holder;
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    @Override
    public int getItemViewType(int position) {

        int currentRowType = 0;

        switch (mItems.get(position).getType())
        {
            case TYPE_DIALOG_SENT:
                currentRowType = TYPE_DIALOG_SENT;
                break;
            case TYPE_DIALOG_RECEIVED:
                currentRowType = TYPE_DIALOG_RECEIVED;
                break;
            case TYPE_MESSAGE_ERROR:
                currentRowType = TYPE_MESSAGE_ERROR;
                break;
            case TYPE_MESSAGE_INFORMATION:
                currentRowType = TYPE_MESSAGE_INFORMATION;
                break;
            case TYPE_MESSAGE_NOT_IMPLEMENTED:
                currentRowType = TYPE_MESSAGE_NOT_IMPLEMENTED;
                break;
        }

        return currentRowType;
    }
}
