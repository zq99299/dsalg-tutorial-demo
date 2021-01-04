package cn.mrcode.study.dsalgtutorialdemo.algorithm.prim;

import org.junit.Test;


/**
 * 普利姆算法
 */
public class PrimAlgorithm {
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


    /**
     * 最小生成树
     */
    class MinTree {
        /**
         * 普利姆算法
         *
         * @param mGraph 无向图
         * @param v      从哪一个点开始生成
         */
        public void prim(MGraph mGraph, int v) {
            int minTotalWeight = 0; // 记录已选择的边的总权值，仅仅只是为了测试打印验证

            // 记录已经选择过的节点
            boolean[] selects = new boolean[mGraph.vertex];
            // 从哪个节点开始，则标识已经被访问
            selects[v] = true;

            // 一共要生成 n-1 条边
            for (int i = 1; i < mGraph.vertex; i++) {
                // 每次循环：选择一条权值最小的边出来

                // 记录最小值
                int minWeight = 10000;
                int x = -1;
                int y = -1;

                // 每次查找权值最小的边：根据思路，每次都是从已经选择过的点，中去找与该点直连的点的权值
                // 并且该点还没有被选择过：如果两个点都被选择过，要么他们是双向的，要么就是被其他的点选择过了
                // 这里双循环的含义：建议对照笔记中步骤分析理解
                for (int j = 0; j < mGraph.vertex; j++) {
                    for (int k = 0; k < mGraph.vertex; k++) {
                        // 通过 j 来限定已经选择过的点
                        // 通过 k 来遍历匹配，没有选择过的点
                        // 假设第一轮是 A 点：j = 0
                        // 那么在这里将会执行 0,1  0,2, 0,3 也就是与 A 点直连，且没有被选择过的点，的最小权值
                        if (selects[j] && !selects[k]
                                && mGraph.weights[j][k] < minWeight
                        ) {
                            // 记录最小权值，与这条边的信息
                            minWeight = mGraph.weights[j][k];
                            x = j;
                            y = k;
                        }
                    }
                }
                // 当一次循环结束时：就找到了一条权值最小的边
                System.out.printf("%s,%s [%s] \n", mGraph.datas[x], mGraph.datas[y], mGraph.weights[x][y]);
                minTotalWeight += mGraph.weights[x][y]; // 统计已选择边权值

                minWeight = 10000;
                // 记录该点已经被选择
                // 在查找最小权值边的代码中：y=k, k 表示没有被选择过的点，所以，找到之后，这里记录 y 为这条边的另一个点
                selects[y] = true;
            }
            System.out.println("总权值：" + minTotalWeight);
        }
    }

    @Test
    public void primTest() {
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

        MinTree minTree = new MinTree();
        minTree.prim(mGraph, 0);
        minTree.prim(mGraph, 1);

    }
}
