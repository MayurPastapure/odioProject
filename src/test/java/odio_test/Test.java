package odio_test;

import static org.testng.Assert.assertEquals;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class Test {
	WebDriver driver = new ChromeDriver();
	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
	
	public void browserLaunch() {
		driver.get("http://65.108.148.94/login");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
	}
	
	public void login() {
		driver.findElement(By.id("inputEmail")).sendKeys("admin@insurancedekho.com");
		driver.findElement(By.id("inputChoosePassword")).sendKeys("password");
		driver.findElement(By.xpath("//*[@type='submit']")).click();
		
		WebElement card = driver.findElement(By.xpath("//*[@class='mb-1 text-dark font-14 carusal-data']"));
		wait.until(ExpectedConditions.visibilityOf(card));
	}
	
	public void browserClose() {
		driver.quit();
	}
	
	public void dashboardFilter() {
		WebElement filterSwitch = driver.findElement(By.xpath("//*[@class='bx bx-filter-alt']"));
		wait.until(ExpectedConditions.elementToBeClickable(filterSwitch)).click();
		
//		WebElement momentBktDrop = driver.findElement(By.xpath("(//*[@name='role'])[1]"));
//		wait.until(ExpectedConditions.visibilityOf(momentBktDrop));
//		Select selMBkt = new Select(momentBktDrop);
////*****Provide specific Moment Bucket filter*****
//		selMBkt.selectByVisibleText("NexusBucket");
		
//		WebElement coeDrop = driver.findElement(By.xpath("//*[@name='role' and @placeholder ='Select COE']"));
//		wait.until(ExpectedConditions.visibilityOf(coeDrop));
//		Select selCOE = new Select(coeDrop);
////*****Provide specific COE filter*****
//		selCOE.selectByVisibleText("New York");
		
		WebElement selectDateDrop = driver.findElement(By.xpath("//*[@name='selectDate']"));
		wait.until(ExpectedConditions.visibilityOf(selectDateDrop));
		Select selDate = new Select(selectDateDrop);
//*****Provide specific date filter*****
		selDate.selectByVisibleText(" Last Year ");
		
		WebElement applyBtn = driver.findElement(By.xpath("//*[@type='submit']"));
		wait.until(ExpectedConditions.elementToBeClickable(applyBtn)).click();
		
		WebElement card = driver.findElement(By.xpath("//*[@class='mb-1 text-dark font-14 carusal-data']"));
		wait.until(ExpectedConditions.visibilityOf(card));
		
	}
	
	public void conversationsPage() throws InterruptedException  {
		WebElement logoIcon = driver.findElement(By.xpath("//*[@class='logo-icon']"));
		logoIcon.click();
		
		WebElement conversationsDrop = driver.findElement(By.xpath("//*[text()='Conversations']"));
		conversationsDrop.click();
		WebElement voice = driver.findElement(By.xpath("//*[text()='Voice']"));
		voice.click();
		
		WebElement groupCardContainer = driver.findElement(By.xpath("//*[@class='card-container']"));
		wait.until(ExpectedConditions.visibilityOf(groupCardContainer));
		
		WebElement groupView = driver.findElement(By.xpath("//*[@value='Group']"));
		groupView.click();
		Thread.sleep(500);
		

	}
	
	public void conversationPagination() throws InterruptedException {
	    JavascriptExecutor js = (JavascriptExecutor) driver;

	    WebElement pagination = driver.findElement(By.xpath("//*[@class='pagination']"));
	    wait.until(ExpectedConditions.elementToBeClickable(pagination));
	    js.executeScript("window.scrollBy(0, 1000);");
	    Thread.sleep(1000);

	    List<Integer> callsCount = new ArrayList<>();

	    while (true) {
	        List<WebElement> callCards = driver.findElements(By.xpath("//*[@class='salesCallCard card-body']"));
	        if (!callCards.isEmpty()) {
	            callsCount.add(callCards.size());
	        }

	        try {
	            WebElement nextPageBtn = driver.findElement(By.xpath("//*[@aria-label='Next page']"));

	            if (!nextPageBtn.isDisplayed() || !nextPageBtn.isEnabled()) {
	                System.out.println("Next button not active. Reached last page.");
	                break;
	            }

	            js.executeScript("arguments[0].scrollIntoView(true);", nextPageBtn);
	            Thread.sleep(500); 

	            try {
	                nextPageBtn.click();
	            } 
	            catch (ElementClickInterceptedException e) {
	                System.out.println("Next button is not clickable. Probably reached last page.");
	                break;
	            }

	            wait.until(ExpectedConditions.stalenessOf(callCards.get(0))); 
	        } catch (NoSuchElementException e) {
	            System.out.println("Next button not found. Stopping pagination.");
	            break;
	        }
	    }

	    System.out.println("Total call counts per page: " + callsCount);

	    int totalCalls = callsCount.stream().mapToInt(Integer::intValue).sum();
	    System.out.println("Total number of calls: " + totalCalls);
	}
	
	public void conversationSpecificPage() throws InterruptedException {
		 JavascriptExecutor js = (JavascriptExecutor) driver;

		    WebElement goToPage = driver.findElement(By.xpath("//*[@placeholder='Go to page']"));
		    wait.until(ExpectedConditions.elementToBeClickable(goToPage));
		    js.executeScript("arguments[0].scrollIntoView();", goToPage);
		    Thread.sleep(500);
		    
//*****Provide specific page number*****
		    goToPage.sendKeys("5");  
		    WebElement goBtn = driver.findElement(By.xpath("//*[@type='submit' and @class ='groupCall']"));
		    wait.until(ExpectedConditions.elementToBeClickable(goBtn));
		    goBtn.click();
		    
		    List<WebElement> callCards = driver.findElements(By.xpath("//*[@class='salesCallCard card-body']"));
		    wait.until(ExpectedConditions.visibilityOfAllElements(callCards));
		    
		    js.executeScript("window.scrollTo(0,0);");
		    
	}
	
	public void searchCallByFileName() {
		WebElement agentView = driver.findElement(By.xpath("//*[@value='Agent']"));
		wait.until(ExpectedConditions.elementToBeClickable(agentView));
		agentView.click();
		WebElement agentCardBody = driver.findElement(By.xpath("//*[@class='salesCallCard card-body']"));
		wait.until(ExpectedConditions.visibilityOf(agentCardBody));
		
		WebElement filterSwitch = driver.findElement(By.xpath("//*[@class='bx bx-filter-alt']"));
		wait.until(ExpectedConditions.elementToBeClickable(filterSwitch)).click();
		
		WebElement fileNameIn = driver.findElement(By.xpath("//*[@name='file']"));
		wait.until(ExpectedConditions.visibilityOf(fileNameIn));
//*****Provide specific file name*****		
		fileNameIn.sendKeys("20240102-103337_77352587_90002285_HLTH_NEW");
		
		WebElement ApplyBtn = driver.findElement(By.xpath("//*[@type='submit' and @class='btn px-4 lh-base update-btn']"));
		wait.until(ExpectedConditions.elementToBeClickable(ApplyBtn)).click();
		
		WebElement agentCard = driver.findElement(By.xpath("//*[@class='card']"));
		wait.until(ExpectedConditions.visibilityOf(agentCard));
		WebElement viewCallBtn = driver.findElement(By.xpath("//*[text()='View Call']"));
		wait.until(ExpectedConditions.elementToBeClickable(viewCallBtn)).click();
		
		WebElement moreInfoBtn = driver.findElement(By.xpath("//*[text()='More Info.']"));
		wait.until(ExpectedConditions.elementToBeClickable(moreInfoBtn)).click();
		
		WebElement fileName = driver.findElement(By.xpath("//ul[@class='ps-0']//span"));
		String FileName = fileName.getText();
		System.out.println(FileName);
//*****Provide specific file name as above*****		
		String ExpectedFileName = "20240102-103337_77352587_90002285_HLTH_NEW";
		
		Assert.assertTrue(
		        FileName.contains(ExpectedFileName),
		        "*** " + FileName + " does not contain " + ExpectedFileName + " ***");	
		
		WebElement moreInfoCloseBtn = driver.findElement(By.xpath("//button[@class='btn-close']"));
		wait.until(ExpectedConditions.elementToBeClickable(moreInfoCloseBtn)).click();
		
	}


	
	public static void main(String[] args) throws InterruptedException {
		Test obj = new Test();
		obj.browserLaunch();
		obj.login();
		obj.dashboardFilter();
		obj.conversationsPage();
		//obj.conversationPagination();
		//obj.conversationSpecificPage();
		obj.searchCallByFileName();
		
		//obj.browserClose();
	}
	
	
	
	


}
