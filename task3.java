import java.util.PrimitiveIterator;

/**
 * task3
 */
public class task3
{

    public static void main(String[] args)
    {
        for(int start = 0; start < 64; start++)
        {
            int ferzCount = 0;
            var board = new boolean[8][8];
            for(int i = start/8; i < 8; i++)
            {
                for (int j = start%8; j < 8; j++)
                {
                    if(board[i][j]) continue;
                    if(CheckBeating(new int[] { i,j }, board)) continue;
                    board[i][j] = true;
                    ferzCount++;
                }
            }
            if(ferzCount < 5) continue;
            PrintBoard(board);
            break;
        }
    }

    private static boolean CheckBeating(int[] square, boolean[][] board)
    {
        return CheckVerticalBeating(square, board)   ||
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