package com.dnkilic.application41;

/**
 * Created by dnkilic on 04/06/16.
 */
public class CommandAnalyzer {

    public CommandAnalyzer()
    {

    }


    public String analyze(String result) {
        String announce = null;


        if(result.contains("seviyorum") || result.contains("sevgi")
                || result.contains("sev") || result.contains("hoşlan")
                || result.contains("aşk") || result.contains("aşık"))
        {
            announce = "Duygularımız karşılıklı canım";
        }


        return announce;
    }
}
