package cn.mrcode.study.dsalgtutorialdemo.datastructure.linkedlist.josepfu;

import org.junit.Test;

/**
 * 约瑟夫问题测试
 */
public class JosepfuTest {
    /**
     * 添加测试
     */
    @Test
    public void addTest() {
        CircleSingleLinkedList circleSingleLinkedList = new CircleSingleLinkedList();
        circleSingleLinkedList.add(5);
        circleSingleLinkedList.print();
    }

    @Test
    public void countBoy() {
        CircleSingleLinkedList circleSingleLinkedList = new CircleSingleLinkedList();
        circleSingleLinkedList.add(5);
        System.out.println("构建环形队列");
        circleSingleLinkedList.print();

        // 开始玩游戏
        // 正确的输出顺序为：2、4、1、5、3
        circleSingleLinkedList.countBoy(1, 2, 5);
    }
}
