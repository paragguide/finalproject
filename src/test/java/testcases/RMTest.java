package testcases;

import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import core.Page;

import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.annotations.DataProvider;

public class RMTest extends Page
{
	@FindBy(xpath = xpath.RMLoginXpath.id)
	WebElement userid;
	
	@FindBy(xpath = xpath.RMLoginXpath.pwd)
	WebElement password;
	
	@FindBy(xpath = xpath.RMLoginXpath.submit)
	WebElement submit;
	
  @Test(dataProvider = "rmdata")
  public void rmlogin(String n, String s) 
  {
	  log.debug("RM Login clled ");
	  test.log(LogStatus.INFO, "RM Login calledt ");
	  
	  userid.sendKeys(n);
	  log.debug("userid sent "+n);
	  test.log(LogStatus.INFO, "userid sent "+n);
	  
	  password.sendKeys(s);
	  log.debug("pwd sent "+s);
	  test.log(LogStatus.INFO, "pwd sent "+s);
	  
	  submit.click();
	  log.debug("form submit ");
	  test.log(LogStatus.PASS, "form submit ");
	  
  }

  @DataProvider
  public Object[][] rmdata() throws Exception 
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
