//~--- JDK imports ------------------------------------------------------------

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;

import java.util.Random;

public class Ghost extends Thread {
    private static final String IMAGE_SOURCE = "src/pacman/img/";
    boolean                     isRunning    = true;
    int                         mazeheight   = 22;
    int                         mazewidth    = 46;
    Random                      randGen      = new Random();
    Cell[][]                    cells;
    private Image               ghostPicIcon;
    private int                 ghostRow, ghostCol;
    int                         lives;
    Maze                        maze;

    public Ghost(int initialRow, int initialColumn, Maze startMaze, String ghostGraphic, int initialLives) {
        ghostRow     = initialRow;
        ghostCol     = initialColumn;
        maze         = startMaze;
        lives        = initialLives;
        cells        = maze.getCells();
        ghostPicIcon = Toolkit.getDefaultToolkit().getImage(IMAGE_SOURCE + ghostGraphic);
    }

    public void drawGhost(Graphics g) {
        g.drawImage(ghostPicIcon, ghostRow * 20, ghostCol * 20, maze);
    }

    /*
     * Get the current row
     *
     */
    protected int getRow() {
        return ghostRow;
    }

    /*
     * Get the current column
     *
     */
    protected int getCol() {
        return ghostCol;
    }

    /*
     * Set horizontal
     *
     */
    protected void setRow(int x) {
        if (isCellNavigable(ghostCol, x)) {
            ghostRow = x;
        }
    }

    /*
     * Set vertical
     *
     */
    protected void setCol(int y) {
        if (isCellNavigable(y, ghostRow)) {
            ghostCol = y;
        }
    }

    /*
     * Run method
     */
    @Override
    public void run() {
        while (isRunning) {
            setCol(randGen.nextInt(mazeheight));
            setRow(randGen.nextInt(mazewidth));
            maze.repaint();

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                System.err.println(e);
            }
        }
    }

    /*
     * Check whether a cell is navigable
     *
     */
    public boolean isCellNavigable(int column, int row) {
        return (cells[column][row].getType() == 'x');
    }

    /*
     * Get number of lives left
     *
     */
    public int getLives() {
        return this.lives;
    }

    /*
     * Lose a life
     *
     */
    public void loseLife() {
        this.lives -= 1;
    }

    public void checkCollision(Pacman enemy) {
        if ((this.getCol() == enemy.getCol()) && (this.getRow() == enemy.getRow())) {
            System.out.println("Pacman ate Ghost!");
            loseLife();
        }
    }

    protected void endgame() {
        this.isRunning = false;
    }
}
