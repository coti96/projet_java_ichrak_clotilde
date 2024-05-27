package projetpizzeria;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MenuFrame extends JFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    JButton exitButton = new JButton("Retour");

	public MenuFrame() {
        setTitle("Bella Pizza - Menu");
        setSize(1300, 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);


        // Création des composants
        JLabel menuLabel = new JLabel("<html><h1>Menu des Pizzas</h1>"
                + "<ul>"
                + "<li>Margherita</li>"
                + "<li>Pepperoni</li>"
                + "<li>Hawaiian</li>"
                + "<li>Veggie</li>"
                + "<li>BBQ Chicken</li>"
                + "</ul></html>");
        
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });


        // Ajout du label au panneau
        JPanel panel = new JPanel();
        panel.add(menuLabel);
        panel.add(exitButton);
;
        // Ajout du panneau à la fenêtre
        add(panel);
    }
}
