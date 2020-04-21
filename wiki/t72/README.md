# [72. 编辑距离](https://leetcode-cn.com/problems/edit-distance/)

给你两个单词 word1 和 word2，请你计算出将 word1 转换成 word2 所使用的最少操作数 。

你可以对一个单词进行如下三种操作：

插入一个字符
删除一个字符
替换一个字符

示例 1：

```
输入：word1 = "horse", word2 = "ros"
输出：3
解释：
horse -> rorse (将 'h' 替换为 'r')
rorse -> rose (删除 'r')
rose -> ros (删除 'e')
```

示例 2：

```
输入：word1 = "intention", word2 = "execution"
输出：5
解释：
intention -> inention (删除 't')
inention -> enention (将 'i' 替换为 'e')
enention -> exention (将 'n' 替换为 'x')
exention -> exection (将 'n' 替换为 'c')
exection -> execution (插入 'u')
```

#### 1.解题思路

本题一看要将一个字符串变成另外一个字符串，还有3种操作可选，可能一时间就会觉得很难，不知道从哪下手，但是我们仔细的分析题目，首先题目要求是最小操作数，那么大概率要用到min这个方法，其次既然有3种操作，那么应该就是这3种操作中最小操作数。我们以示例1举例，一开始用horse这么长的单词变成ros我们可能找不到规律，我们不凡将用例缩小来研究，比如h这个字母要变成r,需要操作几次？很明显只需要一次，那么h变成ro需要几次?需要两次。是不是意识到了什么？我们能否把horse和ros这个两个单词的所有字母进行拆分，统计出每个被拆分单词变为另外一个单词时需要几种操作，这样最后我们就能得到完成单词的最小操作数，这么说可能有些模糊，我们来看图。

![](https://github.com/121880399/leetcode_training_camp/blob/master/wiki/t72/1.png)

我们建立了一个二维数组，里面的数字记录了每个纵轴的字母变为横轴字母时最小的操作数，比如h变为r,需要一步操作，h变为ro需要两步操作，ho变为r需要了两步操作。把这些最小操作数列出来以后，数组的最后一位就是我们想要的结果，那么我们从这二维数组中找到什么规律了吗？我们发现当最后一位相等时，我们只需要看前面字母的最小操作数是多少，比如ho变为ro,因为最后一个字母o时相等的，我们只需要看h变为r需要几步操作，ho变为ro就需要几步。很明显ho变为ro的值等于第i-1行第j-1列的值，就是它左上角的值。那么如果最后一位不相等会是什么情况呢？我们先看下图：

![](https://github.com/121880399/leetcode_training_camp/blob/master/wiki/t72/2.png)

从第一张图中我们看见了两种可能性

​	1.word1[i]=word2[j]:由于两个单词最后一个字母相等，所以他们最小操作数只用看前面的单词，dp[i-1] [j-1]。

​	2.word1[i]!=word2[j]:两个单词最后一个字母不相等，这时候可以进行3种操作，增加，删除，替换，你可以选择这3种中任何一种操作，但是每一种操作的步骤数可能不同，我们现在分别来看。

​	先看增加，图中要将horse变为ros，我们试图在horse结尾增加一个s字母，让最后一位与ros相同，虽然这样做可能多余，还可能增加操作步骤，但是这也是一种可选的操作方式。horse变为单词horses以后，由于最后一位与ros相同，那么我们只需要考虑红框中的单词变换，也就是horse变为ro需要的操作步骤，知道horse变为ro的操作步骤以后，我们再增加一个s操作，是不是就把ro变成了ros了？所以增加操作的操作数就是**dp[i] [j]=dp[i] [j-1]+1**。

​	接着我们看删除操作，要将horse变为ros,我们还可以把horse中最后一个字母删掉，变成hors。所以我们就值用看红框部分，也就是hors变为ros需要几步，得到hors变为ros的步数以后，我们要记得加1，因为是我们先删除了一个e，才使得在这个操作步骤下得到ros的。图中正好巧合，删除一个e以后，导致hors与ros最后一个字母又相等了，根据前面所说的，当最后一个字母相同的时候，只用看前面单词的操作数。结果就是**dp[i] [j]=dp[i-1] [j]+1**。

​	最后是替换操作，我们将horse最后一个字母e替换成s,这样就变为了horss，这时候跟ros最后一个字母相同，根据前面所说，最后一个字母相同，只用看前面单词的操作数，也就是hors变为ro的操作数，最后再加1就行，因为我们先把e变为了s。结果就是**dp[i] [j]=dp[i-1] [j-1]+1**。

#### 2.定义

```
dp[i][j]:单词1前i个字符要替换成单词2前j个字符最少步数。
```

#### 3.方程

```
	1.如果word1[i]==word2[j]
		dp[i][j] = dp[i-1][j-1]
		最后两个字符相等了，只用看前面字符的最少操作数。
	2.如果word1[i]!=word2[j]
		那么有3种操作：
		1.增加
		2.删除
		3.替换
		dp[i][j]=min{dp[i][j-1],dp[i-1][j],dp[i-1][j-1]}+1
```

#### 4.初始值

在做初始值的时候，我们为了方便，在每个单词前面都加了一个空字母，也就是多加了1列和1行。为什么要这么做？因为这样来初始化更方便，我们来看看之前画的这张图。
![](https://github.com/121880399/leetcode_training_camp/blob/master/wiki/t72/1.png)

从这种图中我们看到，如果要初始化第一列，那么我们必须对比当前i指向的单词1跟单词2的第0个字母是否相等，如果相等的话，就只用看前面单词的操作数。如果我们使用**dp[i] [0]=dp[i-1] [0]**这段代码的时候，我们还必须单独处理第i=0的情况，i=0时，再减1等于负数，就发生了越界。除此之外我们还得考虑单词1和单词2是存在空字符串的情况，因为如果是空字符串，我们在建立二维数组的时候，就会有其中的列或者是行被赋值为0的情况。所以我们采用下图的形式来初始化。

![](https://github.com/121880399/leetcode_training_camp/blob/master/wiki/t72/4.png)

这样我们不论在初始化第一行还是第一列，都是从0开始一路递增，因为不论几个字母的单词变为空字母都是删除操作，有几个字母就删除几个。这样在初始化时就不用单独处理字母是否相同的问题，其次就算传入的单词1和单词2存在空串也没关系，因为我们人为的增加了一行和一列，这样就不会出现在给二维数组初始化时，行或者列是0的情况。

#### 5.代码实现

```java
public int minDistance(String word1, String word2) {
    int word1Len = word1.length();
        int word2Len = word2.length();
        //dp[0][1]代表的是word1的第0给字符到word2第一个字符的最小操作数，既然有第0给字符，那么在创建数组时，两个字符串都要多加1位
        int [][] dp = new int[word1Len+1][word2Len+1];
        //初始化第一行，也就时word1为0个字符时，变成word2需要的最小操作数
        for(int i=1;i<=word2Len;i++){
            dp[0][i] = dp[0][i-1]+1;
        }
        //初始化第一列，也就时word2为0个字符时，变成word1需要的最小操作数
        for(int j=1;j<=word1Len;j++){
            dp[j][0] = dp[j-1][0]+1;
        }
        
        for(int i = 1; i <= word1Len;i++ ){
            for(int j = 1; j <= word2Len;j++){
                //如果word1[i]==word2[j]时，那么dp[i][j]的值就等于dp[i-1][j-1]的值
                if(word1.charAt(i-1)==word2.charAt(j-1)){
                    dp[i][j] = dp[i-1][j-1];
                }else{
                //如果word1[i]!=word2[j]，那么有三种操作
                //1.增加 比如word1：ho word2:ros 那么word1增加一个s变成hos，增加了一个字符，那么操作数要加1，
                //因为最后一个字符已经一样，那么就看前面的字符ho->ro需要几个操作。dp[i][j-1],i指向word1的o,
                //j指向word2的s
                //2.删除 比如word1:hor word2:ro 那么word1删除一个r变成ho，删除了一个字符，那么操作所要加1
                //因为最后一个数被删除了，那么只用看ho->ro需要几个操作。dp[i-1][j],i指向word1的r，j指向
                //word2的o
                //3.替换 比如word1：hor word2：ros，那么word1将r替换成s变成hos，替换了一共字符，操作数要加1
                //因为word1和word2的最后一个字符已经相等了，只用看ho->ro需要几个操作。dp[i-1][j-1]
                //既然可以选择3种操作，那么我们选择哪种呢？答案是选择操作数最小的
                    dp[i][j] = Math.min(Math.min(dp[i][j-1],dp[i-1][j]),dp[i-1][j-1])+1;
                }
            }
        }
       
        return dp[word1Len][word2Len];
}
```

代码其实很简单，就是注释多了一些，逻辑理清楚了，其实代码量很少。

#### 6.优化

以上的代码还能再优化吗？如果传入的单词特别长，那么二维数组申请的空间是否也特别的大？我们考虑一下是否真的需要保存这么多数据呢？好像不需要，我们只需要一个一维数组来保存上一行的数据，然后不断的更新它就行。

#### 7.代码实现

```java
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
    //用来保存左上角的值
    int preValue=0;
    //用来保存过渡数据
    int temp = 0;
    for(int i=1;i<=world1Len;i++){
        preValue = dp[0];
        //每一行的第0个数据是跟着行数不断递增的
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
```

#### 7.再次优化

以上代码已经把空间复杂度从二维数组降为一维数组，还能再优化吗？我们考虑一种情况，输入两个字符串，这两个字符串有可能一长一短，我们只要把短的字符串存放在列的位置，那么我们的一维数组就能缩短。比如输入两个字符串“abcd"和”a"，我们只要申请int dp[2]那么我们比申请int dp[5]能缩短几个存储单元。

```java
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
```

可以看见代码量有变长，这是因为我们要判断两个字符串哪个长，哪个字符串用来当做列，哪个字符串用来当做行。如果输入的字符串比较长的情况，这种方式应该会比较节约空间。