package cn.mrcode.study.dsalgtutorialdemo.datastructure.sparsearray;

/**
 * <pre>
 *  稀疏数组：
 *      1. 二维数组转稀疏数组
 *      2. 稀疏数组转二维数组
 * </pre>
 */
public class SparseArray {
    public static void main(String[] args) {
        // 创建原始二维数组
        // 0：没有棋子，1：黑棋，2：白棋
        // 棋盘大小 11 x 11
        int chessArr[][] = new int[11][11];
        chessArr[1][2] = 1;
        chessArr[2][3] = 2;
        // 预览棋盘上的棋子位置
        System.out.println("预览原始数组");
        printChessArray(chessArr);
        // 二维数组转稀疏数组
        int[][] sparseArr = chessToSparse(chessArr);
//        int[][] sparseArr = chessToSparse2(chessArr);
        System.out.println("二维数组转稀疏数组");
        printChessArray(sparseArr);
        // 稀疏数组转二维数组
        int[][] chessArr2 = sparseToChess(sparseArr);
        System.out.println("稀疏数组转二维数组");
        printChessArray(chessArr2);
    }

    /**
     * 二维数组转稀疏数组
     *
     * @param chessArr
     */
    private static int[][] chessToSparse(int[][] chessArr) {
        // 1. 遍历数组得到有效棋子个数
        int sum = 0;
        for (int[] row : chessArr) {
            for (int chess : row) {
                if (chess != 0) {
                    sum++;
                }
            }
        }
        // 2. 创建稀疏数组
        int[][] sparseArr = new int[sum + 1][3];
        // 3. 将二维数据的有效数据存入到稀疏数组中（从第 2 行开始存储）
        int chessRow = chessArr.length;  // 行： 棋盘大小
        int chessCol = 0;  // 列： 棋盘大小
        int count = 0; // 记录当前是第几个非 0 的数据
        for (int i = 0; i < chessArr.length; i++) {
            int[] rows = chessArr[i];
            if (chessCol == 0) {
                chessCol = rows.length;
            }
            for (int j = 0; j < rows.length; j++) {
                int chess = rows[j];
                if (chess == 0) {
                    continue;
                }
                count++;  // 第一行是棋盘信息，所以先自增
                sparseArr[count][0] = i;
                sparseArr[count][1] = j;
                sparseArr[count][2] = chess;
            }
        }
        // 4. 补全第一行的棋盘大小和有效数据
        sparseArr[0][0] = chessRow;
        sparseArr[0][1] = chessCol;
        sparseArr[0][2] = sum;
        return sparseArr;
    }

    /**
     * 稀疏数组转二维数组
     *
     * @param sparseArr
     * @return
     */
    private static int[][] sparseToChess(int[][] sparseArr) {
        // 1. 创建二维数组
        int[][] chessArr = new int[sparseArr[0][0]][sparseArr[0][1]];
        // 2. 恢复有效数据到二维数组
        for (int i = 1; i < sparseArr.length; i++) {
            int[] rows = sparseArr[i];
            chessArr[rows[0]][rows[1]] = rows[2];
        }
        return chessArr;
    }


    /**
     * 打印棋盘上的棋子布局
     *
     * @param chessArr
     */
    public static void printChessArray(int[][] chessArr) {
        for (int[] row : chessArr) {
            for (int data : row) {
                // 左对齐，使用两个空格补齐 2 位数
                System.out.printf("%-2d\t", data);
            }
            System.out.println("");
        }
    }

    /**
     * 二维数组转稀疏数组; 代码紧凑版本
     *
     * @param chessArr
     */
    private static int[][] chessToSparse2(int[][] chessArr) {
        // 1. 遍历数组得到有效棋子个数
        int sum = 0;
        for (int[] row : chessArr) {
            for (int chess : row) {
                if (chess != 0) {
                    sum++;
                }
            }
        }
        // 2. 创建稀疏数组
        int[][] sparseArr = new int[sum + 1][3];
        // 3. 将二维数据的有效数据存入到稀疏数组中（从第 2 行开始存储）
        int chessRow = chessArr.length;  // 行： 棋盘大小
        int chessCol = 0;  // 列： 棋盘大小
        int count = 0; // 记录当前是第几个非 0 的数据
        for (int i = 0; i < chessArr.length; i++) {
            if (chessCol == 0) {
                chessCol = chessArr[i].length;
            }
            for (int j = 0; j < chessCol; j++) {
                if (chessArr[i][j] != 0) {
                    count++;  // 第一行是棋盘信息，所以先自增
                    sparseArr[count][0] = i;
                    sparseArr[count][1] = j;
                    sparseArr[count][2] = chessArr[i][j];
                }
            }
        }
        // 4. 补全第一行的棋盘大小和有效数据
        sparseArr[0][0] = chessRow;
        sparseArr[0][1] = chessCol;
        sparseArr[0][2] = sum;
        return sparseArr;
    }
}
