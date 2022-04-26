package m5;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.Scanner;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

public class Consumer {
    public static void main(String[] args) throws IOException, TimeoutException {
        //连接
        ConnectionFactory f = new ConnectionFactory();
        f.setHost("wht6.cn");  // wht6.cn
        f.setPort(5672);
        f.setUsername("admin");
        f.setPassword("admin");
        Channel c = f.newConnection().createChannel();

        //1.创建随机队列 2.创建交换机 3.使用关键词绑定
        String queue = UUID.randomUUID().toString();
        c.queueDeclare(queue, false, true, true, null);
        c.exchangeDeclare("topic_logs", BuiltinExchangeType.TOPIC);
        System.out.println("输入绑定键，用空格隔开：");
        String s = new Scanner(System.in).nextLine();// "aa  bb   cc"
        String[] a = s.split("\\s+");//   \s空白字符    +一到多个
        for (String k : a) {
            // 第三个参数是路由键关键词
            c.queueBind(queue, "topic_logs", k);
        }

        //回调对象
        DeliverCallback deliverCallback = (consumerTag, message) -> {
            String msg = new String(message.getBody());
            //消息中携带的关键词
            String key = message.getEnvelope().getRoutingKey();
            System.out.println(key + " - " + msg);
        };
        CancelCallback cancelCallback = consumerTag -> {};
        //接收消息
        c.basicConsume(queue, true, deliverCallback, cancelCallback);
    }
}