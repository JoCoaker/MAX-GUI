package screens;

import components.Board;
import components.Player;
import utility.Config;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

/**
 * Das JFrame welches das eigentliche Spiel beinhaltet inklusive des Scoreboards und Benutzereingabe.
 *
 * @author Daniel Banciu (198632), Felix Ruess (199261), Lukas Reichert (199034)
 * @version 3.0
 */
public class Game extends JFrame implements ActionListener, KeyListener {

    private Board board;
    private int playerIndex;

    private JPanel boardPanel;

    private JPanel scoreboardPanel;
    private JLabel infoLabel;
    private JButton up;
    private JButton left;
    private JButton right;
    private JButton down;

    /**
     * Game Konstruktor.
     */
    public Game() {
        // screens.Game code
        board = new Board();
        playerIndex = new Random().nextInt(Config.PLAYER_NAMES.length);

        setTitle("Max - Spass mit Bruechen");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        setLayout(new BorderLayout());

        boardPanel = new JPanel(new GridLayout(Config.HEIGHT, Config.WIDTH));

        scoreboardPanel = new JPanel(new GridLayout(3, 1));
        JPanel navigationPanel = new JPanel(new GridLayout(4, 3));

        infoLabel = new JLabel("Spieler \"" + board.getPlayer(playerIndex).getName() + "\" ist dran:");
        up = new JButton("UP");
        left = new JButton("LEFT");
        right = new JButton("RIGHT");
        down = new JButton("DOWN");
        up.setActionCommand("UP");
        left.setActionCommand("LEFT");
        right.setActionCommand("RIGHT");
        down.setActionCommand("DOWN");

        up.addActionListener(this);
        left.addActionListener(this);
        right.addActionListener(this);
        down.addActionListener(this);

        JButton buttonNewGame = new JButton("New");
        buttonNewGame.setActionCommand("GAME");
        buttonNewGame.addActionListener(this);

        JPanel panel = new JPanel(new GridLayout(1, 2));
        panel.add(new JLabel());
        panel.add(buttonNewGame);

        // Scoreboard
        scoreboardPanel.add(new JLabel("Scoreboard:"));
        for (int i = 0; i < Config.PLAYER_NAMES.length; i++) {
            scoreboardPanel.add(new JLabel(""));
        }
        scoreboard();

        // Navigation Panel
        navigationPanel.add(new JPanel());
        navigationPanel.add(infoLabel);
        navigationPanel.add(new JPanel());
        navigationPanel.add(new JPanel());
        navigationPanel.add(up);
        navigationPanel.add(new JPanel());
        navigationPanel.add(left);
        navigationPanel.add(new JPanel());
        navigationPanel.add(right);
        navigationPanel.add(new JPanel());
        navigationPanel.add(down);
        navigationPanel.add(panel);

        JPanel[][] tiles = this.board.getTilePanels();
        // Durch alle Spielfelder gehen und die Panels hinzufuegen
        for (int i = 0; i < tiles.length; i++) {
            for (int f = 0; f < tiles[i].length; f++) {
                boardPanel.add(tiles[i][f]);
            }
        }

        // Componenten dem frame hinzufuegen
        getContentPane().add(scoreboardPanel, BorderLayout.NORTH);
        getContentPane().add(boardPanel, BorderLayout.CENTER);
        getContentPane().add(navigationPanel, BorderLayout.SOUTH);

        up.addKeyListener(this);
        left.addKeyListener(this);
        right.addKeyListener(this);
        down.addKeyListener(this);
        buttonNewGame.addKeyListener(this);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((int) screenSize.getWidth() / 2 - 300, (int) screenSize.getHeight() / 2 - 250);

        setSize(new Dimension(600, 700));
        setVisible(true);
    }

    /**
     * Updated das Scoreboard.
     */
    private void scoreboard() {
        Player[] players = board.getPlayers();
        // Scoreboard der Spieler hinzufuegen.
        for (int i = 0; i < players.length; i++) {
            ((JLabel) scoreboardPanel.getComponent(i + 1)).setText(players[i].getName() + ": " + String.format("%.2f", players[i].getPoints().doubleValue()) + " Punkte");
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Neues Fenster
        if (e.getActionCommand().equals("GAME")) {
            new Game();
            return;
        }
        // Input verarbeiten
        boolean moved = board.movePlayer(playerIndex, e.getActionCommand());
        if (moved) {
            // Nächster Spieler ist dran
            if (++playerIndex >= Config.PLAYER_NAMES.length) {
                playerIndex = 0;
            }
            infoLabel.setText("Spieler \"" + board.getPlayer(playerIndex).getName() + "\" ist dran:");
            scoreboard();
            infoLabel.setForeground(Color.black);
        } else {
            // Spieler nochmal zur Eingabe auffordern
            infoLabel.setText("\"" + board.getPlayer(playerIndex).getName() + "\" probieren Sie eine andere Richtung!");
            infoLabel.setForeground(Color.red);
        }
        // Überprüfen, ob jemand gewonnen hat
        Player winner = board.checkWon();
        if (winner != null) {
            setVisible(false);
            new Winner(winner);
            dispose();
        }

    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Key Input verarbeiten
        switch (e.getExtendedKeyCode()) {
            case KeyEvent.VK_UP:
                actionPerformed(new ActionEvent(up, 1, "UP"));
                break;
            case KeyEvent.VK_LEFT:
                actionPerformed(new ActionEvent(left, 2, "LEFT"));
                break;
            case KeyEvent.VK_RIGHT:
                actionPerformed(new ActionEvent(right, 3, "RIGHT"));
                break;
            case KeyEvent.VK_DOWN:
                actionPerformed(new ActionEvent(down, 4, "DOWN"));
                break;
        }
    }
}
