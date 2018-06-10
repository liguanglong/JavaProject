import org.aspectj.lang.reflect.Pointcut;
import org.aspectj.weaver.ast.Call;

/**
 * @author LiGuanglong
 * @date 2018/6/5
 */
public aspect MyAspect {

    pointcut recordLog():call(* HelloWorld.sayHello(..));

//    pointcut recordLog():call(* HelloWorld.sayHello(..));

    pointcut performanceMonitor():call(* HelloWorld.sayHello(..));

    before():recordLog(){
        System.out.println("print log");
    }

    after():performanceMonitor(){
        System.out.println("Performance Monitor");
    }


}
