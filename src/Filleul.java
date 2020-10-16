
public class Filleul extends Personne{
	private Parrain potentiel_parrain = null;
	private int id;
	private Parrain parrain = null;
	
	public Filleul(String nom, Langue langue) {
		super(nom,langue);
		id = compteur_id;
	}
	
	public Filleul(String nom, Langue langue, String ville) {
		super(nom, langue, ville);
		id = compteur_id;
	}
	
	public Filleul(String nom,Langue langue,String ville, Parrain potentiel_parrain) {
		super(nom,langue , ville);
		if(potentiel_parrain!= null)
			this.potentiel_parrain= new Parrain(potentiel_parrain);
		else
			this.potentiel_parrain = null;
		id = compteur_id;
	}
	
	public Filleul(Filleul p) {
		this(p.nom,p.langue,p.ville,p.potentiel_parrain);
		id = compteur_id;
	}

	public Parrain getPotentiel_parrain() {
		return potentiel_parrain;
	}

	public void setPotentiel_parrain(Parrain potentiel_parrain) {
		this.potentiel_parrain = potentiel_parrain;
	}
	
	public Parrain getParrain() {
		return parrain;
	}
	public int getId() {
		return id;
	}
	
	public boolean estEgal(Filleul f) {
		if( f == null)
			return false;
		else
			return f.id == this.id;
	}
	
	public boolean estFilleul(Parrain p) {
		if( p == null)
			return false;
		else
			return parrain.estEgal(p);
	}
	
	public void lier(Parrain p) {
		parrain = p;
	}
	
	public boolean aUnParrain() {
		if(parrain == null)
			return false;
		else
			return true;
	}
}
