package com.rhino.bjl.server;

import java.util.HashMap;
import java.util.List;

public interface IDataMessage {
    List<HashMap<String,Object>> findReetList(String category, String one, String two);

    HashMap<String,Object> findReetList2(String start, String limit, String oneCount, String twoCount, String threeCount, String oneType, String twoType, String threeType, String one, String two, String three, String allCount, String pages, String threeType4);

    List<HashMap<String,Object>> findReetListByMN(String jishu, String oushu, int start, int limit, String qiang);

    int findReetListCountByMN(String jishu, String oushu, int start, int limit, String qiang);

    List<HashMap<String,Object>> findReetByPai(String xian1, String xian2, String xian3, String zhuang1, String zhuang2, String zhuang3, String qiang);

    HashMap<String,Object> findReetProbability(String oneCount, String twoCount, String threeCount, String oneType, String twoType, String threeType, String one, String two, String three, String allCount, String pages, String threeType4);


    List<HashMap<String, Object>> findSReetList(int start, int limit);

    int findSReetListCount();

    HashMap<String, Object> selectProbability(String type);
}
