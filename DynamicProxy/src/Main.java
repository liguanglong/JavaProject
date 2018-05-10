import javax.sound.midi.MidiSystem;
import java.lang.reflect.InvocationHandler;

/**
 * @author LiGuanglong
 * @date 2018/5/9
 */
public class Main {

    /**
     * 动态代理五大步骤：
     * 1通过实现 InvocationHandler 接口创建自己的调用处理器；
     * 2通过为 Proxy 类指定 ClassLoader 对象和一组 interface 来创建动态代理类；
     * 3通过反射机制获得动态代理类的构造函数，其唯一参数类型是调用处理器接口类型；
     * 4通过构造函数创建动态代理类实例，构造时调用处理器对象作为参数被传入。
     * 5通过代理对象调用目标方法
     */

    public static void main(String[] args) {

        //实例化目标对象
        MyService myService = new MyServiceImpl();

        myService.insert();
        System.out.println(myService.add(1, 1));

        System.out.println("---------------------------");

        /**
         * 实例化InvocationHandler调用处理器
         * 每个代理类都有一个与之关联的InvocationHandler,当在代理实例上调用方法时(如：myServiceProxy.insert())，
         * 方法调用（insert()）将被编码并分派给InvocationHandler的invoke方法。
         * InvocationHandler中的invoke方法来执行通过反射获得的insert()方法
         * InvocationHandler可以实现方法调用从代理类到委托类的分派转发
         */
        MyInvocationHandler target = new MyInvocationHandler(myService);

        /**
         * 根据目标对象生成代理对象$Proxy0
         * target.getProxy();返回的是$Proxy0，然后强制转化为MyService
         * 代理类是final和public的，既所有类都可以访问，但不能继承
         * 注意：动态代理类只能代理interface，不能代理Class
         * 使用动态代理的时候,获取的对象是用接口引用的：
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
        myServiceProxy.insert();

        //保存代理类的字节码
        ProxyUtils.saveProxyClass("D:\\git\\a.class", "MyServiceProxy", myService.getClass().getInterfaces());
    }
}
