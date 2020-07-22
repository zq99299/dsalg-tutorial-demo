package cn.mrcode.study.dsalgtutorialdemo.datastructure.stack.calculator;

/**
 * 计算器代码实现
 */
public class Calculator {
    // 使用前面章节实现过的 数组模拟栈，来当我们 计算器中用来存储数据和符号的 容器
    private ArrayStack numStack = new ArrayStack(10); // 数组栈
    private ArrayStack operStack = new ArrayStack(10); // 符号栈

    public static void main(String[] args) {
        String expression = "3+2*6-2";

        Calculator calculator = new Calculator();
        // 扫描表达式
        calculator.scan(expression);
        // 剩余数据进行计算
        int res = calculator.nextCal();
        System.out.printf("%s = %d \n", expression, res);
    }


    /**
     * 第一步：扫描表达式
     */
    public void scan(String expression) {
        int index = 0;
        while (true) {
            if (index == expression.length()) {
                break;
            }
            // 每次只截取一个数字
            // 要注意这里的 ch，使用 ch 做运算的时候要小心
            char ch = expression.substring(index, ++index).charAt(0);
            if (isOper(ch)) {
                // 符号栈为空，则直接入符号
                if (operStack.isEmpty()) {
                    operStack.push(ch);
                    continue;
                }
                // 当 当前操作符 的优先级 大于 栈顶符号：将 当前操作符入符号栈
                if (priority(ch) >= priority((char) operStack.peek())) {
                    operStack.push(ch);
                    continue;
                }
                // 小于栈顶操作符，则将栈顶符号取出，进行计算
                int num1 = numStack.pop();
                int num2 = numStack.pop();
                int oper = operStack.pop();
                int res = cal(num1, num2, oper);
                // 将结果入数栈
                numStack.push(res);
                // 将当期操作符入符号栈
                operStack.push(ch);
            } else {
                // 是数字，直接入数栈
                // ch 被当成 int 的使用的话，需要特别注意
                // ASCII 码表中数字是从 48 开始的，这里的 ch 对应的数字是 ASCII 码表，所以要减掉 48
                // 当然也可以使用字符串解析的方式 Integer.valueOf(字符串) 来得到数字
                numStack.push(ch - 48);
            }
        }
    }

    /**
     * 第 2 步：从栈中取出来数据和符号，然后计算
     *
     * @return
     */
    private int nextCal() {
        System.out.println("符号栈中符号情况：");
        operStack.print();
        while (true) {
            // 当符号栈为空时，表示已经计算完了
            if (operStack.isEmpty()) {
                break;
            }
            int num1 = numStack.pop();
            int num2 = numStack.pop();
            int oper = operStack.pop();
            int res = cal(num1, num2, oper);
            // 将结果入数栈
            numStack.push(res);
        }
        // 计算完成之后，数栈中只有一个数据了，就是结果
        System.out.println("栈中数据是否只有一个结果数字：");
        numStack.print();
        return numStack.pop();
    }

    /**
     * 计算
     *
     * @param num1 依次从栈顶弹出来的数据
     * @param num2
     * @param oper 操作符
     * @return
     */
    private int cal(int num1, int num2, int oper) {
        switch (oper) {
            case '+':
                return num1 + num2;
            case '-':
                // 注意顺序，在栈底的数据，是先进去的，如果是减法，则是前面的数字减后面的数字
                return num2 - num1;
            case '*':
                return num1 * num2;
            case '/':
                return num2 / num1;
        }
        // 由于前面校验过操作符，不会走到这里来的
        return 0;
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

    /**
     * 是否是操作符
     *
     * @param ch
     * @return
     */
    private boolean isOper(char ch) {
        switch (ch) {
            case '+':
            case '-':
            case '*':
            case '/':
                return true;
        }
        return false;
    }
}
