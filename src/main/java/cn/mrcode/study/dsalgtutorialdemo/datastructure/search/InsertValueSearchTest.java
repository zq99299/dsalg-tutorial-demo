package cn.mrcode.study.dsalgtutorialdemo.datastructure.search;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class InsertValueSearchTest {
    /**
     * 先来看一个场景，在二分查找中查找需要几次
     */
    @Test
    public void binary2Test() {
        int[] arr = new int[]{1, 8, 10, 89, 1000, 1000, 1234, 1234};
        int findVal = 1;
        Integer result = binary(arr, 0, arr.length - 1, findVal);
        System.out.println("查找值 " + findVal + "：" + (result == null ? "未找到" : "找到值，索引为：" + result));

        findVal = 1000;
        result = binary(arr, 0, arr.length - 1, findVal);
        System.out.println("查找值 " + findVal + "：" + (result == -1 ? "未找到" : "找到值，索引为：" + result));

        findVal = 1234;
        result = binary(arr, 0, arr.length - 1, findVal);
        System.out.println("查找值 " + findVal + "：" + (result == -1 ? "未找到" : "找到值，索引为：" + result));

        findVal = 12345;
        result = binary(arr, 0, arr.length - 1, findVal);
        System.out.println("查找值 " + findVal + "：" + (result == -1 ? "未找到" : "找到值，索引为：" + result));

        findVal = 89;
        result = binary(arr, 0, arr.length - 1, findVal);
        System.out.println("查找值 " + findVal + "：" + (result == -1 ? "未找到" : "找到值，索引为：" + result));
    }

    /**
     * @param arr
     * @param left    左边索引
     * @param right   右边索引
     * @param findVal 要查找的值
     * @return 未找到返回 -1，否则返回该值的索引
     */
    private int binary(int[] arr, int left, int right, int findVal) {
        System.out.println("binary");
        // 当找不到时，则返回 -1
        if (left > right) {
            return -1;
        }
        int mid = (left + right) / 2;
        int midVal = arr[mid];
        // 相等则找到
        if (midVal == findVal) {
            return mid;
        }
        // 要查找的值在右边，则右递归
        if (findVal > midVal) {
            // mid 的值，就是当前对比的值，所以不需要判定
            return binary(arr, mid + 1, right, findVal);
        }
        return binary(arr, left, mid - 1, findVal);
    }

    @Test
    public void insertValueTest() {
        int[] arr = new int[100];
        for (int i = 0; i < 100; i++) {
            arr[i] = i + 1;
        }

        int findVal = 1;
        int result = insertValueSearch(arr, 0, arr.length - 1, findVal);
        System.out.println("查找值 " + findVal + "：" + (result == -1 ? "未找到" : "找到值，索引为：" + result));

        findVal = 50;
        result = insertValueSearch(arr, 0, arr.length - 1, findVal);
        System.out.println("查找值 " + findVal + "：" + (result == -1 ? "未找到" : "找到值，索引为：" + result));

        findVal = 100;
        result = insertValueSearch(arr, 0, arr.length - 1, findVal);
        System.out.println("查找值 " + findVal + "：" + (result == -1 ? "未找到" : "找到值，索引为：" + result));
    }

    public int insertValueSearch(int[] arr, int left, int right, int findValue) {
        System.out.println("insertValueSearch");
//        if (left > right) {
//            return -1;
//        }
        // 这里可以对未找到进行优化
        // 同时对于查找的边界判定来说是必须的，因为 mid 求职是根据值来求，不进行边界判定，有可能就越界了
        if (left > right || findValue < arr[0] || findValue > arr[arr.length - 1]) {
            return -1;
        }
        int mid = left + (right - left) * (findValue - arr[left]) / (arr[right] - arr[left]);
        if (arr[mid] == findValue) {
            return mid;
        }

        // 当查找值大于该值，则表示目标值在右侧
        if (findValue > arr[mid]) {
            return insertValueSearch(arr, mid + 1, right, findValue);
        }

        // 当查找值小于该值，则表示目标值在左侧
        return insertValueSearch(arr, left, mid - 1, findValue);
    }

    /**
     * 查找非连续性的值
     */
    @Test
    public void insertValueTest2() {
        int[] arr = new int[]{1, 8, 10, 89, 1000, 1000, 1234, 1234};

        int findVal = 1;
        int result = insertValueSearch(arr, 0, arr.length - 1, findVal);
        System.out.println("查找值 " + findVal + "：" + (result == -1 ? "未找到" : "找到值，索引为：" + result));

        findVal = 1000;
        result = insertValueSearch(arr, 0, arr.length - 1, findVal);
        System.out.println("查找值 " + findVal + "：" + (result == -1 ? "未找到" : "找到值，索引为：" + result));

        findVal = 1234;
        result = insertValueSearch(arr, 0, arr.length - 1, findVal);
        System.out.println("查找值 " + findVal + "：" + (result == -1 ? "未找到" : "找到值，索引为：" + result));

        findVal = 12345;
        result = insertValueSearch(arr, 0, arr.length - 1, findVal);
        System.out.println("查找值 " + findVal + "：" + (result == -1 ? "未找到" : "找到值，索引为：" + result));

        findVal = 89;
        result = insertValueSearch(arr, 0, arr.length - 1, findVal);
        System.out.println("查找值 " + findVal + "：" + (result == -1 ? "未找到" : "找到值，索引为：" + result));
    }
}
