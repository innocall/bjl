package com.rhino.bjl.server;

/**
 * Created by wuxiaotie on 2017/6/23.
 */
public interface ILoginMessage {

    Object[] login(String username, String password);

    boolean reetTbl();

    boolean roomTbl();

    boolean reetTbl2();

    boolean reetTbl3();

    boolean getParameter(String number);
}
