package cn.mrcode.study.dsalgtutorialdemo.datastructure.queue;

import java.util.Scanner;

/**
 * 数组拟环形队列
 */
public class CircleQueueDemo {
    public static void main(String[] args) {
        CircleQueue queue = new CircleQueue(3);

        // 为了测试方便，写一个控制台输入的小程序
        Scanner scanner = new Scanner(System.in);
        boolean loop = true;
        char key = ' '; // 接受用户输入指令
        System.out.println("s(show): 显示队列");
        System.out.println("e(exit): 退出程序");
        System.out.println("a(add): 添加数据到队列");
        System.out.println("g(get): 从队列取出数据");
        System.out.println("h(head): 查看队列头的数据");
        System.out.println("t(tail): 查看队列尾的数据");
        System.out.println("p(isEmpty): 队列是否为空");
        while (loop) {
            key = scanner.next().charAt(0);
            switch (key) {
                case 's':
                    queue.show();
                    break;
                case 'e':
                    loop = false;
                    break;
                case 'a':
                    System.out.println("请输入要添加到队列的整数：");
                    int value = scanner.nextInt();
                    queue.add(value);
                    break;
                case 'g':
                    try {
                        int res = queue.get();
                        System.out.printf("取出的数据是：%d\n", res);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 'h':
                    try {
                        int res = queue.head();
                        System.out.printf("队首数据：%d\n", res);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 't':
                    try {
                        int res = queue.tail();
                        System.out.printf("队尾数据：%d\n", res);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 'p':
                    System.out.printf("队列是否为空：%s", queue.isEmpty());
                    break;
            }
        }
    }
}

class CircleQueue {
    private int maxSize; // 队列最大容量
    private int front; // 队列头,指向 队头 的元素
    private int rear; // 队列尾，指向 队尾 的下一个元素
    private int arr[]; // 用于存储数据，模拟队列

    public CircleQueue(int arrMaxSize) {
        maxSize = arrMaxSize + 1;
        arr = new int[maxSize];
        front = 0;
        rear = 0;
    }

    /**
     * 取出队列数据
     */
    public int get() {
        if (isEmpty()) {
            throw new RuntimeException("队列空");
        }
        // front 指向的是队首的位置
        int value = arr[front];
        // 需要向后移动，但是由于是环形，同样需要使用取模的方式来计算
        front = (front + 1) % maxSize;
        return value;
    }

    /**
     * 往队列存储数据
     */
    public void add(int n) {
        if (isFull()) {
            System.out.println("队列已满");
            return;
        }
        arr[rear] = n;
        // rear 指向的是下一个位置
        // 由于是环形队列,需要使用取模的形式来唤醒他的下一个位置
        rear = (rear + 1) % maxSize;
    }

    /**
     * 显示队列中的数据
     */
    public void show() {
        if (isEmpty()) {
            System.out.println("队列为空");
            return;
        }
        // 打印的时候，需要从队首开始打印
        // 打印的次数则是：有效的元素个数
        // 获取数据的下标：由于是环形的，需要使用取模的方式来获取
        for (int i = front; i < front + size(); i++) {
            int index = i % maxSize;
            System.out.printf("arr[%d] = %d \n", index, arr[index]);
        }
    }

    /**
     * 查看队列的头部数据，注意：不是取出数据，只是查看
     *
     * @return
     */
    public int head() {
        if (isEmpty()) {
            throw new RuntimeException("队列空");
        }
        return arr[front];
    }

    /**
     * 查看队尾数据
     *
     * @return
     */
    public int tail() {
        if (isEmpty()) {
            throw new RuntimeException("队列空");
        }
        // rear - 1 是队尾数据，但是如果是环形收尾相接的时候
        // 那么 0 -1 就是 -1 了，负数时，则是数组的最后一个元素
        return rear - 1 < 0 ? arr[maxSize - 1] : arr[rear - 1];
    }

    // 队列是否已满
    private boolean isFull() {
        return (rear + 1) % maxSize == front;
    }

    // 队列是否为空
    public boolean isEmpty() {
        return rear == front;
    }

    // 有效个数
    public int size() {
        return (rear + maxSize - front) % maxSize;
    }
}
