import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author: zjh
 * @Date: 2021/6/3 11:11
 * @Version 1.0
 *
 * 提供者与消费者，阻塞队列版本
 * 优点：不需要再像之前的synchronized或者lock那样手动去暂停和唤醒线程,blockqueue自动做了这些事情
 */

class MyResource{

    private volatile boolean FLAG = true;//默认开启
    private AtomicInteger atomicInteger = new AtomicInteger();
    BlockingQueue<String> blockingQueue = null;

    public MyResource(BlockingQueue<String> blockingQueue) {
        this.blockingQueue = blockingQueue;
    }

    //生产者生产商品
    public void myProd(){

        String data = null;
        boolean returnValue;
        while (FLAG){
            try {
                data = atomicInteger.incrementAndGet()+"";
                returnValue = blockingQueue.offer(data,2l, TimeUnit.SECONDS);
                if (returnValue) {
                    System.out.println(Thread.currentThread().getName() + "\t生产商品 " + data + " 成功");
                }else {
                    System.out.println(Thread.currentThread().getName() + "\t生产商品 " + data + " 失败");
                }
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(Thread.currentThread().getName()+"结束生产!!");
    }

    //消费者消费商品
    public void myConsumer(){
        String data = null;
        while (FLAG){
            try {
                data = blockingQueue.poll(2l, TimeUnit.SECONDS);

                if (data == null || data.equals("")){
                    System.out.println(Thread.currentThread().getName()+"\t已超过2秒没有获取商品，消费结束");
                    FLAG = false;
                    return;
                }

                System.out.println(Thread.currentThread().getName()+"\t消费商品 "+data+" 成功");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(Thread.currentThread().getName()+"结束消费!!");
    }

    public void stopTheWorld(){
        FLAG = false;
    }

}

public class ProdConsumer_BlockQueue {

    public static void main(String[] args) {
        MyResource myResource = new MyResource(new ArrayBlockingQueue<>(10));

        new Thread(()->{
            System.out.println(Thread.currentThread().getName()+"\t生产者开始生产商品");
            myResource.myProd();
        },"生产者").start();

        new Thread(()->{
            try {
                TimeUnit.SECONDS.sleep(2);
                System.out.println(Thread.currentThread().getName()+"\t消费者等待2秒开始消费商品");
                myResource.myConsumer();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"消费者").start();

        try {
            TimeUnit.SECONDS.sleep(5);
            myResource.stopTheWorld();
            System.out.println(Thread.currentThread().getName()+"\t运行5秒叫停");
        } catch (InterruptedException e) { e.printStackTrace(); }

    }

}
