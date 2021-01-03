package cn.mrcode.study.dsalgtutorialdemo.algorithm.dynamic;

import org.junit.Test;

import java.util.Arrays;

/**
 * 动态规划
 */
public class KnapsackProblem {
    /**
     * 填表法：输出填表后的信息
     */
    @Test
    public void table() {
        // 商品价值和重量前面都有一个 0 ，方便后续的公式写法
        int[] val = {0, 1500, 3000, 2000};  // 商品价值
        int[] w = {0, 1, 4, 3}; // 商品对应重量
        int m = 4; // 背包容量
        int n = val.length; // 物品个数
        // 构建初始表格， +1 是因为有一行列和行都是 0
        // v[i][j] 存放的是：前 i 个物品中能够装入容量为 j 的背包中的最大价值
        int[][] v = new int[n][m + 1];

        // 1. 初始化  v[i][0] = v[0][j]=0
        //    本程序中，可以不初始化，默认就是 0
        //    为了体现步骤，进行初始化（有可能在你的场景中有其他的含义）
        // 为了与默认值 0 区分开，看出初始化效果，这里默 0 定义为 -1
        // 验证之后，修改回 0，否则在计算上一个格子的时候，就会导致计算错误
        int zero = 0;
        for (int i = 0; i < v.length; i++) {
            for (int j = 0; j < v[0].length; j++) {
                v[i][0] = zero; // 初始化第一列为 0
                v[0][i] = zero; // 初始化第一行为 0
            }
        }
        print(v);

        // 开始填表：动态规划处理
        for (int i = 1; i < v.length; i++) {  // 第一行 0 不处理，从 1 开始
            // 一个商品一个容量进行尝试
            for (int j = 1; j < v[0].length; j++) { // 第一列 0 不处理，从 1 开始
                // 当当前物品重量大于，背包限定重量时：
                //   当前背包容量，可存放最大的商品价值为：前一格子中的存放策略总价值
                if (w[i] > j) {
                    v[i][j] = v[i - 1][j];
                }
                // 当当前背包容量 大于 当前物品重量时：
                //      说明：还有空余空间存放其他产品
                else {
                    // max{v[i - 1][j],v[i - 1][j - w[i]] + v[i]}`
                    int pre = v[i - 1][j]; // 前一个格子中存放策略总价值
                    int curr = val[i]; // i当前商品总价值
                    int free = v[i - 1][j - w[i]];  // 存放完当前商品后，剩余空间能存放的总价值
                    // 当前格子：只会存放比上一个格子总价值大的策略价值
                    v[i][j] = Math.max(pre, curr + free);
                }
            }
        }
        System.out.println("动态规划后");
        print(v);
    }

    /**
     * 填表法：输出填表后的信息,包括最优存放的商品信息
     */
    @Test
    public void tableAndProduct() {
        // 商品价值和重量前面都有一个 0 ，方便后续的公式写法
        int[] val = {0, 1500, 3000, 2000};  // 商品价值
        int[] w = {0, 1, 4, 3}; // 商品对应重量
        int m = 4; // 背包容量
        int n = val.length; // 物品个数
        // 构建初始表格， +1 是因为有一行列和行都是 0
        // v[i][j] 存放的是：前 i 个物品中能够装入容量为 j 的背包中的最大价值
        int[][] v = new int[n][m + 1];

        int zero = 0;
        for (int i = 0; i < v.length; i++) {
            for (int j = 0; j < v[0].length; j++) {
                v[i][0] = zero; // 初始化第一列为 0
                v[0][i] = zero; // 初始化第一行为 0
            }
        }
        print(v);

        // 用于存放每个格子中保存的商品
        int[][] path = new int[n][m + 1];

        // 开始填表：动态规划处理
        for (int i = 1; i < v.length; i++) {  // 第一行 0 不处理，从 1 开始
            // 一个商品一个容量进行尝试
            for (int j = 1; j < v[0].length; j++) { // 第一列 0 不处理，从 1 开始
                // 当当前物品重量大于，背包限定重量时：
                //   当前背包容量，可存放最大的商品价值为：前一格子中的存放策略总价值
                if (w[i] > j) {
                    v[i][j] = v[i - 1][j];
                }
                // 当当前背包容量 大于 当前物品重量时：
                //      说明：还有空余空间存放其他产品
                else {
                    // max{v[i - 1][j],v[i - 1][j - w[i]] + v[i]}`
                    int pre = v[i - 1][j]; // 前一个格子中存放策略总价值
                    int curr = val[i]; // i当前商品总价值
                    int free = v[i - 1][j - w[i]];  // 存放完当前商品后，剩余空间能存放的总价值
                    // 当前格子：只会存放比上一个格子总价值大的策略价值
//                    v[i][j] = Math.max(pre, curr + free);
                    // 当需要存放新的策略时，标记当前路径
                    if (pre < curr + free) {
                        v[i][j] = curr + free;
                        path[i][j] = 1;
                    } else {
                        v[i][j] = pre;
                    }
                }
            }
        }
        System.out.println("动态规划后");
        print(v);

        System.out.println("存放路径");
        print(path);

        System.out.println("提取最优商品存放信息");
        int i = path.length - 1; // 行的最大下标
        int j = path[0].length - 1; // 列的最大下标
        // 从 path 最后开始往前找
        while (i > 0 && j > 0) {
            if (path[i][j] == 1) {
                System.out.printf("第 %d 个商品放入背包，坐标[%d,%d] \n", i, i, j);
                // 当前格子商品组成为：当前商品重量 + 剩余重量
                // 所以：要重置 j 为剩余重量
                j = j - w[i];
            }
            i--;
        }
    }

    // 封装成方法，测试更多的商品
    @Test
    public void tableAndProduct2() {
        int[] val = {0, 1500, 3000, 2000, 200};  // 商品价值
        int[] w = {0, 1, 4, 3, 1}; // 商品对应重量
        int m = 5; // 背包容量
        knapsackProblem(val, w, m);
    }

    /**
     * 背包问题：动态规划
     *
     * @param val 商品价值，第 0 个为没有商品
     * @param w   商品重量，第 0 个为没有重量
     * @param m   背包容量
     */
    public void knapsackProblem(int[] val, int[] w, int m) {
        // 商品价值和重量前面都有一个 0 ，方便后续的公式写法
        int n = val.length; // 物品个数
        // 构建初始表格， +1 是因为有一行列和行都是 0
        // v[i][j] 存放的是：前 i 个物品中能够装入容量为 j 的背包中的最大价值
        int[][] v = new int[n][m + 1];

        int zero = 0;
        for (int i = 0; i < v.length; i++) {
            for (int j = 0; j < v[0].length; j++) {
                v[i][0] = zero; // 初始化第一列为 0
                v[0][i] = zero; // 初始化第一行为 0
            }
        }
        print(v);

        // 用于存放每个格子中保存的商品
        int[][] path = new int[n][m + 1];

        // 开始填表：动态规划处理
        for (int i = 1; i < v.length; i++) {  // 第一行 0 不处理，从 1 开始
            // 一个商品一个容量进行尝试
            for (int j = 1; j < v[0].length; j++) { // 第一列 0 不处理，从 1 开始
                // 当当前物品重量大于，背包限定重量时：
                //   当前背包容量，可存放最大的商品价值为：前一格子中的存放策略总价值
                if (w[i] > j) {
                    v[i][j] = v[i - 1][j];
                }
                // 当当前背包容量 大于 当前物品重量时：
                //      说明：还有空余空间存放其他产品
                else {
                    // max{v[i - 1][j],v[i - 1][j - w[i]] + v[i]}`
                    int pre = v[i - 1][j]; // 前一个格子中存放策略总价值
                    int curr = val[i]; // i当前商品总价值
                    int free = v[i - 1][j - w[i]];  // 存放完当前商品后，剩余空间能存放的总价值
                    // 当前格子：只会存放比上一个格子总价值大的策略价值
//                    v[i][j] = Math.max(pre, curr + free);
                    // 当需要存放新的策略时，标记当前路径
                    if (pre < curr + free) {
                        v[i][j] = curr + free;
                        path[i][j] = 1;
                    } else {
                        v[i][j] = pre;
                    }
                }
            }
        }
        System.out.println("动态规划后");
        print(v);

        System.out.println("存放路径");
        print(path);

        System.out.println("提取最优商品存放信息");
        int i = path.length - 1; // 行的最大下标
        int j = path[0].length - 1; // 列的最大下标
        // 从 path 最后开始往前找
        while (i > 0 && j > 0) {
            if (path[i][j] == 1) {
                System.out.printf("第 %d 个商品放入背包，坐标[%d,%d] \n", i, i, j);
                // 当前格子商品组成为：当前商品重量 + 剩余重量
                // 所以：要重置 j 为剩余重量
                j = j - w[i];
            }
            i--;
        }
    }

    /**
     * 查看和给定商品列表顺序结果是否有变化：结论是没有变化，放入的商品都是同一个
     */
    @Test
    public void tableAndProduct3() {
        int[] val = {0, 1500, 3000, 2000, 1500};  // 商品价值
        int[] w = {0, 1, 4, 3, 1}; // 商品对应重量
        int m = 5; // 背包容量
        knapsackProblem(val, w, m);
    }

    @Test
    public void tableAndProduct4() {
        int[] val = {0, 1500, 1500, 3000, 2000};  // 商品价值
        int[] w = {0, 1, 1, 4, 3}; // 商品对应重量
        int m = 5; // 背包容量
        knapsackProblem(val, w, m);
    }

    /**
     * 打印填表信息
     *
     * @param table
     */
    private void print(int[][] table) {
        for (int i = 0; i < table.length; i++) {
            System.out.println(Arrays.toString(table[i]));
        }
    }
}
