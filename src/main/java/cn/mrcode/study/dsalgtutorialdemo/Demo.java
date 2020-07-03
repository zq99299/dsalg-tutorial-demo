package cn.mrcode.study.dsalgtutorialdemo;

public class Demo {
    public static void main(String[] args) {
        String str = "Java,Java, hello,world!";
        String newStr = str.replaceAll("Java", "尚硅谷~"); //算法
        System.out.println("newStr=" + newStr);
    }
}
