/**
 * @Author: zjh
 * @Date: 2021/5/24 16:10
 * @Version 1.0
 */
public class SimgleTonDemo {

    //为了弥补DCL不确保有序性的问题，添加轻量级volatile锁,保证有序性，禁止指令重排
    private static volatile SimgleTonDemo instance = null;

    private SimgleTonDemo(){
        System.out.println(Thread.currentThread().getName()+"我是构造函数SimgleTomDemo()");
    }

    public static SimgleTonDemo getInstance(){
        if(instance == null){

            //DCL(double check lock 双端检锁)锁代码块，保证多线程下代码可见性，但不完全确保有序性（上万亿次的并发下可能出现指令重排）
            synchronized (SimgleTonDemo.class){
                if(instance == null){
                    instance = new SimgleTonDemo();
                }
            }
        }
        return instance;
    }

    public static void main(String[] args) {
//        System.out.println(SimgleTonDemo.getInstance() == SimgleTonDemo.getInstance());
//        System.out.println(SimgleTonDemo.getInstance() == SimgleTonDemo.getInstance());
//        System.out.println(SimgleTonDemo.getInstance() == SimgleTonDemo.getInstance());

        for (int i = 1; i <= 2000; i++) {
            new Thread(()->{
                for (int j = 1; j <= 10000; j++) {
                    SimgleTonDemo.getInstance();
                }
            },String.valueOf(i)).start();
        }

    }

}
