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
    public List<Commande> getAllCommandes() {
        List<Commande> commandes = new ArrayList<>();
        String sql = "SELECT * FROM commande";
        
       
             DatabaseConnection dbConnection = new DatabaseConnection();
             try (Connection conn = dbConnection.connect();     
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Commande commande = new Commande();
                commande.setIdCommande(rs.getString("id_commande"));
                commande.setMontantTotal(rs.getDouble("montant_total"));
                commande.setDateCommande(rs.getString("date_commande"));
                commande.setBonusPizzaGratuite(rs.getBoolean("bonus_pizza_gratuite"));
                commande.setBonusLivraison(rs.getBoolean("bonus_livraison"));
                commande.setIdLivreur(rs.getString("id_livreur"));
                commande.setIdClient(rs.getString("id_client"));
                commande.setIdPizza(rs.getString("id_pizza"));

                commandes.add(commande);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return commandes;
    }
}
