import java.util.*;

public class ListeParrain implements Comparator<Parrain>{
	private TreeSet<Parrain> liste;
	
	public ListeParrain() {
		liste= new TreeSet<Parrain>();
	}
	
	public void  ajouter(Parrain p) {
		liste.add(p);
	}
	
	public TreeSet<Parrain>  getListe(){
		return liste;
	}
	
	public void setListe(TreeSet<Parrain> l) {
		liste.clear();
		liste = l;
	}
	public int compare(Parrain p1, Parrain p2) {
		if(p1.getFilleul().size() > p2.getFilleul().size())
			return 1;
		else if(p1.getFilleul().size() < p2.getFilleul().size())
			return -1;
		else
			return 0;
	}
}
