package cn.mrcode.study.dsalgtutorialdemo.datastructure.sort.radix;

import org.junit.Test;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;

/**
 * 基数排序
 */
public class RadixSortTest {
    /**
     * 推导：推导每一步的状态，然后找规律
     */
    @Test
    public void processDemo() {
        int arr[] = {53, 3, 542, 748, 14, 214};
        System.out.println("原始数组：" + Arrays.toString(arr));
        processRadixSort(arr);
    }

    public void processRadixSort(int[] arr) {
        // 第一轮
        // 1. 将每个元素的 个位数 取出，然后放到对应的桶中（桶为一个一维数组）
        // 2. 按照这个桶的顺序，依次取出数据，放回原来的数组

        // 定义 10 个桶，每个桶是一个一维数组
        // 由于无法知道每个桶需要多少个元素，所以声明为数组长度
        // 加入：10 个数字都是 1，那么只会分配到同一个通中
        int[][] buckets = new int[10][arr.length];
        // 定义每个桶中有效的数据个数
        // 桶长度为数组大小，那么每一个桶中存放了几个有效的元素呢？就需要有这个变量来指示
        int[] bucketCounts = new int[buckets.length];

        // 开始第一轮的代码实现
        // 1. 将每个元素的 个位数 取出，然后放到对应的桶中（桶为一个一维数组）
        for (int i = 0; i < arr.length; i++) {
            // 获取到个位数
            int temp = arr[i] % 10;
            // 根据规则，将该数放到对应的桶中
            buckets[temp][bucketCounts[temp]] = arr[i];
            // 并将该桶的有效个数+1
            bucketCounts[temp]++;
        }
        // 2. 按照这个桶的顺序，依次取出数据，放回原来的数组
        int index = 0; // 标识当前放回原数组的哪一个了
        for (int i = 0; i < buckets.length; i++) {
            if (bucketCounts[i] == 0) {
                // 标识该桶无数据
                continue;
            }
            for (int j = 0; j < bucketCounts[i]; j++) {
                arr[index++] = buckets[i][j];
            }
            // 取完数据后，要重置每个桶的有效数据个数
            bucketCounts[i] = 0;
        }
        System.out.println("第一轮排序后：" + Arrays.toString(arr));

        // 第 2 轮：比较十位数
        for (int i = 0; i < arr.length; i++) {
            // 获取到十位数
            int temp = arr[i] / 10 % 10;
            buckets[temp][bucketCounts[temp]] = arr[i];
            bucketCounts[temp]++;
        }
        index = 0; // 标识当前放回原数组的哪一个了
        for (int i = 0; i < buckets.length; i++) {
            if (bucketCounts[i] == 0) {
                continue;
            }
            for (int j = 0; j < bucketCounts[i]; j++) {
                arr[index++] = buckets[i][j];
            }
            bucketCounts[i] = 0;
        }
        System.out.println("第二轮排序后：" + Arrays.toString(arr));

        // 第 3 轮：比较百位数
        for (int i = 0; i < arr.length; i++) {
            // 获取到十位数
            int temp = arr[i] / 100 % 10;
            buckets[temp][bucketCounts[temp]] = arr[i];
            bucketCounts[temp]++;
        }
        index = 0; // 标识当前放回原数组的哪一个了
        for (int i = 0; i < buckets.length; i++) {
            if (bucketCounts[i] == 0) {
                continue;
            }
            for (int j = 0; j < bucketCounts[i]; j++) {
                arr[index++] = buckets[i][j];
            }
            bucketCounts[i] = 0;
        }
        System.out.println("第三轮排序后：" + Arrays.toString(arr));
    }

    @Test
    public void radixSortTest() {
        int arr[] = {53, 3, 542, 748, 14, 214};
        System.out.println("原始数组：" + Arrays.toString(arr));
        radixSort(arr);
        System.out.println("排序后：" + Arrays.toString(arr));
    }

    /**
     * 根据推导规律，整理出完整算法
     *
     * @param arr
     */
    public void radixSort(int[] arr) {
        // 1. 得到数组中的最大值，并获取到该值的位数。用于循环几轮
        int max = arr[0];
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] > max) {
                max = arr[i];
            }
        }
        // 得到位数
        int maxLength = (max + "").length();

        // 定义桶 和 标识桶中元素个数
        int[][] bucket = new int[10][arr.length];
        int[] bucketCounts = new int[bucket.length];

        // 总共需要进行 maxLength 轮
        for (int k = 1, n = 1; k <= maxLength; k++, n *= 10) {
            // 进行桶排序
            for (int i = 0; i < arr.length; i++) {
                // 获取该轮的桶索引：每一轮按 10 的倍数递增，获取到对应数位数
                // 这里额外使用一个步长为 10 的变量 n 来得到每一次递增后的值
                int bucketIndex = arr[i] / n % 10;
                // 放入该桶中
                bucket[bucketIndex][bucketCounts[bucketIndex]] = arr[i];
                // 标识该桶元素多了一个
                bucketCounts[bucketIndex]++;
            }
            // 将桶中元素获取出来，放到原数组中
            int index = 0;
            for (int i = 0; i < bucket.length; i++) {
                if (bucketCounts[i] == 0) {
                    // 该桶无有效元素，跳过不获取
                    continue;
                }
                // 获取桶中有效的个数
                for (int j = 0; j < bucketCounts[i]; j++) {
                    arr[index++] = bucket[i][j];
                }
                // 取完后，重置该桶的元素个数为 0 ，下一次才不会错乱数据
                bucketCounts[i] = 0;
            }
//            System.out.println("第" + k + "轮排序后：" + Arrays.toString(arr));
        }
    }

    /**
     * 大量数据排序时间测试
     */
    @Test
    public void bulkDataSort() {
        int max = 80_0000;
//        max = 8;
        int[] arr = new int[max];
        for (int i = 0; i < max; i++) {
            arr[i] = (int) (Math.random() * 80_000);
        }
        if (arr.length < 10) {
            System.out.println("原始数组：" + Arrays.toString(arr));
        }
        Instant startTime = Instant.now();
        radixSort(arr);
        if (arr.length < 10) {
            System.out.println("排序后：" + Arrays.toString(arr));
        }
        Instant endTime = Instant.now();
        System.out.println("共耗时：" + Duration.between(startTime, endTime).toMillis() + " 毫秒");
    }
}
