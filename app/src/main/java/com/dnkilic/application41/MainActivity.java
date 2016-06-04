package com.dnkilic.application41;

import android.content.Intent;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeechService;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements RecognitionResultListener {

    private RecognitionManager recognitionManager;
    private SpeakerManager speakerManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        speakerManager = new SpeakerManager(this);
        recognitionManager = new RecognitionManager(this);
    }

    public void start(View v)
    {
        recognitionManager.start();
    }

    @Override
    public void onResult(String result) {
        CommandAnalyzer analyzer = new CommandAnalyzer();
        speakerManager.speak(analyzer.analyze(result));
    }
}
