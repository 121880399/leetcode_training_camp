package org.zzy.leetcode.hard.t42;

import org.zzy.leetcode.MainActivity;

/**
 * ================================================
 * 作    者：ZhouZhengyi
 * 创建日期：2020/6/6 10:00
 * 描    述：Leetcode 42题 接雨水 难度：困难
 * 给定 n 个非负整数表示每个宽度为 1 的柱子的高度图，计算按此排列的柱子，下雨之后能接多少雨水。
 * https://leetcode-cn.com/problems/trapping-rain-water/
 * 修订历史：
 * ================================================
 */
public class Solution {

    /**
    * 暴力求解法
     * 每次寻找i左边和右边最高的台阶
    * 作者: ZhouZhengyi
    * 创建时间: 2020/6/6 15:41
    */
    public int trap_one(int []height){
        int len = height.length;
        if(len == 0){
            return 0;
        }
        int result = 0;
        int left_max ;
        int right_max ;
        for(int i=1;i<len-1;i++){
            left_max =  height[0];
            right_max = height[len-1];
            //寻找i左边最高台阶
            for(int j = i;j > 0 ;j--){
                left_max = Math.max(left_max,height[j]);
            }
            //寻找i右边最高的台阶
            for(int k = i;k < len-1;k++){
                right_max = Math.max(right_max,height[k]);
            }
            result += Math.min(left_max,right_max)-height[i];
        }
        return result;
    }

    /**
    * 备忘录法
     * 用两个一维数组来记录i位置左边和右边的最高台阶
     * 来避免每次都循环查找，但是空间复杂度会增加
    * 作者: ZhouZhengyi
    * 创建时间: 2020/6/6 16:11
    */
    public int trap_two(int []height){
        int len = height.length;
        if(len == 0){
            return 0;
        }
        int result = 0;
        int []left_max = new int[len];
        int []right_max = new int[len];
        left_max[0] = height[0];
        right_max[len-1] = height[len-1];
        //寻找在i位置上左边最大台阶
        for(int i=1;i<len;i++){
            //i位置上左边最大台阶=Max(本身台阶数，i-1的最大台阶数）
            left_max[i] = Math.max(height[i],left_max[i-1]);
        }

        //寻找在i位置上右边最大台阶
        for(int j=len-2;j>=0;j--){
            //j位置上右边最大台阶 = Max(本身台阶数，j+1的最大台阶数)
            right_max[j] = Math.max(height[j],right_max[j+1]);
        }
        //从第1位遍历到倒数第2位，因为第0位和倒数第1位不论怎么样都无法存水
        //第0位没有左边的台阶，倒数第1位没有右边的台阶
        for(int k =1 ; k < len-1;k++){
            result+=Math.min(left_max[k],right_max[k]) - height[k];
        }
        return result;
    }

    /**
    * 备忘录法的空间复杂度增加了，能不能减少？
     * 发现第i位的左边最大台阶数只与i-1位有关
     * 第i为的右边最大台阶数只与i+1位有关
     * 那么就没必要存储整个数组
    * 作者: ZhouZhengyi
    * 创建时间: 2020/6/6 16:33
    */
    public int trap(int[] height){
        int len = height.length;
        if(len == 0){
            return 0;
        }
        int left =1;
        int right=len-2;
        int result = 0;

        int left_max = height[0];
        int right_max = height[len-1];

        while(left <= right){
            left_max = Math.max(left_max,height[left]);
            right_max = Math.max(right_max,height[right]);

            if(left_max<right_max){
                result += left_max - height[left];
                left++;
            }else{
                result += right_max - height[right];
                right--;
            }
        }
        return result;
    }

    public static void main(String[] args){
        int[] arr = new int[]{0,1,4,2,1,0,6,2,1,2,1};
        Solution solution = new Solution();
        System.out.println(solution.trap_one(arr));
        System.out.println(solution.trap_two(arr));
        System.out.println(solution.trap(arr));
    }
}
