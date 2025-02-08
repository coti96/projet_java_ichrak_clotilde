package projetpizzeria;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import projetpizzeria.DataAccessModel.CommandeDAO;
import projetpizzeria.Model.Commande;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ButtonEditor extends DefaultCellEditor implements ActionListener {
    private static final long serialVersionUID = 1L;
    private JButton button;
    private String idCommande;
    private JTable table;
    private boolean isCancelAction;
    private boolean isAddFidelityBonusAction;
    private boolean isAddDeliveryBonusAction;

    public ButtonEditor(JCheckBox checkBox) {
        super(checkBox);
        button = new JButton();
        button.setOpaque(true);
        button.addActionListener(this);
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        idCommande = table.getValueAt(row, 0).toString();
        button.setText((value == null) ? "" : value.toString());
        isCancelAction = "❌ Annuler".equals(value.toString());  
        isAddFidelityBonusAction = "🎁".equals(value.toString());
        isAddDeliveryBonusAction = "🚚".equals(value.toString());
        this.table = table;
        return button;
    }

    @Override
    public Object getCellEditorValue() {
        return button.getText();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (isCancelAction) {
            // Suppression de la commande
            DatabaseConnection dbConnection = new DatabaseConnection();
            String sql = "DELETE FROM commande WHERE id_commande = ?";
            try (Connection conn = dbConnection.connect();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, idCommande);
                stmt.executeUpdate();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

            // Mise à jour du tableau
            ((DefaultTableModel) table.getModel()).removeRow(table.getSelectedRow());
            fireEditingStopped();
        } else if (isAddFidelityBonusAction) {
            // Ajouter bonus fidélité
            DatabaseConnection dbConnection = new DatabaseConnection();
            String sql = "UPDATE commande SET bonus_pizza_gratuite = 1 WHERE id_commande = ?";
            try (Connection conn = dbConnection.connect();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, idCommande);
                stmt.executeUpdate();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

            // Mise à jour du montant de la commande
            CommandeDAO commandeDAO = new CommandeDAO();
            commandeDAO.updateCommandeMontantToZero(idCommande);
            fireEditingStopped();
            loadOrdersData();
        } else if (isAddDeliveryBonusAction) {
            // Ajouter bonus livraison
            DatabaseConnection dbConnection = new DatabaseConnection();
            String sql = "UPDATE commande SET bonus_livraison = 1 WHERE id_commande = ?";
            try (Connection conn = dbConnection.connect();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, idCommande);
                stmt.executeUpdate();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

            // Mise à jour du montant de la commande
            CommandeDAO commandeDAO = new CommandeDAO();
            commandeDAO.updateCommandeMontantToZero(idCommande);
            fireEditingStopped();
            loadOrdersData();
        } else {
            // Voir les détails de la commande
            CommandeDAO commandeDAO = new CommandeDAO();
            Commande commande = commandeDAO.getCommandeById(idCommande);
            JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(table), "Détails de la Commande", true);
            dialog.getContentPane().add(new OrderDetailsPanel(commande));
            dialog.pack();
            dialog.setLocationRelativeTo(null);
            dialog.setVisible(true);
            fireEditingStopped();
        }
    }

    private void loadOrdersData() {
        OrdersFrame ordersFrame = (OrdersFrame) SwingUtilities.getWindowAncestor(table);
        if (ordersFrame != null) {
            ordersFrame.loadOrdersData();
        }
    }
}
