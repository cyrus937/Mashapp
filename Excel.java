
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

public class Excel {
    private String chemin = null;

    public Excel(String chemin) {
	this.chemin = chemin;
    }

    public String getChemin() {
	return chemin;
    }

    public ArrayList getInfo() throws IOException {
	ArrayList<String> liste = new ArrayList<String>();
	String str = "";
	POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(chemin));
	HSSFWorkbook wb = new HSSFWorkbook(fs);
	HSSFSheet sheet = wb.getSheetAt(0);
	HSSFCell cell = null;
	for (Iterator<HSSFRow> rowIt = sheet.rowIterator(); rowIt.hasNext();) {
	    HSSFRow row = (HSSFRow) rowIt.next();
	    for (Iterator<HSSFCell> cellIt = row.cellIterator(); cellIt.hasNext();) {
		cell = (HSSFCell) cellIt.next();
		// totalLigne += cell.getNumericCellValue();
		if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
		    int num = (int) cell.getNumericCellValue();
		    str += Integer.toString(num) + ";";
		} else
		    str += cell.getStringCellValue() + ";";
	    }
	    liste.add(str);
	    str = "";
	}
	return liste;
    }

    public static void setInfo(ArrayList<Filleul> liste_filleul) {
	HSSFWorkbook wb = new HSSFWorkbook();
	FileOutputStream fileOut;
	HSSFSheet sheet = wb.createSheet("Liste Finale");

	HSSFCellStyle cellStyle = null, cellStyle1 = null;
	HSSFFont fonte = wb.createFont(), fonte1 = wb.createFont();

	cellStyle = wb.createCellStyle();
	cellStyle1 = wb.createCellStyle();

	fonte.setFontName("Calibri");
	fonte.setFontHeightInPoints((short) 12);

	fonte1.setFontName("Calibri");
	fonte1.setFontHeightInPoints((short) 12);
	fonte1.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);

	cellStyle.setFont(fonte);
	cellStyle1.setFont(fonte1);

	HSSFRow row = sheet.createRow(0);
	HSSFCell cell = row.createCell((short) 0, HSSFCell.CELL_TYPE_STRING);
	cell.setCellValue("Nom et Prenoms");
	cell.setCellStyle(cellStyle1);

	cell = row.createCell((short) 1, HSSFCell.CELL_TYPE_STRING);
	cell.setCellValue("Langue");
	cell.setCellStyle(cellStyle1);

	cell = row.createCell((short) 2, HSSFCell.CELL_TYPE_STRING);
	cell.setCellValue("Ville");
	cell.setCellStyle(cellStyle1);

	cell = row.createCell((short) 3, HSSFCell.CELL_TYPE_STRING);
	cell.setCellValue("Nom du parrain");
	cell.setCellStyle(cellStyle1);

	cell = row.createCell((short) 4, HSSFCell.CELL_TYPE_STRING);
	cell.setCellValue("Langue du parrain");
	cell.setCellStyle(cellStyle1);

	cell = row.createCell((short) 5, HSSFCell.CELL_TYPE_STRING);
	cell.setCellValue("Ville du parrain");
	cell.setCellStyle(cellStyle1);

	for (int i = 0; i < liste_filleul.size(); i++) {
	    row = sheet.createRow(i + 1);
	    Filleul f = liste_filleul.get(i);

	    cell = row.createCell((short) 0, HSSFCell.CELL_TYPE_STRING);
	    cell.setCellValue(f.getNom());
	    cell.setCellStyle(cellStyle);

	    cell = row.createCell((short) 1, HSSFCell.CELL_TYPE_STRING);
	    if (f.getLangue() == Langue.FRANCAIS)
		cell.setCellValue("FRANCAIS");
	    else
		cell.setCellValue("ANGLAIS");
	    cell.setCellStyle(cellStyle);

	    cell = row.createCell((short) 2, HSSFCell.CELL_TYPE_STRING);
	    cell.setCellValue(f.getVille());
	    cell.setCellStyle(cellStyle);

	    cell = row.createCell((short) 3, HSSFCell.CELL_TYPE_STRING);
	    cell.setCellValue(f.getParrain().getNom());
	    cell.setCellStyle(cellStyle);

	    cell = row.createCell((short) 4, HSSFCell.CELL_TYPE_STRING);
	    if (f.getParrain().getLangue() == Langue.FRANCAIS)
		cell.setCellValue("FRANCAIS");
	    else
		cell.setCellValue("ANGLAIS");
	    cell.setCellStyle(cellStyle);

	    cell = row.createCell((short) 5, HSSFCell.CELL_TYPE_STRING);
	    cell.setCellValue(f.getParrain().getVille());
	    cell.setCellStyle(cellStyle);
	}

	for (int i = 0; i <= sheet.getLastRowNum(); i++)
	    sheet.autoSizeColumn((short) i);
	try {
	    fileOut = new FileOutputStream("final.xls");
	    wb.write(fileOut);
	    Runtime run = Runtime.getRuntime();
	    String cmd = "cmd /C start excel final.xls";
	    Process proc = run.exec(cmd);
	    fileOut.close();
	} catch (FileNotFoundException e) {
	    e.printStackTrace();
	} catch (IOException e) {
	    e.printStackTrace();
	}

	System.out.println("Bien !");
    }

    public static void mashapp_algorithme(String chemin_filleul, String chemin_parrain) throws IOException {
	Excel excel = null;
	ListeFinale liste_finale = new ListeFinale();
	excel = new Excel(chemin_filleul);
	Excel excel2 = new Excel(chemin_parrain);
	if (excel != null)
	    liste_finale.initListeParrain(excel2.getInfo());
	if (excel2 != null)
	    liste_finale.initListeFilleul(excel.getInfo());
	for (Filleul f : liste_finale.getListeFilleul()) {
	    if (f.getPotentiel_parrain() != null)
		System.out.println(f + " -> Parrain " + f.getPotentiel_parrain());
	    else
		System.out.println(f);
	}

	liste_finale.match();
	Excel.setInfo(liste_finale.getListeFilleul());
    }
}
