package main;

import java.util.Stack;

/**
 * Created by Asus- on 2018/7/16.
 * 链表反转
 */
public class LinkListReverse {

    /**
     * 内部类，模拟链表
     */
    static class ListNode {
        int val;
        ListNode next;

        public ListNode(int val) {
            this.val = val;
        }
    }

    public static void main(String[] args) {
        ListNode node1 = new ListNode(1);
        ListNode node2 = new ListNode(2);
        ListNode node3 = new ListNode(3);
        ListNode node4 = new ListNode(4);

        node1.next = node2;
        node2.next = node3;
        node3.next = node4;

        Stack<ListNode> nodeStack = linkListReverse(node1);
        //如果栈不为空，出栈，打印
        while (!nodeStack.isEmpty()){
            ListNode node = nodeStack.pop();
            System.out.println(node.val);
        }
    }

    /**
     * 利用栈的后进先出特性
     * @param head
     * @return
     */
    public static Stack<ListNode> linkListReverse(ListNode head) {
        Stack<ListNode> nodeStack = new Stack<>();
        ListNode current = head;
        while (current != null){
            nodeStack.push(current);
            current = current.next;
        }
        return nodeStack;
    }
}
