package projetpizzeria.DataAccessModel;

import projetpizzeria.DatabaseConnection;
import projetpizzeria.Model.Client;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClientDAO {
    private DatabaseConnection dbConnection;

    public ClientDAO() {
        this.dbConnection = new DatabaseConnection();
    }

    public List<Client> getAllClients() {
        List<Client> clients = new ArrayList<>();
        String sql = "SELECT nom, prenom, telephone, COUNT(commande.id_client) AS nombre_commandes " +
                     "FROM client " +
                     "LEFT JOIN commande ON client.id_client = commande.id_client " +
                     "GROUP BY client.id_client";

        try (Connection conn = dbConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Client client = new Client();
                client.setNom(rs.getString("nom"));
                client.setPrenom(rs.getString("prenom"));
                client.setNumeroTelephone(rs.getString("telephone"));
                client.setNombreCommandes(rs.getInt("nombre_commandes"));
                clients.add(client);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clients;
    }
}
