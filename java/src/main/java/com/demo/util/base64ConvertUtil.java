package com.demo.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;

public class base64ConvertUtil {
    //base64でコード処理
    public static int  base64change(String base64img,String fileName){
        int result = 0;

        byte[] bytes = Base64.getDecoder().decode(base64img);
        FileOutputStream file = null;

        try {
            file = new FileOutputStream(fileName);
            file.write(bytes);
            file.close();
            result = 1;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}