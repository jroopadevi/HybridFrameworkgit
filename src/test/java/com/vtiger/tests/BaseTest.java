package com.vtiger.tests;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseTest {
	public WebDriver driver;	
	public ExtentReports extent;
	public ExtentTest logger;
	public Properties prop;
	public Map<String,Map<String,String>> dt;
	
	@BeforeSuite
	public void initiation()
	{
		dt = getDatawithFillo("Sheet1");		
		prop = readGlobalSetting();
		createExtentReport();
		launchApp();
	}
	
	@AfterSuite
	public void tierdown()
	{
		driver.quit();
	}
	
	
	public void launchApp()
	{
		if(prop.getProperty("Browser").equals("chrome"))
		{
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		}
		else if(prop.getProperty("Browser").equals("firefox"))
		{
		WebDriverManager.firefoxdriver().setup();
		driver = new FirefoxDriver();
		}
		else if(prop.getProperty("Browser").equals("edge"))
		{
		WebDriverManager.edgedriver().setup();
		driver = new EdgeDriver();
		}
		driver.manage().window().maximize();
		int imptime = Integer.parseInt(prop.getProperty("ImplicitWait"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(imptime));
		driver.get(prop.getProperty("AppUrl"));
	}
	
	public Properties readGlobalSetting()
	{
		String file = System.getProperty("user.dir") + "/src/test/resources/Configuration/GlobalSettings.properties";
		Properties prop = new Properties();
		try {
			FileInputStream fis  = new FileInputStream(file);
			prop.load(fis);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return prop;
	}
	
	public void createExtentReport()
	{
		Date d = new Date();
		DateFormat ft = new SimpleDateFormat("ddMMyyyyhhmmss");
		String fileName = ft.format(d);
		ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir") + "/src/test/java/com/vtiger/reports/ExtentReport"+fileName+".html");
    	// Create an object of Extent Reports
		extent = new ExtentReports();  
		extent.attachReporter(htmlReporter);
		extent.setSystemInfo("Host Name", "Automation Test Hub");
		    	extent.setSystemInfo("Environment", "Test");
		extent.setSystemInfo("User Name", "Rajesh U");
		htmlReporter.config().setDocumentTitle("vTiger Regression Report"); 
		            // Name of the report
		htmlReporter.config().setReportName("Name of the Report Comes here "); 
		            // Dark Theme
		htmlReporter.config().setTheme(Theme.STANDARD); 
		
	}
	
	public void ReadTestData(String workbook, String sheet) 
	{
		System.out.println(workbook);
		Fillo fillo=new Fillo();
		Connection connection;
		try {
			connection = fillo.getConnection(workbook);
		
		String strQuery="Select * from "+sheet;
		Recordset recordset=connection.executeQuery(strQuery);
		int rowcount = recordset.getCount();
	    System.out.println("Row count="+rowcount);
	    List<String> colms = recordset.getFieldNames();
	    for(String colm:colms)
	    {
	    	System.out.println(colm);
	    }
		 
		recordset.close();
		connection.close();
		} catch (FilloException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Map<String,Map<String,String>> getDatawithFillo(String Sheet)
	{
		Map<String,Map<String,String>> AllData = null;
		try {
		Fillo f = new Fillo();		
		Connection connection = f.getConnection(System.getProperty("user.dir")+"/src/test/resources/Data/TestData.xlsx");
		Recordset recordset = connection.executeQuery("SELECT * FROM "+Sheet);
		int numberOfRows = recordset.getCount();
		List<String> AllColms = recordset.getFieldNames();
		int numberOfColms = AllColms.size();		
		
		AllData = new LinkedHashMap<String,Map<String,String>>();
		while (recordset.next()) {
			Map<String,String> colmData = new LinkedHashMap<String,String>();
			for(int i=1;i<numberOfColms;i++)
			{
				String key = AllColms.get(i).trim();
				String val = recordset.getField(AllColms.get(i)).trim();
				colmData.put(key, val);
			}
			
			
			AllData.put(recordset.getField("TCName"), colmData);
		}
		//System.out.println(AllData);
		} catch (FilloException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return AllData;
		
	}

}
