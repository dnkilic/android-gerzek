package com.dnkilic.application41;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.widget.TextView;
import android.widget.Toast;

import com.dnkilic.application41.view.Dialog;
import com.dnkilic.application41.view.DialogType;

import java.util.ArrayList;
import java.util.Locale;


public class RecognitionManager implements RecognitionListener {

    private MainActivity mAct;
    private SpeechRecognizer speechRecognizer;
    //private TextView tvRecognitionResult;
    //private TextView tvSpeechEvents;
    private RecognitionResultListener mListener;

    public RecognitionManager(MainActivity act)
    {
        mAct = act;
        mListener = (RecognitionResultListener) act;

       /* tvRecognitionResult = (TextView) act.findViewById(R.id.tvRecognitionResult);
        tvSpeechEvents = (TextView) act.findViewById(R.id.tvSpeechEvents);*/

        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(act);
        speechRecognizer.setRecognitionListener(this);

    }

    public void start() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_MINIMUM_LENGTH_MILLIS, 500);
        intent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_COMPLETE_SILENCE_LENGTH_MILLIS, 500);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, new Locale("TR-tr"));
        speechRecognizer.startListening(intent);
    }

    @Override
    public void onReadyForSpeech(Bundle params) {
        //mAct.addItem(new Dialog("Dinlemeye Hazırım", DialogType.TYPE_MESSAGE_INFORMATION));
    }

    @Override
    public void onBeginningOfSpeech() {
        //mAct.addItem(new Dialog("Konuşma Başladı", DialogType.TYPE_MESSAGE_INFORMATION));
    }

    @Override
    public void onRmsChanged(float rmsdB) {}

    @Override
    public void onBufferReceived(byte[] buffer) {}

    @Override
    public void onEndOfSpeech() {
        //mAct.addItem(new Dialog("Konuşma Bitti", DialogType.TYPE_MESSAGE_INFORMATION));
    }

    @Override
    public void onError(int error) {

        String errorMessage = null;

        switch (error)
        {
            case SpeechRecognizer.ERROR_AUDIO:
                errorMessage = "ERROR_AUDIO";
                mAct.addItem(new Dialog("Bir Hata Oluştu : " + errorMessage, DialogType.TYPE_MESSAGE_ERROR));
                break;
            case SpeechRecognizer.ERROR_CLIENT:
                errorMessage = "ERROR_CLIENT";
                mAct.addItem(new Dialog("Bir Hata Oluştu : " + errorMessage, DialogType.TYPE_MESSAGE_ERROR));

                break;
            case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
                errorMessage = "ERROR_INSUFFICIENT_PERMISSIONS";
                mAct.addItem(new Dialog("Bir Hata Oluştu : " + errorMessage, DialogType.TYPE_MESSAGE_ERROR));
                break;
            case SpeechRecognizer.ERROR_NETWORK:
                errorMessage = "ERROR_NETWORK";
                mAct.addItem(new Dialog("Bir Hata Oluştu : " + errorMessage, DialogType.TYPE_MESSAGE_ERROR));
                break;
            case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
                errorMessage = "ERROR_NETWORK_TIMEOUT";
                mAct.addItem(new Dialog("Bir Hata Oluştu : " + errorMessage, DialogType.TYPE_MESSAGE_ERROR));
                break;
            case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
                errorMessage = "ERROR_RECOGNIZER_BUSY";
                mAct.addItem(new Dialog("Bir Hata Oluştu : " + errorMessage, DialogType.TYPE_MESSAGE_ERROR));
                break;
            case SpeechRecognizer.ERROR_SERVER:
                errorMessage = "ERROR_SERVER";
                mAct.addItem(new Dialog("Bir Hata Oluştu : " + errorMessage, DialogType.TYPE_MESSAGE_ERROR));
                break;
        }

    }

    @Override
    public void onResults(Bundle results) {

        ArrayList<String> text = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
        float [] confidence = results.getFloatArray(SpeechRecognizer.CONFIDENCE_SCORES);

        mListener.onResult(text.get(0));
        Toast.makeText(mAct, text.toString(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onPartialResults(Bundle partialResults) {
        //tvSpeechEvents.setText("Cevap Parçalı Alındı");
    }

    @Override
    public void onEvent(int eventType, Bundle params) {}
}
