package cn.mrcode.study.dsalgtutorialdemo.datastructure.tree;

import org.junit.Test;

import java.util.Arrays;

/**
 * 堆排序
 */
public class HeapSortTest {
    @Test
    public void processSortTest() {
        int[] arr = {4, 6, 8, 5, 9};
        processSort(arr);
    }

    private void processSort(int[] arr) {
        // 第一次：从最后一个非叶子节点开始调整，从左到右，从上到下进行调整。
        // 参与比较的元素是：6,5,9
        int i = 1;
        int temp = arr[i]; // 6
        // 如果 i 的左比右大
        int k = i * 2 + 1; // i 的左节点

        // 要将这三个数（堆），调整为一个大顶堆
        // i 的左节点小于右节点
        if (arr[k] < arr[k + 1]) {
            k++; // 右边的大，则将 k 变成最大的那一个
        }
        // 如果左右中最大的一个数，比 i 大。则调整它
        if (arr[k] > temp) {
            arr[i] = arr[k];
            arr[k] = temp;
        }
        System.out.println(Arrays.toString(arr)); // 4,9,8,5,6

        // 第二次调整：参与比较的元素是  4,9,5
        i = 0;
        temp = arr[i]; // 4
        k = i * 2 + 1;
        if (arr[k] < arr[k + 1]) {
            k++; // 右边的大，则将 k 变成最大的那一个
        }
        // 9 比 4 大，交换的是 9 和 4
        if (arr[k] > temp) {
            arr[i] = arr[k];
            arr[k] = temp;
        }
        System.out.println(Arrays.toString(arr)); // 9，4,8,5,6

        // 上面调整导致了，第一次的堆：4,5,6 的混乱。这里要对他进行重新调整
        i = 1;
        temp = arr[i]; // 4
        k = i * 2 + 1;
        if (arr[k] < arr[k + 1]) {
            k++; // 右边的大，则将 k 变成最大的那一个
        }
        // 6 比 4 大，交换它
        if (arr[k] > temp) {
            arr[i] = arr[k];
            arr[k] = temp;
        }
        System.out.println(Arrays.toString(arr)); // 9，6,8,5,4
        // 到这里就构造成了一个大顶堆
    }

    @Test
    public void sortTest() {
        int[] arr = {4, 6, 8, 5, 9};
        sort(arr);
        int[] arr2 = {99, 4, 6, 8, 5, 9, -1, -2, 100};
        sort(arr2);
    }


    private void sort(int[] arr) {
        // =====  1. 构造初始堆
        // 从第一个非叶子节点开始调整
        // 4,9,8,5,6
        //  adjustHeap(arr, arr.length / 2 - 1, arr.length);

        // 循环调整
        // 从第一个非叶子节点开始调整，自低向上
        for (int i = arr.length / 2 - 1; i >= 0; i--) {
            adjustHeap(arr, i, arr.length);
        }
        // 第一轮调整了 3 个堆后：结果为：9，6,8,5,4
        // System.out.println(Arrays.toString(arr));

        // 2. 将堆顶元素与末尾元素进行交换，然后再重新调整
        int temp = 0;
        for (int j = arr.length - 1; j > 0; j--) {
            temp = arr[j];  // j 是末尾元素
            arr[j] = arr[0];
            arr[0] = temp;
            // 这里是从第一个节点开始: 不是构建初始堆了
            // 如果
            adjustHeap(arr, 0, j);
        }
        System.out.println(Arrays.toString(arr));
    }

    /**
     * 调整堆
     *
     * @param arr
     * @param i      非叶子节点，以此节点为基础，将它、它的左、右，调整为一个大顶堆
     * @param length
     */
    private void adjustHeap(int[] arr, int i, int length) {
        // 难点是将当前的堆调整之后，影响了它后面节点堆的混乱，如何继续对影响的堆进行调整
        // 所以第一步中：是额外循环的从 低向上调整的
        //    第三步中：就是本代码的，从上到下调整的；这个很重要，一定要明白
        int temp = arr[i];
        // 从传入节点的左节点开始处理，下一次则是以该节点为顶堆的左节点进行调整
        for (int k = i * 2 + 1; k < length; k = k * 2 + 1) {
            // 要将这三个数（堆），调整为一个大顶堆
            // i 的左节点小于右节点
            // k+1 < length : 当调整长度为 2 时，也就是数组的前两个元素，其实它没有第三个节点了，就不能走这个判定
            if (k + 1 < length && arr[k] < arr[k + 1]) {
                k++; // 右边的大，则将 k 变成最大的那一个
            }
            // 如果左右中最大的一个数，比 i 大。则调整它
            if (arr[k] > temp) {
                arr[i] = arr[k];
                i = k; // i 记录被调整后的索引。
            } else {
                break;
                // 由于初始堆，就已经是大顶堆了，每个子堆的顶，都是比他的左右两个大的
                // 当这里没有进行调整的话，那么就可以直接退出了
                // 如果上面进行了调整。那么在初始堆之后，每次都是从 0 节点开始 自左到右，自上而下调整的
                //    就会一层一层的往下进行调整
            }
        }
        arr[i] = temp;
    }
}
