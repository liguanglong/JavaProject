import javax.xml.ws.soap.MTOM;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author LiGuanglong
 * @date 2018/5/9
 */

public class MethodTest {

    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, ClassNotFoundException, InstantiationException {

        /**
         *  反射
         */
        //获取OriginMethod类名
        Class<?> cls = Class.forName("OriginMethod");
        //实例化cls,为cls创建对象，分配空间
        Object o = cls.newInstance();
        //获取cls中的指定方法
        Method method = cls.getMethod("sayHello");
        //调用上面获得的方法
        method.invoke(cls,null);
    }
}
