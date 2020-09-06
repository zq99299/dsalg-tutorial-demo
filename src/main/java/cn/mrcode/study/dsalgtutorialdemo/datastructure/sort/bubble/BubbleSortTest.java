package cn.mrcode.study.dsalgtutorialdemo.datastructure.sort.bubble;

import org.junit.Test;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;

/**
 * 冒泡排序测试
 */
public class BubbleSortTest {
    /**
     * 为了更好的理解，这里把冒泡排序的演变过程演示出来
     */
    @Test
    public void processDemo() {
        int arr[] = {3, 9, -1, 10, -2};

        // 第 1 趟排序：将最大的数排在最后
        // 总共排序：arr.length - 1
        int temp = 0; // 临时变量，交换的时候使用
        for (int i = 0; i < arr.length - 1; i++) {
            if (arr[i] > arr[i + 1]) {
                temp = arr[i];
                arr[i] = arr[i + 1];
                arr[i + 1] = temp;
            }
        }
        System.out.println("第 1 趟排序后的数组");
        System.out.println(Arrays.toString(arr));

        // 第 2 趟排序：将第 2 大的数排在倒数第 2 位
        // 总共排序：arr.length - 1 - 1  ；
        // 从头开始排序，其他没有变化，只是将排序次数减少了一次
        for (int i = 0; i < arr.length - 1 - 1; i++) {
            if (arr[i] > arr[i + 1]) {
                temp = arr[i];
                arr[i] = arr[i + 1];
                arr[i + 1] = temp;
            }
        }
        System.out.println("第 2 趟排序后的数组");
        System.out.println(Arrays.toString(arr));

        // 第 3 趟排序：将第 3 大的数排在倒数第 3 位
        // 总共排序：arr.length - 1 - 2  ；
        // 从头开始排序，其他没有变化，只是将排序次数减少了 2 次
        for (int i = 0; i < arr.length - 1 - 2; i++) {
            if (arr[i] > arr[i + 1]) {
                temp = arr[i];
                arr[i] = arr[i + 1];
                arr[i + 1] = temp;
            }
        }
        System.out.println("第 3 趟排序后的数组");
        System.out.println(Arrays.toString(arr));

        // 第 4 趟排序：将第 4 大的数排在倒数第 4 位
        // 总共排序：arr.length - 1 - 3  ；
        // 从头开始排序，其他没有变化，只是将排序次数减少了 3 次
        for (int i = 0; i < arr.length - 1 - 3; i++) {
            if (arr[i] > arr[i + 1]) {
                temp = arr[i];
                arr[i] = arr[i + 1];
                arr[i + 1] = temp;
            }
        }
        System.out.println("第 4 趟排序后的数组");
        System.out.println(Arrays.toString(arr));

        // 第 5 趟没有必要，因为这里有 5 个数字，确定了 4 个数字，剩下的那一个就已经出来了
    }

    /**
     * 从上述的 4 趟排序过程来看，循环体都是一样的，只是每次循环的次数在减少，那么就可以如下演变
     */
    @Test
    public void processDemo2() {
        int arr[] = {3, 9, -1, 10, -2};

        // 总共排序：arr.length - 1
        int temp = 0; // 临时变量，交换的时候使用
        for (int j = 0; j < arr.length - 1; j++) {
            for (int i = 0; i < arr.length - 1 - j; i++) {
                if (arr[i] > arr[i + 1]) {
                    temp = arr[i];
                    arr[i] = arr[i + 1];
                    arr[i + 1] = temp;
                }
            }
            System.out.println("第 " + (j + 1) + " 趟排序后的数组");
            System.out.println(Arrays.toString(arr));
        }
    }

    /**
     * 优化：当某一轮未交换位置，则表示序列有序了
     */
    @Test
    public void processDemo3() {
        int arr[] = {3, 9, -1, 10, 20};

        // 总共排序：arr.length - 1
        int temp = 0; // 临时变量，交换的时候使用
        boolean change = false;
        for (int j = 0; j < arr.length - 1; j++) {
            change = false;
            for (int i = 0; i < arr.length - 1 - j; i++) {
                if (arr[i] > arr[i + 1]) {
                    temp = arr[i];
                    arr[i] = arr[i + 1];
                    arr[i + 1] = temp;
                    change = true;
                }
            }
            if (!change) {
                // 如果 1 轮下来，都没有进行排序，则可以提前退出
                break;
            }
            System.out.println("第 " + (j + 1) + " 趟排序后的数组");
            System.out.println(Arrays.toString(arr));
        }
    }

    /**
     * 测试封装后的算法
     */
    @Test
    public void bubbleSortTest() {
        int[] arr = {3, 9, -1, 10, 20};
        System.out.println("排序前：" + Arrays.toString(arr));
        bubbleSort(arr);
        System.out.println("排序后：" + Arrays.toString(arr));
    }

    /**
     * 把排序算法封装成一个方法，方便被复用
     *
     * @param arr
     */
    public static void bubbleSort(int[] arr) {
        // 总共排序：arr.length - 1
        int temp = 0; // 临时变量，交换的时候使用
        boolean change = false;
        for (int j = 0; j < arr.length - 1; j++) {
            change = false;
            for (int i = 0; i < arr.length - 1 - j; i++) {
                if (arr[i] > arr[i + 1]) {
                    temp = arr[i];
                    arr[i] = arr[i + 1];
                    arr[i + 1] = temp;
                    change = true;
                }
            }
            if (!change) {
                // 如果 1 轮下来，都没有进行排序，则可以提前退出
                break;
            }
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
        bubbleSort(arr);
//        System.out.println(Arrays.toString(arr));
        Instant endTime = Instant.now();
        System.out.println("共耗时：" + Duration.between(startTime, endTime).toMillis() + " 毫秒");
    }
}
