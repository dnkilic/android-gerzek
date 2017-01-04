package com.dnkilic.stupid.view;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.dnkilic.stupid.MainActivity;
import com.dnkilic.stupid.R;

import java.util.ArrayList;
import java.util.Random;

public class ViewController implements ProposalTouchListener{

    private MainActivity mAct;
    private ViewInteractListener mViewInteractListener;

    private RecyclerView rvDialog;
    private RecyclerView.Adapter mAdapterDialog;
    private ArrayList<Dialog> mDialogItems;
    private boolean mIsTextInput = false;

    private RecyclerView rvProposals;
    private RecyclerView.Adapter mAdapterProposals;
    private ArrayList<Proposal> mProposalItems;
    private ArrayList<Proposal> mProposalList;

    private EditText etMessage;

    public ViewController(MainActivity activity) {
        this.mAct = activity;
        this.mViewInteractListener = activity;
        this.mProposalList = new ArrayList<>();
        mProposalList.add(new Proposal("twitter"));
        mProposalList.add(new Proposal("facebook"));
        mProposalList.add(new Proposal("hakkında"));
        mProposalList.add(new Proposal("kimsin"));
        mProposalList.add(new Proposal("kamera"));
        mProposalList.add(new Proposal("selfie"));
        mProposalList.add(new Proposal("fotoğraf"));
        mProposalList.add(new Proposal("whatsapp"));
        mProposalList.add(new Proposal("uygulamayı kapat"));
        mProposalList.add(new Proposal("bluetooth kapa"));
        mProposalList.add(new Proposal("bluetooth aç"));
        mProposalList.add(new Proposal("akbil"));
        mProposalList.add(new Proposal("seni seviyorum"));
        mProposalList.add(new Proposal("evlen benimle"));
        mProposalList.add(new Proposal("günaydın"));
        mProposalList.add(new Proposal("sıkıldım"));
        mProposalList.add(new Proposal("deli"));
        mProposalList.add(new Proposal("orada mısın"));
        mProposalList.add(new Proposal("saat"));
        mProposalList.add(new Proposal("kahve yapar mısın"));
        mProposalList.add(new Proposal("şarkı söyle"));
        mProposalList.add(new Proposal("nasılsın"));
        mProposalList.add(new Proposal("fıkra"));
        mProposalList.add(new Proposal("masal"));
        mProposalList.add(new Proposal("tekerleme"));
        mProposalList.add(new Proposal("yalnızım"));
    }

    public void initialize() {
        rvDialog = (RecyclerView) mAct.findViewById(R.id.rvDialog);
        LinearLayoutManager layoutManagerDialogs = new LinearLayoutManager(mAct);
        rvDialog.setLayoutManager(layoutManagerDialogs);
        mDialogItems = new ArrayList<>();
        mAdapterDialog = new DialogAdapter(mDialogItems, mAct);
        rvDialog.setAdapter(mAdapterDialog);

        rvProposals = (RecyclerView) mAct.findViewById(R.id.rvProposals);
        LinearLayoutManager layoutManagerProposals = new LinearLayoutManager(mAct);
        layoutManagerProposals.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvProposals.setLayoutManager(layoutManagerProposals);
        mProposalItems = new ArrayList<>();
        mAdapterProposals = new ProposalDialogAdapter(mProposalItems, mAct, this);
        rvProposals.setAdapter(mAdapterProposals);

        etMessage = (EditText) mAct.findViewById(R.id.etMessage);
        etMessage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(final CharSequence s, int start, int before, int count) {
                mIsTextInput = true;
                etMessage.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.input_text, 0);
            }
            @Override
            public void afterTextChanged(final Editable s) {
                if(s.length() == 0)
                {
                    mIsTextInput = false;
                    etMessage.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.input_microphone, 0);
                }
            }
        });

        etMessage.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(event.getAction() == MotionEvent.ACTION_UP) {
                    if(etMessage.getCompoundDrawables()[2] != null){
                        if(event.getX() >= (etMessage.getRight()- etMessage.getLeft() - etMessage.getCompoundDrawables()[2].getBounds().width())) {
                            if(mIsTextInput)
                            {
                                if(etMessage.getText().length() >= 1)
                                {
                                    InputMethodManager imm = (InputMethodManager)mAct.getSystemService(Context.INPUT_METHOD_SERVICE);
                                    imm.hideSoftInputFromWindow(etMessage.getWindowToken(), 0);
                                    mViewInteractListener.sendTextMessage(etMessage.getText().toString());
                                    etMessage.setText("");
                                }
                            }
                            else
                            {
                                mViewInteractListener.startRecognition();
                            }
                            etMessage.setInputType(0);
                        }
                        else
                        {
                            etMessage.setInputType(InputType.TYPE_CLASS_TEXT);
                        }
                    }
                }
                return false;
            }
        });
    }

    public void addDialogItem(Dialog dialog)
    {
        mDialogItems.add(dialog);
        rvDialog.scrollToPosition(mAdapterDialog.getItemCount()-1);
        mAdapterDialog.notifyDataSetChanged();
    }

    public void addRandomProposals() {

        while (mProposalItems.size() < 5)
        {
            Proposal proposal = getRandomProposal();
            if(!isProposalExist(proposal))
            {
                mProposalItems.add(proposal);
            }
        }

        mProposalItems.add(new Proposal("hakkında"));

        mAdapterProposals.notifyDataSetChanged();
    }

    private Proposal getRandomProposal() {
        Random randomGenerator = new Random();
        int index = randomGenerator.nextInt(mProposalList.size());
        return mProposalList.get(index);
    }

    private boolean isProposalExist(Proposal selectedProposal)
    {
        if(mProposalItems.contains(selectedProposal))
        {
            return true;
        }
        return false;
    }

    public void addProposalItem(Proposal proposal)
    {
        mProposalItems.add(proposal);
        rvProposals.scrollToPosition(mAdapterProposals.getItemCount()-1);
        mAdapterProposals.notifyDataSetChanged();
    }

    @Override
    public void onProposalTouch(Proposal proposal) {
        mViewInteractListener.onProposalSelected(proposal);
    }

    public void disableActions() {
        etMessage.setEnabled(false);
        rvProposals.setEnabled(false);
    }

    public void enableActions() {
        etMessage.setEnabled(true);
        rvProposals.setEnabled(true);
    }

    public void clearProposals() {
        mProposalItems.clear();
        mAdapterProposals.notifyDataSetChanged();
    }
}
