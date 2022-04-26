package m3;

import com.rabbitmq.client.*;

import java.io.IOException;
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

        //1.创建队列 2.创建交换机 3.绑定
        String queue = UUID.randomUUID().toString();
        c.queueDeclare(queue, false, true, true, null);
        c.exchangeDeclare("logs", BuiltinExchangeType.FANOUT);
        // 第三个参数对 fanout 交换机无效
        c.queueBind(queue, "logs", "");

        //回调对象
        DeliverCallback deliverCallback = (consumerTag, message) -> {
            String s = new String(message.getBody());
            System.out.println("收到： "+s);
        };
        CancelCallback cancelCallback = consumerTag -> {};
        //接收消息
        c.basicConsume(queue, true, deliverCallback, cancelCallback);


    }
}