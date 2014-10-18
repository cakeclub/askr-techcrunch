package com.askr.hackathon.tools;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by Jake on 18/10/2014.
 */
public class TextReplyer {

    public final static String NEXMO = "http://rest.nexmo.com/sms/json";
    public final static String RESPONCE = "text=%s&to=%s&from=%s&api_key=%s&api_secret=%s";
    public final static String charset = "UTF-8";
    public final static String API_KEY = "935ccbbf";
    public final static String API_SECRET = "0af96623";
    public final static String FROM = "askr";

    public static boolean sentMessage(String text, String to) {
        try {
            String query = String.format(RESPONCE,
                    URLEncoder.encode(text, charset),
                    URLEncoder.encode(to, charset),
                    URLEncoder.encode(FROM, charset),
                    URLEncoder.encode(API_KEY, charset),
                    URLEncoder.encode(API_SECRET, charset));
            HttpURLConnection conn = (HttpURLConnection) new URL(NEXMO + "?" + query).openConnection();
            conn.connect();
            return conn.getResponseCode() == 200 ? true : false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
