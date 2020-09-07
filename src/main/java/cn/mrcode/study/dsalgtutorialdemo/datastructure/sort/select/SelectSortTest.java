package cn.mrcode.study.dsalgtutorialdemo.datastructure.sort.select;

import org.junit.Test;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;

/**
 * 选择排序
 */
public class SelectSortTest {
    /**
     * 推导每一步的演变过程，便于理解
     * <pre>
     *   这是一个很重要的思想：
     *      一个算法：先简单  -->  做复杂：
     *      就是可以把一个复杂的算法，拆分成简单的问题  -> 逐步解决
     * </pre>
     */
    @Test
    public void processDemo() {
        int arr[] = {101, 34, 119, 1};
        System.out.println("原始数组：" + Arrays.toString(arr));
        processSelectSort(arr);
    }

    public void processSelectSort(int[] arr) {
        // 第 1 轮：
        // 原始数组：101, 34, 119, 1
        // 排序后：  1, 34, 119, 101
        int min = arr[0]; // 先假定第一个数为最小值
        int minIndex = 0;
        for (int j = 0 + 1; j < arr.length; j++) {
            // 挨个与最小值对比，如果小于，则进行交换
            if (min > arr[j]) {
                // 如果后面的值比当前的 min 小，则重置为这个数
                min = arr[j];
                minIndex = j;
            }
        }
        // 第 1 轮结束后，得到了最小值
        // 将这个最小值与 arr[0] 交换
        arr[minIndex] = arr[0];
        arr[0] = min;
        System.out.println("第 1 轮排序后：" + Arrays.toString(arr));

        // 第 2 轮
        // 当前数组：1, 34, 119, 101
        // 排序后：  1, 34, 119, 101
        min = arr[1];
        minIndex = 1;
        // 第二轮，与第 3 个数开始比起
        for (int j = 0 + 2; j < arr.length; j++) {
            // 挨个与最小值对比，如果小于，则进行交换
            if (min > arr[j]) {
                // 如果后面的值比当前的 min 小，则重置为这个数
                min = arr[j];
                minIndex = j;
            }
        }
        // 第 2 轮结束后，得到了最小值
        // 将这个最小值与 arr[1] 交换
        arr[minIndex] = arr[1];
        arr[1] = min;
        System.out.println("第 2 轮排序后：" + Arrays.toString(arr));

        // 第 3 轮
        // 当前数组：1, 34, 119, 101
        // 排序后：  1, 34, 101, 119
        min = arr[2];
        minIndex = 2;
        // 第二轮，与第 4 个数开始比起
        for (int j = 0 + 3; j < arr.length; j++) {
            // 挨个与最小值对比，如果小于，则进行交换
            if (min > arr[j]) {
                // 如果后面的值比当前的 min 小，则重置为这个数
                min = arr[j];
                minIndex = j;
            }
        }
        // 第 2 轮结束后，得到了最小值
        // 将这个最小值与 arr[2] 交换
        arr[minIndex] = arr[2];
        arr[2] = min;
        System.out.println("第 3 轮排序后：" + Arrays.toString(arr));
    }

    /**
     * 从上述的演变过程来看，循环体都是相同的，只是每次比较假定的最小值顺序在递增。
     * 因此可以改写成如下方式
     */
    @Test
    public void processDemo2() {
        int arr[] = {101, 34, 119, 1};
        System.out.println("原始数组：" + Arrays.toString(arr));
        processSelectSort2(arr);
    }

    public void processSelectSort2(int[] arr) {
        // 把之前假定当前最小值的地方，使用变量 i 代替了
        // 由于需要 arr.length -1 轮，所以使用外层一个循环，就完美的解决了这个需求
        for (int i = 0; i < arr.length - 1; i++) {
            int min = arr[i]; // 先假定第一个数为最小值
            int minIndex = i;
            for (int j = i + 1; j < arr.length; j++) {
                // 挨个与最小值对比，如果小于，则进行交换
                if (min > arr[j]) {
                    // 如果后面的值比当前的 min 小，则重置为这个数
                    min = arr[j];
                    minIndex = j;
                }
            }
            // 第 i 轮结束后，得到了最小值
            // 将这个最小值与 arr[i] 交换
            if (minIndex == i) {
                // 如果最小值未发生改变，则不需要执行后面的交换了
                continue;
            }
            arr[minIndex] = arr[i];
            arr[i] = min;
            System.out.println("第 " + (i + 1) + " 轮排序后：" + Arrays.toString(arr));
        }
    }


    @Test
    public void selectSortTest() {
        int arr[] = {101, 34, 119, 1};
        System.out.println("升序");
        System.out.println("原始数组：" + Arrays.toString(arr));
        selectSort(arr, true);
        System.out.println("排序后：" + Arrays.toString(arr));
        System.out.println("降序");
        System.out.println("原始数组：" + Arrays.toString(arr));
        selectSort(arr, false);
        System.out.println("排序后：" + Arrays.toString(arr));
    }

    /**
     * 选择排序算法封装
     *
     * @param arr 要排序的数组
     * @param asc 升序排列，否则降序
     */
    public void selectSort(int[] arr, boolean asc) {

        // 把之前假定当前最小值的地方，使用变量 i 代替了
        // 由于需要 arr.length -1 轮，所以使用外层一个循环，就完美的解决了这个需求
        for (int i = 0; i < arr.length - 1; i++) {
            int current = arr[i]; // 先假定第一个数为最小值
            int currentIndex = i;
            for (int j = i + 1; j < arr.length; j++) {
                // 挨个与最小值对比，如果小于，则进行交换
                if (asc) {
                    if (current > arr[j]) {
                        // 如果后面的值比当前的 min 小，则重置为这个数
                        current = arr[j];
                        currentIndex = j;
                    }
                } else {
                    if (current < arr[j]) {
                        // 如果后面的值比当前的 min 大，则重置为这个数
                        current = arr[j];
                        currentIndex = j;
                    }
                }
            }
            // 第 i 轮结束后，得到了最小/大值
            // 将这个值与 arr[i] 交换
            if (currentIndex == i) {
                // 如果最小值未发生改变，则不需要执行后面的交换了
                continue;
            }
            arr[currentIndex] = arr[i];
            arr[i] = current;
        }
    }


    /**
     * 大量数据排序时间测试
     */
    @Test
    public void bulkDataSort() {
        int max = 80_000;
        int[] arr = new int[max];
        for (int i = 0; i < max; i++) {
            arr[i] = (int) (Math.random() * 80_000);
        }

        Instant startTime = Instant.now();
        selectSort(arr, true);
//        System.out.println(Arrays.toString(arr));
        Instant endTime = Instant.now();
        System.out.println("共耗时：" + Duration.between(startTime, endTime).toMillis() + " 毫秒");
    }
}
