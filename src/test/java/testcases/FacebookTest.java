package testcases;

import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import core.Page;

import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.annotations.DataProvider;

public class FacebookTest extends Page 
{
	@FindBy(xpath = xpath.FacebookLoginXPath.id)
	WebElement userid;
	
	@FindBy(xpath = xpath.FacebookLoginXPath.pwd)
	WebElement password;
	
	@FindBy(xpath = xpath.FacebookLoginXPath.submit)
	WebElement submit;
	
  @Test(dataProvider = "facebookdata")
  public void facebooklogintest(String id, String pwd) 
  {
	  log.debug("Facebook Test Called");
	  test.log(LogStatus.PASS,"Facebook Test Called");
	// driver.findElement(By.xpath("//*[@id=\"email\"]")).sendKeys(id);
	      // or
	  userid.sendKeys(id); 
	  log.debug("userid sent "+id);
	  test.log(LogStatus.INFO, "userid sent "+pwd);
	  password.sendKeys(pwd);
	  log.debug("pwd sent "+pwd);
	  test.log(LogStatus.INFO, "pwdd sent "+pwd);
	  
	  submit.click();
	  log.debug("form submit");
	  test.log(LogStatus.PASS, "Form submit ");
	  
	 
  }

  @DataProvider
  public Object[][] facebookdata() throws Exception 
  {
	  ResultSetMetaData rsmt=rs.getMetaData();
	  int columncount=rsmt.getColumnCount();

	  rs.last();
	  int rowcount=rs.getRow();

	  System.out.println(columncount+" , "+rowcount);
	  rs.beforeFirst(); // reset

	  Object data[][] = new Object[rowcount][columncount]; //-> size of array 
	  			
	  for(int rowNum = 1 ; rowNum <= rowcount ; rowNum++)
	     { 
	  				
	  for(int colNum=1 ; colNum<= columncount; colNum++)
	        {
	                   rs.absolute(rowNum); // point to row  
	  	String data1= rs.getString(colNum); // getting values from excel
	  	
	  		data[rowNum-1][colNum-1]= data1 ; //adding table data in  array , array starts from 0
	  				}
	  			}
	  			
	  //System.out.println(data);
	  return data;

  }
}
