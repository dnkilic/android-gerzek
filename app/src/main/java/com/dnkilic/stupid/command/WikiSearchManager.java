package com.dnkilic.stupid.command;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.net.URLDecoder;
import java.net.URLEncoder;
public class WikiSearchManager {

    private WikipediaReader mReadResultListener;

    public WikiSearchManager(WikipediaReader reader)
    {
        mReadResultListener = reader;
    }

    public void getWikiResults(final String key) {

        Thread thread = new Thread()
        {
            @Override
            public void run() {
                try {

                    String url;
                    String google = "http://www.google.com/search?q=";
                    String search = key+" vikipedia";
                    String charset = "UTF-8";
                    String userAgent = "Mozilla";
                    Elements links = Jsoup.connect(google + URLEncoder.encode(search, charset)).userAgent(userAgent).get().select(".g>.r>a");
                    url = links.get(0).absUrl("href").replaceAll("https", "http");
                    url = URLDecoder.decode(url.substring(url.indexOf('=') + 1, url.indexOf('&')), "UTF-8");
                    System.out.println(url);

                    System.out.println(links.get(0).text());

                    Document doc = Jsoup.connect(url.replaceAll("tr.wikipedia.org", "tr.m.wikipedia.org")).get();
                    String result = doc.select("#mw-content-text > div.mf-section-0").select("p").text();
                    mReadResultListener.onWikipediaResult(result);

                }
                catch (Exception e)
                {
                    mReadResultListener.onWikipediaResult("Bu bilgiyi wikipedia'da bulamadım.");
                    e.printStackTrace();
                }
            }
        };
        thread.start();
    }
}

