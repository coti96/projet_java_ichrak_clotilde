package projetpizzeria;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ButtonEditor extends AbstractCellEditor implements TableCellEditor, ActionListener {
	private static final long serialVersionUID = 1L;
	private JButton button;
    private String idCommande;
    private JTable table;

    public ButtonEditor(JCheckBox checkBox) {
        button = new JButton();
        button.setOpaque(true);
        button.addActionListener(this);
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        idCommande = table.getValueAt(row, 0).toString(); 
        button.setText((value == null) ? "Annuler" : value.toString());
        this.table = table;
        return button;
    }

    @Override
    public Object getCellEditorValue() {
        return new String(button.getText());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Supprimer la commande de la base de données
        DatabaseConnection dbConnection = new DatabaseConnection();
        String sql = "DELETE FROM commande WHERE id_commande = ?";
        try (Connection conn = dbConnection.connect(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, idCommande);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        // Rafraîchir la table
        ((DefaultTableModel) table.getModel()).removeRow(table.getSelectedRow());
        fireEditingStopped();
    }
}
