package org.zzy.leetcode.middle.t64;

/**
 * ================================================
 * 作    者：ZhouZhengyi
 * 创建日期：2020/4/20 23:10
 * 描    述：Leetcode 第64题 最小路径和 难度：中等
 * 给定一个包含非负整数的 m x n 网格，请找出一条从左上角到右下角的路径，使得路径上的数字总和为最小。
 *
 * 说明：每次只能向下或者向右移动一步。
 * 修订历史：
 * ================================================
 */
public class Solution {
    public int minPathSum(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        int [][]dp = new int[m][n];
        //初始化dp[0][0]
        dp[0][0] = grid[0][0];
        //初始化第一列
        for(int i=1;i<m;i++){
            dp[i][0]=dp[i-1][0]+grid[i][0];
        }
        //初始化第一行
        for(int j=1;j<n;j++){
            dp[0][j]=dp[0][j-1]+grid[0][j];
        }
        for(int i=1;i<m;i++){
            for(int j=1;j<n;j++){
                dp[i][j] = Math.min(dp[i-1][j],dp[i][j-1])+grid[i][j];
            }
        }
        return dp[m-1][n-1];
    }

    /**
    * 降低空间复杂度
     * 使用一位数组记录一行的数据
     * 不断的更新一维数组中的值
    * 作者: ZhouZhengyi
    * 创建时间: 2020/4/20 23:52
    */
    public int betterMinPathSum(int[][] grid) {
        //得到行数
        int m = grid.length;
        //得到列数
        int n = grid[0].length;
        //创建一维数组
        int []dp = new int[n];
        //初始化dp[0]
        dp[0] = grid[0][0];
        //初始化第一行
        for(int j=1;j<n;j++){
            //第一行的所有值都是前一列的最小路径和加上本身的数字
            dp[j]=dp[j-1]+grid[0][j];
        }
        for(int i=1;i<m;i++){
            //等式右边的dp[0]是上一行的第0列最小路径和，等式左边的dp[0]是当前行第0列的值
            dp[0] = dp[0]+grid[i][0];
            for(int j=1;j<n;j++){
                dp[j] = Math.min(dp[j],dp[j-1])+grid[i][j];
            }
        }
        return dp[n-1];
    }

}
