package jvm.re;

import java.lang.ref.SoftReference;

/**
 * @Author: zjh
 * @Date: 2021/6/29 11:08
 * @Version 1.0
 *
 * 软引用:当内存够用时，不回收对象，不够用才回收
 */
public class SoftReferenceDemo {

    //内存够用的情况下，弱引用不会被回收
    public static void member_enough(){
        Object obj1 = new Object();
        SoftReference<Object> softReference = new SoftReference<>(obj1);
        System.out.println(obj1);
        System.out.println(softReference.get());

        obj1 = null;
        System.gc();//手动触发gc
        System.out.println(obj1);
        System.out.println(softReference.get());
    }

    //内存不够用，弱引用会被垃圾回收
    public static void member_not_enough(){
        Object obj1 = new Object();
        SoftReference<Object> softReference = new SoftReference<>(obj1);
        System.out.println(obj1);
        System.out.println(softReference.get());

        obj1 = null;

        try {
            byte[] b = new byte[30 * 1024 * 1024];
        }catch (Throwable e){
            e.printStackTrace();
        }finally {
            System.out.println(obj1);
            System.out.println(softReference.get());
        }

    }

    public static void main(String[] args) {
        member_not_enough();
    }

}
