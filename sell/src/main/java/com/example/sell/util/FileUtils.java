package com.example.sell.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileUtils {
    /**
     *
     * @param file 文件
     * @param path 文件存放路径
     * @return
     */
    public static boolean upload(MultipartFile file, String path) {

        File dest = new File(path);
        //判断文件父目录是否存在
        if(!dest.getParentFile().exists()){
            dest.getParentFile().mkdir();
        }
        try {
            file.transferTo(dest);
        } catch (IOException e) {
           return false;
        }catch (IllegalStateException e){
            return false;
        }
        return true;
    }

}
