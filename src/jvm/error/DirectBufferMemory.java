package jvm.error;

import java.nio.ByteBuffer;

/**
 * @Author: zjh
 * @Date: 2021/6/29 18:03
 * @Version 1.0
 * 使用sun.misc.VM.maxDirectMemory()可以查看可使用的最大物理内存
 * 直接内存错误:NIO下使用ByteBuffer来读取或者写入数据，这是基于通道(channel)与缓冲区(buffer)的I/O方式,
 * 它可以使用Native函数库直接分配堆外内存,然后通过一个储存在java堆里面的DirectByteBuffer对象作为这块内存的引用进行操作。
 * 这样能在一些场景中显著提高性能,因为避免了在java堆中和Native堆中来回复制数据。
 *
 * ByteBuffer.allocate(capability) 第一种方式是分配jvm堆内存，属于GC管辖范围,由于需要拷贝所以速度较慢
 *
 * ByteBuffer.allocateDirect(capability) 第二种方式是分配到os本地内存，不属于GC管辖范围，由于不需要内存拷贝所以速度较快
 *
 * 但如果不断分配本地内存，堆内存很少使用，那么jvm就不需要执行GC，DirectByteBuffer们就不会被回收，
 * 这时候堆内存充足，但本地内存已经使用光了，再次尝试分配本地内存就会出现OutOfMemoryError错误，程序直接崩溃
 *
 */
public class DirectBufferMemory {

    public static void main(String[] args) {

        System.out.println(sun.misc.VM.maxDirectMemory());

        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(6 * 1024 * 1024);

    }

}
