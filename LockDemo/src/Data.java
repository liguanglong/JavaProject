import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author LiGuanglong
 * @date 2018/7/13
 */
public class Data {

    private final char[] buffer;


    // 创建读写锁
    private final ReadWriteLock lock = new ReentrantReadWriteLock();

    // 获取读锁
    private final Lock readLock = lock.readLock();

    // 获取写锁
    private final Lock writeLock = lock.writeLock();

    public Data(int size) {
        this.buffer = new char[size];
        for (int i = 0; i < size; i++) {
            buffer[i] = '*';
        }
    }

//    public synchronized  String read() {
//        StringBuilder result = new StringBuilder();
//        for (char c : buffer) {
//            result.append(c);
//        }
//        sleep(100);
//        System.out.println("read sleep");
//        return result.toString();
//    }




    public String read() {
        readLock.lock();
        try {
            StringBuilder result = new StringBuilder();
            for (char c : buffer) {
                result.append(c);
            }
            System.out.println(Thread.currentThread().getName() + " => " +"append complete:" + result);
            sleep(100);
            System.out.println(Thread.currentThread().getName() + " => "+ "read sleep");
            return result.toString();
        }finally {
            readLock.unlock();
        }

    }



//    public synchronized  void write(char c) {
//        for (int i = 0; i < buffer.length; i++) {
//            buffer[i] = c;
//            sleep(100);
//            System.out.println("write sleep");
//        }
//    }


    public void write(char c) {
        writeLock.lock();
        try {
            for (int i = 0; i < buffer.length; i++) {
                buffer[i] = c;
                sleep(100);
                System.out.println("write sleep");
            }
        }finally {
            writeLock.unlock();
        }

    }

    private void sleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
