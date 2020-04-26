package org.zzy.leetcode.middle.t322;

/**
 * ================================================
 * 作    者：ZhouZhengyi
 * 创建日期：2020/4/26 14:49
 * 描    述：Leetcode 第322题 零钱兑换 难度：中等
 * 修订历史：给定不同面额的硬币 coins 和一个总金额 amount。编写一个函数来计算可以凑成总金额所需的最少的硬币个数。如果没有任何一种硬币组合能组成总金额，返回 -1。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/coin-change
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 * ================================================
 */
public class Solution {
    public int coinChange(int[] coins, int amount) {
       int len = coins.length;
       int [] dp = new int[amount+1];
       for(int i=1;i<=amount;i++){
           dp[i]=amount+1;
           for(int j= 0;j<len;j++){
               if(i-coins[j]<0){
                   continue;
               }
               dp[i]=Math.min(dp[i-coins[j]]+1,dp[i]);
           }
        }
       return dp[amount] > amount ? -1 : dp[amount];
    }

    public static void main(String [] args){
        Solution solution = new Solution();
        int [] conins = new int[]{2};
        System.out.println(solution.coinChange(conins,3));
    }
}
