package cn.mrcode.study.dsalgtutorialdemo.datastructure.search;

import org.junit.Test;

/**
 * 线性查找
 */
public class SeqSearchTest {
    @Test
    public void seqSearchTest() {
        int[] arr = {1, 8, 10, 89, 1000, 1234};
        int i = seqSearch(arr, 1000);
        System.out.println("查找目标值 1000：" + (i == -1 ? "未找到" : "已找到，下标为 " + i));
        i = seqSearch(arr, -990);
        System.out.println("查找目标值 -990：" + (i == -1 ? "未找到" : "已找到，下标为 " + i));
    }

    /**
     * 实现的线性查找法是找到首个出现的位置
     *
     * @param arr
     * @param value
     * @return
     */
    public int seqSearch(int[] arr, int value) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == value) {
                return i;
            }
        }
        return -1;
    }
}
