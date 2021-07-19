import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author: zjh
 * @Date: 2021/5/20 10:53
 * @Version 1.0
 */

class Mydata{
    volatile int number = 0;

    public void addTO60(){
        this.number = 60;
    }

    public void addPlusPlus(){
        number++;
    }

    AtomicInteger atomicInteger = new AtomicInteger();

    public void addMyAtomic(){
        atomicInteger.getAndIncrement();
    }

}


public class ValatileDemo {//测试原子不可见行：原子性及数据完整性，当线程在对某一数据操作时，其他线程不可对他加塞
    public static void main(String[] args) {
        Mydata mydata  = new Mydata();

        //创建20个线程，每个各执行1000次，总计20000次、
        for (int i = 1;i<=20;i++){
            new Thread(()->{
                for (int j = 1;j<=1000;j++){
                    mydata.addPlusPlus();
                    mydata.addMyAtomic();
                }
            },"线程"+i).start();
        }

        //当20个子线程没结束时，不可执行主线程
        while (Thread.activeCount()>2){
            Thread.yield();
        }

        System.out.println(Thread.currentThread().getName()+"主线程输出结果: "+mydata.number); //结果num不一定等于20000,验证不保证原子性成功
        System.out.println(Thread.currentThread().getName()+"主线程输出结果: "+mydata.atomicInteger); //结果原子性AtomicInteger等于20000,验证jmm原子性成功

    }


    //测试volatile可见性
    public static void testVolatile(){
        Mydata mydata = new Mydata();
        System.out.println(Thread.currentThread().getName()+",is come in,value: "+mydata.number);
        new Thread(()->{
            System.out.println(Thread.currentThread().getName()+",is come in,value: "+mydata.number);
            try {
                //等3秒
                TimeUnit.SECONDS.sleep(3);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            mydata.addTO60();
            System.out.println(Thread.currentThread().getName()+",update number to "+mydata.number);
        },"AAA").start();

        while (mydata.number == 0){
            //如果当前数据没有检测到更改，则循环
        }

        System.out.println(Thread.currentThread().getName()+" is over,value:"+mydata.number);
    }
}