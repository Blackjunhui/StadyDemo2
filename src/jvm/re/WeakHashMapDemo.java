package jvm.re;

import java.util.HashMap;
import java.util.WeakHashMap;

/**
 * @Author: zjh
 * @Date: 2021/6/29 15:34
 * @Version 1.0
 * 弱hashMap会被gc回收
 */
public class WeakHashMapDemo {

    public static void main(String[] args) {
        //useHashMap();
        useWeakHashMap();
    }

    private static void useWeakHashMap() {
        WeakHashMap<Integer,String> map = new WeakHashMap<>();
        Integer integer = new Integer(1);
        String string = "HashMap";
        String string2 = "WeakHashMap";

        map.put(integer,string);

        System.out.println(map);

        integer = null;

        System.out.println(map);

        System.gc();

        System.out.println(map);
    }

    private static void useHashMap() {
        HashMap<Integer,String> map = new HashMap<>();
        Integer integer = new Integer(1);
        String string = "HashMap";

        map.put(integer,string);

        System.out.println(map);

        integer = null;

        System.out.println(map);

        System.gc();

        System.out.println(map);

    }


}
