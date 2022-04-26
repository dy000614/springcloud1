package m1;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Producer {
    public static void main(String[] args) throws IOException, TimeoutException {
        // 连接
        ConnectionFactory f = new ConnectionFactory();
        f.setHost("192.168.64.140");
        f.setPort(5672); // 5672收发消息，  15672管理控制台
        f.setUsername("admin");
        f.setPassword("admin");
        Connection con = f.newConnection(); // 连接
        Channel c = con.createChannel();    // 通信通道
        // 在服务器上创建队列： helloworld
        // 队列已经存在不会重复创建
        c.queueDeclare("helloworld",false,false,false,null);
        // 发送消息
        c.basicPublish("", "helloworld", null, "Hello world!".getBytes());
        // 断开
        c.close();
        con.close();
    }
}