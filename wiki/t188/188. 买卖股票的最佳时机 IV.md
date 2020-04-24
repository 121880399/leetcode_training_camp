# [188. 买卖股票的最佳时机 IV](https://leetcode-cn.com/problems/best-time-to-buy-and-sell-stock-iv/)

给定一个数组，它的第 i 个元素是一支给定的股票在第 i 天的价格。

设计一个算法来计算你所能获取的最大利润。你最多可以完成 k 笔交易。

注意: 你不能同时参与多笔交易（你必须在再次购买前出售掉之前的股票）。

示例 1:

```
输入: [2,4,1], k = 2
输出: 2
解释: 在第 1 天 (股票价格 = 2) 的时候买入，在第 2 天 (股票价格 = 4) 的时候卖出，这笔交易所能获得利润 = 4-2 = 2 。
```

示例 2:

```
输入: [3,2,6,5,0,3], k = 2
输出: 7
解释: 在第 2 天 (股票价格 = 2) 的时候买入，在第 3 天 (股票价格 = 6) 的时候卖出, 这笔交易所能获得利润 = 6-2 = 4 。
     随后，在第 5 天 (股票价格 = 0) 的时候买入，在第 6 天 (股票价格 = 3) 的时候卖出, 这笔交易所能获得利润 = 3-0 = 3 。
```

#### 1.解题思路

本题可以说是股票买卖类题目的泛型，这道题可以通过状态机的方式来解，这题弄会了，其他股票买卖类题目都迎刃而解了，所谓状态机就是把所用到的状态全部记录下来，比如这题可以用给一个三维数组来存储所有的状态，一维存储价格，一维存储交易限制，一维存储当前持有股票的状态。

#### 2.定义：

```
dp[i][k][j]：
	i：表示当前的价格
	k：表示交易次数
	j：表示持有股票的状态，如果每次只能持有一股的话，就只有0未持有，1持有两种
```

#### 3.方程

```
dp[i][k][0] = Math.max(dp[i-1][k][0],dp[i-1][k][1]+prices[i]);
第i天，k次交易且不持有股票的情况：
	1.i-1天时就是k次交易且不持有股票，i天时不动
	2.i-1天时就是k次交易持有股票，i天时卖出
dp[i][k][1] = Math.max(dp[i-1][k][1],dp[i-1][k-1][0]-prices[i]);
第i天，k次交易且持有股票的情况：
	1.i-1天时就k次交易且持有股票，i天时不动
	2.i-1天时就k-1次交易且不持有股票，i天时买入（买入要增加一次交易）
```

#### 4.代码实现

```java
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
```

这里顺便实现了k=1，k=2，k=无限次的算法代码，也就相当于解了[121题](https://github.com/121880399/leetcode_training_camp/blob/master/wiki/t121/README.md)，[122题](https://github.com/121880399/leetcode_training_camp/blob/master/wiki/t122/README.md)，[123题](https://github.com/121880399/leetcode_training_camp/blob/master/wiki/t123/README.md)。