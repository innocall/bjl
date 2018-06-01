package com.rhino.bjl.server;

import java.util.HashMap;
import java.util.List;

public interface IDataMessage {
    List<HashMap<String,Object>> findReetList(String category, String one, String two);

    List<HashMap<String,Object>> findReetList2(String oneType, String twoType, String threeType, String one, String two, String three,String allCount);
}
