package org.zzy.leetcode.middle.t120;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * ================================================
 * 作    者：ZhouZhengyi
 * 创建日期：2020/4/22 15:58
 * 描    述：Leetcode 第120题 三角形最小路径和 难度：中等
 * 给定一个三角形，找出自顶向下的最小路径和。每一步只能移动到下一行中相邻的结点上。
 * https://leetcode-cn.com/problems/triangle/
 * 修订历史：
 * ================================================
 */
public class Solution {
    public int minimumTotal(List<List<Integer>> triangle) {
        if(triangle == null || triangle.size()==0){
            return 0;
        }
        //这个三角形行数跟列数是相等的
        int n = triangle.size();
        int [][]dp = new int[n][n];
        //初始化最后一行
        List<Integer> lastRow = triangle.get(n-1);
        for(int j=0;j<n;j++){
            dp[n-1][j]=lastRow.get(j);
        }
        for(int i = n-2;i>=0;i--){
            List<Integer> row = triangle.get(i);
            for(int j=0;j<row.size();j++){
                dp[i][j]=Math.min(dp[i+1][j],dp[i+1][j+1])+row.get(j);
            }
        }
        return dp[0][0];
    }

    /**
    * 采用一维数组来存储，降低空间复杂度
    * 作者: ZhouZhengyi
    * 创建时间: 2020/4/22 17:28
    */
    public int betterMinimumTotal(List<List<Integer>> triangle){
        if(triangle == null || triangle.size()==0){
            return 0;
        }
        int n = triangle.size();
        int [] dp = new int[n+1];

        for(int i = n-1;i>=0;i--){
            List<Integer> row = triangle.get(i);
            for(int j=0;j<row.size();j++){
                //把最后一行的初始化也统一到了这里，因为最后一行没有dp[j+1],为了保证数组不越界，只能在申请大小时加一
                dp[j] = Math.min(dp[j],dp[j+1])+row.get(j);
            }
        }
        return dp[0];
    }

    public static void main(String []args){
        Solution solution = new Solution();
        List<List<Integer>> triangle = new ArrayList<>();
        List<Integer> one = Arrays.asList(2);
        List<Integer> two = Arrays.asList(3,4);
        List<Integer> three = Arrays.asList(6,5,7);
        List<Integer> four = Arrays.asList(4,1,8,3);
        triangle.add(one);
        triangle.add(two);
        triangle.add(three);
        triangle.add(four);
        System.out.println(solution.betterMinimumTotal(triangle));
    }
}
