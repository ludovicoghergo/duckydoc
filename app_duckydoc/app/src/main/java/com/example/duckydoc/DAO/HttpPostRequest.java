package com.example.duckydoc.DAO;

import android.os.AsyncTask;
import android.util.Log;
import android.webkit.CookieManager;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

public class HttpPostRequest extends AsyncTask<String, Void, String> {

    // This is the JSON body of the post
    Object postData;

    // This is a constructor that allows you to pass in the JSON body
    public HttpPostRequest(Object postData) {
        if (postData != null) {
            this.postData = postData;
        }
    }

    @Override
    protected String doInBackground(String... params) {
        String urlRequest = params[0];
        HttpURLConnection con = null;
        try {
            URL url = new URL(urlRequest);
            con = (HttpURLConnection) url.openConnection();

            con.setDoInput(true);
            con.setDoOutput(true);
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestMethod("POST");

            if (this.postData != null) {
                Gson gson = new Gson();
                OutputStreamWriter writer = new OutputStreamWriter(con.getOutputStream());
                Log.i("body", gson.toJson(postData));
                writer.write(gson.toJson(postData));
                writer.flush();
            }

            CookieManager cookieManager = CookieManager.getInstance();
            String cookie = cookieManager.getCookie(con.getURL().toString());
            if(cookie != null){
                con.setRequestProperty("Cookie", cookie);
            }
            con.connect();

            List<String> cookieList = con.getHeaderFields().get("Set-Cookie");
            if (cookieList != null) {
                for (String cookieTmp : cookieList) {
                    cookieManager.setCookie(con.getURL().toString(), cookieTmp);
                }
            }

            StringBuilder sb = new StringBuilder();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String json;
            while ((json = bufferedReader.readLine()) != null) {
                sb.append(json).append("\n");
            }
            return sb.toString().trim();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Tools.genericError;
        }finally {
            if (con != null) {
                con.disconnect();
            }
        }
    }
}
