package com.dnkilic.stupid.view;

/**
 * Created by dogan.kilic on 3.01.2017.
 */

public interface ViewInteractListener {
    void startRecognition();
    void sendTextMessage(String message);
    void onProposalSelected(Proposal proposal);
}
