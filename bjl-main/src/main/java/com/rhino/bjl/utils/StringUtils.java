package com.rhino.bjl.utils;

import com.rhino.bjl.bean.MaxMinBean;
import net.sf.json.JSONObject;

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
        if (org.apache.commons.lang.StringUtils.isNotBlank(str) && !str.equals("0")) {
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

    public static int showData(String data) {
        if (data == null || data.equals("")) {
            return 0;
        } else {
            if (Integer.parseInt(data) > 9) {
                return 0;
            } else {
                return Integer.parseInt(data);
            }
        }
    }


    public static String getJsonString(JSONObject jsonObject,String str) {
        Object obj = jsonObject.get(str);
        if (obj == null) {
            return "";
        }else {
            return obj.toString();
        }
    }

    public static MaxMinBean setMaxMin(String zhuang1, String zhuang2, String zhuang3, String xian1, String xian2, String xian3, int maxCount, int minCount) {
        if (StringUtils.showData(zhuang1) > 5) {
            maxCount = maxCount + 1;
        } else if (StringUtils.showData(zhuang1) < 6 && StringUtils.showData(zhuang1) > 0) {
            minCount = minCount + 1;
        }
        if (StringUtils.showData(zhuang2) > 5) {
            maxCount = maxCount + 1;
        } else if (StringUtils.showData(zhuang2) < 6 && StringUtils.showData(zhuang2) > 0) {
            minCount = minCount + 1;
        }
        if (StringUtils.showData(zhuang3) > 5) {
            maxCount = maxCount + 1;
        } else if (StringUtils.showData(zhuang3) < 6 && StringUtils.showData(zhuang3) > 0) {
            minCount = minCount + 1;
        }
        if (StringUtils.showData(xian1) > 5) {
            maxCount = maxCount + 1;
        } else if (StringUtils.showData(xian1) < 6 && StringUtils.showData(xian1) > 0) {
            minCount = minCount + 1;
        }
        if (StringUtils.showData(xian2) > 5) {
            maxCount = maxCount + 1;
        } else if (StringUtils.showData(xian2) < 6 && StringUtils.showData(xian2) > 0) {
            minCount = minCount + 1;
        }
        if (StringUtils.showData(xian3) > 5) {
            maxCount = maxCount + 1;
        } else if (StringUtils.showData(xian3) < 6 && StringUtils.showData(xian3) > 0) {
            minCount = minCount + 1;
        }
        return new MaxMinBean(maxCount,minCount);
    }

    /**
     * 返回小数点后2位
     * @param i
     * @return
     */
    public static float fromtFloat(float i) {
        float num=(float)(Math.round(i*100)/100);//如果要求精确4位就*10000然后/10000
        return num;
    }

    public static String fromtString(String i) {
        float num=(float)(Math.round(Float.parseFloat(i)*100)/100);//如果要求精确4位就*10000然后/10000
        return num + "";
    }

}
