package m1;

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
        c.queueDeclare("helloworld",false,false,false,null);

        // 创建回调对象
        // ctrl+空格， 代码辅助， 被切换输入法占用
        // 可以修改这个快捷键，改成 eclipse 的提示快捷键 alt+/
        // https://wanght.blog.csdn.net/article/details/106247855
        DeliverCallback deliverCallback = (consumerTag, message) -> { //处理消息
            byte[] a = message.getBody();
            String s = new String(a);
            System.out.println("收到： "+s);
        };
        CancelCallback cancelCallback = consumerTag -> {}; //取消接收消息时执行

        // 从 helloworld 队列接收消息，收到的消息会传递到回调对象进行处理
        c.basicConsume("helloworld", true, deliverCallback, cancelCallback);
    }
}