package com.dnkilic.application41;

/**
 * Created by dnkilic on 04/06/16.
 */
public class Command
{
    private String announce;
    private int commandType;

    public Command(String announce, int commandType) {
        this.announce = announce;
        this.commandType = commandType;
    }

    public String getAnnounce() {
        return announce;
    }

    public int getCommandType() {
        return commandType;
    }
}
