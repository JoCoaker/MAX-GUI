package components;

import utility.BoardObject;
import utility.Config;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;
import java.util.Random;

/**
 * Das Spiel Objekt, dass das Spielfeld und die Spieler beinhaltet.
 *
 * @author Daniel Banciu (198632), Felix Ruess (199261), Lukas Reichert (199034)
 * @version 3.0
 */
public class Board implements Serializable {
    private BoardObject[][] tiles;
    private JPanel[][] tilePanels;

    private Player[] players;

    /**
     * Konstruktor.
     * Inititialisiert die benoetigten Objekte.
     */
    public Board() {
        /**
         * Ein Loop der sicherstellt, dass das Spielfeld mindestens doppelt so viele Punkte besitzt wie zum Gewinnen benoetigt wird.
         */
        while (tileSum() < Config.WIN_POINTS * 2) {
            this.generateBoard();
        }

    }

    /**
     * Generiert ein neues Spielfeld und Spieler.
     */
    private void generateBoard() {
        players = new Player[Config.PLAYER_NAMES.length];
        tiles = new BoardObject[Config.HEIGHT][Config.WIDTH];
        tilePanels = new JPanel[Config.HEIGHT][Config.WIDTH];

        // Spieler Start-Position zufaellig auswaehlen
        Random rand = new Random();
        int offsetX = rand.nextInt(2);
        int offsetY = rand.nextInt(2);

        // Spieler inititialisieren
        for (int i = 0; i < players.length; i++) {
            players[i] = new Player(Config.PLAYER_NAMES[i]);
            players[i].setPosition(Config.WIDTH / 2 - offsetX, Config.HEIGHT / 2 - offsetY);
            // Spieler auf dem Spielfeld setzen
            tiles[players[i].getY()][players[i].getX()] = players[i];
            // GUI elemente erstellen
            tilePanels[players[i].getY()][players[i].getX()] = new JPanel(new GridLayout(3, 1));
            JLabel playerLabel = new JLabel(players[i].getName(), SwingConstants.CENTER);
            playerLabel.setForeground(Color.red);
            tilePanels[players[i].getY()][players[i].getX()].add(new JLabel());
            tilePanels[players[i].getY()][players[i].getX()].add(playerLabel);
            tilePanels[players[i].getY()][players[i].getX()].add(new JLabel());
            tilePanels[players[i].getY()][players[i].getX()].setBorder(BorderFactory.createLineBorder(Color.black));
            // Position fuer den 2ten Spieler umaendern
            if (offsetX == 0) {
                offsetX++;
            } else {
                offsetX = 0;
            }
            if (offsetY == 0) {
                offsetY++;
            } else {
                offsetY = 0;
            }

        }

        // Das Spielfeld generieren
        for (int i = 0; i < tiles.length; i++) {
            for (int f = 0; f < tiles[i].length; f++) {
                // Sicher gehen, dass das Spieler-Objekt nicht ueberschrieben wird
                if (tiles[i][f] == null) {
                    // Spielfeld hinzufuegen
                    tiles[i][f] = generateFractionNumber();
                    // GUI elemente erstellen
                    tilePanels[i][f] = new JPanel(new GridLayout(3, 1));
                    JLabel numeratorLabel = new JLabel(((Fraction) tiles[i][f]).getNumerator().toString(), SwingConstants.CENTER);
                    if (((Fraction) tiles[i][f]).isInteger()) {
                        tilePanels[i][f].add(new JLabel());
                        tilePanels[i][f].add(numeratorLabel);
                        tilePanels[i][f].add(new JLabel());
                    }else {
                        JLabel emptyLabel = new JLabel("--------", SwingConstants.CENTER);
                        JLabel denominatorLabel = new JLabel(((Fraction) tiles[i][f]).getDenominator().toString(), SwingConstants.CENTER);
                        tilePanels[i][f].add(numeratorLabel);
                        tilePanels[i][f].add(emptyLabel);
                        tilePanels[i][f].add(denominatorLabel);
                    }
                    tilePanels[i][f].setBorder(BorderFactory.createLineBorder(Color.black));
                }
            }
        }
    }

    /**
     * Generiert eine 2 Stellige stellige Nummer.
     * (n = Config.MAX_NUM_DIGITS)
     *
     * @return {Fraction}
     */
    private Fraction generateFractionNumber() {
        Random rand = new Random();

        int max = 0;

        for (int i = 1; i <= Config.MAX_NUM_DIGITS; i++) {
            max += 9;
            if (i < Config.MAX_NUM_DIGITS) {
                max = max * 10;
            }
        }

        int denominator = rand.nextInt(max) + 1;
        int numerator = rand.nextInt(((denominator * 10 >= max ? max : denominator * 10) - denominator) + 1) + denominator;

        return new Fraction(numerator, denominator);
    }

    /**
     * Gibt die Punktanzahl des Spielfeldes zurueck.
     *
     * @return {double}
     */
    private double tileSum() {
        double sum = 0;

        if (tiles != null) {
            // Durch alle Spielfelder durch iterieren
            for (int i = 0; i < tiles.length; i++) {
                for (int f = 0; f < tiles[i].length; f++) {
                    // Sicherstellen, dass es ein Bruch ist (und nicht ein Spieler)
                    if (tiles[i][f] instanceof Fraction) {
                        sum += ((Fraction) tiles[i][f]).doubleValue();
                    }
                }
            }
        }

        return sum;
    }

    /**
     * Bewege ein Spieler in eine bestimmte Richtung.
     *
     * @param playerIndex Player Index, der bewegt werden soll.
     * @param c           Die Bewegungsrichtung.
     * @return {boolean} Ob die Bewegung erfolgreich war
     */
    public boolean movePlayer(int playerIndex, String c) {
        boolean moved = false;
        switch (c) {
            case "UP":
                // Ueberpruefen, ob der Spieler sich dort hin bewegen darf
                if (players[playerIndex].getY() > 0 && !(tiles[players[playerIndex].getY() - 1][players[playerIndex].getX()] instanceof Player)) {
                    // Spieler updaten und vorheriges Spielfeld Objekt loeschen (Bruch aufsammeln)
                    tiles[players[playerIndex].getY()][players[playerIndex].getX()] = null;
                    ((JLabel)tilePanels[players[playerIndex].getY()][players[playerIndex].getX()].getComponent(0)).setText("");
                    ((JLabel)tilePanels[players[playerIndex].getY()][players[playerIndex].getX()].getComponent(1)).setText("");
                    ((JLabel)tilePanels[players[playerIndex].getY()][players[playerIndex].getX()].getComponent(2)).setText("");
                    players[playerIndex].setY(players[playerIndex].getY() - 1);

                    moved = true;
                }
                break;
            case "RIGHT":
                // Ueberpruefen, ob der Spieler sich dort hin bewegen darf
                if (players[playerIndex].getX() < Config.WIDTH - 1 && !(tiles[players[playerIndex].getY()][players[playerIndex].getX() + 1] instanceof Player)) {
                    // Spieler updaten und vorheriges Spielfeld Objekt loeschen
                    tiles[players[playerIndex].getY()][players[playerIndex].getX()] = null;
                    ((JLabel)tilePanels[players[playerIndex].getY()][players[playerIndex].getX()].getComponent(0)).setText("");
                    ((JLabel)tilePanels[players[playerIndex].getY()][players[playerIndex].getX()].getComponent(1)).setText("");
                    ((JLabel)tilePanels[players[playerIndex].getY()][players[playerIndex].getX()].getComponent(2)).setText("");
                    players[playerIndex].setX(players[playerIndex].getX() + 1);

                    moved = true;
                }
                break;
            case "LEFT":
                // Ueberpruefen, ob der Spieler sich dort hin bewegen darf
                if (players[playerIndex].getX() > 0 && !(tiles[players[playerIndex].getY()][players[playerIndex].getX() - 1] instanceof Player)) {
                    // Spieler updaten und vorheriges Spielfeld Objekt loeschen
                    tiles[players[playerIndex].getY()][players[playerIndex].getX()] = null;
                    ((JLabel)tilePanels[players[playerIndex].getY()][players[playerIndex].getX()].getComponent(0)).setText("");
                    ((JLabel)tilePanels[players[playerIndex].getY()][players[playerIndex].getX()].getComponent(1)).setText("");
                    ((JLabel)tilePanels[players[playerIndex].getY()][players[playerIndex].getX()].getComponent(2)).setText("");
                    players[playerIndex].setX(players[playerIndex].getX() - 1);

                    moved = true;
                }
                break;
            case "DOWN":
                // Ueberpruefen, ob der Spieler sich dort hin bewegen darf
                if (players[playerIndex].getY() < Config.HEIGHT - 1 && !(tiles[players[playerIndex].getY() + 1][players[playerIndex].getX()] instanceof Player)) {
                    // Spieler updaten und vorheriges Spielfeld Objekt loeschen
                    tiles[players[playerIndex].getY()][players[playerIndex].getX()] = null;
                    ((JLabel)tilePanels[players[playerIndex].getY()][players[playerIndex].getX()].getComponent(0)).setText("");
                    ((JLabel)tilePanels[players[playerIndex].getY()][players[playerIndex].getX()].getComponent(1)).setText("");
                    ((JLabel)tilePanels[players[playerIndex].getY()][players[playerIndex].getX()].getComponent(2)).setText("");
                    players[playerIndex].setY(players[playerIndex].getY() + 1);

                    moved = true;
                }
                break;
        }
        // Schauen ob der Spieler sich bewegt hat
        if (moved) {
            // Ueberpruefen ob es Punkte auf der Spielfeld-Position gibt (gibt es ein Bruch zum Aufsammeln?)
            if (tiles[players[playerIndex].getY()][players[playerIndex].getX()] != null) {
                // Punkte hinzufuegen
                players[playerIndex].addPoints((Fraction) tiles[players[playerIndex].getY()][players[playerIndex].getX()]);
            }
            // Spieler Position auf dem Spielfeld aktualisieren
            tiles[players[playerIndex].getY()][players[playerIndex].getX()] = players[playerIndex];
            ((JLabel)tilePanels[players[playerIndex].getY()][players[playerIndex].getX()].getComponent(1)).setForeground(Color.red);
            ((JLabel)tilePanels[players[playerIndex].getY()][players[playerIndex].getX()].getComponent(0)).setText("");
            ((JLabel)tilePanels[players[playerIndex].getY()][players[playerIndex].getX()].getComponent(1)).setText(players[playerIndex].getName());
            ((JLabel)tilePanels[players[playerIndex].getY()][players[playerIndex].getX()].getComponent(2)).setText("");
        }

        return moved;
    }

    /**
     * Ueberpruefen ob ein Spieler gewonnen hat.
     *
     * @return {Player} null wenn keiner gewonnen hat.
     */
    public Player checkWon() {
        Player winner = null;
        for (Player player : players) {
            if (player.getPoints().doubleValue() >= Config.WIN_POINTS) {
                winner = player;
                break;
            }
        }
        return winner;
    }

    public JPanel[][] getTilePanels() {
        return tilePanels;
    }

    /**
     * Zeigt das Scoreboard an.
     *
     * @return {String}
     */
    public Player[] getPlayers() {
        return players;
    }

    /**
     * Gibt den Spieler anhand des Indexes zurueck.
     *
     * @param i Index des Spielers
     * @return {Player} Bei ungueltigen Index null
     */
    public Player getPlayer(int i) {
        if (i >= 0 && i < players.length)
            return players[i];
        return null;
    }
}
