package com.sya.rb;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

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
            channel.queueDeclare("new-rabbit",true,false,false,null);

            int count = 0;

            while(count < 5000){
                String message = "Message "+count;
                channel.basicPublish("","new-rabbit",null,message.getBytes());
                System.out.println("Publised "+message);
                count++;
                Thread.sleep(5000);
            }
        } catch (URISyntaxException | IOException | TimeoutException | InterruptedException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
    }
}
