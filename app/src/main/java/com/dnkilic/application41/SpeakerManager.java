package com.dnkilic.application41;

import android.app.Activity;
import android.speech.tts.TextToSpeech;
import android.widget.Toast;

import java.util.Locale;

public class SpeakerManager {

    private TextToSpeech textToSpeech;
    private Activity mAct;

    public SpeakerManager(Activity act)
    {
        mAct = act;

        textToSpeech = new TextToSpeech(mAct, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status == TextToSpeech.SUCCESS)
                {
                    textToSpeech.setLanguage(new Locale("TR-tr"));
                    textToSpeech.speak("Merhaba ben Sementha sana nasıl yardımcı olabilirim?", TextToSpeech.QUEUE_FLUSH, null);
                    Toast.makeText(mAct, "TTS Başarılı", Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(mAct, "TTS Başarısız", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void speak(String announce) {
        textToSpeech.speak(announce, TextToSpeech.QUEUE_FLUSH, null);
    }
}
