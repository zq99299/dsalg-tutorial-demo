package cn.mrcode.study.dsalgtutorialdemo.algorithm.kmp;

import org.junit.Test;

/**
 * 暴力匹配
 */
public class ViolenceMatch {
    /**
     * 暴力匹配
     *
     * @param str1 要匹配的文本
     * @param str2 关键词
     * @return
     */
    public int violenceMatch(String str1, String str2) {
        char[] s1 = str1.toCharArray();
        char[] s2 = str2.toCharArray();

        int s1Len = s1.length;
        int s2Len = s2.length;

        int i = 0; // 指向 s1 中正在匹配的位置
        int j = 0; // 执行 s2 中正在匹配的位置
        while (i < s1Len && j < s2Len) {
            // 如果相等，则让两个指针都往前移动
            if (s1[i] == s2[j]) {
                i++;
                j++;
            } else {
                // 当不匹配的时候
                // j 重置为 0，子串要重新匹配
                i = i - (j - 1);
                j = 0;
            }
        }
        // 如果找到，则返回当前索引
        // 因为在匹配过程中，没有匹配上 j 就重置为 0 了
        if (j == s2Len) {
            // 返回匹配开始的字符
            return i - j;
        }
        return -1;
    }

    /**
     * 测试匹配上
     */
    @Test
    public void fun1() {
        String str1 = "硅硅谷 尚硅谷你尚硅 尚硅谷你尚硅谷你尚硅你好";
        String str2 = "尚硅谷你尚硅你";
        System.out.println(violenceMatch(str1, str2));
    }

    /**
     * 测试匹配失败
     */
    @Test
    public void fun2() {
        String str1 = "硅硅谷 尚硅谷你尚硅 尚硅谷你尚硅谷你尚硅你好";
        String str2 = "尚硅谷你尚硅你x";
        System.out.println(violenceMatch(str1, str2));
    }
}
