package com.dnkilic.application41.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.dnkilic.application41.R;

public class DialogViewHolderInformation extends RecyclerView.ViewHolder {

    public TextView mInformationMessage;

    public DialogViewHolderInformation(View itemView) {
        super(itemView);

        mInformationMessage = (TextView) itemView.findViewById(R.id.tvInformationMessage);
    }

    public TextView getTextView()
    {
        return mInformationMessage;
    }


}
