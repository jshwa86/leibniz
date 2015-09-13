package com.joshbailey.ui;

import java.awt.AWTException;
import java.awt.HeadlessException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.junit.Test;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver.Window;
import org.openqa.selenium.chrome.ChromeDriver;

import com.joshbailey.leibniz.tests.ServerDependentTest;
import com.joshbailey.springmvc.liebniz.servers.StandardServer;

import io.github.bonigarcia.wdm.ChromeDriverManager;

public class ChromeSeleniumTest extends ServerDependentTest{

	public ChromeSeleniumTest(StandardServer leibnitzServer){
	}
	
	@Test
	public void doTest() throws InterruptedException, IOException, HeadlessException, AWTException{
//		System.setProperty("webdriver.chrome.driver", "C:\\dev\\chromedriver-2.19\\chromedriver.exe");
		ChromeDriverManager.setup(); 
		
		ChromeDriver driver = new ChromeDriver();
		
		driver.get("http://localhost:8080/leibnitz-example-springmvc-app-0.0.1-SNAPSHOT/sarah");
		Thread.sleep(5000);
//		File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
//		FileUtils.copyFile(scrFile, new File("screenshot.png"));
		
		Window w = driver.manage().window();
		Dimension d = w.getSize();
		
		BufferedImage image = new Robot().createScreenCapture(new Rectangle(w.getPosition().getX(),w.getPosition().getY(),d.getWidth(),d.getHeight()));
        ImageIO.write(image, "jpg", new File("screenshot.jpg"));
		
		driver.quit();
	}
	
}
