package com.askr.hackathon.tools;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by jamessharman on 19/10/2014.
 */
public class NumberInfoCaller {

    public final static String NEXMO = "https://rest.nexmo.com/ni/json";
    public final static String RESPONSE = "number=%s&callback=%s&api_key=%s&api_secret=%s";
    public final static String charset = "UTF-8";
    public final static String API_KEY = "935ccbbf";
    public final static String API_SECRET = "0af96623";
    public final static String CALLBACK = "http://www.handlocker.co.uk/hackathon/NumberInfo";

    public static boolean queryNumberInfo(String number) {
        try {
            String query = String.format(RESPONSE,
                    URLEncoder.encode(number, charset),
                    URLEncoder.encode(CALLBACK, charset),
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
