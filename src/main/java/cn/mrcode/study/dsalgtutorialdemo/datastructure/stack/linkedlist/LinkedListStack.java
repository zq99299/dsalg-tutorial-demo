package cn.mrcode.study.dsalgtutorialdemo.datastructure.stack.linkedlist;

/**
 * 链表实现栈; 单向链表
 */
public class LinkedListStack {
    int maxSize; // 最大支持数
    int size; // 当前栈中元素个数
    // 用来记录栈顶的元素
    Node top;

    public LinkedListStack(int maxSize) {
        this.maxSize = maxSize;
    }

    /**
     * 是否已满
     *
     * @return
     */
    public boolean isFull() {
        return size == maxSize;
    }

    /**
     * 是否为空
     *
     * @return
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * 入栈
     *
     * @param value
     */
    public void push(int value) {
        if (isFull()) {
            System.out.println("栈已满");
            return;
        }
        // 要保证 top 是最后进来的
        Node temp = top;
        top = new Node(value);
        top.next = temp;
        size++;
    }


    /**
     * 出栈
     *
     * @return
     */
    public int pop() {
        if (isEmpty()) {
            throw new RuntimeException("栈已空");
        }
        // top 保存的是最后入栈的元素，直接从 top 取出即可
        Node temp = top;
        top = temp.next;
        size--;
        return temp.value;
    }

    /**
     * 显示栈中数据，从栈顶开始显示，也就是按出栈的顺序显示
     */
    public void print() {
        if (isEmpty()) {
            System.out.println("栈已空");
            return;
        }
        Node cur = top;
        while (cur != null) {
            System.out.println(cur);
            cur = cur.next;
        }
    }
}

class Node {
    int value;
    Node next;

    public Node(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Node{" +
                "value=" + value +
                '}';
    }
}
