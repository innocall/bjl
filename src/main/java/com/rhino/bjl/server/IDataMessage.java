package com.rhino.bjl.server;

import java.util.HashMap;
import java.util.List;

public interface IDataMessage {
    List<HashMap<String,Object>> findReetList(String category, String one, String two);
}
