package com.example.asus.myapplication;

import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by ASUS on 5/1/2018.
 */

public class ThesaurusActivity implements Runnable{


    private String word;
    private ArrayList<String> type = new ArrayList<>();
    private ArrayList<String> synonyms = new ArrayList<>();

    public static final String key ="0ykjP1a7cUCt2AP1wrUR";

    public ThesaurusActivity(String word) {
        this.word = word;
    }

    @Override
    public void run() {
        findThesaurus();
    }

    private void findThesaurus() {
        String error = null;
        HttpURLConnection http_URLConnection = null;
        try {

            if (Thread.interrupted())
                throw new InterruptedException();

            URL urlLink = new URL("http://thesaurus.altervista.org/thesaurus/v1?word=" + word + "&language=en_US&key=0ykjP1a7cUCt2AP1wrUR&output=xml");

            http_URLConnection = (HttpURLConnection) urlLink.openConnection();
            http_URLConnection.setReadTimeout(10000 /* milliseconds */);
            http_URLConnection.setConnectTimeout(15000 /* milliseconds */);
            http_URLConnection.setRequestMethod("GET");
            http_URLConnection.setDoInput(true);


            http_URLConnection.connect();//Starting query

            if (Thread.interrupted())
                throw new InterruptedException();

            XmlPullParser parse= Xml.newPullParser();//read results
            parse.setInput(http_URLConnection.getInputStream(), null);
            int event = parse.getEventType();
            while (event != XmlPullParser.END_DOCUMENT) {


                if (event == XmlPullParser.START_TAG && parse.getName().equalsIgnoreCase("category")){
                    event = parse.next();
                    type.add(parse.getText());
                    System.out.println("Category: " + parse.getText());
                }

                if (event == XmlPullParser.START_TAG && parse.getName().equalsIgnoreCase("synonyms")){
                    event= parse.next();
                    synonyms.add(parse.getText());
                    System.out.println("Valie: " + parse.getText());
                }

                if (XmlPullParser.TEXT == event){

                }

                event = parse.next();


            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(type);
        System.out.println(synonyms);

    }

    public ArrayList<String> getType() {
        return type;
    }

    public ArrayList<String> getSynonyms() {
        return synonyms;
    }
}
