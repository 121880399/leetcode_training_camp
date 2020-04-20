package org.zzy.leetcode.easy.t70;

/**
 * ================================================
 * 作    者：ZhouZhengyi
 * 创建日期：2020/4/20 9:26
 * 描    述：Leetcode第70题  动态规划-爬楼梯
 * 假设你正在爬楼梯。需要 n 阶你才能到达楼顶。
 *
 * 每次你可以爬 1 或 2 个台阶。你有多少种不同的方法可以爬到楼顶呢？
 *
 * 注意：给定 n 是一个正整数。
 * 修订历史：
 * ================================================
 */
public class Solution {
    /**
    * 利用数组来记录
    * 作者: ZhouZhengyi
    * 创建时间: 2020/4/20 9:58
    */
    public int climbStairs(int n) {
        if(n <= 2){
            return n;
        }
        int [] o = new int[n+1];
        o[1] = 1;
        o[2] = 2;
        for(int i=3; i<o.length;i++){
            o[i] = o[i-1]+o[i-2];
        }
        return o[n];
    }

    /**
    * 降低空间复杂度，从O(n)->O(1)
    * 作者: ZhouZhengyi
    * 创建时间: 2020/4/20 10:00
    */
    public int betterClimbStairs(int n){
        //小于2阶台阶，走法跟台阶数一样
        if(n <= 2){
            return n;
        }
        //能执行到这，说明最少3阶台阶
        //表示前面一阶台阶，3阶前面一阶台阶的走法一定是2
        int preOne = 2;
        //表示前面两阶台阶，3阶前面两阶的走法一定是1
        int preTwo = 1;
        //总的走法
        int total = 0;
        for(int i = 2; i < n ; i++){
            total = preOne + preTwo;
            preTwo = preOne;
            preOne = total;
        }
        return total;
    }



    public static void main(String []args){
        Solution solution = new Solution();
        System.out.println(solution.betterClimbStairs(5));
    }
}
