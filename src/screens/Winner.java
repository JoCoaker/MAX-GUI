package screens;

import components.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Das JFrame, welches das Winner Screen anzeigt.
 *
 * @author Daniel Banciu (198632), Felix Ruess (199261), Lukas Reichert (199034)
 * @version 1.0
 */
public class Winner extends JFrame implements ActionListener {

    /**
     * Winner Konstruktor.
     *
     * @param winner {Player}
     */
    public Winner(Player winner) {
        setTitle("Max - Spaß mit brüchen");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new GridLayout(4, 1));
        JLabel label = new JLabel("Herzlichen Glueckwunsch \"" + winner.getName() + "\"", SwingConstants.CENTER);
        JLabel label1 = new JLabel("Sie haben gewonnen mit " + String.format("%.2f", winner.getPoints().doubleValue()) + " Punkte", SwingConstants.CENTER);
        JButton button = new JButton("Erneut spielen!");
        button.addActionListener(this);

        getContentPane().add(label);
        getContentPane().add(label1);
        getContentPane().add(new Label());
        getContentPane().add(button);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((int) screenSize.getWidth() / 2 - 125, (int) screenSize.getHeight() / 2 - 125);

        setSize(new Dimension(250, 250));
        setResizable(false);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Neues Fenster
        new Game();
        setVisible(false);
        dispose();
    }
}
