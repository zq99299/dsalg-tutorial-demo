package cn.mrcode.study.dsalgtutorialdemo.datastructure.hashtab;

import org.junit.Test;

/**
 * 数组 + 链表的 哈希表实现
 */
public class HashTabTest {
    /**
     * 测试添加和打印
     */
    @Test
    public void test1() {
        HashTab hashtable = new HashTab(7);
        hashtable.list();
        hashtable.add(new Emp(1, "小明"));
        hashtable.add(new Emp(2, "小红"));
        hashtable.add(new Emp(3, "小蓝"));
        System.out.println("");
        hashtable.list();
        hashtable.add(new Emp(3, "小蓝"));
        hashtable.add(new Emp(4, "小蓝4"));
        hashtable.add(new Emp(5, "小蓝5"));
        hashtable.add(new Emp(6, "小蓝6"));
        hashtable.add(new Emp(7, "小蓝7"));
        hashtable.add(new Emp(8, "小蓝8"));
        hashtable.add(new Emp(9, "小蓝9"));
        System.out.println("");
        hashtable.list();

    }

    /**
     * 测试查找
     */
    @Test
    public void findByIdTest() {
        HashTab hashtable = new HashTab(7);
        hashtable.add(new Emp(1, "小明"));
        hashtable.add(new Emp(2, "小红"));
        hashtable.add(new Emp(3, "小蓝"));
        hashtable.add(new Emp(3, "小蓝"));
        hashtable.add(new Emp(4, "小蓝4"));
        hashtable.add(new Emp(5, "小蓝5"));
        hashtable.add(new Emp(6, "小蓝6"));
        hashtable.add(new Emp(7, "小蓝7"));
        hashtable.add(new Emp(8, "小蓝8"));
        hashtable.add(new Emp(9, "小蓝9"));
        System.out.println("");
        hashtable.list();

        hashtable.findById(-1);
        hashtable.findById(1);
        hashtable.findById(9);
        hashtable.findById(5);
        hashtable.findById(19);

    }

    /**
     * 删除查找
     */
    @Test
    public void deleteByIdTest() {
        HashTab hashtable = new HashTab(7);
        hashtable.add(new Emp(1, "小明"));
        hashtable.add(new Emp(2, "小红"));
        hashtable.add(new Emp(3, "小蓝"));
        hashtable.add(new Emp(3, "小蓝"));
        hashtable.add(new Emp(4, "小蓝4"));
        hashtable.add(new Emp(5, "小蓝5"));
        hashtable.add(new Emp(6, "小蓝6"));
        hashtable.add(new Emp(7, "小蓝7"));
        hashtable.add(new Emp(8, "小蓝8"));
        hashtable.add(new Emp(9, "小蓝9"));
        System.out.println("");
        hashtable.list();

        hashtable.deleteById(1);
        hashtable.findById(1);

        hashtable.deleteById(-1);
        hashtable.deleteById(9);
        hashtable.deleteById(10);

        System.out.println("");
        hashtable.list();

    }
}
// 为了方便，下面的各种需要获取的属性都用 public

/**
 * 员工信息
 */
class Emp {
    public int id;
    public String name;
    public Emp next;

    public Emp(int id, String name) {
        this.id = id;
        this.name = name;
    }
}

/**
 * 链表类
 */
class EmpLinkedList {
    /**
     * 这里头直接保存元素，和普通完整的链表有一点不一样
     */
    private Emp head;

    /**
     * 添加一个员工
     *
     * @param emp
     */
    public void add(Emp emp) {
        if (head == null) {
            head = emp;
            return;
        }
        Emp temp = head;
        while (true) {
            if (temp.next == null) {
                break;
            }
            temp = temp.next;
        }
        temp.next = emp;
    }

    /**
     * 打印链表元素
     *
     * @param no
     */
    public void list(int no) {
        if (head == null) {
            System.out.println("链表为空");
            return;
        }
        Emp temp = head;
        while (true) {
            System.out.printf("%d : \t id=%d,\t name=%s \n", no, temp.id, temp.name);
            if (temp.next == null) {
                break;
            }
            temp = temp.next;
        }
    }

    /**
     * 根据 ID 查找雇员
     *
     * @param id
     * @return
     */
    public Emp findById(int id) {
        if (head == null) {
            return null;
        }
        // 有 head 则循环查找链表
        Emp temp = head;
        while (true) {
            if (temp.id == id) {
                // 已经找到
                break;
            }
            if (temp.next == null) {
                // 当下一个为空的时候，则表示没有找到
                temp = null;
                break;
            }
            temp = temp.next;
        }
        return temp;
    }

    /**
     * 按 ID 删除雇员，如果删除成功，则返回删除的元素，如果删除失败，则返回 null
     *
     * @param id
     * @return
     */
    public Emp deleteById(int id) {
        if (head == null) {
            return null;
        }
        // 有 head 则循环查找链表
        Emp temp = head;
        Emp prev = head;
        while (true) {
            if (temp.id == id) {
                // 已经找到
                break;
            }
            if (temp.next == null) {
                // 当下一个为空的时候，则表示没有找到
                temp = null;
                break;
            }
            prev = temp;  // 标记上一个雇员
            temp = temp.next;
        }

        // 没有找到
        if (temp == null) {
            return null;
        }
        // 如果找到的就是 head ,则删除自己
        if (head == temp) {
            head = temp.next;
            return temp;
        }
        // 如果已经找到目标元素，从它的上一个雇员节点中删掉自己
        prev.next = temp.next;
        return temp;
    }
}

/**
 * 哈希表，对外暴露的也就是奔类了。关于里面的数组怎么算，链表怎么放，都是本类来做包装
 */
class HashTab {
    // 链表数组
    private EmpLinkedList[] linkedArray;
    private int size;

    /**
     * 构造一个哈希表
     *
     * @param size 哈希表大小
     */
    public HashTab(int size) {
        this.size = size;
        this.linkedArray = new EmpLinkedList[size];
        // 初始化哈希表中的链表对象
        for (int i = 0; i < size; i++) {
            linkedArray[i] = new EmpLinkedList();
        }
    }

    /**
     * 往哈希表中添加一个员工
     *
     * @param emp
     */
    public void add(Emp emp) {
        // 首先需要确定：该员工的 id 所在的哈希位置，用散列函数来计算
        int id = emp.id;
        int index = hashFun(id);
        linkedArray[index].add(emp);
    }

    /**
     * 打印哈希表
     */
    public void list() {
        for (int i = 0; i < size; i++) {
            linkedArray[i].list(i);
        }
    }

    public Emp findById(int id) {
        // 先找到该 id 属于那一条链表
        int no = hashFun(id);
        // 先判断边界
        if (no > size || no < 0) {
            System.out.printf("id = %d 异常，计算出目标链表为 %d \n", id, no);
            return null;
        }
        Emp emp = linkedArray[no].findById(id);
        if (emp == null) {
            System.out.printf("在第 %d 条链表中未找到 id = %d 的雇员 \n", no, id);
        } else {
            System.out.printf("在第 %d 条链表中找到 id = %d 的雇员, name = %s \n", no, id, emp.name);
        }
        return emp;
    }

    public Emp deleteById(int id) {
        // 先找到该 id 属于那一条链表
        int no = hashFun(id);
        // 先判断边界
        if (no > size || no < 0) {
            System.out.printf("id = %d 异常，计算出目标链表为 %d \n", id, no);
            return null;
        }

        Emp emp = linkedArray[no].deleteById(id);
        if (emp == null) {
            System.out.printf("在第 %d 条链表中未找到 id = %d 的雇员，删除失败 \n", no, id);
        } else {
            System.out.printf("在第 %d 条链表中找到 id = %d 的雇员, name = %s ,删除成功\n", no, id, emp.name);
        }
        return emp;
    }

    /**
     * 散列函数
     *
     * @param id
     * @return
     */
    private int hashFun(int id) {
        // 散列函数的计算法方法有很多种
        // 这里就使用最简单的取模
        return id % size;
    }
}
