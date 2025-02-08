package projetpizzeria.DataAccessModel;

import projetpizzeria.DatabaseConnection;
import projetpizzeria.Model.PizzaMenu;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MenuDAO {
    private DatabaseConnection dbConnection;

    public MenuDAO() {
        this.dbConnection = new DatabaseConnection();
    }

    public List<PizzaMenu> getPizzaMenu() {
        List<PizzaMenu> pizzaMenuList = new ArrayList<>();
        String sql = "SELECT pizza.nom AS pizza_nom, pizza.prix_base, GROUP_CONCAT(ingredient.nom SEPARATOR ', ') AS ingredients " +
                     "FROM pizza " +
                     "JOIN se_compose ON pizza.id_pizza = se_compose.id_pizza " +
                     "JOIN ingredient ON se_compose.id_ingredient = ingredient.id_ingredient " +
                     "GROUP BY pizza.id_pizza";
        try (Connection conn = dbConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                String pizzaNom = rs.getString("pizza_nom");
                double prixBase = rs.getDouble("prix_base");
                String ingredients = rs.getString("ingredients");
                pizzaMenuList.add(new PizzaMenu(pizzaNom, prixBase, ingredients));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pizzaMenuList;
    }
}
