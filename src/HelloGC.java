/**
 * @Author: zjh
 * @Date: 2021/6/25 15:27
 * @Version 1.0
 */
public class HelloGC {

    public static void main(String[] args) throws InterruptedException {
//        System.out.println("*******HelloGC");
//        Thread.sleep(Integer.MAX_VALUE);

        int i = 31819;
        String hex = Integer.toHexString(i);
        System.out.println(hex);
    }

}
