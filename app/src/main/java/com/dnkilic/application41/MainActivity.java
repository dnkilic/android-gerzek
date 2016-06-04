package com.dnkilic.application41;

import android.bluetooth.BluetoothAdapter;
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

        Command command = analyzer.analyze(result.toLowerCase());

        switch (command.getCommandType())
        {
            case CommandAnalyzer.CLOSE_BLUETOOTH:
                if(setBluetooth(false))
                {
                    speakerManager.speak(command.getAnnounce());
                }
                else
                {
                    speakerManager.speak("Bilmediğim bir sebepten dolayı bluetooth'u kapatamadım.");
                }
                break;
            case CommandAnalyzer.OPEN_BLUETOOTH:
                if(setBluetooth(true))
                {
                    speakerManager.speak(command.getAnnounce());
                }
                else
                {
                    speakerManager.speak("Bilmediğim bir sebepten dolayı bluetooth'u açamadım.");
                }
                break;
            case CommandAnalyzer.EXECUTE_UNKNOWN_COMMAND:
                speakerManager.speak(command.getAnnounce());
                break;
            case CommandAnalyzer.MAKE_FUNNY_ANSWER:
                speakerManager.speak(command.getAnnounce());
                break;
            case CommandAnalyzer.CLOSE_APPLICATION:
                speakerManager.speak(command.getAnnounce());
                finish();
                break;
        }
    }

    public static boolean setBluetooth(boolean enable) {
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        boolean isEnabled = bluetoothAdapter.isEnabled();
        if (enable && !isEnabled) {
            return bluetoothAdapter.enable();
        }
        else if(!enable && isEnabled) {
            return bluetoothAdapter.disable();
        }
        // No need to change bluetooth state
        return true;
    }


}
