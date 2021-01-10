package cn.mrcode.study.dsalgtutorialdemo.algorithm.floyd;

import org.junit.Test;

import java.util.Arrays;

/**
 * 佛洛依德算法-最短路径
 */
public class FloydAlgorithm {
    /**
     * 图：首先需要有一个带权的连通无向图
     */
    class MGraph {
        int vertex;  // 顶点个数
        int[][] weights;  // 邻接矩阵
        char[] datas; // 村庄数据

        /**
         * @param vertex  村庄数量， 会按照数量，按顺序生成村庄，如 A、B、C...
         * @param weights 需要你自己定义好那些点是连通的，那些不是连通的
         */
        public MGraph(int vertex, int[][] weights) {
            this.vertex = vertex;
            this.weights = weights;

            this.datas = new char[vertex];
            for (int i = 0; i < vertex; i++) {
                // 大写字母 A 从 65 开始
                datas[i] = (char) (65 + i);
            }
        }

        public void show() {
            System.out.printf("%-8s", " ");
            for (char vertex : datas) {
                // 控制字符串输出长度：少于 8 位的，右侧用空格补位
                System.out.printf("%-8s", vertex + " ");
            }
            System.out.println();
            for (int i = 0; i < weights.length; i++) {
                System.out.printf("%-8s", datas[i] + " ");
                for (int j = 0; j < weights.length; j++) {
                    System.out.printf("%-8s", weights[i][j] + " ");
                }
                System.out.println();
            }
        }
    }

    @Test
    public void mGraphTest() {
        // 不连通的默认值：
        // 这里设置为较大的数，是为了后续的计算方便，计算权值的时候，不会选择
        int defaultNo = 100000;
        int[][] weights = new int[][]{
                {defaultNo, 5, 7, defaultNo, defaultNo, defaultNo, 2},    // A
                {5, defaultNo, defaultNo, 9, defaultNo, defaultNo, 3},// B
                {7, defaultNo, defaultNo, defaultNo, 8, defaultNo, defaultNo},// C
                {defaultNo, 9, defaultNo, defaultNo, defaultNo, 4, defaultNo},// D
                {defaultNo, defaultNo, 8, defaultNo, defaultNo, 5, 4},// E
                {defaultNo, defaultNo, defaultNo, 4, 5, defaultNo, 6},// F
                {2, 3, defaultNo, defaultNo, 4, 6, defaultNo}// G
        };
        MGraph mGraph = new MGraph(7, weights);
        mGraph.show();
    }

    int defaultNo = 100000;

    @Test
    public void floydTest() {
        int[][] weights = new int[][]{
                {defaultNo, 5, 7, defaultNo, defaultNo, defaultNo, 2},    // A
                {5, defaultNo, defaultNo, 9, defaultNo, defaultNo, 3},// B
                {7, defaultNo, defaultNo, defaultNo, 8, defaultNo, defaultNo},// C
                {defaultNo, 9, defaultNo, defaultNo, defaultNo, 4, defaultNo},// D
                {defaultNo, defaultNo, 8, defaultNo, defaultNo, 5, 4},// E
                {defaultNo, defaultNo, defaultNo, 4, 5, defaultNo, 6},// F
                {2, 3, defaultNo, defaultNo, 4, 6, defaultNo}// G
        };
        MGraph mGraph = new MGraph(7, weights);
        mGraph.show();
        floyd(mGraph);

        showFloydDis();
        showFloydPre();
        showFormat();
    }

    private char[] vertexs; // 存放顶点
    private int[][] dis; // 从各个顶点出发到其他顶点的距离
    private int[][] pre; // 到达目标顶点的前驱顶点

    public void floyd(MGraph mGraph) {
        vertexs = mGraph.datas;
        dis = mGraph.weights;
        pre = new int[mGraph.vertex][mGraph.vertex];
        // 初始化 pre
        for (int i = 0; i < pre.length; i++) {
            Arrays.fill(pre[i], i);
        }

        // 从中间顶点的遍历
        for (int i = 0; i < vertexs.length; i++) {
            // 出发顶点
            for (int j = 0; j < vertexs.length; j++) {
                // 终点
                for (int k = 0; k < vertexs.length; k++) {
                    // 中间节点连接: 从 j 到 i 到 k 的距离
                    int lji = dis[j][i];
                    int lik = dis[i][k];
                    int leng = lji + lik;

                    // 直连
                    int ljk = dis[j][k];

                    // 如果间接距离比直连短，则更新
                    if (leng < ljk) {
                        dis[j][k] = leng;
                        /*
                         最难理解的是这里：
                           i 是已知的中间节点，前驱的时候直接设置为 i (pre[j][k] = i;) ，结果是不对的。
                           比如：A-G-F-D ， 中间节点是是 两个节点，那么 A 到 D 的前驱节点是 F，而不是 G
                           如果直接赋值 i，前驱节点就会计算错误。
                           理解步骤为：
                            1. A-G-F：距离 8
                               A-F  : 不能直连
                               那么设置：A,F 的前驱节点是 G; 对应这里的代码是 j,i
                            2. G-F-D: 距离是 10
                               G-D：不能直连
                               那么设置：G,D 的前驱节点是 F; 对应这里的代码是 i,k
                            3. 那么最终 A,D 的前驱节点是是什么呢？
                               其实就应该是 G,D 指向的值; 对应这里的代码是 i,k
                         */
                        pre[j][k] = pre[i][k]; // 前驱节点更新为中间节点
                    }
                }
            }
        }
    }

    /**
     * 显示 dis 和 pre，这个数据也是最后的结果数据
     */
    public void showFloydDis() {
        System.out.println("dis 结果");
        show(dis);
    }

    public void showFloydPre() {
        System.out.println("pre 结果");
        show(pre);
    }

    public void show(int[][] weights) {
        System.out.printf("%-8s", " ");
        for (char vertex : vertexs) {
            // 控制字符串输出长度：少于 8 位的，右侧用空格补位
            System.out.printf("%-8s", vertex + " ");
        }
        System.out.println();
        for (int i = 0; i < weights.length; i++) {
            System.out.printf("%-8s", vertexs[i] + " ");
            for (int j = 0; j < weights.length; j++) {
                System.out.printf("%-8s", weights[i][j] + " ");
            }
            System.out.println();
        }
    }

    /**
     * 直接打印出我们的结果
     */
    public void showFormat() {
        System.out.println("最终结果格式化显示：");
        for (int i = 0; i < dis.length; i++) {
            // 先将 pre 数组输出一行
            System.out.println(vertexs[i] + " 到其他顶点的最短距离");
            // 输出 dis 数组的一行数据
            // 每一行数据是，一个顶点，到达其他顶点的最短路径
            for (int k = 0; k < dis.length; k++) {
                System.out.printf("%-16s", vertexs[i] + " → " + vertexs[k] + " = " + dis[i][k] + "");
            }
            System.out.println();
            System.out.println();
        }
    }
}
