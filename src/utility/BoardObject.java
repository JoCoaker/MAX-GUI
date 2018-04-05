package utility;

/**
 * Jegliche Objekte die in dem Spielfeld angezeigt werden (z.B. Brüche, Spieler, ...) müssen dieses Interface implementieren.
 *
 * @version 1.0
 * @author Daniel Banciu (198632), Felix Ruess (199261), Lukas Reichert (199034)
 */
public interface BoardObject {

    /**
     * Methode zum Anzeigen des Gegenstands auf dem Spielfeld.
     *
     * @return String der angezeigt wird.
     */
    String display();
}
