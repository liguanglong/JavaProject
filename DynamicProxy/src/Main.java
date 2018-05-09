import javax.sound.midi.MidiSystem;

/**
 * @author LiGuanglong
 * @date 2018/5/9
 */
public class Main {

    public static void main(String[] args) {

        //实例化目标对象
        MyService myService = new MyServiceImpl();

        myService.insert();
        System.out.println(myService.add(1, 1));

        System.out.println("---------------------------");

        //实例化InvocationHandler
        MyInvocationHandler target = new MyInvocationHandler(myService);

        /**
         * 根据目标对象生成代理对象
         * 注意：使用动态代理的时候,获取的对象是用接口引用的：
         * <pre>
         * MyService myServiceProxy = (MyService) target.getProxy();             RIGHT!!
         * MyServiceImpl myServiceProxy = (MyServiceImpl) target.getProxy();     WRONG!!
         * <pre/>
         * 字节码查看：Bytecode-Viewer  GitHub:https://github.com/Konloch/bytecode-viewer
         * 反编译生成的代理类的字节码，可以看到代理类继承自Proxy类，并实现了自定义接口（MyService）
         * 所以如果用实体类来引用返回的代理类的话肯定会报错(本例：MyServiceImpl和Proxy肯定不能相互转换啊)
         */
        MyService myServiceProxy = (MyService) target.getProxy();

        myServiceProxy.add(2, 2);

        ProxyUtils.saveProxyClass("D:\\git\\a.class", "myServiceProxy", myService.getClass().getInterfaces());

    }
}
