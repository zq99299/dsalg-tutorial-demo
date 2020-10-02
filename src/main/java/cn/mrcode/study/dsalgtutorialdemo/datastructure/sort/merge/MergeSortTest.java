package cn.mrcode.study.dsalgtutorialdemo.datastructure.sort.merge;

import org.junit.Test;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;

/**
 * 归并排序
 */
public class MergeSortTest {
    @Test
    public void sortTest() {
        int[] arr = new int[]{8, 4, 5, 7, 1, 3, 6, 2};
        int[] temp = new int[arr.length];
        mergeSort(arr, 0, arr.length - 1, temp);
        System.out.println("排序后：" + Arrays.toString(arr));
    }

    /**
     * 分 和 合并
     */
    public void mergeSort(int[] arr, int left, int right, int[] temp) {
        if (left < right) {
            int mid = (left + right) / 2;
            // 先分解左侧
            mergeSort(arr, left, mid, temp);
            // 再分解右侧
            mergeSort(arr, mid + 1, right, temp);
            // 最后合并
            // 由于是递归，合并这里一定是栈顶的先执行
            // 第一次：left = 0,right=1
            // 第二次：left = 2,right=3
            // 第三次：left = 0，right=3
//            System.out.println("left=" + left + "；right=" + right);
            merge(arr, left, mid, right, temp);
        }
    }

    /**
     * <pre>
     *  最难的是合并,所以可以先完成合并的方法，此方法请参考 基本思想 和 思路分析中的图解来完成
     *
     * </pre>
     *
     * @param arr   要排序的原始数组
     * @param left  因为是合并，所以要得到左右两边的的数组信息，这个是左侧数组的第一个索引值
     * @param mid   因为是一个数组，标识是第一个数组的最后一个索引，即 mid+1 是右侧数组的第一个索引
     * @param right 右侧数组的结束索引
     * @param temp  临时空间
     */
    public void merge(int[] arr, int left, int mid, int right, int[] temp) {
        // 1. 按规则将两个数组合并到 temp 中
        // 定时临时变量，用来遍历数组比较
        int l = left;  // 左边有序数组的初始索引
        int r = mid + 1; // 右边有序数组的初始索引
        int t = 0; // temp 数组中当前最后一个有效数据的索引

        // 因为是合并两个数组，所以需要两边的数组都还有值的时候才能进行比较合并
        while (l <= mid && r <= right) {
            // 如果左边的比右边的小，则将左边的该元素填充到 temp 中
            // 并移动 t++,l++,好继续下一个
            if (arr[l] < arr[r]) {
                temp[t] = arr[l];
                t++;
                l++;
            }
            // 否则则将右边的移动到 temp 中
            else {
                temp[t] = arr[r];
                t++;
                r++;
            }
        }
        // 2. 如果有任意一边的数组还有值，则依序将剩余数据填充到 temp 中
        // 如果左侧还有值
        while (l <= mid) {
            temp[t] = arr[l];
            t++;
            l++;
        }
        // 如果右侧还有值
        while (r <= right) {
            temp[t] = arr[r];
            t++;
            r++;
        }

        // 3. 将 temp 数组，拷贝到原始数组
        // 注意：只拷贝当前有效数据到对应的原始数据中
        int tempL = left; // 从左边开始拷贝
        t = 0;  // temp 中的有效值，可以通过下面的 right 判定，因为合并两个数组到 temp 中，最大值则是 right
        /*
         * 对于 8,4,5,7,1,3,6,2 这个数组,
         * 从栈顶开始合并：8,4 | 5,7       1,3 | 6,2
         * 先左递归的话：
         * 第一次：8,4 合并：tempL=0，right=1
         * 第二次：5,7 合并：tempL=2，right=3
         * 第三次：4,8 | 5，7 进行合并，tempL=0，right=3
         * 直到左递归完成，得到左侧一个有序的序列：4,5,7,8 然后开始右递归
         * 最后回到栈底分解成两个数组的地方，将两个数组合并成一个有序数组
         */
//        System.out.println("tempL=" + tempL + "; right=" + right);
        while (tempL <= right) {
            arr[tempL] = temp[t];
            tempL++;
            t++;
        }
    }

    /**
     * 大量数据排序时间测试
     */
    @Test
    public void bulkDataSort() {
        int max = 80_000;
//        max = 8;
        int[] arr = new int[max];
        for (int i = 0; i < max; i++) {
            arr[i] = (int) (Math.random() * 80_000);
        }
        if (arr.length < 10) {
            System.out.println("原始数组：" + Arrays.toString(arr));
        }
        Instant startTime = Instant.now();
        int[] temp = new int[arr.length];
        mergeSort(arr, 0, arr.length - 1, temp);
        if (arr.length < 10) {
            System.out.println("排序后：" + Arrays.toString(arr));
        }
        Instant endTime = Instant.now();
        System.out.println("共耗时：" + Duration.between(startTime, endTime).toMillis() + " 毫秒");
    }
}
