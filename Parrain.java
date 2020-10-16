import java.util.*;
public class Parrain extends Personne{
	private int id;
	private Filleul potentiel_filleul = null;
	private ArrayList<Filleul> filleul = null;
	
	public  static final int NOMBRE_MAX_FILLEUL = 2;
	

	public Parrain(String nom, Langue langue, String ville) {
		super(nom, langue,ville);
		id = compteur_id;
		filleul = new ArrayList<Filleul>();
	}
	
	public Parrain(String nom, Langue langue,String ville, Filleul potentiel_filleul) {
		super(nom, langue , ville);
		filleul = new ArrayList<Filleul>();
		if(potentiel_filleul != null)
			filleul.add(new Filleul(potentiel_filleul));
		id = compteur_id;
	}
	
	public Parrain(Parrain p) {	
		this(p.nom,p.langue,p.ville,p.potentiel_filleul);
		filleul = new ArrayList<Filleul>();
	}

	public int getId() {
		return id;
	}

	public Filleul getPotentiel_filleul() {
		return potentiel_filleul;
	}
	
	public ArrayList<Filleul> getFilleul() {
		return filleul;
	}

	public void setFilleul(ArrayList<Filleul> filleul) {
		this.filleul = filleul;
	}

	public boolean estEgal(Parrain p) {
		if( p == null)
			return false;
		else
			return p.id == this.id;
	}
	
	public boolean lierFilleul(Filleul f) {
		if(f == null)
			return false;
		else {
			if(filleul.size() >= NOMBRE_MAX_FILLEUL)
				return false;
			else {
				filleul.add(new Filleul(f));		
				return true;
			}
		}

	}

	public boolean estParrain(Filleul f) {
		if( f == null)
			return false;
		else {
			for(Filleul tmp : filleul) {
				if(tmp.estEgal(f))
					return true;
			}
			return false;
		}		
	}

}
