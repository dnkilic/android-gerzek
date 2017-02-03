package com.dnkilic.stupid.command;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommandAnalyzer {

    private HashMap<Integer, List<String>> concepts;

    public final static int EXECUTE_UNKNOWN_COMMAND = -1;
    public final static int MUSIC = 11;
    public final static int COFFEE = 12;
    public final static int TALE2 = 13;
    public final static int TALE1 = 14;
    public final static int TALE = 15;
    public final static int EMOTION = 16;
    public final static int SWEARING = 17;
    public final static int HOUR = 18;
    public final static int HERE = 19;
    public final static int FORGET = 20;
    public final static int CRAZY = 21;
    public final static int HOW_ARE_YOU = 22;
    public final static int BORING = 23;
    public final static int MORNING = 24;
    public final static int MARRY = 25;
    public final static int BEAUTIFUL = 26;
    public final static int LOVE = 27;
    public final static int TRANSPORT = 28;
    public final static int OPEN_BLUETOOTH = 30;
    public final static int CLOSE_BLUETOOTH = 31;
    public final static int CLOSE_APPLICATION = 40;
    public final static int SET_ALARM = 50;
    public final static int OPEN_CAMERA = 60;
    public final static int READ_WIKIPEDIA = 70;
    public final static int OPEN_WHATSAPP = 81;
    public final static int OPEN_FACEBOOK = 82;
    public final static int OPEN_TWITTER = 83;
    public final static int ABOUT = 90;

    public CommandAnalyzer() {
        concepts = new HashMap<>();
        concepts.put(SWEARING, Arrays.asList("siktir", "göt", "amcık", "ibne", "şerefsiz", "piç", "orospu", "yavşak"));
        concepts.put(EMOTION, Arrays.asList("depresyon", "yalnızım"));
        concepts.put(TALE, Collections.singletonList("masal"));
        concepts.put(TALE1, Collections.singletonList("tekerleme"));
        concepts.put(TALE2, Collections.singletonList("fıkra"));
        concepts.put(HOUR, Arrays.asList("saat", "kaç"));
        concepts.put(COFFEE, Arrays.asList("kahve", "çay"));
        concepts.put(MUSIC, Arrays.asList("şarkı", "söyle"));
        concepts.put(FORGET, Collections.singletonList("unut"));
        concepts.put(HERE, Arrays.asList("orada", "mısın", "orda"));
        concepts.put(CRAZY, Arrays.asList("ruh", "hasta", "deli"));
        concepts.put(HOW_ARE_YOU, Collections.singletonList("nasılsın"));
        concepts.put(BORING, Arrays.asList("can", "sıkıl"));
        concepts.put(MORNING, Arrays.asList("günaydın", "hayırlı sabahlar", "sabah şerif", "iyi uyudun mu", "iyi sabahlar"));
        concepts.put(MARRY, Collections.singletonList("evlen"));
        concepts.put(BEAUTIFUL, Collections.singletonList("güzel"));
        concepts.put(LOVE, Arrays.asList("seviyorum", "sevgi", "aşk", "hoşlan", "aşık"));
        concepts.put(TRANSPORT, Collections.singletonList("akbil"));
        concepts.put(OPEN_BLUETOOTH, Arrays.asList("bluetooth", "aç"));
        concepts.put(CLOSE_BLUETOOTH, Arrays.asList("bluetooth", "kapa"));
        concepts.put(CLOSE_APPLICATION, Arrays.asList("uygulama", "kapa"));
        concepts.put(SET_ALARM, Arrays.asList("alarm", "alarm", "kur", "saat kur", "beni uyandır", "ayarla"));
        concepts.put(OPEN_WHATSAPP, Arrays.asList("vatsabı", "vatsap", "whatsapp", "whatssapp", "aç"));
        concepts.put(OPEN_FACEBOOK, Arrays.asList("feysi", "feysbuğu", "feys", "face", "feysbık", "facebook", "facebook'u", "aç"));
        concepts.put(OPEN_TWITTER, Arrays.asList("twiter", "twitter", "tivitır", "tıvıtır", "aç"));
        concepts.put(OPEN_CAMERA, Arrays.asList("kamera","kamera","selfie","resim","fotoğraf","öz çekim", "aç", "çek"));
        concepts.put(ABOUT, Arrays.asList("hakkında","kimsin","üretti","yaptı","seni","yarattı", "geliştirdi", "iletişim"));
        concepts.put(READ_WIKIPEDIA, Arrays.asList("wiki","wikipedia","kimdir","nedir"));
    }

    public Integer analyze(String result) {
        List<String> text = Arrays.asList(result.split(" "));
        HashMap<Integer, Integer> conceptsConfidence = new HashMap<>();

        for(Map.Entry<Integer, List<String>> entry : concepts.entrySet())
        {
            Integer key = entry.getKey();
            List<String> value = entry.getValue();

            int county = 0;

            for(String item : text)
            {
                for(String conceptItem : value)
                {
                    if(item.contains(conceptItem))
                    {
                        county++;
                    }
                }
            }

            conceptsConfidence.put(key, county);
        }

        return getCommand(conceptsConfidence);
    }

    private Integer getCommand(HashMap<Integer, Integer> map)
    {
        int maxValueInMap=(Collections.max(map.values()));

        if(maxValueInMap == 0)
        {
            return EXECUTE_UNKNOWN_COMMAND;
        }
        else
        {
            for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
                if (entry.getValue() == maxValueInMap) {
                    return entry.getKey();
                }
            }
        }

        return EXECUTE_UNKNOWN_COMMAND;
    }
}
