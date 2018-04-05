import screens.Game;

/**
 * Spiel zum Einsammeln von Brüchen.
 *
 * Mit den Pfeiltasten oder durch klicken der Buttons bewegt man sich in entsprechender Richtung.
 * Abwechselnd muss jeder Spieler sich in eine gültige Richtung bewegen.
 * Bei einer gewissen Punktzahl gewinnt dieser Spieler.
 *
 * @version 3.0
 * @author Daniel Banciu (198632), Felix Ruess (199261), Lukas Reichert (199034)
 */
public class Main {

    /**
     * Einstiegsmethode des Programmes. Beginnt die Hauptschleife des Programms.
     *
     * @param args Argumente bei Laufzeit beginn
     */
    public static void main(String[] args) {
        new Game();
    }

}
