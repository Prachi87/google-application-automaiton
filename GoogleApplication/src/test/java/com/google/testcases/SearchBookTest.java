package com.google.testcases;

import java.util.Hashtable;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.google.base.BaseDriver;
import com.google.pages.SearchBook;
import com.google.util.DataUtil;
import com.google.util.Xls_Reader;


public class SearchBookTest extends BaseDriver{
	
	SearchBook searchBook;
	String testCaseName="SearchBook";
	Xls_Reader xls;
	
	public SearchBookTest() {
		super();
	}
	
	@BeforeMethod
	public void setup() {
		
		searchBook=new SearchBook();
		
	}
	
	
	@Test(dataProvider="getData")
	public void searchBookName(Hashtable<String,String> data) {
		searchBook.searchText(data.get("URL"));
		
	}
	@Test
	public void verifyAuthor() {
		searchBook.verifyAuthor();
		
	}
	@DataProvider
	public Object[][] getData(){
		return DataUtil.getTestData(xls, testCaseName);
	}
}