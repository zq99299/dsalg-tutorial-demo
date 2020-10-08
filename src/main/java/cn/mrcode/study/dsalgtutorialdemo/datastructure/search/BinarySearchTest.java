package cn.mrcode.study.dsalgtutorialdemo.datastructure.search;

import org.junit.Test;

/**
 * 二分查找
 */
public class BinarySearchTest {
    @Test
    public void binaryTest() {
        int[] arr = new int[]{1, 8, 10, 89, 1000, 1234};
        int findVal = 89;
        int result = binary(arr, 0, arr.length - 1, findVal);
        System.out.println("查找值 " + findVal + "：" + (result == -1 ? "未找到" : "找到值，索引为：" + result));

        findVal = -1;
        result = binary(arr, 0, arr.length - 1, findVal);
        System.out.println("查找值 " + findVal + "：" + (result == -1 ? "未找到" : "找到值，索引为：" + result));

        findVal = 123456;
        result = binary(arr, 0, arr.length - 1, findVal);
        System.out.println("查找值 " + findVal + "：" + (result == -1 ? "未找到" : "找到值，索引为：" + result));

        findVal = 1;
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
}
