package org.zzy.leetcode.hard.t188;

/**
 * ================================================
 * 作    者：ZhouZhengyi
 * 创建日期：2020/4/24 17:20
 * 描    述：Leetcode 第188题 买卖股票的最佳时机 IV 难度：困难
 * 给定一个数组，它的第 i 个元素是一支给定的股票在第 i 天的价格。
 *
 * 设计一个算法来计算你所能获取的最大利润。你最多可以完成 k 笔交易。
 *
 * 注意: 你不能同时参与多笔交易（你必须在再次购买前出售掉之前的股票）。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/best-time-to-buy-and-sell-stock-iv
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 * 修订历史：
 * ================================================
 */
public class Solution {
    /**
    * 最多k笔交易
    * 作者: ZhouZhengyi
    * 创建时间: 2020/4/24 17:22
    */
    public int maxProfit(int k, int[] prices) {
        int len = prices.length;
        if(len < 2){
            return 0;
        }
        //每笔交易需要2天，len天一共最多进行len/2笔交易，如果超过这个值，设置了k就没用约束的意义，直接采用122题的算法
        if(k > len/2){
           return infinityTrade(prices);
        }
        if(k == 1){
            return oneTrade(prices);
        }
        if(k == 2){
            return twoTrade(prices);
        }
        //设置最多只能进行k笔交易为了防止越界多加1，只能有持有和未持有两种状态
        int [][][] profitMax = new int[len][k+1][2];
        for(int i=0;i<len;i++){
            for(int j =1;j<=k;j++){
                if(i - 1 == -1){
                    profitMax[i][j][0] = 0;
                    profitMax[i][j][1] = -prices[i];
                    continue;
                }
                //第i天交易j次且不持有股票的状态=前一天j次交易不持有股票 和 前一天j次交易持有股票今天卖出 的最大值
                profitMax[i][j][0] = Math.max(profitMax[i-1][j][0],profitMax[i-1][j][1]+prices[i]);
                //第i天交易j次且持有股票的状态=前一天j次交易持有股票 和 前一天j-1次交易（买入增加一次交易）不持有股票今天买入 的最大值
                profitMax[i][j][1] = Math.max(profitMax[i-1][j][1],profitMax[i-1][j-1][0]-prices[i]);
            }
        }
        return profitMax[len-1][k][0];
    }

    /**
    * 无限次交易的方案，k不造成约束，所以不用定义k
     * 而每次最大值只跟前一天有关系，所以可以将3维数组
     * 转换成用几个变量表示
    * 作者: ZhouZhengyi
    * 创建时间: 2020/4/24 17:40
    */
    public int infinityTrade(int[] prices){
        int profitMax_i_0 = 0;
        int profitMax_i_1 = Integer.MIN_VALUE;
        int temp;
        for (int price: prices){
            temp = profitMax_i_0;
            //等式右边的profitMax_i_0是昨天不持有股票的最大利润
            profitMax_i_0 = Math.max(profitMax_i_0,profitMax_i_1+price);
            //等式右边的temp是昨天不持有股票的最大利润，由于上一句代码进行了更新，所以使用temp记录
            profitMax_i_1 = Math.max(profitMax_i_1,temp-price);
        }
        //最后返回不持有状态的最大值
        return profitMax_i_0;
    }

    /**
    * k=1时的交易算法
    * 作者: ZhouZhengyi
    * 创建时间: 2020/4/24 17:56
    */
    public int oneTrade(int[] prices){
        int profitMax_i_1_0 = 0;
        int profitMax_i_1_1 = Integer.MIN_VALUE;
        for(int price:prices){
            profitMax_i_1_0 = Math.max(profitMax_i_1_0,profitMax_i_1_1+price);
            //这里-price的原因是在于最多只能一次交易，而前一天的交易是0次交易且不持有的最大利润，所以一定是0，则省去
            profitMax_i_1_1 = Math.max(profitMax_i_1_1,-price);
        }
        return profitMax_i_1_0;
    }

    /**
    * k=2时的交易算法
    * 作者: ZhouZhengyi
    * 创建时间: 2020/4/24 17:56
    */
    public int twoTrade(int[] prices){
        //定义为Integer最小值是方便处理边界问题
        int profitMax_i_1_0 = 0 ,profitMax_i_1_1=Integer.MIN_VALUE;
        int profitMax_i_2_0 = 0 ,profitMax_i_2_1=Integer.MIN_VALUE;
        for (int price :prices) {
            //第二次交易必须在第一次交易前面，因为第二次交易使用了第一次交易的值
            profitMax_i_2_0 = Math.max(profitMax_i_2_0,profitMax_i_2_1+price);
            profitMax_i_2_1 = Math.max(profitMax_i_2_1,profitMax_i_1_0-price);
            profitMax_i_1_0 = Math.max(profitMax_i_1_0,profitMax_i_1_1+price);
            profitMax_i_1_1 = Math.max(profitMax_i_1_1,-price);

        }
        return profitMax_i_2_0;
    }
}
