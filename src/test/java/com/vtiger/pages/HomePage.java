package com.vtiger.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.aventstack.extentreports.ExtentTest;
import com.vtiger.utilities.CommonActions;

public class HomePage {
	
private WebDriver driver;
private CommonActions actions;
public ExtentTest logger;
	
	public HomePage(WebDriver driver,ExtentTest logger)
	{
		this.driver = driver;
		this.logger=logger;
		PageFactory.initElements(driver, this);
		actions = new CommonActions(driver,logger);
	}
	
	@FindBy(linkText="Logout")
	WebElement logout;
	
	
	public void clickLogout()
	{
		//logout.click();
		actions.clickElement(logout, "Link Logout clicked");
	}

}
