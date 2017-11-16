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
                String j = arr[i];
                if ("A".equals(j)) {
                    j = "1";
                } else  if ("J".equals(j)) {
                    j = "11";
                } else  if ("Q".equals(j)) {
                    j = "12";
                } else  if ("K".equals(j)) {
                    j = "13";
                }
                if (i == arr.length - 1) {
                    s = s + j + ")";
                } else {
                    s = s + j + "," ;
                }
            }
        } else {
            s = "("+ str +")";
        }
        return s;
    }

}
