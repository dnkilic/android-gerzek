package com.dnkilic.application41;

/**
 * Created by dnkilic on 04/06/16.
 */
public class CommandAnalyzer {

    /*
    * Hüseyin wikipedia'dan
    * Hüseyin makale okutsun Resmigazeteden birşey okutsun resmi gazete oku
    * Aslıhan SMS gönderme
    * Suzan Naber/Naasılsın/Hal hatır sormaya cevaplar
    * Ahmet Y. 1.telefondaki mp3leri bulacak + herhangibirisini çalacak
    * Cihan backgroundaddan arama yapacaksın 1. rehber kayıtları bir map'e koyulur 2. kullanıcının söylediği isim tanınır 3. arama yapılır
    * Mesut openweatherapi hava durumu nasıl
    * Barış kullanıcılar tarafından yapılan sorguları veritabanına kaydetme
    * Barış2 dolar ne kadar euro ne kadar
    * Barış3 Alarm kursun
    * Şenel ankaradan izmire nasıl gidilir 81
    * Onur neredeyim sorusuna cevap versin
    * */

    public final static int EXECUTE_UNKNOWN_COMMAND = -1;
    public final static int OPEN_BLUETOOTH = 0;
    public final static int CLOSE_BLUETOOTH = 1;
    public final static int MAKE_FUNNY_ANSWER = 2;
    public final static int CLOSE_APPLICATION = 3;

    public CommandAnalyzer()
    {

    }

    public Command analyze(String result) {
        Command command = null;

        if(result.contains("seviyorum") || result.contains("sevgi")
                || result.contains("sev") || result.contains("hoşlan")
                || result.contains("aşk") || result.contains("aşık"))
        {
            command = new Command("Duygularımız karşılıklı canım", CommandAnalyzer.MAKE_FUNNY_ANSWER);
        }
        else if(result.contains("günaydın") || result.contains("hayırlı sabahlar")
                || result.contains("sabah şerifleriniz hayır olsun") || result.contains("iyi uyudun mu")
                || result.contains("iyi sabahlar"))
        {
            command = new Command("Günaydın aşk kuşum", CommandAnalyzer.MAKE_FUNNY_ANSWER);
        }
        else if(result.contains("evlen") || result.contains("kırma beni evlen benle"))
        {
            command = new Command("Bluzun pembe evlen benle", CommandAnalyzer.MAKE_FUNNY_ANSWER);
        }
        else if(result.contains("akbil") || result.contains("akbili fazla olan var mı")
                || result.contains("akbil akbil akbil") || result.contains("akbili benim yerime tıklatacak olan var mı")
                )
        {
            command = new Command("ne demek abla sen iste yeter", CommandAnalyzer.MAKE_FUNNY_ANSWER);
        }
        else if(result.contains("bluetooth") && result.contains("aç"))
        {
            command = new Command("Tamam bluetooth'u açıyorum kanka", CommandAnalyzer.OPEN_BLUETOOTH);
        }
        else if(result.contains("bluetooth") && result.contains("kapa"))
        {
            command = new Command("Tamam bluetooth'u kapatıyorum kanka", CommandAnalyzer.CLOSE_BLUETOOTH);
        }
        else if(result.contains("uygulamayı kapat") || result.contains("uygulama kapat"))
        {
            command = new Command("Kalbimi kırıyorsun. Ben annemin evine gidiyorum.", CommandAnalyzer.CLOSE_APPLICATION);
        }
        else
        {
            command = new Command("Bu komutla ilgili şuan bir işlem yapamıyorum ama kısa sürede yapıyor olabileceğim. Teşekkürler gülücük", CommandAnalyzer.EXECUTE_UNKNOWN_COMMAND);
        }

        return command;
    }
}
