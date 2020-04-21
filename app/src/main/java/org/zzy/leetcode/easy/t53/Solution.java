package org.zzy.leetcode.easy.t53;

/**
 * ================================================
 * 作    者：ZhouZhengyi
 * 创建日期：2020/4/21 21:54
 * 描    述：Leetcode 第53题 最大子序和 难度：简单
 * 给定一个整数数组 nums ，找到一个具有最大和的连续子数组（子数组最少包含一个元素），返回其最大和。
 * https://leetcode-cn.com/problems/maximum-subarray/
 * 修订历史：
 * ================================================
 */
public class Solution {
    public int maxSubArray(int[] nums) {
        int sum = nums[0];
        int max = sum;
        for(int i=1;i<nums.length;i++){
            if(sum <=0){
                sum = nums[i];
            }else{
                sum = sum + nums[i];
            }
            max=Math.max(max,sum);
        }
        return max;
    }
}
