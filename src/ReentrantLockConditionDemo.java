import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author: zjh
 * @Date: 2021/6/2 17:46
 * @Version 1.0
 *
 * 释义lock中condition的用法和lock的优势：可以精准挂载/唤醒线程
 *
 * 题目：a打印5次，然后b打印10次,然后c打印15次。。。
 * ....
 * 循环10轮
 */

class ShareResource{

    private int number = 1; //a =1,b =2, c=3
    private Lock lock = new ReentrantLock();  //lock具有精准释放线程的功能，隧不使用synchronized
    //颁发锁（即限制条件）
    private Condition a = lock.newCondition();
    private Condition b = lock.newCondition();
    private Condition c = lock.newCondition();

    public void printA(){
        lock.lock();
        try {
            //判断条件
            while (number !=1){
                a.await();
            }
            //执行5次
            for (int i = 1; i <= 5; i++) {
                System.out.println(Thread.currentThread().getName()+"\t"+i);
            }
            //唤醒b线程
            number = 2;
            b.signal();

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

    public void printB(){
        lock.lock();
        try {
            //判断条件
            while (number !=2){
                b.await();
            }
            //执行10次
            for (int i = 1; i <= 10; i++) {
                System.out.println(Thread.currentThread().getName()+"\t"+i);
            }
            //唤醒c线程
            number = 3;
            c.signal();

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

    public void printC(){
        lock.lock();
        try {
            //判断条件
            while (number !=3){
                c.await();
            }
            //执行15次
            for (int i = 1; i <= 15; i++) {
                System.out.println(Thread.currentThread().getName()+"\t"+i);
            }
            //唤醒a线程
            number = 1;
            a.signal();

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }


}

public class ReentrantLockConditionDemo {

    public static void main(String[] args) {
        ShareResource shareResource = new ShareResource();

        for (int i = 1; i <= 10; i++) {
            new Thread(()->{
                shareResource.printA();
            },"AA").start();
        }

        for (int i = 1; i <= 10; i++) {
            new Thread(()->{
                shareResource.printB();
            },"BB").start();
        }

        for (int i = 1; i <= 10; i++) {
            new Thread(()->{
                shareResource.printC();
            },"CC").start();
        }

    }

}
