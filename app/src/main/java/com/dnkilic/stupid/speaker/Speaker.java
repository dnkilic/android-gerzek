package com.dnkilic.stupid.speaker;

import android.os.Build;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;

import com.dnkilic.stupid.MainActivity;

import java.util.Locale;

public class Speaker {

    private TextToSpeech mTextToSpeech;
    private SpeakerUtteranceListener mListener;
    private boolean mIsTTSEnabled = true;

    public Speaker(final MainActivity act)
    {
        mListener = act;

        mTextToSpeech = new TextToSpeech(act, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status == TextToSpeech.SUCCESS)
                {
                    mTextToSpeech.setLanguage(new Locale("TR-tr"));

                    switch (mTextToSpeech.isLanguageAvailable(new Locale("TR-tr")))
                    {
                        case TextToSpeech.LANG_COUNTRY_VAR_AVAILABLE:
                        case TextToSpeech.LANG_COUNTRY_AVAILABLE:
                        case TextToSpeech.LANG_AVAILABLE:
                            speak("Merhaba ben Gerzek sana nasıl yardımcı olabilirim?");
                            break;
                        case TextToSpeech.LANG_MISSING_DATA:
                        case TextToSpeech.LANG_NOT_SUPPORTED:
                            speak("Merhaba ben Gerzek sana nasıl yardımcı olabilirim? Telefonunda Türkçe ses paketi olmadığı için biraz enterasan konuşacağım.");
                        default:
                            break;
                    }
                }
                else
                {
                    mIsTTSEnabled = false;
                }
            }
        });

        mTextToSpeech.setOnUtteranceProgressListener(new UtteranceProgressListener() {
            @Override
            public void onStart(String utteranceId) {}

            @Override
            public void onDone(String utteranceId) {
                mListener.onDoneTTS();
            }

            @Override
            public void onError(String utteranceId) {
                mListener.onErrorTTS();
            }
        });
    }

    public void speak(final String announce) {
        mListener.onStartTTS(announce);

        if(mIsTTSEnabled)
        {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                mTextToSpeech.speak(announce, TextToSpeech.QUEUE_ADD, null, "");
            }
            else
            {
                mTextToSpeech.speak(announce, TextToSpeech.QUEUE_FLUSH, null);
            }
        }
        else
        {
            mListener.onDoneTTS();
        }
    }

    public void shutdown() {
        if(mTextToSpeech != null)
        {
            mTextToSpeech.shutdown();
        }
    }
}
