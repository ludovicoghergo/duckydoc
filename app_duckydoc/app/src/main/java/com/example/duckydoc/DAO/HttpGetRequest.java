package com.example.duckydoc.DAO;

import android.os.AsyncTask;
import android.util.Log;
import android.webkit.CookieManager;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class HttpGetRequest extends AsyncTask<String, Void, String> {
    @Override
    protected String doInBackground(String... params) {
        String urlRequest = params[0];
        HttpURLConnection con = null;
        try {
            URL url = new URL(urlRequest);
            con = (HttpURLConnection) url.openConnection();

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
    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }
}