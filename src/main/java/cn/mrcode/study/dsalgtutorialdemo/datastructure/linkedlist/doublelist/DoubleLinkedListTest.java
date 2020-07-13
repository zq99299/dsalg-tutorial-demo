package cn.mrcode.study.dsalgtutorialdemo.datastructure.linkedlist.doublelist;

import org.junit.Before;
import org.junit.Test;

/**
 * 双向链表测试
 */
public class DoubleLinkedListTest {
    DoubleLinkedList doubleLinkedList;

    @Before
    public void before() {
        HeroNode hero1 = new HeroNode(1, "宋江", "及时雨");
        HeroNode hero2 = new HeroNode(2, "卢俊义", "玉麒麟");
        HeroNode hero3 = new HeroNode(3, "吴用", "智多星");
        HeroNode hero4 = new HeroNode(4, "林冲", "豹子头");

        // 测试新增
        doubleLinkedList = new DoubleLinkedList();
        doubleLinkedList.add(hero1);
        doubleLinkedList.add(hero4);
        doubleLinkedList.add(hero2);
        doubleLinkedList.add(hero3);
    }

    @Test
    public void addTest() {
        // before 中已测试
    }

    /**
     * 更新测试
     */
    @Test
    public void updateTest() {
        System.out.println("更新前");
        doubleLinkedList.print();
        HeroNode hero4New = new HeroNode(4, "林冲-修改测试", "豹子头-修改测试");
        doubleLinkedList.update(hero4New);
        System.out.println("更新后");
        doubleLinkedList.print();
    }

    /**
     * 删除测试
     */
    @Test
    public void deleteTest() {
        System.out.println("删除前");
        doubleLinkedList.print();
        doubleLinkedList.delete(1);
        doubleLinkedList.delete(4);
        doubleLinkedList.delete(3);
        System.out.println("删除后");
        doubleLinkedList.print();
    }

    @Test
    public void addByOrderTest() {
        HeroNode hero1 = new HeroNode(1, "宋江", "及时雨");
        HeroNode hero2 = new HeroNode(2, "卢俊义", "玉麒麟");
        HeroNode hero3 = new HeroNode(3, "吴用", "智多星");
        HeroNode hero4 = new HeroNode(4, "林冲", "豹子头");

        // 测试新增
        doubleLinkedList = new DoubleLinkedList();
        doubleLinkedList.addByOrder(hero1);
        doubleLinkedList.addByOrder(hero4);
        doubleLinkedList.addByOrder(hero2);
        doubleLinkedList.addByOrder(hero3);
        doubleLinkedList.addByOrder(hero3);
        doubleLinkedList.print();
    }
}
