package projetpizzeria;

import projetpizzeria.DataAccessModel.CommandeDAO;
import projetpizzeria.Model.Commande;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class OrdersFrame extends JFrame {
    private static final long serialVersionUID = 1L;

    public OrdersFrame() {
        setTitle("Liste des Commandes");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());

        CommandeDAO commandeDAO = new CommandeDAO();
        List<Commande> commandes = commandeDAO.getAllCommandes();

        String[] columnNames = {"Numéro Commande", "Montant Total", "Date Commande", "Pizza Gratuite", "Bonus Livraison", "Numero Livreur", "Numero Client", "Numero Pizza", "Annuler"};
        Object[][] data = new Object[commandes.size()][9]; 

        for (int i = 0; i < commandes.size(); i++) {
            Commande commande = commandes.get(i);
            data[i][0] = commande.getIdCommande();
            data[i][1] = commande.getMontantTotal();
            data[i][2] = commande.getDateCommande();
            data[i][3] = commande.isBonusPizzaGratuite();
            data[i][4] = commande.isBonusLivraison();
            data[i][5] = commande.getIdLivreur();
            data[i][6] = commande.getIdClient();
            data[i][7] = commande.getIdPizza();
            data[i][8] = "Annuler"; 
        }

        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        JTable table = new JTable(model);

        
        table.getColumn("Annuler").setCellRenderer(new ButtonRenderer());
        table.getColumn("Annuler").setCellEditor(new ButtonEditor(new JCheckBox()));

        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        add(panel);
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new OrdersFrame().setVisible(true);
            }
        });
    }
}
