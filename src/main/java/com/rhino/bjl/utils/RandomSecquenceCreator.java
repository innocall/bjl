package com.rhino.bjl.utils;

import java.util.Hashtable;
import java.util.Random;
import java.util.UUID;

public class RandomSecquenceCreator {
    static Hashtable hash = new Hashtable();
    // 随机数字生成器
    static Random rand = new Random(System.currentTimeMillis());
    static long lastRandTime = System.currentTimeMillis();

    public RandomSecquenceCreator() {
    }

    /**
     * 生成长度不限的随机数字序列号
     * @return String
     */
    public static String getId() {
        // 根据时间值，重置hash，否则hash会无限增大
        if (System.currentTimeMillis()-lastRandTime>20000)
            hash.clear();
        Integer id = new Integer(0);
        synchronized (hash) {
            // 生成一个唯一的随机数字
            id = new Integer(rand.nextInt());
            while (hash.containsKey(id)) {
                id = new Integer(rand.nextInt());
            }
            // 为当前用户保留该ID
            String data = "";
            hash.put(id, data);
        }
        lastRandTime = System.currentTimeMillis();
        return System.currentTimeMillis() + "" + Math.abs(id.intValue());
    }

    /**
     * 生成长度在length之内的随机数字序列号
     * @param length int
     * @return String
     */
    public static String getId(int length) {
        // 根据时间值，重置hash，否则hash会无限增大 System.currentTimeMillis()有13位
        if (System.currentTimeMillis()-lastRandTime>20000)
            hash.clear();
        Integer id = new Integer(0);
        String strId = "";
        synchronized (hash) {
            // 生成一个唯一的随机数字
            id = new Integer(rand.nextInt());
            if (length > 15)
                strId = System.currentTimeMillis() + "" + Math.abs(id.intValue());
            else
                strId = "" + Math.abs(id.intValue());
            if (strId.length() > length)
                strId = strId.substring(0, length);
            while (hash.containsKey(strId)) {
                id = new Integer(rand.nextInt());
                if (length > 15)
                    strId = System.currentTimeMillis() + "" + Math.abs(id.intValue());
                else
                    strId = "" + Math.abs(id.intValue());
                if (strId.length() > length)
                    strId = strId.substring(0, length);
            }
            // 为当前用户保留该ID
            String data = "";
            hash.put(strId, data);
        }
        lastRandTime = System.currentTimeMillis();
        return strId;
    }
    
    
    public synchronized static String getRandomCode(int length){
    	String sRand = ""; 
		for (int i = 0;i < length; i++) { 
			Random random = new Random();
			String rand = String.valueOf(random.nextInt(10)); 
			sRand += rand;
		}
		return sRand;
    }
    
    public static final String GenerateGUID(){  
        UUID uuid = UUID.randomUUID();  
        return uuid.toString();       
    }

    /**
     * 获取20位长度的数字id
     * start 4位
     * @return
     */
    public static int Guid=10;
    public static String getAssId(String start) {
       /* RandomSecquenceCreator.Guid+=1;
        long now = System.currentTimeMillis();
        //获取时间戳
        String info=now+"";
        info = info.substring(3,13);
        //获取三位随机数
        //int ran=(int) ((Math.random()*9+1)*100);
        //要是一段时间内的数据连过大会有重复的情况，所以做以下修改
        int ran=0;
        if(RandomSecquenceCreator.Guid>99){
            RandomSecquenceCreator.Guid=10;
        }
        ran=RandomSecquenceCreator.Guid;*/
        return start+createUUID(12);
    }

    public static String createUUID(int len) {
        String uuid = java.util.UUID.randomUUID().toString()
                .replaceAll("-", "");
        return uuid.substring(0, len);
    }

    public static void main(String[] args) {
		//String str = getRandomCode(4);
//		MD5Encrypt md5 = new MD5Encrypt();
//		str = md5.getMD5ofStr(str);
    //	String str = GenerateGUID();
	//	System.out.println(str);
    	//long Temp=Math.round(Math.random()*8999+1000);
    	/*
    	 * 8098 7400 - 80987499
    	 */
    	System.out.println(getAssId("JSB12028"));
    	System.out.println(createUUID(12));
    	System.out.println(System.currentTimeMillis() + "");
	}
}
