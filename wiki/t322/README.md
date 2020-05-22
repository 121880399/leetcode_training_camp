# [322. 零钱兑换](https://leetcode-cn.com/problems/coin-change/)

给定不同面额的硬币 coins 和一个总金额 amount。编写一个函数来计算可以凑成总金额所需的最少的硬币个数。如果没有任何一种硬币组合能组成总金额，返回 -1。

示例 1:

```
输入: coins = [1, 2, 5], amount = 11
输出: 3 
解释: 11 = 5 + 5 + 1
```

示例 2:

```
输入: coins = [2], amount = 3
输出: -1
```

说明:
你可以认为每种硬币的数量是无限的。

#### 1.解题思路

​	本题可以用暴力求解，也可以用动态规划，暴力求解就是用要凑成的总金额去减所给的每个硬币，比如用11去减5，一直减到为负数之前得到余数，比如11-5-5 余1，在拿1在数组中进行查找，如果有1这个数那么加一，最后统计使用5元硬币需要的次数，对所给数组中的元素依次这样做，最后取最小的就行，这里可以进行一些剪枝，比如最开始从最大的数开始减，在示例一中就是从5开始减，得到5使用的次数，在减其他数字时比如1，发现减的次数已经超过了5的次数，那么就没必要再继续下去了。

​	对于动态规范的解法，我们可以把题目转换为[爬楼梯问题](https://github.com/121880399/leetcode_training_camp/tree/master/wiki/t70)，在爬楼梯问题里，要爬到第n阶，每次可以爬一阶和两阶，那么爬到第n阶的不同种爬法肯定是n-1阶和n-2阶爬法之和，然后一直向前递推。本题也可以这样考虑，以示例1为例，有11阶台阶，每次可以爬1阶，2阶，5阶，那么爬到11阶的最少爬法是多少？那么我们就要看11-5,11-1,11-2阶台阶的最小爬法，然后一直向前递推。

#### 2.定义

```
dp[i]:金额为i时，最少使用的硬币个数
```

#### 3.方程

```
dp[i]=min(dp[i-coins[j]])+1
```

用爬楼梯的说法就是第i阶台阶的爬法等于之前i-coins[j]阶中的最小爬法再加上1。

#### 4.代码实现

```java
public int coinChange(int[] coins, int amount) {
      int len = coins.length;
    //记录金额为0-amount时，最少使用的硬币个数
       int [] dp = new int[amount+1];
       for(int i=1;i<=amount;i++){
           //因为最少使用硬币个数最多为amount个，所以这里使用amount+1来表示最大值
           dp[i]=amount+1;
           for(int j= 0;j<len;j++){
               //为负数的情况，说明当前硬币面值太大，不能凑成金额
               if(i-coins[j]<0){
                   continue;
               }
               dp[i]=Math.min(dp[i-coins[j]]+1,dp[i]);
           }
        }
    //如果最后的值大于amount说明没法拼凑返回-1
       return dp[amount] > amount ? -1 : dp[amount];
    }
```
