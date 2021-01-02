package cn.mrcode.study.dsalgtutorialdemo.algorithm.dac;

import org.junit.Test;

/**
 * 汉诺塔算法
 */
public class Hanoitower {
    /**
     * 汉诺塔算法
     *
     * @param num 有几个盘子
     * @param a   a 柱子
     * @param b   b 柱子
     * @param c   c 柱子
     */
    public void hanoiTower(int num, char a, char b, char c) {
        // 当只有一个盘时：直接从 a -> c
        if (num == 1) {
//            System.out.printf("第 1 个盘从 %s → %s \n", a, c);
            System.out.printf("第 %d 个盘从 %s → %s \n", num, a, c);
        } else {
            // 否则，始终看成只有两个盘
            // 1. 最上面的盘：a -> b, 中间会用到 c
            // 因为最小规模是只有一个盘的时候，直接移动到 c
            hanoiTower(num - 1, a, c, b);
            // 2. 最下面的盘：a -> c
            System.out.printf("第 %d 个盘从 %s → %s \n", num, a, c);
            // 3. 把 B 塔所有的盘，移动到 c：b -> c, 移动过程中使用到 a
            hanoiTower(num - 1, b, a, c);
        }
    }

    @Test
    public void han1() {
        hanoiTower(1, 'A', 'B', 'C');
    }

    @Test
    public void han2() {
        hanoiTower(2, 'A', 'B', 'C');
    }

    @Test
    public void han3() {
        hanoiTower(3, 'A', 'B', 'C');
    }

    @Test
    public void han4() {
        hanoiTower(4, 'A', 'B', 'C');
    }

    @Test
    public void han5() {
        hanoiTower(5, 'A', 'B', 'C');
    }
}
