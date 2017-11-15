package com.rhino.bjl.utils;

public class StringUtils {

    /**
     *  3-9-6 格式化为(3,9,6)
     * @param str
     * @return
     */
    public static String formatSql(String str) {
        String s = "";
        String arr[] = str.split("-");
        if (arr.length > 1){
            s = "(";
            for (int i=0;i<arr.length;i++) {
                if (i == arr.length - 1) {
                    s = s + arr[i] + ")";
                } else {
                    s = s + arr[i] + "," ;
                }
            }
        } else {
            s = "("+ str +")";
        }
        return s;
    }

}
