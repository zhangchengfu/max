package com.laozhang.max_interview;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileUtil {
    //写一个文件复制方法
    public void copyFile(String src, String des) {
        FileInputStream input = null;
        FileOutputStream output = null;
        try {
            input = new FileInputStream(new File(src));
            output = new FileOutputStream(new File(des));

            byte[] bt = new byte[1024];
            int realbyte = 0;

            while ((realbyte = input.read(bt)) > 0) {
                output.write(bt, 0, realbyte);
            }
            output.flush();
            output.close();
            input.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (input != null) {
                    input.close();
                }
                if (output != null) {
                    output.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
