package com.google.pages;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import com.google.base.BaseDriver;

public class SearchBook extends BaseDriver{

	@FindBy(id="fakebox-input")
	WebElement searchBox;
	
	@FindBy(name="John Kotter")
	WebElement authorName;
	
	
	public SearchBook(){
		PageFactory.initElements(driver, this);
	}
	
	public Object searchText(String bookName) {
		
		try {
			
			this.searchBox.sendKeys(bookName);
			System.out.println("Entered bookName is " + bookName);
		} catch (NoSuchElementException e) {
			e.printStackTrace();
		}
		return bookName;
	}
	
	public void verifyAuthor(){
		Assert.assertEquals(this.authorName, "John Kotter");
	}
}
	