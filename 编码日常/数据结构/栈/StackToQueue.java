package main;

import java.util.Stack;

/**
 * Created by Asus- on 2018/7/18.
 * 使用两个栈实现队列的入队和出队操作
 */
public class StackToQueue<T> {

    //栈1用于入队
    private Stack<T> stack1 = new Stack<>();
    //栈2用于出队
    private Stack<T> stack2 = new Stack<>();

    /**
     * 入队列
     *
     * @param t
     */
    public void append(T t) {
        stack1.push(t);
    }

    /**
     * @return
     * @throws Exception
     */
    public T deleteHead() throws Exception {
        /**
         * 如果栈2是空的，把栈1的所有元素出栈压入栈2，再弹出，否则直接弹出
         */
        if (stack2.isEmpty()) {
            while (!stack1.isEmpty()) {
                stack2.push(stack1.pop());
            }
        }
        if (stack2.isEmpty()) {
            throw new Exception("stack2为空");
        }
        return stack2.pop();
    }

    public static void main(String[] args) throws Exception {
        StackToQueue<String> stackToQueue = new StackToQueue<>();
        stackToQueue.append("1");
        stackToQueue.append("2");
        stackToQueue.append("3");
        System.out.println(stackToQueue.deleteHead());
        System.out.println(stackToQueue.deleteHead());
        stackToQueue.append("4");
        stackToQueue.append("5");
        stackToQueue.append("6");
        System.out.println(stackToQueue.deleteHead());
        System.out.println(stackToQueue.deleteHead());
        System.out.println(stackToQueue.deleteHead());
        System.out.println(stackToQueue.deleteHead());
    }
}
