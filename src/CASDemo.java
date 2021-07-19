import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author: zjh
 * @Date: 2021/5/25 11:44
 * @Version 1.0
 */
public class CASDemo {

    //CAS(compareAndSet:比较并交换),CAS理论引发ABA问题，自旋锁
    public static void main(String[] args) {
        AtomicInteger atomicInteger = new AtomicInteger(5);  //初始化值为5，不填则为0
        System.out.println(atomicInteger.compareAndSet(5,1080)+",\t now data:"+atomicInteger.get());
        System.out.println(atomicInteger.compareAndSet(5,1020)+",\t now data:"+atomicInteger.get());
    }

}
