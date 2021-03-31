package com.example.duckydoc.DAO;

import android.os.AsyncTask;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpGetDocument  extends AsyncTask<String, Void, byte[]> {

    private ByteArrayOutputStream outputStream;
    private byte[] images;

    @Override
    protected void onPreExecute() {
        //Get the current thread object, name
        Log.i("tag", Thread.currentThread().getName() + "----onPreExecute-------");
    }
    @Override
    protected byte[] doInBackground(String... params) {
        //Connect to the network to get the byte array of the picture
        try {
            outputStream = new ByteArrayOutputStream();
            images = null;
            URL url = new URL(params[0]);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoInput(true);
            conn.setRequestMethod("GET");
            conn.connect();//Connect
            //Response code
            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {
                InputStream input = conn.getInputStream();
                byte[] data = new byte[1024];
                int temp;
                while ((temp = input.read(data)) != -1) {
                    outputStream.write(data, 0, temp);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        images = outputStream.toByteArray();
        return images;
    }
    @Override
    protected void onPostExecute(byte[] res) {
        //Judging whether the result is empty
        super.onPostExecute(res);
    }

}
