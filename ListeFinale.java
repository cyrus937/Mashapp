import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ListeFinale {
    private ArrayList<Filleul> liste_filleul;
    private ArrayList<Parrain> liste_parrain;

    Comparator<Filleul> comparateur_filleul = new Comparator<Filleul>() {
	public int compare(Filleul f1, Filleul f2) {
	    if (f1.getPotentiel_parrain() != null && f2.getPotentiel_parrain() == null)
		return -1;
	    else if (f1.getPotentiel_parrain() == null && f2.getPotentiel_parrain() != null)
		return 1;
	    else {
		if (f1.getLangue() == Langue.FRANCAIS && f2.getLangue() == Langue.ANGLAIS)
		    return 1;
		else if (f1.getLangue() == Langue.ANGLAIS && f2.getLangue() == Langue.FRANCAIS)
		    return -1;
		else
		    return 0;
	    }
	}
    };

    Comparator<Parrain> comparateur_parrain = new Comparator<Parrain>() {
	public int compare(Parrain p1, Parrain p2) {

	    if (p1.getFilleul() != null && p2.getFilleul() != null) {
		if (p1.getFilleul().size() < p2.getFilleul().size())
		    return -1;
		else if (p1.getFilleul().size() > p2.getFilleul().size())
		    return 1;
		else {
		    if (p1.getPotentiel_filleul() != null && p2.getPotentiel_filleul() == null)
			return 1;
		    else if (p1.getPotentiel_filleul() == null && p2.getPotentiel_filleul() != null)
			return -1;
		    else
			return 0;
		}
	    } else if (p1.getFilleul() == null && p2.getFilleul() != null)
		return -1;
	    else if (p1.getFilleul() != null && p2.getFilleul() == null)
		return 1;
	    else
		return 0;
	}
    };
    Comparator<Filleul> comparateur_final = new Comparator<Filleul>() {
	public int compare(Filleul f1, Filleul f2) {
	    return f1.getNom().compareTo(f2.getNom());
	}
    };

    public ListeFinale(ArrayList<Filleul> f, ArrayList<Parrain> p) {
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

    public ArrayList<Filleul> getListeFilleul() {
	return liste_filleul;
    }

    public void initListeParrain(ArrayList<String> liste) {
	if (liste == null)
	    System.out.println("Impossible d'initialiser la liste");
	else {
	    for (int i = 1; i < liste.size(); i++) {
		String data[] = liste.get(i).split(";");
		Langue l;
		if (Integer.parseInt(data[1]) == 0)
		    l = Langue.FRANCAIS;
		else
		    l = Langue.ANGLAIS;
		liste_parrain.add(new Parrain(data[0], l, data[2]));
	    }
	}
    }

    public void initListeFilleul(ArrayList<String> liste) {
	if (liste == null)
	    System.out.println("Impossible d'initialiser la liste");
	else {
	    for (int i = 1; i < liste.size(); i++) {
		String data[] = liste.get(i).split(";");
		Langue l;
		if (Integer.parseInt(data[1]) == 0)
		    l = Langue.FRANCAIS;
		else
		    l = Langue.ANGLAIS;
		boolean trouve = false;
		Parrain p = null;
		if (data.length >= 4) {
		    String nom = data[3];
		    int j = 0;
		    while (j < liste_parrain.size() && !trouve) {
			if (nom.equals(liste_parrain.get(j).getNom())) {
			    trouve = true;
			    p = liste_parrain.get(j);
			}
			j++;
		    }
		}
		if (trouve == true)
		    liste_filleul.add(new Filleul(data[0], l, data[2], p));
		else
		    liste_filleul.add(new Filleul(data[0], l, data[2]));
	    }
	}
    }

    public List<Parrain> getListeParrain() {
	return liste_parrain;
    }

    public void trier() {
	Collections.sort(liste_filleul, comparateur_filleul);
	Collections.sort(liste_parrain, comparateur_parrain);
    }

    public void match() {
	trier();
	int i = 0, j = 0, pastrouve = 0;
	boolean trouve = false;

	while (i < liste_filleul.size() && liste_filleul.get(i).getPotentiel_parrain() != null) {
	    Filleul f = liste_filleul.get(i);
	    Parrain p = liste_filleul.get(i).getPotentiel_parrain();
	    if (p.lierFilleul(f)) {
		f.lier(p);
	    } else {
		while (j < liste_parrain.size() && !trouve) {
		    if (liste_parrain.get(j).lierFilleul(f)) {
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
	while (j < liste_parrain.size() && liste_parrain.get(j).getPotentiel_filleul() != null) {
	    Parrain p = liste_parrain.get(j);
	    Filleul f = p.getPotentiel_filleul();
	    if (!f.aUnParrain()) {
		p.lierFilleul(f);
		f.lier(p);
	    } else {
		while (i < liste_filleul.size() && !trouve) {
		    if (!liste_filleul.get(i).aUnParrain()) {
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
	while (i < liste_filleul.size() && liste_filleul.get(i).getLangue() == Langue.ANGLAIS) {
	    Filleul f = liste_filleul.get(i);
	    while (j < liste_parrain.size() && !trouve) {
		if (liste_parrain.get(j).getLangue() == Langue.ANGLAIS && liste_parrain.get(j).lierFilleul(f)) {
		    f.lier(liste_parrain.get(j));
		    trouve = true;
		}
		j++;
	    }
	    if (j == liste_parrain.size())
		pastrouve++;
	    trouve = false;
	    i++;
	}

	trier();
	j = 0;
	i = i - pastrouve;
	pastrouve = 0;

	trouve = false;
	while (i < liste_filleul.size()) {
	    Filleul f = liste_filleul.get(i);
	    while (j < liste_parrain.size() && !trouve) {
		if (f.getVille().equals(liste_parrain.get(j).getVille()) && liste_parrain.get(j).lierFilleul(f)) {
		    f.lier(liste_parrain.get(j));
		    trouve = true;
		}
		j++;
	    }
	    if (j == liste_parrain.size())
		pastrouve++;
	    trier();
	    j = 0;
	    i++;
	    trouve = false;
	}

	trier();
	j = 0;
	i = i - pastrouve;
	pastrouve = 0;

	while (i < liste_filleul.size()) {
	    Filleul f = liste_filleul.get(i);
	    while (j < liste_parrain.size() && !trouve) {
		if (liste_parrain.get(j).lierFilleul(f)) {
		    f.lier(liste_parrain.get(j));
		    trouve = true;
		}
		j++;
	    }
	    trier();
	    j = 0;
	    i++;
	    trouve = false;
	    Collections.sort(liste_filleul, comparateur_final);
	}

    }
}
