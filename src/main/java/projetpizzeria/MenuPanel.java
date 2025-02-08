package projetpizzeria;

import projetpizzeria.DataAccessModel.MenuDAO;
import projetpizzeria.Model.PizzaMenu;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MenuPanel extends JPanel {

    private static final long serialVersionUID = 1L;
    private Font italianFont;

    public MenuPanel() {
        italianFont = new Font("Times New Roman", Font.ITALIC, 18);
        setLayout(new GridBagLayout());
        setBackground(Color.WHITE);

        JPanel menuContentPanel = new JPanel();
        menuContentPanel.setLayout(new BoxLayout(menuContentPanel, BoxLayout.Y_AXIS));
        menuContentPanel.setBackground(Color.WHITE);

        JLabel menuLabel = new JLabel("Menu");
        menuLabel.setFont(italianFont.deriveFont(Font.BOLD, 40f));
        menuLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        menuContentPanel.add(menuLabel);
        menuContentPanel.add(Box.createVerticalStrut(20)); 

        // Récupération des données du menu
        MenuDAO menuDAO = new MenuDAO();
        List<PizzaMenu> pizzaMenuList = menuDAO.getPizzaMenu();

        // Affichage des données
        for (PizzaMenu pizzaMenu : pizzaMenuList) {
            String pizzaInfo = String.format(
                    "<html><h2>%s</h2><p>Prix: %.2f €</p><p>Ingrédients: %s</p><p>Tailles disponibles:</p>"
                    + "<ul><li>Naine: %.2f €</li><li>Humaine: %.2f €</li><li>Ogresse: %.2f €</li></ul></html>",
                    pizzaMenu.getNom(),
                    pizzaMenu.getPrix(),
                    pizzaMenu.getIngredients(),
                    pizzaMenu.getPrix() * 2 / 3,
                    pizzaMenu.getPrix(),
                    pizzaMenu.getPrix() * 4 / 3
            );
            JLabel pizzaLabel = new JLabel(pizzaInfo.toString());
            pizzaLabel.setFont(italianFont.deriveFont(18f));
            pizzaLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            menuContentPanel.add(pizzaLabel);
            menuContentPanel.add(Box.createVerticalStrut(10)); // Add space between pizzas
        }

        // Centrage du contenu
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(20, 20, 20, 20);
        gbc.anchor = GridBagConstraints.CENTER;

        add(menuContentPanel, gbc);
    }
}
