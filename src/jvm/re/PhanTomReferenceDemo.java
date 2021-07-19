package jvm.re;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;

/**
 * @Author: zjh
 * @Date: 2021/6/29 16:33
 * @Version 1.0
 * 虚引用：get()方法获取的值总是为null，虚引用必须配合referenceQueue使用
 */
public class PhanTomReferenceDemo {

    public static void main(String[] args) {
        Object obj1 = new Object();
        ReferenceQueue<Object> referenceQueue = new ReferenceQueue<>();
        PhantomReference<Object> phantomReference = new PhantomReference<>(obj1,referenceQueue);
        System.out.println(obj1);
        System.out.println(phantomReference.get());
        System.out.println(referenceQueue.poll());

        obj1 = null;
        System.gc();

        System.out.println(obj1);
        System.out.println(phantomReference.get());
        System.out.println(referenceQueue.poll());
    }

}
