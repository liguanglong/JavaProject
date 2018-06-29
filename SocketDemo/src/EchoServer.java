import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author LiGuanglong
 * @date 2018/6/29
 */

/**
 * Socket 是 TCP 层的封装，通过 socket，我们就能进行 TCP 通信。
 * 在 Java 的 SDK 中，socket 的共有两个接口：用于监听客户连接的 ServerSocket 和用于通信的 Socket。使用 socket 的步骤如下：
 * 1、创建 ServerSocket 并监听客户连接
 * 2、使用 Socket 连接服务端
 * 3、通过 Socket 获取输入输出流进行通信
 *  echo 服务，就是客户端向服务端写入任意数据，服务器都将数据原封不动地写回给客户端
 *  服务器端
 */
public class EchoServer {

    //ServerSocket用于监听客户连接（端口）
    private final ServerSocket serverSocket;

    public EchoServer(int port) throws IOException{
        // 1. 创建一个 ServerSocket 并监听端口 port
        serverSocket = new ServerSocket(port);
    }

    public void run() throws IOException {
        // 2. 开始接受客户连接
        Socket socket = serverSocket.accept();
        handleSocket(socket);
    }

    public void handleSocket(Socket socket) throws IOException {
        // 3. 使用 socket的输入输出流 进行通信
        InputStream inputStream = socket.getInputStream();
        OutputStream outputStream = socket.getOutputStream();
        byte[] data = new byte[1024];
        int n;
        while((n = inputStream.read(data))>0) {
            //返回socket读取的数据到客户端
            outputStream.write(data, 0, n);
            //打印socket读取的数据作验证
            System.out.println(new String(data));
        }
    }

    public static void main(String[] args) throws IOException {
        //监听6000端口
        EchoServer server = new EchoServer(6000);
        server.run();
    }
}
