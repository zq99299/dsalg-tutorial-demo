package cn.mrcode.study.dsalgtutorialdemo.datastructure.graph;

import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 邻接矩阵 图
 */
public class GraphTest {
    @Test
    public void graphTest() {
        int n = 5;
        String vertexValue[] = {"A", "B", "C", "D", "E"};
        Grap grap = new Grap(n);
        for (String value : vertexValue) {
            grap.insertVertex(value);
        }
        // 设置顶点关系

        /*
        // A 与 C、B 直连
        grap.insertEdge(0, 1, 1);
        grap.insertEdge(0, 2, 1);

        // B 与 C、A、E、D 直连
        grap.insertEdge(1, 0, 1);
        grap.insertEdge(1, 2, 1);
        grap.insertEdge(1, 3, 1);
        grap.insertEdge(1, 4, 1);

        // C 与 B、A 直连
        grap.insertEdge(2, 0, 1);
        grap.insertEdge(2, 1, 1);

        // D 与 B 直连
        grap.insertEdge(3, 1, 1);

        // E 与 B 直连
        grap.insertEdge(4, 1, 1);
         */
        // 上面这种写法是双向的，由于内部已经处理过双向边了，所以只需要设置 5 条单向的即可
        // a,b  a,c  b,c  b,d  b,e
        grap.insertEdge(0, 1, 1);
        grap.insertEdge(0, 2, 1);
        grap.insertEdge(1, 2, 1);
        grap.insertEdge(1, 3, 1);
        grap.insertEdge(1, 4, 1);

        grap.showGraph();
        System.out.println("边：" + grap.getNumOfEdges());
        System.out.println("下标 1:" + grap.getValueByIndex(1));
    }

    /**
     * 图的深度优先遍历
     */
    @Test
    public void dfsTest() {
        int n = 5;
        String vertexValue[] = {"A", "B", "C", "D", "E"};
        Grap grap = new Grap(n);
        for (String value : vertexValue) {
            grap.insertVertex(value);
        }
        // a,b  a,c  b,c  b,d  b,e
        grap.insertEdge(0, 1, 1);
        grap.insertEdge(0, 2, 1);
        grap.insertEdge(1, 2, 1);
        grap.insertEdge(1, 3, 1);
        grap.insertEdge(1, 4, 1);
        grap.showGraph();

        System.out.println();
        grap.dfs();
    }

    /**
     * 图的广度优先遍历
     */
    @Test
    public void bfsTest() {
        int n = 5;
        String vertexValue[] = {"A", "B", "C", "D", "E"};
        Grap grap = new Grap(n);
        for (String value : vertexValue) {
            grap.insertVertex(value);
        }
        // a,b  a,c  b,c  b,d  b,e
        grap.insertEdge(0, 1, 1);
        grap.insertEdge(0, 2, 1);
        grap.insertEdge(1, 2, 1);
        grap.insertEdge(1, 3, 1);
        grap.insertEdge(1, 4, 1);
        grap.showGraph();

        System.out.println();
        grap.bsf();
    }


    /**
     * 构建数量较多的点的 图
     *
     * @return
     */
    private Grap buildGrap2() {
        String vertexValue[] = {"1", "2", "3", "4", "5", "6", "7", "8"};
        Grap grap = new Grap(vertexValue.length);
        for (String value : vertexValue) {
            grap.insertVertex(value);
        }
        // a,b  a,c  b,c  b,d  b,e
        grap.insertEdge(0, 1, 1);
        grap.insertEdge(0, 2, 1);
        grap.insertEdge(1, 3, 1);
        grap.insertEdge(1, 4, 1);
        grap.insertEdge(3, 7, 1);
        grap.insertEdge(4, 7, 1);
        grap.insertEdge(2, 5, 1);
        grap.insertEdge(2, 6, 1);
        grap.insertEdge(5, 6, 1);
        return grap;
    }

    /**
     * 图的深度优先遍历：点数量较多的测试
     */
    @Test
    public void dfsTest2() {
        Grap grap = buildGrap2();
        grap.showGraph();

        System.out.println();
        grap.dfs();
    }

    /**
     * 图的广度优先遍历：点数量较多的测试
     */
    @Test
    public void bfsTest2() {
        Grap grap = buildGrap2();
        grap.showGraph();

        System.out.println();
        grap.bsf();
    }

    class Grap {
        /**
         * 存放所有的顶点
         */
        private List<String> vertexs;
        /**
         * 矩阵：存放边的关系（顶点之间的关系）
         */
        private int[][] edges;
        /**
         * 存放有多少条边
         */
        private int numOfEdges = 0;
        /**
         * 存放顶点是否已经访问过，下标对应顶点插入列表的下标
         */
        private boolean isVisiteds[];

        /**
         * @param n 有几个顶点
         */
        public Grap(int n) {
            vertexs = new ArrayList<>(n);
            edges = new int[n][n];
            isVisiteds = new boolean[n];
        }
        /*
         *=============
         * 有两个核心方法：插入顶点，设置边的关系
         */

        /**
         * 插入顶点
         *
         * @param vertex
         */
        public void insertVertex(String vertex) {
            vertexs.add(vertex);
        }

        /**
         * 添加边的关系
         *
         * @param v1     第一个顶点对应的矩阵下标
         * @param v2     第二个顶点对应的矩阵下标
         * @param weight 他们之间的关系：0|不直连，1|直连
         */
        public void insertEdge(int v1, int v2, int weight) {
            edges[v1][v2] = weight;
            // 由于是无向图，反向也可以连通
            edges[v2][v1] = weight;
            numOfEdges++;  // 边增加 1
        }

        /*
         *=============
         * 下面写几个图的常用方法
         */

        /**
         * 获取顶点的数量
         */
        public int getNumOfVertex() {
            return vertexs.size();
        }

        /**
         * 获取边的数量
         *
         * @return
         */
        public int getNumOfEdges() {
            return numOfEdges;
        }

        /**
         * 根据下标获得顶点的值
         *
         * @param i
         * @return
         */
        public String getValueByIndex(int i) {
            return vertexs.get(i);
        }

        /**
         * 显示图的矩阵
         */
        public void showGraph() {
            System.out.printf("  ");
            for (String vertex : vertexs) {
                System.out.printf(vertex + " ");
            }
            System.out.println();
            for (int i = 0; i < edges.length; i++) {
                System.out.printf(vertexs.get(i) + " ");
                for (int j = 0; j < edges.length; j++) {
                    System.out.printf(edges[i][j] + " ");
                }
                System.out.println();
            }
        }

        /**
         * 深度遍历
         */
        public void dfs() {
            for (int i = 0; i < vertexs.size(); i++) {
                // 如果已经访问过，则跳过
                if (isVisiteds[i]) {
                    continue;
                }
                // 没有访问过，则以此节点为基础进行深度遍历
                dfs(i);
            }
        }

        /**
         * 深度优先遍历
         *
         * @param i 当前是以，顶点插入列表中的哪一个顶点进行深度优先查找
         */
        public void dfs(int i) {
            // 输出自己，并标记为已访问过
            System.out.print(vertexs.get(i) + " -> ");
            isVisiteds[i] = true;

            // 查找此节点的第一个邻接节点
            int w = getFirstNeighbor(i);
            // 如果找到了 w ，则对 w 进行深度优先遍历
            while (w != -1) {
                // 已经访问过，
                if (isVisiteds[w]) {
                    w = getNextNeighbor(i, w);
                } else {
                    dfs(w);
                }
            }
        }

        /**
         * 查找第一个邻接节点
         *
         * @param i
         * @return 如果找到，则返回具体的下标
         */
        private int getFirstNeighbor(int i) {
            for (int j = i; j < vertexs.size(); j++) {
                if (edges[i][j] == 1) {
                    return j;
                }
            }
            return -1;
        }

        /**
         * 如果 w 节点被访问过，则查找 i 节点的下一个 邻接节点（就不是第一个节点了）
         *
         * @param i
         * @param w
         * @return
         */
        private int getNextNeighbor(int i, int w) {
            for (int j = w + 1; j < vertexs.size(); j++) {
                if (edges[i][j] == 1) {
                    return j;
                }
            }
            return -1;
        }

        /**
         * 对整个节点进行 广度优先 遍历
         */
        public void bsf() {
            for (int i = 0; i < vertexs.size(); i++) {
                // 如果已经访问过，则跳过
                if (isVisiteds[i]) {
                    continue;
                }
                System.out.println("新的节点广度优先");  // 换行 1
                // 没有访问过，则以此节点为基础进行深度遍历
                bsf(i);
            }
        }

        /**
         * 对单个节点为初始节点，进行广度优先遍历
         *
         * @param i
         */
        private void bsf(int i) {
            // 访问该节点，并标记已经访问
            System.out.print(getValueByIndex(i) + " → ");
            isVisiteds[i] = true;

            // 将访问过的添加到队列中
            LinkedList<Integer> queue = new LinkedList<>();
            queue.addLast(i); // 添加到末尾

            int u; // 队列头的节点
            int w; // u 的下一个邻接节点
            // 当队列不为空的时候，查找节点 u 的第一个邻接节点 w
            while (!queue.isEmpty()) {
                System.out.println(""); // 换行 2
                u = queue.removeFirst();
                w = getFirstNeighbor(u);
                // w 存在的话
//                while (w != -1) {
//                    // 如果 w 已经被访问过
//                    if (isVisiteds[w]) {
//                        // 则：以 u 为初始节点，查找 w 的下一个邻接节点
//                        w = getNextNeighbor(u, w);
//                    }
//                    // 如果 w 没有被访问过，则访问它，并标记已经访问
//                    else {
//                        System.out.print(getValueByIndex(w) + " → ");
//                        isVisiteds[w] = true;
//                        queue.addLast(w); // 访问过的一定要入队列
//                    }
//                }
                // 上面这样写，容易阅读，但是会存在多一次循环的问题，改写成下面这样
                while (w != -1) {
                    // 如果没有被访问过，则访问，并标记为已经访问过
                    if (!isVisiteds[w]) {
                        System.out.print(getValueByIndex(w) + " → ");
                        isVisiteds[w] = true;
                        queue.addLast(w); // 访问过的一定要入队列
                    }
                    // 上面访问之后，就需要获取该节点的下一个节点
                    // 否则，下一次还会判断一次 w，然后去获取下一个节点，只获取，但是没有进行访问相关操作
                    // 相当于每个节点都会循环两次，这里减少到一次
                    w = getNextNeighbor(u, w);
                }
            }
        }
    }
}
