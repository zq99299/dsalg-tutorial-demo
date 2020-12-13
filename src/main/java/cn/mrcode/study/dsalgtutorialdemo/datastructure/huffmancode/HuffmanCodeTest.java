package cn.mrcode.study.dsalgtutorialdemo.datastructure.huffmancode;

import org.junit.Test;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
        String content = "i like like like java do you like a java";
//        String content = "我是一个中国人，我热爱中国";
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
//        System.out.println("原始字符串对应的赫夫曼编码字符串为：" + contentHuffmanCodeStr);
        // 这里长度应该是之前分析的 133
        int length = contentHuffmanCodeStr.length();
//        System.out.println("原始字符串对应的赫夫曼编码字符串长度为：" + length);

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

    /**
     * 将赫夫曼二进制数组转成 赫夫曼编码字符串
     */
    @Test
    public void unzipBytesToHuffmanCodeStrTest() {
        byte[] huffmanBytes = {-88, -65, -56, -65, -56, -65, -55, 77, -57, 6, -24, -14, -117, -4, -60, -90, 28};
        String contentHuffmanCodeStr = unzipBytesToHuffmanCodeStr(huffmanBytes);
        System.out.println(contentHuffmanCodeStr);

        // 为了方便验证，把转成赫夫曼数组之前的编码字符串拿过来进行对比
        String oldContentHuffmanCodeStr = "1010100010111111110010001011111111001000101111111100100101001101110001110000011011101000111100101000101111111100110001001010011011100";
        System.out.println(oldContentHuffmanCodeStr.equals(contentHuffmanCodeStr));
    }

    /**
     * 将压缩之后的数据，bytes 转成赫夫曼编码字符串
     *
     * @param huffmanBytes
     * @return 返回的是赫夫曼编码字符串，也就是 101010001011....
     */
    private String unzipBytesToHuffmanCodeStr(byte[] huffmanBytes) {
        StringBuilder contentHuffmanCodeStr = new StringBuilder();
        for (int i = 0; i < huffmanBytes.length; i++) {
            byte huffmanByte = huffmanBytes[i];

            // 是最后一个 byte，则不需要补位
            boolean flag = (i == huffmanBytes.length - 1); // 如果是最后一个，不需要补位
            contentHuffmanCodeStr.append(byteToBitString(!flag, huffmanByte));
        }
        return contentHuffmanCodeStr.toString();
    }

    @Test
    public void unzipTest() {
        // 由于需要使用构建时的码表，这里先构建一次，把码表拿到
        // 至于这个解压的码表 是如何保留的，这个视频中暂时没有说明
        String content = "i like like like java do you like a java";
//        String content = "我是一个中国人，我热爱中国";
        byte[] contentBytes = content.getBytes(); // 会使用所在系统的默认字符集构建 bytes，一般是 UTF-8

        // 1. 构建 nodes 列表
        List<Node> nodes = buildNodes(contentBytes);

        // 2. 对列表进行赫夫曼树的构建
        Node node = createHuffmanTree(nodes);

        // 3. 基于赫夫曼树生成 赫夫曼编码表
        Map<Byte, String> huffmanCodes = buildHuffmanCodes(node);

        // 4. 基于赫夫曼编码表，压缩内容
        byte[] huffmanCodeBytes = zip(contentBytes, huffmanCodes);

        // 5. 将压缩的内容进行解压，得到原始字符的 byte 数组
        byte[] unzipBytes = unzip(huffmanCodeBytes, huffmanCodes);
        String unzipStr = new String(unzipBytes);
        System.out.println("解压之后的字节数组：" + Arrays.toString(unzipBytes));
        System.out.println("解压之后的字节数组长度：" + unzipBytes.length);  // 如果正常的话，则为 40
        System.out.println("解压之后的原始字符串：" + unzipStr);
        System.out.println("压缩前和解压之后的字符串是否一致：" + content.equals(unzipStr));
    }

    /**
     * 解压
     *
     * @param huffmanCodeBytes 压缩之后的直接数组
     * @param huffmanCodeMap   介于构建时的码表 进行解压
     * @return 返回解压之后的字节数组
     */
    private byte[] unzip(byte[] huffmanCodeBytes, Map<Byte, String> huffmanCodeMap) {
        // 1. 获得码表的转换，反查的时候，要通过权值路径，查找字符串
        Map<String, Byte> codeMap = new HashMap<>();
        huffmanCodeMap.forEach((key, value) -> {
            codeMap.put(value, key);
        });
        // 2. 获得赫夫曼编码字符串
        String huffmanCodeStr = unzipBytesToHuffmanCodeStr(huffmanCodeBytes);
        // 3. 挨个解析，然后从码表中获取

        List<Byte> results = new ArrayList<>();
        // 注意这个循环没有步长
        // 由内部的 while 循环找到一个字符串之后，将 i 重置到下一个开始的字符处
        for (int i = 0; i < huffmanCodeStr.length(); ) {
            int count = 1;
            boolean flag = true;
            Byte aByte = null;
            while (flag) {
                // 1010100010111111110
                // i = 0 时：截取的是 1
                // i = 1 时：截取的是 0
                String key = huffmanCodeStr.substring(i, i + count);
                aByte = codeMap.get(key);
                if (aByte != null) {
                    // 匹配到一个，则退出这次匹配
                    flag = false;
                } else {
                    // 如果当次没有匹配到，则将 count+1，让下一次循环可以往后截取一个字符串
                    // 假设第一次：截取了 1 ，没有匹配
                    // 下一次，将截取到的字符串是 10，依次类推
                    count++;
                }
            }
            // 将 i 重置到下一个字符串开始的位置
            i = i + count;
            results.add(aByte);
        }
        byte[] bytes = new byte[results.size()];
        for (int i = 0; i < results.size(); i++) {
            bytes[i] = results.get(i);
        }
        return bytes;
    }

    /**
     * 文件压缩
     */
    @Test
    public void fileZipTest() throws IOException {
        // 要压缩的文件
        Path srcPath = Paths.get("/Users/mrcode/Desktop/1.png");
        // 压缩之后的存放路径
        Path distPath = Paths.get("/Users/mrcode/Desktop/1.png.zip");
        fileZip(srcPath, distPath);

        long srcSize = Files.size(srcPath);
        long distSize = Files.size(distPath);
        System.out.println("原始文件大小：" + srcSize / 1024);
        System.out.println("压缩文件大小：" + distSize / 1024);
        System.out.println("压缩比为 " + ((double) (srcSize - distSize) / srcSize) * 100 + "%");
    }

    /**
     * 压缩文件
     *
     * @param srcPath  源文件
     * @param distPath 压缩之后存放路径
     */
    private void fileZip(Path srcPath, Path distPath) {
        try {
            // 拿到源文件的字节数组
            byte[] scrPytes = Files.readAllBytes(srcPath);

            // 1. 构建 nodes 列表
            List<Node> nodes = buildNodes(scrPytes);

            // 2. 对列表进行赫夫曼树的构建
            Node node = createHuffmanTree(nodes);

            // 3. 基于赫夫曼树生成 赫夫曼编码表
            Map<Byte, String> huffmanCodes = buildHuffmanCodes(node);

            // 4. 基于赫夫曼编码表，压缩内容
            byte[] huffmanCodeBytes = zip(scrPytes, huffmanCodes);

            // 写入目标文件
            try (
                    OutputStream outputStream = Files.newOutputStream(distPath);
                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            ) {
                // 这里使用对象流来方便的写入我们的数据
                // 1. 先写入文件数据
                objectOutputStream.writeObject(huffmanCodeBytes);
                // 2. 再写入码表数据
                objectOutputStream.writeObject(huffmanCodes);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void fileUnzipTest() throws IOException {
        // 压缩文件地址
        Path zipPath = Paths.get("/Users/mrcode/Desktop/1.png.zip");
        // 解压之后的存放路径
        Path distPath = Paths.get("/Users/mrcode/Desktop/1.unzip.png");
        fileUnzip(zipPath, distPath);

        // 原始文件
        Path srcPath = Paths.get("/Users/mrcode/Desktop/1.png");
        long srcSize = Files.size(srcPath);
        long distSize = Files.size(distPath);
        System.out.println("原始文件大小：" + srcSize / 1024);
        System.out.println("解压文件大小：" + distSize / 1024);
        System.out.println("是否相等：" + (srcSize == distSize));
    }

    /**
     * 将压缩文件解压
     *
     * @param zipPath  压缩文件地址
     * @param distPath 解压后存放地址
     */
    public void fileUnzip(Path zipPath, Path distPath) {
        try (
                InputStream in = Files.newInputStream(zipPath);
                // 包装成对象流，读入之前写入的码表等数据
                ObjectInputStream ois = new ObjectInputStream(in);
        ) {
            // 读取数据
            byte[] huffmanCodeBytes = (byte[]) ois.readObject();
            // 读取码表
            Map<Byte, String> huffmanCodes = (Map<Byte, String>) ois.readObject();
            byte[] bytes = unzip(huffmanCodeBytes, huffmanCodes);

            // 将还原后的数据写出到文件
            Files.write(distPath, bytes);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
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
