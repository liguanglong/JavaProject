/**
 * @author LiGuanglong
 * @date 2018/7/13
 */
public class ReadThread  extends  Thread{

    private final Data data;

    public ReadThread(Data data) {
        this.data = data;
    }

    @Override
    public void run() {
        while (true) {
            long begin = System.currentTimeMillis();
//            for (int i = 0; i < 10; i++) {
                String result = data.read();
                System.out.println(Thread.currentThread().getName() + " => " + result + "read");
//            }
            long time = System.currentTimeMillis() - begin;
//            System.out.println(Thread.currentThread().getName() + " -- " + time + "ms");
        }
    }
}
