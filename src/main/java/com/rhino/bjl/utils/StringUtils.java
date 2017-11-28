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
                j = getCountFormat(j);
                if (i == arr.length - 1) {
                    s = s + j + ")";
                } else {
                    s = s + j + "," ;
                }
            }
        } else {
            str = getCountFormat(str);
            s = "("+ str +")";
        }
        return s;
    }

    public static String getCountFormat(String j) {
        if ("A".equals(j)) {
            j = "1";
        } else  if ("J".equals(j)) {
            j = "11";
        } else  if ("Q".equals(j)) {
            j = "12";
        } else  if ("K".equals(j)) {
            j = "13";
        }
        return j;
    }

    /**
     * 0 传人是 0
     * 1 传入基数
     * 2 传入偶数
     * 3 空
     * @param str
     * @return
     */
    public static int getCountType(String str) {
        if (org.apache.commons.lang.StringUtils.isNotBlank(str)) {
            if (str.equals("10") || str.equals("11") || str.equals("12") || str.equals("13")) {
                return 0;
            } else if (Integer.parseInt(str)%2 == 0) {
                return 2;
            } else {
                return 1;
            }
        } else {
            return 3;
        }
    }

    public static int getCount(int allCount) {
        int count = allCount;
        if (allCount > 9) {
            count = allCount % 10;
        }
        return count;
    }

    public static int showData(int data) {
        if (data > 9) {
            return 0;
        } else {
            return data;
        }
    }

}
