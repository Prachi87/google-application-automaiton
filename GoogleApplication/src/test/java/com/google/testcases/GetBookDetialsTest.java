package com.google.testcases;


import java.util.Hashtable;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.google.base.BaseDriver;
import com.google.pages.GetBookDetails;
import com.google.util.DataUtil;
import com.google.util.Xls_Reader;



public class GetBookDetialsTest extends BaseDriver {

	protected GetBookDetails seachBook;
	String testCaseName="SearchBook";
	String colNameUrl = "URL";
	String colNameAuthor = "Author";
	String storeURL;
	String authorName;
	Xls_Reader xls;
	
	@BeforeClass
	public void setUp() throws Exception {
		
		test = rep.startTest("TestSearchedBook");
	
	}
	
	@AfterClass
	public void tearDown() throws Exception {
		
		this.seachBook = null;
	
	}
	
	
	@Test(dataProvider="writeData")
	public void testSearchedBook(Hashtable writeData) {
	
			this.seachBook.searchText("Leading Change the Book");
			
			storeURL = this.seachBook.selectLink();
			authorName = this.seachBook.getAuthorName();
	
	}
	
	@DataProvider
	public void writeData(){
		xls=new Xls_Reader(System.getProperty("user.dir")+"//src//main//java//com//google//testdata//Data.xlsx");
		 DataUtil.writeData(xls, testCaseName, colNameUrl, 1, storeURL);
		 DataUtil.writeData(xls, testCaseName, colNameAuthor, 1, authorName);
	}

}
