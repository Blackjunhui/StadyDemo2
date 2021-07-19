import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * @Author: zjh
 * @Date: 2021/6/1 17:10
 * @Version 1.0
 */

//指示灯，作用：给定一个指定的数量，多个线程抢占这个数量，秒杀机制
public class SemaphoreDemo {

    //模拟抢车位
    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(10);

        for (int i = 1; i <= 20; i++) {
            new Thread(()->{
                try {
                    semaphore.acquire(); //抢占车位
                    System.out.println(Thread.currentThread().getName()+"\t 抢到车位");
                    TimeUnit.SECONDS.sleep(3);
                    System.out.println(Thread.currentThread().getName()+"\t 停了3秒，离开车位");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    semaphore.release();//释放车位
                }
            },String.valueOf(i)).start();
        }

    }

}
