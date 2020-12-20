package cn.mrcode.study.dsalgtutorialdemo.datastructure.avl;

import org.junit.Test;

/**
 * 平衡二叉树
 */
public class AvlTreeTest {
    /**
     * 树高度测试
     */
    @Test
    public void heightTest() {
        AvlTree tree = new AvlTree();
        int[] arr = {4, 3, 6, 5, 7, 8};
        for (int i = 0; i < arr.length; i++) {
            tree.add(new Node(arr[i]));
        }
        tree.infixOrder();
        System.out.println("树高度：" + tree.root.height());   // 4
        System.out.println("左树高度：" + tree.root.leftHeight());  // 1
        System.out.println("右树高度：" + tree.root.rightHeight()); // 3
    }

    /**
     * 左旋转测试
     */
    @Test
    public void leftRotatedTest() {
        AvlTree tree = new AvlTree();
        int[] arr = {4, 3, 6, 5, 7, 8};
        for (int i = 0; i < arr.length; i++) {
            tree.add(new Node(arr[i]));
        }
        tree.infixOrder();
        System.out.println("树高度：" + tree.root.height());   // 3
        System.out.println("左树高度：" + tree.root.leftHeight());  // 2
        System.out.println("右树高度：" + tree.root.rightHeight()); // 2
    }

    /**
     * 右旋转测试
     */
    @Test
    public void rightRotatedTest() {
        AvlTree tree = new AvlTree();
        int[] arr = {10, 12, 8, 9, 7, 6};
        for (int i = 0; i < arr.length; i++) {
            tree.add(new Node(arr[i]));
        }
        tree.infixOrder();
        System.out.println("树高度：" + tree.root.height());   // 3
        System.out.println("左树高度：" + tree.root.leftHeight());  // 2
        System.out.println("右树高度：" + tree.root.rightHeight()); // 2
        System.out.println("当前根节点：" + tree.root); // 8
    }
}

/**
 * 排序二叉树
 */
class AvlTree {
    Node root;

    public Node getRoot() {
        return root;
    }

    /**
     * 添加节点
     *
     * @param node
     */
    public void add(Node node) {
        if (root == null) {
            root = node;
            return;
        }
        root.add(node);
    }

    /**
     * 中序遍历
     */
    public void infixOrder() {
        if (root == null) {
            return;
        }
        root.infixOrder();
    }

    /**
     * 查找目标节点
     *
     * @param value
     * @return
     */
    public Node searchTarget(int value) {
        if (root == null) {
            return null;
        }
        return root.searchTarget(value);
    }

    /**
     * 查找父节点
     *
     * @param value
     * @return
     */
    public Node searchParent(int value) {
        if (root == null) {
            return null;
        }
        if (root.value == value) {
            return null;
        }
        return root.searchParent(value);
    }


    /**
     * 删除节点
     * <pre>
     *   注意：删除节点的思路是找到 目标节点 和 父节点，利用这两个节点就可以完成删除了，
     *        而不是去递归查找的。这一点需要明白，而且很重要。否则你将不知道递归如何写
     * </pre>
     *
     * @param value
     */
    public void delete(int value) {
        if (root == null) {
            return;
        }
        Node target = searchTarget(value);
        // 如果没有找到目标节点，则返回
        if (target == null) {
            return;
        }
        // 如果找到了节点
        // 并且，root 没有子节点了，则说明当前只有 root 一个节点
        if (root.left == null && root.right == null) {
            root = null;
            return;
        }

        Node parent = searchParent(value);


        // 1. 如果目标节点是叶子节点
        if (target.left == null && target.right == null) {
            // 如果目标节点是 父节点的 左节点
            if (parent.left != null && target.value == parent.left.value) {
                parent.left = null;
                return;
            }
            // 如果目标节点是 父节点的 右节点
            if (parent.right != null && target.value == parent.right.value) {
                parent.right = null;
                return;
            }
        }
        // 2. 如果目标节点有两颗子节点
        else if (target.left != null && target.right != null) {
            // 以目标节点为 root 节点，找到左子树中最小的节点，并删掉；也就是找到左子树中的一个 叶子节点
            Node min = deleteRightTreeMin(target);

            // 如果删除的是 root 节点，全程不要操作 parent
            if (parent == null) {
                root = min;
                min.right = target.right;
                min.left = target.left;
                return;
            }

            // 如果是父节点的 左节点
            if (parent.left != null && target.value == parent.left.value) {
                parent.left = min;
                min.right = target.right;
                min.left = target.left;
                return;
            }
            // 如果是父节点的 右节点
            if (parent.right != null && target.value == parent.right.value) {
                parent.right = min;
                min.right = target.right;
                min.left = target.left;
                return;
            }
        }
        // 3. 如果目标节点有 1 颗子节点
        else {
            // 如果删除的是 root 节点，全程不要操作 parent
            // 由于目标节点有一颗节点，先拿到这个要替换掉目标节点的  节点
            Node replaceNode = null;
            // 要替换的节点，由于只有一个，不是左就是右
            if (target.left != null) {
                replaceNode = target.left;
            } else {
                replaceNode = target.right;
            }
            // 如果要删除的是 root 节点
            if (parent == null) {
                root = replaceNode;
                return;
            }

            // 如果是父节点的 左节点
            if (parent.left != null && target.value == parent.left.value) {
                parent.left = replaceNode;
                return;
            }
            if (parent.right != null && target.value == parent.right.value) {
                parent.right = replaceNode;
            }
        }
        return;
    }

    /**
     * 以目标节点为 root 节点，找到左子树中最小的节点，并删掉；也就是找到左子树中的一个 叶子节点
     *
     * @param target
     * @return
     */
    private Node deleteRightTreeMin(Node target) {
        Node min = target.right;
        while (min.left != null) {
            min = min.left;
        }
        delete(min.value);
        return min;
    }
}

/**
 * 节点
 */
class Node {
    int value;
    Node left;
    Node right;

    public Node(int value) {
        this.value = value;
    }

    /**
     * 以当前节点为基础：计算出它包含它子树的所有高度
     *
     * @return
     */
    public int height() {
        /*
          这里使用了递归：返回了左右子树中，最高的那一个数值。
          递归原理：第一个开始统计的时候，一定是一个叶子节点
                  根据这个逻辑：叶子节点的 Math.max(0,0) = 0
                  因为当前节点算一层，所以 + 1;

                  返回到上一层的时候，至少是这样：Math.max(1,0) = 1
                  然后把自己本身的层 +1。 以此类推，返回到根节点的时候，就拿到了从包含根节点的树的高度
         */
        return Math.max(
                (left == null ? 0 : left.height()),
                (right == null ? 0 : right.height())
        ) + 1;
    }

    /**
     * 计算左子树的高度
     *
     * @return
     */
    public int leftHeight() {
        if (left == null) {
            return 0;
        }
        // 如果从根节点开始的话
        // 其实它从中间分开，左侧就有很多的小树
        // 所以还是要计算左右树的高度，返回一个最大的值，只不过是开始节点变化了
        return left.height();
    }

    /**
     * 计算右子树的高度
     *
     * @return
     */
    public int rightHeight() {
        if (right == null) {
            return 0;
        }
        return right.height();
    }

    /**
     * 添加节点：按照排序二叉树的要求添加
     *
     * @param node
     */
    public void add(Node node) {
        if (node == null) {
            return;
        }
        // 如果添加的值小于当前节点，则往左走
        if (node.value < value) {
            // 左节点为空，则直接挂在上面
            if (left == null) {
                left = node;
            } else {
                // 否则继续往下查找
                left.add(node);
            }
        } else {
            // 往右走
            if (right == null) {
                right = node;
            } else {
                right.add(node);
            }
        }

        // 旋转的时候有以下规则
        // 每添加一个节点之后：检查树的高度是否平衡
        //      如果右子树高度 - 左子树高度 > 1，则左旋转
        //      也就是说：每次旋转的层只涉及到 4 层(对照笔记上的图示理解)
        if (rightHeight() - leftHeight() > 1) {
            leftRotate();
        }

        if (leftHeight() - rightHeight() > 1) {
            rightRotate();
        }
    }

    /**
     * 以当前节点为根节点，进行左旋转
     */
    public void leftRotate() {
        // 1. 创建一个新的节点 newNode，值等于当前 根节点 的值
        Node newNode = new Node(value);
        // 2. 把 新节点的 左子树 设置为当前节点的左子树
        newNode.left = left;
        // 3. 把 新节点的 右子树 设置为当前节点的 右子树的左子树
        newNode.right = right.left;
        // 4. 把 当前节点的值，替换为 右子树 节点的子
        value = right.value;
        // 5. 把 当前节点 的 右节点 设置为 右子树的右子树
        right = right.right;
        // 6. 把 当前节点 的 左节点 设置为 新节点
        left = newNode;
    }

    /**
     * 以当前节点为根节点，进行右旋转
     */
    public void rightRotate() {
        // 1. 创建一个新的节点 newNode，值等于当前 根节点 的值
        Node newNode = new Node(value);
        // 2. 把 新节点的 右子树 设置为当前节点的右子树
        newNode.right = right;
        // 3. 把 新节点的 左子树 设置为当前节点的 左子树的右子树
        newNode.left = left.right;
        // 4. 把 当前节点的值，替换为 左子树 节点的子
        value = left.value;
        // 5. 把 当前节点 的 左节点 设置为 左子树的左子树
        left = left.left;
        // 6. 把 当前节点 的 右节点 设置为 新节点
        right = newNode;
    }

    /**
     * 中序遍历：刚好是从小到大的顺序
     */
    public void infixOrder() {
        if (left != null) {
            left.infixOrder();
        }
        System.out.println(value);
        if (right != null) {
            right.infixOrder();
        }
    }


    /**
     * 搜索目标节点
     *
     * @param value
     * @return
     */
    public Node searchTarget(int value) {
        if (value == this.value) {
            return this;
        } else if (value < this.value) {
            if (left != null) {
                return left.searchTarget(value);
            }
        } else {
            if (right != null) {
                return right.searchTarget(value);
            }
        }
        return null;
    }

    /**
     * 查找目标值的父节点
     *
     * @param value
     * @return
     */
    public Node searchParent(int value) {
        // 本节点能匹配到左右两节点其中一个等于，则父节点是本节点
        if (left != null && left.value == value
                || right != null && right.value == value
        ) {
            return this;
        }
        if (value < this.value && left != null) {
            return left.searchParent(value);
        }
        if (value >= this.value && right != null) {
            return right.searchParent(value);
        }
        return null;
    }

    @Override
    public String toString() {
        return "Node{" +
                "value=" + value +
                '}';
    }
}