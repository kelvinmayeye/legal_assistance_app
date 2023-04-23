package com.example.legalassistance;

import android.content.Context;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Http {
    Context context;
    private String url,method ="GET",data=null, responce=null;
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

    public void setData(String data) {
        this.data = data;
    }

    public Boolean getToken() {
        return token;
    }

    public void setToken(Boolean token) {
        this.token = token;
    }

    public String getResponce() {
        return responce;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void send(){
        try{
            URL sUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) sUrl.openConnection();
            connection.setRequestMethod(method);
            connection.setRequestProperty("Context-Type","application/json");
            connection.setRequestProperty("X-Requested-With","XMLHttpRequest");
            if (token){
                connection.setRequestProperty("Authorization","Bearer"+localStorage.getToken());
            }
            if(! method.equals("GET")){
                connection.setDoOutput(true);
            }
            if (data != null){
                OutputStream os = connection.getOutputStream();
                os.write(data.getBytes());
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
            responce = sb.toString();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
