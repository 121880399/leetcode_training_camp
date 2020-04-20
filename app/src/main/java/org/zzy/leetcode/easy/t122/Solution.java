package org.zzy.leetcode.easy.t122;

/**
 * ================================================
 * 作    者：ZhouZhengyi
 * 创建日期：2020/4/20 16:54
 * 描    述：Leetcode 122题 买卖股票的最佳时机II
 * 给定一个数组，它的第 i 个元素是一支给定股票第 i 天的价格。
 *
 * 设计一个算法来计算你所能获取的最大利润。你可以尽可能地完成更多的交易（多次买卖一支股票）。
 *
 * 注意：你不能同时参与多笔交易（你必须在再次购买前出售掉之前的股票）。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/best-time-to-buy-and-sell-stock-ii
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 * 修订历史：
 * ================================================
 */
public class Solution {
    public int maxProfit(int[] prices) {
        int [] dp = new int[prices.length];
        dp[0]=0;
        int costMin = -prices[0];
        for(int i=1;i<prices.length;i++){
            int temp = prices[i]-prices[i-1];
            if(temp > 0) {
                dp[i] = Math.max(dp[i - 1]+temp,costMin+prices[i]);
            }else{
                dp[i] = Math.max(dp[i - 1],costMin+prices[i]);
            }
            costMin = Math.max(costMin,-prices[i]);
        }
        return dp[prices.length-1];
    }

    public int betterMaxProfit(int[] prices) {
        int profitMax = 0;
        int costMin = -prices[0];
        for(int i=1;i<prices.length;i++){
            int temp = prices[i]-prices[i-1];
            if(temp > 0) {
                profitMax = Math.max(profitMax+temp,costMin+prices[i]);
            }else{
                profitMax = Math.max(profitMax,costMin+prices[i]);
            }
            costMin = Math.max(costMin,-prices[i]);
        }
        return profitMax;
    }

    public static void main(String[] args){
        int [] prices = new int[]{7,1,5,3,6,4};
        Solution solution = new Solution();
        System.out.println(solution.betterMaxProfit(prices));
    }
}
