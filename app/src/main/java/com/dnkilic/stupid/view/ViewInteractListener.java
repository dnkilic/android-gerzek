package com.dnkilic.stupid.view;

public interface ViewInteractListener {
    void startRecognition();
    void sendTextMessage(String message);
    void onProposalSelected(Proposal proposal);
}
