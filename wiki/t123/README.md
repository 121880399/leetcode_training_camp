# [123. 买卖股票的最佳时机 III](https://leetcode-cn.com/problems/best-time-to-buy-and-sell-stock-iii/)

给定一个数组，它的第 i 个元素是一支给定的股票在第 i 天的价格。

设计一个算法来计算你所能获取的最大利润。你最多可以完成 两笔 交易。

注意: 你不能同时参与多笔交易（你必须在再次购买前出售掉之前的股票）。

示例 1:

```
输入: [3,3,5,0,0,3,1,4]
输出: 6
解释: 在第 4 天（股票价格 = 0）的时候买入，在第 6 天（股票价格 = 3）的时候卖出，这笔交易所能获得利润 = 3-0 = 3 。
     随后，在第 7 天（股票价格 = 1）的时候买入，在第 8 天 （股票价格 = 4）的时候卖出，这笔交易所能获得利润 = 4-1 = 3 。
```

示例 2:

```
输入: [1,2,3,4,5]
输出: 4
解释: 在第 1 天（股票价格 = 1）的时候买入，在第 5 天 （股票价格 = 5）的时候卖出, 这笔交易所能获得利润 = 5-1 = 4 。   
     注意你不能在第 1 天和第 2 天接连购买股票，之后再将它们卖出。   
     因为这样属于同时参与了多笔交易，你必须在再次购买前出售掉之前的股票。
```

示例 3:

```
输入: [7,6,4,3,1] 
输出: 0 
解释: 在这个情况下, 没有交易完成, 所以最大利润为 0。
```

#### 1.解题思路

这道题算是比较难的题目了，但是对于股票类型的题目却是有着一种通用解法，就是状态机，掌握这种解法对于股票类题目基本就没用太大问题了。在讲状态机之前，我们先想想还有没有其他的解法？这道题难的地方在于它限定了股票交易的次数为2次。要是交易次数是1次或者无限次都很好解，唯独规定次数以后却是有点麻烦，那么这题我们能不能这样来考虑，既然是要得到两次交易中最大的利润，我们在[121题](https://github.com/121880399/leetcode_training_camp/blob/master/wiki/t121/README.md)的时候讲了一次交易的解法，那么我们能不能把两次交易拆分成1次交易？我们先交易一次，然后剩下的一次在剩余的天数中找到一次交易最大利润，最后把两次交易的利润相加，最后取最大值不就行了吗？这里有一个需要注意的地方，就是第一次交易的时候我们能不能取之前交易的利润最大值来加上后面一次交易？这是不行的，因为如果你取前面交易的最大值，你就不知道前面交易了几次，也许前面已经交易了2次，这就违规了，另外一点在于这种解法要求在i这个位置必须交易一次，不能不交易。所以第一次交易的利润一定是当前的价格减去之前的最低买入价格，第二次交易就按照121题的解法来找到一次交易的最大利润。

#### 2.定义

```
dp[i]:在第i天交易一次的情况下，两次交易的最大利润
```

#### 3.方程

```
oneTrade = costMin+prices[i]
dp[i]=oneTrade+profitMax
```

这里oneTrade就是第一次交易的利润，profitMax是第二次交易的利润。

#### 4.代码实现

```java
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
```

这种方式能够通过代码测试，但是效率很低，我们接着讲一下前面提到的状态机的解法。

#### 5.状态机

状态机这个词很唬人，其实就是穷举所有的状态。就拿这个题来说，这个题的的限制条件有3个，首先是给定天数的价格，我们必须在给定的天数内进行交易，其次是交易次数最多是2次，再次是每次买入之前必须出售掉之前的股票，也就是说买入的时候手上必须没有股票。而每种限制都有不同的状态，比如给定价格是一个数组，数组每个位置的价格是不一样的，我们需要记录，其次是交易次数，最多2次交易，那么可以只交易1次，也可以交易2次，再次是手上是否持有股票，可以持有也可以不持有，所以为了记录3中限制的这么多种状态，我们采用一个三维数组来记录。

```
profitMax[prices.length][tradeLimit][holdState]:
prices.length是价格数组的长度
tradeLimite是交易的限制
holdeState是持有股票的状态

这道题tradeLimit就是2。
holdState也是2，因为0表示不持有，1表示当前持有
举例：
profitMax[2][1][1]:表示第三天交易了一次并且持有股票的最大利润。
```

#### 6.方程

有了定义以后，状态转移方程是怎样的呢？

```
比如：
profitMax[2][1][0]:表示第三天交易了一次且没有持有股票的最大利润
要达到这种状态，可能的操作有两种：
1.不进行操作，那么这时就等于前一天不持有股票的最大利润profitMax[1][1][0]
2.进行操作，前一天持有股票，但是今天卖出去了profitMax[1][1][1]+prices[2]
所以综合起来就是这两种操作的最大值
profitMax[2][1][0]=max{profitMax[1][1][0],profitMax[1][1][1]+prices[2]}

profitMax[2][1][1]:表示第三天交易了一次且持有股票的最大利润
要达到这种状态，可能的操作也是两种：
1.不进行操作，那么就等于前一天持有股票的最大利润profitMax[1][1][1]
2.进行操作，前一天不持有股票，但是今天买入了profitMax[1][0][0]-prices[2]
这里注意在进行操作的时候，要去前一天第0次交易且不持股票的最大利润值，为什么要取第0次呢？因为买和卖算一次交易，如果卖的时候算一次交易的话，就在前面减1，买的时候算一次交易就在这里减1。这里我们采用买的时候算一次交易，所以要达到profitMax[2][1][1]这个状态，并且在第3天进行了买入操作，那么前一天必须是0次交易，才能保证今天买入时是第一次交易。
```

理解了这个之后我们就可以通过循环来填充状态，就几维数组就用几层循环，比如这里三维数组，我们就使用3层循环来填充，不过由于这里第3层只有0，1两种状态，我们完全可以不用循环。

#### 7.代码实现

```java
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
```

这就是状态机的解法，这里注意在申请数组的时候，在限制交易的维度上我多加了1，是因为在对k循环的时候存在数组下标越界的情况，如果不加1，k的值只能取0-1，取0的时候，在循环k-1就下标越界的，所以我们加1，并且让k的值从1开始，这样就不会发生越界的情况。最后的结果会保存在profitMax的pricesLen-1天交易2次且不持有股票的时候，为什么一定会保存在这呢？因为如果只交易一次得到最大值，那么k=1和k=2时的值都是一样的，交易2次的时候肯定是第二次的值最大，具体的可以自己debug一下就明白了。还有在holdState这个维度，最后的结果已经是不持有股票的情况，也就是股票全部卖掉的情况。

#### 8.优化

还能优化吗？我们注意到最大利润会存在三维数组中最后一位里面，且每个位置的值只与前面的值有关系，并且状态其实并不多，我们是否可以把状态罗列出来呢？

#### 9.代码实现

```java
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
```

注释写的很详细了，最后这种写法只是因为状态比较少，如果同时可以持有多只股票，也就是holdState的值不是2，而是更多，那么罗列状态的写法就很麻烦了，就要回归到上面的解法，并且要多加一层holdState的循环。