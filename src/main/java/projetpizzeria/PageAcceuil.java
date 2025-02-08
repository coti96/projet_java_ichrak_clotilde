package projetpizzeria;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class PageAcceuil extends JFrame {

    private static final long serialVersionUID = 1L;
    private JTabbedPane tabbedPane;
    private Font italianFont;

    public PageAcceuil() {
        
        initComponents();
    }

   

    private void initComponents() {
    	 italianFont = new Font("Times New Roman", Font.ITALIC, 18);
        setTitle("Bella Pizza - Page d'Accueil");
        setSize(1300, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.WHITE);

        tabbedPane = new JTabbedPane();
        tabbedPane.setFont(italianFont);
        tabbedPane.setOpaque(true);

        // Création des composants
        JPanel homePanel = new JPanel(new BorderLayout());
        homePanel.setBackground(Color.WHITE);

        // Panneau pour centrer le titre avec un espace au-dessus
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        titlePanel.setBackground(Color.WHITE);
        titlePanel.setBorder(BorderFactory.createEmptyBorder(100, 0, 0, 0)); 

        JLabel welcomeLabel = new JLabel("Bienvenue chez Bella Pizza", JLabel.CENTER);
        welcomeLabel.setFont(italianFont.deriveFont(Font.BOLD, 60f));
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT); 
        
        JLabel subtitleLabel = new JLabel("Pizza Napolitana depuis 1985", JLabel.CENTER);
        subtitleLabel.setFont(italianFont.deriveFont(Font.ITALIC, 30f));
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        titlePanel.add(welcomeLabel);
        titlePanel.add(Box.createVerticalStrut(20));
        titlePanel.add(subtitleLabel);
        titlePanel.add(Box.createVerticalStrut(20)); 

        homePanel.add(titlePanel, BorderLayout.NORTH);

        // Ajout d'une image centrale
        JLabel imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(JLabel.CENTER);
        imageLabel.setIcon(new ImageIcon("src/main/java/projetpizzeria/image/logo.png"));
        homePanel.add(imageLabel, BorderLayout.CENTER);

        // Panneau des commandes
        OrdersFrame ordersFrame = new OrdersFrame();
        JPanel ordersPanel = new JPanel(new BorderLayout());
        ordersPanel.setBackground(Color.WHITE);
        ordersPanel.add(ordersFrame.getContentPane(), BorderLayout.CENTER);

        // Panneau des statistiques
        StatsFrame statsFrame = new StatsFrame();
        JPanel statsPanel = new JPanel(new BorderLayout());
        statsPanel.setBackground(Color.WHITE);
        statsPanel.add(statsFrame.getContentPane(), BorderLayout.CENTER);

        // Panneau du menu
        MenuPanel menuPanel = new MenuPanel();
        JPanel menuPanelContainer = new JPanel(new BorderLayout());
        menuPanelContainer.setBackground(Color.WHITE);
        menuPanelContainer.add(new JScrollPane(menuPanel), BorderLayout.CENTER);

        // Panneau de la liste des clients
        ClientListFrame clientListFrame = new ClientListFrame();
        JPanel clientPanel = new JPanel(new BorderLayout());
        clientPanel.setBackground(Color.WHITE);
        clientPanel.add(clientListFrame.getContentPane(), BorderLayout.CENTER);

        // Ajout des panneaux au tabbedPane
        tabbedPane.addTab("Accueil", homePanel);
        tabbedPane.addTab("Gérer les Commandes", ordersPanel);
        tabbedPane.addTab("Voir les Statistiques", statsPanel);
        tabbedPane.addTab("Voir le Menu", menuPanelContainer);
        tabbedPane.addTab("Liste des Clients", clientPanel);

        // Ajout du tabbedPane à la fenêtre
        add(tabbedPane, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        // Exécution de la fenêtre d'accueil
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new PageAcceuil().setVisible(true);
            }
        });
    }
}
