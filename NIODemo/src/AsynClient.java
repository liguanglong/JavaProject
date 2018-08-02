import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.concurrent.Future;

/**
 * 整个流程是：
 * 1、客户端发送请求
 * 2、服务端收到客户端请求，并建立连接（这时候也利用回调函数判断连接是否连接成功）
 * 3、客户端发送数据，此时传入回调函数（ClientChannelHandler），当写入成功后执行
 * 4、服务端读取数据，读取成功执行回调函数（ChannelHandler），并向客户端发送响应数据
 * 5、客户端收到服务端发送的响应数据，证明发送成功，也相当于写入buffer成功，执行执行回调函数（ClientChannelHandler）
 * 6、此时客户端可以继续向服务端发送数据，也可以选择关闭连接
 *
 * //大前提：所有数据操作都在socket中完成，ServerSocket只是起到监听端口的作用
 *
 * 异步IO-客户端
 * @author LiGuanglong
 * @date 2018/7/30
 */
public class AsynClient {

    public static void main(String[] args) throws Exception {
        AsynchronousSocketChannel client = AsynchronousSocketChannel.open();
        // 来个 Future 形式的
        Future<?> future = client.connect(new InetSocketAddress("localhost", 8080));
        // 阻塞一下，等待连接成功
        future.get();

        Attachment att = new Attachment();
        att.setClient(client);
        att.setReadMode(false);
        att.setBuffer(ByteBuffer.allocate(2048));
        byte[] data = "I am obot!".getBytes();
        att.getBuffer().put(data);
        att.getBuffer().flip();

        // 异步发送数据到服务端

        //后面回调函数的意义在于：如果数据发送到服务端，可以返回执行结果，这时客户端可以一直发送数据，不需要等待服务端的返回执行结果
        //也就是说服务端收到客户端发送的数据后，客户端希望服务端完成什么动作
        client.write(att.getBuffer(), att, new ClientChannelHandler());

        // 这里休息一下再退出，给出足够的时间处理数据
        Thread.sleep(2000);
    }
}
