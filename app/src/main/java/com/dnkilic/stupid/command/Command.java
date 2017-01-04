package com.dnkilic.stupid.command;

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
