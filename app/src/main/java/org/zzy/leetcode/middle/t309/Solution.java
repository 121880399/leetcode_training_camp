package org.zzy.leetcode.middle.t309;

/**
 * ================================================
 * 作    者：ZhouZhengyi
 * 创建日期：2020/4/24 22:02
 * 描    述：Leetcode 第309题 最佳买卖股票时机含冷冻期 难度：中等
 * 给定一个整数数组，其中第 i 个元素代表了第 i 天的股票价格 。​
 * <p>
 * 设计一个算法计算出最大利润。在满足以下约束条件下，你可以尽可能地完成更多的交易（多次买卖一支股票）:
 * <p>
 * 你不能同时参与多笔交易（你必须在再次购买前出售掉之前的股票）。
 * 卖出股票后，你无法在第二天买入股票 (即冷冻期为 1 天)。
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/best-time-to-buy-and-sell-stock-with-cooldown
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 * 修订历史：
 * ================================================
 */
public class Solution {
    public int maxProfit(int[] prices) {
        int len = prices.length;
        if (len < 2) {
            return 0;
        }
        int[][] profitMax = new int[len][2];
        for (int i = 0; i < len; i++) {
            if (i - 1 == -1) {
                profitMax[i][0] = 0;
                profitMax[i][1] = -prices[0];
                continue;
            }
            //第i天不持有股票的最大利润=第i-1天不持有股票最大利润（不动）和第i-1天持有股票的最大利润，但是第i天卖出  的最大值
            profitMax[i][0] = Math.max(profitMax[i - 1][0], profitMax[i - 1][1] + prices[i]);
            if (i - 2 == -1) {
                profitMax[i][1] = Math.max(profitMax[i-1][1],-prices[i]);
            } else {
                //第i天持有股票的最大利润 = 第i-1天持有股票最大利润（不动） 和 第i-2天不持有股票的最大利润，但是今天买入 的最大值
                profitMax[i][1] = Math.max(profitMax[i - 1][1], profitMax[i - 2][0] - prices[i]);
            }

        }
        return profitMax[len - 1][0];
    }

    public int betterMaxProfit(int [] prices){
        int len = prices.length;
        if(len < 2){
            return 0;
        }
        int profitMax_i_0 = 0,profitMax_i_1=Integer.MIN_VALUE;
        //用来代替profitMax[i-2][0]
        int profitMax_pre_0 = 0;
        //用来存放profitMax[i-1][0]
        int temp;
        for(int price:prices){
            temp = profitMax_i_0;
            profitMax_i_0 = Math.max(profitMax_i_0,profitMax_i_1+price);
            profitMax_i_1 = Math.max(profitMax_i_1,profitMax_pre_0-price);
            profitMax_pre_0 = temp;
        }
        return profitMax_i_0;
    }

    public static void main(String []args){
        Solution solution = new Solution();
        int [] prices = new int[]{2,1,4};
        System.out.println(solution.maxProfit(prices));
    }
}
