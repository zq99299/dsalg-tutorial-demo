package cn.mrcode.study.dsalgtutorialdemo.datastructure.recursion;

public class RecursionTest {
    public static void main(String[] args) {
//        test(4);
//        System.out.println(factorial(5));
        System.out.println(factorial2(2));
    }

    /**
     * 打印问题：打印信息是什么？
     *
     * @param n
     */
    public static void test(int n) {
        if (n > 2) {
            test(n - 1);
        }
        System.out.println("n=" + n);
    }

    /**
     * <pre>
     * 阶乘问题：传递一个数值, 从 1 开始乘以 2，再乘以 3，一直到 N，的结果，比如从 1 阶乘到 5，如下所示
     * 1 * 2 = 2
     * 2 * 3 = 6
     * 6 * 4 = 24
     * 24 * 5 = 120
     * 结果是 120
     * </pre>
     *
     * @return
     */
    public static int factorial(int n) {
        if (n == 1) {
            return 1;
        } else {
            return factorial(n - 1) * n;
        }
    }

    public static int factorial2(int n) {
        if (n == 1) {
            return 1;
        } else {
            int factorial = factorial2(n - 1);
            System.out.printf("%d * %d = %d \n", factorial, n, factorial * n);
            return factorial * n;
        }
    }

}
