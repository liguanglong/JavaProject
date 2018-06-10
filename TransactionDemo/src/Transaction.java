import java.lang.annotation.*;

/**
 * @author LiGuanglong
 * @date 2018/6/10
 */


/**
 * 自定义注解，模拟spring的Transaction
 */


@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
//如果一个使用了@Inherited修饰的annotation类型被用于一个class，则这个annotation将被用于该class的子类
@Inherited
public @interface Transaction {
}
