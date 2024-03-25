package DriverFactory;

import java.sql.Driver;

import org.openqa.selenium.WebDriver;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import CommonFunctions.FunctionLibrary;
import Utilites.ExcelFilesUtail;

public class DriverScript {
	public static WebDriver driver;
	String Inputpath="./FileInput/DataEngine.xlsx";
	String Outputpath="./FileOutput/HybridReprots.xlsx";
	ExtentReports Reports;
	ExtentTest logger;
	String TastCases="MasterTestCases";
	public void startTest() throws Throwable {
		String Module_Status="";
		//
		ExcelFilesUtail xl= new ExcelFilesUtail(Inputpath);
		for(int i=1;i<=xl.RowCount(TastCases);i++) {
			if(xl.GetCellData(TastCases, i, 2).equalsIgnoreCase("Y")) 
			{
				String TCModule=xl.GetCellData(TastCases, i, 1);
				//test  scenarios
				Reports= new ExtentReports("./target/Reports/"+TCModule+FunctionLibrary.GenarateDate()+"html");
				logger=Reports.startTest(TCModule);
				for(int j=1;j<=xl.RowCount(TCModule);j++) {
					String Description=xl.GetCellData(TCModule, j, 0);
					String Objective_Type=xl.GetCellData(TCModule, j, 1);
					String Locator_Type=xl.GetCellData(TCModule, j, 2);
					String Locator_Value=xl.GetCellData(TCModule, j, 3);
					String Test_Data=xl.GetCellData(TCModule, j, 4);
					try {

						if(Objective_Type.equalsIgnoreCase("StartBrowser")) {
							driver =FunctionLibrary.StartBrowser();
							logger.log(LogStatus.INFO,Description);
						}
						if(Objective_Type.equalsIgnoreCase("OpenUrl")) {
							FunctionLibrary.OpenUrl();
							logger.log(LogStatus.INFO,Description);
						}
						if(Objective_Type.equalsIgnoreCase("WaitForElement")) {
							FunctionLibrary.WaitForElement(Locator_Type, Locator_Value, Test_Data);
							logger.log(LogStatus.INFO,Description);
						}
						if(Objective_Type.equalsIgnoreCase("TypeAction")) {
							FunctionLibrary.TypeAction(Locator_Type, Locator_Value, Test_Data);
							logger.log(LogStatus.INFO,Description);
						}
						if(Objective_Type.equalsIgnoreCase("ClickAction")) {
							FunctionLibrary.ClickAction(Locator_Type,Locator_Value);
							logger.log(LogStatus.INFO,Description);
						}
						if(Objective_Type.equalsIgnoreCase("ValidateTittle")) {
							FunctionLibrary.ValidateTittle(Test_Data);
							logger.log(LogStatus.INFO,Description);
						}
						if(Objective_Type.equalsIgnoreCase("CloseBrowser")) {
							FunctionLibrary.CloseBrowser();
							logger.log(LogStatus.INFO,Description);
						}
						
						if(Objective_Type.equalsIgnoreCase("dropDownAction")) {
							FunctionLibrary.dropDownAction(Locator_Type, Locator_Value, Test_Data);
							logger.log(LogStatus.INFO,Description);
						}
						
						if(Objective_Type.equalsIgnoreCase("captureStockNumber")) {
							FunctionLibrary.captureStockNumber(Locator_Type, Locator_Value);
							logger.log(LogStatus.INFO,Description);
						}
						if(Objective_Type.equalsIgnoreCase("stockTable")) {
							FunctionLibrary.stockTable();
							logger.log(LogStatus.INFO,Description);
						}
						if(Objective_Type.equalsIgnoreCase("CapureSupNum")) {
							FunctionLibrary.CapureSupNum(Locator_Type,Locator_Value);
							logger.log(LogStatus.INFO,Description);
						}
						if(Objective_Type.equalsIgnoreCase("SupplierTable")) {
							FunctionLibrary.supplierTable();
							logger.log(LogStatus.INFO,Description);
						}
						
						//write as pass into status call data 
						xl.SetCellData(TCModule, j, 5, "pass",Outputpath);
						logger.log(LogStatus.PASS,Description);
						Module_Status="True";
						
					} 
					catch (Exception e) {
						System.out.println(e.getMessage());
						xl.SetCellData(TCModule, j, 5,"fail", Outputpath);
						logger.log(LogStatus.FAIL,Description);
						Module_Status="Fail";
					}
					if(Module_Status.equalsIgnoreCase("true")) {
						xl.SetCellData(TastCases, i, 3, "pass",Outputpath);
					} 
					else {
						xl.SetCellData(TastCases, i, 3, "fail",Outputpath);
					}
					Reports.endTest(logger);
					Reports.flush();
				}

			}
			else {
				xl.SetCellData(TastCases, i, 3, "Blocked",Outputpath);
			}
		}
	}


}
