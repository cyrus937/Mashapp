enum Langue {
    FRANCAIS, ANGLAIS
}

/**
 * Cette classe représente une personne quelconque caractérisée par son nom, son
 * prenom, sa langue d'expression(par défaut le Francais) et sa ville d'étude.
 * 
 * @author Harold
 *
 */
public class Personne {
    protected String nom;
    protected Langue langue = Langue.FRANCAIS;
    protected String ville = null;

    public static int compteur_id = 0;

    /**
     * Ce constructeur initialise les paramètres ci-dessous
     * 
     * @param nom
     * @param prenom
     * @param ville
     */
    public Personne(String nom, Langue langue) {
	this.nom = nom;
	this.langue = langue;
	compteur_id++;
    }

    public Personne(String nom, String ville) {
	this.nom = nom;
	this.ville = ville;
	compteur_id++;
    }

    /**
     * Ce constructeur initialise les paramètres ci-dessous
     * 
     * @param nom
     * @param prenom
     * @param langue
     * @param ville
     */
    public Personne(String nom, Langue langue, String ville) {
	this(nom, ville);
	this.langue = langue;
	compteur_id++;
    }

    public String getNom() {
	return nom;
    }

    public void setNom(String nom) {
	this.nom = nom;
    }

    public Langue getLangue() {
	return langue;
    }

    public void setLangue(Langue langue) {
	this.langue = langue;
    }

    public String getVille() {
	return ville;
    }

    public void setVille(String ville) {
	this.ville = ville;
    }

    public String toString() {
	return " Nom : " + getNom() + " Langue : " + getLangue() + " Ville : " + getVille();
    }
}
