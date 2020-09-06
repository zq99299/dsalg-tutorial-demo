package cn.mrcode.study.dsalgtutorialdemo;

public class Demo {
    public static void main(String[] args) {
        int total = 0;
        int end = 100;
// 使用 for 循环计算
        for (int i = 1; i <= end; i++) {
            total += i;
        }

        System.out.println(total);
        System.out.println((1 + end) * end / 2);
    }
}
