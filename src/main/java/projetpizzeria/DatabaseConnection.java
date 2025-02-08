package projetpizzeria;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

	private String url = "jdbc:mysql://localhost:3306/pizzeria";
	private String user = "root";
	private String password = "";

	public Connection connect() {
		 try {
	            Connection connection = DriverManager.getConnection(url, user, password);
	            if (connection != null) {
	                System.out.println("Connecté à la base de données !");
	                return connection;
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return null;
	    }
	}