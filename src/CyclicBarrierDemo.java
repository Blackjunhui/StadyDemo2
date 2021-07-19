import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @Author: zjh
 * @Date: 2021/6/1 16:43
 * @Version 1.0
 */

//模拟集齐七龙珠，召唤神龙
    //线程加法，限制当规定的一定线程做完了才执行，与CountDownLatch相反
public class CyclicBarrierDemo {

    public static void main(String[] args) {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(7,()->{
            System.out.println("******召唤神龙");
        });

        for (int i = 1; i <= 7; i++) {

            final int tempI = i;
            new Thread(()->{
                System.out.println("已收集"+tempI+"星龙珠");
                try {
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }

            },String.valueOf(i)).start();
        }

    }

}
