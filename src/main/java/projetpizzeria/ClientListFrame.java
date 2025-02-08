package projetpizzeria;

import projetpizzeria.DataAccessModel.ClientDAO;
import projetpizzeria.Model.Client;
import java.io.IOException;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.io.File;

public class ClientListFrame extends JFrame {
    private static final long serialVersionUID = 1L;
    private Font italianFont;

    public ClientListFrame() {
        initComponents();
    }

   

    private void initComponents() {
    	italianFont = new Font("Times New Roman", Font.ITALIC, 18); 
        setTitle("Liste des Clients");
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // Panel pour le titre
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.WHITE);

      
        // Titre de la fenêtre
        JLabel titleLabel = new JLabel("Liste des clients", JLabel.CENTER);
        titleLabel.setFont(italianFont.deriveFont(Font.ITALIC, 30f));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        topPanel.add(titleLabel, BorderLayout.CENTER);

        add(topPanel, BorderLayout.NORTH);

        // Table pour afficher la liste des clients
        String[] columnNames = {"Nom", "Prénom", "Numéro de Téléphone", "Nombre de Commandes"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        JTable clientsTable = new JTable(tableModel);
        clientsTable.setFont(italianFont.deriveFont(16f));
        clientsTable.setFillsViewportHeight(true);
        clientsTable.setRowHeight(30);

        JScrollPane scrollPane = new JScrollPane(clientsTable);
        add(scrollPane, BorderLayout.CENTER);

        // Charger les données des clients
        loadClientsData(tableModel);
    }

    private void loadClientsData(DefaultTableModel tableModel) {
        ClientDAO clientDAO = new ClientDAO();
        List<Client> clients = clientDAO.getAllClients();
        for (Client client : clients) {
            tableModel.addRow(new Object[]{
                client.getNom(),
                client.getPrenom(),
                client.getNumeroTelephone(),
                client.getNombreCommandes()
            });
        }
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                ClientListFrame frame = new ClientListFrame();
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(800, 600);
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }
}
