//~--- JDK imports ------------------------------------------------------------

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

class PacmanGUI extends JFrame {
    private static JTextField gameDisp;
    private static Maze       gameMaze;

    public void startGUI() {

        // Control Button Declarations
        final JButton stopButton, startButton, pauseButton, resumeButton;
        JFrame        gameBoard = new JFrame("Pacman");

        gameBoard.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameBoard.setLayout(new BorderLayout());
        gameMaze = new Maze();

        // add the maze to the game board
        gameBoard.add(gameMaze, BorderLayout.CENTER);
        startButton = new JButton("Start");
        startButton.setEnabled(false);
        stopButton = new JButton("Stop");
        stopButton.setEnabled(false);
        pauseButton = new JButton("Pause");
        pauseButton.setEnabled(true);
        resumeButton = new JButton("Resume");
        resumeButton.setEnabled(false);
        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

//              pacman.setRunning(true);
//              pacman.setEnabled(true);
//              pacman.startGame();
                startButton.setEnabled(false);
                stopButton.setEnabled(true);
                pauseButton.setEnabled(true);
                resumeButton.setEnabled(false);
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
        JPanel dispPanel = new JPanel();

        dispPanel.setLayout(new FlowLayout());
        dispPanel.setBackground(Color.BLACK);
        gameBoard.add(dispPanel, BorderLayout.SOUTH);

//      CONTROLS PANEL
        JPanel controls = new JPanel();

        controls.setLayout(new FlowLayout());

//      controls.add(startButton);
//      controls.add(stopButton);
        controls.add(pauseButton);
        controls.add(resumeButton);
        controls.setBackground(Color.BLACK);
        dispPanel.add(controls, BorderLayout.SOUTH);    // add controls to the gameboard frame

//      Score & Lives
        gameDisp = new JTextField(10);
        gameDisp.setEditable(false);
        gameDisp.setBackground(Color.BLACK);
        gameDisp.setForeground(Color.YELLOW);
        gameDisp.setBorder(null);
        gameDisp.removeFocusListener(null);
        dispPanel.add(gameDisp);
        newDisp();

//      Display the game window on screen
        gameBoard.pack();
        gameBoard.setVisible(true);
    }

    public static void newDisp() {
        gameDisp.setText("Lives left: " + gameMaze.getLives());
        gameDisp.repaint();
    }
}
