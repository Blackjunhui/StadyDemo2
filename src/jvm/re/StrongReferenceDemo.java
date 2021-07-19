package jvm.re;

/**
 * @Author: zjh
 * @Date: 2021/6/29 10:56
 * @Version 1.0
 * 强引用
 */
public class StrongReferenceDemo {

    public static void main(String[] args) {

        Object obj1 = new Object();
        Object obj2 = obj1;
        obj1 = null;
        System.gc();
        System.out.println(obj2);

    }

}
