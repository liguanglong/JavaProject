/**
 * @author LiGuanglong
 * @date 2018/7/5
 */
public class Main {
    public static void main(String[] args) throws InterruptedException {
        Business business = new Business(new WXResponse());
        business.createOrder();

        BusinessCake businessCake = new BusinessCake(new WXResponse());
        businessCake.createOrder();
    }
}
