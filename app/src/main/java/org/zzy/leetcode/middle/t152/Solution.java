package org.zzy.leetcode.middle.t152;

/**
 * ================================================
 * 作    者：ZhouZhengyi
 * 创建日期：2020/4/22 20:41
 * 描    述：Leetcode 第152题 乘积最大子数组 难度：中等
 * 给你一个整数数组 nums ，请你找出数组中乘积最大的连续子数组（该子数组中至少包含一个数字）。
 * https://leetcode-cn.com/problems/maximum-product-subarray/
 * 修订历史：
 * ================================================
 */
public class Solution {
    public int maxProduct(int[] nums) {
        //全局最大值
        int max = nums[0];
        //当前最大值,初始化为1是因为1乘以任何数都是对方本身
        int curMax = 1;
        //当前最小值
        int curMin = 1;
        int temp = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] < 0) {
                //小于零，说明要乘以负数，那么将当前最大值和最小值进行交换
                temp = curMax;
                curMax = curMin;
                curMin = temp;
            }
            curMax = Math.max(curMax * nums[i], nums[i]);
            curMin = Math.min(curMin * nums[i], nums[i]);
            max = Math.max(max, curMax);
        }
        return max;
    }


    public static void main(String[] args) {
        int[] prices = new int[]{2, 2, -9, -1};
        Solution solution = new Solution();
        System.out.println(solution.maxProduct(prices));
    }
}
