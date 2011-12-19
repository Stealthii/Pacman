//~--- JDK imports ------------------------------------------------------------

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

class PacmanGUI extends JFrame {

    private static JTextField currentScore;
    private static Maze gameMaze;

    public void startGUI() {
        JTextField lives;


        // Control Button Declarations
        final JButton stopButton, startButton, pauseButton, resumeButton;
        JFrame gameBoard = new JFrame("Pacman");

        gameBoard.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameBoard.setLayout(
                new BorderLayout());

        // Reference to the maze
        gameMaze = new Maze();
        // Reference to the cells that make up the maze
        Cell[][] cells = gameMaze.getCells();

        // add the maze to the game board
        gameBoard.add(gameMaze, BorderLayout.CENTER);
        startButton = new JButton("Start");

        startButton.setEnabled(
                true);
        stopButton = new JButton("Stop");

        stopButton.setEnabled(
                false);
        pauseButton = new JButton("Pause");

        pauseButton.setEnabled(
                false);
        resumeButton = new JButton("Resume");

        resumeButton.setEnabled(
                false);
        startButton.addActionListener(
                new ActionListener() {

                    public void actionPerformed(ActionEvent e) {

//              pacman.setRunning(true);
//              pacman.setEnabled(true);
//              pacman.startGame();
                        startButton.setEnabled(false);
                        stopButton.setEnabled(true);
                        pauseButton.setEnabled(true);
                        resumeButton.setEnabled(true);
                    }
                });
        stopButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                startButton.setEnabled(true);
                stopButton.setEnabled(false);
                pauseButton.setEnabled(false);
                resumeButton.setEnabled(false);

//              pacman.stopGame();
//              Thread.currentThread().interrupt();//stop a thread that could wait for a long period
//              pacman.setEnabled(false);
            }
        });
        pauseButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                startButton.setEnabled(false);
                stopButton.setEnabled(true);
                pauseButton.setEnabled(false);
                resumeButton.setEnabled(true);

//              pacman.pauseGame();
//              animation.interrupt();//stop a thread that could wait for a long period
//              face.setEnabled(false);
            }
        });
        resumeButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                startButton.setEnabled(false);
                stopButton.setEnabled(true);
                pauseButton.setEnabled(true);
                resumeButton.setEnabled(false);

//              pacman.resumeGame();
//              animation.interrupt();//stop a thread that could wait for a long period
//              face.setEnabled(false);
            }
        });

//      SCORE PANEL
        JPanel scorePanel = new JPanel();

        scorePanel.setLayout(new FlowLayout());
        scorePanel.setBackground(Color.BLACK);
        gameBoard.add(scorePanel, BorderLayout.SOUTH);

//      CONTROLS PANEL
//              JPanel controls = new JPanel();
//              controls.setLayout(new FlowLayout());
//              controls.add(startButton);
//              controls.add(stopButton);
//              controls.add(pauseButton);
//              controls.add(resumeButton);
//              controls.setBackground(Color.BLACK);
//              scorePanel.add(controls, BorderLayout.SOUTH);//add controls to the gameboard frame
//      Score
        currentScore = new JTextField(10);
        currentScore.setEditable(false);
        currentScore.setBackground(Color.BLACK);
        currentScore.setForeground(Color.YELLOW);
        currentScore.setBorder(null);
        currentScore.removeFocusListener(null);

//      Lives Remaining
        lives = new JTextField(10);
        lives.setEditable(false);
        lives.setBackground(Color.BLACK);
        lives.setForeground(Color.YELLOW);
        lives.setBorder(null);
        currentScore.removeFocusListener(null);
        scorePanel.add(currentScore, lives);
        newScore();

//      Display the game window on screen
        gameBoard.pack();
        gameBoard.setVisible(true);
    }

    public static void newScore() {
        currentScore.setText("Score " + gameMaze.getScore());
        currentScore.repaint();
    }
}
