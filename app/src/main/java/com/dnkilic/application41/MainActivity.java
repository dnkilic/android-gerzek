package com.dnkilic.application41;

import android.content.Intent;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements RecognitionListener{

    private SpeechRecognizer speechRecognizer;
    private TextView tvRecognitionResult;
    private TextView tvSpeechEvents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvRecognitionResult = (TextView) findViewById(R.id.tvRecognitionResult);
        tvSpeechEvents = (TextView) findViewById(R.id.tvSpeechEvents);

        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
        speechRecognizer.setRecognitionListener(this);



    }

    public void start(View v)
    {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, new Locale("TR-tr"));


        speechRecognizer.startListening(intent);
    }

    @Override
    public void onReadyForSpeech(Bundle params) {
        tvSpeechEvents.setText("Konuşmaya Hazırım");
    }

    @Override
    public void onBeginningOfSpeech() {
        tvSpeechEvents.setText("Konuşma Başladı");

    }

    @Override
    public void onRmsChanged(float rmsdB) {}

    @Override
    public void onBufferReceived(byte[] buffer) {}

    @Override
    public void onEndOfSpeech() {
        tvSpeechEvents.setText("Konuşma Bitti");
    }

    @Override
    public void onError(int error) {

        String errorMessage = null;

        switch (error)
        {
            case SpeechRecognizer.ERROR_AUDIO:
                errorMessage = "ERROR_AUDIO";
                break;
            case SpeechRecognizer.ERROR_CLIENT:
                errorMessage = "ERROR_CLIENT";
                break;
            case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
                errorMessage = "ERROR_INSUFFICIENT_PERMISSIONS";
                break;
            case SpeechRecognizer.ERROR_NETWORK:
                errorMessage = "ERROR_NETWORK";
                break;
            case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
                errorMessage = "ERROR_NETWORK_TIMEOUT";
                break;
            case SpeechRecognizer.ERROR_NO_MATCH:
                errorMessage = "ERROR_NO_MATCH";
                break;
            case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
                errorMessage = "ERROR_RECOGNIZER_BUSY";
                break;
            case SpeechRecognizer.ERROR_SERVER:
                errorMessage = "ERROR_SERVER";
                break;
            case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
                errorMessage = "ERROR_SPEECH_TIMEOUT";
                break;
        }


        tvSpeechEvents.setText("Bir hata oluştu : " + errorMessage);
    }

    @Override
    public void onResults(Bundle results) {
        tvSpeechEvents.setText("Cevap Alındı");

        ArrayList<String> text = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
        float [] confidence = results.getFloatArray(SpeechRecognizer.CONFIDENCE_SCORES);

        int alfa= 5;

    }

    @Override
    public void onPartialResults(Bundle partialResults) {
        tvSpeechEvents.setText("Cevap Parçalı Alındı");
    }

    @Override
    public void onEvent(int eventType, Bundle params) {}
}
