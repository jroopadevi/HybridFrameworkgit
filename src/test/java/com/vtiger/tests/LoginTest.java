package com.vtiger.tests;

import org.testng.annotations.Test;

import com.vtiger.pages.HomePage;
import com.vtiger.pages.LoginPage;

public class LoginTest extends BaseTest {
	
	
	
	
	@Test
	public void InvalidLoginTC01()
	{
		String TCName = "InvalidLoginTC01";
        logger = extent.createTest(TCName);
		LoginPage lp = new LoginPage(driver,logger);
		lp.Login(dt.get(TCName).get("Userid"), dt.get(TCName).get("Password"));
		lp.verifyErrorMsg();
		extent.flush();
	}
	
	@Test
	public void validLoginTC02()
	{
		String TCName = "validLoginTC02";
        logger = extent.createTest(TCName);
		LoginPage lp = new LoginPage(driver,logger);
		lp.Login(dt.get(TCName).get("Userid"), dt.get(TCName).get("Password"));
		HomePage hp = new HomePage(driver,logger);	
		hp.clickLogout();		
		extent.flush();
	}
	
	@Test
	public void verifyLogoTC03()
	{
		String TCName = "verifyLogoTC03";
        logger = extent.createTest(TCName);
		LoginPage lp = new LoginPage(driver,logger);
		lp.verifyLogo();
		extent.flush();
	}

}
