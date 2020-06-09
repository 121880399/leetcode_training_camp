# [面试题06. 从尾到头打印链表](https://leetcode-cn.com/problems/cong-wei-dao-tou-da-yin-lian-biao-lcof/)

输入一个链表的头节点，从尾到头反过来返回每个节点的值（用数组返回）。

 

示例 1：

```
输入：head = [1,3,2]
输出：[2,3,1]
```

限制：

```
0 <= 链表长度 <= 10000
```

### 1.解题思路

单链表只有next指针，所以直接要逆序打印会很麻烦，要不就重新构造一个逆序新链表，或者就使用栈数据结构，把单链表从头获取元素入栈，最后把所有元素出栈，就是逆序。

### 2.代码实现

```java
public class ListNode {
         int val;
         ListNode next;
         ListNode(int x) { val = x; }
     }

     /**
     * Stack实现
     * 作者: ZhouZhengyi
     * 创建时间: 2020/6/9 18:09
     */
    public int[] reversePrint(ListNode head) {
        Stack<Integer> stack = new Stack();
        while(head != null){
            stack.push(head.val);
            head = head.next;
        }
        int [] result = new int[stack.size()];
        for(int i=0;i<result.length;i++){
            result[i] = stack.pop();
        }
        return result;
    }
    
    /**
    * Deque实现
    * 作者: ZhouZhengyi
    * 创建时间: 2020/6/9 18:22
    */
    public int[] reversePrint_Deque(ListNode head){
        Deque<Integer> deque = new ArrayDeque<>();
        while(head != null){
            deque.push(head.val);
            head = head.next;
        }
        int [] result = new int[deque.size()];
        for(int i=0;i<result.length;i++){
            result[i] = deque.pop();
        }
        
```





