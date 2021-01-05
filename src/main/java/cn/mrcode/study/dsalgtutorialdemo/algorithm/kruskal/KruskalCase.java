package cn.mrcode.study.dsalgtutorialdemo.algorithm.kruskal;

import org.junit.Test;

import java.util.Arrays;
import java.util.Comparator;

/**
 * 克鲁斯卡尔算法
 */
public class KruskalCase {
    // 不连通的默认值：0 则代表同一个点
    int INF = 100000;

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
                    if (weights[i][j] != INF) {
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
                //     A    B    C    D    E    F   G
                /*A*/ {0, 12, INF, INF, INF, 16, 14},
                /*B*/ {12, 0, 10, INF, INF, 7, INF},
                /*C*/ {INF, 10, 0, 3, 5, 6, INF},
                /*D*/ {INF, INF, 3, 0, 4, INF, INF},
                /*E*/ {INF, INF, 5, 4, 0, 2, 8},
                /*F*/ {16, 07, INF, INF, 2, 0, 9},
                /*G*/ {14, INF, INF, INF, 8, 9, INF}
        };
        MGraph mGraph = new MGraph(7, weights);
        mGraph.show();
        System.out.printf("共有 %d 条边", mGraph.edgeNum);
    }

    /**
     * 将无向图中的边 转换成对象数组
     *
     * @param graph
     * @return
     */
    public Edata[] convertEdatas(MGraph graph) {
        Edata[] datas = new Edata[graph.edgeNum];
        int[][] weights = graph.weights;
        char[] vertexs = graph.datas;
        int index = 0;
        for (int i = 0; i < weights.length; i++) {
            for (int j = i + 1; j < weights.length; j++) {
                if (weights[i][j] != INF) {
                    datas[index++] = new Edata(vertexs[i], vertexs[j], weights[i][j]);
                }
            }
        }
        return datas;
    }

    /**
     * 将边按权值从小到大排序
     *
     * @param edata
     */
    public void sort(Edata[] edata) {
        Arrays.sort(edata, Comparator.comparingInt(o -> o.weight));
    }

    public Edata[] kruskal(MGraph mGraph, Edata[] edatas) {
        // 存放结果，数组最大容量为所有边的容量
        Edata[] rets = new Edata[mGraph.edgeNum];
        int retsIndex = 0;

        /*
          按照算法思路：
            记录顶点在 **最小生成树** 中的终点，顶点的终点是 **在最小生成树中与它连通的最大顶点**。
            然后每次需要将一条边添加到最小生存树时，判断该边的两个顶点的终点是否重合，重合的话则会构成回路。
         */
        // 用于存所有的终点：该数组中的内容随着被选择的边增加，终点也会不断的增加
        int[] ends = new int[mGraph.edgeNum];
        // 对所有边进行遍历
        for (Edata edata : edatas) {
            // 获取这两条边的顶点下标:
            //  第一次：E,F  ->  4,5
            int p1 = getPosition(mGraph.datas, edata.start);
            int p2 = getPosition(mGraph.datas, edata.end);

            // 获取对应顶点的 终点
            /*
              第 1 次：E,F  ->  4,5
                ends = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]
                m: 获取 4 的终点：ends[4] 为 0，说明此点 还没有一个终点，那么就返回它自己 4
                n: 获取 5 的终点：同上
                m != n , 选择这一条边。那么此时 E,F  ->  4,5 已有边的终点就是 5
                ends = [0, 0, 0, 0, 5, 0, 0, 0, 0, 0, 0, 0]
                终点表中读法：         ↑ index=4,value=5 那么表示 4 这个顶点的终点为 5
              第 2 次：C,D  ->  2,3
                ends = [0, 0, 0, 0, 5, 0, 0, 0, 0, 0, 0, 0]
                 m: 获取 2 的终点，ends[2] = 0，说明此点 还没有一个终点，则返回它自己 2
                 n: 获取 3 的终点
                 m != n , 选择这一条边。那么此时 C,D  ->  2,3 已有边的终点就是 3
                 ends = [0, 0, 3, 0, 5, 0, 0, 0, 0, 0, 0, 0]
              第 3 次：D,E  ->  3,4
                ends = [0, 0, 3, 0, 5, 0, 0, 0, 0, 0, 0, 0]
                 m: 获取 3 的终点，ends[3] = 0，说明此点 还没有一个终点，则返回它自己 3
                 n: 获取 4 的终点,!! 前面第一次，已经将 4 的终点 5 放进来了
                    那么将获取到的终点为 5，getEnd() 还会尝试去获取 5 的终点，发现为 0，则 4 的终点是 5
                 m != n -> 3 != 5 , 选择这一条边。那么此时 D,E  ->  3,4 已有边的终点就是 5
                 ends = [0, 0, 3, 5, 5, 0, 0, 0, 0, 0, 0, 0]
             */
            int m = getEnd(ends, p1);
            int n = getEnd(ends, p2);
            if (m != n) {
                ends[m] = n;
                rets[retsIndex++] = edata;
            }
        }
        return rets;
    }

    /**
     * 获取该顶点的：终点
     *
     * @param ends
     * @param vertexIndex
     * @return
     */
    private int getEnd(int[] ends, int vertexIndex) {
        int temp = vertexIndex;
        while (ends[temp] != 0) {
            temp = ends[temp];
        }
        return temp;
    }

    /**
     * 获取此顶点的下标
     *
     * @param vertexs
     * @param vertex
     * @return
     */
    private int getPosition(char[] vertexs, char vertex) {
        for (int i = 0; i < vertexs.length; i++) {
            if (vertexs[i] == vertex) {
                return i;
            }
        }
        return 0;
    }

    /**
     * 描述一条边
     */
    class Edata {
        // 一条边的开始和结束，比如 A,B
        char start;
        char end;
        int weight; // 这条边的权值

        public Edata(char start, char end, int weight) {
            this.start = start;
            this.end = end;
            this.weight = weight;
        }

        @Override
        public String toString() {
            return start + "," + end + " [" + weight + "]";
        }
    }

    @Test
    public void kruskalTest() {
        int[][] weights = new int[][]{
                //     A    B    C    D    E    F   G
                /*A*/ {0, 12, INF, INF, INF, 16, 14},
                /*B*/ {12, 0, 10, INF, INF, 7, INF},
                /*C*/ {INF, 10, 0, 3, 5, 6, INF},
                /*D*/ {INF, INF, 3, 0, 4, INF, INF},
                /*E*/ {INF, INF, 5, 4, 0, 2, 8},
                /*F*/ {16, 07, INF, INF, 2, 0, 9},
                /*G*/ {14, INF, INF, INF, 8, 9, INF}
        };
        MGraph mGraph = new MGraph(7, weights);
        mGraph.show();
        System.out.printf("共有 %d 条边 \n", mGraph.edgeNum);
        System.out.println("边数组为：");
        Edata[] edatas = convertEdatas(mGraph);
        printEdatas(edatas);
        System.out.println("排序后的边数组为：");
        sort(edatas);
        printEdatas(edatas);

        Edata[] kruskal = kruskal(mGraph, edatas);
        System.out.println("克鲁斯卡尔算法计算结果的边为：");
        printEdatas(kruskal);
        int total = Arrays.stream(kruskal).filter(item -> item != null).mapToInt(item -> item.weight).sum();
        System.out.println("总里程数为：" + total);
    }

    private void printEdatas(Edata[] edatas) {
        for (Edata edata : edatas) {
            if (edata == null) {
                continue;
            }
            System.out.println(edata);
        }
    }
}
