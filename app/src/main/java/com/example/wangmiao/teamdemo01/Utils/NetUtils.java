package com.example.wangmiao.teamdemo01.Utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by kzj on 2016/11/1.
 */
public class NetUtils  {
    public static InputStream getInputStreamFromNet(String path){
        try {
            URL url = new URL(path);
            HttpURLConnection httpURLConnection= (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setConnectTimeout(3000);
            int responseCode = httpURLConnection.getResponseCode();
            if(responseCode==HttpURLConnection.HTTP_OK){
               return httpURLConnection.getInputStream();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static String inputStreamToString(InputStream inputStream){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] buffer=new byte[1024];
        int len=0;
        try {
            while ((len=inputStream.read(buffer))!=-1){
                byteArrayOutputStream.write(buffer,0,len);
            }
            return byteArrayOutputStream.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
