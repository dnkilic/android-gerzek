package com.dnkilic.stupid.command;

public interface CommandResultListener {
    void onCommandSuccess(String announce);
    void onCommandFail(String announce);
}
