package cn.mrcode.study.dsalgtutorialdemo.algorithm.dijkstra;

import org.junit.Test;

import java.util.Arrays;

/**
 * 迪杰斯特拉算法-最短路径问题
 */
public class DijkstraAlgorithm {
    // 不连通的默认值
    int N = 100000;

    /**
     * 图：首先需要有一个带权的连通无向图
     */
    class MGraph {
        int vertex;  // 顶点个数
        int[][] weights;  // 邻接矩阵
        char[] datas; // 村庄数据
        int edgeNum; // 共有多少条边

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
            // 计算有多少条边
            for (int i = 0; i < weights.length; i++) {
                /*
                        A       B       C       D       E       F       G
                A       0       12      100000  100000  100000  16      14
                B       12      0       10      100000  100000  7       100000
                j = i + 1：比如:
                        i=0,j=1, 那么就是 A,B 从而跳过了 A,A
                        i=1,j=2, 那么就是 B,C 从而跳过了 B,A  B,B
                        那么含义就出来了：跳过双向边的统计，也跳过自己对自己值得为 0 的统计
                 */
                for (int j = i + 1; j < weights.length; j++) {
                    if (weights[i][j] != N) {
                        edgeNum++;
                    }
                }
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
        int[][] weights = new int[][]{
                //     A  B  C  D  E  F  G
                /*A*/ {N, 5, 7, N, N, N, 2},
                /*B*/ {5, N, N, 9, N, N, 3},
                /*C*/ {7, N, N, N, 8, N, N},
                /*D*/ {N, 9, N, N, N, 4, N},
                /*E*/ {N, N, 8, N, N, 5, 4},
                /*F*/ {N, N, N, 4, 5, N, 6},
                /*G*/ {2, 3, N, N, 4, 6, N}
        };
        MGraph mGraph = new MGraph(7, weights);
        mGraph.show();
        System.out.printf("共有 %d 条边 \n", mGraph.edgeNum);
    }

    /**
     * 从 G 出发
     */
    @Test
    public void dijkstraTest() {
        int[][] weights = new int[][]{
                //     A  B  C  D  E  F  G
                /*A*/ {N, 5, 7, N, N, N, 2},
                /*B*/ {5, N, N, 9, N, N, 3},
                /*C*/ {7, N, N, N, 8, N, N},
                /*D*/ {N, 9, N, N, N, 4, N},
                /*E*/ {N, N, 8, N, N, 5, 4},
                /*F*/ {N, N, N, 4, 5, N, 6},
                /*G*/ {2, 3, N, N, 4, 6, N}
        };
        MGraph mGraph = new MGraph(7, weights);
        mGraph.show();
        System.out.printf("共有 %d 条边 \n", mGraph.edgeNum);

        dijkstra(mGraph, 'G');
    }

    // 记录各个顶点是否访问过
    private boolean[] already_arr;
    // 记录每个下标对应的值为前一个顶点下标（前驱节点）
    private int[] pre_visited_arr;
    // 记录出发点到其他所有顶点的距离
    private int[] dis_arr;
    private MGraph mGraph;

    private void dijkstra(MGraph mGraph, char start) {
        this.mGraph = mGraph;
        // 三个数组的长度为 顶点的个数
        already_arr = new boolean[mGraph.vertex];
        pre_visited_arr = new int[mGraph.vertex];
        dis_arr = new int[mGraph.vertex];
        // 找到开始节点的下标
        int v = 0;
        for (int i = 0; i < mGraph.datas.length; i++) {
            if (mGraph.datas[i] == start) {
                v = i;
                break;
            }
        }
        // 初始化所有前驱节点为默认状态，使用不可连通的 N 值表示
        Arrays.fill(pre_visited_arr, N);
        // 标记开始节点为访问状态
        already_arr[v] = true;
        // 标记前驱节点为：笔记中有歧义，我们使用 N 表示没有前驱节点
        // v 是开始节点，那么它就没有前驱节点
        pre_visited_arr[v] = N;
        // 初始化从起点到到所有点的距离为最大值，后续方便通过它来与新路径距离比较
        Arrays.fill(dis_arr, N);
        // 初始化，当前访问节点的距离为 0
        dis_arr[v] = 0;

        // 准备工作完成：开始查找最短路径
        // 广度优先策略：从起始节点计算它能直达的点的所有距离
        update(v);
        // 一共只需要计算 6 层： 7 个站点 -1
        for (char data : mGraph.datas) {
            // 寻找下一个访问节点
            int index = findNext();
            // 标记该节点被访问过，然后再计算与它直连点的路径
            already_arr[index] = true;
            // 并继续计算路径
            update(index);
        }

        // 所有节点都访问过之后：dis_arr 中就保留了从起点 到各个点的最短距离
        System.out.println(Arrays.toString(already_arr));
        System.out.println(Arrays.toString(pre_visited_arr));
        System.out.println(Arrays.toString(dis_arr));

        System.out.println("从 " + start + " 到以下点的最短距离为：");
        // 为了结果好认一点，格式化最后的结果
        for (int i = 0; i < dis_arr.length; i++) {
            System.out.printf("%S(%d) ", mGraph.datas[i], dis_arr[i]);
        }
        System.out.println();
    }

    /**
     * 计算起点到：当前节点所有能直连的节点的距离
     *
     * @param v
     */
    private void update(int v) {
        int[][] weights = mGraph.weights; // 我们的邻接矩阵图

        int len = 0;
        // weights[v]：由于是广度优先，所以每次只计算与该点能直连的点，也就是该点所在的一行
        for (int i = 0; i < weights[v].length; i++) {
            if (weights[v][i] == N) { // 不能直连，跳过
                continue;
            }
            // 计算从起点到当前要连通节点的距离   = 起点到当前访问节点的距离 + 访问节点到直连节点的距离
            len = dis_arr[v] + weights[v][i];

            // 首先：起点G -> A, A 要没有被访问过
            // 其次：如果当前计算新的路径距离 小于 已经存在的 从 起点 G -> 当前计算点的距离
            //      说明之前可能从其他途径到达了 i 点，这个距离是比现在找到的距离远
            // 当前的近，那么就更新数组中的数据
            if (!already_arr[i] && len < dis_arr[i]) {
                dis_arr[i] = len;
                pre_visited_arr[i] = v; // 更改前驱节点，表示 经过了 v 这个点（当前正在访问的点），到达的 i 点
            }
        }
    }

    /**
     * 广度优先策略一层计算完成之后，寻找下一个节点再计算
     *
     * @return
     */
    private int findNext() {
        int min = N, index = 0;
        // 总共需要找 n 此
        for (int i = 0; i < already_arr.length; i++) {
            // 该节点没有被访问过
            // 并且：从起点到达该节点的距离是最小的
            // 如果是第一层执行完成之后：那么有值的则只有：与起点能直连的那几个
            //      这里就类似与：原来广度优先中使用队列来保存搜索路径了
            if (!already_arr[i] && dis_arr[i] < min) {
                min = dis_arr[i];
                index = i;
            }
        }
        return index;
    }

    /**
     * 从 c 出发
     */
    @Test
    public void dijkstraTest2() {
        int[][] weights = new int[][]{
                //     A  B  C  D  E  F  G
                /*A*/ {N, 5, 7, N, N, N, 2},
                /*B*/ {5, N, N, 9, N, N, 3},
                /*C*/ {7, N, N, N, 8, N, N},
                /*D*/ {N, 9, N, N, N, 4, N},
                /*E*/ {N, N, 8, N, N, 5, 4},
                /*F*/ {N, N, N, 4, 5, N, 6},
                /*G*/ {2, 3, N, N, 4, 6, N}
        };
        MGraph mGraph = new MGraph(7, weights);
        mGraph.show();
        System.out.printf("共有 %d 条边 \n", mGraph.edgeNum);

        dijkstra(mGraph, 'C');
    }
}
