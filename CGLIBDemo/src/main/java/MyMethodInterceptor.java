import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author LiGuanglong
 * @date 2018/6/10
 */
public class MyMethodInterceptor implements MethodInterceptor{

    //1、代理对象；2、委托类方法；3、方法参数；4、代理方法的MethodProxy对象。
    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("intercept");
        return methodProxy.invokeSuper(o, objects);
    }
}
