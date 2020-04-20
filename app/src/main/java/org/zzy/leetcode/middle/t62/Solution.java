package org.zzy.leetcode.middle.t62;

/**
 * ================================================
 * 作    者：ZhouZhengyi
 * 创建日期：2020/4/20 21:41
 * 描    述：Leetcode 62题 不同路径 难度：中等
 * 一个机器人位于一个 m x n 网格的左上角 （起始点在下图中标记为“Start” ）。
 *
 * 机器人每次只能向下或者向右移动一步。机器人试图达到网格的右下角（在下图中标记为“Finish”）。
 *
 * 问总共有多少条不同的路径？
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/unique-paths
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 * 修订历史：
 * ================================================
 */
public class Solution {
    public int uniquePaths(int m, int n) {
        int [][] dp = new int[m][n];
        //初始化第一行和第一列
        for(int i=0;i<m;i++){
            dp[i][0]=1;
        }
        for(int j=0;j<n;j++){
            dp[0][j]=1;
        }
        for(int i=1;i<m;i++){
            for(int j=1;j<n;j++){
                dp[i][j]=dp[i-1][j]+dp[i][j-1];
            }
        }
        return dp[m-1][n-1];
    }

    /**
    * 降低空间复杂度
     * 我们发现，当前行的值，只有上一行的值有关系
     * 所以我们就打算只用一个一维数组来存储
    * 作者: ZhouZhengyi
    * 创建时间: 2020/4/20 22:11
    */
    public int betterUniquePaths(int m,int n){
        int [] dp = new int[n];
        //初始化
        for(int j=0;j<n;j++){
            //第一行全为1
            dp[j]=1;
        }
        //从第二个行开始赋值
        for(int i=1;i<m;i++){
            //每一行的第一个值
            dp[0]=1;
            for(int j=1;j<n;j++){
                //更新dp[j]的值，等式右边的dp[j]是上一行同一列的值，等式左边是当前行当前列的值
                dp[j] = dp[j-1]+dp[j];
            }
        }
        return dp[n-1];
    }
}
