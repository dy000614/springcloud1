package m5;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

public class Producer {
    public static void main(String[] args) throws IOException, TimeoutException {
        //连接
        ConnectionFactory f = new ConnectionFactory();
        f.setHost("wht6.cn");  // wht6.cn
        f.setPort(5672);
        f.setUsername("admin");
        f.setPassword("admin");
        Channel c = f.newConnection().createChannel();

        //创建 Direct 类型交换机： topic_logs
        c.exchangeDeclare("topic_logs", BuiltinExchangeType.TOPIC);
        //向交换机发送消息，在消息上要添加关键词
        while (true) {
            System.out.print("输入消息：");
            String s = new Scanner(System.in).nextLine();
            System.out.print("输入路由键：");
            String k = new Scanner(System.in).nextLine();
            /*
            第二个参数： 路由键关键词
                       简单模式和工作模式中使用的默认交换机，默认交换机使用队列名来绑定队列
             */
            c.basicPublish("topic_logs", k, null, s.getBytes());
        }
    }
}