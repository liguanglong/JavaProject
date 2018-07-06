import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 回调机制:一般的做法是将回调方法做成一个接口，不同的发送者(Business,BusinessCake...)去实现该接口(PayResult)，
 * 并且把自己的接口实现类的对象(Business.this)在发送消息时传递给消息处理者(WXResponse)
 *
 * @author LiGuanglong
 * @date 2018/7/5
 */
public class Business implements PayResult{

    //调用回调函数的对象
    private WXResponse wxResponse;


    public Business(WXResponse wxResponse) {
        this.wxResponse = wxResponse;
    }

    //自定义回调函数功能
    @Override
    public void onPay(String content) {
        System.out.println("接受支付状态通知:");
        //回调:例如待微信支付完成之后，可以将支付信息输出到自己指定的服务器上
        System.out.println(content);
    }


    public  void createOrder() {
        /**
         * 创建多线程进行异步回调
         */

        //线程工厂，管理线程编号、名字、状态
        MyThreadFactory myThreadFactory = new MyThreadFactory("ThreadFactory");

        //线程池
        ExecutorService threadPool = new ThreadPoolExecutor(3, 5, 0, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(1024),
                myThreadFactory, new ThreadPoolExecutor.AbortPolicy());


        threadPool.execute(() -> {
            System.out.println("商家创建订单，并构造支付请求1");
            try {
                //请求微信服务器
                wxResponse.orderHandle(Business.this,1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        threadPool.execute(() -> {
            System.out.println("商家创建订单，并构造支付请求2");
            try {
                wxResponse.orderHandle(Business.this,3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        //一定要关闭线程池
        threadPool.shutdown();
    }

}
