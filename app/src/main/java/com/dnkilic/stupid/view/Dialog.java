package com.dnkilic.stupid.view;

public class Dialog {

    DialogType type;
    String dialogText;

    public Dialog(String dialogText, DialogType type) {
        this.type = type;
        this.dialogText = dialogText;
    }

    public DialogType getType() {
        return type;
    }

    public String getDialogText() {
        return dialogText;
    }
}
