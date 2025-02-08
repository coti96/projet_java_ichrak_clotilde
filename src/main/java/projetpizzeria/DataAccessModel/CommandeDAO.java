package projetpizzeria.DataAccessModel;

import projetpizzeria.DatabaseConnection;
import projetpizzeria.Model.Commande;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CommandeDAO {
    private DatabaseConnection dbConnection;

    public CommandeDAO() {
        this.dbConnection = new DatabaseConnection();
    }

    public String getLivreurNom(String idLivreur) {
        String sql = "SELECT CONCAT(nom, ' ', prenom) AS nom_complet FROM livreur WHERE id_livreur = ?";
        try (Connection conn = dbConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, idLivreur);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("nom_complet");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "N/A";
    }

    public String getVehiculeType(String idLivreur) {
        String sql = "SELECT type FROM vehicule v JOIN utilise u ON v.plaque_imm = u.plaque_imm WHERE u.id_livreur = ?";
        try (Connection conn = dbConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, idLivreur);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("type");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "N/A";
    }

    public String getClientNom(String idClient) {
        String sql = "SELECT CONCAT(nom, ' ', prenom) AS nom_complet FROM client WHERE id_client = ?";
        try (Connection conn = dbConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, idClient);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("nom_complet");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "N/A";
    }

    public String getPizzaNom(String idPizza) {
        String sql = "SELECT nom FROM pizza WHERE id_pizza = ?";
        try (Connection conn = dbConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, idPizza);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("nom");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "N/A";
    }

   
    public boolean hasSufficientBalance(String clientId, double orderAmount, boolean bonusLivraison, boolean bonusPizzaGratuite) {
        if (bonusLivraison || bonusPizzaGratuite) {
            return true;  // Ne pas facturer si la pizza a un bonus de livraison ou de fidélité
        }
        
        String sql = "SELECT portefeuille FROM client WHERE id_client = ?";
        try (Connection conn = dbConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, clientId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                double balance = rs.getDouble("portefeuille");
                return balance >= orderAmount;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public double getPizzaPrice(String pizzaId) {
        String sql = "SELECT prix_base FROM pizza WHERE id_pizza = ?";
        try (Connection conn = dbConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, pizzaId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getDouble("prix_base");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }

    public List<Commande> getAllCommandes() {
        List<Commande> commandes = new ArrayList<>();
        String sql = "SELECT * FROM commande";
        try (Connection conn = dbConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Commande commande = new Commande();
                commande.setIdCommande(rs.getString("id_commande"));
                commande.setIdClient(rs.getString("id_client"));
                commande.setIdPizza(rs.getString("id_pizza"));
                commande.setMontantTotal(rs.getDouble("montant_total"));
                commande.setDateCommande(rs.getString("date_commande"));
                commande.setBonusLivraison(rs.getBoolean("bonus_livraison"));
                commande.setBonusPizzaGratuite(rs.getBoolean("bonus_pizza_gratuite"));
                commandes.add(commande);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return commandes;
    }

    
    public Commande getCommandeById(String idCommande) {
        Commande commande = null;
        String sql = "SELECT * FROM commande WHERE id_commande = ?";
        try (Connection conn = dbConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, idCommande);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                commande = new Commande();
                commande.setIdCommande(rs.getString("id_commande"));
                commande.setIdClient(rs.getString("id_client"));
                commande.setIdPizza(rs.getString("id_pizza"));
                commande.setMontantTotal(rs.getDouble("montant_total"));
                commande.setDateCommande(rs.getString("date_commande"));
                commande.setBonusLivraison(rs.getBoolean("bonus_livraison"));
                commande.setBonusPizzaGratuite(rs.getBoolean("bonus_pizza_gratuite"));
                commande.setIdLivreur(rs.getString("id_livreur"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return commande;
    }

    public void updateCommandeMontantToZero(String idCommande) {
        String sql = "UPDATE commande SET montant_total = 0 WHERE id_commande = ?";
        try (Connection conn = dbConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, idCommande);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    
    public double getDailyRevenue() {
        double dailyRevenue = 0.0;
        String sql = "SELECT SUM(montant_total) AS total FROM commande WHERE DATE(date_commande) = CURDATE()";
        try (Connection conn = dbConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                dailyRevenue = rs.getDouble("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dailyRevenue;
    }
}
