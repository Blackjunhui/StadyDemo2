import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

/**
 * @Author: zjh
 * @Date: 2021/6/3 16:30
 * @Version 1.0
 */

class MyThread implements Callable<Integer>{

    @Override
    public Integer call() throws Exception {
        System.out.println("********Callable is coming!");
        TimeUnit.SECONDS.sleep(3);
        return 10086;
    }
}

public class CallableDemo {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        FutureTask<Integer> futureTask = new FutureTask<>(new MyThread());
        new Thread(()->{
            System.out.println("AA线程执行");
            futureTask.run();
        },"AA").start();

//        new Thread(()->{
//            System.out.println("BB线程执行");
//            futureTask.run();
//        },"BB").start();

        System.out.println("主线程到此一游");
        Integer result2 = 100;
        System.out.println("主线程获取result2值"+result2);
        Integer result = futureTask.get();
        System.out.println("__________result = "+(result+result2));

    }

}
