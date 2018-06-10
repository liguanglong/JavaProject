/**
 * @author LiGuanglong
 * @date 2018/6/10
 */
public class Main {

    public static void main(String[] args) {
        AddBook addBook = new Book();
        MyInvocationHandler target = new MyInvocationHandler(addBook);

        AddBook a = (AddBook) target.getProxy();

        a.add();
    }
}
