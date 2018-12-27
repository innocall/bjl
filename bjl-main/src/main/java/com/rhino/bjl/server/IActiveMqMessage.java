package com.rhino.bjl.server;


import java.util.Map;

public interface IActiveMqMessage {

    void sendMessage(Map<String,Object> map);
}
