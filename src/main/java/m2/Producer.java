package m2;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

public class Producer {
    public static void main(String[] args) throws IOException, TimeoutException {
        // 连接
        ConnectionFactory f = new ConnectionFactory();
        f.setHost("192.168.64.140");
        f.setPort(5672);
        f.setUsername("admin");
        f.setPassword("admin");
        Connection con = f.newConnection();
        Channel c = con.createChannel();

        // 在服务器上创建队列： helloworld
        c.queueDeclare("helloworld", false, false, false, null);

        //循环在控制台输入消息发送
        while (true) {
            System.out.print("输入消息： ");
            String s = new Scanner(System.in).nextLine();
            c.basicPublish("", "helloworld", null, s.getBytes());
        }
    }
}