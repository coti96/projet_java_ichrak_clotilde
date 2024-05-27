package projetpizzeria;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class OrdersFrame extends JFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public OrdersFrame() {
        setTitle("Bella Pizza - Commandes");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Création des composants
        JLabel ordersLabel = new JLabel("<html><h1>Gestion des Commandes</h1>"
                + "<p>Interface pour gérer les commandes...</p></html>");
        

        // Ajout du label au panneau
        JPanel panel = new JPanel();
        panel.add(ordersLabel);

        // Ajout du panneau à la fenêtre
        add(panel);
    }
}
