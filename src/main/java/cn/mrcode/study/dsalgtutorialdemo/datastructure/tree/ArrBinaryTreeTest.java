package cn.mrcode.study.dsalgtutorialdemo.datastructure.tree;

import org.junit.Test;

/**
 * 顺序存储二叉树
 */
public class ArrBinaryTreeTest {
    /**
     * 前序遍历测试
     */
    @Test
    public void preOrder() {
        int[] arr = new int[]{1, 2, 3, 4, 5, 6, 7};
        ArrBinaryTree tree = new ArrBinaryTree(arr);
        tree.preOrder(0); // 1,2,4,5,3,6,7
    }

    /**
     * 中序遍历测试
     */
    @Test
    public void infixOrder() {
        int[] arr = new int[]{1, 2, 3, 4, 5, 6, 7};
        ArrBinaryTree tree = new ArrBinaryTree(arr);
        tree.infixOrder(0); // 4,2,5,1,6,3,7
    }

    /**
     * 后序遍历测试
     */
    @Test
    public void postOrder() {
        int[] arr = new int[]{1, 2, 3, 4, 5, 6, 7};
        ArrBinaryTree tree = new ArrBinaryTree(arr);
        tree.postOrder(0); // 4,5,2,6,7,3,1
    }

}

class ArrBinaryTree {
    int[] arr;

    public ArrBinaryTree(int[] arr) {
        this.arr = arr;
    }

    /**
     * 前序遍历
     *
     * @param index 就是知识点中的 n,从哪一个节点开始遍历
     */
    public void preOrder(int index) {
        /*
            1. 顺序二叉树 通常只考虑 **完成二叉树**
            2. 第 n 个元素的 **左子节点** 为 `2*n+1`
            3. 第 n 个元素的 **右子节点** 为 `2*n+2`
            4. 第 n 个元素的 **父节点** 为 `(n-1)/2`
         */
        if (arr == null || arr.length == 0) {
            System.out.println("数组为空，不能前序遍历二叉树");
            return;
        }
        // 1. 先输出当前节点（初始节点是 root 节点）
        System.out.println(arr[index]);
        // 2. 如果左子节点不为空，则递归继续前序遍历
        int left = 2 * index + 1;
        if (left < arr.length) {
            preOrder(left);
        }
        // 3. 如果右子节点不为空，则递归继续前序遍历
        int right = 2 * index + 2;
        if (right < arr.length) {
            preOrder(right);
        }
    }

    /**
     * 中序遍历：先遍历左子树，再输出父节点，再遍历右子树
     *
     * @param index
     */
    public void infixOrder(int index) {
        if (arr == null || arr.length == 0) {
            System.out.println("数组为空，不能前序遍历二叉树");
            return;
        }
        int left = 2 * index + 1;
        if (left < arr.length) {
            infixOrder(left);
        }
        System.out.println(arr[index]);
        int right = 2 * index + 2;
        if (right < arr.length) {
            infixOrder(right);
        }
    }

    /**
     * 后序遍历：先遍历左子树，再遍历右子树，最后输出父节点
     *
     * @param index
     */
    public void postOrder(int index) {
        if (arr == null || arr.length == 0) {
            System.out.println("数组为空，不能前序遍历二叉树");
            return;
        }
        int left = 2 * index + 1;
        if (left < arr.length) {
            postOrder(left);
        }
        int right = 2 * index + 2;
        if (right < arr.length) {
            postOrder(right);
        }
        System.out.println(arr[index]);
    }
}
