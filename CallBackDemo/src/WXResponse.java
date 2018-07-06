/**
 * 微信服务器
 * @author LiGuanglong
 * @date 2018/7/6
 */
public class WXResponse {

    /**
     * 处理订单支付请求
     * 注意该方法的参数是回调接口，这也是能回调的原因!!!!!!!!!!!!!!
     * @param payResult
     * @throws InterruptedException
     */
    public void orderHandle(PayResult payResult,int orderId) throws InterruptedException {
        //模拟支付过程，支付成功，耗时两秒
        Thread.sleep(2000);
        String msg = MsgEnum.getMsgContentFromOrderId(orderId);

        //等待两秒主动执行上层传进来的回调函数
        payResult.onPay(msg);
    }
}
