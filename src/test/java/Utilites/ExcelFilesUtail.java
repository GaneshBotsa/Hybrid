package Utilites;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelFilesUtail {
	Workbook wb;
	public ExcelFilesUtail(String Excelpath) throws Throwable {
		FileInputStream fi=new FileInputStream(Excelpath);
		wb=WorkbookFactory.create(fi);
	}
	//maethod for row counting
	
	public int RowCount(String sheetName) {
		 return wb.getSheet(sheetName).getLastRowNum();
		 
	}
	//method for getcelldata and any data have intger type and its chang to string type
	
	public String GetCellData(String sheetName,int row,int coloumn) {
		
		String data="";
		if(wb.getSheet(sheetName).getRow(row).getCell(coloumn).getCellType()==CellType.NUMERIC) {
			int celldata=(int) wb.getSheet(sheetName).getRow(row).getCell(coloumn).getNumericCellValue();
			data=String.valueOf(celldata);
			
		}
		else
		{
			data =wb.getSheet(sheetName).getRow(row).getCell(coloumn).getStringCellValue();
		}
		return data;
	}
	
	//method for setcelldata and set style also
	
	public void SetCellData(String sheetName,int row,int coloumn,String status,String WriteExcel) throws Throwable {
		
	   Sheet ws=wb.getSheet(sheetName);
	   Row rowNum=ws.getRow(row);
	   Cell cell=rowNum.getCell(coloumn);
	   cell.setCellValue(status);
		if(status.equalsIgnoreCase("Pass"))
		{
			CellStyle style=wb.createCellStyle();
			Font font =wb.createFont();
			font.setColor(IndexedColors.GREEN.getIndex());
			font.setBold(true);
			style.setFont(font);
			ws.getRow(row).getCell(coloumn).setCellStyle(style);
			
		}
		else if(status.equalsIgnoreCase("Fail"))
		{
			CellStyle style = wb.createCellStyle();
			Font font = wb.createFont();
			font.setColor(IndexedColors.RED.getIndex());
			font.setBold(true);
			style.setFont(font);
			ws.getRow(row).getCell(coloumn).setCellStyle(style);
		}
		else if(status.equalsIgnoreCase("Blocked"))
		{
			CellStyle style = wb.createCellStyle();
			Font font = wb.createFont();
			font.setColor(IndexedColors.BLUE.getIndex());
			font.setBold(true);
			style.setFont(font);
			ws.getRow(row).getCell(coloumn).setCellStyle(style);
		}
		FileOutputStream fo =new FileOutputStream(WriteExcel);
		wb.write(fo);
		
	}

}
