# [42. 接雨水](https://leetcode-cn.com/problems/trapping-rain-water/)

给定 n 个非负整数表示每个宽度为 1 的柱子的高度图，计算按此排列的柱子，下雨之后能接多少雨水。

![img](https://assets.leetcode-cn.com/aliyun-lc-upload/uploads/2018/10/22/rainwatertrap.png)

上面是由数组 [0,1,0,2,1,0,1,3,2,1,2,1] 表示的高度图，在这种情况下，可以接 6 个单位的雨水（蓝色部分表示雨水）。 感谢 Marcos 贡献此图。

示例:

```
输入: [0,1,0,2,1,0,1,3,2,1,2,1]
输出: 6
```



#### 1.解题思路

看到这题首先想到什么情况下才能接雨水？一定是左右两边高，中间低才行。能接多少雨水取决于左右两边最矮的台阶数，这就跟木桶原理一样，一个木桶能装多少水，取决于最短的那块木板。

#### 2.暴力法

有了前面的思考，我们就考虑到，对于位置i来说，能接多少水在于位置i左边和右边最高台阶数然后减去i位置本身的高度。那么我们可以每次都寻找位置i的左边和右边的最高台阶数是多少，然后再减去i位置的高度，就能得到i位置能接多少水，最后累加起来就是最后总的接雨水数量。

```java
public int trap_one(int []height){
        int len = height.length;
        if(len == 0){
            return 0;
        }
        int result = 0;
        int left_max ;
        int right_max ;
    //从位置1遍历到位置len-2,因为位置0和位置len-1永远不可能接水
        for(int i=1;i<len-1;i++){
            left_max =  height[0];
            right_max = height[len-1];
            //寻找i左边最高台阶
            for(int j = i;j > 0 ;j--){
                left_max = Math.max(left_max,height[j]);
            }
            //寻找i右边最高的台阶
            for(int k = i;k < len-1;k++){
                right_max = Math.max(right_max,height[k]);
            }
            //位置i,左边最高台阶和右边最高台阶中最矮的台阶-本身台阶数
            result += Math.min(left_max,right_max)-height[i];
        }
        return result;
    }
```

#### 2.备忘录法

暴力法的弊端在于每次都要循环寻找位置i的左边和右边最高台阶数，能不能一开始就把每个位置的左边和右边最高台阶数算出来并保存，这样就不用每次都循环查找。这里叫备忘录法，其实里面也有动态规划的思想，每个位置都记录在该位置之前（包括该位置）的最高台阶数，这样的话第i个位置的最高台阶数就只用将本身与i-1记录的最高台阶数进行比较就可以得出。

```java
/**
    * 备忘录法
     * 用两个一维数组来记录i位置左边和右边的最高台阶
     * 来避免每次都循环查找，但是空间复杂度会增加
    * 作者: ZhouZhengyi
    * 创建时间: 2020/6/6 16:11
    */
    public int trap_two(int []height){
        int len = height.length;
        if(len == 0){
            return 0;
        }
        int result = 0;
        int []left_max = new int[len];
        int []right_max = new int[len];
        left_max[0] = height[0];
        right_max[len-1] = height[len-1];
        //寻找在i位置上左边最大台阶
        for(int i=1;i<len;i++){
            //i位置上左边最大台阶=Max(本身台阶数，i-1的最大台阶数）
            left_max[i] = Math.max(height[i],left_max[i-1]);
        }

        //寻找在i位置上右边最大台阶
        for(int j=len-2;j>=0;j--){
            //j位置上右边最大台阶 = Max(本身台阶数，j+1的最大台阶数)
            right_max[j] = Math.max(height[j],right_max[j+1]);
        }
        //从第1位遍历到倒数第2位，因为第0位和倒数第1位不论怎么样都无法存水
        //第0位没有左边的台阶，倒数第1位没有右边的台阶
        for(int k =1 ; k < len-1;k++){
            result+=Math.min(left_max[k],right_max[k]) - height[k];
        }
        return result;
    }
```

#### 3.双指针法

备忘录法把时间复杂度降下来了，但是空间复杂度又提升了，那么能不能在时间复杂度下降的同时，空间复杂度也不增加呢？我们通过分析发现，备忘录法需要把整个数组每个位置的左边和右边最高台阶数记录下来，但是对于位置i只与位置i-1的最大台阶数有关，对于i-2,i-3的值，并不关心，所以可以使用一个变量left_max来记录i-1的最高台阶数，因为left_max记录的肯定是在i-1之前（包括i-1）的最高台阶数。用right_max来记录j+1的最高台阶数。并且动态的获得左边和右边的最高台阶数，而不是一开始就计算好，为了实现动态获去左右两边的最高台阶数，需要使用到两个变量left和right来记录当前的位置。left会从位置1开始向右递增，right会从位置len-2开始向左递减，当两数相等的时候说明整个数组都遍历完成。

```java
 public int trap(int[] height){
        int len = height.length;
        if(len == 0){
            return 0;
        }
        int left =1;
        int right=len-2;
        int result = 0;

        int left_max = height[0];
        int right_max = height[len-1];

        while(left <= right){
            left_max = Math.max(left_max,height[left]);
            right_max = Math.max(right_max,height[right]);

            if(left_max<right_max){
                result += left_max - height[left];
                left++;
            }else{
                result += right_max - height[right];
                right--;
            }
        }
        return result;
    }
```









