package cn.mrcode.study.dsalgtutorialdemo.datastructure.search;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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


    @Test
    public void binary2Test() {
        int[] arr = new int[]{1, 8, 10, 89, 1000, 1000, 1234, 1234};
        int findVal = 89;
        List<Integer> result = binary2(arr, 0, arr.length - 1, findVal);
        System.out.println("查找值 " + findVal + "：" + (result == null ? "未找到" : "找到值，索引为：" + result));

        findVal = -1;
        result = binary2(arr, 0, arr.length - 1, findVal);
        System.out.println("查找值 " + findVal + "：" + (result == null ? "未找到" : "找到值，索引为：" + result));

        findVal = 123456;
        result = binary2(arr, 0, arr.length - 1, findVal);
        System.out.println("查找值 " + findVal + "：" + (result == null ? "未找到" : "找到值，索引为：" + result));

        findVal = 1;
        result = binary2(arr, 0, arr.length - 1, findVal);
        System.out.println("查找值 " + findVal + "：" + (result == null ? "未找到" : "找到值，索引为：" + result));

        findVal = 1000;
        result = binary2(arr, 0, arr.length - 1, findVal);
        System.out.println("查找值 " + findVal + "：" + (result == null ? "未找到" : "找到值，索引为：" + result));

        findVal = 1234;
        result = binary2(arr, 0, arr.length - 1, findVal);
        System.out.println("查找值 " + findVal + "：" + (result == null ? "未找到" : "找到值，索引为：" + result));
    }

    /**
     * 查找所有符合条件的下标
     *
     * @param arr
     * @param left    左边索引
     * @param right   右边索引
     * @param findVal 要查找的值
     * @return 未找到返回 -1，否则返回该值的索引
     */
    private List<Integer> binary2(int[] arr, int left, int right, int findVal) {
        // 当找不到时，则返回 -1
        if (left > right) {
            return null;
        }
        int mid = (left + right) / 2;
        int midVal = arr[mid];
        // 相等则找到
        if (midVal == findVal) {
            List<Integer> result = new ArrayList<>();
            // 如果已经找到，则先不要退出
            // 因为二分查找的前提是：对一个有序的数组进行查找
            // 所以，我们只需要，继续挨个的往左边和右边查找目标值就好了
            int tempIndex = mid - 1;
            result.add(mid);
            // 先往左边找
            while (true) {
                // 当左边已经找完
                // 或则找到一个不与目标值相等的值，就可以跳出左边查找
                if (tempIndex < 0 || arr[tempIndex] != midVal) {
                    break;
                }
                result.add(tempIndex);
                tempIndex--;
            }
            // 再往右边查找
            tempIndex = mid + 1;
            while (true) {
                if (tempIndex >= arr.length || arr[tempIndex] != midVal) {
                    break;
                }
                result.add(tempIndex);
                tempIndex++;
            }
            return result;
        }
        // 要查找的值在右边，则右递归
        if (findVal > midVal) {
            // mid 的值，就是当前对比的值，所以不需要判定
            return binary2(arr, mid + 1, right, findVal);
        }
        return binary2(arr, left, mid - 1, findVal);
    }
}
