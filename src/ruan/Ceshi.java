package ruan;

import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.nio.charset.Charset;


import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
public class Ceshi {
  private WebDriver driver;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();
  
  
  String val = null;
	static String id;
	static String github;
  DecimalFormat df = new DecimalFormat("0");
  static List<ArrayList<String>>  strLists = new ArrayList<ArrayList<String>>();
  
  public void getValues(String filePath )
  {
     
      try{
          InputStream is = new FileInputStream(filePath);
       
          XSSFWorkbook xwb = new XSSFWorkbook(is); 
        
          XSSFSheet sheet = xwb.getSheetAt(0); 
          strLists.clear();
          
          for (int i = sheet.getFirstRowNum(); i < sheet.getPhysicalNumberOfRows(); i++) { 
          	List<String> strList = new ArrayList<String>();
          	XSSFRow row = sheet.getRow(i);
          	int colNum = row.getPhysicalNumberOfCells();
            
              for (int j = 0; j < colNum; j++) {
                  XSSFCell cell = row.getCell(j);
                  strList.add(getXCellVal(cell));
              }
              strLists.add(i, (ArrayList<String>) strList); 
          }
          
          }catch(Exception e) {
              System.out.println("ÒÑÔËÐÐxlRead() : " + e );
          }
  }

private String getXCellVal(XSSFCell cell) {
	  switch (cell.getCellType()) {
    case XSSFCell.CELL_TYPE_NUMERIC:
        val = df.format(cell.getNumericCellValue()); 
        break;
    case XSSFCell.CELL_TYPE_STRING: 
        val = cell.getStringCellValue();
        break;
    case XSSFCell.CELL_TYPE_BLANK: //¿Õ°×
        val = cell.getStringCellValue();
        break;
	  }
	  return val;
	}


  @Before
  public void setUp() throws Exception {
    driver = new FirefoxDriver();
    baseUrl = "https://psych.liebes.top/";
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
  }

  @Test
  public void testCeshi() throws Exception {
	  
	  String filePath="C:/Users/maxiao/Desktop/input.xlsx";
		Ceshi er = new Ceshi();
		er.getValues(filePath);
	    for (int i = 0 ; i < strLists.size(); i++) {
	    id = strLists.get(i).get(0);
	    github = strLists.get(i).get(1);
	    String getpwd = id.substring(4,10);
	      driver.get(baseUrl + "/st");
	    driver.findElement(By.id("password")).clear();
	    driver.findElement(By.id("password")).sendKeys("");
	    driver.findElement(By.id("username")).clear();
	    driver.findElement(By.id("username")).sendKeys(id);
	    driver.findElement(By.id("password")).clear();
	    driver.findElement(By.id("password")).sendKeys(getpwd);
	    driver.findElement(By.id("submitButton")).click();
	    if (github
                .equals(driver
                        .findElement(By.cssSelector("p.login-box-msg"))
                        .getText().trim())){
	    	 System.out.println(id + " "+"success"+" "+github);
	    }
	    else{
	    	 System.out.println(id + " "+"fail"+" "+github);
	    }
	   
	    
	    }
    
  }

  @After
  public void tearDown() throws Exception {
    driver.quit();
    String verificationErrorString = verificationErrors.toString();
    if (!"".equals(verificationErrorString)) {
      fail(verificationErrorString);
    }
  }

  private boolean isElementPresent(By by) {
    try {
      driver.findElement(by);
      return true;
    } catch (NoSuchElementException e) {
      return false;
    }
  }

  private boolean isAlertPresent() {
    try {
      driver.switchTo().alert();
      return true;
    } catch (NoAlertPresentException e) {
      return false;
    }
  }

  private String closeAlertAndGetItsText() {
    try {
      Alert alert = driver.switchTo().alert();
      String alertText = alert.getText();
      if (acceptNextAlert) {
        alert.accept();
      } else {
        alert.dismiss();
      }
      return alertText;
    } finally {
      acceptNextAlert = true;
    }
  }
}
