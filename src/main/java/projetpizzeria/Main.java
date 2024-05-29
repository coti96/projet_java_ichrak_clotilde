package projetpizzeria;


import java.sql.Connection;

public class Main {
    public static void main(String[] args) {
        DatabaseConnection dbConnection = new DatabaseConnection();
        Connection connection = dbConnection.connect();

        if (connection != null) {
        	 new PageAcceuil().setVisible(true);
            System.out.println("Application Pizzeria démarrée avec succès !");
        } else {
            System.out.println("Échec de la connexion à la base de données.");
        }
    }
}