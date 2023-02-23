/**
 * test
 */
public class task3
{

    public static void main(String[] args)
    {
        F8 d = new F8();
        d.provM();
    }
}


class F8 {
    public byte[] p = new byte[9];
    public void provP() {
        boolean b1, b2, b3;
        for (byte j1 = 1; j1 < 8; j1++)
            for (byte j2 = (byte) (j1 + 1); j2 <= 8; j2++) {
                b1 = (p[j1] != p[j2]);
                b2 = (p[j2] - p[j1]) != (j2 - j1);
                b3 = (p[j1] + j1) != (p[j2] + j2);
                if (b1 && b2 && b3)
                    p[0]++;
            }
    }

    public boolean provG() {
        boolean b = false;
        for (int i = 1; i <= 8; i++) {
            if ((i == p[i]) | ((i + p[i]) == 9)) {
                b = true;
            }
            if (b)
                break;
        }
        return b;
    }

    public void provM() {
        int count = 0;
        for (byte j1 = 1; j1 <= 8; j1++) {
            p[1] = j1;
            for (byte j2 = 1; j2 <= 8; j2++) {
                p[2] = j2;
                for (byte j3 = 1; j3 <= 8; j3++) {
                    p[3] = j3;
                    for (byte j4 = 1; j4 <= 8; j4++) {
                        p[4] = j4;
                        for (byte j5 = 1; j5 <= 8; j5++) {
                            p[5] = j5;
                            for (byte j6 = 1; j6 <= 8; j6++) {
                                p[6] = j6;
                                for (byte j7 = 1; j7 <= 8; j7++) {
                                    p[7] = j7;
                                    for (byte j8 = 1; j8 <= 8; j8++) {
                                        p[8] = j8;
                                        p[0] = 0;
                                        provP();
                                        if (p[0] == 28) {
                                            Boolean b = provG();
                                            if (!b) {
                                                count++;
                                                System.out.printf("%d): ", count);
                                                var board = new boolean[8][8];
                                                for (int i = 1; i <= 8; i++)
                                                    board[i - 1][p[i] - 1] = true;
                                                System.out.println();
                                                PrintBoard(board);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        System.out.printf("Всего расстановок - %d", count);
    }

    private void PrintBoard(boolean[][] board)
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