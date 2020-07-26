package cn.mrcode.study.dsalgtutorialdemo.datastructure.stack.calculator;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

/**
 * 逆波兰计算器实现（后缀表达式）
 */
public class ReversePolishCalculatorTest {
    /**
     * 直接计算后缀表达式测试
     */
    public void test1() {
        ReversePolishCalculator calculator = new ReversePolishCalculator();

        //  (3+4)*5-6 => 对应的后缀表达式 `3 4 + 5 * 6 -`
        String postfixExpression = "3 4 + 5 * 6 -";
        System.out.println("(3+4)*5-6 = " + calculator.cal(postfixExpression));

        //  (30+4)*5-6 => 对应的后缀表达式 `30 4 + 5 * 6 -`
        postfixExpression = "30 4 + 5 * 6 -";
        System.out.println("(30+4)*5-6 = " + calculator.cal(postfixExpression));

        // 4*5-8+60+8/2  => 对应的后缀表达式 `4 5 * 8 - 60 + 8 2 / +`
        postfixExpression = "4 5 * 8 - 60 + 8 2 / +";
        System.out.println("4*5-8+60+8/2 = " + calculator.cal(postfixExpression));

        // (3+4)-(3-4)*10，对应后缀表达式为：3 4 + 10 3 4 - * -
        postfixExpression = "3 4 + 10 3 4 - * -";
        System.out.println("(3+4)-(3-4)*10 = " + calculator.cal(postfixExpression));
    }

    /**
     * 中缀表达式转后缀表达式，再给定计算器计算测试
     */
    @Test
    public void test2() {
        InfixToSuffix infixToSuffix = new InfixToSuffix();
        // 目标：1+((2+3)*4)-5  转为 1 2 3 + 4 * + 5 -
        // 1. 将中缀表达式转成 List，方便在后续操作中获取数据
        String infixExpression = "1+((2+3)*4)-5";
        List<String> infixList = infixToSuffix.infix2List(infixExpression);
        // 2. 将中缀表达式转成后缀表达式
        ArrayList<String> suffixList = infixToSuffix.infixList2SuffixList(infixList);
        System.out.println(suffixList); // [1, 2, 3, +, 4, *, +, 5, -]

        ReversePolishCalculator calculator = new ReversePolishCalculator();
        int res = calculator.start(suffixList);
        System.out.println(infixExpression + " = " + res);
    }
}
