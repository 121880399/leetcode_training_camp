# [714. 买卖股票的最佳时机含手续费](https://leetcode-cn.com/problems/best-time-to-buy-and-sell-stock-with-transaction-fee/)

给定一个整数数组 prices，其中第 i 个元素代表了第 i 天的股票价格 ；非负整数 fee 代表了交易股票的手续费用。

你可以无限次地完成交易，但是你每笔交易都需要付手续费。如果你已经购买了一个股票，在卖出它之前你就不能再继续购买股票了。

返回获得利润的最大值。

注意：这里的一笔交易指买入持有并卖出股票的整个过程，每笔交易你只需要为支付一次手续费。

示例 1:

```
输入: prices = [1, 3, 2, 8, 4, 9], fee = 2
输出: 8
解释: 能够达到的最大利润:  
在此处买入 prices[0] = 1
在此处卖出 prices[3] = 8
在此处买入 prices[4] = 4
在此处卖出 prices[5] = 9
总利润: ((8 - 1) - 2) + ((9 - 4) - 2) = 8.
```

注意:

```
0 < prices.length <= 50000.
0 < prices[i] < 50000.
0 <= fee < 50000.
```

#### 1.解题思路

本题很简单，是[188题](https://github.com/121880399/leetcode_training_camp/blob/master/wiki/t188/README.md)的变种，只是增加手续费而已，只要在购买股票或者卖出股票的时候扣除手续费就行了，在这里我们采用卖出时扣除手续费。

#### 2.代码实现

```java
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
```

这里需要注意，在初始化值的时候profitMax_i_1我们没有设置为Integer.MIN_VALUE，因为我们是在卖出的时候扣除手续费的，如果是最小值再减去一个数，可能就溢出了。在买入时扣除手续费则没有这个问题。
