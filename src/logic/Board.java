package logic;

public class Board {

    private int n, m, k;
    private int board[][];
    private Player player1, player2;
    private Player active;

    public Board(int m, int n, int k, Player p1, Player p2) {
        this.setN(n);
        this.setM(m);
        this.setK(k);
        player1 = p1;
        player2 = p2;
        board = new int[m][n];
        active = player1;
    }

    public void nextTurn() {
        active = (active == player1) ? player2 : player1;
    }

    public Player getActivePlayer() {
        return active;
    }

    public int get2d(int m, int n) {
        return board[m][n];
    }

    public int getSize() {
        return getM() * getN();
    }

    public int getK() {
        return k;
    }

    public void setK(int k) {
        this.k = k;
    }

    public int getM() {
        return m;
    }

    public void setM(int m) {
        this.m = m;
    }

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }

    public String getPlayerNameInField(int m, int n) {
        int val = get2d(m, n);
        String retStr;
        switch (val) {
            case 1:
                retStr = player1.getName();
                break;
            case 2:
                retStr = player2.getName();
                break;
            default:
                retStr = "        ";
        }
        return String.format("%5s", retStr);
    }

    public Player getPlayer2d(int m, int n) {
        int pid = get2d(m, n);
        switch (pid) {
            case 1:
                return player1;
            case 2:
                return player2;
            default:
                return null;
        }
    }

    public void resetGame() {
        active = player1;
        board = new int[m][n];
    }

    public void setToken2d(int m, int n, Player p) {
        board[m][n] = (player1 == p) ? 1 : 2;
    }

    public WinState checkWin() {
        int tilesLeft = 0;
        for (int i = 0; i < getM(); i++) {
            for (int j = 0; j < getN(); j++) {
                int checkPlayer = board[i][j];
                if (checkPlayer != 0) {
                    boolean win = false;
                    // Check rows
                    if (j <= getN() - getK()) {
                        win = true;
                        for (int k = 1; k < getK(); k++) {
                            if (checkPlayer != board[i][j+k]) {
                                win = false;
                                break;
                            }
                        }
                    }
                    // Check columns
                    if (!win && i <= getM() - getK()) {
                        win = true;
                        for (int k = 1; k < getK(); k++) {
                            if (checkPlayer != board[i+k][j]) {
                                win = false;
                                break;
                            }
                        }
                    }
                    // Check diagonals
                    if (!win && i <= getM() - getK() && j <= getN() - getK()) {
                        win = true;
                        for (int k = 1; k < getK(); k++) {
                            if (checkPlayer != board[i+k][j+k]) {
                                win = false;
                                break;
                            }
                        }
                    }
                    if (!win && i >= getK() - 1 && j <= getN() - getK()) {
                        win = true;
                        for (int k = 1; k < getK(); k++) {
                            if (checkPlayer != board[i-k][j+k]) {
                                win = false;
                                break;
                            }
                        }
                    }
                    if (win) {
                        return WinState.values()[checkPlayer];
                    }
                } else {
                    tilesLeft++;
                }
            }
        }
        if (tilesLeft == 0) {
            return WinState.tie;
        }
        return WinState.none;
    }

}
