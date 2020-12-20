package cn.mrcode.study.dsalgtutorialdemo.datastructure.binarysorttree;

import org.junit.Test;

/**
 * 二叉排序树
 */
public class BinarySortTreeTest {
    /**
     * 二叉排序树添加和遍历测试
     */
    @Test
    public void addTest() {
        BinarySortTree tree = new BinarySortTree();
        int[] arr = {7, 3, 10, 12, 5, 1, 9};
        for (int i = 0; i < arr.length; i++) {
            tree.add(new Node(arr[i]));
        }
        tree.infixOrder();
        int item = 2;
        tree.add(new Node(item));
        System.out.println("\n添加新节点：" + item + " 到二叉排序树中");
        System.out.println("添加之后的中序顺序：");
        tree.infixOrder();

        item = 4;
        tree.add(new Node(item));
        System.out.println("\n添加新节点：" + item + " 到二叉排序树中");
        System.out.println("添加之后的中序顺序：");
        tree.infixOrder();
    }

    /**
     * 删除：叶子节点
     */
    @Test
    public void delete1() {
        System.out.println("\n\n删除叶子节点：2，5，9，12");
        BinarySortTree tree = new BinarySortTree();
        int[] arr = {7, 3, 10, 12, 5, 1, 9, 2};
        for (int i = 0; i < arr.length; i++) {
            tree.add(new Node(arr[i]));
        }
        tree.infixOrder();

        // 当只实现了删除叶子节点时，这步骤是删除不成功的
//        tree.delete(1);
//        System.out.println("删除非叶子节点后的内容：");
//        tree.infixOrder();

        tree.delete(2);
        tree.delete(5);
        tree.delete(9);
        tree.delete(12);
        System.out.println("删除后的内容：");
        tree.infixOrder();
    }

    /**
     * 删除：只有一颗叶子节点的节点
     */
    @Test
    public void delete2() {
        System.out.println("\n\n只有一颗叶子节点的节点：1");
        BinarySortTree tree = new BinarySortTree();
        int[] arr = {7, 3, 10, 12, 5, 1, 9, 2};
        for (int i = 0; i < arr.length; i++) {
            tree.add(new Node(arr[i]));
        }
        tree.infixOrder();

        tree.delete(1);
        System.out.println("删除后的内容：");
        tree.infixOrder();
    }

    /**
     * 删除：有两颗子节点的 节点
     */
    @Test
    public void delete3() {
        System.out.println("\n\n有两颗子节点的节点: 10");
        BinarySortTree tree = new BinarySortTree();
        int[] arr = {7, 3, 10, 12, 5, 1, 9, 2};
        for (int i = 0; i < arr.length; i++) {
            tree.add(new Node(arr[i]));
        }
        tree.infixOrder();

        tree.delete(10);
        System.out.println("删除节点后的内容：");
        tree.infixOrder();
    }

    /**
     * 删除  root 节点
     */
    @Test
    public void deleteRoot() {
        System.out.println("\n\n删除 root 节点:7");
        BinarySortTree tree = new BinarySortTree();
        int[] arr = {7, 3, 10, 12, 5, 1, 9, 2};
        for (int i = 0; i < arr.length; i++) {
            tree.add(new Node(arr[i]));
        }
        tree.infixOrder();

        tree.delete(7);
        System.out.println("删除节点后的内容：");
        tree.infixOrder();
    }

    /**
     * 排序二叉树
     */
    class BinarySortTree {
        Node root;

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

                // 因为只有一颗节点，不是左就是右边
               /* if (target.left != null) {
                    // 删除的如果是 root 节点
                    if (parent == null) {
                        root = target.left;
                        return;
                    }
                    // 如果是父节点的 左节点
                    if (parent.left != null && target.value == parent.left.value) {
                        parent.left = target.left;
                        return;
                    }
                    if (parent.right != null && target.value == parent.right.value) {
                        parent.right = target.left;
                    }
                } else {
                    // 删除的如果是 root 节点
                    if (parent == null) {
                        root = target.right;
                        return;
                    }
                    // 如果是父节点的 右节点
                    if (parent.left != null && target.value == parent.left.value) {
                        parent.left = target.right;
                        return;
                    }
                    if (parent.right != null && target.value == parent.right.value) {
                        parent.right = target.right;
                    }
                }
                */
                // 上面的写法重构后为下面这样
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