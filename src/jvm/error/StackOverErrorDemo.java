package jvm.error;

/**
 * @Author: zjh
 * @Date: 2021/6/29 17:28
 * @Version 1.0
 * 栈溢出错误:方法调用太多导致撑爆了栈
 */
public class StackOverErrorDemo {

    public static void main(String[] args) {
        isStackOverError();
    }

    private static void isStackOverError() {
        isStackOverError(); //Exception in thread "main" java.lang.StackOverflowError
    }


}
