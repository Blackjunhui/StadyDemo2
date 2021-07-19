import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @Author: zjh
 * @Date: 2021/5/27 15:19
 * @Version 1.0
 */
public class ArrayListDemo {



    public static void main(String[] args) {
        //map可以通过Collections工具类和ConcurrentHashMao来解决并发不安全问题
        Map<String,String> map = new ConcurrentHashMap<>();//Collections.synchronizedMap(new HashMap<>());//new HashMap<>();
        for (int i = 1; i <= 30; i++) {
            new Thread(()->{

                map.put(Thread.currentThread().getName(),UUID.randomUUID().toString().substring(0,8));
                System.out.println(map);
            },String.valueOf(i)).start();
        }
    }

    public void listUnSafe(){//ArrayList添加值线程不安全的原因是因为add()方法没有synchronized
        //使用Vector和Collections工具类可以解决并发列表ConcurrentModificationException问题,除以上两个外还可以使用copyOnWrite容器（重要）
        //copyOnWrite的实现是加锁，复制出新的列表对象，对新的列表对象扩容，添加新的元素，再让地址指向新的列表，读写分离，同步代码块
        List<String> list = new CopyOnWriteArrayList();//Collections.synchronizedList(new ArrayList<>());//new Vector<>();//new ArrayList();
        for (int i = 1; i <= 30; i++) {
            new Thread(()->{
                list.add(UUID.randomUUID().toString().substring(0,8));
                System.out.println(list);
            },String.valueOf(i)).start();
        }
    }

    public void setUnSafe(){
        //set集合add方法使用和arrayList相同的机制。set的底层结构是hashMap,hashSet只设置key值，而value是java自己设置的对象present
        Set<String> set = new CopyOnWriteArraySet<>();//Collections.synchronizedSet(new HashSet<>());//new HashSet<>();
        for (int i = 1; i <= 30; i++) {
            new Thread(()->{
                set.add(UUID.randomUUID().toString().substring(0,8));
                System.out.println(set);
            },String.valueOf(i)).start();
        }
    }

}
