import java.lang.invoke.MethodHandle;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author LiGuanglong
 * @date 2018/5/9
 */
public class MyInvocationHandler implements InvocationHandler{

    /**
     * 目标对象
     */
    private Object target;


    /**
     * 构造函数
     * @param target
     */
    public MyInvocationHandler(Object target) {
        super();
        this.target = target;
    }

    /**
     * 获取目标对象的代理对象
     * @return 代理对象
     */
    public Object getProxy(){
        return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),target.getClass().getInterfaces(),this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("Before");

        //反射
        //restlt是执行目标方法后，目标方法返回的值
        Object result = method.invoke(target,args);
        System.out.println(result);

        System.out.println("After");

        return result;
    }
}
