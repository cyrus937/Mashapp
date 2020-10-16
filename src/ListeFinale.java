import java.util.*;

public class ListeFinale {
	private List<Filleul> liste_filleul;
	private List<Parrain> liste_parrain;
	
	Comparator<Filleul> comparateur_filleul = new Comparator<Filleul>() {
		public int compare(Filleul f1, Filleul f2) {
			if(f1.getPotentiel_parrain() != null && f2.getPotentiel_parrain() == null)
				return -1;
			else if(f1.getPotentiel_parrain() == null && f2.getPotentiel_parrain() != null)
				return 1;
			else {
				if(f1.getLangue() == Langue.FRANCAIS && f2.getLangue()== Langue.ANGLAIS)
					return 1;
				else if(f1.getLangue() == Langue.ANGLAIS && f2.getLangue()== Langue.FRANCAIS)
					return -1;
				else
					return 0;
			}
		}
	};
	
	Comparator<Parrain> comparateur_parrain = new Comparator<Parrain>() {
		public int compare(Parrain p1, Parrain p2) {
			
			if(p1.getFilleul() != null && p2.getFilleul() != null) {
				if(p1.getFilleul().size() < p2.getFilleul().size())
					return -1;
				else if(p1.getFilleul().size() > p2.getFilleul().size())
					return 1;
				else {
					if(p1.getPotentiel_filleul() != null && p2.getPotentiel_filleul() == null)
						return 1;
					else if(p1.getPotentiel_filleul() == null && p2.getPotentiel_filleul() != null)
						return -1;
					else
						return 0;
				}
			}
			else if(p1.getFilleul() == null && p2.getFilleul() != null)
				return -1;
			else if(p1.getFilleul() != null && p2.getFilleul() == null)
				return 1;
			else
				return 0;
		}
	};
	public ListeFinale(ArrayList<Filleul> f,ArrayList<Parrain> p) {
		liste_filleul = f;
		liste_parrain = p;
	}
	
	public ListeFinale() {
		liste_filleul = new ArrayList<Filleul>();
		liste_parrain = new ArrayList<Parrain>();
	}
	public void ajouterFilleul(Filleul f) {
		liste_filleul.add(f);
	}
	
	public void ajouterParrain(Parrain p) {
		liste_parrain.add(p);
	}
	
	public List<Filleul> getListeFilleul(){
		return liste_filleul;
	}
	
	public List<Parrain> getListeParrain(){
		return liste_parrain;
	}
	
	public void trier() {
		Collections.sort(liste_filleul,comparateur_filleul);
		Collections.sort(liste_parrain,comparateur_parrain);
	}
	
	public void match() {
		trier();
		int  i = 0,j = 0, pastrouve = 0;
		boolean trouve = false;
		
		while(i<liste_filleul.size() && liste_filleul.get(i).getPotentiel_parrain()!=null) {
			Filleul f = liste_filleul.get(i);
			Parrain p = liste_filleul.get(i).getPotentiel_parrain();
			if(p.lierFilleul(f)) {
				f.lier(p);
			}
			else {
				while ( j < liste_parrain.size() && !trouve) {
					if(liste_parrain.get(j).lierFilleul(f)) {
						f.lier(liste_parrain.get(j));
						trouve = true;
					}
						j++;
				}
			}
			i++;
			trouve = false;
		}
		
		trier();
		j = 0;
		i = i - pastrouve;
		pastrouve = 0;
		
		trouve = false;
		while( j < liste_parrain.size() && liste_parrain.get(j).getPotentiel_filleul() != null) {
			Parrain p = liste_parrain.get(j);
			Filleul f = p.getPotentiel_filleul();
			if(!f.aUnParrain()) {
				p.lierFilleul(f);
				f.lier(p);
			}
			else {
				while ( i < liste_filleul.size() && !trouve) {
					if(!liste_filleul.get(i).aUnParrain()) {
						liste_filleul.get(i).lier(p);
						p.lierFilleul(liste_filleul.get(i));
						trouve = true;
					}
						i++;
				}
			}
			j++;
			trouve = false;
		}
	
		trier();
		j = 0;
		i = i - pastrouve;
		pastrouve = 0;
		
		trouve = false;
		while( i <liste_filleul.size() && liste_filleul.get(i).getLangue()==Langue.ANGLAIS) {
			Filleul f = liste_filleul.get(i);
			while( j < liste_parrain.size() && !trouve) {
				if(liste_parrain.get(j).getLangue() == Langue.ANGLAIS && liste_parrain.get(j).lierFilleul(f)) {
					f.lier(liste_parrain.get(j));
					trouve = true;
				}	
				j++;
			}
			if(j == liste_parrain.size())
				pastrouve ++;
			trouve = false;
			i++;
		}
		
		trier();
		j = 0;
		i = i - pastrouve;
		pastrouve = 0;
		
		trouve = false;
		while(i<liste_filleul.size()) {
			Filleul f = liste_filleul.get(i);
			while( j <liste_parrain.size() && !trouve) {
				if(f.getVille().equals(liste_parrain.get(j).getVille()) && liste_parrain.get(j).lierFilleul(f)) {
					f.lier(liste_parrain.get(j));
					trouve = true;
				}
				j++;
			}
			if(j == liste_parrain.size())
				pastrouve ++;
			trier();
			j = 0;
			i++;
			trouve = false;
		}
		
		
		trier();
		j = 0;
		i = i - pastrouve;
		pastrouve = 0;
		
		while(i<liste_filleul.size()) {
			Filleul f = liste_filleul.get(i);
			while( j <liste_parrain.size() && !trouve) {
				if(liste_parrain.get(j).lierFilleul(f)) {
					f.lier(liste_parrain.get(j));
					trouve = true;
				}
				j++;
			}
			trier();
			j = 0;
			i++;
			trouve = false;
		}
		
	}
}
