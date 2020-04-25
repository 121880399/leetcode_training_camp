# [309. 最佳买卖股票时机含冷冻期](https://leetcode-cn.com/problems/best-time-to-buy-and-sell-stock-with-cooldown/)

给定一个整数数组，其中第 i 个元素代表了第 i 天的股票价格 。

设计一个算法计算出最大利润。在满足以下约束条件下，你可以尽可能地完成更多的交易（多次买卖一支股票）:

你不能同时参与多笔交易（你必须在再次购买前出售掉之前的股票）。
卖出股票后，你无法在第二天买入股票 (即冷冻期为 1 天)。

**示例:**

```
输入: [1,2,3,0,2]
输出: 3 
解释: 对应的交易状态为: [买入, 卖出, 冷冻期, 买入, 卖出]
```

#### 1.解题思路

本题的解题思路还是使用状态机的方式，如果不了解状态机可以参考[123题](https://github.com/121880399/leetcode_training_camp/blob/master/wiki/t123/README.md)和[188题](https://github.com/121880399/leetcode_training_camp/blob/master/wiki/t188/README.md)。

#### 2.定义

```
dp[i][0]:在第i天不持有股票的最大利润
dp[i][1]:在第i天持有股票的最大利润
```

#### 3.方程

```
dp[i][0] = max{dp[i-1][0],dp[i-1][1]+prices[i]}
//这里由于需要冷冻一天，所以只能取i-2天不持有股票，今天买入股票的情况
dp[i][1] = max{dp[i-1][1],dp[i-2][0]-prices[i]}
```

#### 4.代码实现

```java
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
```

虽然使用状态机来解很容易，但是边界问题处理起来还挺麻烦，其中要想清楚，在profitMax[1] [1]的值是多少？首先如果不交易的情况下，肯定是profitMax[i-1] [1],如果交易的情况，正常我们应该取i-2天，但是i-2天不存在，所以不存在利润，那么就去当天买入的价格。按上面代码处理边界问题还挺麻烦，而且还使用了二维数组，在123题中我们说到在状态较少的情况下，可以把状态全部用变量列出来，可以将数组降级为变量，这里我们也试着这样做。

#### 5.优化代码

```java
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
```

这里唯一需要注意的是profitMax_pre_0这个变量，这个变量用来替代上面代码中的profitMax[i-2] [0]的值。