package cn.mrcode.study.dsalgtutorialdemo.datastructure.sort.merge;

import org.junit.Test;

import java.util.Arrays;

/**
 * 为了巩固记忆，自己根据基本思想进行默写实现
 */
public class MyMergeSortTest {
    @Test
    public void sortTest() {
        int[] arr = new int[]{8, 4, 5, 7, 1, 3, 6, 2};
        mergeSort(arr);
        System.out.println("排序后：" + Arrays.toString(arr));
    }

    public void mergeSort(int arr[]) {
        int[] temp = new int[arr.length];
        doMergeSort(arr, 0, arr.length - 1, temp);
    }

    /**
     * 分治 与 合并
     *
     * @param arr
     * @param left
     * @param right
     * @param temp
     */
    private void doMergeSort(int[] arr, int left, int right, int[] temp) {
        // 当还可以分解时，就做分解操作
        if (left < right) {
            int mid = (left + right) / 2;
            // 先左递归分解
            doMergeSort(arr, left, mid, temp);
            // 再右递归分解
            doMergeSort(arr, mid + 1, right, temp);
            // 左递归分解到栈顶时，其实也是分为左右数组
            // 左右都到栈顶时，开始合并：
            // 第一次：合并的是 0,1 的索引,分解到最后的时候，其实只有一个数为一组，所以第一次合并是合并两个数字
            merge(arr, left, mid, right, temp);
        }
    }

    /**
     * 开始合并，每次合并都是两组，第一次合并是两个数字，左右一组都只有一个数字
     *
     * @param arr
     * @param left
     * @param mid
     * @param right
     * @param temp
     */
    private void merge(int[] arr, int left, int mid, int right, int[] temp) {
        // 1. 按照规则，将 temp 数组填充
        // 2. 当任意一边填充完成后，剩余未填充的依次填充到 temp 数组中
        // 3. 将 temp 数组的有效内容，拷贝会原数组，也就是将这次排序好的数组覆盖回原来排序的原数组中

        int l = left; // 左侧数组初始索引
        int r = mid + 1; // 右侧数组初始索引
        int t = 0; // 当前 temp 中有效数据的最后一个元素索引
        // 1. 按照规则，将 temp 数组填充
        // 当两边都还有数字可比较的时候，进行比较，然后填充 temp 数组
        // 只要任意一边没有数值可用时，就仅需到下一步骤
        while (l <= mid && r <= right) {
            // 当左边比右边小时，则填充到 temp 数组中
            if (arr[l] < arr[r]) {
                // 赋值完成后，t 和 l 都需要 +1，往后移动一个位置
                // t++ 是先把 t 进行赋值，再进行 t+1 操作
                temp[t++] = arr[l++];
            } else {
                // 当不满足时，则说明 右侧数字比左侧的小，进行右侧的填充
                temp[t++] = arr[r++];
            }
        }

        // 2. 有可能有其中一边会有剩余数字未填充到 temp 中，进行收尾处理
        while (l <= mid) {
            temp[t++] = arr[l++];
        }
        while (r <= right) {
            temp[t++] = arr[r++];
        }

        // 3. 将这个有序数组，覆盖会原始排序的数组中
        t = 0;
        int tempL = left; // 从左侧开始，到右侧结束，就是这一次要合并的两组数据
//        System.out.println("tempL=" + tempL + "; right=" + right);
        while (tempL <= right) {
            arr[tempL++] = temp[t++];
        }
    }


}
