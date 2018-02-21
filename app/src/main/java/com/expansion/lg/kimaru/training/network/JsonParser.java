package com.expansion.lg.kimaru.training.network;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.util.Log;

public class JsonParser {

    static String json = "";

    // constructor
    public JsonParser() {

    }

    public String getJSONFromUrl(String urlString) {

        // Making HTTP request

        try {
            Log.d("TREMAP", urlString);
            Log.d("TREMAP", "===================================");
            Log.d("TREMAP", "GETTING DATA");
            Log.d("TREMAP", "===================================");
            URL url = new URL(urlString);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(httpURLConnection.getInputStream());

            // Read the BufferedInputStream
            BufferedReader r = new BufferedReader(new InputStreamReader(in));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = r.readLine()) != null) {
                sb.append(line);
            }
            json = sb.toString();
            httpURLConnection.disconnect();
        }catch (MalformedURLException e){
            Log.d("Tremap", "MALFORMED -- : "+e.getMessage());
        }catch(IOException e){
            Log.d("Tremap", "IO ERROR -- : "+ e.getMessage());
        }finally {

        }

        return json;

    }
}