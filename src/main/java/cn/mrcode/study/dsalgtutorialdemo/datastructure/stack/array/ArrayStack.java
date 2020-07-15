package cn.mrcode.study.dsalgtutorialdemo.datastructure.stack.array;

/**
 * 数组模拟栈
 */
public class ArrayStack {
    int[] stack; // 数据存储
    int maxSize; // 栈最大数量
    int top = -1; // 栈顶位置

    public ArrayStack(int maxSize) {
        this.maxSize = maxSize;
        stack = new int[maxSize];
    }

    /**
     * 是否已满
     *
     * @return
     */
    public boolean isFull() {
        return maxSize - 1 == top;
    }

    /**
     * 是否为空
     *
     * @return
     */
    public boolean isEmpty() {
        return top == -1;
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
        stack[++top] = value;
    }

    /**
     * 出栈
     *
     * @return
     */
    public int pop() {
        if (isEmpty()) {
            throw new RuntimeException("栈中无数据");
        }
        return stack[top--];
    }

    /**
     * 显示栈中数据，从栈顶开始显示，也就是按出栈的顺序显示
     */
    public void print() {
        if (isEmpty()) {
            System.out.println("栈中无数据");
            return;
        }
        for (int i = top; i >= 0; i--) {
            System.out.printf("index=%d, value=%d \n", i, stack[i]);
        }
    }
}
