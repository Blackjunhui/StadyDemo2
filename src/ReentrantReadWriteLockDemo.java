import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @Author: zjh
 * @Date: 2021/5/31 17:37
 * @Version 1.0
 */

class ReadWrite{

    private volatile Map<String,Object> map = new HashMap<>();
    ReentrantReadWriteLock rmLock = new ReentrantReadWriteLock();

    public void put(String key,Object value){
        rmLock.writeLock().lock();
        try {
            System.out.println(Thread.currentThread().getName()+"\t 正在写入: "+key);
            TimeUnit.MILLISECONDS.sleep(300);
            map.put(key,value);
            System.out.println(Thread.currentThread().getName()+"\t 写入完成");
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            rmLock.writeLock().unlock();
        }
    }

    public void get(String key){
        rmLock.readLock().lock();
        try {
            System.out.println(Thread.currentThread().getName()+"\t 正在读取");
            TimeUnit.MILLISECONDS.sleep(300);
            Object result = map.get(key);
            System.out.println(Thread.currentThread().getName()+"\t 读取完成: "+result);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            rmLock.readLock().unlock();
        }
    }

}

//读写锁，读写分离，写保持一致性，读保持可见性

/**
 * 读-读  ok
 * 读-写 no
 * 写写 no
 */
public class ReentrantReadWriteLockDemo {

    public static void main(String[] args) {
        ReadWrite rm = new ReadWrite();
        for (int i = 1; i <= 5; i++) {
            final int tempI = i;
            new Thread(()->{
                rm.put(tempI+"",tempI+"");
            },String.valueOf(i)).start();
        }
        for (int i = 1; i <= 5; i++) {
            final int tempI = i;
            new Thread(()->{
                rm.get(tempI+"");
            },String.valueOf(i)).start();
        }
    }

}
