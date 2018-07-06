/**
 * @author LiGuanglong
 * @date 2018/7/6
 */
public class BusinessCake implements PayResult{

    private WXResponse wxResponse;

    public BusinessCake(WXResponse wxResponse) {
        this.wxResponse = wxResponse;
    }


    @Override
    public void onPay(String content) {
        System.out.println("蛋糕店的支付状态");
        System.out.println(content);
    }

    public void createOrder() throws InterruptedException {
        System.out.println("蛋糕店创建订单，发起支付请求");
        wxResponse.orderHandle(BusinessCake.this, 4);

    }



}
