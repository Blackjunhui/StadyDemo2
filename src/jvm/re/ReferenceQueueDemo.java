package jvm.re;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.concurrent.TimeUnit;

/**
 * @Author: zjh
 * @Date: 2021/6/29 15:51
 * @Version 1.0
 * 引用队列：当引用即将被回收的时候，会放入引用队列中
 */
public class ReferenceQueueDemo {

    public static void main(String[] args) throws InterruptedException{
        Object obj1 = new Object();
        ReferenceQueue<Object> referenceQueue = new ReferenceQueue<>();
        SoftReference<Object> weakReference = new SoftReference<>(obj1,referenceQueue);
        System.out.println(obj1);
        System.out.println(weakReference.get());
        System.out.println(referenceQueue.poll());

        System.out.println();
        System.out.println();

        obj1 = null;


        try {
            byte[] b = new byte[30 * 1024 * 1024];
        }finally {
            TimeUnit.MILLISECONDS.sleep(500);
            System.out.println(obj1);
            System.out.println(weakReference.get());
            System.out.println(referenceQueue.poll());
        }





    }

}
