//~--- JDK imports ------------------------------------------------------------

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.util.Random;

public class Inky extends Thread {   
    private static final String IMAGE_SOURCE = "src/pacman/img/";
    boolean                     isRunning    = true;

    /*
     * Run  method
     */
    Random        generator = new Random();
    public int    rand      = generator.nextInt(4) + 1;
    Cell[][]      cells;
    private char  direction;
    private Image ghostPicIcon;
    private int   ghostRow, ghostCol;
    Maze          maze;

    public Inky(int initialRow, int initialColumn, Maze startMaze) {
        ghostRow = initialRow;
        ghostCol = initialColumn;
        maze     = startMaze;

        // livesLeft = lives;
        cells        = maze.getCells();
        ghostPicIcon = Toolkit.getDefaultToolkit().getImage(IMAGE_SOURCE + "inky.png");
    }

    public void drawGhost(Graphics g) {
        g.drawImage(ghostPicIcon, ghostRow * 20, ghostCol * 20, maze);
        g.drawRect(ghostRow * 20, ghostCol * 20, 25, 25);    
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
     * Set the row
     * 
     */
    protected void setRow(int x) {
        ghostRow = x;
    }

    /*
     * Set the column
     * 
     */
    protected void setCol(int y) {
        ghostCol = y;
    }

    /* 
     * Set direction
     * 
     */
    public void setDirection(char direction) {
        this.direction = direction;
    }

    /*
     * Run  method
     */
    @Override
    public void run() {
        while (isRunning)   
        {
            int randomInt = generator.nextInt(4) + 1;

            switch (randomInt) {
            case (1) :
                if (isCellNavigable(ghostCol - 1, ghostRow)) {
                    moveGhost(0, -1);
                }

                maze.repaint();

                break;

            case (2) :
                if (isCellNavigable(ghostCol + 1, ghostRow)) {
                    moveGhost(0, 1);
                }

                maze.repaint();

                break;

            case (3) :
                if (isCellNavigable(ghostCol, ghostRow - 1)) {
                    moveGhost(-1, 0);
                }

                maze.repaint();

                break;

            case (4) :
                if (isCellNavigable(ghostCol, ghostRow + 1)) {
                    moveGhost(1, 0);
                }

                maze.repaint();

                break;
            }

            try {
                Thread.sleep(150);
            } catch (InterruptedException e) {
                System.err.println(e);
            }
        }
    }

    /*
     * Move Ghost
     *
     */
    public void moveGhost(int x, int y) {
        ghostRow = ghostRow + x;
        ghostCol = ghostCol + y;

        // image = image+ 1;
    }

    /**
     *  Check whether a cell is navigable
     */
    public boolean isCellNavigable(int column, int row) {
        if ((cells[column][row].getType() == 'o') || (cells[column][row].getType() == 'd')
                || (cells[column][row].getType() == 'p')) {
            return true;
        }

        return false;
    }

    protected Rectangle getBoundingBox() {
        return new Rectangle(ghostRow, ghostCol, 25, 25);
    }
}
