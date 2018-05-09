/**
 * @author LiGuanglong
 * @date 2018/5/9
 */
public class MyServiceImpl implements MyService{

    @Override
    public void insert() {
        System.out.println("Current");
    }

    @Override
    public int add(int a, int b) {
        return a+b;
    }
}
