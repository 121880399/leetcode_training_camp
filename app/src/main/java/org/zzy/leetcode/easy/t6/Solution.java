package org.zzy.leetcode.easy.t6;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Stack;

/**
 * ================================================
 * 作    者：ZhouZhengyi
 * 创建日期：2020/6/9 18:08
 * 描    述：Leetcode 面试题06 从尾到头打印链表 难度：简单
 * 修订历史：
 * ================================================
 */
public class Solution {

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
        return result;
    }
}
