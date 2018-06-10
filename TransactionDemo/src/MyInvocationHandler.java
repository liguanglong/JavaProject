import org.omg.CORBA.OBJ_ADAPTER;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;

/**
 * @author LiGuanglong
 * @date 2018/6/10
 */
public class MyInvocationHandler implements InvocationHandler {

    private Object target;

    public MyInvocationHandler(Object target) {
        this.target = target;
    }

    public Object getProxy() {
        return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), target.getClass().getInterfaces(), this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("invoke");
        Object result = null;

        //获取方法上的注解
        Annotation annotation = target.getClass().getDeclaredMethod(method.getName(), method.getParameterTypes()).getAnnotation(Transaction.class);

        if (annotation == null) {
            result = method.invoke(target, args);
        } else {
            System.out.println("Transaction start");
            result = method.invoke(target, args);
            System.out.println("Transaction end");
        }
        return result;
    }
}
