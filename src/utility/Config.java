package utility;

/**
 * Die Konfiguration Variablen des Spiels.
 *
 * @version 1.0
 * @author Daniel Banciu (198632), Felix Ruess (199261), Lukas Reichert (199034)
 */
public class Config {
    /**
     * Anzahl der Spieler (Namen der Spieler) festlegen (Derzeit werden nur 2 unterstützt).
     */
    public static final String[] PLAYER_NAMES = new String[]{"W", "B"};

    /**
     * Die Breite des Spielfeldes (nur Gerade-Zahlen).
     */
    public static final int WIDTH = 8;

    /**
     * Die Höhe des Spielfeldes (nur Gerade-Zahlen).
     */
    public static final int HEIGHT = 8;

    /**
     * Wie viel Stellen die Bruche maximal haben dürfen.
     */
    public static final int MAX_NUM_DIGITS = 2;

    /**
     * Punktzahl die ein Spieler erreichen muss, um zu gewinnen.
     */
    public static final int WIN_POINTS = 80;
}
