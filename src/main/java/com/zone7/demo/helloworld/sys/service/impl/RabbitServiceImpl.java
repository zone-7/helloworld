package com.zone7.demo.helloworld.sys.service.impl;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.zone7.demo.helloworld.sys.service.RabbitService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * 消息监听器以及发送服务
 */
@Service
public class RabbitServiceImpl implements RabbitService {
    private static final Logger log= LoggerFactory.getLogger(RabbitServiceImpl.class);

    @Autowired
    private Environment env;

    @Autowired
    private RabbitTemplate rabbitTemplate;


    /**
     * 消息消费
     * @param message
     */
    @RabbitListener(queues = "${basic.info.mq.queue.name}",containerFactory = "singleListenerContainer")
    public void consumeMessage(@Payload byte[] message){
        try {
            //TODO：接收String
            String result=new String(message,"UTF-8");
            log.info("接收String消息： {} ",result);
        }catch (Exception e){
            log.error("监听消费消息 发生异常： ",e.fillInStackTrace());
        }
    }

    /**
     * 消息发送
     * @param message
     */
    @Override
    public void sendMessage(String message) {
        try {
            log.info("待发送的消息： {} ",message);

            rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
            rabbitTemplate.setExchange(env.getProperty("basic.info.mq.exchange.name"));
            rabbitTemplate.setRoutingKey(env.getProperty("basic.info.mq.routing.key.name"));

            Message msg= MessageBuilder.withBody(message.getBytes(Charset.forName("UTF-8"))).build();
            rabbitTemplate.convertAndSend(msg);

        }catch (Exception e){
            log.error("发送简单消息发生异常： ",e.fillInStackTrace());
        }
    }


}
