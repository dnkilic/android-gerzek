package com.dnkilic.stupid.command;

import android.app.Activity;
import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.AlarmClock;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.canelmas.let.AskPermission;
import com.dnkilic.stupid.MainActivity;
import com.dnkilic.stupid.R;

import java.io.File;
import java.util.Random;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.RECORD_AUDIO;
import static com.dnkilic.stupid.command.CommandAnalyzer.OPEN_FACEBOOK;
import static com.dnkilic.stupid.command.CommandAnalyzer.OPEN_TWITTER;
import static com.dnkilic.stupid.command.CommandAnalyzer.OPEN_WHATSAPP;

public class CommandRunner implements WikipediaReader {

    private Context mContext;
    private MainActivity mActivity;
    private CommandAnalyzer mCommandAnalyzer;
    private CommandResultListener mCommandResultListener;

    public CommandRunner(MainActivity mainActivity) {
        this.mActivity = mainActivity;
        this.mCommandResultListener = mainActivity;
        this.mContext = mainActivity;
        mCommandAnalyzer = new CommandAnalyzer();
    }

    public void run(String text) {

        try {

            Integer command = mCommandAnalyzer.analyze(text);
            String announce = getRandomStringFromResources(R.array.EXECUTE_UNKNOWN_COMMAND);

            if (command != null) {
                switch (command) {
                    case CommandAnalyzer.CLOSE_BLUETOOTH:
                        announce = getRandomStringFromResources(R.array.CLOSE_BLUETOOTH);
                        setBluetooth(false);
                        mCommandResultListener.onCommandSuccess(announce);
                        break;
                    case CommandAnalyzer.OPEN_BLUETOOTH:
                        announce = getRandomStringFromResources(R.array.OPEN_BLUETOOTH);
                        setBluetooth(true);
                        mCommandResultListener.onCommandSuccess(announce);
                        break;
                    case CommandAnalyzer.EXECUTE_UNKNOWN_COMMAND:
                        announce = getRandomStringFromResources(R.array.EXECUTE_UNKNOWN_COMMAND);
                        mCommandResultListener.onCommandSuccess(announce);
                        break;
                    case CommandAnalyzer.CLOSE_APPLICATION:
                        announce = getRandomStringFromResources(R.array.CLOSE_APPLICATION);
                        mCommandResultListener.onCommandSuccess(announce);
                        mActivity.finish();
                        break;
                    case CommandAnalyzer.SET_ALARM:
                        announce = setAlarmClock(text);
                        mCommandResultListener.onCommandSuccess(announce);
                        break;
                    case CommandAnalyzer.OPEN_CAMERA:
                        announce = getRandomStringFromResources(R.array.OPEN_CAMERA);
                        mActivity.openCamera();
                        mCommandResultListener.onCommandSuccess(announce);
                        break;
                    case CommandAnalyzer.MUSIC:
                        announce = getRandomStringFromResources(R.array.MUSIC);
                        mCommandResultListener.onCommandSuccess(announce);
                        break;
                    case CommandAnalyzer.COFFEE:
                        announce = getRandomStringFromResources(R.array.COFFEE);
                        mCommandResultListener.onCommandSuccess(announce);
                        break;
                    case CommandAnalyzer.TALE2:
                        announce = getRandomStringFromResources(R.array.TALE2);
                        mCommandResultListener.onCommandSuccess(announce);
                        break;
                    case CommandAnalyzer.TALE:
                        announce = getRandomStringFromResources(R.array.TALE);
                        mCommandResultListener.onCommandSuccess(announce);
                        break;
                    case CommandAnalyzer.TALE1:
                        announce = getRandomStringFromResources(R.array.TALE1);
                        mCommandResultListener.onCommandSuccess(announce);
                        break;
                    case CommandAnalyzer.EMOTION:
                        announce = getRandomStringFromResources(R.array.EMOTION);
                        mCommandResultListener.onCommandSuccess(announce);
                        break;
                    case CommandAnalyzer.SWEARING:
                        announce = getRandomStringFromResources(R.array.SWEARING);
                        mCommandResultListener.onCommandSuccess(announce);
                        break;
                    case CommandAnalyzer.HOUR:
                        announce = getRandomStringFromResources(R.array.HOUR);
                        mCommandResultListener.onCommandSuccess(announce);
                        break;
                    case CommandAnalyzer.HERE:
                        announce = getRandomStringFromResources(R.array.HERE);
                        mCommandResultListener.onCommandSuccess(announce);
                        break;
                    case CommandAnalyzer.FORGET:
                        announce = getRandomStringFromResources(R.array.FORGET);
                        mCommandResultListener.onCommandSuccess(announce);
                        break;
                    case CommandAnalyzer.CRAZY:
                        announce = getRandomStringFromResources(R.array.CRAZY);
                        mCommandResultListener.onCommandSuccess(announce);
                        break;
                    case CommandAnalyzer.HOW_ARE_YOU:
                        announce = getRandomStringFromResources(R.array.HOWAREYOU);
                        mCommandResultListener.onCommandSuccess(announce);
                        break;
                    case CommandAnalyzer.BORING:
                        announce = getRandomStringFromResources(R.array.BORING);
                        mCommandResultListener.onCommandSuccess(announce);
                        break;
                    case CommandAnalyzer.MORNING:
                        announce = getRandomStringFromResources(R.array.MORNING);
                        mCommandResultListener.onCommandSuccess(announce);
                        break;
                    case CommandAnalyzer.MARRY:
                        announce = getRandomStringFromResources(R.array.MARRY);
                        mCommandResultListener.onCommandSuccess(announce);
                        break;
                    case CommandAnalyzer.BEAUTIFUL:
                        announce = getRandomStringFromResources(R.array.BEAUTIFUL);
                        mCommandResultListener.onCommandSuccess(announce);
                        break;
                    case CommandAnalyzer.LOVE:
                        announce = getRandomStringFromResources(R.array.LOVE);
                        mCommandResultListener.onCommandSuccess(announce);
                        break;
                    case CommandAnalyzer.TRANSPORT:
                        announce = getRandomStringFromResources(R.array.TRANSPORT);
                        mCommandResultListener.onCommandSuccess(announce);
                        break;
                    case CommandAnalyzer.READ_WIKIPEDIA:
                        WikiSearchManager wikiSearchManager = new WikiSearchManager(this);
                        wikiSearchManager.getWikiResults(text);
                        break;
                    case OPEN_WHATSAPP:
                        announce = getRandomStringFromResources(R.array.OPEN_WHATSAPP);
                        openApp(OPEN_WHATSAPP);
                        mCommandResultListener.onCommandSuccess(announce);
                        break;
                    case OPEN_FACEBOOK:
                        announce = getRandomStringFromResources(R.array.OPEN_FACEBOOK);
                        openApp(OPEN_FACEBOOK);
                        mCommandResultListener.onCommandSuccess(announce);
                        break;
                    case OPEN_TWITTER:
                        announce = getRandomStringFromResources(R.array.OPEN_TWITTER);
                        openApp(OPEN_TWITTER);
                        mCommandResultListener.onCommandSuccess(announce);
                        break;
                    case CommandAnalyzer.ABOUT:
                        announce = getRandomStringFromResources(R.array.ABOUT);
                        showAboutPopup();
                        mCommandResultListener.onCommandSuccess(announce);
                        break;
                    default:
                        mCommandResultListener.onCommandSuccess(announce);
                        break;
                }
            }

        }
        catch (Exception e)
        {
            mCommandResultListener.onCommandFail("İşlem sırasında bir hata oluştu.");
            e.printStackTrace();
        }
    }

    private String getRandomStringFromResources(int resourceId) {
        Random randomGenerator = new Random();
        String[] myString;
        myString = mContext.getResources().getStringArray(resourceId);
        return myString[randomGenerator.nextInt(myString.length)];
    }

    private void showAboutPopup() {
        Dialog creditDialog = new Dialog(mActivity);

        creditDialog.setContentView(R.layout.popup_credit);
        creditDialog.setCancelable(true);
        creditDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        DisplayMetrics metrics = new DisplayMetrics();
        mActivity.getWindowManager().getDefaultDisplay().getMetrics(metrics);

        creditDialog.getWindow().setLayout(metrics.widthPixels / 10 * 9, RecyclerView.LayoutParams.WRAP_CONTENT);
        creditDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);

        creditDialog.show();
    }

    private void openApp(Integer selected){

        Intent waIntent = new Intent();

        switch (selected)
        {
            case OPEN_WHATSAPP:
                waIntent.setPackage("com.whatsapp");
                mContext.startActivity(waIntent);
                break;
            case OPEN_FACEBOOK:
                Intent intent = new Intent("android.intent.category.LAUNCHER");
                intent.setClassName("com.facebook.katana", "com.facebook.katana.LoginActivity");
                mContext.startActivity(intent);
                break;
            case OPEN_TWITTER:
                waIntent.setPackage("com.twitter.android");
                mContext.startActivity(waIntent);
                break;
        }
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

        return true;
    }

    private String setAlarmClock(String result)
    {
        String str = result.replaceAll("[^\\d]", " ").trim();
        String [] time;
        time = str.split(" ");

        if(time.length == 2)
        {
            setAlarm(time[0], time[1]);
            return  time[0] + ":" + time[1] + " alarmı kuruldu";
        }
        else if(time.length == 1)
        {
            if(time[0].isEmpty())
            {
                return  "Alarmı kurmak için saati söylemelisin.";
            }
            else
            {
                setAlarm(time[0], "0");
                return  time[0] + " alarmı kuruldu";
            }
        }
        else
        {
            return "Alarmı kurmak için saati söylemelisin.";
        }
    }

    private void setAlarm(String hour, String minute) {
        Intent i = new Intent(AlarmClock.ACTION_SET_ALARM);
        i.putExtra(AlarmClock.EXTRA_HOUR, Integer.parseInt(hour));
        i.putExtra(AlarmClock.EXTRA_MINUTES, Integer.parseInt(minute));
        i.putExtra(AlarmClock.EXTRA_SKIP_UI, true);
        mActivity.startActivity(i);
    }

    @Override
    public void onWikipediaResult(String content) {
        mCommandResultListener.onCommandSuccess(content);
    }
}