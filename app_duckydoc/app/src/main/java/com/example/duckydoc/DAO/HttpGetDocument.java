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

    //1. The preparation method that runs in the UI thread before the asynchronous task is executed. Initialization operation
    @Override
    protected void onPreExecute() {
        //Get the current thread object, name
        Log.i("tag", Thread.currentThread().getName() + "----onPreExecute-------");
    }

    //2. Rewrite a time-consuming operation method, execute after the onPreQExecute() method, and run in the worker thread

    /**
     * The parameter type of the method is consistent with the first parameter type of the generic type in the class,
     * The return value is consistent with the third parameter of the generic type in the class
     */
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
                int temp = 0;
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

    //3. When the doInBackground() method is executed, the result of the time-consuming operation is returned to the method and displayed on the UI interface
    @Override
    protected void onPostExecute(byte[] res) {
        //Judging whether the result is empty
        super.onPostExecute(res);
    }

}
