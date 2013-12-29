package com.example.autocomp;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;
 
public class JParser {
	
	public static final String LOG_TAG = "JSON PARSER";
    static InputStream is = null;
    static JSONObject jObj = null;
    static String json = "";
 
    // constructor
    public JParser() {
    	
    }
 
    public JSONObject getJSONFromUrl(String url) {
 
        // Making HTTP request
        try {
            // defaultHttpClient
            DefaultHttpClient httpClient = new DefaultHttpClient();
            Log.d("JParser","after DefaultHttpClient");
            HttpGet httpget = new HttpGet(url);
            Log.d("JParser","after HttpGet");
            httpget.setHeader("Content-Type", "application/json");
            HttpResponse httpResponse = httpClient.execute(httpget);
            HttpEntity httpEntity= httpResponse.getEntity();
            
            is = httpEntity.getContent();  
            
            /*
              HttpPost httpPost = new HttpPost(url);
              httpPost.setHeader("Content-Type", "application/json");
              httpPost.setHeader("Accept", "JSON");           
              HttpResponse httpResponse = httpClient.execute(httpPost);
              HttpEntity httpEntity = httpResponse.getEntity();
              */
              //Log.d(LOG_TAG,httpPost);
 
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
 
        try {
        	
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    is, "iso-8859-1"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
            	sb.append(line + "\n");
            	Log.d("JParser",line);
            }
            is.close();
            json = sb.toString();
            
            
        } catch (Exception e) {
            Log.e("Buffer Error", "Error converting result " + e.toString());
        }
 
        // try parse the string to a JSON object
        try {
            jObj = new JSONObject(json);
        } catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }
 
        // return JSON String
        return jObj;
 
    }
    
}