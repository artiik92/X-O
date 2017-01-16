import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

public class Map extends JPanel {
    private int linesCount;
    private int dotsToWin;
    private final int PANEL_SIZE = 500;
    private int CELL_SIZE;
    private boolean gameOver;
    private String gameOverMsg;
    Random rand = new Random();

    public void setW(boolean w) {
        this.w = w;
    }

    private boolean w;

    private int[][] field;
    private final int PLAYER1_DOT = 1;
    private final int PLAYER2_DOT = 2;

    public Map() {
        startNewGame(3, 3);
        setBackground(Color.white);

        addMouseListener(new MouseAdapter() {
            int q = 1;
            int z = PLAYER1_DOT;

            @Override
            public void mouseReleased(MouseEvent e) {
                if (w == true) {
                    super.mouseReleased(e);
                    int cmx, cmy;
                    cmx = e.getX() / CELL_SIZE;
                    cmy = e.getY() / CELL_SIZE;
                    if (!gameOver) {
                        if (setDot(cmx, cmy, PLAYER1_DOT)) {
                            checkFieldFull();
                            checkWin(PLAYER1_DOT);
                            repaint();
                            aiTurn();
                        }
                    }
                } else {
                    super.mouseReleased(e);
                    int cmx, cmy;
                    cmx = e.getX() / CELL_SIZE;
                    cmy = e.getY() / CELL_SIZE;
                    if (!gameOver) {
                        if (setDot(cmx, cmy, z)) {
                            checkFieldFull();
                            checkWin(z);
                            repaint();
                            if (q == 1) {
                                q = 2;
                                z = PLAYER2_DOT;
                            } else {
                                q = 1;
                                z = PLAYER1_DOT;
                            }
                        }
                    }
                }

            }
        });
    }

    public void aiTurn() {
        if (gameOver) return;
        int x, y;
        do {
            x = rand.nextInt(linesCount);
            y = rand.nextInt(linesCount);
        } while (!setDot(x, y, PLAYER2_DOT));
        checkFieldFull();
        checkWin(PLAYER2_DOT);
        repaint();
    }

    public void startNewGame(int linesCount, int dotsToWin) {
        this.linesCount = linesCount;
        this.dotsToWin = dotsToWin;
        CELL_SIZE = PANEL_SIZE / linesCount;
        gameOver = false;
        field = new int[linesCount][linesCount];
        repaint();
    }

    public void checkFieldFull() {
        boolean b = true;
        for (int i = 0; i < linesCount; i++) {
            for (int j = 0; j < linesCount; j++) {
                if (field[i][j] == 0) b = false;
            }
        }
        if (b) {
            gameOver = true;
            gameOverMsg = "DRAW";
        }
    }

    public boolean checkWin(int ox) {
        for (int i = 0; i < linesCount; i++) {
            for (int j = 0; j < linesCount; j++) {
                if (checkLine(i, j, 1, 0, dotsToWin, ox) || checkLine(i, j, 0, 1, dotsToWin, ox) || checkLine(i, j, 1, 1, dotsToWin, ox) || checkLine(i, j, 1, -1, dotsToWin, ox)) {
                    gameOver = true;
                    if (PLAYER1_DOT == ox) gameOverMsg = "PLAYER 1 WIN";
                    if (PLAYER2_DOT == ox) gameOverMsg = "PLAYER 2 WIN";
                    return true;
                }
            }
        }
        return false;
    }

    public boolean checkLine(int cx, int cy, int vx, int vy, int l, int ox) {
        if (cx + l * vx > linesCount || cy + l * vy > linesCount || cy + l * vy < -1) return false;
        for (int i = 0; i < l; i++) {
            if (field[cx + i * vx][cy + i * vy] != ox) return false;
        }
        return true;
    }

    public boolean setDot(int x, int y, int dot) {
        if (field[x][y] == 0) {
            field[x][y] = dot;
            return true;
        }
        return false;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.black);
        for (int i = 0; i <= linesCount; i++) {
            g.drawLine(0, i * CELL_SIZE, PANEL_SIZE, i * CELL_SIZE);
            g.drawLine(i * CELL_SIZE, 0, i * CELL_SIZE, PANEL_SIZE);
        }

        //Graphics2D g2d = (Graphics2D) g;
        //g2d.setStroke(new BasicStroke(5));
        //g2d.drawLine(0, 0, 10, 0);

        for (int i = 0; i < linesCount; i++) {
            for (int j = 0; j < linesCount; j++) {
                if (field[i][j] > 0) {
                    if (field[i][j] == PLAYER1_DOT) g.setColor(Color.red);
                    if (field[i][j] == PLAYER2_DOT) g.setColor(Color.blue);
                    g.fillOval(i * CELL_SIZE + 2, j * CELL_SIZE + 2, CELL_SIZE - 4, CELL_SIZE - 4);
                }
            }
        }
        if (gameOver) {
            g.setFont(new Font("Arial", Font.BOLD, 64));
            g.setColor(Color.DARK_GRAY);
            g.fillRect(0, 220, 500, 70);
            g.setColor(Color.black);
            g.drawString(gameOverMsg, 12, 277);
            g.setColor(Color.yellow);
            g.drawString(gameOverMsg, 10, 275);
        }
    }
}
