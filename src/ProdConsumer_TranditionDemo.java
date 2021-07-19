import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author: zjh
 * @Date: 2021/6/2 16:32
 * @Version 1.0
 */

class ShareDate{

    private int number;
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    public void increment() {

        lock.lock();
        try {
            while (number != 0) { //多线程判断必须用while
                //不增加、不操作
                condition.await();
            }
            //干活
            number++;
            System.out.println(Thread.currentThread().getName() + "\t" + number);
            //唤醒线程
            condition.signalAll();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void decrement() {

        lock.lock();
        try {
            while (number == 0) {
                //不减少、不操作
                condition.await(); //线程挂起等待
            }
            //干活
            number--;
            System.out.println(Thread.currentThread().getName() + "\t" + number);
            //唤醒线程
            condition.signalAll();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }


}

//传统多线程操作通信消费者和提供者
public class ProdConsumer_TranditionDemo {

    public static void main(String[] args) {

        ShareDate shareDate = new ShareDate();

        for (int i = 1; i <= 5; i++) {
            new Thread(()->{
                shareDate.increment();
            },"AA").start();
        }

        for (int i = 1; i <= 5; i++) {
            new Thread(()->{
                shareDate.decrement();
            },"BB").start();
        }

        for (int i = 1; i <= 5; i++) {
            new Thread(()->{
                shareDate.increment();
            },"CC").start();
        }

        for (int i = 1; i <= 5; i++) {
            new Thread(()->{
                shareDate.decrement();
            },"DD").start();
        }

    }

}
