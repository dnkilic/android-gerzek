package com.dnkilic.application41.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.dnkilic.application41.R;

public class DialogViewHolderError extends RecyclerView.ViewHolder {

    public TextView mErrorMessage;

    public DialogViewHolderError(View itemView) {
        super(itemView);

        mErrorMessage = (TextView) itemView.findViewById(R.id.tvErrorMessage);
    }

    public TextView getTextView()
    {
        return mErrorMessage;
    }

}
