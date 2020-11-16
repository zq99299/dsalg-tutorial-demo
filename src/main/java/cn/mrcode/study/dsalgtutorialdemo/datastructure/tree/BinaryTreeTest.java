package cn.mrcode.study.dsalgtutorialdemo.datastructure.tree;

import org.junit.Test;

/**
 * 二叉树测试
 */
public class BinaryTreeTest {

    // 先编写二叉树节点
    class HeroNode {
        public int id;
        public String name;
        public HeroNode left;
        public HeroNode right;

        public HeroNode(int id, String name) {
            this.id = id;
            this.name = name;
        }

        @Override
        public String toString() {
            return "HeroNode{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    '}';
        }

        /**
         * 前序遍历
         */
        public void preOrder() {
            // 1. 先输出当前节点
            System.out.println(this);
            // 2. 如果左子节点不为空，则递归继续前序遍历
            if (this.left != null) {
                this.left.preOrder();
            }
            // 3. 如果右子节点不为空，则递归继续前序遍历
            if (this.right != null) {
                this.right.preOrder();
            }
        }

        /**
         * 中序遍历
         */
        public void infixOrder() {
            // 1. 如果左子节点不为空，则递归继续前序遍历
            if (this.left != null) {
                this.left.infixOrder();
            }
            // 2. 先输出当前节点
            System.out.println(this);

            // 3. 如果右子节点不为空，则递归继续前序遍历
            if (this.right != null) {
                this.right.infixOrder();
            }
        }

        /**
         * 后序遍历
         */
        public void postOrder() {
            // 1. 如果左子节点不为空，则递归继续前序遍历
            if (this.left != null) {
                this.left.postOrder();
            }
            // 2. 如果右子节点不为空，则递归继续前序遍历
            if (this.right != null) {
                this.right.postOrder();
            }
            // 3. 先输出当前节点
            System.out.println(this);
        }
    }

    // 编写二叉树对象
    class BinaryTree {
        public HeroNode root;

        /**
         * 前序遍历
         */
        public void preOrder() {
            if (root == null) {
                System.out.println("二叉树为空");
                return;
            }
            root.preOrder();
        }

        /**
         * 中序遍历
         */
        public void infixOrder() {
            if (root == null) {
                System.out.println("二叉树为空");
                return;
            }
            root.infixOrder();
        }

        /**
         * 后续遍历
         */
        public void postOrder() {
            if (root == null) {
                System.out.println("二叉树为空");
                return;
            }
            root.postOrder();
        }
    }

    // 编写二叉树对象

    /**
     * 前、中、后 遍历测试
     */
    @Test
    public void fun1() {
        // 创建节点与构建二叉树
        HeroNode n1 = new HeroNode(1, "宋江");
        HeroNode n2 = new HeroNode(2, "无用");
        HeroNode n3 = new HeroNode(3, "卢俊");
        HeroNode n4 = new HeroNode(4, "林冲");
        n1.left = n2;
        n1.right = n3;
        n3.right = n4;
        BinaryTree binaryTree = new BinaryTree();
        binaryTree.root = n1;

        System.out.println("\n 前序遍历：");
        binaryTree.preOrder();
        System.out.println("\n 中序遍历：");
        binaryTree.infixOrder();
        System.out.println("\n 后序遍历：");
        binaryTree.postOrder();
    }

    /**
     * 考题：给卢俊新增一个 left 节点，然后打印前、中、后 遍历顺序
     */
    @Test
    public void fun2() {
        // 创建节点与构建二叉树
        HeroNode n1 = new HeroNode(1, "宋江");
        HeroNode n2 = new HeroNode(2, "无用");
        HeroNode n3 = new HeroNode(3, "卢俊");
        HeroNode n4 = new HeroNode(4, "林冲");
        HeroNode n5 = new HeroNode(5, "关胜");
        n1.left = n2;
        n1.right = n3;
        n3.right = n4;
        n3.left = n5;
        BinaryTree binaryTree = new BinaryTree();
        binaryTree.root = n1;

        System.out.println("\n 前序遍历：");
        binaryTree.preOrder();
        System.out.println("\n 中序遍历：");
        binaryTree.infixOrder();
        System.out.println("\n 后序遍历：");
        binaryTree.postOrder();
    }
}
