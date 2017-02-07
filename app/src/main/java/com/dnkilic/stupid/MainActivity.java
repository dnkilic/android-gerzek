package com.dnkilic.stupid;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.speech.RecognitionListener;
import android.speech.SpeechRecognizer;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.canelmas.let.AskPermission;
import com.canelmas.let.DeniedPermission;
import com.canelmas.let.Let;
import com.canelmas.let.RuntimePermissionListener;
import com.canelmas.let.RuntimePermissionRequest;
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
import com.google.firebase.analytics.FirebaseAnalytics;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.RECORD_AUDIO;

public class MainActivity extends AppCompatActivity implements RecognitionListener, SpeakerUtteranceListener, ViewInteractListener, CommandResultListener, RuntimePermissionListener {

    private RecognitionManager recognitionManager;
    private Speaker speakerManager;
    private ViewController viewController;
    private CommandRunner commandRunner;
    private FirebaseAnalytics mFirebaseAnalytics;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewController = new ViewController(this);
        viewController.initialize();
        speakerManager = new Speaker(this);
        recognitionManager = new RecognitionManager(this);
        commandRunner = new CommandRunner(this);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        Bundle bundle = new Bundle();
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.APP_OPEN, bundle);
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
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Let.handle(this, requestCode, permissions, grantResults);
    }

    @AskPermission(RECORD_AUDIO)
    @Override
    public void startRecognition() {

        Bundle bundle = new Bundle();
        mFirebaseAnalytics.logEvent("SPEECH_RECOGNITION", bundle);

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

        Bundle bundle = new Bundle();
        mFirebaseAnalytics.logEvent("TEXT_INPUT", bundle);

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

        Bundle bundle = new Bundle();
        mFirebaseAnalytics.logEvent("PROPOSAL_SELECT", bundle);

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

        Bundle b = new Bundle();
        b.putString("Query", text);
        mFirebaseAnalytics.logEvent("ANALYZE", b);

        commandRunner.run(text.toLowerCase());
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

        if(recognitionResults != null && !recognitionResults.isEmpty())
        {
            final String sbDictationResult;

            sbDictationResult = recognitionResults.get(0);

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    viewController.addDialogItem(new Dialog(sbDictationResult, DialogType.TYPE_DIALOG_SENT));
                }
            });

            analyzeAndRunText(sbDictationResult);
        }
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
                viewController.addDialogItem(new Dialog("Ses sentezi yapılırken bir hata oluştu", DialogType.TYPE_MESSAGE_ERROR));
                viewController.enableActions();
                viewController.clearProposals();
                viewController.addRandomProposals();
            }
        });
    }

    @Override
    public void onShowPermissionRationale(List<String> permissions, final RuntimePermissionRequest request) {

        final StringBuilder sb = new StringBuilder();

        for (String permission : permissions) {
            sb.append(getRationale(permission));
            sb.append("\n");
        }

        new AlertDialog.Builder(this).setTitle("İzin Gerekli!")
                .setMessage(sb.toString())
                .setCancelable(true)
                .setNegativeButton("Hayır Teşekkürler", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton("Tekrar Dene", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        request.retry();
                    }
                })
                .show();

    }

    @Override
    public void onPermissionDenied(List<DeniedPermission> results) {

        final StringBuilder sb = new StringBuilder();

        for (DeniedPermission result : results) {

            if (result.isNeverAskAgainChecked()) {
                sb.append(result.getPermission() + " izni için bir daha diyalog gösterilmeyecek.");
                sb.append("\n");
            }
        }

        if (sb.length() != 0) {

            new AlertDialog.Builder(this).setTitle("Ayarlara Git ve İzin Al")
                    .setMessage(sb.toString())
                    .setCancelable(true)
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            Uri uri = Uri.fromParts("package", getPackageName(), null);
                            intent.setData(uri);
                            startActivityForResult(intent, 1);

                            dialog.dismiss();
                        }
                    }).show();
        }


    }

    private String getRationale(String permission) {
        if (permission.equals(RECORD_AUDIO)) {
            return "Ses tanıma yapabilmek için izin vermeniz gerekiyor.";
        }
        return "Kamerayı açabilmek için izin vermeniz gerekiyor.";
    }

    @AskPermission(CAMERA)
    public void openCamera() {
        String dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/gerzekPhoto/";
        File newdir = new File(dir);
        newdir.mkdirs();

        String file = dir +".jpg";
        File newfile = new File(file);
        try {
            newfile.createNewFile();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        Uri outputFileUri = Uri.fromFile(newfile);

        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);

        startActivity(cameraIntent);
    }
}
