package projetpizzeria;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import projetpizzeria.Model.Commande;
import projetpizzeria.DataAccessModel.CommandeDAO;

public class OrderDetailsPanel extends JPanel {
    private static final long serialVersionUID = 1L;
    private Font customFont;

    public OrderDetailsPanel(Commande commande) {
    	customFont = new Font("Arial", Font.PLAIN, 18);
        setLayout(new BorderLayout());
        
        // Ajout du titre
        JLabel titleLabel = new JLabel("Fiche de Livraison", JLabel.CENTER);
        titleLabel.setFont(customFont.deriveFont(24f));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0)); // Espacement
        add(titleLabel, BorderLayout.NORTH);

        // Ajout des détails
        JTextArea detailsTextArea = new JTextArea(10, 40);
        detailsTextArea.setText(getOrderDetailsText(commande));
        detailsTextArea.setFont(customFont.deriveFont(18f));
        detailsTextArea.setEditable(false);
        detailsTextArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Marges internes

        JScrollPane scrollPane = new JScrollPane(detailsTextArea);
        add(scrollPane, BorderLayout.CENTER);
    }

  

    private String getOrderDetailsText(Commande commande) {
    	
    	System.out.println("idLivreur: " + commande.getIdLivreur());
        CommandeDAO commandeDAO = new CommandeDAO();
        String livreurNom = commandeDAO.getLivreurNom(commande.getIdLivreur());
        String vehiculeType = commandeDAO.getVehiculeType(commande.getIdLivreur());
        String clientNom = commandeDAO.getClientNom(commande.getIdClient());
        String pizzaNom = commandeDAO.getPizzaNom(commande.getIdPizza());
        double pizzaPrixBase = commandeDAO.getPizzaPrice(commande.getIdPizza());
        boolean retard = commande.isBonusLivraison();  // Assuming bonusLivraison indicates delay

        StringBuilder details = new StringBuilder();
        details.append("Nom du Livreur: ").append(livreurNom).append("\n");
        details.append("Type de Véhicule: ").append(vehiculeType).append("\n");
        details.append("Nom du Client: ").append(clientNom).append("\n");
        details.append("Date de Commande: ").append(commande.getDateCommande()).append("\n");
        details.append("Retard éventuel: ").append(retard ? "Oui" : "Non").append("\n");
        details.append("Nom de la Pizza: ").append(pizzaNom).append("\n");
        details.append("Prix de Base de la Pizza: ").append(pizzaPrixBase).append(" €\n");
        return details.toString();
    }
}
