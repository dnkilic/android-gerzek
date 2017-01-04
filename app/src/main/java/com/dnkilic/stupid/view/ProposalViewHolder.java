package com.dnkilic.stupid.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.dnkilic.stupid.R;


public class ProposalViewHolder extends RecyclerView.ViewHolder {

    private TextView mDialogMessage;
    private Proposal mProposal;

    public ProposalViewHolder(View itemView, final ProposalTouchListener listener) {
        super(itemView);

        mDialogMessage = (TextView) itemView.findViewById(R.id.tvMessage);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onProposalTouch(mProposal);
            }
        });
    }

    public void setProposal(Proposal proposal)
    {
        mDialogMessage.setText(proposal.getProposalText());
        mProposal = proposal;
    }
}
