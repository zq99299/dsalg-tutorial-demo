package cn.mrcode.study.dsalgtutorialdemo.datastructure.sort.shell;

import cn.mrcode.study.dsalgtutorialdemo.datastructure.sort.insertion.InsertionSortTest;
import org.junit.Test;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;

/**
 * 希尔排序
 */
public class ShellSortTest {
    /**
     * 推到的方式来演示每一步怎么做，然后找规律
     */
    @Test
    public void processDemo() {
        int arr[] = {8, 9, 1, 7, 2, 3, 5, 4, 6, 0};
        System.out.println("原始数组：" + Arrays.toString(arr));
        processShellSort(arr);
    }

    public void processShellSort(int[] arr) {
        // 按照笔记中的基本思想，一共三轮
        // 第 1 轮：初始数组 [8, 9, 1, 7, 2, 3, 5, 4, 6, 0]
        // 将 10 个数字分成了 5 组( length / 2)，增量也是 5，需要对 5 组进行排序
        // 外层循环，并不是循环 5 次，只是这里巧合了。
        // 一定要记得，希尔排序：先分组，在对组进行插入排序
        for (int i = 5; i < arr.length; i++) {
            // 第 1 组：[8,3] , 分别对应原始数组的下标 0,5
            // 第 2 组：[9,5] , 分别对应原始数组的下标 1,6
            // ...
            // 内层循环对 每一组 进行直接排序操作
            // i = 5 ：j = 0, j-=5 = 0 - 5 = -5,跳出循环，这是对第 1 组进行插入排序
            // i = 6 ：j = 1, j-=5 = 0 - 1 = -1,跳出循环，这是对第 2 组进行插入排序
            // i = 9 ：j = 4, j-=5 = 0 - 4 = -4,跳出循环，这是对第 3 组进行插入排序
            for (int j = i - 5; j >= 0; j -= 5) {
                if (arr[j] > arr[j + 5]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 5];
                    arr[j + 5] = temp;
                }
            }
        }
        System.out.println("第 1 轮排序后：" + Arrays.toString(arr));

        // 第 2 轮：上一轮排序后的数组：[3, 5, 1, 6, 0, 8, 9, 4, 7, 2]
        // 将 10 个数字分成了 2 组（上一次的增量 5 / 2），增量也为 2，需要对 2 组进行排序
        for (int i = 2; i < arr.length; i++) {
            // 第 1 组：[3,1,0,9,7] , 分别对应原始数组的下标 0,2,4,6,8
            // 第 2 组：[5,6,8,4,2] , 分别对应原始数组的下标 1,3,5,7,9
            // ...
            // 内层循环对 每一组 进行直接排序操作
            // i = 2 ：j = 0, j-=2 = 0 - 2 = -2，跳出循环，
            //      这是对第 1 组中的 3,1 进行比较，1 为无序列表中的比较元素，3 为有序列表中的最后一个元素，3 > 1，进行交换
            //      交换后的数组：[1, 5, 3, 6, 0, 8, 9, 4, 7, 2]
            //      第 1 组：[1,3,0,9,7]
            // i = 3 ：j = 1, j-=2 = 1 - 2 = -1,跳出循环
            //      这是对第 2 组中的 5,6 进行比较，6 为无序列表中的比较元素，5 为有序列表中的最后一个元素，5 < 6，不进行交换
            //      交换后的数组：[1, 5, 3, 6, 0, 8, 9, 4, 7, 2] , 没有交换
            //      第 2 组：[5,6,8,4,2]
            // i = 4 ：j = 2, j-=2 = 2 - 2 = 0，
            //      这是对第 1 组中的 3,0 进行比较，0 为无序列表中的比较元素，3 为有序列表中的最后一个元素，3 > 0，进行交换
            //      交换后的数组：[1, 5, 0, 6, 3, 8, 9, 4, 7, 2]，
            //           第 1 组：[1,0,3,9,7]
            //      由于 2 - 2 = 0，此时 j = 0，满足条件，继续循环 i = 4 ：j = 0, j-=2 = 0 - 2 = -2，
            //      这是对第 1 组中的有序列表中的剩余数据进行交换，1,0, 1>0 ，他们进行交换
            //           第 2 组：[0,1,3,9,7]
            for (int j = i - 2; j >= 0; j -= 2) {
                if (arr[j] > arr[j + 2]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 2];
                    arr[j + 2] = temp;
                }
            }
        }
        System.out.println("第 2 轮排序后：" + Arrays.toString(arr));

        // 第 3 轮：上一轮排序后的数组：[0, 2, 1, 4, 3, 5, 7, 6, 9, 8]
        // 将 10 个数字分成了 1 组（上一次的增量 2 / 2），增量也为 1，需要对 1 组进行排序
        for (int i = 1; i < arr.length; i++) {
            // 第 1 组：[0, 2, 1, 4, 3, 5, 7, 6, 9, 8]
            // i = 1 ：j = 0, j-=1 = 0 - 1 = -1，跳出循环
            //      0 为有序列表中的最后一个元素，2 为无须列表中要比较的元素。 0 < 2,不交换
            //      [0, 2    有序 <-> 无序, 1, 4, 3, 5, 7, 6, 9, 8]
            // i = 2 ：j = 1, j-=1 = 1 - 1 = o
            //      2 为有序列表中的最后一个元素，1 为无序列表中要比较的元素， 2 > 1,交换
            //      交换后：[0, 1, 2,     4, 3, 5, 7, 6, 9, 8]
            //      由于不退出循环，还要比较有序列表中的数据，0 与 1
            for (int j = i - 1; j >= 0; j -= 1) {
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
        System.out.println("第 3 轮排序后：" + Arrays.toString(arr));
    }

    @Test
    public void shellSortTest() {
        int arr[] = {8, 9, 1, 7, 2, 3, 5, 4, 6, 0};
        System.out.println("原始数组：" + Arrays.toString(arr));
        shellSort(arr);
    }

    /**
     * 根据前面的分析，得到规律，变化的只是增量步长，那么可以改写为如下方式
     */
    public void shellSort(int[] arr) {
        int temp = 0;
        // 第 1 层循环：得到每一次的增量步长
        for (int gap = arr.length / 2; gap > 0; gap /= 2) {
            // 第 2 层和第 3 层循环，是对每一个增量中的每一组进行插入排序
            for (int i = gap; i < arr.length; i++) {
                for (int j = i - gap; j >= 0; j -= gap) {
                    if (arr[j] > arr[j + gap]) {
                        temp = arr[j];
                        arr[j] = arr[j + gap];
                        arr[j + gap] = temp;
                    }
                }
            }
//            System.out.println("增量为 " + gap + " 的这一轮排序后：" + Arrays.toString(arr));
        }
    }

    /**
     * 大量数据排序时间测试
     */
    @Test
    public void bulkDataSort() {
        int max = 80_000;
//        int max = 8;
        int[] arr = new int[max];
        for (int i = 0; i < max; i++) {
            arr[i] = (int) (Math.random() * 80_000);
        }

        Instant startTime = Instant.now();
        shellSort(arr);
//        System.out.println(Arrays.toString(arr));
        Instant endTime = Instant.now();
        System.out.println("共耗时：" + Duration.between(startTime, endTime).toMillis() + " 毫秒");
    }


    /**
     * 移动法希尔排序
     */
    @Test
    public void moveShellSortTest() {
        int arr[] = {8, 9, 1, 7, 2, 3, 5, 4, 6, 0};
        System.out.println("原始数组：" + Arrays.toString(arr));
        moveShellSort(arr);
    }

    /**
     * 插入排序采用移动法
     */
    public void moveShellSort(int[] arr) {
        // 第 1 层循环：得到每一次的增量步长
        for (int gap = arr.length / 2; gap > 0; gap /= 2) {
            /**
             这里的内层循环，除了是获得每一组的值（按增量取），
             移动法使用的是简单插入排序的算法 {@link InsertionSortTest#processSelectSort2(int[])}
             唯一不同的是，这里的组前一个是按增量来计算的
             */
            // 每一轮，都是针对某一个组的插入排序中：待排序的起点
            for (int i = gap; i < arr.length; i++) {
                int currentInsertValue = arr[i]; // 无序列表中的第一个元素
                int insertIndex = i - gap; // 有序列表中的最后一个元素
                while (insertIndex >= 0
                        && currentInsertValue < arr[insertIndex]) {
                    // 比较的数比前一个数小，则前一个往后移动
                    arr[insertIndex + gap] = arr[insertIndex];
                    insertIndex -= gap;
                }
                // 对找到的位置插入值
                arr[insertIndex + gap] = currentInsertValue;
            }
//            System.out.println("增量为 " + gap + " 的这一轮排序后：" + Arrays.toString(arr));
        }
    }

    /**
     * 移动法，大数据量测试速度
     */
    @Test
    public void moveBulkDataSort() {
        int max = 80_000;
//        int max = 8;
        int[] arr = new int[max];
        for (int i = 0; i < max; i++) {
            arr[i] = (int) (Math.random() * 80_000);
        }

        Instant startTime = Instant.now();
        moveShellSort(arr);
//        System.out.println(Arrays.toString(arr));
        Instant endTime = Instant.now();
        System.out.println("共耗时：" + Duration.between(startTime, endTime).toMillis() + " 毫秒");
    }
}
