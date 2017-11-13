package com.rhino.bjl.utils;

import java.io.*;

/**
 * 批量修改扑克牌名字
 */
public class Test {

    public static String path = "C:\\Users\\Administrator\\Desktop\\新建文件夹 (3)\\9"; //扑克牌路径
    public static int len = 208;

    public static void main(String[] args) {
        File file = new File(path);
        if (file.exists()) {
            File[] files = file.listFiles();
            String str = "";
            for (File file2 : files) {
                if (file2.isDirectory()) {
                    // System.out.println("文件夹:" + file2.getAbsolutePath());
                } else {
                    String fileName = file2.getName();
                    String s = "imageName == \"" + file2.getName() + "\"";
                    str = str + s  + "||";
//                    "veryhuo.com_pkp_8.jpg"
//                    int startLen = 16;
//                    int endLen = fileName.length() - 4;
//                    String str = fileName.substring(startLen,endLen);
//                    fileName = fileName.substring(0,startLen) + (Integer.parseInt(str) + len) + fileName.substring(endLen,fileName.length());
//                     copyFile(file2.getAbsolutePath(),path + "\\" +fileName);
                    //System.out.println("文件:" + file2.getName() + " --" + str + "--" + fileName);
                }
            }
            System.out.println(str);
        } else {
            System.out.println("文件不存在!");
        }
    }




    public static void copyFile(String oldPath, String newPath) {
      /*  String commandStr = "copy " + oldPath + " " + newPath;
        System.out.println(commandStr);
        try {
            Runtime.getRuntime().exec(commandStr);
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        try {
            int bytesum = 0;
            int byteread = 0;
            File oldfile = new File(oldPath);
            if (oldfile.exists()) { //文件存在时
                InputStream inStream = new FileInputStream(oldPath); //读入原文件
                FileOutputStream fs = new FileOutputStream(newPath);
                byte[] buffer = new byte[1444];
                int length;
                while ((byteread = inStream.read(buffer)) != -1) {
                    bytesum += byteread; //字节数 文件大小
                    System.out.println(bytesum);
                    fs.write(buffer, 0, byteread);
                }
                inStream.close();
            }
        } catch (Exception e) {
            System.out.println("复制单个文件操作出错");
            e.printStackTrace();

        }

    }
}
