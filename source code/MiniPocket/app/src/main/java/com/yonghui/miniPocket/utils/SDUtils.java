package com.yonghui.miniPocket.utils;

import android.os.Environment;
import android.text.TextUtils;

import java.io.*;


public class SDUtils {



    private static boolean isMounted() {
        return Environment.MEDIA_MOUNTED.equals(
                Environment.getExternalStorageState());
    }

    private static String getRootPath() {
        if (isMounted()) {
            String path = Environment.getExternalStorageDirectory().getAbsolutePath()
                    +
                    File.separator + "MiniPocket";

            File file = new File(path);
            if (!file.exists()) {
                file.mkdirs();
            }

            return file.getAbsolutePath();
        }

        return null;
    }

    public static void delete(String fileName) {
        String rootPath = getRootPath();
        if (TextUtils.isEmpty(rootPath)) {
            return;
        }
        File file = new File(rootPath, fileName);
        if (file.exists()) {
            file.delete();
        }
    }

    public static boolean save(String fileName, String data) {

        String rootPath = getRootPath();

        if (TextUtils.isEmpty(rootPath)) {
            return false;
        }

        File file = new File(rootPath, fileName);

        try {
            FileWriter fw = new FileWriter(file, true);
            BufferedWriter bos = new BufferedWriter(fw);
            bos.write(data);
            bos.newLine();
            bos.flush();
            return true;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }
}
