import java.util.*;

public class ListeFilleul implements Comparator<Filleul> {
	private Set liste;
	
	public ListeFilleul() {
		liste = new TreeSet<Filleul>();
	}
	
	public void  ajouter(Filleul f) {
		liste.add(f);
	}
	
	public Set getListe(){
		return liste;
	}
	
	public void setListe(TreeSet<Filleul> l) {
		liste.clear();
		liste = l;
	}
	public int compare(Filleul f1, Filleul f2) {
		if(f1.getPotentiel_parrain() != null && f2.getPotentiel_parrain() == null)
			return 1;
		else if(f1.getPotentiel_parrain() == null && f2.getPotentiel_parrain() != null)
			return -1;
		else {
			if(f1.getLangue() == Langue.FRANCAIS && f2.getLangue()== Langue.ANGLAIS)
				return -1;
			else if(f1.getLangue() == Langue.ANGLAIS && f2.getLangue()== Langue.FRANCAIS)
				return 1;
			else
				return 0;
		}
	}
}
