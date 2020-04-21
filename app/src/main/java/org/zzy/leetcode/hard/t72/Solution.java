package org.zzy.leetcode.hard.t72;

import org.zzy.leetcode.Examination;

/**
 * ================================================
 * 作    者：ZhouZhengyi
 * 创建日期：2020/4/21 8:34
 * 描    述：Leetcode 第72题  编辑距离 难度：困难
 * 给你两个单词 word1 和 word2，请你计算出将 word1 转换成 word2 所使用的最少操作数 。
 * <p>
 * 你可以对一个单词进行如下三种操作：
 * <p>
 * 插入一个字符
 * 删除一个字符
 * 替换一个字符
 *  
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/edit-distance
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 * 修订历史：
 * ================================================
 */
public class Solution {
    public int minDistance(String word1, String word2) {
        int word1Len = word1.length();
        int word2Len = word2.length();
        int [][] dp = new int[word1Len+1][word2Len+1];

        //初始化第一行
        for(int j = 1; j <= word2Len ;j++){
            dp[0][j] = dp[0][j-1]+1;
        }
        //初始化第一列
        for(int i = 1; i <= word1Len;i++){
            dp[i][0] = dp[i-1][0]+1;
        }

        for(int i =1;i<=word1Len;i++){
            for(int j=1;j<=word2Len;j++){
                if(word1.charAt(i-1) == word2.charAt(j-1)){
                    dp[i][j] = dp[i-1][j-1];
                }else{
                    dp[i][j] = Math.min(Math.min(dp[i][j-1],dp[i-1][j]),dp[i-1][j-1])+1;
                }
            }
        }
        return dp[word1Len][word2Len];
    }

    /**
    * 使用一维数组来存储一行，降低空间复杂度
    * 作者: ZhouZhengyi
    * 创建时间: 2020/4/21 17:44
    */
    public int betterMinDistance(String word1,String word2){
        int world1Len = word1.length();
        int world2Len = word2.length();
        int [] dp = new int[world2Len+1];

        //初始化第一行
        for(int j=1;j<= world2Len ; j++){
            dp[j] = dp[j-1]+1;
        }
        int preValue=0;
        int temp = 0;
        for(int i=1;i<=world1Len;i++){
            preValue = dp[0];
            dp[0]=i;
            for (int j=1;j<=world2Len;j++){
                if(word1.charAt(i-1) == word2.charAt(j-1)){
                    temp = preValue;
                }else {
                    temp = Math.min(Math.min(dp[j - 1], dp[j]), preValue) + 1;
                }
                preValue = dp[j];
                dp[j] = temp;
            }
        }
        return dp[world2Len];
    }

    /**
    * 动态判断word1和word2的长度
     * 短的作为列，长的作为行
     * 这样一维数组大小会变得小一些
     * 但是会多循环
    * 作者: ZhouZhengyi
    * 创建时间: 2020/4/21 17:45
    */
    public int otherMinDistance(String word1,String word2){
        int row = 0;
        int column = 0;
        //word1是行吗？
        boolean isWord1 = true;
        if(word1.length() > word2.length()){
            isWord1 = true;
            row = word1.length();
            column = word2.length();
        }else{
            isWord1 = false;
            row = word2.length();
            column = word1.length();
        }

        int [] dp = new int[column+1];
        //初始化第一行
        for(int j=1;j<= column;j++){
            dp[j] = dp[j-1]+1;
        }
        int preValue=0;
        int temp = 0;
        char word1Char;
        char word2Char;
        for(int i=1;i<=row;i++){
            preValue = dp[0];
            dp[0]=i;
            for (int j=1;j<=column;j++){
                if(isWord1){
                    word1Char = word1.charAt(i-1);
                    word2Char = word2.charAt(j-1);
                }else{
                    word1Char = word1.charAt(j-1);
                    word2Char = word2.charAt(i-1);
                }
                if(word1Char == word2Char){
                    temp = preValue;
                }else {
                    temp = Math.min(Math.min(dp[j - 1], dp[j]), preValue) + 1;
                }
                preValue = dp[j];
                dp[j] = temp;
            }
        }
        return dp[column];
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.betterMinDistance("pneumonoultramicroscopicsilicovolcanoconiosis", "ultramicroscopically"));
//        System.out.println(solution.betterMinDistance("a", "ab"));
    }
}
