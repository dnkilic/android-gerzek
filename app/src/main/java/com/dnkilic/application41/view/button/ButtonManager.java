package com.dnkilic.application41.view.button;

import android.view.View;

import com.dnkilic.application41.MainActivity;

/**
 * Created by dogan.kilic on 18.10.2016.
 */

public class ButtonManager {

    final private CircularProgressButton btnRecord;

    ButtonManager(final MainActivity activity) {
       // btnRecord = (CircularProgressButton) activity.findViewById(33333331);//R.id.btnRecord);
        btnRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btnRecord.getProgress() == 0) {
                    btnRecord.setProgress(50);
                } else if (btnRecord.getProgress() == 100 || btnRecord.getProgress() == -1) {
                    btnRecord.setProgress(0);
                } else {
                    btnRecord.setProgress(100);
                    btnRecord.setProgress(0);
                }
            }
        });

    }

    public void refreshRecordButtonWithError()
    {
        btnRecord.setProgress(100);
        btnRecord.setProgress(0);
    }
}
