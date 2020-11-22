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

        /**
         * 前序查找
         */
        public HeroNode preOrderSearch(int id) {
            System.out.println("  进入前序遍历");  // 用来统计查找了几次
            // 1. 如何相等，则返回
            if (this.id == id) {
                return this;
            }
            // 2. 如果左子节点不为空，则递归继续前序遍历
            if (this.left != null) {
                HeroNode result = this.left.preOrderSearch(id);
                if (result != null) {
                    return result;
                }
            }
            // 3. 如果右子节点不为空，则递归继续前序遍历
            if (this.right != null) {
                HeroNode result = this.right.preOrderSearch(id);
                if (result != null) {
                    return result;
                }
            }
            return null;
        }

        /**
         * 中序查找
         */
        public HeroNode infixOrderSearch(int id) {
//            System.out.println("  进入中序遍历");  // 用来统计查找了几次，不能在这里打印，这里打印是进入了方法几次，看的是比较了几次
            // 1. 如果左子节点不为空，则递归继续前序遍历
            if (this.left != null) {
                HeroNode result = this.left.infixOrderSearch(id);
                if (result != null) {
                    return result;
                }
            }
            System.out.println("  进入中序遍历");
            // 2. 如果相等，则返回
            if (this.id == id) {
                return this;
            }

            // 3. 如果右子节点不为空，则递归继续前序遍历
            if (this.right != null) {
                HeroNode result = this.right.infixOrderSearch(id);
                if (result != null) {
                    return result;
                }
            }
            return null;
        }

        /**
         * 后序查找
         */
        public HeroNode postOrderSearch(int id) {
//            System.out.println("  进入后序遍历");  // 用来统计查找了几次，不能在这里打印，这里打印是进入了方法几次，看的是比较了几次
            // 1. 如果左子节点不为空，则递归继续前序遍历
            if (this.left != null) {
                HeroNode result = this.left.postOrderSearch(id);
                if (result != null) {
                    return result;
                }
            }
            // 2. 如果右子节点不为空，则递归继续前序遍历
            if (this.right != null) {
                HeroNode result = this.right.postOrderSearch(id);
                if (result != null) {
                    return result;
                }
            }
            System.out.println("  进入后序遍历");
            // 3. 如果相等，则返回
            if (this.id == id) {
                return this;
            }
            return null;
        }

        /**
         * 删除节点
         *
         * @param id
         * @return
         */
        public HeroNode delete(int id) {
            // 判断左子节点是否是要删除的节点
            HeroNode target = null;
            if (this.left != null) {
                if (this.left.id == id) {
                    target = this.left;
                    this.left = null;
                } else {
                    // 使用子节点的删除方法,进行左递归删除
                    target = this.left.delete(id);
                }
            }

            // 在左找到目标节点，并且删除了，则返回被删除的节点
            if (target != null) {
                return target;
            }

            if (this.right != null) {
                if (this.right.id == id) {
                    target = this.right;
                    this.right = null;
                } else {
                    target = this.right.delete(id);
                }
            }
            return target;
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

        /**
         * 前序查找
         */
        public HeroNode preOrderSearch(int id) {
            if (root == null) {
                System.out.println("二叉树为空");
                return null;
            }
            return root.preOrderSearch(id);
        }

        /**
         * 中序查找
         */
        public HeroNode infixOrderSearch(int id) {
            if (root == null) {
                System.out.println("二叉树为空");
                return null;
            }
            return root.infixOrderSearch(id);
        }

        /**
         * 后序查找
         */
        public HeroNode postOrderSearch(int id) {
            if (root == null) {
                System.out.println("二叉树为空");
                return null;
            }
            return root.postOrderSearch(id);
        }

        /**
         * 删除节点
         *
         * @param id
         * @return
         */
        public HeroNode delete(int id) {
            if (root == null) {
                System.out.println("树为空");
                return null;
            }
            HeroNode target = null;
            // 树只有 root 节点，则直接置空
            if (root.id == id) {
                target = root;
                root = null;
            } else {
                target = this.root.delete(id);
            }

            return target;
        }
    }

    // 二叉树测试

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

    /**
     * 查找 id=5 的前、中、后序测试
     */
    @Test
    public void fun3() {
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

        System.out.println("找到测试：");
        int id = 5;
        System.out.println("\n前序遍历查找 id=" + id);
        System.out.println(binaryTree.preOrderSearch(id));
        System.out.println("\n中序遍历查找 id=" + id);
        System.out.println(binaryTree.infixOrderSearch(id));
        System.out.println("\n后序遍历查找 id=" + id);
        System.out.println(binaryTree.postOrderSearch(id));

        System.out.println("找不到测试：");
        id = 15;
        System.out.println("\n前序遍历查找 id=" + id);
        System.out.println(binaryTree.preOrderSearch(id));
        System.out.println("\n中序遍历查找 id=" + id);
        System.out.println(binaryTree.infixOrderSearch(id));
        System.out.println("\n后序遍历查找 id=" + id);
        System.out.println(binaryTree.postOrderSearch(id));
    }

    /**
     * 构建当前这个树
     *
     * @return
     */
    private BinaryTree buildBinaryTree() {
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
        return binaryTree;
    }

    @Test
    public void delete3() {
        // 创建节点与构建二叉树
        BinaryTree binaryTree = buildBinaryTree();
        binaryTree.preOrder();

        // 删除 3 号节点
        HeroNode target = binaryTree.delete(3);
        String msg = (target == null ? "删除失败，未找到" : "删除成功：" + target.toString());
        System.out.println(msg);
        binaryTree.preOrder();
    }


    @Test
    public void delete5() {
        // 创建节点与构建二叉树
        BinaryTree binaryTree = buildBinaryTree();
        binaryTree.preOrder();

        // 删除 5 号节点
        HeroNode target = binaryTree.delete(5);
        String msg = (target == null ? "删除失败，未找到" : "删除成功：" + target.toString());
        System.out.println(msg);
        binaryTree.preOrder();
    }

    /**
     * 删除一个不存在的节点
     */
    @Test
    public void deleteFail() {
        // 创建节点与构建二叉树
        BinaryTree binaryTree = buildBinaryTree();
        binaryTree.preOrder();

        // 删除 5 号节点
        HeroNode target = binaryTree.delete(9);
        String msg = (target == null ? "删除失败，未找到" : "删除成功：" + target.toString());
        System.out.println(msg);
        binaryTree.preOrder();
    }

    /**
     * 删除 root 节点
     */
    @Test
    public void deleteRoot() {
        // 创建节点与构建二叉树
        BinaryTree binaryTree = buildBinaryTree();
        binaryTree.preOrder();

        // 删除 1 号节点
        HeroNode target = binaryTree.delete(1);
        String msg = (target == null ? "删除失败，未找到" : "删除成功：" + target.toString());
        System.out.println(msg);
        binaryTree.preOrder();
    }
}
