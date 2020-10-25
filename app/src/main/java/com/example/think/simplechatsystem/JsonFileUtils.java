package com.example.think.simplechatsystem;

import android.app.DownloadManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Administrator on 2017/9/9.
 */

public class JsonFileUtils {


    // write SDCard
    public static void writeFileSdcardFile(String fileName, String writeStr)  {
        try {
            String ff = MyApplication.getInstance().getFilesDir().getPath().toString() + "/"+fileName;
            File yourFile = new File(ff);
            yourFile.createNewFile(); // if file already exists will do nothing
            FileOutputStream fout = new FileOutputStream(yourFile,false);
            byte[] bytes = writeStr.getBytes();
            fout.write(bytes);
            fout.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //    read SDCard
    public static String readFileSdcardFile(String fileName) {
        String res = null;
        try {
            String ff = MyApplication.getInstance().getFilesDir().getPath().toString() + "/"+fileName;
            FileInputStream fin = new FileInputStream(ff);
            int length = fin.available();

            byte[] buffer = new byte[length];
            fin.read(buffer);
            res = new String(buffer);
            fin.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    public static boolean fileIsExists(String path){
        try{
            File f=new File(path);
            if(!f.exists()){
                return false;
            }

        }catch (Exception e) {
            // TODO: handle exception
            return false;
        }
        return true;
    }

    //加载图片
    public static Bitmap getURLimage(String url) {
        Bitmap bmp = null;
        try {
            URL myurl = new URL(url);
            // 获得连接
            HttpURLConnection conn = (HttpURLConnection) myurl.openConnection();
            conn.setConnectTimeout(6000);//设置超时
            conn.setDoInput(true);
            conn.setUseCaches(false);//不缓存
            conn.connect();
            InputStream is = conn.getInputStream();//获得图片的数据流
            bmp = BitmapFactory.decodeStream(is);
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bmp;
    }
    //加载图片
    public static String SaveURLImage(String url) {
        Bitmap bmp = null;
        URL myurl = null;
        String path="";
        //创建下载任务,downloadUrl就是下载链接
        try {
            myurl = new URL(url);
            path = myurl.getFile();
            path = path.substring(path.lastIndexOf("/")+1);
            // 获得连接
        } catch (Exception e) {
            e.printStackTrace();
        }

    //指定下载路径和下载文件名
        String ff = MyApplication.getInstance().getFilesDir().getPath().toString() + "/";
        if(fileIsExists(ff+path))return ff+path;
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        request.setDestinationInExternalPublicDir(ff, path);
    //获取下载管理器
        DownloadManager downloadManager= (DownloadManager) MyApplication.getInstance().getSystemService(Context.DOWNLOAD_SERVICE);
    //将下载任务加入下载队列，否则不会进行下载
        downloadManager.enqueue(request);
        return ff+path;
    }
}
