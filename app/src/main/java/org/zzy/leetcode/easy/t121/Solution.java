package org.zzy.leetcode.easy.t121;

/**
 * ================================================
 * 作    者：ZhouZhengyi
 * 创建日期：2020/4/20 11:06
 * 描    述：Leetcode 第121题 动态规划-买卖股票的最佳时机 难度：简单
 * 给定一个数组，它的第 i 个元素是一支给定股票第 i 天的价格。
 *
 * 如果你最多只允许完成一笔交易（即买入和卖出一支股票一次），设计一个算法来计算你所能获取的最大利润。
 *
 * 注意：你不能在买入股票前卖出股票。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/best-time-to-buy-and-sell-stock
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 * 修订历史：
 * ================================================
 */
public class Solution {

    public int maxProfit(int[] prices) {
        //利益最大
        int profitMax = 0;
        //花费最小
        int costMin = Integer.MIN_VALUE;
        int n = prices.length;
        for(int i = 0 ; i<n;i++){
            profitMax = Math.max(profitMax,costMin+prices[i]);
            costMin = Math.max(costMin,-prices[i]);
        }
        return profitMax;
    }

    public static void main(String[] args){
        int [] prices = new int[]{7,1,5,3,6,4};
        Solution  solution = new Solution();
        System.out.println(solution.maxProfit(prices));
    }
}
