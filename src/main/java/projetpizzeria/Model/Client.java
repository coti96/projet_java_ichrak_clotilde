package projetpizzeria.Model;

public class Client {
    private String nom;
    private String prenom;
    private String numeroTelephone;
    private int nombreCommandes;

    // Getters et setters
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNumeroTelephone() {
        return numeroTelephone;
    }

    public void setNumeroTelephone(String numeroTelephone) {
        this.numeroTelephone = numeroTelephone;
    }

    public int getNombreCommandes() {
        return nombreCommandes;
    }

    public void setNombreCommandes(int nombreCommandes) {
        this.nombreCommandes = nombreCommandes;
    }
}
