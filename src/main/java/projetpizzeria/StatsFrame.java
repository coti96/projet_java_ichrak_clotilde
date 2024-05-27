package projetpizzeria;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class StatsFrame extends JFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public StatsFrame() {
        setTitle("Bella Pizza - Statistiques");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Création des composants
        JLabel statsLabel = new JLabel("<html><h1>Statistiques</h1>"
                + "<p>Interface pour afficher les statistiques...</p></html>");

        // Ajout du label au panneau
        JPanel panel = new JPanel();
        panel.add(statsLabel);

        // Ajout du panneau à la fenêtre
        add(panel);
    }
}
