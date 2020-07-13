package cn.mrcode.study.dsalgtutorialdemo.datastructure.linkedlist.doublelist;

/**
 * 双向链表的操作实现
 */
public class DoubleLinkedList {
    private HeroNode head = new HeroNode(0, "", "");

    /**
     * 添加：将节点添加到链表尾部
     *
     * @param node
     */
    public void add(HeroNode node) {
        HeroNode temp = head;
        // 找到链表的末尾
        while (temp.next != null) {
            temp = temp.next;
        }
        // 将新节点添加到末尾的节点上
        temp.next = node;
        node.pre = temp;
    }

    /**
     * <pre>
     *  按编号顺序添加
     *  思路：
     *     1. 从头遍历节点，
     *     2. 找到比目标大的节点：插入到该节点之前（升序）
     *     2. 如果已经存在相同编号的节点，则提示不允许添加
     *
     * </pre>
     *
     * @param node
     */
    public void addByOrder(HeroNode node) {
        HeroNode temp = head;
        boolean exist = false;  // 添加的节点是否已经在链表中存在

        while (true) {
            // 已到列表尾部
            if (temp.next == null) {
                break;
            }
            // 已找到
            if (temp.next.no > node.no) {
                break;
            }

            // 已存在该编号
            if (temp.next.no == node.no) {
                exist = true;
                break;
            }
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
     * 更新：以 id 匹配，更新链表中找到的节点；与单向链表的逻辑一样
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
     * 删除：按编号匹配，将其删除；
     *  思路：直接找到该节点，然后自我删除
     * </pre>
     *
     * @param no
     */
    public void delete(int no) {
        if (head.next == null) {
            System.out.println("链表为空");
            return;
        }
        HeroNode cur = head.next;
        boolean exist = false;  // 是否找到要删除的节点
        while (true) {
            if (cur == null) {
                break;
            }
            // 找到与自己相同的 id
            if (cur.no == no) {
                exist = true;
                break;
            }
            cur = cur.next;
        }
        if (!exist) {
            System.out.printf("未找到匹配的编号 %d \n", no);
            return;
        }

        // 完成自我删除
        // 这里有一个边界问题：当 cur 是末尾节点的时候，next 为空，不处理
        if (cur.next != null) {
            cur.next.pre = cur.pre;
        }
        cur.pre.next = cur.next;
    }

    /**
     * 打印链表
     */
    public void print() {
        if (head.next == null) {
            System.out.println("链表为空");
            return;
        }
        HeroNode cur = head.next;
        while (cur != null) {
            System.out.println(cur);
            cur = cur.next;
        }
    }
}
