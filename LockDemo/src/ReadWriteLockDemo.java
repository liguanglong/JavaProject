import com.sun.corba.se.pept.transport.ReaderThread;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/** 读写锁
 * 每次只能有一个写线程，但是同时可以有多个线程并发地读数据
 * ReadWriteLock适用于读多写少的并发情况
 * @author LiGuanglong
 * @date 2018/7/13
 */
public class ReadWriteLockDemo  {

    public static void main(String[] args) {
        Data data = new Data(10);

        new ReadThread(data).start();
        new ReadThread(data).start();
        new ReadThread(data).start();
        new ReadThread(data).start();
        new ReadThread(data).start();

        new WriteThread(data, "ABCDEFGHI").start();
        new WriteThread(data, "012345789").start();

//        new WriteThread(data, "012").start();
    }



}
