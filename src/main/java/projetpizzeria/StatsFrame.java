package projetpizzeria;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class StatsFrame extends JFrame {

    private static final long serialVersionUID = 1L;
    private Font italianFont;
    
   

    public StatsFrame() {
    	 italianFont = new Font("Times New Roman", Font.PLAIN, 18);
        setTitle("Bella Pizza - Statistiques");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Création des composants
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(Color.WHITE);

        JLabel statsLabel = new JLabel(" Statistiques", JLabel.CENTER);
        statsLabel.setFont(italianFont.deriveFont(Font.ITALIC, 30f));
        statsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(statsLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));

        
        panel.add(createStatPanel("Meilleur Client", getBestClient()));
        panel.add(createStatPanel("Pire Livreur", getWorstDeliveryMan()));
        panel.add(createStatPanel("Pizza la Plus Demandée", getMostPopularPizza()));
        panel.add(createStatPanel("Pizza la Moins Demandée", getLeastPopularPizza()));
        panel.add(createStatPanel("Ingrédient Favori", getFavoriteIngredient()));
        panel.add(createStatPanel("Véhicules n'ayant jamais servi", getUnusedVehicles()));
        panel.add(createStatPanel("Nombre de Commandes par Client", getOrdersPerClient()));
        panel.add(createStatPanel("Moyenne des Commandes", getAverageOrders()));
        panel.add(createStatPanel("Clients ayant Commandé plus que la Moyenne", getClientsAboveAverage()));

        // Ajout du panneau à la fenêtre
        add(new JScrollPane(panel));
    }

  

    private JPanel createStatPanel(String title, String content) {
        JPanel statPanel = new JPanel();
        statPanel.setLayout(new BorderLayout());
        statPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        statPanel.setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel(title, JLabel.LEFT);
        titleLabel.setFont(italianFont.deriveFont(20f));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));
        statPanel.add(titleLabel, BorderLayout.NORTH);

        JLabel contentLabel = new JLabel("<html>" + content + "</html>", JLabel.LEFT);
        contentLabel.setFont(italianFont.deriveFont(16f));
        statPanel.add(contentLabel, BorderLayout.CENTER);

        return statPanel;
    }

    private String getBestClient() {
        String query = "SELECT client.prenom, client.nom, COUNT(*) AS nb_commandes " +
                       "FROM commande " +
                       "JOIN client ON commande.id_client = client.id_client " +
                       "GROUP BY client.id_client " +
                       "ORDER BY nb_commandes DESC " +
                       "LIMIT 1;";
        StringBuilder result = new StringBuilder();
        try (Connection conn = new DatabaseConnection().connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            if (rs.next()) {
                result.append("Le meilleur client est ")
                      .append(rs.getString("prenom")).append(" ")
                      .append(rs.getString("nom")).append(" avec ")
                      .append(rs.getInt("nb_commandes")).append(" commandes.");
            } else {
                result.append("Aucun client trouvé.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            result.append("Erreur lors de la récupération des données.");
        }
        return result.toString();
    }

    private String getWorstDeliveryMan() {
        String query = "SELECT livreur.prenom, livreur.nom, COUNT(*) AS nb_retards " +
                       "FROM livreur " +
                       "JOIN commande ON livreur.id_livreur = commande.id_livreur " +
                       "WHERE commande.bonus_livraison = 1 " +
                       "GROUP BY livreur.id_livreur " +
                       "ORDER BY nb_retards DESC " +
                       "LIMIT 1;";
        StringBuilder result = new StringBuilder();
        try (Connection conn = new DatabaseConnection().connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            if (rs.next()) {
                result.append("Le pire livreur est ")
                      .append(rs.getString("prenom")).append(" ")
                      .append(rs.getString("nom")).append(" avec ")
                      .append(rs.getInt("nb_retards")).append(" commandes retardées.");
            } else {
                result.append("Aucun livreur trouvé.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            result.append("Erreur lors de la récupération des données.");
        }
        return result.toString();
    }

    private String getMostPopularPizza() {
        String query = "SELECT pizza.nom, COUNT(*) AS nb_commandes " +
                       "FROM commande " +
                       "JOIN pizza ON commande.id_pizza = pizza.id_pizza " +
                       "GROUP BY pizza.id_pizza " +
                       "ORDER BY nb_commandes DESC " +
                       "LIMIT 1;";
        StringBuilder result = new StringBuilder();
        try (Connection conn = new DatabaseConnection().connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            if (rs.next()) {
                result.append("La pizza la plus demandée est ")
                      .append(rs.getString("nom")).append(" avec ")
                      .append(rs.getInt("nb_commandes")).append(" commandes.");
            } else {
                result.append("Aucune pizza trouvée.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            result.append("Erreur lors de la récupération des données.");
        }
        return result.toString();
    }

    private String getLeastPopularPizza() {
        String query = "SELECT pizza.nom, COUNT(*) AS nb_commandes " +
                       "FROM commande " +
                       "JOIN pizza ON commande.id_pizza = pizza.id_pizza " +
                       "GROUP BY pizza.id_pizza " +
                       "ORDER BY nb_commandes ASC " +
                       "LIMIT 1;";
        StringBuilder result = new StringBuilder();
        try (Connection conn = new DatabaseConnection().connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            if (rs.next()) {
                result.append("La pizza la moins demandée est ")
                      .append(rs.getString("nom")).append(" avec ")
                      .append(rs.getInt("nb_commandes")).append(" commandes.");
            } else {
                result.append("Aucune pizza trouvée.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            result.append("Erreur lors de la récupération des données.");
        }
        return result.toString();
    }

    private String getFavoriteIngredient() {
        String query = "SELECT ingredient.nom, COUNT(*) AS nb_commandes " +
                       "FROM se_compose " +
                       "JOIN ingredient ON se_compose.id_ingredient = ingredient.id_ingredient " +
                       "GROUP BY ingredient.nom " +
                       "ORDER BY nb_commandes DESC " +
                       "LIMIT 1;";
        StringBuilder result = new StringBuilder();
        try (Connection conn = new DatabaseConnection().connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            if (rs.next()) {
                result.append("L'ingrédient favori est ")
                      .append(rs.getString("nom")).append(" avec ")
                      .append(rs.getInt("nb_commandes")).append(" commandes.");
            } else {
                result.append("Aucun ingrédient trouvé.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            result.append("Erreur lors de la récupération des données.");
        }
        return result.toString();
    }

    private String getUnusedVehicles() {
        String query = "SELECT plaque_imm FROM vehicule WHERE plaque_imm NOT IN (SELECT DISTINCT plaque_imm FROM utilise);";
        StringBuilder result = new StringBuilder();
        try (Connection conn = new DatabaseConnection().connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                result.append(rs.getString("plaque_imm")).append(", ");
            }
            if (result.length() == 0) {
                result.append("Aucun véhicule inutilisé trouvé.");
            } else {
                result.setLength(result.length() - 2); // Remove trailing comma and space
            }
        } catch (SQLException e) {
            e.printStackTrace();
            result.append("Erreur lors de la récupération des données.");
        }
        return "Les véhicules n'ayant jamais servi sont : " + result.toString();
    }

    private String getOrdersPerClient() {
        String query = "SELECT client.prenom, client.nom, COUNT(*) AS nb_commandes " +
                       "FROM commande " +
                       "JOIN client ON commande.id_client = client.id_client " +
                       "GROUP BY client.id_client;";
        StringBuilder result = new StringBuilder();
        try (Connection conn = new DatabaseConnection().connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                result.append(rs.getString("prenom")).append(" ")
                      .append(rs.getString("nom")).append(" : ")
                      .append(rs.getInt("nb_commandes")).append(" commandes<br>");
            }
            if (result.length() == 0) {
                result.append("Aucun client trouvé.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            result.append("Erreur lors de la récupération des données.");
        }
        return result.toString();
    }

    private String getAverageOrders() {
        String query = "SELECT AVG(nb_commandes) AS avg_commandes FROM (SELECT COUNT(*) AS nb_commandes FROM commande GROUP BY id_client) AS commandes_par_client;";
        StringBuilder result = new StringBuilder();
        try (Connection conn = new DatabaseConnection().connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            if (rs.next()) {
                result.append("La moyenne des commandes est de ")
                      .append(rs.getDouble("avg_commandes")).append(" commandes.");
            } else {
                result.append("Aucune donnée trouvée.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            result.append("Erreur lors de la récupération des données.");
        }
        return result.toString();
    }

    private String getClientsAboveAverage() {
        String query = "SELECT client.prenom, client.nom, COUNT(*) AS nb_commandes " +
                       "FROM commande " +
                       "JOIN client ON commande.id_client = client.id_client " +
                       "GROUP BY client.id_client " +
                       "HAVING nb_commandes > (SELECT AVG(nb_commandes) FROM (SELECT COUNT(*) AS nb_commandes FROM commande GROUP BY id_client) AS commandes_par_client);";
        StringBuilder result = new StringBuilder();
        try (Connection conn = new DatabaseConnection().connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                result.append(rs.getString("prenom")).append(" ")
                      .append(rs.getString("nom")).append(" avec ")
                      .append(rs.getInt("nb_commandes")).append(" commandes<br>");
            }
            if (result.length() == 0) {
                result.append("Aucun client trouvé.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            result.append("Erreur lors de la récupération des données.");
        }
        return "Les clients ayant commandé plus que la moyenne sont : <br>" + result.toString();
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new StatsFrame().setVisible(true);
            }
        });
    }
}
