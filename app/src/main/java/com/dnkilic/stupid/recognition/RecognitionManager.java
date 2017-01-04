package com.dnkilic.stupid.recognition;

import android.app.Activity;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;

import com.dnkilic.stupid.MainActivity;

public class RecognitionManager implements  LanguageAvailabilityListener{

    private SpeechRecognizer mSpeechRecognizer;
    private boolean mIsFirstRequest = true;
    private Activity mActivity;

    public RecognitionManager(MainActivity act)
    {
        mSpeechRecognizer = SpeechRecognizer.createSpeechRecognizer(act);
        mSpeechRecognizer.setRecognitionListener(act);
        mActivity = act;
    }

    public void start() {

        if(mIsFirstRequest)
        {
            Intent detailsIntent = new Intent(RecognizerIntent.ACTION_GET_LANGUAGE_DETAILS);
            mActivity.sendOrderedBroadcast(detailsIntent, null, new LanguageDetailsChecker(this), null, Activity.RESULT_OK, null, null);
        }
        else
        {
            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
          /*  intent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_MINIMUM_LENGTH_MILLIS, 500);
            intent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_COMPLETE_SILENCE_LENGTH_MILLIS, 500);*/
           // intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
           // intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "tr-TR");
            mSpeechRecognizer.startListening(intent);
        }

        mIsFirstRequest = false;
    }

    public void destroy()
    {
        if(mSpeechRecognizer != null)
        {
            mSpeechRecognizer.destroy();
        }
    }

    @Override
    public void onLanguageAvailabilityCheck(boolean availability) {

        if(availability)
        {
            start();
        }
        else
        {
            //TODO
        }
    }
}
