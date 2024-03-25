package CommonFunctions;

import static org.testng.Assert.assertEquals;

import java.awt.RenderingHints.Key;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

import org.apache.logging.log4j.util.PropertiesUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;

import Utilites.PropertyFileUtil;
import io.opentelemetry.sdk.metrics.data.Data;

public class FunctionLibrary {

	public static WebDriver driver;
	
	//method for start browser
	
	public static WebDriver StartBrowser() throws Throwable {
		if(PropertyFileUtil.getValueForKey("Browser").equalsIgnoreCase("chrome")) {
			driver=new ChromeDriver();
			driver.manage().window().maximize();
		}
		else if (PropertyFileUtil.getValueForKey("Browser").equalsIgnoreCase("firefox")) {
			driver= new FirefoxDriver();
			
		}
		else {
			Reporter.log("Browser value is not matching",true);
		}
		return driver;	
		
	}
	
	//method for lunch url
	
	public static void OpenUrl() throws Throwable {
		driver.get(PropertyFileUtil.getValueForKey("Url"));
	}
	
	//method for wait element
	
	public static void WaitForElement(String locatortype,String locatorvalue, String textdata) { 
		
		WebDriverWait mywait=new WebDriverWait(driver, Duration.ofSeconds(Integer.parseInt(textdata)));
		if(locatortype.equalsIgnoreCase("id")){
		mywait.until(ExpectedConditions.visibilityOfElementLocated(By.id(locatorvalue)));
		}
		if(locatortype.equalsIgnoreCase("xpath")) {
		mywait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locatorvalue)));
		}
		if(locatortype.equalsIgnoreCase("name")) {
			mywait.until(ExpectedConditions.visibilityOfElementLocated(By.name(locatorvalue)));
		}
		
	}
	
	//method for sendkeys in any testboxes
	
	
		
		public static void TypeAction(String locatortype,String locatorvalue, String textdata) {
			
			if(locatortype.equalsIgnoreCase("id")) {
				driver.findElement(By.id(locatorvalue)).clear();
				driver.findElement(By.id(locatorvalue)).sendKeys(textdata);
			}
			if(locatortype.equalsIgnoreCase("xpath")) {
				driver.findElement(By.xpath(locatorvalue)).clear();
				driver.findElement(By.xpath(locatorvalue)).sendKeys(textdata);
			}
			if(locatortype.equalsIgnoreCase("name")) {
				driver.findElement(By.name(locatorvalue)).clear();
				driver.findElement(By.name(locatorvalue)).sendKeys(textdata);
			}
		}
		
		//method for click buttons,imga,links,checkbox
		
		public static void ClickAction(String locatortype,String locatorvalue) {
			if(locatortype.equalsIgnoreCase("id")) {
				driver.findElement(By.id(locatorvalue)).click();
			}
			if(locatortype.equalsIgnoreCase("xpath")) {
				driver.findElement(By.xpath(locatorvalue)).click();
			}
			if(locatortype.equalsIgnoreCase("name")) {
				driver.findElement(By.name(locatorvalue)).sendKeys(Keys.ENTER);
			}
		}
		
		//method for  validate the tittle
		
		public static void ValidateTittle(String Expted_title) {
			String Actual_tittle =driver.getTitle();
			try {
				Assert.assertEquals(Actual_tittle, Expted_title, "This title is notmatching");
			} catch (AssertionError e) {
				System.out.println(e.getMessage());
			}
			
			
		}
		
		//method for close browser
		
		public static void CloseBrowser() {
			driver.quit();
		}
		
		
		//method for genarate date
		
		public static String GenarateDate() {
			Date date=new Date();
			DateFormat df= new SimpleDateFormat("YYYY_MM_DD hh_mm_ss");
			return df.format(date);	
		}
		
		//method for listboxes 
		public static void dropDownAction(String locatortype,String locatorvalue, String textdata) {
			
			if(locatortype.equalsIgnoreCase("xpath")) {
				int value = Integer.parseInt(textdata);
				Select element= new Select(driver.findElement(By.xpath(locatorvalue)));
				element.selectByIndex(value);
			}
			if(locatortype.equalsIgnoreCase("id")) {
				int value = Integer.parseInt(textdata);
				Select element= new Select(driver.findElement(By.id(locatorvalue)));
				element.selectByIndex(value);
			}
			if(locatortype.equalsIgnoreCase("name")) {
				int value = Integer.parseInt(textdata);
				Select element= new Select(driver.findElement(By.name(locatorvalue)));
				element.selectByIndex(value);
			}
		}
		
		//method for stock account number capture into notepad
		public static void captureStockNumber(String locatortype,String locatorvalue) throws Throwable {
			
			String stocknum="";
			if(locatortype.equalsIgnoreCase("xpath")) {
				stocknum=driver.findElement(By.xpath(locatorvalue)).getAttribute("value");
			}
			if(locatortype.equalsIgnoreCase("id")) {
				stocknum=driver.findElement(By.id(locatorvalue)).getAttribute("value");
			}
			if(locatortype.equalsIgnoreCase("name")) {
				stocknum=driver.findElement(By.name(locatorvalue)).getAttribute("value");
			}
			System.out.println(stocknum);
			
         FileWriter fw=new FileWriter("./CaptureData/stockNumber.txt");
         BufferedWriter bw= new BufferedWriter(fw);
         bw.write(stocknum);
         bw.flush();
         bw.close();
		}
		
		//method for stock table
		public static void stockTable() throws Throwable {
			FileReader fr = new FileReader("./CaptureData/stockNumber.txt");
			BufferedReader br=new BufferedReader(fr);
		
			String Ex_Data=br.readLine();
			if(!driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("search-textbox"))).isDisplayed());
			driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("search-panel"))).click();
			driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("search-textbox"))).clear();
			driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("search-textbox"))).sendKeys(Ex_Data);
			driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("search-button"))).click();
			Thread.sleep(4000);
			String Act_data= driver.findElement(By.xpath("//table[@class='table ewTable']/tbody/tr[1]/td[8]/div/span/span")).getText();
		    Reporter.log(Ex_Data+"      "+Act_data,true);
		    
		    try {
		    	Assert.assertEquals(Ex_Data, Act_data,"Stock is Not Matching");
			} catch (AssertionError e) {
				System.out.println(e.getMessage());
			}
		}
		
		// method for capture capture for CapureSupNum
		public static void CapureSupNum(String locatortype,String locatorvalue) throws Throwable {
			String supplier_number="";
			
			if(locatortype.equalsIgnoreCase("xpath")) {
				supplier_number=driver.findElement(By.xpath(locatorvalue)).getAttribute("value");
			}
			if(locatortype.equalsIgnoreCase("id")) {
				supplier_number=driver.findElement(By.id(locatorvalue)).getAttribute("value");
			}
			if(locatortype.equalsIgnoreCase("name")) {
				supplier_number=driver.findElement(By.name(locatorvalue)).getAttribute("value");
			}
			System.out.println(supplier_number);
			FileWriter fw = new FileWriter("./CaptureData/supplierNumber.txt");
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(supplier_number);
			bw.flush();
			bw.close();

		}
		
		public static void supplierTable()throws Throwable
		{
			FileReader fr = new FileReader("./CaptureData/supplierNumber.txt");
			BufferedReader br = new BufferedReader(fr);
			String Exp_Data = br.readLine();
			if(!driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("search-textbox"))).isDisplayed())
				driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("search-panel"))).click();
			driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("search-textbox"))).clear();
			driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("search-textbox"))).sendKeys(Exp_Data);
			driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("search-button"))).click();
			Thread.sleep(4000);
			String Act_Data = driver.findElement(By.xpath("//table[@class='table ewTable']/tbody/tr[1]/td[6]/div/span/span")).getText();
			Reporter.log(Act_Data+"      "+Exp_Data,true);
			try {
			Assert.assertEquals(Act_Data, Exp_Data, "Supplier Number Not Matching");
			}catch(AssertionError a)
			{
				System.out.println(a.getMessage());
			}
		}


		
		
	
}

