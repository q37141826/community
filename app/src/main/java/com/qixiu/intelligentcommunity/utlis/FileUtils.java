package com.qixiu.intelligentcommunity.utlis;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Description：文件工具类
 * Author：XieXianyong
 * Date： 2017/6/20 20:24
 */
public class FileUtils {
    // SD卡文件根目录名称
    public final static String ROOT_DIRECTORY = "Intelligentcommunity";
    // 图片目录
    public static final String IMAGE_PATH = "image";

    public static String SDPATH = Environment.getExternalStorageDirectory()
            + "/formats/";

    // 图片下载的SD卡路径
    public static String getPubImagePath() {
        String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + File.separator + ROOT_DIRECTORY;
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        return path;
    }

    // 图片下载的SD卡路径
    public static String getImagePath() {
        return getStructureDirs(IMAGE_PATH);
    }

    /**
     * 创建文件路径 <br/>
     */
    public static String getStructureDirs(String dir) {
        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + ROOT_DIRECTORY + File.separator + dir;
        File file = new File(path);
        if (!file.exists()) {
            try {
                file.mkdirs();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return file.getAbsolutePath();
    }

    /**
     * 判断SD卡上的文件夹是否存在
     */
    public static boolean isFileExist(String path, String fileName) {
        File file = new File(path + File.separator + fileName);
        return file.exists();
    }

    // 判断图片是否已经下载 如果存在返回路径
    public static String isPubImageFileExist(String imgName) {
        if (isFileExist(getPubImagePath(), imgName)) {
            return getPubImagePath() + File.separator + imgName;
        }
        return "";
    }

    //扫描指定文件
    public static void scanFileAsync(Context ctx, String filePath) {
        Intent scanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        scanIntent.setData(Uri.fromFile(new File(filePath)));
        ctx.sendBroadcast(scanIntent);
    }

    public static void saveBitmap(Bitmap bm, String picName) {
        Log.e("", "保存图片");
        try {
            if (!isFileExist("")) {
                File tempf = createSDDir("");
            }
            File f = new File(SDPATH, picName + ".JPEG");
            if (f.exists()) {
                f.delete();
            }
            FileOutputStream out = new FileOutputStream(f);
            bm.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();
            Log.e("", "已经保存");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static File createSDDir(String dirName) throws IOException {
        File dir = new File(SDPATH + dirName);
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {

            System.out.println("createSDDir:" + dir.getAbsolutePath());
            System.out.println("createSDDir:" + dir.mkdir());
        }
        return dir;
    }

    public static boolean isFileExist(String fileName) {
        File file = new File(SDPATH + fileName);
        file.isFile();
        return file.exists();
    }
}
