package org.zzy.leetcode.easy.t191;

/**
 * ================================================
 * 作    者：ZhouZhengyi
 * 创建日期：2020/5/22 9:20
 * 描    述：leetcode 191题 位1的个数 难度：简单
 * 编写一个函数，输入是一个无符号整数，返回其二进制表达式中数字位数为 ‘1’ 的个数（也被称为汉明重量）。
 * https://leetcode-cn.com/problems/number-of-1-bits/
 * 修订历史：
 * ================================================
 */
public class Solution {

    /**
    * 方法1：每次截取最后一位，判断是否
     * 为1。
    * 作者: ZhouZhengyi
    * 创建时间: 2020/5/22 9:22
    */
    public static int hammingWeight(int n) {
        int num=0;
        //n的二进制有可能很大，导致n是一个负数，所以这里不能使用
        //n>0,比如n的二进制位为11111111111111111111111111111101
        while(n != 0) {
            if ((n & 1) == 1) {
                num++;
            }
            n = n >>> 1;
        }
        return num;
    }

    /**
    * 方法2：采用分组的方式，每次统计一组中1的个数
     * 然后一直合并，最终得到1的个数。
    * 作者: ZhouZhengyi
    * 创建时间: 2020/5/22 10:17
    */
    public static int other(int n){
        //每1位为一组
        //0101的16进制为5 int一共32位，一共有8组0101
        //所以有8个5
        int m_1 = 0x55555555;
        //每2位为一组 0011的16进制为3
        int m_2 = 0x33333333;
        //每4位为一组 00001111的16进制为0f
        int m_4 = 0x0f0f0f0f;
        //每8位为一组 0000000011111111的16进制为00ff
        int m_8 = 0x00ff00ff;
        //每16位为一组 00000000000000001111111111111111的16进制为0000ffff
        int m_16 =0x0000ffff;

        //n与m_1做与运算，得到每组最右边的数
        //再将n右移一位跟m_1做与运算，得到每组最左边的数
        //将得到的两个数相加
        int a = (n & m_1) + ((n>>>1) & m_1);
        int b = (a & m_2) + ((a>>>2) & m_2);
        int c = (b & m_4) + ((b>>>4) & m_4);
        int d = (c & m_8) + ((c>>>8) & m_8);
        int e = (d & m_16) + ((d>>>16) & m_16);
        return e;
    }

    public static void main(String [] args){
        System.out.println(other(24));
        System.out.println(hammingWeight(24));
    }
}
