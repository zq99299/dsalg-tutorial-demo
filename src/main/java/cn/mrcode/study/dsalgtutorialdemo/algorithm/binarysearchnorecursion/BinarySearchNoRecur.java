package cn.mrcode.study.dsalgtutorialdemo.algorithm.binarysearchnorecursion;

import org.junit.Test;

/**
 * 二分查找：非递归
 */
public class BinarySearchNoRecur {
    @Test
    public void fun() {
        int[] arr = new int[]{1, 3, 8, 10, 11, 67, 100};
        int target = 1;
        int result = binarySearch(arr, target);
        System.out.printf("查找 %d ,找位置为 %d \n", target, result);

        target = 11;
        result = binarySearch(arr, target);
        System.out.printf("查找 %d ,找位置为 %d \n", target, result);

        target = 100;
        result = binarySearch(arr, target);
        System.out.printf("查找 %d ,找位置为 %d \n", target, result);

        target = -1;
        result = binarySearch(arr, target);
        System.out.printf("查找 %d ,找位置为 %d \n", target, result);

        target = 200;
        result = binarySearch(arr, target);
        System.out.printf("查找 %d ,找位置为 %d \n", target, result);
    }

    /**
     * 二分查找：非递归
     *
     * @param arr 数组，前提：升序排列
     * @return 找到则返回下标，找不到则返回 -1
     */
    public int binarySearch(int[] arr, int target) {
        int left = 0;
        int right = arr.length;
        int mid = 0;
        // 表示还可以进行查找
        while (left <= right) {
            mid = (left + right) / 2;
            if (mid >= arr.length // 查找的值大于数组中的最大值
            ) {
                // 防止越界
                return -1;
            }
            if (arr[mid] == target) {
                return mid;
            }
            // 升序：目标值比中间值大，则向左查找
            if (target > arr[mid]) {
                left = mid + 1;
            } else {
                // 否则：向右查找
                right = mid - 1;
            }
        }
        return -1;
    }
}
