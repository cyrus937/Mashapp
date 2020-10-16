import java.nio.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.io.FileOutputStream;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import java.io.FileInputStream;

public class Test {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader("src/Filleul(version1).csv"));
		BufferedReader br2 = new BufferedReader(new FileReader("src/liste_parrain(version1).csv"));
		
		ListeFinale liste_finale = new ListeFinale();
		
		ArrayList<String> ligne_filleul = new ArrayList<String>();
		ArrayList<String> ligne_parrain = new ArrayList<String>();
		//Lecture
		for(String str = br.readLine(); str != null; str = br.readLine())
			ligne_filleul.add(str);
		for(String  str = br2.readLine(); str != null; str = br2.readLine())
			ligne_parrain.add(str);
		br.close();
		br2.close();
		
		for(int i =1 ; i< ligne_parrain.size(); i++) {
			String data[] = ligne_parrain.get(i).split(";");
			Langue l;
			if(Integer.parseInt(data[1])== 0)
				l = Langue.FRANCAIS;
			else
				l = Langue.ANGLAIS;
			liste_finale.ajouterParrain(new Parrain(data[0],l,data[2]));
		}
		
		for(int i =1 ; i< ligne_filleul.size(); i++) {
			String data[] = ligne_filleul.get(i).split(";");
			Langue l;
			if(Integer.parseInt(data[1])== 0)
				l = Langue.FRANCAIS;
			else 
				l = Langue.ANGLAIS;
			boolean trouve = false;
			Parrain p = null;
			if(data.length >= 4) {
				String  nom = data[3];
				int j = 0;
				
				while( j < liste_finale.getListeParrain().size() && !trouve) {
					if(nom.equals(liste_finale.getListeParrain().get(j).getNom())) {
						trouve = true;
						p = liste_finale.getListeParrain().get(j);
					}
					j++;
				}
			}
			if( trouve == true)
				liste_finale.ajouterFilleul(new Filleul(data[0],l,data[2],p));
			else	
				liste_finale.ajouterFilleul(new Filleul(data[0],l,data[2]));
		}
		
		liste_finale.match();

		Comparator<Filleul> comparateur_final= new Comparator<Filleul>() {
			public int compare(Filleul f1, Filleul f2) {
				return f1.getNom().compareTo(f2.getNom());
			}
		};
		Collections.sort(liste_finale.getListeFilleul(), comparateur_final);
		
		HSSFWorkbook wb = new HSSFWorkbook();
		FileOutputStream fileOut;
		HSSFSheet sheet = wb.createSheet("Liste Finale");
		
		HSSFCellStyle cellStyle = null, cellStyle1 = null;
		HSSFFont fonte = wb.createFont(), fonte1 = wb.createFont();
		
		cellStyle = wb.createCellStyle();
		cellStyle1 = wb.createCellStyle();
		
		fonte.setFontName("Calibri");
		fonte.setFontHeightInPoints((short)12);
		
		fonte1.setFontName("Calibri");
		fonte1.setFontHeightInPoints((short)12);
		fonte1.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		
		cellStyle.setFont(fonte);
		cellStyle1.setFont(fonte1);
	
		
		HSSFRow row = sheet.createRow(0);
		HSSFCell cell = row.createCell((short)0,HSSFCell.CELL_TYPE_STRING);
		cell.setCellValue("Nom et Prenoms");
		cell.setCellStyle(cellStyle1);
		
		cell = row.createCell((short)1,HSSFCell.CELL_TYPE_STRING);
		cell.setCellValue("Langue");
		cell.setCellStyle(cellStyle1);
		
		cell = row.createCell((short)2,HSSFCell.CELL_TYPE_STRING);
		cell.setCellValue("Ville");
		cell.setCellStyle(cellStyle1);
		
		cell = row.createCell((short)3,HSSFCell.CELL_TYPE_STRING);
		cell.setCellValue("Nom du parrain");
		cell.setCellStyle(cellStyle1);
		
		cell = row.createCell((short)4,HSSFCell.CELL_TYPE_STRING);
		cell.setCellValue("Langue du parrain");
		cell.setCellStyle(cellStyle1);
		
		cell = row.createCell((short)5,HSSFCell.CELL_TYPE_STRING);
		cell.setCellValue("Ville du parrain");
		cell.setCellStyle(cellStyle1);
		
		for(int i = 0; i < liste_finale.getListeFilleul().size() ; i++) {
			row = sheet.createRow(i+1);
			Filleul f = liste_finale.getListeFilleul().get(i);
			
			cell = row.createCell((short)0,HSSFCell.CELL_TYPE_STRING);
			cell.setCellValue(f.getNom());
			cell.setCellStyle(cellStyle);
			
			cell = row.createCell((short)1,HSSFCell.CELL_TYPE_STRING);
			if(f.getLangue()== Langue.FRANCAIS)
				cell.setCellValue("FRANCAIS");
			else
				cell.setCellValue("ANGLAIS");
			cell.setCellStyle(cellStyle);
			
			cell = row.createCell((short)2,HSSFCell.CELL_TYPE_STRING);
			cell.setCellValue(f.getVille());
			cell.setCellStyle(cellStyle);
			
			cell = row.createCell((short)3,HSSFCell.CELL_TYPE_STRING);
			cell.setCellValue(f.getParrain().getNom());
			cell.setCellStyle(cellStyle);
			
			cell = row.createCell((short)4,HSSFCell.CELL_TYPE_STRING);
			if(f.getParrain().getLangue()== Langue.FRANCAIS)
				cell.setCellValue("FRANCAIS");
			else
				cell.setCellValue("ANGLAIS");
			cell.setCellStyle(cellStyle);
			
			cell = row.createCell((short)5,HSSFCell.CELL_TYPE_STRING);
			cell.setCellValue(f.getParrain().getVille());
			cell.setCellStyle(cellStyle);
		}
		
		for(int i =0; i <= sheet.getLastRowNum(); i++)
			sheet.autoSizeColumn((short)i);
		try {
			fileOut = new FileOutputStream("monfichier.xls");
			wb.write(fileOut);
			Runtime run = Runtime.getRuntime();
			String cmd = "cmd /C start excel monfichier.xls";
			Process proc = run.exec(cmd);
			fileOut.close();
		}catch(FileNotFoundException e) {
			e.printStackTrace();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		
		System.out.println("Bien !");
	}
}
