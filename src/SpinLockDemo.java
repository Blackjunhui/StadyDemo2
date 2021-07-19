import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @Author: zjh
 * @Date: 2021/5/31 16:22
 * @Version 1.0
 */

//手写自旋锁demo
    //自旋锁是CAS理论的底层
    //优点：不阻塞，缺点：当某一个线程占用锁的时间过长会消耗cpu性能
public class SpinLockDemo {

    AtomicReference<Thread> atomicReference = new AtomicReference<>(); //原子引用,引用类型默认值为null

    public void myLock(){
        Thread thread = Thread.currentThread();
        System.out.println(thread.getName()+"is coming in!!!!");

        while (!atomicReference.compareAndSet(null,thread)){
            //System.out.println(thread.getName()+"当前锁已被其他线程占用");
        }
    }

    public void myUnLock(){
        Thread thread = Thread.currentThread();
        atomicReference.compareAndSet(thread,null);
        System.out.println(thread.getName()+"invoke unLock()");
    }

    public static void main(String[] args) {

        SpinLockDemo spinLockDemo = new SpinLockDemo();

        new Thread(()->{
            spinLockDemo.myLock();
            try { TimeUnit.SECONDS.sleep(2); } catch (InterruptedException e) { e.printStackTrace(); }
            spinLockDemo.myUnLock();
        },"AA").start();

        new Thread(()->{
            spinLockDemo.myLock();
            //try { TimeUnit.SECONDS.sleep(1); } catch (InterruptedException e) { e.printStackTrace(); }
            spinLockDemo.myUnLock();
        },"BB").start();

    }

}
