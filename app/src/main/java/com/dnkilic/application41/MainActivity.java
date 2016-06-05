package com.dnkilic.application41;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.dnkilic.application41.view.Dialog;
import com.dnkilic.application41.view.DialogAdapter;
import com.dnkilic.application41.view.DialogType;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements RecognitionResultListener {

    private RecognitionManager recognitionManager;
    private SpeakerManager speakerManager;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private ArrayList<Dialog> mItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        mLayoutManager = new LinearLayoutManager(this);

        mRecyclerView.setLayoutManager(mLayoutManager);

        mItems = new ArrayList<>();

        mAdapter = new DialogAdapter(mItems, this);

        mRecyclerView.setAdapter(mAdapter);

        speakerManager = new SpeakerManager(this);
        recognitionManager = new RecognitionManager(this);
    }

    public void start(View v) {
        recognitionManager.start();
    }

    public void addItem(Dialog dialog)
    {
        mItems.add(dialog);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onResult(String result) {

        addItem(new Dialog(result, DialogType.TYPE_DIALOG_SENT));

        CommandAnalyzer analyzer = new CommandAnalyzer(this);

        Command command = analyzer.analyze(result.toLowerCase());

        //TODO işlem 3: bu bölümde anonsların okunması ve comutların execute edilmesi işlemleri yapılır
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
            case CommandAnalyzer.SET_ALARM:
                speakerManager.speak(command.getAnnounce());
                break;
            case CommandAnalyzer.OPEN_CAMERA:
                speakerManager.speak(command.getAnnounce());
                openCamera();
                break;
        }
    }

    private void openCamera() {

        String dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/semathasPhoto/";
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

    private static boolean setBluetooth(boolean enable) {
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
