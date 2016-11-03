package com.example.wangmiao.teamdemo01.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.v4.util.LruCache;
import android.widget.Toast;

import com.example.wangmiao.teamdemo01.receiver.JudgeNetIsConnectedSuccessReceiver;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by kzj on 2016/10/9.
 */
public class LruCatcheImage {
    public static Map<String,SoftReference<Bitmap>> map=new HashMap<>();
    public static LruCache<String,Bitmap> lruCache;
    private static String savePath;
    private static Bitmap bitmap1;
    private static Map<String,Bitmap> imagemap=new HashMap<>();

    public static Bitmap getBitMapByPath(String path,Context context){
        Bitmap bitmap = null;
        initCache();
          //System.out.println("path="+path);
          savePath=getSavePath(path);
          //System.out.println("savePath="+savePath);
          Bitmap bitMapFromCache = getBitMapFromCache(path);
          //System.out.println("bitMapFromCache="+bitMapFromCache);
          if(bitMapFromCache!=null){
              return bitMapFromCache;
          }else {
              downloadFromNet(path, context);
              //System.out.println("path="+path);
              //System.out.println("bitmap="+imagemap.get(path));
              //System.out.println("imagemap="+imagemap);


              bitmap = imagemap.get(path);
              //System.out.println("bitmap="+bitmap);

              return bitmap;
          }


    }

    private static String getSavePath(String path) {
        String absolutePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath();
        //System.out.println("path="+path);
        String substring = path.substring(path.lastIndexOf("/"));
        //System.out.println("substring="+substring);
        savePath=absolutePath+substring;
        return savePath;
    }

    private static void downloadFromNet(final String path, final Context context) {
        if(JudgeNetIsConnectedSuccessReceiver.JudgeNetIsConnectedSuccess(context)){
            getInputStreamFromNet(path, context,new GetInputStream() {
                @Override
                public void getInputStream(InputStream inputStreamFromNet) {
                    //System.out.println("inputStream="+inputStreamFromNet);
                    if(inputStreamFromNet!=null){
                        byte[] buffer = NetUtils.inputStreamTobyteArray(inputStreamFromNet);
                        //System.out.println("buffer="+buffer);
                        boolean b = NetUtils.savebyteArrayToSdcard(buffer, savePath);
                        //System.out.println("b="+b);
                        Bitmap bitmap = BitmapFactory.decodeByteArray(buffer, 0, buffer.length);
                        //System.out.println("bitmap="+bitmap);
                        lruCache.put(path,bitmap);
                        imagemap.put(path,bitmap);


                    }else {
                        Toast.makeText(context, "访问网络失败", Toast.LENGTH_SHORT).show();
                    }

                }
            });



        }else {
            Toast.makeText(context, "手机没有联网，请先联网", Toast.LENGTH_SHORT).show();

        }
    }
interface GetInputStream{
    void getInputStream(InputStream inputStream);
}
    private static void getInputStreamFromNet(final String path, final Context context, final GetInputStream get) {
      /*  final Handler handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                System.out.println("handleMessage.Thread="+Thread.currentThread().getName());
                Toast.makeText(context, (CharSequence) msg.obj, Toast.LENGTH_SHORT).show();
            }
        };*/
        if(JudgeNetIsConnectedSuccessReceiver.JudgeNetIsConnectedSuccess(context)){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        URL url = new URL(path);
                        //System.out.println("path="+path);
                        //InputStream inputStream = url.openStream();
                        HttpURLConnection httpURLConnection= (HttpURLConnection) url.openConnection();
                        httpURLConnection.setRequestMethod("GET");
                        httpURLConnection.setConnectTimeout(5000);
                        if(httpURLConnection.getResponseCode()==HttpURLConnection.HTTP_OK){
                            InputStream inputStream = httpURLConnection.getInputStream();
                            get.getInputStream(inputStream);
                        }else {
                            Toast.makeText(context, "访问网络失败", Toast.LENGTH_SHORT).show();
                      /* Message message = new Message();
                       message.obj="网络连接超时,请稍后再试";
                       handler.sendMessage(message);*/

                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }else {
            Toast.makeText(context, "手机没有联网，请先联网", Toast.LENGTH_SHORT).show();
        }

    }


    private static Bitmap getBitMapFromCache(String key) {
        Bitmap bitmap=null;
        bitmap = lruCache.get(key);
        if(bitmap!=null){
            System.out.println("从一级缓存中获取图片");
            return bitmap;
        }else {
            SoftReference<Bitmap> bitmapSoftReference = map.get(key);
            if(bitmapSoftReference!=null){
                System.out.println("从二级缓存中获取图片");
               bitmap = bitmapSoftReference.get();
                if(bitmap!=null){
                    Bitmap put = lruCache.put(key, bitmap);
                    if(put!=null){
                        map.remove(key);
                    }
                }
            }else {
                Bitmap bitMapFromSDCard = getBitMapfromSdcard(savePath);
                if(bitMapFromSDCard!=null){
                    System.out.println("从三级缓存中获取图片");
                    lruCache.put(key,bitMapFromSDCard);
                }

            }
        }
        return null;
    }

    public static Bitmap getBitMapfromSdcard(String savePath){
        //String savePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath() + File.separator + fileName;
        try {
            //System.out.println("savePath="+savePath);
            File file = new File(savePath);
            if(!file.exists()){
               file.createNewFile();
            }
            InputStream inputStream=new FileInputStream(file);
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            //System.out.println("bitmap="+bitmap);
            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static void initCache() {
        long maxMemory = Runtime.getRuntime().maxMemory();
        lruCache=new LruCache<String,Bitmap>((int)maxMemory/8){
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getRowBytes()*value.getHeight();
            }

            @Override
            protected void entryRemoved(boolean evicted, String key, Bitmap oldValue, Bitmap newValue) {
                if(evicted){
                    SoftReference<Bitmap> bitmapSoftReference = new SoftReference<>(oldValue);
                    map.put(key,bitmapSoftReference);
                }
            }
        };
    }
}
