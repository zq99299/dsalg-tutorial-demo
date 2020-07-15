package cn.mrcode.study.dsalgtutorialdemo.datastructure.stack.array;

import org.junit.Test;

public class ArrayStackTest {
    @Test
    public void pushTest() {
        ArrayStack stack = new ArrayStack(4);
        stack.push(1);
        stack.push(2);
        stack.push(3);
        stack.push(4);
        stack.print();
        stack.push(5);
    }

    @Test
    public void popTest() {
        ArrayStack stack = new ArrayStack(4);
        stack.push(1);
        stack.push(2);
        stack.print();
        System.out.println("pop 数据：" + stack.pop());
        stack.print();
        System.out.println("pop 数据：" + stack.pop());
        stack.print();
    }
}
