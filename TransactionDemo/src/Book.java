/**
 * @author LiGuanglong
 * @date 2018/6/10
 */
public class Book implements AddBook {
    @Override
    @Transaction
    public void query() {
        System.out.println("查询是否已经存在");
    }

    @Override
//    @Transaction
    public void add() {
        query();
        System.out.println("查询不存在，则新增");
    }
}
