import java.util.concurrent.*;

/**
 * @Author: zjh
 * @Date: 2021/6/4 10:53
 * @Version 1.0
 *
 * 线程池
 *
 * 线程池最大处理线程如何配置？
 * 1.cpu密集型：一直执行任务，根据cpu核数+1
 * 2.io密集型：与数据库反复io获取数据操作数据  2.1 cpu核数*2
 *                                             2.2 cpu/1-(阻塞系数)   阻塞系数一般在0.8-0.9(某大厂的结论)
 */
public class MyThreadPool {

    public static void main(String[] args) {

        System.out.println(Runtime.getRuntime().availableProcessors());
        //手写线程池
        ExecutorService thread = new ThreadPoolExecutor(
                2,
                Runtime.getRuntime().availableProcessors()*2,//获取cpu核数
                1l,
                TimeUnit.SECONDS,
                new SynchronousQueue<>(),
                Executors.defaultThreadFactory(),
                //new ThreadPoolExecutor.AbortPolicy() //抛出异常的拒绝策略RejectedExecutionException
                //new ThreadPoolExecutor.CallerRunsPolicy() //回退式拒绝策略，把新来的处理不过来的线程回退回去
                //new ThreadPoolExecutor.DiscardOldestPolicy() //把等待时间最长的线程抹去
                new ThreadPoolExecutor.DiscardPolicy() //把新来的处理不过来的线程抹去
        );

        try {
            for (int i = 1; i <= 20; i++) {
                thread.execute(()->{
                    System.out.println(Thread.currentThread().getName()+"\t执行操作");
                });
                //TimeUnit.MILLISECONDS.sleep(5);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            thread.shutdown();
        }

    }


    //常用三个线程池

    /**
     * 为什么一般情况下都不用，而需要手写线程？
     * 因为jdk提供的线程池中，有会用到LinkedBlockingQueue阻塞队列，该队列默认值为Integer.MAX_VALUE表示队列可以加入的最大线程数为21亿多个线程，
     * 在高并发的情况下，线程不停的加入阻塞队列中，从而导致OOM（outOfMemory 内存耗尽）,所以必须手写线程池new ThreadPoolExecutor.
     */
    public void ThreadPool(){
        //ExecutorService executor = Executors.newFixedThreadPool(5);  //一池五线程
        //ExecutorService executor = Executors.newSingleThreadExecutor();  //一池一线程
        ExecutorService executor = Executors.newCachedThreadPool();  //一池多线程

        try {
            for (int i = 1; i <= 20; i++) {
                executor.execute(()->{
                    System.out.println(Thread.currentThread().getName()+"\t执行操作");
                });
                TimeUnit.MILLISECONDS.sleep(5);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            executor.shutdown();
        }
    }

}
