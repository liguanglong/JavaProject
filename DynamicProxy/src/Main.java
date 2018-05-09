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


        /**
         * 反编译代理类的字节码：
         * <pre>
         * m4 = Class.forName("MyService").getMethod("insert", new Class[0]);
         * m2 = Class.forName("java.lang.Object").getMethod("toString", new Class[0]);
         * m3 = Class.forName("MyService").getMethod("add", new Class[] { Integer.TYPE, Integer.TYPE });
         * </>
         * 生成的代理类MyServiceProxy实现了MyService接口，并且实现了add（int,int）和insert()方法
         * 实际调用的时候是用InvocationHandler(本例是target)，并且传入了m4和m3,其中m4和m3是通过*反射*拿到MyService接口中的
         * add(int,int)和insert()方法
         *
         */
        myServiceProxy.add(2, 2);

        //保存代理类的字节码
        ProxyUtils.saveProxyClass("D:\\git\\a.class", "MyServiceProxy", myService.getClass().getInterfaces());

    }
}
