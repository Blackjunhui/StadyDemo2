package jvm.error;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: zjh
 * @Date: 2021/6/29 17:36
 * @Version 1.0
 * GC处理垃圾回收占用了98%的内存，然而处理的垃圾仅有2%，事倍功半，GC直接罢工
 */
public class GCOverheadLimitExceedDemo {

    public static void main(String[] args) {

        int num = 0;
        List<String> list = new ArrayList<>();

        try {
            while (true){
                list.add(String.valueOf(++num).intern());
            }
        }catch (Throwable e){
            System.out.println("**********"+num);
            e.printStackTrace();
            throw e;
        }

    }


}
