package org.zzy.leetcode.middle.t714;

/**
 * ================================================
 * 作    者：ZhouZhengyi
 * 创建日期：2020/4/25 10:15
 * 描    述：Leetcode 第714题 买卖股票的最佳时机含手续费 难度：中等
 * 给定一个整数数组 prices，其中第 i 个元素代表了第 i 天的股票价格 ；非负整数 fee 代表了交易股票的手续费用。
 *
 * 你可以无限次地完成交易，但是你每笔交易都需要付手续费。如果你已经购买了一个股票，在卖出它之前你就不能再继续购买股票了。
 *
 * 返回获得利润的最大值。
 *
 * 注意：这里的一笔交易指买入持有并卖出股票的整个过程，每笔交易你只需要为支付一次手续费。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/best-time-to-buy-and-sell-stock-with-transaction-fee
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 * 修订历史：
 * ================================================
 */
public class Solution {
    public int maxProfit(int[] prices, int fee) {
        int len = prices.length;
        if(len < 2){
            return 0;
        }
        int profitMax_i_0 = 0 ,profitMax_i_1=-prices[0];
        int temp;
        for(int price:prices){
            temp = profitMax_i_0;
            profitMax_i_0 = Math.max(profitMax_i_0,profitMax_i_1+price-fee);
            profitMax_i_1 = Math.max(profitMax_i_1,temp-price);
        }
        return profitMax_i_0;
    }

    public static void main(String[] args){
        Solution solution = new Solution();
        int []prices = new int[]{1,3,2,8,4,9};
        int fee = 2;
        System.out.println(solution.maxProfit(prices,fee));
    }
}
