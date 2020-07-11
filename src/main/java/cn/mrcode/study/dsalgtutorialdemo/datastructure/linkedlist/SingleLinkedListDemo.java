package cn.mrcode.study.dsalgtutorialdemo.datastructure.linkedlist;

/**
 * 单向链表测试
 */
public class SingleLinkedListDemo {
    public static void main(String[] args) {
//        test1();
        test2();
    }

    /**
     * 不考虑顺序的添加
     */
    public static void test1() {
        HeroNode hero1 = new HeroNode(1, "宋江", "及时雨");
        HeroNode hero2 = new HeroNode(2, "卢俊义", "玉麒麟");
        HeroNode hero3 = new HeroNode(3, "吴用", "智多星");
        HeroNode hero4 = new HeroNode(4, "林冲", "豹子头");

        SingleLinkedList singleLinkedList = new SingleLinkedList();
        singleLinkedList.add(hero1);
        singleLinkedList.add(hero4);
        singleLinkedList.add(hero2);
        singleLinkedList.add(hero3);

        singleLinkedList.list();
    }

    /**
     * 考虑顺序的添加
     */
    public static void test2() {
        HeroNode hero1 = new HeroNode(1, "宋江", "及时雨");
        HeroNode hero2 = new HeroNode(2, "卢俊义", "玉麒麟");
        HeroNode hero3 = new HeroNode(3, "吴用", "智多星");
        HeroNode hero4 = new HeroNode(4, "林冲", "豹子头");

        SingleLinkedList singleLinkedList = new SingleLinkedList();
        singleLinkedList.addByOrder(hero1);
        singleLinkedList.addByOrder(hero4);
        singleLinkedList.addByOrder(hero4);
        singleLinkedList.addByOrder(hero2);
        singleLinkedList.addByOrder(hero3);

        singleLinkedList.list();
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
