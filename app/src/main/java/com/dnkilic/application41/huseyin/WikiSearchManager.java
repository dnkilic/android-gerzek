package com.dnkilic.application41.huseyin;

import com.dnkilic.application41.WikipediaReader;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
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
                    String getKey = "https://tr.m.wikipedia.org/w/index.php?search="+URLEncoder.encode(key,"UTF-8")+"&fulltext=search";
                    URL url = new URL(getKey);

                    HttpURLConnection con = (HttpURLConnection) url.openConnection();

                    con.setRequestMethod("GET");

                    con.setRequestProperty("User-Agent", "Mozilla/5.0");

                    BufferedReader in = new BufferedReader(
                            new InputStreamReader(con.getInputStream(),"UTF-8"));
                    String inputLine;
                    StringBuffer response = new StringBuffer();

                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    in.close();

                    Document doc = Jsoup.parse(response.toString());
                    Elements keyHeads = doc.getElementsByClass("mw-search-results");
                    mReadResultListener.onWikipediaResult(keyHeads.select("div[class=searchresult]").get(0).text());

                }
                catch (Exception e)
                {
                    mReadResultListener.onWikipediaResult("");
                    e.printStackTrace();
                }
            }
        };
        thread.start();
    }
}

