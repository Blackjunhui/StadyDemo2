import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @Author: zjh
 * @Date: 2021/6/1 10:26
 * @Version 1.0
 */

//火箭发射计数器，当指定的数字到达0时才执行之后的线程操作
public class CountDownLatchDemo {

    static int count = 6;

    static volatile AtomicReference<String> atomicReference = new AtomicReference<>("韩");

    public static void mylock(){
        Thread thread = Thread.currentThread();

        while (!thread.getName().equals(atomicReference.get())){
            //System.out.println("正在灭掉"+thread.getName()+"国...");
        }
    }

    public static void myUnlock(int index){
        Thread thread = Thread.currentThread();
        atomicReference.compareAndSet(thread.getName(),CountryEnum.for_eachEnum(index).getNext());
        System.out.println(thread.getName()+"\t 国被灭!");
    }

    //模拟秦灭六国统一华夏
    public static void main(String[] args) throws InterruptedException {

        CountDownLatch countDownLatch = new CountDownLatch(count);

        for (int i = 1; i <= 6; i++) {
            final int tempI = i;
            new Thread(()->{
                CountDownLatchDemo.mylock();
                countDownLatch.countDown();
                CountDownLatchDemo.myUnlock(tempI);
            },CountryEnum.for_eachEnum(i).getValue()).start();
        }



        countDownLatch.await();
        if(atomicReference.get().equals("")){
            System.out.println(Thread.currentThread().getName()+"\t 秦统一华夏!!");
        }

    }

    public void rocketGo() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(count);

        for (int i = 1; i <= 6; i++) {
            new Thread(()->{
                System.out.println(Thread.currentThread().getName()+"\t 调试完毕!");
                countDownLatch.countDown();
            },String.valueOf(i)).start();
        }

        countDownLatch.await();
        System.out.println(Thread.currentThread().getName()+"\t 所有功能已调试完毕，火箭发射!!");
    }

}
