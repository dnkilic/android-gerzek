package com.dnkilic.application41.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.dnkilic.application41.R;

public class DialogViewHolderReceived extends RecyclerView.ViewHolder {

    private TextView mDialogMessage;

    public DialogViewHolderReceived(View itemView) {
        super(itemView);

        mDialogMessage = (TextView) itemView.findViewById(R.id.tvMessage);
    }

    public TextView getTextView()
    {
        return mDialogMessage;
    }
}
