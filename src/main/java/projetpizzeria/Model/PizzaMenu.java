package projetpizzeria.Model;

public class PizzaMenu {
    private String nom;
    private double prix;
    private String ingredients;

    public PizzaMenu(String nom, double prix, String ingredients) {
        this.nom = nom;
        this.prix = prix;
        this.ingredients = ingredients;
    }

    public String getNom() {
        return nom;
    }

    public double getPrix() {
        return prix;
    }

    public String getIngredients() {
        return ingredients;
    }
}
