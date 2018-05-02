/**
 * @author LiGuanglong
 * @date 2018/5/2
 */
public class HelloServiceImpl implements HelloService {

    @Override
    public String hello(String name) {
        return "Hello " + name;
    }
}
