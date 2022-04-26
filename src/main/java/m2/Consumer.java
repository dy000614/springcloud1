package m2;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Consumer {
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

        //创建回调对象
        //模拟耗时消息，遍历字符串，每遇到一个'.'字符暂停1秒
        DeliverCallback deliverCallback = (consumerTag, message) -> {
            String s = new String(message.getBody());
            System.out.println("收到： "+s);
            for (int i = 0; i < s.length(); i++) {
                if (s.charAt(i) == '.') {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                    }
                }
            }
            System.out.println("-------------------------------消息处理完成");
        };
        CancelCallback cancelCallback = consumerTag -> {};

        //接收消息
        c.basicConsume("helloworld", true, deliverCallback, cancelCallback);
    }
}