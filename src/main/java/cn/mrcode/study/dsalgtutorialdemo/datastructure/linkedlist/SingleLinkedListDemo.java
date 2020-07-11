package cn.mrcode.study.dsalgtutorialdemo.datastructure.linkedlist;

import org.junit.Before;
import org.junit.Test;

import java.util.Stack;

/**
 * 单向链表测试
 */
public class SingleLinkedListDemo {

    public static void main(String[] args) {
        HeroNode hero1 = new HeroNode(1, "宋江", "及时雨");
        HeroNode hero2 = new HeroNode(2, "卢俊义", "玉麒麟");
        HeroNode hero3 = new HeroNode(3, "吴用", "智多星");
        HeroNode hero4 = new HeroNode(4, "林冲", "豹子头");

        // 测试新增
        SingleLinkedList singleLinkedList = new SingleLinkedList();
//        singleLinkedList.add(hero1);
//        singleLinkedList.add(hero4);
//        singleLinkedList.add(hero2);
//        singleLinkedList.add(hero3);

        // 测试有顺序的新增
        singleLinkedList.addByOrder(hero1);
//        singleLinkedList.addByOrder(hero4);
        singleLinkedList.addByOrder(hero4);
        singleLinkedList.addByOrder(hero2);
        singleLinkedList.addByOrder(hero3);

        // 测试修改
//        System.out.println("修改前");
//        singleLinkedList.list();
//        HeroNode hero4New = new HeroNode(4, "林冲-修改测试", "豹子头-修改测试");
//        singleLinkedList.update(hero4New);

//        System.out.println("修改后");

        System.out.println("删除前");
        singleLinkedList.list();
        singleLinkedList.delete(1);
        singleLinkedList.delete(4);
        System.out.println("删除后");
        singleLinkedList.list();
    }

    private SingleLinkedList singleLinkedList;

    @Before
    public void before() {
        HeroNode hero1 = new HeroNode(1, "宋江", "及时雨");
        HeroNode hero2 = new HeroNode(2, "卢俊义", "玉麒麟");
        HeroNode hero3 = new HeroNode(3, "吴用", "智多星");
        HeroNode hero4 = new HeroNode(4, "林冲", "豹子头");

        // 测试新增
        singleLinkedList = new SingleLinkedList();
        singleLinkedList.add(hero1);
        singleLinkedList.add(hero4);
        singleLinkedList.add(hero2);
        singleLinkedList.add(hero3);
    }


    /**
     * 长度测试
     */
    @Test
    public void lengthTest() {
        System.out.println(singleLinkedList.length());
        singleLinkedList.delete(1);
        System.out.println(singleLinkedList.length());
    }

    /**
     * 查找单链表中的倒数第 k 个结点
     */
    @Test
    public void findLastIndexNodeTest() {
        singleLinkedList.list();
        System.out.println("查找测试");
        HeroNode lastIndexNode = singleLinkedList.findLastIndexNode(1);
        System.out.println("查找倒数第 1 个 " + lastIndexNode);
        lastIndexNode = singleLinkedList.findLastIndexNode(4);
        System.out.println("查找倒数第 4 个 " + lastIndexNode);
        lastIndexNode = singleLinkedList.findLastIndexNode(2);
        System.out.println("查找倒数第 2 个 " + lastIndexNode);
        lastIndexNode = singleLinkedList.findLastIndexNode(5);
        System.out.println("查找倒数第 5 个 " + lastIndexNode);
    }

    /**
     * 翻转链表
     */
    @Test
    public void reverseTest() {
        System.out.println("翻转前");
        singleLinkedList.list();

        singleLinkedList.reverse();

        System.out.println("翻转后");
        singleLinkedList.list();
    }

    /**
     * 逆序打印链表
     */
    @Test
    public void reversePrintTest(){
        System.out.println("链表数据");
        singleLinkedList.list();
        System.out.println("逆序打印");
        singleLinkedList.reversePrint();
    }
}

/**
 * 单向链表
 */
class SingleLinkedList {
    // 头节点，不保存任何数据，只是用来作为一个起始点
    private HeroNode head = new HeroNode(0, "", "");

    /**
     * <pre>
     *
     * 添加节点，不考虑编号顺序时：
     *      1. 找到当前链表的最后节点
     *      2. 将最后整个节点的 next 指向新的节点
     * </pre>
     *
     * @param node
     */
    public void add(HeroNode node) {
        // 要遍历到 next 为 null 的时候，才能进行添加
        HeroNode temp = head;
        while (true) {
            // 找到链表的最后,就退出循环
            if (temp.next == null) {
                break;
            }
            temp = temp.next;
        }
        temp.next = node;
    }

    /**
     * <pre>
     * 添加节点，考虑排序
     *  1. 先找到该节点要添加的位置
     *  2. 改变前一个和该节点的 next 指向
     * </pre>
     *
     * @param node
     */
    public void addByOrder(HeroNode node) {
        // 由于 head 变量不能动，动了就无法从头遍历了，使用辅助变量来完成我们的添加
        HeroNode temp = head;
        boolean exist = false;  // 添加的节点是否已经在链表中存在
        while (true) {
            if (temp.next == null) {
                // 如果是链表尾，则跳出循环
                break;
            }
            // 如果当前节点的 next 编号，大于目标节点编号，则找到
            // 应该将目标节点添加到  temp 与 next 之间
            if (temp.next.no > node.no) {
                break;
            }
            // 如果他们相等，则表示链表中已经存在目标节点了
            if (temp.next.no == node.no) {
                exist = true;
                break;
            }
            // 没找到，则后移 temp ，继续寻找
            temp = temp.next;
        }

        if (exist) {
            System.out.printf("准备插入的英雄编号 %d 已经存在，不能加入 \n", node.no);
            return;
        }
        // 把节点插入到 temp 和 temp.next 之间
        // temp  ->  node  -> temp.next
        node.next = temp.next;
        temp.next = node;
    }

    /**
     * 根据 no 属性找到匹配的节点，进行修改，但是不修改 next 节点。
     *
     * @param newNode
     */
    public void update(HeroNode newNode) {
        if (head.next == null) {
            System.out.println("链表为空");
            return;
        }

        HeroNode temp = head.next;
        boolean exist = false;  // 是否找到要修改的节点
        while (true) {
            // 如果是链表尾部
            if (temp == null) {
                break;
            }
            // 如果已找到
            if (temp.no == newNode.no) {
                exist = true;
                break;
            }
            temp = temp.next;
        }
        // 如果已找到，则修改信息
        if (exist) {
            temp.name = newNode.name;
            temp.nickName = newNode.nickName;
        } else {
            System.out.printf("未找到编号为 %d 的英雄", newNode.no);
        }
    }

    /**
     * <pre>
     *   按编号删除节点
     *   1. 找到要删除的前一个节点
     *   2. 然后将这个前一个节点的 next 指向变更到要删除节点的 next 节点
     * </pre>
     *
     * @param no
     */
    public void delete(int no) {
        if (head.next == null) {
            System.out.println("链表为空");
            return;
        }
        HeroNode temp = head;
        boolean exist = false;  // 是否找到要删除的节点
        while (true) {
            if (temp.next == null) {
                break;
            }
            if (temp.next.no == no) {
                exist = true;
                break;
            }
            temp = temp.next;
        }
        if (!exist) {
            System.out.printf("未找到匹配的编号 %d \n", no);
            return;
        }
        // 删除操作
        temp.next = temp.next.next;
    }

    /**
     * 打印链表中的数据
     */
    public void list() {
        if (head.next == null) {
            System.out.println("链表为空");
            return;
        }
        HeroNode temp = head.next;
        while (true) {
            // 如果是链表的最后
            if (temp == null) {
                break;
            }
            System.out.println(temp);
            temp = temp.next;
        }
    }

    /**
     * <pre>
     * 获取链表长度. 思路：直接循环统计
     * </pre>
     *
     * @return
     */
    public int length() {
        if (head.next == null) {
            return 0;
        }
        HeroNode temp = head.next;
        int num = 0;
        while (temp != null) {
            num++;
            temp = temp.next;
        }
        return num;
    }

    /**
     * <pre>
     *  查找单链表中的倒数第 k 个结点
     *
     *  思路：
     *   1. 获得该链表节点的个数 size
     *   2. 从第一个节点循环开始，到 size-index 结束
     * <pre/>
     * @param index 倒数第几个节点
     * @return
     */
    public HeroNode findLastIndexNode(int index) {
        int size = length();
        if (size == 0) {
            return null;  // 空链表
        }

        // 校验 index
        if (index <= 0 || index > size) {
            return null;
        }

        // 循环查找
        HeroNode cur = head.next;
        for (int i = 0; i < size - index; i++) {
            cur = cur.next;
        }
        return cur;
    }

    /**
     * <pre>
     *  翻转链表。思路如下
     *   1. 定义一个新的 reverseHead 节点
     *   2. 从原链表中依次取出节点，并 始终添加到 reverseHead 的第一个节点
     *   3. 将原 head 节点的 next 指向 reverseHead.next
     * </pre>
     */
    public void reverse() {
        if (head.next == null) {
            return;
        }

        HeroNode cur = head.next; // 保存从原链表中取出来的节点
        HeroNode next = null;
        HeroNode reverseHead = new HeroNode(0, "", "");
        while (cur != null) {
            next = cur.next;
            // 取出来的话，就要清空下一个节点: cur.next = null;
            // 但是这里由于要添加到 reverseHead 的 下一个节点（永远添加到最前面）
            //      那么，就需要一个将已有的 reverseHead 后面的链表添加到这个节点的 next，达到在最前面插入的效果
            cur.next = reverseHead.next;
            reverseHead.next = cur;

            // cur 后移，完成原节点的遍历
            cur = next;
        }
        // 将翻转后的链表挂到现有的 head.next 上
        head.next = reverseHead.next;
    }

    /**
     * <pre>
     * 逆序打印链表：使用栈先进后出的特点达到
     * </pre>
     */
    public void reversePrint() {
        if (head.next == null) {
            System.out.println("链表为空");
            return;
        }

        Stack<HeroNode> stack = new Stack<>();
        HeroNode cur = head.next;
        // 遍历原链表，入栈
        while (cur != null) {
            stack.push(cur);
            cur = cur.next;
        }
        // 打印栈
        while (!stack.empty()) {
            System.out.println(stack.pop());
        }
    }

}

/**
 * 链表中的一个节点：英雄节点
 */
class HeroNode {
    public int no;
    public String name;
    public String nickName;
    public HeroNode next;

    public HeroNode(int no, String name, String nickName) {
        this.no = no;
        this.name = name;
        this.nickName = nickName;
    }

    /**
     * 为了显示方便，重写
     *
     * @return
     */
    @Override
    public String toString() {
        return "HeroNode{" +
                "no=" + no +
                ", name='" + name + '\'' +
                ", nickName='" + nickName + '\'' +
                '}';
    }
}
