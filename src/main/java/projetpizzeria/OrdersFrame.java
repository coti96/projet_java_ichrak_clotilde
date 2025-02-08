package projetpizzeria;

import projetpizzeria.DataAccessModel.CommandeDAO;
import projetpizzeria.Model.Commande;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.util.List;

public class OrdersFrame extends JFrame {
    private static final long serialVersionUID = 1L;
    private JTable ordersTable;
    private DefaultTableModel tableModel;
    private JLabel revenueLabel;
    private Font italianFont;

    private static final String CANCEL_SYMBOL = "\u274C Annuler";
    private static final String VIEW_SYMBOL = "📄 Fiche Livraison";
    private static final String BONUS_SYMBOL = "🎁";
    private static final String DELIVERY_SYMBOL = "🚚";

    public OrdersFrame() {
        italianFont = new Font("Times New Roman", Font.ITALIC, 18);
        setTitle("Liste des Commandes");
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Agrandir la fenêtre
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Création d'un panel principal avec BorderLayout
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Ajouter des marges

        // Ajout d'un titre
        JLabel titleLabel = new JLabel("Liste des commandes du jour", JLabel.CENTER);
        titleLabel.setFont(italianFont.deriveFont(Font.ITALIC, 30f));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0)); // Espacement
        panel.add(titleLabel, BorderLayout.NORTH);

        // Noms des colonnes
        String[] columnNames = {
            "Numéro", "Montant", "Date et Heure", "Bonus Fidélité", "Bonus Livraison",
            "Solde Suffisant", "Annuler", "Fiche Livraison", "Ajouter Bonus Fidélité", "Ajouter Bonus Livraison"
        };
        tableModel = new DefaultTableModel(columnNames, 0);
        ordersTable = new JTable(tableModel);
        ordersTable.setFont(italianFont.deriveFont(16f));
        ordersTable.setFillsViewportHeight(true); // Remplir le viewport du JScrollPane
        ordersTable.setRowHeight(30); // Augmenter la hauteur des lignes pour une meilleure lisibilité

        // Ajuster la largeur des colonnes
        TableColumn dateColumn = ordersTable.getColumn("Date et Heure");
        dateColumn.setPreferredWidth(200);

        // Configuration des boutons dans le tableau
        ordersTable.getColumn("Annuler").setCellRenderer(new ButtonRenderer());
        ordersTable.getColumn("Annuler").setCellEditor(new ButtonEditor(new JCheckBox()));
        ordersTable.getColumn("Fiche Livraison").setCellRenderer(new ButtonRenderer());
        ordersTable.getColumn("Fiche Livraison").setCellEditor(new ButtonEditor(new JCheckBox()));
        ordersTable.getColumn("Ajouter Bonus Fidélité").setCellRenderer(new ButtonRenderer());
        ordersTable.getColumn("Ajouter Bonus Fidélité").setCellEditor(new ButtonEditor(new JCheckBox()));
        ordersTable.getColumn("Ajouter Bonus Livraison").setCellRenderer(new ButtonRenderer());
        ordersTable.getColumn("Ajouter Bonus Livraison").setCellEditor(new ButtonEditor(new JCheckBox()));

        // Ajout du tableau dans un JScrollPane
        JScrollPane scrollPane = new JScrollPane(ordersTable);
        scrollPane.setPreferredSize(new Dimension(1600, 800));
        panel.add(scrollPane, BorderLayout.CENTER);

        // Ajouter le label du chiffre d'affaires
        revenueLabel = new JLabel("Chiffre d'affaires du jour : " + getDailyRevenue() + " €", JLabel.CENTER);
        revenueLabel.setFont(italianFont.deriveFont(20f));
        revenueLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        panel.add(revenueLabel, BorderLayout.SOUTH);

        // Ajout du bouton Refresh
        JButton refreshButton = new JButton("Refresh");
        refreshButton.setFont(italianFont.deriveFont(18f));
        refreshButton.addActionListener(e -> loadOrdersData());
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(refreshButton);
        panel.add(buttonPanel, BorderLayout.NORTH);

        // Ajout du panel principal à la fenêtre
        add(panel);

        // Charger les données des commandes
        loadOrdersData();
    }

    public void loadOrdersData() {
        CommandeDAO commandeDAO = new CommandeDAO();
        List<Commande> commandes = commandeDAO.getAllCommandes();
        tableModel.setRowCount(0);
        for (Commande commande : commandes) {
            if (commande.isBonusLivraison() || commande.isBonusPizzaGratuite()) {
                commandeDAO.updateCommandeMontantToZero(commande.getIdCommande());
            }
            double montantTotal = (commande.isBonusLivraison() || commande.isBonusPizzaGratuite()) ? 0.0 : commande.getMontantTotal();
            double pizzaPrice = commandeDAO.getPizzaPrice(commande.getIdPizza());
            boolean sufficientBalance = commandeDAO.hasSufficientBalance(
                    commande.getIdClient(), pizzaPrice, commande.isBonusLivraison(), commande.isBonusPizzaGratuite());
            tableModel.addRow(new Object[]{
                commande.getIdCommande(),
                montantTotal,
                commande.getDateCommande(),
                commande.isBonusPizzaGratuite() ? "Oui" : "Non",
                commande.isBonusLivraison() ? "Oui" : "Non",
                sufficientBalance ? "Oui" : "Non",
                CANCEL_SYMBOL,
                VIEW_SYMBOL,
                BONUS_SYMBOL,
                DELIVERY_SYMBOL
            });
        }
        // Mettre à jour le chiffre d'affaires
        revenueLabel.setText("Chiffre d'affaires du jour : " + getDailyRevenue() + " €");
    }

    private double getDailyRevenue() {
        CommandeDAO commandeDAO = new CommandeDAO();
        return commandeDAO.getDailyRevenue();
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new OrdersFrame().setVisible(true);
            }
        });
    }
}
