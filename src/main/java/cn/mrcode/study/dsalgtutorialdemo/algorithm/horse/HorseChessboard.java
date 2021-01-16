package cn.mrcode.study.dsalgtutorialdemo.algorithm.horse;

import org.junit.Test;

import java.awt.*;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;


/**
 * 骑士周游问题-马踏棋盘算法
 */
public class HorseChessboard {
    @Test
    public void nextTest() {
        // 测试
        Point current = new Point(4, 4);
        System.out.println(current + " 下一步可选位置有：");
        ArrayList<Point> next = next(current);
        System.out.println(next);

        // 测试一个 0,0
        current = new Point(0, 0);
        System.out.println(current + " 下一步可选位置有：");
        next = next(current);
        System.out.println(next);
    }

    int X = 8; // 棋盘的行数
    int Y = 8; // 棋盘的列数

    /**
     * 根据马儿所在的位置，查找它下一步可以走的位置; 从 0 ~ 7 的策略，耗时很长很长，回溯太久了，好几分钟都出不来
     *
     * @param current
     * @return
     */
    public ArrayList<Point> next1(Point current) {
        ArrayList<Point> result = new ArrayList<>();
        int cx = current.x;
        int cy = current.y;
        // 第 0 个点
        if (cx - 1 >= 0 && cy + 2 < Y) {
            result.add(new Point(cx - 1, cy + 2));
        }
        // 第 1 个点
        if (cx + 1 < X && cy + 2 < Y) {
            result.add(new Point(cx + 1, cy + 2));
        }
        // 第 2 个点
        if (cx + 2 < X && cy - 1 >= 0) {
            result.add(new Point(cx + 2, cy - 1));
        }
        // 第 3 个点
        if (cx + 2 < X && cy + 1 < Y) {
            result.add(new Point(cx + 2, cy + 1));
        }
        // 第 4 个点
        if (cx + 1 < X && cy - 2 >= 0) {
            result.add(new Point(cx + 1, cy - 2));
        }
        // 第 5 个点
        if (cx - 1 >= 0 && cy - 2 >= 0) {
            result.add(new Point(cx - 1, cy - 2));
        }
        // 第 6 个点
        if (cx - 2 >= 0 && cy - 1 >= 0) {
            result.add(new Point(cx - 2, cy - 1));
        }
        // 第 7 个点
        if (cx - 2 >= 0 && cy + 1 < Y) {
            result.add(new Point(cx - 2, cy + 1));
        }
        return result;
    }

    /**
     * 从 5 ~ 7，7 ~ 0 ，这个策略大概需要 44 秒
     *
     * @param current
     * @return
     */
    public ArrayList<Point> next(Point current) {
        ArrayList<Point> result = new ArrayList<>();
        int cx = current.x;
        int cy = current.y;
        // 第 5 个点
        if (cx - 1 >= 0 && cy - 2 >= 0) {
            result.add(new Point(cx - 1, cy - 2));
        }
        // 第 6 个点
        if (cx - 2 >= 0 && cy - 1 >= 0) {
            result.add(new Point(cx - 2, cy - 1));
        }
        // 第 7 个点
        if (cx - 2 >= 0 && cy + 1 < Y) {
            result.add(new Point(cx - 2, cy + 1));
        }
        // 第 0 个点
        if (cx - 1 >= 0 && cy + 2 < Y) {
            result.add(new Point(cx - 1, cy + 2));
        }
        // 第 1 个点
        if (cx + 1 < X && cy + 2 < Y) {
            result.add(new Point(cx + 1, cy + 2));
        }
        // 第 2 个点
        if (cx + 2 < X && cy - 1 >= 0) {
            result.add(new Point(cx + 2, cy - 1));
        }
        // 第 3 个点
        if (cx + 2 < X && cy + 1 < Y) {
            result.add(new Point(cx + 2, cy + 1));
        }
        // 第 4 个点
        if (cx + 1 < X && cy - 2 >= 0) {
            result.add(new Point(cx + 1, cy - 2));
        }
        return result;
    }

    private boolean finished; // 是否已经完成，由于是递归，在某一步已经完成，回溯时就可以跳过
    private boolean[] visited = new boolean[X * Y];  // 标记是否访问过，访问过的不再访问

    /**
     * 马踏棋盘算法核心代码
     *
     * @param chessboard 棋盘，用于标识哪一个点是第几步走的
     * @param cx         当前要尝试访问的点 x 坐标（行）
     * @param cy         当前要尝试访问的点 y 坐标（列）
     * @param step       当前是第几步
     */
    public void traversalChessboard(int[][] chessboard, int cx, int cy, int step) {
        // 1. 标识当前点已经访问
        visited[buildVisitedIndex(cx, cy)] = true;
        // 2. 标识当前棋盘上的点是第几步走的
        chessboard[cx][cy] = step;
        // 3. 根据当前节点计算马儿可以走的点
        ArrayList<Point> points = next(new Point(cx, cy));
        sort(points);
        //  不为空则可以一直尝试走
        while (!points.isEmpty()) {
            Point point = points.remove(0);
            // 如果该点，没有被访问过，则递归访问：深度优先
            if (!visited[buildVisitedIndex(point.x, point.y)]) {
                traversalChessboard(chessboard, point.x, point.y, step + 1);
            }
        }

        // 所有可走的点都走完了，如果还没有完成，则重置当前访问的点为没有访问过

        if (step < X * Y && !finished) {
            visited[buildVisitedIndex(cx, cy)] = false;
            chessboard[cx][cy] = 0; // 重置为 0 表示没有走过
        } else {
            finished = true;  // 表示已经完成任务
        }
//        System.out.println(step);
//        show(chessboard);
    }

    /**
     * 贪心算法优化：按每一个点的 next 可选择的点数量进行升序排列
     *
     * @param points
     */
    private void sort(ArrayList<Point> points) {
        points.sort((o1, o2) -> {
            ArrayList<Point> next1 = next(o1);
            ArrayList<Point> next2 = next(o2);
            // 你可以尝试修改下这里：按降序排列的话，这个等待时间就太多了
            // 升序排列，我这里只需要 100 毫秒左右，而降序排列需要接近 1 分多钟甚至几分钟
            if (next1.size() > next2.size()) {
                return 1;
            } else if (next1.size() == next2.size()) {
                return 0;
            } else {
                return -1;
            }

        });
    }

    /**
     * 使用的是一个一维数组来表示某个点是否被访问过
     * <pre>
     *   那么就直接数格子，第 N 个格子对应某一个点，从左到右，上到下数
     *   0,1,2,3,4,5,6,7,
     *   8,9,10,11...
     * </pre>
     *
     * @param cx
     * @param xy
     * @return
     */
    private int buildVisitedIndex(int cx, int xy) {
        //比如 0,1: 0*8 + 1 =  1
        return cx * X + xy;
    }

    @Test
    public void traversalChessboardTest() {
        int[][] chessboard = new int[X][Y];
        Instant start = Instant.now();
        traversalChessboard(chessboard, 0, 0, 1);
        Duration re = Duration.between(start, Instant.now());
        System.out.println("耗时：" + re.toMillis() + "毫秒");
        System.out.println("耗时：" + re.getSeconds() + "秒");
        show(chessboard);
    }

    private void show(int[][] chessboard) {
        for (int[] row : chessboard) {
            System.out.println(Arrays.toString(row));
        }
    }
}
