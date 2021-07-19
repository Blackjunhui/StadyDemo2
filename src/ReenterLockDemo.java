import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author: zjh
 * @Date: 2021/5/31 10:05
 * @Version 1.0
 */

class Phone implements Runnable{
    public synchronized void sendSms(){
        System.out.println(Thread.currentThread().getName()+"\t invoke sendSms()");
        sendEmail();
    }
    public synchronized void sendEmail(){
        System.out.println(Thread.currentThread().getName()+"\t *********invoke sendEmail()");
    }

    @Override
    public void run() {
        get();
    }

    Lock lock = new ReentrantLock();

    public void get(){
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName()+"\t invoke get()");
            set();
        }finally {
            lock.unlock();
        }

    }

    public void set(){
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName()+"\t *********invoke set()");
        }finally {
            lock.unlock();
        }
    }

}

//ReentrantLock(默认情况下)和synchronize都是非公平的可重入锁（递归锁）

/*什么是可重入锁?同一线程外层函数获得锁后，内层递归函数仍然能获得该锁的代码
*在同一个线程外层方法获取锁的时候，在进入内层方法会自动获取锁
 */

//case:可重入锁就好比自家大门上锁，只要挟持钥匙的人打开锁，那么他就可以任意的进入卧室、厨房、卫生间等带有锁的门

//可重入锁的作用是：防止死锁
public class ReenterLockDemo {

    public static void main(String[] args) throws InterruptedException {
        Phone phone = new Phone();

        //线程操作资源类
        //验证synchronized的可重入锁
        new Thread(()->{
            phone.sendSms();
        },"t1").start();

        new Thread(()->{
            phone.sendSms();
        },"t2").start();

        TimeUnit.SECONDS.sleep(1);

        System.out.println();
        System.out.println();

        //验证ReentrantLock为可重入锁
        //PS:ReentrantLock可以加多个锁但是加多少lock(),就要解多少unlock(),否则会出现线程卡死，即死锁。
        Thread t3 = new Thread(phone,"t3");
        Thread t4 = new Thread(phone,"t4");

        t3.start();
        t4.start();

    }

}
