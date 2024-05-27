package projetpizzeria;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection  {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/pizzeria";
        String user = "root";
        String password = "0000";

        try {
            Connection connection = DriverManager.getConnection(url, user, password);
            if (connection != null) {
                System.out.println("Connecté à la base de données !");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
