package projetpizzeria.Model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Commande {
    private String idCommande;
    private double montantTotal;
    private String dateCommande;
    private boolean bonusPizzaGratuite;
    private boolean bonusLivraison;
    private String idLivreur;
    private String idClient;
    private String idPizza;

    // Getter pour idCommande
    public String getIdCommande() {
        return idCommande;
    }

    // Setter pour idCommande
    public void setIdCommande(String idCommande) {
        this.idCommande = idCommande;
    }

    // Getter pour montantTotal
    public double getMontantTotal() {
        return montantTotal;
    }

    // Setter pour montantTotal
    public void setMontantTotal(double montantTotal) {
        this.montantTotal = montantTotal;
    }

    // Getter pour dateCommande
    public String getDateCommande() {
        return dateCommande;
    }

    // Setter pour dateCommande
    public void setDateCommande(String dateCommande) {
        this.dateCommande = convertToFrenchDate(dateCommande);
    }

    // Getter pour bonusPizzaGratuite
    public boolean isBonusPizzaGratuite() {
        return bonusPizzaGratuite;
    }

    // Setter pour bonusPizzaGratuite
    public void setBonusPizzaGratuite(boolean bonusPizzaGratuite) {
        this.bonusPizzaGratuite = bonusPizzaGratuite;
    }

    // Getter pour bonusLivraison
    public boolean isBonusLivraison() {
        return bonusLivraison;
    }

    // Setter pour bonusLivraison
    public void setBonusLivraison(boolean bonusLivraison) {
        this.bonusLivraison = bonusLivraison;
    }

    // Getter pour idLivreur
    public String getIdLivreur() {
        return idLivreur;
    }

    // Setter pour idLivreur
    public void setIdLivreur(String idLivreur) {
        this.idLivreur = idLivreur;
    }

    // Getter pour idClient
    public String getIdClient() {
        return idClient;
    }

    // Setter pour idClient
    public void setIdClient(String idClient) {
        this.idClient = idClient;
    }

    // Getter pour idPizza
    public String getIdPizza() {
        return idPizza;
    }

    // Setter pour idPizza
    public void setIdPizza(String idPizza) {
        this.idPizza = idPizza;
    }

    private String convertToFrenchDate(String dateCommande) {
        SimpleDateFormat englishFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.ENGLISH);
        SimpleDateFormat frenchFormat = new SimpleDateFormat("dd MMMM yyyy HH:mm", Locale.FRENCH);
        try {
            Date date = englishFormat.parse(dateCommande);
            return frenchFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return dateCommande; 
        }
    }
}
