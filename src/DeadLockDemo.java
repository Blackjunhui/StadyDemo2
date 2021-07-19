import java.util.concurrent.TimeUnit;

/**
 * @Author: zjh
 * @Date: 2021/6/23 10:18
 * @Version 1.0
 * 死锁:指两个或两个以上的线程在执行的过程中，
 * 因争夺资源而互相等待的现象，
 * 若无外力干涉而无法推进下去
 */

class HoldLockDemo implements Runnable{
    private String lockA;
    private String lockB;

    public HoldLockDemo(String lockA, String lockB) {
        this.lockA = lockA;
        this.lockB = lockB;
    }

    @Override
    public void run() {
        synchronized (lockA){
            System.out.println(Thread.currentThread().getName()+"自己获取锁"+lockA+"尝试获取锁"+lockB);

            try {
                TimeUnit.SECONDS.sleep(2);
            }catch (InterruptedException e){
                e.printStackTrace();
            }

            synchronized (lockB){
                System.out.println(Thread.currentThread().getName()+"自己获取锁"+lockB+"尝试获取锁"+lockA);
            }
        }
    }
}

/**
 * java中提供类似linux查看线程的命令ps -ef|grep XXX 是 jps -l;
 * 并提供jstack 线程编号定位死锁信息
 */

public class DeadLockDemo {

    public static void main(String[] args) {
        String lockA = "lockA";
        String lockB = "lockB";

        new Thread(new HoldLockDemo(lockA,lockB),"AAA").start();

        new Thread(new HoldLockDemo(lockB,lockA),"BBB").start();
    }

}
