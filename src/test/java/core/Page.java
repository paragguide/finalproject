package core;

import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;


import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.AfterTest;
import java.sql.*;
public class Page 
{
	public WebDriver driver = null;
	public Connection con = null;
	public ResultSet rs = null;
	public Logger log = null;
	public ExtentReports report = null;
	public ExtentTest test = null;
	
  @Parameters({"browser","url","scrshot"})  // third method -> called again -2 for each @Test
  @BeforeMethod
  public void openBrowser(String browser,String url,String scrshot) throws Exception 
  {
	  if(browser.equals("chrome"))
	  {
		  System.setProperty("webdriver.chrome.driver","D:\\browserdrivers\\chromedriver.exe");
		//	WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
			
			log.debug("chrome open");
			test.log(LogStatus.PASS, "chrome open");
			
	  }
	  else if(browser.equals("edge"))
	  {
		  //WebDriverManager.edgedriver().setup();
		  driver = new EdgeDriver();
	  }
	  driver.navigate().to(url);
	  log.debug("url to test "+url);
	  test.log(LogStatus.INFO, "url to test "+url);
	  
	  PageFactory.initElements(driver, this); // for @FindBy
	  
	  driver.manage().window().maximize();
	  
	  driver.manage().timeouts().implicitlyWait(50L, TimeUnit.SECONDS);
  
	  // screen shot
	  File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE); // store file to temprary location
		//Now you can do whatever you need to do with it, for example copy somewhere download org.apache.commons.io.FileUtils class API set classpath and use this class to copy.
		String screenshotpath = System.getProperty("user.dir")+"\\src\\test\\java\\screenshot\\"+scrshot+".jpeg";
		
		FileUtils.copyFile(scrFile, new File(screenshotpath));

		test.log(LogStatus.INFO, "Screen shot taken");

	  
  }

  @AfterMethod  // fourth method -> called again -2 after @Test
  public void closeBrowser() 
  {
	  driver.quit();
  }

  @Parameters({"wbpath","sheetname"}) // second method
  @BeforeClass
  public void makeWBConnection(String wbpath,String sheetname) throws Exception
  {
	  Class.forName("com.googlecode.sqlsheet.Driver"); // register excel driver 
		con = DriverManager.getConnection("jdbc:xls:file:"+wbpath);
		System.out.println("connected to excel WB..");
		Statement stm=con.createStatement();  // default top to bottom
		 rs=stm.executeQuery("select * from "+sheetname); // Sheet name 

  }

  @AfterClass   // fifth method
  public void closeWBConnection() throws Exception 
  {
	  con.commit();
	  con.close();
  }
  
  @Parameters({"filename","key"})   // First method
  @BeforeTest
  public void generateLogReport(String filename,String key) throws Exception
  {
	  if(!Boolean.parseBoolean(key))
	  {
		  throw new SkipException("skip test");
	  }
	  else {
	  Properties p = new Properties();
	FileInputStream fis = new FileInputStream(System.getProperty("user.dir")+"\\src\\test\\resources\\"+filename+".properties");
			p.load(fis);
			PropertyConfigurator.configure(p);  // load
			log = log.getLogger(filename);
			
			report = new ExtentReports(System.getProperty("user.dir")+"\\src\\test\\java\\reports\\"+filename+".html");
			test = report.startTest(filename);
	  }
  }

  @AfterTest  // sixth method
  public void closeReport() 
  {
	  report.endTest(test);
		report.flush();

  }

}
