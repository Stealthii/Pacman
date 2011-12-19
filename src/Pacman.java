//~--- JDK imports ------------------------------------------------------------

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;

public class Pacman extends Thread {

    private static final String IMAGE_SOURCE = "src/pacman/img/";
    static String[] pacmanSequencesL = {
        IMAGE_SOURCE + "pac32_left.png", IMAGE_SOURCE + "pac32_left_wide.png", IMAGE_SOURCE + "pac32_left_widest.png",
        IMAGE_SOURCE + "pacman_closed.png"
    };
    static String[] pacmanSequencesR = {
        IMAGE_SOURCE + "pac32_right.png", IMAGE_SOURCE + "pac32_right_widest.png",
        IMAGE_SOURCE + "pac32_right_wide.png", IMAGE_SOURCE + "pacman_closed.png"
    };
    static String[] pacmanSequencesU = {
        IMAGE_SOURCE + "pac32_up.png", IMAGE_SOURCE + "pac32_up_wide.png", IMAGE_SOURCE + "pac32_up_widest.png",
        IMAGE_SOURCE + "pacman_closed.png"
    };
    static String[] pacmanSequencesD = {
        IMAGE_SOURCE + "pac32_down.png", IMAGE_SOURCE + "pac32_down_wide.png", IMAGE_SOURCE + "pac32_down_widest.png",
        IMAGE_SOURCE + "pacman_closed.png"
    };
    int current = 0;
    private char direction = 'l';
    boolean isRunning = true;
    int score = 0;
    Image[] pictureUp = new Image[pacmanSequencesU.length];
    Image[] pictureRight = new Image[pacmanSequencesR.length];
    Image[] pictureLeft = new Image[pacmanSequencesL.length];
    Image[] pictureDown = new Image[pacmanSequencesD.length];
    int totalPictures = 0;
    Cell[][] cells;
    int livesLeft;
    Maze maze;
    private int pacmanRow, pacmanCol;
    private String score_string;
    Thread thread;

    // int pause = 200;
    public Pacman(int initialRow, int initialColumn, Maze startMaze, int lives) {
        pacmanRow = initialRow;
        pacmanCol = initialColumn;
        maze = startMaze;
        livesLeft = lives;
        cells = maze.getCells();

        Toolkit kit = Toolkit.getDefaultToolkit();

        for (int i = 0; i < pacmanSequencesL.length; i++) {
            pictureLeft[i] = kit.getImage(pacmanSequencesL[i]);
        }

        for (int i = 0; i < pacmanSequencesR.length; i++) {
            pictureRight[i] = kit.getImage(pacmanSequencesR[i]);
        }

        for (int i = 0; i < pacmanSequencesU.length; i++) {
            pictureUp[i] = kit.getImage(pacmanSequencesU[i]);
        }

        for (int i = 0; i < pacmanSequencesD.length; i++) {
            pictureDown[i] = kit.getImage(pacmanSequencesD[i]);
        }
    }

    /*
     * Draw Pacman
     *
     */
    public void drawPacman(Graphics g) {
        if (direction == 'u') {
            if (current > pictureUp.length - 1) {
                current = 0;
            }

            g.drawImage(pictureUp[current], pacmanRow * 20, pacmanCol * 20, 22, 22, maze);
            g.drawRect(pacmanRow * 20, pacmanCol * 20, 25, 25);
        }

        if (direction == 'd') {
            if (current > pictureDown.length - 1) {
                current = 0;
            }

            g.drawImage(pictureDown[current], pacmanRow * 20, pacmanCol * 20, 22, 22, maze);
            g.drawRect(pacmanRow * 20, pacmanCol * 20, 25, 25);
        }

        if (direction == 'l') {
            if (current > pictureLeft.length - 1) {
                current = 0;
            }

            g.drawImage(pictureLeft[current], pacmanRow * 20, pacmanCol * 20, 22, 22, maze);
            g.drawRect(pacmanRow * 20, pacmanCol * 20, 25, 25);
        }

        if (direction == 'r') {
            if (current > pictureRight.length - 1) {
                current = 0;
            }

            g.drawImage(pictureRight[current], pacmanRow * 20, pacmanCol * 20, 22, 22, maze);
            g.drawRect(pacmanRow * 20, pacmanCol * 20, 25, 25);
        }
    }

    /*
     * Get the current row
     *
     */
    protected int getRow() {
        return pacmanRow;
    }

    /*
     * Get the current column
     *
     */
    protected int getCol() {
        return pacmanCol;
    }

    /*
     * Set the row
     *
     */
    protected void setRow(int x) {
        pacmanRow = x;
    }

    /*
     * Set the column
     *
     */
    protected void setCol(int y) {
        pacmanCol = y;
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
                if (isCellNavigable(pacmanCol - 1, pacmanRow)) {
                    movePacman(0, -1);
                }
            }

            eatPellet(pacmanCol, pacmanRow);
            maze.checkCollision();
            maze.repaint();

            if (direction == 'd') {
                if (isCellNavigable(pacmanCol + 1, pacmanRow)) {
                    movePacman(0, 1);
                }
            }

            eatPellet(pacmanCol, pacmanRow);
            maze.checkCollision();
            maze.repaint();

            if (direction == 'l') {
                if (isCellNavigable(pacmanCol, pacmanRow - 1)) {
                    movePacman(-1, 0);
                }
            }

            eatPellet(pacmanCol, pacmanRow);

            // maze.checkCollision();
            maze.repaint();

            if (direction == 'r') {
                if (isCellNavigable(pacmanCol, pacmanRow + 1)) {
                    movePacman(1, 0);
                }
            }

            eatPellet(pacmanCol, pacmanRow);
            maze.checkCollision();
            maze.repaint();

            try {
                Thread.sleep(150);
            } catch (InterruptedException e) {
                System.err.println(e);
            }
        }
    }

//  TODO - implement audio
    /**
     * Check if next move will be pellet Detect Collision and "eat pellet"
     *
     */
    public void eatPellet(int column, int row) {
        if (cells[column][row].getType() == 'd') {
            score += 10;
            cells[column][row].type = 'o';
            maze.eatPelletSound.play();
            PacmanGUI.newScore();
        }

        if (cells[column][row].getType() == 'p') {
            score += 50;
            cells[column][row].type = 'o';
            maze.eatPelletSound.play();
            PacmanGUI.newScore();

            // Ghost.setEdible;
        }
    }

    protected Rectangle getBoundingBox() {
        return new Rectangle(pacmanRow * 20, pacmanCol * 20, 25, 25);
    }

    /*
     * Move Pacman
     *
     */
    public void movePacman(int x, int y) {
        pacmanRow = pacmanRow + x;
        pacmanCol = pacmanCol + y;
        current++;
        System.out.println("ROW " + pacmanRow + ", COL " + pacmanCol);    // print out current row and column to console
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

    /*
     * Get the current score
     *
     */
    public int getScore() {
        return score;
    }

    /*
     * Get number of lives left
     *
     */
    public int getLives() {
        return livesLeft;
    }
}
