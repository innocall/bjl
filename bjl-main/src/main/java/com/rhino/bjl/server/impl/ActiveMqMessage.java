package com.rhino.bjl.server.impl;

import com.rhino.bjl.server.IActiveMqMessage;
import com.rhino.bjl.utils.JsonUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import java.util.Map;

@Service
public class ActiveMqMessage implements IActiveMqMessage {

    public static final Logger logger = Logger.getLogger(ActiveMqMessage.class);

    @Resource(name = "jmsTemplate")
    private JmsTemplate jmsTemplate;

    //目的地队列的明证，我们要向这个队列发送消息
    @Resource(name = "destinationQueue")
    private Destination destination;

    @Override
    public void sendMessage(Map<String, Object> map) {
       final String msg = JsonUtil.toJsonString(map);
        try {
            logger.info("将要向队列{"+destination+"}发送的消息msg:{"+msg+"}");
            jmsTemplate.send(destination, new MessageCreator() {
                @Override
                public Message createMessage(Session session) throws JMSException {
                    return session.createTextMessage(msg);
                }
            });

        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error("向队列{"+destination+"}发送消息失败，消息为：{"+msg+"}");
        }
    }
}
