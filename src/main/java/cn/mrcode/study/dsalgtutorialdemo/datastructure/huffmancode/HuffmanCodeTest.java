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

    /**
     * 基于赫夫曼编码表，将字符串压缩成 byte[]
     */
    @Test
    public void zipTest() {
//        String content = "i like like like java do you like a java";
        String content = "我是一个中国人，我热爱中国";
        byte[] contentBytes = content.getBytes(); // 会使用所在系统的默认字符集构建 bytes，一般是 UTF-8

        // 1. 构建 nodes 列表
        List<Node> nodes = buildNodes(contentBytes);

        // 2. 对列表进行赫夫曼树的构建
        Node node = createHuffmanTree(nodes);

        // 3. 基于赫夫曼树生成 赫夫曼编码表
        Map<Byte, String> huffmanCodes = buildHuffmanCodes(node);

        // 4. 基于赫夫曼编码表，压缩内容
        byte[] huffmanCodeBytes = zip(contentBytes, huffmanCodes);
        System.out.println("将原始字符串压缩成赫夫曼编码的 byte 数组内容为：" + Arrays.toString(huffmanCodeBytes));
        int huffmanCodeBytesLength = huffmanCodeBytes.length;
        System.out.println("将原始字符串压缩成赫夫曼编码的 byte 数组内容长度为：" + huffmanCodeBytesLength);
        int contentBytesLength = contentBytes.length;
        System.out.println("原始字符串的字节数组为 " + contentBytesLength + " ，压缩之后变成了" + huffmanCodeBytesLength +
                "，那么他的压缩比为 " + ((double) (contentBytesLength - huffmanCodeBytesLength) / contentBytesLength) * 100 + "%");
    }

    public byte[] zip(byte[] contentBytes, Map<Byte, String> huffmanCodes) {
        // 1. 将原始字符串 byte 数组，转成以 护赫夫曼编码表 组成的字符串
        StringBuilder contentHuffmanCodeStr = new StringBuilder();
        for (byte contentByte : contentBytes) {
            contentHuffmanCodeStr.append(huffmanCodes.get(contentByte));
        }
        // 1010100010111111110010001011111111001000101111111100100101001101110001110000011011101000111100101000101111111100110001001010011011100
        System.out.println("原始字符串对应的赫夫曼编码字符串为：" + contentHuffmanCodeStr);
        // 这里长度应该是之前分析的 133
        int length = contentHuffmanCodeStr.length();
        System.out.println("原始字符串对应的赫夫曼编码字符串长度为：" + length);

        // 2. 将字符串每 8 个字符转成一个 byte
        // 计算转换后的 byte 数组长度
        // 下面判断分支对应的一行高效代码为：len = contentHuffmanCodeStr.length() + 7 / 8,原理是让它大于等于 8 ，无论是刚好整除还是有余数，都和下面的结果一致
        int len = 0;
        if (length % 8 == 0) {
            len = length / 8;
        } else {
            len = length / 8 + 1;
        }
        byte[] contentHuffmanCodeBytes = new byte[len];
        // 8 位一切分，所以循环步长为 8
        int index = 0;
        for (int i = 0; i < length; i = i + 8) {
            String huffmanCode;
            if (i + 8 < length) {
                huffmanCode = contentHuffmanCodeStr.substring(i, i + 8);
            } else {
                huffmanCode = contentHuffmanCodeStr.substring(i);
            }
            // 10101000 -> 转成 byte，也就是将二进制字符串转成 byte
            contentHuffmanCodeBytes[index++] = (byte) Integer.parseInt(huffmanCode, 2);
        }
        return contentHuffmanCodeBytes;
    }

    /**
     * byte 转成二进制字符串测试
     */
    @Test
    public void byteToBitStringTest() {
        byte num = -1;
        int temp = num;
        // 注意：这里返回的是补码，并非原码：而正数的补码就是原码
        String s = Integer.toBinaryString(temp);
        // 11111111111111111111111111111111
        System.out.println(s);
        // 我们只需要 8 位的，所以需要进行截取
        s = s.substring(s.length() - 8);
        // 11111111
        System.out.println(s);

        // 但是：如果换成正数
        num = 1;
        temp = num;
        s = Integer.toBinaryString(temp);
        // 1
        System.out.println(s);
        // 由于正数，返回的是整数的本身，需要进行高位补位
        //  s = s.substring(s.length() - 8);

        // 高位补位
        // 256 的二进制是 0000 0001 0000 0000
        // 1   的二进制是 0000 0000 0000 0001
        // 进行与计算后：  0000 0001 0000 0001
        // 再截取最后 8 位，         0000 0001
        temp |= 256;
        s = Integer.toBinaryString(temp);
        s = s.substring(s.length() - 8);
        // 0000 0001
        System.out.println(s);
    }

    /**
     * 将上面的推导过程封装成函数
     */
    @Test
    public void byteToBitStringTest2() {
        byte num = -1;
        String s = byteToBitString(true, num);
        System.out.println(num + " 的二进制显示：" + s);

        num = 1;
        s = byteToBitString(true, num);
        System.out.println(num + " 的二进制显示：" + s);
    }

    /**
     * <pre>
     *     是否需要补位的场景有：
     *     1. 上面推导过程中的，正数需要补位。而负数不需要补位；
     *        对于负数补位来说，由于是低 8 位， 256 是第 9 位，运行与之后，截取出来的是不变的
     *        那么这里就统一都补位。而末尾的在外部需要判定是否满 8 个字符，没有满的话，则不需要补位
     *     2. 但是在我们将赫夫曼编码字符串转成 bytes 的时候，末尾的 byte 有可能不是 8 位，那么这个就不需要进行补位，补位就无法还原了
     * </pre>
     *
     * @param flag 是否需要补位
     * @param b    将此 byte 转成二进制字符串返回
     * @return
     */
    public String byteToBitString(boolean flag, byte b) {
        int temp = b;
        // 需要补位
        if (flag) {
            // 高位补位
            // 256 的二进制是 0000 0001 0000 0000
            // 1   的二进制是 0000 0000 0000 0001
            // 进行与计算后：  0000 0001 0000 0001
            // 再截取最后 8 位，         0000 0001
            temp |= 256;
            // 对于负数补位来说，由于是低 8 位， 256 是第 9 位，运行与之后，截取出来的是不变的
        }
        String str = Integer.toBinaryString(temp);
        if (flag) {
            // 只截取低 8 位
            return str.substring(str.length() - 8);
        }
        return str;
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
