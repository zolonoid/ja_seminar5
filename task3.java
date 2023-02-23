import java.util.PrimitiveIterator;

/**
 * task3
 */
public class task3
{

    public static void main(String[] args)
    {
        // for(int start = 0; start < 64; start++)
        // {
        //     int ferzCount = 0;
        //     var board = new boolean[8][8];
        //     for(int i = start/8; i < 8; i++)
        //     {
        //         for (int j = start%8; j < 8; j++)
        //         {
        //             if(board[i][j]) continue;
        //             if(CheckBeating(new int[] { i,j }, board)) continue;
        //             board[i][j] = true;
        //             ferzCount++;
        //         }
        //     }
        //     if(ferzCount < 5) continue;
        //     PrintBoard(board);
        //     break;
        // }

        for(int f1 = 0; f1 < 64; f1++) {
            for(int f2 = 0; f2 < 64; f2++) {
                for(int f3 = 0; f3 < 64; f3++) {
                    for(int f4 = 0; f4 < 64; f4++) {
                        for(int f5 = 0; f5 < 64; f5++) {
                            for(int f6 = 0; f6 < 64; f6++) {
                                for(int f7 = 0; f7 < 64; f7++) {
                                    for(int f8 = 0; f8 < 64; f8++) {
                                        var board = new boolean[8][8];
                                        board[f1 / 8][f1 % 8] = true;
                                        if(CheckBeating(new int[] { f2 / 8, f2 % 8 }, board)) continue;
                                        board[f2 / 8][f2 % 8] = true;
                                        if(CheckBeating(new int[] { f3 / 8, f3 % 8 }, board)) continue;
                                        board[f3 / 8][f3 % 8] = true;
                                        if(CheckBeating(new int[] { f4 / 8, f4 % 8 }, board)) continue;
                                        board[f4 / 8][f4 % 8] = true;
                                        if(CheckBeating(new int[] { f5 / 8, f5 % 8 }, board)) continue;
                                        board[f5 / 8][f5 % 8] = true;
                                        if(CheckBeating(new int[] { f6 / 8, f6 % 8 }, board)) continue;
                                        board[f6 / 8][f6 % 8] = true;
                                        if(CheckBeating(new int[] { f7 / 8, f7 % 8 }, board)) continue;
                                        board[f7 / 8][f7 % 8] = true;
                                        if(CheckBeating(new int[] { f8 / 8, f8 % 8 }, board)) continue;
                                        board[f8 / 8][f8 % 8] = true;
                                        PrintBoard(board);
                                        return;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private static boolean CheckBeating(int[] square, boolean[][] board)
    {
        return board[square[0]][square[1]]           ||
               CheckVerticalBeating(square, board)   ||
               CheckHorizontalBeating(square, board) ||
               CheckDiagonalBeating(square, board);
    }

    private static boolean CheckVerticalBeating(int[] square, boolean[][] board)
    {
        for (int i = 0; i < 8; i++)
        {
            if(board[square[0]][i])
                return true;
        }
        return false;
    }

    private static boolean CheckHorizontalBeating(int[] square, boolean[][] board)
    {
        for (int i = 0; i < 8; i++)
        {
            if(board[i][square[1]])
                return true;
        }
        return false;
    }

    private static boolean CheckDiagonalBeating(int[] square, boolean[][] board)
    {
        for(int i = square[0], j = square[1]; i < 8 && j < 8; i++, j++)
        {
                if(board[i][j])
                    return true;
        }
        for(int i = square[0], j = square[1]; i < 8 && j >= 0; i++, j--)
        {
                if(board[i][j])
                    return true;
        }
        for(int i = square[0], j = square[1]; i >= 0 && j >= 0; i--, j--)
        {
                if(board[i][j])
                    return true;
        }
        for(int i = square[0], j = square[1]; i >= 0 && j < 8; i--, j++)
        {
                if(board[i][j])
                    return true;
        }
        return false;
    }

    private static void PrintBoard(boolean[][] board)
    {
        var print = new StringBuilder();
        for(int j = 0; j < 8; j++)
        {
            for(int i = 0; i < 8; i++)
            {
                print.append(board[i][j] ? 'x' : 'o');
                print.append("  ");
            }
            print.append('\n');
        }
        System.out.println(print.toString());
    }
}