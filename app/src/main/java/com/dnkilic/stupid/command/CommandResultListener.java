package com.dnkilic.stupid.command;

/**
 * Created by dogan.kilic on 4.01.2017.
 */

public interface CommandResultListener {
    void onCommandSuccess(String announce);
    void onCommandFail(String announce);
}
