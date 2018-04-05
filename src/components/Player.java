package components;

import utility.BoardObject;

/**
 * Stellt einen Spieler dar.
 *
 * @version 1.0
 * @author Daniel Banciu (198632), Felix Ruess (199261), Lukas Reichert (199034)
 */
public class Player implements BoardObject {
    private Fraction points;
    private int x;
    private int y;
    private String name;

    /**
     * Konstruktor.
     * Inititialisiert den Spieler mit seinen Namen und Punktzahl.
     *
     * @param name Name des Spielers
     */
    public Player(String name) {
        this.name = name;
        this.points = new Fraction(0, 1);
    }

    /**
     * Setzt die Position des Spielers auf dem Spielfeld.
     *
     * @param x Position X
     * @param y Position Y
     */
    public void setPosition(int x,int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Gibt die Punktzahl des Spielers zurück.
     *
     * @return points {Fraction} Punkte des Spielers.
     */
    public Fraction getPoints() {
        return points;
    }

    /**
     * Eine Punktzahl dem Spieler hinzufügen.
     *
     * @param f Punktzahl die hinzugefügt werden soll.
     */
    public void addPoints(Fraction f) {
        points = points.add(f);
    }

    /**
     * Den Namen des Spielers zurückgeben.
     *
     * @return name {String} Den Spielername.
     */
    public String getName() {
        return name;
    }

    /**
     * Position X setzen.
     *
     * @param x Position X.
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Position Y setzen.
     *
     * @param y Position Y.
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Position X zurückgeben.
     *
     * @return x {int} Position X.
     */
    public int getX() {
        return x;
    }

    /**
     * Position Y zurückgeben.
     *
     * @return y {int} Position Y.
     */
    public int getY() {
        return y;
    }

    @Override
    public String display() {
        return name;
    }
}
