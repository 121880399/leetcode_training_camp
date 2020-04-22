package org.zzy.leetcode.middle.t300;

import java.util.Arrays;

/**
 * ================================================
 * 作    者：ZhouZhengyi
 * 创建日期：2020/4/21 23:19
 * 描    述：Leetcode 第300题 最长上升子序列 难度：中等
 * 给定一个无序的整数数组，找到其中最长上升子序列的长度。
 * https://leetcode-cn.com/problems/longest-increasing-subsequence/
 * 修订历史：
 * ================================================
 */
public class Solution {

    public int lengthOfLIS(int[] nums) {
        if(nums.length <= 0){
            return 0;
        }
        //如果给定数组不为空，那么最长上升子序列最少是1
        int result = 1;
        //用于存放给定数值下标对应的最长子序列个数
        int[] dp = new int[nums.length];
        for(int i=0;i<nums.length;i++){
            //全部设置默认值为1，因为就算是倒序的情况下，每个位置对应的最长序列个数也为1
            dp[i]=1;
        }

        for(int i=1;i<nums.length;i++){
            for(int j=0;j<i;j++){
                if(nums[j]<nums[i]){
                    //如果i所在位置的数大于j所在位置的数,说明存在升序
                    //确保dp[i]的值是i之前所有小于Nums[j]的数中，最大子序列数+1的值与默认值1之间的最大值
                    dp[i] = Math.max(dp[i],dp[j]+1);
                }
            }
            //把最大上升子序列数与当前i位置得到的最大上升子序列数相比，保存最大值
            result = Math.max(result,dp[i]);
        }
        System.out.print(Arrays.toString(dp));
        return result;
    }


    public static void main(String []args){
        Solution solution = new Solution();
        int [] arr = new int[]{1,3,6,7,9,4,10,5,6};
        System.out.println(solution.lengthOfLIS(arr));
    }
}
