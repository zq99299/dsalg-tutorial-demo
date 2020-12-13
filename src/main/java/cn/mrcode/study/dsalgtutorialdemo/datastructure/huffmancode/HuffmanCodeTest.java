package cn.mrcode.study.dsalgtutorialdemo.datastructure.huffmancode;

import org.junit.Test;

import java.util.*;

/**
 * 赫夫曼编码
 */
public class HuffmanCodeTest {
    /**
     * 英文和中文各自对应的 byte 是几个
     */
    @Test
    public void fun1() {
        System.out.println("i".getBytes().length); // 1
        System.out.println("中".getBytes().length); // 3
    }

    /**
     * 创建赫夫曼树
     */
    @Test
    public void createHuffmanTreeTest() {
        String content = "i like like like java do you like a java";
        byte[] contentBytes = content.getBytes(); // 会使用所在系统的默认字符集构建 bytes，一般是 UTF-8
        System.out.println("原始字符串：" + content);
        System.out.println("原始字符串长度：" + contentBytes.length);

        // 1. 构建 nodes 列表
        List<Node> nodes = buildNodes(contentBytes);
        System.out.println("构建的 Nodes 列表打印：" + nodes);

        // 2. 对列表进行赫夫曼树的构建
        Node node = createHuffmanTree(nodes);
        System.out.println("构建后的赫夫曼树打印：");
        // 验证：构建的赫夫曼树。注意: 每次构建的树可能不一致（由于排序不稳定性）
        node.preOrder();
    }


    /**
     * 将 bytes 构建成 node 列表
     *
     * @param contentBytes
     * @return
     */
    private List<Node> buildNodes(byte[] contentBytes) {
        // 1. 统计每个 byte 出现的次数
        Map<Byte, Integer> counts = new HashMap<>();
        for (byte contentByte : contentBytes) {
            Integer count = counts.get(contentByte);
            if (count == null) {
                count = 0;
                counts.put(contentByte, count);
            }
            counts.put(contentByte, count + 1);
        }

        // 2. 构建 Node list
        List<Node> nodes = new ArrayList<>();
        counts.forEach((key, value) -> {
            nodes.add(new Node(key, value));
        });
        return nodes;
    }


    /**
     * 创建 赫夫曼树
     *
     * @param nodes
     * @return 返回赫夫曼树的根节点；也就是最大的一个节点
     */
    private Node createHuffmanTree(List<Node> nodes) {
        while (nodes.size() > 1) {
            // 1. 先从小到大排序
            Collections.sort(nodes);
            // 2. 取出最小的两个节点组成新的树
            Node left = nodes.get(0);
            Node right = nodes.get(1);
            // 3. 构成父节点：由于赫夫曼树的数据都在叶子节点，父节点只是权值的总和
            Node parent = new Node(null, left.weight + right.weight);
            parent.left = left;
            parent.right = right;
            // 4. 移除处理过的节点
            nodes.remove(left);
            nodes.remove(right);
            nodes.add(parent);
        }
        return nodes.get(0);
    }
}

class Node implements Comparable<Node> {
    Byte value; // 对应的字符；注意：非叶子节点是没有数据的，只有对应叶子节点的权值总和
    int weight; // 权值：字符串出现的总次数
    Node left;
    Node right;

    public Node(Byte value, int weight) {
        this.value = value;
        this.weight = weight;
    }

    /**
     * 前序遍历：方便打印验证
     */
    public void preOrder() {
        System.out.println(this);
        if (left != null) {
            left.preOrder();
        }
        if (right != null) {
            right.preOrder();
        }
    }

    @Override
    public int compareTo(Node o) {
        // 按字符串出现的次数进行：从小到大排序
        return weight - o.weight;
    }

    @Override
    public String toString() {
        return "Node{" +
                "value=" + value +
                ", weight=" + weight +
                '}';
    }
}
