package cn.mrcode.study.dsalgtutorialdemo.datastructure.search;

import org.junit.Test;

import java.util.Arrays;

public class FibonacciSearchTest {
    @Test
    public void fibTest() {
        int[] arr = {1, 8, 10, 89, 1000, 1234};
        System.out.println("原数组：" + Arrays.toString(arr));
        int findVal = 1;
        int result = fibSearch(arr, findVal);
        System.out.println("查找值 " + findVal + "：" + (result == -1 ? "未找到" : "找到值，索引为：" + result));

        findVal = -1;
        result = fibSearch(arr, findVal);
        System.out.println("查找值 " + findVal + "：" + (result == -1 ? "未找到" : "找到值，索引为：" + result));

        findVal = 8;
        result = fibSearch(arr, findVal);
        System.out.println("查找值 " + findVal + "：" + (result == -1 ? "未找到" : "找到值，索引为：" + result));

        findVal = 10;
        result = fibSearch(arr, findVal);
        System.out.println("查找值 " + findVal + "：" + (result == -1 ? "未找到" : "找到值，索引为：" + result));

        findVal = 1000;
        result = fibSearch(arr, findVal);
        System.out.println("查找值 " + findVal + "：" + (result == -1 ? "未找到" : "找到值，索引为：" + result));

        findVal = 1234;
        result = fibSearch(arr, findVal);
        System.out.println("查找值 " + findVal + "：" + (result == -1 ? "未找到" : "找到值，索引为：" + result));

        findVal = 12345;
        result = fibSearch(arr, findVal);
        System.out.println("查找值 " + findVal + "：" + (result == -1 ? "未找到" : "找到值，索引为：" + result));

    }

    public static int max_size = 20;

    private int fibSearch(int[] arr, int key) {
        // 构建一个斐波那契数列
        int[] f = fib();
        // 查找 k，由数组长度，找到在斐波那契数列中的一个值
        int k = 0;
        int low = 0;
        int high = arr.length - 1;
        while (high > f[k] - 1) {
            k++;
        }

        // 构建临时数组
        int[] temp = Arrays.copyOf(arr, f[k]);
        // 将临时数组扩充的值用原始数组的最后一个值（最大值）填充
        for (int i = high + 1; i < temp.length; i++) {
            temp[i] = arr[high];
        }

        int mid = 0;
        // 当两边没有交叉的时候，就都可以继续查找
        while (low <= high) {
            if (k == 0) {
                // 如果 k = 0 的话，就只有一个元素了，mid 则就是这个元素
                mid = low;
            } else {
                mid = low + f[k - 1] - 1;
            }
            // 要查找的值说明在数组的左侧
            if (key < temp[mid]) {
                high = mid - 1;
                // 1. 全部元素 = 前面的元素 + 后面的元素
                // 2. f[k] = f[k-1] + f[k-2]
                // k -1 , 得到这一段的个数，然后下一次按照这个个数进行黄金分割
                k--;
            }
            // 要查找的值在数组的右侧
            else if (key > temp[mid]) {
                low = mid + 1;
                k -= 2;
            }
            // 找到的话
            else {
                if (mid <= high) {
                    return mid;
                }
                // 当 mid 值大于最高点的话
                // 也就是我们后面填充的值，其实他的索引就是最后一个值，也就是 high
                else {
                    return high;
                }
            }
        }
        return -1;
    }

    private int[] fib() {
        int[] f = new int[20];
        f[0] = 1;
        f[1] = 1;
        for (int i = 2; i < max_size; i++) {
            f[i] = f[i - 1] + f[i - 2];
        }
        return f;
    }
}
