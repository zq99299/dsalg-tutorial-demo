package cn.mrcode.study.dsalgtutorialdemo.datastructure.recursion;

import org.junit.Test;

/**
 * 八皇后问题：
 * <pre>
 *     规则：在 8×8 格的国际象棋上摆放八个皇后，使其不能互相攻击，即：任意 两个皇后 都不能处于同一行、同一列或同一斜线上，问有多少种摆法。
 * </pre>
 */
public class Queue8 {
    // 共有多少个皇后
    int max = 8;
    /**
     * 存放皇后位置的结果
     * <pre>
     * 下标：表示棋盘中的某一行
     * 对应的值：表示在这一行上，该皇后摆放在哪一列
     * 比如：array[0] = 1，表示在第 1 行的第 2 列上摆放了一个皇后
     *
     * 由于规则，一行只能有一个皇后，所以可以使用一维数组来代替二维数组的棋盘结果
     * </pre>
     */
    int[] array = new int[max];
    int count = 0; // 统计有多少个结果

    public static void main(String[] args) {
        Queue8 queue8 = new Queue8();
        queue8.check(0); // 从第 1 行开始放置
    }

    /**
     * 放置第 n 个（行）皇后
     *
     * @param n
     */
    private void check(int n) {
        // n = 8，那么表示放第 9 个皇后，8 个皇后已经放完了
        // 表示找到了一个正确的结果，打印这个结果，并返回
        if (n == max) {
            count++;
            print();
            return;
        }

        // 开始暴力对比，从该行的第一列开始尝试放置皇后，直到与前面所放置的不冲突
        for (int i = 0; i < max; i++) {
            // 在该行的第 i 列上放置一个皇后
            array[n] = i;
            // 检测与已经放置的是否冲突
            if (judge(n)) {
                // 如果不冲突，则表示该行的皇后放置没有问题
                // 开始进入下一行的皇后放置
                check(n + 1);
            }
            // 如果冲突，这里什么也不做
            // 因为是从第一列开始放置，如果冲突，则尝试放置到第 2 列上，直到放置成功
        }
    }

    /**
     * 判定要放置的这一个皇后，和前面已经摆放的位置是否冲突
     *
     * @param n 第 n 个皇后
     * @return
     */
    private boolean judge(int n) {
        for (int i = 0; i < n; i++) {
            if (
                    /*
                     如果他们的摆放位置一样，说明是在同一列
                      x ....
                      x ....
                     */
                    array[i] == array[n]
                            /*
                              检测是否是同一斜列
                              下标: 代表的是第几行
                              值：代表的是第几列
                              . x . . .  n = 1,value = 1
                              x . . . .  i = 0,value = 0
                              Math.abs(n - i) = 1
                              Math.abs(array[n] - array[i]) = 1

                              . . x . .  n = 1,value = 2
                              . x . . .  i = 0,value = 1
                              Math.abs(n - i) = 1
                              Math.abs(array[n] - array[i]) = 1
                             */
                            || Math.abs(n - i) == Math.abs(array[n] - array[i])
            ) {
                return false;
            }
        }
        return true;
    }

    /**
     * 打印皇后的位置
     */
    private void print() {
        System.out.printf("第 %02d 个结果 ：", count);
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i] + " ");
        }
        System.out.println();
    }

    /**
     * 下面是对于判定是否是同行，同列、同一斜列的分步骤测试，比较好理解
     */
    @Test
    public void judgeTest() {
        /*
         * . . . . . . . .
         * x . . . . . . .
         */
        Queue8 queue8 = new Queue8();
        queue8.array[0] = 0;

        //======== 放置第 1 个皇后
        // 判断是否是同一列
        /*
         * x . . . . . . .  // 计划放到这里
         * x . . . . . . .
         */
        queue8.array[1] = 0; // 从第一列开始放置
        System.out.println("同一列，是否通过：" + queue8.judge(1));

        /*
         * . x . . . . . .  // 计划放到这里
         * x . . . . . . .
         */
        queue8.array[1] = 1;
        // 第一列不行，放置到第 2 列上
        System.out.println("同一斜列，是否通过：" + queue8.judge(1));

        /*
         * . . x . . . . .  // 计划放到这里
         * x . . . . . . .
         */
        queue8.array[1] = 2;
        // 第 2 列不行，放置到第 3 列上,这个肯定是可以的
        System.out.println("同一列或同一斜列，是否通过：" + queue8.judge(1));

        //======== 放置第 3 个皇后
        /*
         * x . . . . . . .  // 计划放到这里
         * . . x . . . . .
         * x . . . . . . .
         */
        queue8.array[2] = 0;
        // 与第一行的在同一列上了
        System.out.println("同一列，是否通过：" + queue8.judge(2));

        /*
         * . x . . . . . .  // 计划放到这里
         * . . x . . . . .
         * x . . . . . . .
         */
        queue8.array[2] = 1;
        // 第一列不行，放置到第 2 列
        // 这里与第 2 行的同一斜列了，也是不行的
        System.out.println("同一斜列，是否通过：" + queue8.judge(2));
    }
}
