package cn.mrcode.study.dsalgtutorialdemo.datastructure.huffmantree;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 赫夫曼树实现
 */
public class HuffmanTreeTest {
    /**
     * 首先推导实现
     */
    @Test
    public void processDemo() {
        int[] arr = {13, 7, 8, 3, 29, 6, 1};

        // 1. 为了实现方便，先将每个元素转成 Node 对象，并装入 arrayList 中
        List<Node> nodes = new ArrayList<>();
        for (int i : arr) {
            nodes.add(new Node(i));
        }

        // 2. 从小到大排序
        Collections.sort(nodes);

        // 3. 取出两个较小的树
        Node left = nodes.get(0);
        Node right = nodes.get(1);
        // 4. 构成成新的二叉树
        Node parent = new Node(left.value + right.value);
        parent.left = left;
        parent.right = right;
        // 5. 从 list 中删除已经处理过的二叉树
        nodes.remove(left);
        nodes.remove(right);
        // 6. 将新的二叉树添加到 list 中，为下一轮构建做准备
        nodes.add(parent);

        // 最后来看一下结果
        System.out.println("原始数组：" + Arrays.toString(arr));
        System.out.println("新的节点：" + nodes);
    }

    @Test
    public void createHuffmanTreeTest() {
        int[] arr = {13, 7, 8, 3, 29, 6, 1};
        Node huffmanTree = createHuffmanTree(arr);
        huffmanTree.list();
    }

    private Node createHuffmanTree(int[] arr) {
        List<Node> nodes = new ArrayList<>();
        for (int i : arr) {
            nodes.add(new Node(i));
        }

        while (nodes.size() > 1) {
            // 2. 从小到大排序
            Collections.sort(nodes);

            // 3. 取出两个较小的树
            Node left = nodes.get(0);
            Node right = nodes.get(1);
            // 4. 构成成新的二叉树
            Node parent = new Node(left.value + right.value);
            parent.left = left;
            parent.right = right;
            // 5. 从 list 中删除已经处理过的二叉树
            nodes.remove(left);
            nodes.remove(right);
            // 6. 将新的二叉树添加到 list 中，为下一轮构建做准备
            nodes.add(parent);
        }

        // 返回赫夫曼树的 root 节点
        // 因为前面从小到大排序的，最后一个就是最大节点
        return nodes.get(0);
    }
}


/**
 * 节点
 */
class Node implements Comparable<Node> {
    int value; // 权
    Node left;
    Node right;

    public Node(int value) {
        this.value = value;
    }

    /**
     * 为了打印方便
     *
     * @return
     */
    @Override
    public String toString() {
        return value + "";
    }

    /**
     * 从小到大排序
     *
     * @param o
     * @return
     */
    @Override
    public int compareTo(Node o) {
        return this.value - o.value;
    }

    /**
     * 前序遍历
     */
    public void list() {
        System.out.println(value);
        if (left != null) {
            left.list();
        }
        if (right != null) {
            right.list();
        }
    }
}