package com.vtiger.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.aventstack.extentreports.ExtentTest;
import com.vtiger.utilities.CommonActions;

public class LoginPage {
	
	private WebDriver driver;
	private CommonActions actions;
	public ExtentTest logger;
	
	public LoginPage(WebDriver driver,ExtentTest logger)
	{
		this.driver = driver;
		this.logger=logger;
		PageFactory.initElements(driver, this);
		actions = new CommonActions(driver,logger);
	}
	
	@FindBy(name="user_name")
	WebElement username;
	
	@FindBy(name="user_password")
	WebElement password;
	
	@FindBy(name="Login")
	WebElement login;
	
	@FindBy(xpath="//*[contains(text(),'You must specify a valid username and password.')]")
	WebElement ErrorMsg;
	
	@FindBy(xpath="//img[@src='include/images/vtiger-crm.gif']")
	WebElement logo;
	
	/*
	By username = By.name("user_name");
	By password = By.name("user_password");
	By login = By.name("Login");
	By ErrorMsg = By.xpath("//*[contains(text(),'You must specify a valid username and password.')]");
	By logo = By.xpath("//img[@src='include/images/vtiger-crm.gif']");
	*/
	
	
	public void Login(String userid,String pwd)
	{
		/*
		username.clear();
		username.sendKeys(userid);
		password.clear();
		password.sendKeys(pwd);
		login.click();
		*/
		actions.enterValue(username, userid, "Text "+userid+" entered into username field");
		actions.enterValue(password, pwd,"Text "+pwd+" entered into password field");
		actions.clickElement(login,"Login button clicked");
	}
	
	public void verifyErrorMsg()
	{
		//ErrorMsg.isDisplayed();
		actions.ElementExist(ErrorMsg,"Error Message validated successfully");
	}
	
	public void verifyLogo()
	{
		//logo.isDisplayed();
		actions.ElementExist(logo,"Logo is present on login page");
	}
	
	
	

}
