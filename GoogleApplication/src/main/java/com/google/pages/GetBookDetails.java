package com.google.pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSFindBy;

import java.util.Hashtable;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.DataProvider;

import com.google.base.BaseDriver;
import com.google.util.DataUtil;
import com.google.util.ExtentManager;
import com.google.util.Xls_Reader;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

	public class GetBookDetails extends BasePage {
		
		public ExtentReports rep = ExtentManager.getInstance();
		public static ExtentTest test;
		String testCaseName="SearchBook";
		String colName = "URL";
		String url;
		Xls_Reader xls;
		
		@AndroidFindBy(id="fakebox-input")
		protected MobileElement searchBox;
		
		@AndroidFindBy(className="wx62f PZPZlf")
		protected MobileElement authorName;
		
		
		public GetBookDetails(AppiumDriver<MobileElement> driver) {
			super(driver);
			PageFactory.initElements(new AppiumFieldDecorator(this.driver), this);
		}
		
		public void searchText(String bookName) {
			
			try {
				
				this.searchBox.sendKeys(bookName);
				System.out.println("Entered bookName is " + bookName);
			} catch (NoSuchElementException e) {
				e.printStackTrace();
			}
		}
		
		public String selectLink(){
			try{
				url = driver.getCurrentUrl();
			}catch(NoSuchElementException e) {
				e.printStackTrace();
			}
			return url;
		}
		
		public String getAuthorName() {
			String author = this.authorName.getText();
			return author.substring(8);
		}
	
		
}
