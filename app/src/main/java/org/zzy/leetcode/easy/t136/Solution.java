package org.zzy.leetcode.easy.t136;

/**
 * ================================================
 * 作    者：ZhouZhengyi
 * 创建日期：2020/7/23 16:12
 * 描    述：Leetcode 136题 只出现一次的数字 难度：简单
 * 给定一个非空整数数组，除了某个元素只出现一次以外，其余每个元素均出现两次。找出那个只出现了一次的元素。
 * https://leetcode-cn.com/problems/single-number/
 * 修订历史：
 * ================================================
 */
class Solution {

    public int singleNumber(int[] nums){
        int n = nums.length;
        int result = nums[0];
        for(int i =0 ;i < n ;i++){
            //两个相等的元素进行异或结果为零
            result = result ^ nums[i];
        }
        return result;
    }
}
