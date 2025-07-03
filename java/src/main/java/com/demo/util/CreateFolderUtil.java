package com.demo.util;

import java.io.IOException;
import java.nio.file.Files;

import java.nio.file.Path;

public class CreateFolderUtil {
    //引数のパスのディレクトリが存在するかをチェックし、存在しなければ作成する
    public static void CreateFolder(Path path){
        if(!Files.exists(path)){
            path.toFile().mkdirs();

//            try {
//                Files.createDirectory(path);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
        }
    }
}
