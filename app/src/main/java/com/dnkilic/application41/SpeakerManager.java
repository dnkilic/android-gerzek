package com.dnkilic.application41;

import android.app.Activity;
import android.speech.tts.TextToSpeech;
import android.widget.Toast;

import com.dnkilic.application41.view.Dialog;
import com.dnkilic.application41.view.DialogType;

import java.util.Locale;

public class SpeakerManager {

    private TextToSpeech textToSpeech;
    private MainActivity mAct;

    public SpeakerManager(MainActivity act)
    {
        mAct = act;

        textToSpeech = new TextToSpeech(mAct, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status == TextToSpeech.SUCCESS)
                {
                    textToSpeech.setLanguage(new Locale("TR-tr"));
                    speak("Merhaba ben Sementha sana nasıl yardımcı olabilirim?");
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
        mAct.addItem(new Dialog(announce, DialogType.TYPE_DIALOG_RECEIVED));
        textToSpeech.speak(announce, TextToSpeech.QUEUE_FLUSH, null);
    }
}
