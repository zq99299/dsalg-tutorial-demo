package cn.mrcode.study.dsalgtutorialdemo.datastructure.stack.calculator;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * 中缀表达式转后缀表达式
 */
public class InfixToSuffix {
    public static void main(String[] args) {
        InfixToSuffix infixToSuffix = new InfixToSuffix();
        // 目标：1+((2+3)*4)-5  转为 1 2 3 + 4 * + 5 -
        // 1. 将中缀表达式转成 List，方便在后续操作中获取数据
        String infixExpression = "1+((2+3)*4)-5";
        List<String> infixList = infixToSuffix.infix2List(infixExpression);
        System.out.println(infixList); // [1, +, (, (, 2, +, 3, ), *, 4, ), -, 5]
        // 2. 将中缀表达式转成后缀表达式
        ArrayList<String> suffixList = infixToSuffix.infixList2SuffixList(infixList);
        System.out.println(suffixList); // [1, 2, 3, +, 4, *, +, 5, -]
    }


    /**
     * 将中缀表达式解析成单个元素的 List，
     *
     * @param infixExpression
     * @return 1+((2+3)*4)-5 -> [1,+,(,(,2,+,3,),*,4,),5]
     */
    public List<String> infix2List(String infixExpression) {
        ArrayList<String> res = new ArrayList<>();
        // 扫描并解析
        int index = 0;
        char ch = 0;
        String tempNum = ""; // 支持多位数
        while (index < infixExpression.length()) {
            ch = infixExpression.charAt(index++);
            // 如果不是数字，就是符号，直接添加到容器中
            // 0 = 48, 9 = 57
            if (!(ch >= 48 && ch <= 57)) {
                // 如果拼接的多位数还有值，则添加到容器中
                if (!tempNum.isEmpty()) {
                    res.add(tempNum);
                    tempNum = "";
                }
                res.add(ch + "");
                continue;
            }
            // 如果是数字，则考虑处理多位数
            tempNum += ch;
            // 如果已经是最后一个字符了，则将这个多位数添加到容器中
            if (index == infixExpression.length()) {
                res.add(tempNum);
                tempNum = "";
            }
        }
        return res;
    }

    /**
     * 中缀表达式 List 转为后缀表达式 List
     *
     * @param infixList
     * @return
     */
    public ArrayList<String> infixList2SuffixList(List<String> infixList) {
        // 符号栈
        Stack<String> s1 = new Stack<>();
        // 思路是使用栈来存储表达式元素
        // 仔细观察他的解析步骤，会发现：只是在入栈，并未出现出栈操作
        // 而且，最后的结果还要逆序，所以这里使用 list，直接顺序读取出来就是最后的结果了
        ArrayList<String> s2 = new ArrayList<>();

        for (String item : infixList) {
            // 如果是数字，则加入 s2
            if (item.matches("\\d+")) {
                s2.add(item);
            }
            // 如果是左括号，直接压入 s1
            else if (item.equals("(")) {
                s1.push(item);
            }
            // 如果是右括号
            // 则依次弹出 s1 栈顶的运算符，并压入 s2，直到遇到 左括号 为止，此时将这一对括号 丢弃
            else if (item.equals(")")) {
                // 如果不是左括号，则取出 s1 中的符号，添加到 s2 中
                while (!s1.peek().equals("(")) {
                    s2.add(s1.pop());
                }
                // 上面循环完之后，那么就是遇到了左括号
                // 则直接弹出这个左括号丢弃
                s1.pop();
            }
            // 剩下的则是运算符
            else {
                // 如果 s1 为空，或则栈顶运算符为 （，则压入符号栈 s1
                // 如果优先级比栈顶运算符 高，则压入符号栈 s1,否则，否则将 s1 栈顶的运算符弹出，压入 s2 中
                // 上面两句话，转换成下面的描述
                // 上面如果  s1 栈顶符号优先级比 当前符号高，则弹出加入到 s2 中。
                // 因为：如果栈顶符号是 （ 返回优先级为 -1.比当前符号低，则不会走该方法
                while (!s1.isEmpty() && priority(s1.peek().charAt(0)) >= priority(item.charAt(0))) {
                    s2.add(s1.pop());
                }
                s1.push(item);
            }
        }
        // 将 s1 中的运算符依次弹出并加入 s2 中
        while (!s1.isEmpty()) {
            s2.add(s1.pop());
        }
        return s2;
    }

    /**
     * 计算操作符号优先级，暂时只支持 + - * /
     *
     * @param ch
     * @return 优先级越高，数值越大
     */
    private int priority(char ch) {
        switch (ch) {
            case '+':
            case '-':
                return 0;
            case '*':
            case '/':
                return 1;
            default:
                return -1;
        }
    }
}
