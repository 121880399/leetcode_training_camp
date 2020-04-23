package org.zzy.leetcode.hard.t123;

/**
 * ================================================
 * 作    者：ZhouZhengyi
 * 创建日期：2020/4/20 21:10
 * 描    述：Leetcode 123题 买卖股票的最佳时机 III 难度：困难
 * 给定一个数组，它的第 i 个元素是一支给定的股票在第 i 天的价格。
 *
 * 设计一个算法来计算你所能获取的最大利润。你最多可以完成 两笔 交易。
 *
 * 注意: 你不能同时参与多笔交易（你必须在再次购买前出售掉之前的股票）。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/best-time-to-buy-and-sell-stock-iii
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 * 修订历史：
 * ================================================
 */
public class Solution {
    public int maxProfit(int[] prices) {
        if(prices.length == 0){
            return 0;
        }
        int max = 0;
        int costMin = -prices[0];
        int profitMax;
        int oneTrade;
        int twoTradeCostMin;
        int []dp = new int[prices.length];
        for(int i=1;i<prices.length;i++){
            //先进行一笔交易
            oneTrade = costMin+prices[i];
            profitMax = 0;
            twoTradeCostMin = Integer.MIN_VALUE;
            for(int j = i+1;j<prices.length;j++){
                profitMax = Math.max(twoTradeCostMin+prices[j],profitMax);
                twoTradeCostMin = Math.max(twoTradeCostMin,-prices[j]);
            }
            dp[i]=oneTrade+profitMax;
            costMin = Math.max(costMin,-prices[i]);
            max=Math.max(max,dp[i]);
        }
        return max;
    }

    /**
    * 更通用的解法，可以自定义股票的状态和交易次数
    * 作者: ZhouZhengyi
    * 创建时间: 2020/4/23 20:43
    */
    public int otherMaxProfit(int prices[]){
        int pricesLen = prices.length;
        if(pricesLen < 2){
            return 0;
        }
        //持有股票状态，0没持有 1持有
        int holdState = 2;
        //交易上限，本题为最多2次交易
        int tradeLimit= 2;
        int [][][] profitMax = new int[pricesLen][tradeLimit+1][holdState];
        for(int i=0;i<pricesLen;i++){
            for(int k=1;k<=tradeLimit;k++){
                if(i-1 == -1){
                    //初始化
                    profitMax[i][k][0]=0;
                    profitMax[i][k][1]=-prices[i];
                    continue;
                }
                //第i天，交易了k次，没持有股票的情况
                //取 今天没有交易=昨天交易了k次，没有股票的情况和昨天交易了k次且持有股票，今天卖了(增加收入)的情况的最大值
                profitMax[i][k][0] = Math.max(profitMax[i-1][k][0],profitMax[i-1][k][1]+prices[i]);
                //取 今天没有交易=昨天交易了k次，有股票的情况和昨天交易了k-1次且没有持有股票，今天买入股票（收入减少）的情况的最大值
                profitMax[i][k][1] = Math.max(profitMax[i-1][k][1],profitMax[i-1][k-1][0]-prices[i]);
            }
        }
        return profitMax[pricesLen-1][2][0];
    }

    /**
    * 由于状态很少，只有2次交易，且只有持有或者不持有的状态，
     * 而且每次最大利润只与上一次的利润有关系，所以可以用4个变量
     * 来记录，降低空间复杂度，将O(m*3*2)降低到O(1)
    * 作者: ZhouZhengyi
    * 创建时间: 2020/4/23 20:41
    */
    public int betterMaxProfit(int prices[]){
        //初始化
        //第i天，最多交易1次时，没持有股票的最大利润
        //第i天，最多交易1次时，持有股票的最大利润
        int profitMax_i_1_0 = 0,profitMax_i_1_1 = Integer.MIN_VALUE;
        //第i天，最多交易2次时，没有持有股票的最大利润
        //第i天，最多交易2次时，持有股票的最大利润
        int profitMax_i_2_0 = 0,profitMax_i_2_1 = Integer.MIN_VALUE;
        for(int price:prices){
            //本次不动 或者 上一次持有这次卖出
            profitMax_i_1_0 = Math.max(profitMax_i_1_0,profitMax_i_1_1+price);
            //本次不动 或者 上一次没持有，这次买进，但是这里记录的是最多交易一次，上一次交易是0次交易，0次交易且没持有就是0
            profitMax_i_1_1 = Math.max(profitMax_i_1_1,-price);
            //本次不动 或者  上次持有，这次卖出，注意等式右边的profitMax都是上次的值
            profitMax_i_2_0 = Math.max(profitMax_i_2_0,profitMax_i_2_1+price);
            //本次不动 或者 上次持有，且交易1次不持有的状态下 这次买入
            profitMax_i_2_1 = Math.max(profitMax_i_2_1,profitMax_i_1_0-price);
        }
        return profitMax_i_2_0;
    }

    public static void main(String []args){
        Solution solution = new Solution();
        int [] prices = new int[]{3,3,5,0,0,3,1,4};
        System.out.println(solution.otherMaxProfit(prices));
    }
}
