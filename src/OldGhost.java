//~--- JDK imports ------------------------------------------------------------

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

public class OldGhost extends Thread {

    private static final String IMAGE_SOURCE = "src/pacman/img/";
    boolean isRunning = true;
    private Image blinky;
    Cell[][] cells;
    private Image clyde;
    private char direction;
    private int ghostRow, ghostCol;
    private Image inky;
    Maze maze;
    private Image pinky;

    public OldGhost(int initialRow, int initialColumn, Maze startMaze) {
        ghostRow = initialRow;
        ghostCol = initialColumn;
        maze = startMaze;
        cells = maze.getCells();
        inky = Toolkit.getDefaultToolkit().getImage(IMAGE_SOURCE + "inky.png");
        pinky = Toolkit.getDefaultToolkit().getImage(IMAGE_SOURCE + "pinky.png");
        blinky = Toolkit.getDefaultToolkit().getImage(IMAGE_SOURCE + "blinky.png");
        clyde = Toolkit.getDefaultToolkit().getImage(IMAGE_SOURCE + "clyde.png");
    }

    // draw OldGhost
//  public void drawGhost(Graphics g)
//  {
//        switch (maze.getGhost())
//        {
//            case ("inky"): g.drawImage(inky, ghostRow, ghostCol, maze);
//            break;
//            case (2): g.drawImage(pinky, ghostRow, ghostRow, maze);
//            break;
//            case (3): g.drawImage(blinky, ghostRow, ghostRow, maze);
//            break;
//            case (4): g.drawImage(clyde, ghostRow, ghostRow, maze);
//            break;
//        }
//    }//end drawGhost
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
     * Run method
     */
    @Override
    public void run() {
        while (isRunning) {
            if (direction == 'u') {
                if (isCellNavigable(ghostCol - 1, ghostRow)) {
                    moveGhost(0, -1);
                }
            }

            maze.repaint();

            if (direction == 'd') {
                if (isCellNavigable(ghostCol + 1, ghostRow)) {
                    moveGhost(0, 1);
                }
            }

            maze.repaint();

            if (direction == 'l') {
                if (isCellNavigable(ghostCol, ghostRow - 1)) {
                    moveGhost(-1, 0);
                }
            }

            maze.repaint();

            if (direction == 'r') {
                if (isCellNavigable(ghostCol, ghostRow + 1)) {
                    moveGhost(1, 0);
                }
            }

            maze.repaint();

            try {
                Thread.sleep(150);
            } catch (InterruptedException e) {
                System.err.println(e);
            }
        }
    }

    /*
     * Move OldGhost
     *
     */
    public void moveGhost(int x, int y) {
        ghostRow = ghostRow + x;
        ghostCol = ghostCol + y;
    }

    /*
     * Check whether a cell is navigable
     *
     */
    public boolean isCellNavigable(int column, int row) {
        if ((cells[column][row].getType() == 'o') || (cells[column][row].getType() == 'd')
                || (cells[column][row].getType() == 'p')) {
            return true;
        }

        return false;
    }

    void drawGhost(Graphics g) {
        g.drawImage(inky, ghostRow, ghostCol, maze);
        System.out.println("ROW" + ghostRow);
        System.out.println("COL" + ghostCol);
    }
}
