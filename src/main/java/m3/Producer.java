package m3;

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

        //创建 fanout 类型交换机： logs
        // c.exchangeDeclare("logs", "fanout");
        c.exchangeDeclare("logs", BuiltinExchangeType.FANOUT);

        //向 logs 交换机发送消息
        while (true) {
            System.out.print("输入消息：");
            String s = new Scanner(System.in).nextLine();
            // 第二个参数对 fanout 交换机无效
            c.basicPublish("logs", "", null, s.getBytes());
        }

    }
}