import net.sf.cglib.proxy.Enhancer;

/**
 * @author LiGuanglong
 * @date 2018/6/10
 */
public class Main {
    public static void main(String[] args) {

        /**
         * JDK代理要求被代理的类必须实现接口，有很强的局限性。而CGLIB动态代理则没有此类强制性要求,
         * 在这里我们需要注意一点：如果委托类被final修饰，那么它不可被继承，即不可被代理；同样，如果委托类中存在final修饰的方法，那么该方法也不可被代理；
         * CGLIB代理：会让生成的代理类继承被代理类，并在代理类中对代理方法进行强化处理(前置处理、后置处理等)
         * JDK代理：代理类继承自Proxy类，并实现了自定义接口（如：MyService），代理类和被代理类虽然都实现了MyService接口，但是
         *          代理类和被代理类不能相互转换，所以JDK代理只能代理接口，不能代理类
         * <pre>
         *     MyServiceImpl是MyService接口的实现类
         *     CGLIB代理强制装换:
         *     MyServiceImpl myServiceImpl = (MyServiceImpl) enhancer.create();
         *     JDK代理强制转换:
         *     MyService myServiceProxy = (MyService) target.getProxy();
         * </pre>
         * 二者的代理设计方式不同，有一定的区别
         */


        //代理类，CGLIB的字节码增强器，对类进行扩展
        Enhancer enhancer = new Enhancer();
        //继承被代理类
        enhancer.setSuperclass(MyServiceImpl.class);
        //设置回调
        //设置回调，这里相当于是对于代理类上所有方法的调用，都会调用CallBack，而Callback则需要实行intercept()方法进行拦截
        enhancer.setCallback(new MyMethodInterceptor());
        //生成被代理类对象
        MyServiceImpl myServiceImpl = (MyServiceImpl) enhancer.create();
        //执行代理方法，调用代理类对象的方法会被方法拦截器拦截
        myServiceImpl.sayHello();

        /**
         * 使用HSDB(HotSpot Debugger)查看字节码
         * https://blog.csdn.net/op_violet/article/details/79106457
         * 生成的class文件在C://user//liguanglong// 下面，或是直接搜索类名也可以找到
         */

        System.out.println(123);
    }
}
