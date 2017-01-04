package com.dnkilic.stupid;

import android.speech.RecognitionListener;
import android.speech.SpeechRecognizer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.dnkilic.stupid.command.CommandResultListener;
import com.dnkilic.stupid.command.CommandRunner;
import com.dnkilic.stupid.recognition.RecognitionManager;
import com.dnkilic.stupid.speaker.Speaker;
import com.dnkilic.stupid.speaker.SpeakerUtteranceListener;
import com.dnkilic.stupid.view.Dialog;
import com.dnkilic.stupid.view.DialogType;
import com.dnkilic.stupid.view.Proposal;
import com.dnkilic.stupid.view.ViewController;
import com.dnkilic.stupid.view.ViewInteractListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements RecognitionListener, SpeakerUtteranceListener, ViewInteractListener, CommandResultListener {

    private RecognitionManager recognitionManager;
    private Speaker speakerManager;
    private ViewController viewController;
    private CommandRunner commandRunner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewController = new ViewController(this);
        viewController.initialize();
        speakerManager = new Speaker(this);
        recognitionManager = new RecognitionManager(this);
        commandRunner = new CommandRunner(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if(speakerManager != null)
        {
            speakerManager.shutdown();
        }

        if(recognitionManager != null)
        {
            recognitionManager.destroy();
        }
    }

    @Override
    public void startRecognition() {
        recognitionManager.start();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                viewController.disableActions();
            }
        });
    }

    @Override
    public void sendTextMessage(final String message) {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                viewController.addDialogItem(new Dialog(message, DialogType.TYPE_DIALOG_SENT));
                viewController.disableActions();
            }
        });

        analyzeAndRunText(message);
    }

    @Override
    public void onProposalSelected(final Proposal proposal) {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                viewController.addDialogItem(new Dialog(proposal.getProposalText(), DialogType.TYPE_DIALOG_SENT));
                viewController.disableActions();
            }
        });

        analyzeAndRunText(proposal.getProposalText());
    }

    private void analyzeAndRunText(String text) {
        commandRunner.run(text);
    }

    @Override
    public void onCommandSuccess(final String announce) {

        speakerManager.speak(announce);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                viewController.enableActions();
            }
        });
    }

    @Override
    public void onCommandFail(final String announce) {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                viewController.enableActions();
                viewController.addDialogItem(new Dialog(announce, DialogType.TYPE_MESSAGE_ERROR));
            }
        });
    }

    @Override
    public void onReadyForSpeech(Bundle params) {}

    @Override
    public void onBeginningOfSpeech() {}

    @Override
    public void onRmsChanged(float rmsdB) {}

    @Override
    public void onBufferReceived(byte[] buffer) {}

    @Override
    public void onEndOfSpeech() {}

    @Override
    public void onError(int error) {

        String message = "Ses tanıma işlemi sırasında bir hata oluştu.";

        switch (error)
        {
            case SpeechRecognizer.ERROR_AUDIO:
                message = "Ses tanıma işlemi sırasında bir hata oluştu. Bu hata mikrofonunuzla ilgili olabilir.";
                break;
            case SpeechRecognizer.ERROR_CLIENT:
                message = "Ses tanıma işlemi sırasında bir hata oluştu. Bu hata telefonunuzdan kaynaklanıyor.";
                break;
            case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
                message = "Ses tanıma işlemi sırasında bir hata oluştu. Bu hata ses kaydetme ve internet erişimi izinleri alınmadığı için meydana gelmiş olabilir.";
                break;
            case SpeechRecognizer.ERROR_NETWORK:
                message = "Ses tanıma işlemi sırasında bir hata oluştu. İnternet bağlantınızı kontrol edin.";
                break;
            case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
                message = "Ses tanıma işlemi sırasında bir hata oluştu. İnternet bağlantınızı kontrol edin.";
                break;
            case SpeechRecognizer.ERROR_NO_MATCH:
                message = "Söylediğinizi anlayamadım.";
                break;
            case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
                message = "Ses tanıma işlemi sırasında bir hata oluştu. Lütfen biraz sakin ol.";
                break;
            case SpeechRecognizer.ERROR_SERVER:
                message = "Ses tanıma işlemi sırasında bir hata oluştu. İnternet bağlantınızı kontrol edin.";
                break;
            case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
                message = "Ses tanıma işlemi sırasında bir hata oluştu. Tekrar deneyin.";
                break;
        }

        final String finalMessage = message;

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                viewController.addDialogItem(new Dialog(finalMessage, DialogType.TYPE_MESSAGE_ERROR));
                viewController.enableActions();
            }
        });
    }

    @Override
    public void onResults(Bundle results) {
        ArrayList<String> recognitionResults = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
        final StringBuilder sbDictationResult = new StringBuilder();

        if(recognitionResults != null)
        {
            for (String result : recognitionResults)
            {
                sbDictationResult.append(result);
                sbDictationResult.append("\t");
            }
        }

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                viewController.addDialogItem(new Dialog(sbDictationResult.toString(), DialogType.TYPE_DIALOG_SENT));
            }
        });

        analyzeAndRunText(sbDictationResult.toString());
    }

    @Override
    public void onPartialResults(Bundle partialResults) {}

    @Override
    public void onEvent(int eventType, Bundle params) {}

    @Override
    public void onDoneTTS() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                viewController.enableActions();
                viewController.clearProposals();
                viewController.addRandomProposals();
            }
        });
    }

    @Override
    public void onStartTTS(final String announce) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                viewController.disableActions();
                viewController.addDialogItem(new Dialog(announce, DialogType.TYPE_DIALOG_RECEIVED));
            }
        });
    }

    @Override
    public void onErrorTTS() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                viewController.addDialogItem(new Dialog("Ses sentezi yapğılırken bir hata oluştu", DialogType.TYPE_MESSAGE_ERROR));
                viewController.enableActions();
                viewController.clearProposals();
                viewController.addRandomProposals();
            }
        });
    }
}
