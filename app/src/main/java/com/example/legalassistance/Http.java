package com.example.legalassistance;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Http {
    Context context;
    private String url,method ="POST",data=null, response=null;
    private Integer statusCode = 0;
    private Boolean token = false;
    private LocalStorage localStorage;


    public Http(Context context, String url) {
        this.context = context;
        this.url = url;
        localStorage = new LocalStorage(context);
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method.toUpperCase();
    }

    public String getData() {
        return data;
    }

    public String setData(String data) {
        this.data = data;
        return data;
    }

    public Boolean getToken() {
        return token;
    }

    public void setToken(Boolean token) {
        this.token = token;
    }

    public String getResponse() {
        return response;
    }


    public Integer getStatusCode() {
        return statusCode;
    }

    public void send(){
        try{
            URL sUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) sUrl.openConnection();
            connection.setRequestMethod(method);
            connection.setRequestProperty("X-Requested-With","XMLHttpRequest");
            connection.setRequestProperty("Content-Type", "application/json");
            if (getToken()){
                connection.setRequestProperty("Authorization", "Bearer " + localStorage.getToken());
                Log.d("Token from localstorage:", localStorage.getToken()); // Print the token value to logs
            }

            connection.setDoOutput(true);
            if(! method.equals("GET")){
                connection.setDoOutput(true);
            }
            if (getData() != null) {
                connection.setRequestProperty("Content-Type", "application/json");
                OutputStream os = connection.getOutputStream();
                os.write(getData().getBytes("UTF-8"));
                os.flush();
                os.close();
            }

            statusCode = connection.getResponseCode();
            InputStreamReader isr;
            if (statusCode >= 200 && statusCode <= 299){
                isr = new InputStreamReader(connection.getInputStream());
            } else {
                isr = new InputStreamReader(connection.getErrorStream());
            }

            BufferedReader br = new BufferedReader(isr);
            StringBuffer sb = new StringBuffer();
            String line;
            while((line = br.readLine()) != null){
                sb.append(line);
            }
            br.close();
            response = sb.toString();

            Log.w("response from api :",response);
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
