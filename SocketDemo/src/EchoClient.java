import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.concurrent.*;

/**
 * @author LiGuanglong
 * @date 2018/6/29
 */

/**
 * 连接客户端
 */
public class EchoClient {

    //Socket用来通信，通过 Socket 获取输入输出流进行通信
    private final Socket socket;

    public EchoClient(String host, int port) throws IOException {
        socket = new Socket(host, port);
    }

    public void run() throws IOException {

        //创建一个线程来监听EchoSever的响应，下面使用了线程池和单独创建线程两种方式

        //线程池
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(2, 5, 500, TimeUnit.MINUTES, new
                ArrayBlockingQueue<Runnable>(1));
        //Lambda表达式
        threadPoolExecutor.execute(()-> {
            try {
                response();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        //正常语法
//        threadPoolExecutor.execute(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    response();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        });


        //单独创建线程
//        Thread thread = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    response();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//
//        thread.start();

        //用于读取用户输入，写入socket
        OutputStream outputStream = socket.getOutputStream();
        byte[] data = new byte[1024];
        int n;
        while ((n = System.in.read(data)) > 0) {
            outputStream.write(data, 0, n);
        }
    }

    /**
     * 读取服务器的响应
     *
     * @throws IOException
     */
    private void response() throws IOException {
        InputStream inputStream = socket.getInputStream();
        byte[] data = new byte[1024];
        while (inputStream.read(data) > 0) {
            //用于展示EchoSever返回的数据
            System.out.println(new String(data));
        }
    }

    public static void main(String[] args) throws IOException {
        //由于在本地运行，所以host写的是localhost
        //由于EchoSever监听的是6000端口，所以这里的连接socket端口号也是6000
        EchoClient client = new EchoClient("localhost", 6000);
        client.run();
    }
}
