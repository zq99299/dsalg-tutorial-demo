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

    /**
     * 基于赫夫曼树，构建赫夫曼编码表
     */
    @Test
    public void buildHuffmanCodesTest() {
        String content = "i like like like java do you like a java";
        byte[] contentBytes = content.getBytes(); // 会使用所在系统的默认字符集构建 bytes，一般是 UTF-8

        // 1. 构建 nodes 列表
        List<Node> nodes = buildNodes(contentBytes);

        // 2. 对列表进行赫夫曼树的构建
        Node node = createHuffmanTree(nodes);

        // 3. 基于赫夫曼树生成 赫夫曼编码表
        Map<Byte, String> codes = buildHuffmanCodes(node);
        System.out.println(codes);
    }

    /**
     * 基于赫夫曼树构建赫夫曼编码表
     *
     * @param node
     * @return
     */
    private Map<Byte, String> buildHuffmanCodes(Node node) {
        if (node == null) {
            System.out.println("赫夫曼树为空");
            return null;
        }
        Map<Byte, String> codes = new HashMap<>();
        StringBuilder sb = new StringBuilder();
        // 根节点没有指向性。所以传递空白的路径代码
        buildHuffmanCodes(node, "", codes, sb);
        return codes;
    }

    /**
     * 递归，查找每个叶子节点，得到每个叶子节点的权路径
     *
     * @param node
     * @param code  左节点，则该值为 0，右节点，则该值为 1
     * @param codes 存放赫夫曼编码的容器
     * @param sb    存放节点 权路径 的容器
     */
    private void buildHuffmanCodes(Node node, String code, Map<Byte, String> codes, StringBuilder sb) {
        if (node == null) {
            return;
        }
        // 注意这里：为什么要用一个新的 sb 容器？
        // 如果一直传递第一个 sb 容器，那么该容器中的字符会越来越长
        // 在递归状态下，每次新增一个 sb 容器，当它回溯的时候，它的下一层处理过的路径，就和该容器无关。那么他还可以进行右节点的拼接。
        // 这样才是正确的使用方式
        StringBuilder sbTemp = new StringBuilder(sb);
        sbTemp.append(code);
        // 如果该节点没有数据，则有下一个叶子节点
        if (node.value == null) {
            buildHuffmanCodes(node.left, "0", codes, sbTemp);
            buildHuffmanCodes(node.right, "1", codes, sbTemp);
        } else {
            // 如果是叶子节点，则将
            codes.put(node.value, sbTemp.toString());
        }
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
