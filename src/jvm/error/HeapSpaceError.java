package jvm.error;

import java.util.Random;

/**
 * @Author: zjh
 * @Date: 2021/6/29 17:31
 * @Version 1.0
 * 堆空间不足，对象太多了
 */
public class HeapSpaceError {

    public static void main(String[] args) {

        String str = "zhangjunhui";

        while (true){
            str += str + new Random().nextInt(1111111)+new Random().nextInt(222222222);
            str.intern();//Exception in thread "main" java.lang.OutOfMemoryError: Java heap space
        }

    }


}
