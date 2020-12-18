package com.sya.rb;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeoutException;

public class Publisher
{
    public static void main(String[] args)
    {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        try {
            connectionFactory.setUri("amqp:guest/guest@localhost");
            connectionFactory.setConnectionTimeout(3000);
            Connection connection = connectionFactory.newConnection();
            Channel channel = connection.createChannel();
            //marking queue as durable saves the messages in case of rabbitMq crash
            channel.queueDeclare("new-rabbit",true,false,false,null);

            int count = 0;

            while(count < 5000){
                String message = "Message "+count;
                channel.basicPublish("","new-rabbit", MessageProperties.PERSISTENT_TEXT_PLAIN,message.getBytes());
                System.out.println("Publised "+message);
                count++;
                //Thread.sleep(5000);
            }
        } catch (URISyntaxException | IOException | TimeoutException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
    }
}
