package com.assignments.modaltest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class modaltest {
	public static WebDriver driver;
	public static String webdriverURL;
	public static String testUrl;
	public static Integer delayTimerSec;
	public static Boolean b_FinalStatus;
	
	@Test
	public void traverseThroughModalWindow() {
		
		// Hai Simple code to navigate between modal. 
		// Used Simple conditional statements for Final Verification (My name is Siva).
		// Steps followed:
		// 1. Invoked browser, 2. Since the object was present in different frame switched to the concern frame using the frame ID.
		// 3. After accessing the frame found the elements for Modal and performed navigation on it.
		
		try {
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(delayTimerSec));
			driver.get(testUrl);
			driver.manage().window().maximize();
			driver.switchTo().frame("result");
			driver.findElement(By.xpath("//button[text()='Show Modal']")).click();
			List<WebElement> elements = driver.findElements(By.cssSelector(".container > div"));
			Integer pageCounter=1;
			for (WebElement element : elements) { 
				b_FinalStatus = element.findElement(By.cssSelector(".modal-title")).getAttribute("innerHTML").matches(String.format("Modal title %s",elements.size()));
				if (b_FinalStatus) {
					System.out.println("Reached the final page.");
					break;
				}	
			    element.findElement(By.xpath(String.format("//button[text()='Launch Modal %s']", ++pageCounter))).click();
			 }	
			Assert.assertTrue(b_FinalStatus, "Reached the 'modal title 4' in given webpage.");
			}
		catch(Exception e) {
			Assert.fail("Test Execution is failed for an exception.");
			System.out.println("Unable to execute the test! Some Exception is occured! Please check!!");
			System.out.println(e);
		};
	}
	@BeforeTest
	public void LoadTestApp() {
		String	propertyFilePath = "C:\\Users\\320043181\\eclipse-workspace\\IntroProject\\src\\resources\\data.properties";
		File file = new File(propertyFilePath); 
		FileInputStream fileInput = null;
		try {
			fileInput = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		Properties testData = new Properties();
		
		//load properties file
		try {
			testData.load(fileInput);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		webdriverURL = testData.getProperty("webdriverURL");
		testUrl = testData.getProperty("testUrl");
		delayTimerSec = Integer.parseInt(testData.getProperty("delayTimerSec"));
		b_FinalStatus = Boolean.parseBoolean(testData.getProperty("b_FinalStatus"));
		
		
		System.setProperty("webdriver.chrome.driver", webdriverURL);	
		driver = new ChromeDriver(); 
	}
	@AfterTest
	public void TestClosure() {
		driver.quit();
	}
}
