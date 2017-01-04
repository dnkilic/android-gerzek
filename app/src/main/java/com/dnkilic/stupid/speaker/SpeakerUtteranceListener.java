package com.dnkilic.stupid.speaker;

public interface SpeakerUtteranceListener {
    void onStartTTS(String announce);
    void onDoneTTS();
    void onErrorTTS();
}
