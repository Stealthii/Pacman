//~--- JDK imports ------------------------------------------------------------

import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.JPanel;

/**
 * Represents the maze that appears on screen. Creates the maze data using a 2D
 * array of Cell objects, and renders the maze on screen.
 *
 */
public final class Maze extends JPanel {

    final static int CELL = 20;
    private int ghostInitialColumn = 13;
    private int ghostInitialRow = 21;
    private int lives = 0;
    private String map = "src/pacman/levels/level1.txt/";
    private int pacmanInitialColumn = 7;
    private int pacmanInitialRow = 21;
    private int score = 0;
    String sound = "eat.wav";
    public Pacman pacman;
    private Ghost inky;
    private Ghost blinky;
    private Ghost pinky;
    private Ghost clyde;
    private Cell[][] cells;
    AudioClip eatPelletSound;
    private int tileHeight;
    private int tileWidth;

    public Maze() {
        createCellArray(map);
        setPreferredSize(new Dimension(CELL * tileWidth, CELL * tileHeight));

        pacman = new Pacman(pacmanInitialRow, pacmanInitialColumn, this, 3);
        pacman.start();
        inky = new Ghost(ghostInitialRow, ghostInitialColumn, this, "inky.png");
        inky.start();
        blinky = new Ghost(ghostInitialRow + 3, ghostInitialColumn, this, "blinky.png");
        blinky.start();
        pinky = new Ghost(ghostInitialRow, ghostInitialColumn + 3, this, "pinky.png");
        pinky.start();
        clyde = new Ghost(ghostInitialRow + 3, ghostInitialColumn + 3, this, "clyde.png");
        clyde.start();

        /*
         * Key Listeners
         */
        this.setFocusable(true);
        this.addKeyListener(new KeyAdapter() {

            @Override
            public void keyPressed(KeyEvent k) {
                switch (k.getKeyCode()) {
                    case (KeyEvent.VK_KP_DOWN):
                    case (KeyEvent.VK_DOWN):
                        pacman.setDirection('d');

                        break;

                    case (KeyEvent.VK_KP_UP):
                    case (KeyEvent.VK_UP):
                        pacman.setDirection('u');

                        break;

                    case (KeyEvent.VK_KP_RIGHT):
                    case (KeyEvent.VK_RIGHT):
                        pacman.setDirection('r');

                        break;

                    case (KeyEvent.VK_KP_LEFT):
                    case (KeyEvent.VK_LEFT):
                        pacman.setDirection('l');

                        break;
                }
            }
        });
        checkCollision();
        repaint();
    }

    /**
     * Reads from the map file and create the two dimensional array
     */
    private void createCellArray(String mapFile) {

        // Scanner object to read from map file
        Scanner fileReader;
        ArrayList<String> lineList = new ArrayList<String>();

        // Attempt to load the maze map file
        try {
            fileReader = new Scanner(new File(mapFile));

            while (true) {
                String line = null;

                try {
                    line = fileReader.nextLine();
                } catch (Exception eof) {
                    // throw new A5FatalException("Could not read resource");
                }

                if (line == null) {
                    break;
                }

                lineList.add(line);
            }

            tileHeight = lineList.size();
            tileWidth = lineList.get(0).length();

            // Create the cells
            cells = new Cell[tileHeight][tileWidth];

            for (int row = 0; row < tileHeight; row++) {
                String line = lineList.get(row);

                for (int column = 0; column < tileWidth; column++) {
                    char type = line.charAt(column);

                    cells[row][column] = new Cell(column, row, type);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Maze map file not found");
        }
    }

    /**
     * Generic paint method Iterates through each cell/tile in the 2D array,
     * drawing each in the appropriate location on screen
     *
     * @param g Graphics object
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, tileWidth * CELL, tileHeight * CELL);

        // Outer loop loops through each row in the array
        for (int row = 0; row < tileHeight; row++) {

            // Inner loop loops through each column in the array
            for (int column = 0; column < tileWidth; column++) {
                cells[row][column].drawBackground(g);
            }
        }

        // Pacman.drawScore(g);
        pacman.drawPacman(g);
        inky.drawGhost(g);
        blinky.drawGhost(g);
        clyde.drawGhost(g);
        pinky.drawGhost(g);
    }

    public Cell[][] getCells() {
        return cells;
    }

    public int getScore() {
        return pacman.getScore();
    }

    public int getLives() {
        return pacman.getLives();
    }

    public void playSound() {

        // play sound
        try {
            URL eatGhostSound = new URL("file:" + System.getProperty("src/pacman/sounds") + "/" + sound);

            eatPelletSound = java.applet.Applet.newAudioClip(eatGhostSound);
        } catch (MalformedURLException e) {
            System.err.println("Bad URL:" + e);
        }
    }

    public void checkCollision() {
        Rectangle pinkyBox = pinky.getBoundingBox();
        Rectangle inkyBox = inky.getBoundingBox();
        Rectangle blinkyBox = blinky.getBoundingBox();
        Rectangle clydeBox = clyde.getBoundingBox();

        if (pinkyBox.intersects(pacman.getBoundingBox())) {
            System.out.println("Pacman eaten by Pinky!");
            lives--;

            // pacmanDies.play();
        } else if (inkyBox.intersects(pacman.getBoundingBox())) {
            System.out.println("Pacman eaten by Inky!");
            lives = lives - 1;

            // pacmanDies.play();
        } else if (blinkyBox.intersects(pacman.getBoundingBox())) {
            System.out.println("Pacman eaten by Blinky!");
            lives = lives - 1;

            // pacmanDies.play();
        } else if (clydeBox.intersects(pacman.getBoundingBox())) {
            System.out.println("Pacman eaten by Clyde!");
            lives = lives - 1;

            // pacmanDies.play();
        }
    }
}
