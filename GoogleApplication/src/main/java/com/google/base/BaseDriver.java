package com.google.base;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.IOSMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.remote.MobilePlatform;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import com.google.util.ExtentManager;
import com.google.util.TestUtil;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

@SuppressWarnings("unused")
public class BaseDriver {
	
	public static WebDriver driver;
	public static Properties prop;
	public static AppiumDriver<MobileElement> mobileDriver;
	protected WebDriverWait wait;
	protected boolean isDevice = false;
	static DateFormat dateFormat;
	public ExtentReports rep = ExtentManager.getInstance();
	public static ExtentTest test;

	
	
	@Parameters({ "serverUrl", "platformName", "deviceName", "android_avd", "ios_udid", "is_simulator" })
@BeforeTest
	public static void initialisation() {
		String  browserName= prop.getProperty("browser");
		if(browserName.equals("Chrome")) {
			System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"//drivers//chromedriver.exe");
			driver =new ChromeDriver();
		}
		else if(browserName.equals("Firefox")) {
			System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir")+"//drivers//geckodriver.exe");
			driver =new FirefoxDriver();
			
		}
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().pageLoadTimeout(TestUtil.PAGE_LOAD_TIMEOUT, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(TestUtil.IMPLICIT_WAIT, TimeUnit.SECONDS);
		
		driver.get(prop.getProperty("url"));
		
	}
	protected void createAppiumDriver(String serverUrl, String platformName, String deviceName, String android_avd, String ios_udid, String is_simulator) throws MalformedURLException, InterruptedException {

		if (platformName.equalsIgnoreCase("Android"))
		{
			mobileDriver = new AndroidDriver<MobileElement>(new URL(serverUrl), createAndroidCapabilities(deviceName, android_avd, is_simulator.equalsIgnoreCase("true")));
		}
		

		//initializing explicit wait object
		wait = new WebDriverWait(mobileDriver, 15);
		
		mobileDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		
		this.isDevice = BaseDriver.isDevice(mobileDriver); 
	}



private DesiredCapabilities createAndroidCapabilities(String deviceName, String android_avd, boolean is_emulator) {
	DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "uiautomator2");
		capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, MobilePlatform.ANDROID);
		capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, deviceName);
		
	capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "6.0.1");
	capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, "Chrome");

		capabilities.setCapability("appPackage", "com.cox.android.mobileconnect");
		capabilities.setCapability("appActivity", "com.cox.android.mobileconnect.user.splash.SplashScreen");
	
		return capabilities;
}

	 
	 

	public static boolean isAndroidPlatform(AppiumDriver<MobileElement> mobileDriver) {
		return mobileDriver.getPlatformName().equalsIgnoreCase(MobilePlatform.ANDROID);
	}

	public static boolean isDevice(AppiumDriver<MobileElement> mobileDriver) {
		boolean isDevice = false;
		Capabilities deviceCapabilities = mobileDriver.getCapabilities();

		if (BaseDriver.isAndroidPlatform(mobileDriver)) {
			isDevice = true;
		
		}

		return isDevice;
	}
	@AfterSuite
	public void afterTest(){
		mobileDriver.quit();
		driver.quit();
	}

	public static void takeScreenshot() {
		// fileName of screenshot
		Date d = new Date();
		String screenshotFile = d.toString().replace(":", "_").replace(" ", "_") + ".png";
		// store screenshot in file
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(scrFile, new File(System.getProperty("user.dir") + "//screenshots//" + screenshotFile));
		} catch (IOException e) {
			e.printStackTrace();
		}
		// put screenshot file in reports
		test.log(LogStatus.INFO, "Screenshot->"+ test.addScreenCapture(System.getProperty("user.dir") + "//screenshots//" + screenshotFile));
	}
	
}


