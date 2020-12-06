package cn.mrcode.study.dsalgtutorialdemo.datastructure.tree;

import org.junit.Test;

/**
 * 线索化二叉树
 */
public class ThreadedBinaryTreeTest {
    class HeroNode {
        public int id;
        public String name;
        public HeroNode left;
        public HeroNode right;
        /**
         * 左节点的类型：0：左子树，1：前驱节点
         */
        public int leftType;
        /**
         * 右节点的类型：0：右子树，1：后继节点
         */
        public int rightType;

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
    }

    class ThreadedBinaryTree {
        public HeroNode root;
        public HeroNode pre; // 保留上一个节点

        /**
         * 线索化二叉树：以 中序的方式线索化
         */
        public void threadeNodes() {
            // 从 root 开始遍历，然后 线索化
            this.threadeNodes(root);
        }

        private void threadeNodes(HeroNode node) {
            if (node == null) {
                return;
            }
            // 中序遍历顺序：先左、自己、再右
            threadeNodes(node.left);
            // 难点就是在这里，如何线索化自己
            // 当自己的 left 节点为空，则设置为前驱节点
            if (node.left == null) {
                node.left = pre;
                node.leftType = 1;
            }

            // 因为要设置后继节点，只有回到自己的后继节点的时候，才能把自己设置为前一个的后继节点
            // 当前一个节点的 right 为空时，则需要自己是后继节点
            if (pre != null && pre.right == null) {
                pre.right = node;
                pre.rightType = 1;
            }

            // 数列： 1,3,6,8,10,14
            // 中序： 8,3,10,1,14,6
            // 这里最好结合图示的二叉树来看，容易理解
            // 因为中序遍历，先遍历左边，所以 8 是第一个输出的节点
            // 当 node = 8 时，pre 还没有被赋值过，则为空。这是正确的，因为 8 就是第一个节点
            // 当 8 处理完成之后，处理 3 时
            // 当 node = 3 时，pre 被赋值为 8 了。
            pre = node;
            threadeNodes(node.right);
        }

        /**
         * 遍历线索化二叉树
         */
        public void threadedList() {
            // 前面线索化使用的是中序，这里也同样要用中序的方式
            // 但是不适合使用之前那种递归了
            HeroNode node = root;
            while (node != null) {
                // 中序：左、自己、右
                // 数列： 1,3,6,8,10,14
                // 中序： 8,3,10,1,14,6
                // 那么先找到左边的第一个线索化节点，也就是 8. 对照图示理解，比较容易
                while (node.leftType == 0) {
                    node = node.left;
                }
                // 找到这个线索化节点之后，打印它
                System.out.println(node);

                // 如果该节点右子节点也是线索化节点，则打印它
                while (node.rightType == 1) {
                    node = node.right;
                    System.out.println(node);
                }

                // 到达这里，就说明遇到的不是一个 线索化节点了
                // 而且，按中序的顺序来看：这里应该处理右侧了
                node = node.right;
            }
        }

        public void preOrderThreadeNodes() {
            preOrderThreadeNodes(root);
        }

        /**
         * 前序线索化二叉树
         */
        public void preOrderThreadeNodes(HeroNode node) {
            // 前序：自己、左（递归）、右（递归）
            // 数列： 1,3,6,8,10,14
            // 前序： 1,3,8,10,6,14

            if (node == null) {
                return;
            }

            System.out.println(node);
            // 当自己的 left 节点为空，则可以线索化
            if (node.left == null) {
                node.left = pre;
                node.leftType = 1;
            }
            // 当前一个节点 right 为空，则可以把自己设置为前一个节点的后继节点
            if (pre != null && pre.right == null) {
                pre.right = node;
                pre.rightType = 1;
            }

            // 因为是前序，因此 pre 保存的是自己
            // 到下一个节点的时候，下一个节点如果是线索化节点 ，才能将自己作为它的前驱节点
            pre = node;

            // 那么继续往左，查找符合可以线索化的节点
            // 因为先处理的自己，如果 left == null，就已经线索化了
            // 再往左的时候，就不能直接进入了
            // 需要判定，如果不是线索化节点，再进入
            // 比如：当前节点 8，前驱 left 被设置为了 3
            // 这里节点 8 的 left 就为 1 了，就不能继续递归，否则又回到了节点 3 上
            // 导致死循环了。
            if (node.leftType == 0) {
                preOrderThreadeNodes(node.left);
            }
            if (node.rightType == 0) {
                preOrderThreadeNodes(node.right);
            }
        }

        /**
         * 前序线索化二叉树遍历
         */
        public void preOrderThreadeList() {
            HeroNode node = root;
            // 最后一个节点无后继节点，就会退出了
            // 前序：自己、左（递归）、右（递归）
            while (node != null) {
                // 先打印自己
                System.out.println(node);

                while (node.leftType == 0) {
                    node = node.left;
                    System.out.println(node);
                }
                while (node.rightType == 1) {
                    node = node.right;
                    System.out.println(node);
                }
                node = node.right;
            }
        }
    }

    @Test
    public void threadeNodesTest() {
        // 1,3,6,8,10,14
        HeroNode n1 = new HeroNode(1, "宋江");
        HeroNode n3 = new HeroNode(3, "无用");
        HeroNode n6 = new HeroNode(6, "卢俊");
        HeroNode n8 = new HeroNode(8, "林冲2");
        HeroNode n10 = new HeroNode(10, "林冲3");
        HeroNode n14 = new HeroNode(14, "林冲4");
        n1.left = n3;
        n1.right = n6;
        n3.left = n8;
        n3.right = n10;
        n6.left = n14;

        ThreadedBinaryTree tree = new ThreadedBinaryTree();
        tree.root = n1;

        tree.threadeNodes();

        // 验证：
        HeroNode left = n10.left;
        HeroNode right = n10.right;
        System.out.println("10 号节点的前驱节点：" + left.id);
        System.out.println("10 号节点的后继节点：" + right.id);
    }

    /**
     * 中序：线索化遍历测试
     */
    @Test
    public void threadedListTest() {
        ThreadedBinaryTree tree = buildTree();
        tree.threadeNodes();
        tree.threadedList(); // 8,3,10,1,14,6
    }

    /**
     * 前序线索化
     */
    @Test
    public void preOrderThreadedNodesTest() {
        // 1,3,6,8,10,14
        HeroNode n1 = new HeroNode(1, "宋江");
        HeroNode n3 = new HeroNode(3, "无用");
        HeroNode n6 = new HeroNode(6, "卢俊");
        HeroNode n8 = new HeroNode(8, "林冲2");
        HeroNode n10 = new HeroNode(10, "林冲3");
        HeroNode n14 = new HeroNode(14, "林冲4");
        n1.left = n3;
        n1.right = n6;
        n3.left = n8;
        n3.right = n10;
        n6.left = n14;

        ThreadedBinaryTree tree = new ThreadedBinaryTree();
        tree.root = n1;

        tree.preOrderThreadeNodes();

        // 验证： 前序顺序： 1,3,8,10,6,14
        HeroNode left = n10.left;
        HeroNode right = n10.right;
        System.out.println("10 号节点的前驱节点：" + left.id); // 8
        System.out.println("10 号节点的后继节点：" + right.id); // 6

        left = n6.left;
        right = n6.right;
        System.out.println("6 号节点的前驱节点：" + left.id); // 14, 普通节点
        System.out.println("6 号节点的后继节点：" + right.id); // 14,线索化节点
    }

    @Test
    public void preOrderThreadeListTest() {
        ThreadedBinaryTree tree = buildTree();
        tree.preOrderThreadeNodes();
        System.out.println("前序线索化遍历");
        tree.preOrderThreadeList(); // 1,3,8,10,6,14
    }

    /**
     * 构建一颗数
     *
     * @return
     */
    private ThreadedBinaryTree buildTree() {
        // 1,3,6,8,10,14
        HeroNode n1 = new HeroNode(1, "宋江");
        HeroNode n3 = new HeroNode(3, "无用");
        HeroNode n6 = new HeroNode(6, "卢俊");
        HeroNode n8 = new HeroNode(8, "林冲2");
        HeroNode n10 = new HeroNode(10, "林冲3");
        HeroNode n14 = new HeroNode(14, "林冲4");
        n1.left = n3;
        n1.right = n6;
        n3.left = n8;
        n3.right = n10;
        n6.left = n14;

        ThreadedBinaryTree tree = new ThreadedBinaryTree();
        tree.root = n1;
        return tree;
    }
}
