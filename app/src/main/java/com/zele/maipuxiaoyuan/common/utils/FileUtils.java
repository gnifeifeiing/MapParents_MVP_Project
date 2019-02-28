package com.zele.maipuxiaoyuan.common.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.channels.FileChannel;

/**
 * Created by Administrator on 2016/9/18.
 */
public class FileUtils {

    public static void save(Context context, String text, String filetext) {
        try {
            if(text!=null&&filetext!=null&&context!=null){

            FileOutputStream outStream =context.openFileOutput(filetext, Context.MODE_PRIVATE);
            outStream.write(text.getBytes());
            outStream.close();
            }
        } catch (FileNotFoundException e) {
            return;
        } catch (IOException e) {
            return;
        }
    }

    /*
 * 定义文件读取的方法
 * */
    public static String read(Context context, String filename) throws IOException {
        //打开文件输入流
        FileInputStream input = context.openFileInput(filename);
        //定义1M的缓冲区
        byte[] temp = new byte[1024];
        //定义字符串变量
        StringBuilder sb = new StringBuilder("");
        int len = 0;
        //读取文件内容，当文件内容长度大于0时，
        while ((len = input.read(temp)) > 0) {
            //把字条串连接到尾部
            sb.append(new String(temp, 0, len));
        }
        //关闭输入流
        input.close();
        //返回字符串
        return sb.toString();
    }
    
    public static String reads(String filename) {
		String str = "";
		try {
			InputStreamReader reader = new InputStreamReader(
					new FileInputStream(filename));
			BufferedReader buff = new BufferedReader(reader);
			try {
				String temp;
				while ((temp = buff.readLine()) != null) {
					str = str + temp + "";
				}
				buff.close();
				reader.close();
			} catch (IOException io) {
				System.out.println("读取文件失败");
				io.printStackTrace();
			} catch (Exception e) {
				System.out.println("读取文件失败");
				e.printStackTrace();
			}
		} catch (FileNotFoundException ne) {
			System.out.println("没有找到目标文件");
			ne.printStackTrace();
		} catch (Exception p) {
			System.out.println("没有找到目标文件");
			p.printStackTrace();
		}
		return str;
	}
    
    public static boolean copy(File source, File target){
        FileChannel in = null;
        FileChannel out = null;
        FileInputStream inStream = null;
        FileOutputStream outStream = null;
        try {
            inStream = new FileInputStream(source);
            outStream = new FileOutputStream(target);
            in = inStream.getChannel();
            out = outStream.getChannel();
            in.transferTo(0, in.size(), out);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(inStream!=null)inStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if(in!=null)in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if(outStream!=null)outStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if(out!=null)out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
    
    public static void saveImageToGallery(Context context, File source) {
        // 首先保存图片
        File appDir = new File(Environment.getExternalStorageDirectory(), "image");
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        File file = new File(appDir, source.getName());
        copy(source,file);
        // 其次把文件插入到系统图库
        try {
            MediaStore.Images.Media.insertImage(context.getContentResolver(),file.getAbsolutePath(), file.getName(), null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // 最后通知图库更新
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + file.getAbsolutePath())));
        ToastUtil.showToast(context,"图片已保存到"+file.getAbsolutePath());
    }

    public static void saveImageToGallery(Context context, Bitmap bitmap) {
        // 首先保存图片
        File appDir = new File(Environment.getExternalStorageDirectory(), "image");
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = System.currentTimeMillis() + ".jpg";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 其次把文件插入到系统图库
        try {
            MediaStore.Images.Media.insertImage(context.getContentResolver(),file.getAbsolutePath(), file.getName(), null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // 最后通知图库更新
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + file.getAbsolutePath())));
        ToastUtil.showToast(context,"图片已保存到"+file.getAbsolutePath());
    }
    public static File getUserDiskDir(Context context) {
        File cachePath = null;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            cachePath = context.getExternalFilesDir(null);
        } else {
            cachePath = context.getFilesDir();
        }
        return cachePath;
    }
    public static void copyImageToGallery(Context context, File file) {
    	// 首先保存图片
    	        File appDir = new File(Environment.getExternalStorageDirectory(), "image");
    	        if (!appDir.exists()) {
    	            appDir.mkdir();
    	        }
    	        String fileName = System.currentTimeMillis() + ".jpg";
    	        File dest = new File(appDir, fileName);
    	        if(!file.exists()){
    	            ToastUtil.showToast(context,"文件不存在！");
    	            return;
    	        }
    	        FileInputStream fi = null;
    	        FileOutputStream fo = null;
    	        FileChannel in = null;
    	        FileChannel out = null;
    	        try {
    	            fi = new FileInputStream(file);
    	            fo = new FileOutputStream(dest);
    	            in = fi.getChannel();//得到对应的文件通道
    	            out = fo.getChannel();//得到对应的文件通道
    	            in.transferTo(0, in.size(), out);//连接两个通道，并且从in通道读取，然后写入out通道
    	        } catch (IOException e) {
    	            e.printStackTrace();
    	        } finally {
    	            try {
    	                if(fi!=null)fi.close();
    	                if(in!=null)in.close();
    	                if(fo!=null)fo.close();
    	                if(out!=null)out.close();
    	            } catch (IOException e) {
    	                e.printStackTrace();
    	            }
    	        }
    	        // 其次把文件插入到系统图库
    	        try {
    	            MediaStore.Images.Media.insertImage(context.getContentResolver(),dest.getAbsolutePath(), dest.getName(), null);
    	        } catch (FileNotFoundException e) {
    	            e.printStackTrace();
    	        }
    	        // 最后通知图库更新
    	        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + dest.getAbsolutePath())));
    	        ToastUtil.showToast(context,"图片已保存到"+dest.getAbsolutePath());
    	    }

    public static String saveImageToCache(Context context, Bitmap bitmap) {
        // 首先保存图片
        File appDir = new File(context.getCacheDir(), "image");
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = System.currentTimeMillis() + ".jpg";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file.getAbsolutePath();
    }
    public static String saveImageToCache(Context context, Bitmap bitmap, String path) {
        // 首先保存图片
        File file = new File(path);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file.getAbsolutePath();
    }
}