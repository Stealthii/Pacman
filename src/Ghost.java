//~--- JDK imports ------------------------------------------------------------

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.util.Random;

public class Ghost extends Thread {

    private static final String IMAGE_SOURCE = "src/pacman/img/";
    boolean isRunning = true;
    Random randGen = new Random();
    Cell[][] cells;
    private char direction;
    private Image ghostPicIcon;
    private int ghostRow, ghostCol;
    Maze maze;
    boolean deadly = true;
    int edibleLifetime = 10;
    int edibleLifeRemaining = edibleLifetime;

    public Ghost(int initialRow, int initialColumn, Maze startMaze, String ghostGraphic) {
        ghostRow = initialRow;
        ghostCol = initialColumn;
        maze = startMaze;

        // livesLeft = lives;
        cells = maze.getCells();
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
     * Move horizontally
     * 
     */
    protected void moveRow(int x) {
        if (isCellNavigable(ghostCol, ghostRow + x)) {
            ghostRow = ghostRow + x;
        }
    }
    
    /*
     * Move vertically
     * 
     */
    protected void moveCol(int y) {
        if (isCellNavigable(ghostCol + y, ghostRow)) {
            ghostCol = ghostCol + y;
        }
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
            
            // Edible processing
            if (this.deadly==false){
                this.edibleLifeRemaining--;
                if (this.edibleLifeRemaining <= 0){
                    this.deadly=true;
                }
            }
            
            // Move
            switch (randGen.nextInt(4) + 1) {
                case (1):
                    moveCol(-1);

                    break;

                case (2):
                    moveCol(1);

                    break;

                case (3):
                    moveRow(-1);

                    break;

                case (4):
                    moveRow(1);

                    break;
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
     * Check whether a cell is navigable
     *
     */
    public boolean isCellNavigable(int column, int row) {
        return (cells[column][row].getType() == 'o' || cells[column][row].getType() == 'd'
                || cells[column][row].getType() == 'p');
    }

    protected Rectangle getBoundingBox() {
        return new Rectangle(ghostRow, ghostCol, 25, 25);
    }
    
    protected void endgame(){
        this.isRunning=false;
    }
}
