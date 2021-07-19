import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * @Author: zjh
 * @Date: 2021/5/26 18:15
 * @Version 1.0
 */

//ABA问题产生与解决
public class AtomicStampedRefrenceDemo {

    static AtomicReference<Integer> atomicReference = new AtomicReference<>(100);
    static AtomicStampedReference<Integer> atomicStampedReference = new AtomicStampedReference<>(100,1);

    public static void main(String[] args) {

        System.out.println("=============ABA问题产生================");
        new Thread(()->{
            atomicReference.compareAndSet(100,101);
            atomicReference.compareAndSet(101,100);
        },"t1").start();

        new Thread(()->{
            try { TimeUnit.SECONDS.sleep(1); }catch (InterruptedException e){ e.printStackTrace(); }//延时一秒，让t1线程狸猫换太子
            System.out.println(atomicReference.compareAndSet(100,2019)+"\t当前的值为："+atomicReference.get());
            atomicReference.compareAndSet(100,2019);
        },"t2").start();

        try { TimeUnit.SECONDS.sleep(1); }catch (InterruptedException e){ e.printStackTrace(); }
        System.out.println("=============ABA问题解决================");
        new Thread(()->{
            int stamp = atomicStampedReference.getStamp();
            System.out.println("t3线程当前版本号为："+stamp);
            try { TimeUnit.SECONDS.sleep(1); }catch (InterruptedException e){ e.printStackTrace(); }
            atomicStampedReference.compareAndSet(100,101,atomicStampedReference.getStamp(),atomicStampedReference.getStamp()+1);
            System.out.println("当前版本号为："+atomicStampedReference.getStamp());
            atomicStampedReference.compareAndSet(101,100,atomicStampedReference.getStamp(),atomicStampedReference.getStamp()+1);
            System.out.println("当前版本号为："+atomicStampedReference.getStamp());
            System.out.println(atomicStampedReference.getReference());
        },"t3").start();

        new Thread(()->{
            int stamp = atomicStampedReference.getStamp();
            System.out.println("t4线程当前版本号为："+stamp);
            try { TimeUnit.SECONDS.sleep(3); }catch (InterruptedException e){ e.printStackTrace(); }//延时一秒，让t1线程狸猫换太子
            System.out.println(atomicStampedReference.compareAndSet(100,2019,stamp,stamp+1)+"\t当前的值为："+atomicStampedReference.getReference());
            System.out.println("当前版本号为："+atomicStampedReference.getStamp());
        },"t4").start();
    }

}
