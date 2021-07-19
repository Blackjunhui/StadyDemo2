import java.util.concurrent.*;

/**
 * @Author: zjh
 * @Date: 2021/6/2 10:03
 * @Version 1.0
 *
 *
 * 阻塞队列:队列保持先进先出原则。设定特定的阻塞队列值，当队列为空时，取出操作阻塞；当队列满时，添加操作阻塞
 * ArrayBlockingQueue,LinkedBlockingQueue 是有界阻塞队列
 * DelayQueue 使用优先级队列实现的延时无界阻塞队列
 * PriorityBlockingQueue 支持优先级排序的无界阻塞队列
 * LinkedBlockingDeque 链式双端有界阻塞队列
 * LinkedTransferQueue 链式无界阻塞队列
 * SynchronousQueue 不存储元素的阻塞队列，也即单个元素队列
 *
 * api方法:抛出异常式:新增 add(异常:Queue full) 取出 remove(异常:NoSuchElementException) 检查 element
 *         返回值式:       offer(返回值:false)       poll(返回值:null)                        peek
 *         死战不退式      put(一直阻塞)             take(一直阻塞)
 *         过时不候式      offer(超时后，返回false)  poll(超时后，返回null)
 *
 */
public class BlockingQueueDemo {

    public static void main(String[] args) throws InterruptedException {
    }

    //过时不候式
    public void timeOut() throws InterruptedException {
        BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(3);

        System.out.println(blockingQueue.offer("A", 2l, TimeUnit.SECONDS));
        System.out.println(blockingQueue.offer("A", 2l, TimeUnit.SECONDS));
        System.out.println(blockingQueue.offer("A", 2l, TimeUnit.SECONDS));
        System.out.println(blockingQueue.offer("A", 2l, TimeUnit.SECONDS)); //超过预设的2秒添加不了数据，则返回false

        System.out.println(blockingQueue.poll(2l, TimeUnit.SECONDS));
        System.out.println(blockingQueue.poll(2l, TimeUnit.SECONDS));
        System.out.println(blockingQueue.poll(2l, TimeUnit.SECONDS));
        System.out.println(blockingQueue.poll(2l, TimeUnit.SECONDS));  //超过预设的2秒没有获取数据返回null
    }

    //死战不退式
    public void untilDead() throws InterruptedException{
        BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(3);

        blockingQueue.put("A");
        blockingQueue.put("A");
        blockingQueue.put("A");
        System.out.println("+++++++++++++++");
        //blockingQueue.put("A"); //在队列没有释放其他空位的情况下添加，一直阻塞

        blockingQueue.take();
        blockingQueue.take();
        blockingQueue.take();
        blockingQueue.take();//在队列为空时，等待队列中的值的产生，否则一直阻塞
    }

    //返回值式
    public void returnValueDemo(){
        BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(3);

        System.out.println(blockingQueue.offer("A"));
        System.out.println(blockingQueue.offer("B"));
        System.out.println(blockingQueue.offer("C"));
        System.out.println(blockingQueue.offer("E"));//超出给定队列的大小值，返回值false

        System.out.println(blockingQueue.peek());

        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());//队列为空时获取数据,返回值null
    }

    //抛出异常式
    public void throwExceptionDemo(){
        BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(3);

        System.out.println(blockingQueue.add("A"));
        System.out.println(blockingQueue.add("B"));
        System.out.println(blockingQueue.add("C"));
        System.out.println(blockingQueue.add("C"));//超出给定队列的大小值，抛出异常Queue full

        System.out.println(blockingQueue.element());

        System.out.println(blockingQueue.remove());
        System.out.println(blockingQueue.remove());
        System.out.println(blockingQueue.remove());
        System.out.println(blockingQueue.remove());//队列为空时获取数据,抛出异常NoSuchElementException
    }

}
