package jvm.re;

import java.lang.ref.WeakReference;

/**
 * @Author: zjh
 * @Date: 2021/6/29 11:35
 * @Version 1.0
 * 弱引用：不管内存够不够都会回收
 */
public class WeakReferenceDemo {

    public static void main(String[] args) {

        Object obj1 = new Object();
        WeakReference<Object> weakReference = new WeakReference<>(obj1);
        System.out.println(obj1);
        System.out.println(weakReference.get());

        obj1 = null;
        System.gc();
        System.out.println("************");
        System.out.println(obj1);
        System.out.println(weakReference.get());

    }

}
