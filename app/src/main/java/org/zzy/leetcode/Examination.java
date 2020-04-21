package org.zzy.leetcode;

/**
 * ================================================
 * 作    者：ZhouZhengyi
 * 创建日期：2020/4/21 17:32
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class Examination {
    public static long concurrentTime1, concurrentTime2, concurrentMemory1, concurrentMemory2;

    public static void start() {
        //得到程序开始时的系统时间（纳秒级，最终转化毫秒，保留小数点后两位）
        concurrentTime1 = System.nanoTime();
        //得到虚拟机运行、程序开始执行时jvm所占用的内存。
        Runtime runtime = Runtime.getRuntime();
        concurrentMemory1 = runtime.totalMemory()-runtime.freeMemory();
    }

    public static void end() {
        //得到程序执行完毕时的系统时间（毫秒级）
        concurrentTime2 = System.nanoTime();
        //得到虚拟机运行、所要测试的执行代码执行完毕时jvm所占用的内存（byte）。
        Runtime runtime = Runtime.getRuntime();
        concurrentMemory2 = runtime.totalMemory()-runtime.freeMemory();

        //计算start和end之间的代码执行期间所耗时间(ms)与内存(M)。
        // 1毫秒(ms) = 1000微秒(us) = 1000 000纳秒(ns)
        // 1M = 1*2^20 byte = 1024 * 1024 byte;
        String time = String.valueOf((double)(concurrentTime2 - concurrentTime1)/1000000);
        String memory = String.valueOf((double)(concurrentMemory2-concurrentMemory1)/1024/1024) ;
        System.out.println("---------------您的代码执行时间为：" + time.substring(0,time.equals("0.0") ? 1 : (time.indexOf(".")+3)) + " ms, 消耗内存：" + memory.substring(0,memory.equals("0.0") ? 1 : (memory.indexOf(".")+3)) + " M + !---------------");
    }
}
