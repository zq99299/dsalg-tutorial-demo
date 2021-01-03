package cn.mrcode.study.dsalgtutorialdemo.algorithm.kmp;

import org.junit.Test;

import java.util.Arrays;

/**
 * KMP 算法
 */
public class KmpAlgorithm {
    /**
     * KMP 简单推导
     */
    @Test
    public void kmpNextTest1() {
        String dest = "A";
        System.out.println("字符串：" + dest);
        System.out.println("的部分匹配表为：" + Arrays.toString(buildKmpNext1(dest)));
        System.out.println();
        dest = "AA";
        System.out.println("字符串：" + dest);
        System.out.println("的部分匹配表为：" + Arrays.toString(buildKmpNext1(dest)));
        System.out.println();
        dest = "AAA";
        System.out.println("字符串：" + dest);
        System.out.println("的部分匹配表为：" + Arrays.toString(buildKmpNext1(dest)));
        System.out.println();
        dest = "AAAB";
        System.out.println("字符串：" + dest);
        System.out.println("的部分匹配表为：" + Arrays.toString(buildKmpNext1(dest)));
        System.out.println();
        dest = "ABCDABD";
        System.out.println("字符串：" + dest);
        System.out.println("的部分匹配表为：" + Arrays.toString(buildKmpNext1(dest)));
        System.out.println();
    }

    /**
     * 生成此字符串的 部分匹配表
     *
     * @param dest
     */
    public int[] buildKmpNext1(String dest) {
        int[] next = new int[dest.length()];
        // 第一个字符的前缀和后缀都没有，所以不会有公共元素，因此必定为 0
        next[0] = 0;
        for (int i = 1, j = 0; i < dest.length(); i++) {
            /*
              ABCDA
              前缀：`A、AB、ABC、ABCD`
              后缀：`BCDA、CDA、DA、A`
              公共元素 A
              部分匹配值：1
             */
            // 当相等时，表示有一个部分匹配值
            if (dest.charAt(i) == dest.charAt(j)) {
                j++;
            }
            next[i] = j;
        }
        return next;
    }

    /**
     * kmp 部分匹配表生成
     */
    @Test
    public void kmpNextTest() {
        String dest = "A";
        System.out.println("字符串：" + dest);
        System.out.println("的部分匹配表为：" + Arrays.toString(buildKmpNext(dest)));
        System.out.println();
        dest = "AA";
        System.out.println("字符串：" + dest);
        System.out.println("的部分匹配表为：" + Arrays.toString(buildKmpNext(dest)));
        System.out.println();
        dest = "AAA";
        System.out.println("字符串：" + dest);
        System.out.println("的部分匹配表为：" + Arrays.toString(buildKmpNext(dest)));
        System.out.println();
        dest = "AAAB";
        System.out.println("字符串：" + dest);
        System.out.println("的部分匹配表为：" + Arrays.toString(buildKmpNext(dest)));
        System.out.println();
        dest = "ABCDABD";
        System.out.println("字符串：" + dest);
        System.out.println("的部分匹配表为：" + Arrays.toString(buildKmpNext(dest)));
        System.out.println();
    }

    /**
     * 生成此字符串的 部分匹配表
     *
     * @param dest
     */
    public int[] buildKmpNext(String dest) {
        int[] next = new int[dest.length()];
        // 第一个字符的前缀和后缀都没有，所以不会有公共元素，因此必定为 0
        next[0] = 0;
        for (int i = 1, j = 0; i < dest.length(); i++) {
            /*
              ABCDA
              前缀：`A、AB、ABC、ABCD`
              后缀：`BCDA、CDA、DA、A`
              公共元素 A
              部分匹配值：1
             */
            // 当  dest.charAt(i) != dest.charAt(j) 时
            // 需要从 next[j-1] 中获取新的 j
            // 这步骤是 部分匹配表的 核心点
            while (j > 0 && dest.charAt(i) != dest.charAt(j)) {
                j = next[j - 1];
            }
            // 当相等时，表示有一个部分匹配值
            if (dest.charAt(i) == dest.charAt(j)) {
                j++;
            }
            next[i] = j;
        }
        return next;
    }

    /**
     * kmp 搜索: 推导过程
     */
    @Test
    public void kmpSearchTest1() {
        String str1 = "BBC ABCDAB ABCDABCDABDE";
        String str2 = "BBC";
        // 获得部分匹配表
        int[] next = buildKmpNext(str2);
        int result = kmpSearch1(str1, str2, next);
        System.out.println(result);

        str2 = "ABCDABD";
        // 获得部分匹配表
        next = buildKmpNext(str2);
        result = kmpSearch1(str1, str2, next);
        System.out.println(result);
    }

    /**
     * kmp 搜索算法
     *
     * @param str1 源字符串
     * @param str2 子串
     * @param next 子串的部分匹配表
     * @return
     */
    private int kmpSearch1(String str1, String str2, int[] next) {
        for (int i = 0, j = 0; i < str1.length(); i++) {
            if (str1.charAt(i) == str2.charAt(j)) {
                j++;
            }
            if (j == str2.length()) {
                return i - j + 1;
            }
        }
        return -1;
    }


    /**
     * kmp 搜索: 推导过程
     */
    @Test
    public void kmpSearchTest() {
        String str1 = "BBC ABCDAB ABCDABCDABDE";
        String str2 = "ABCDABD";
        // 获得部分匹配表
        int[] next = buildKmpNext(str2);
        int result = kmpSearch(str1, str2, next);
        System.out.println(result);
    }

    /**
     * kmp 搜索算法
     *
     * @param str1 源字符串
     * @param str2 子串
     * @param next 子串的部分匹配表
     * @return
     */
    private int kmpSearch(String str1, String str2, int[] next) {
        for (int i = 0, j = 0; i < str1.length(); i++) {
            while (j > 0 && str1.charAt(i) != str2.charAt(j)) {
              /*
              从源字符串中挨个的取出字符与 子串的第一个相比
              直到匹配上时：j++, 如果有一个已经匹配上了比如
                        ↓ i = 10
              BBC ABCDAB ABCDABCDABDE
                  ABCDABD
                        ↑ j = 6
              然后继续下一个，这个时候由于 i 与 j 同步在增加，直到匹配到 空格与 D 时，不匹配
              此时：需要调整子串的匹配其实点：
              j = next[6 - 1] = 2, 调整后

                        ↓ i = 10
              BBC ABCDAB ABCDABCDABDE
                      ABCDABD
                        ↑ j = 2

              会发现不等于，继续调整
              j = next[2 - 1] = 0, 调整后

                        ↓ i = 10
              BBC ABCDAB ABCDABCDABDE
                         ABCDABD
                         ↑ j = 0
              此时：不满足 j > 0 了
             */
                j = next[j - 1];
            }

            /*
              从源字符串中挨个的取出字符与 子串的第一个相比
              直到匹配上时：j++, 如果有一个已经匹配上了比如
                  ↓ i = 4
              BBC ABCDAB ABCDABCDABDE
                  ABCDABD
                  ↑ j = 0
              然后继续下一个，这个时候由于 i 与 j 同步在增加，直到匹配到 空格与 D 时，不匹配
             */
            if (str1.charAt(i) == str2.charAt(j)) {
                j++;
            }
            if (j == str2.length()) {
                return i - j + 1;
            }
        }
        return -1;
    }
}
