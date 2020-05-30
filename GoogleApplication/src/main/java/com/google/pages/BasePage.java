package com.google.pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

public abstract class BasePage {
	
	protected AppiumDriver<MobileElement> driver;
	
	protected BasePage(AppiumDriver<MobileElement> driver) 
	{
		this.driver = driver;
	}

}
