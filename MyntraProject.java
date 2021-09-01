package week4.day2.classroom;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import io.github.bonigarcia.wdm.WebDriverManager;

public class MyntraProject {

	public static void main(String[] args) throws InterruptedException, IOException {

		WebDriverManager.chromedriver().setup();
		ChromeDriver driver = new ChromeDriver();
		// 1) Open https://www.myntra.com/
		driver.get("https://www.myntra.com/");
		// Thread.sleep(4000);

		System.out.println(driver.getTitle());
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

		// 2) Mouse hover on MeN
		WebElement men = driver.findElement(By.xpath("//a[text()='Men']"));
		Actions builder = new Actions(driver);
		builder.moveToElement(men).perform();
		// Thread.sleep(2000);

		// 3) Click Jackets
		driver.findElement(By.xpath("//a[text()='Jackets']")).click();

		// 4) Find the total count of item
		String total = driver.findElement(By.xpath("//span[@class='title-count']")).getText();
		String replaceTotal = total.replaceAll("[^0-9]", "");
		int intTotal = Integer.parseInt(replaceTotal);
		System.out.println("Total count of items : " + intTotal);

		// 5) Validate the sum of categories count matches
		String jackets = driver.findElement(By.xpath("//span[@class='categories-num']")).getText();
		String intJackets = jackets.replaceAll("[^0-9]", "");
		int jacketsCount = Integer.parseInt(intJackets);
		String rainJackets = driver.findElement(By.xpath("(//span[@class='categories-num'])[2]")).getText();
		String intRainJackets = rainJackets.replaceAll("[^0-9]", "");
		int rainJacketsCount = Integer.parseInt(intRainJackets);
		int sum = jacketsCount + rainJacketsCount;
		if (sum == intTotal)
			System.out.println(
					"Total items count and sum of categories " + jacketsCount + " + " + rainJacketsCount + " is same");
		else
			System.out.println("Total items count and sum of categories " + jacketsCount + " + " + rainJacketsCount
					+ " is not same");

		// 6) Check jackets
		driver.findElement(By.xpath("//div[@class='common-checkboxIndicator']")).click();

		// 7) Click + More option under BRAND
		driver.findElement(By.xpath("//div[@class='brand-more']")).click();

		// 8) Type Duke and click checkbox
		driver.findElement(By.className("FilterDirectory-searchInput")).sendKeys("Duke");
		driver.findElement(By.xpath("//input[@value='Duke']/following-sibling::div")).click();

		// 9) Close the pop-up x
		driver.findElement(By.xpath("//span[@class='myntraweb-sprite FilterDirectory-close sprites-remove']")).click();
		Thread.sleep(1000);

		// 10) Confirm all the Coats are of brand Duke
		boolean flag = true;
		List<WebElement> brand = driver.findElements(By.className("product-brand"));
		for (WebElement webElement : brand) {
			String text = webElement.getText();
			if (!text.equalsIgnoreCase("Duke")) {
				flag = false;
				break;
			}
		}
		if (flag)
			System.out.println("All the coats are of brand Duke");
		else
			System.out.println("All the coats are not of brand Duke");

		// 11) Sort by Better Discount
		WebElement sort = driver.findElement(By.className("sort-sortBy"));
		// Actions builder = new Actions(driver);
		builder.moveToElement(sort).perform();
		driver.findElement(By.xpath("(//label[@class='sort-label '])[3]")).click();

		Thread.sleep(1000);

		// 12) Find the price of first displayed item
		String price = driver.findElement(By.xpath("//span[@class='product-discountedPrice']")).getText();
		System.out.println("Price of first displayed item is : " + price);

		// 13) Click on the first product
		driver.findElement(By.xpath("//span[@class='product-discountedPrice']")).click();

		// Handle new window
		Set<String> HandlesSet = driver.getWindowHandles();
		List<String> HandlesList = new ArrayList<String>(HandlesSet);
		// System.out.println(HandlesList.size());
		driver.switchTo().window(HandlesList.get(1));

		// 14) Take a screen shot
		File source = driver.getScreenshotAs(OutputType.FILE);
		File destination = new File("./snaps/item1.png");
		FileUtils.copyFile(source, destination);
		// Thread.sleep(2000);

		// Get to main window
		driver.switchTo().window(HandlesList.get(0));
		// Thread.sleep(4000);

		// 15) Click on WishList Now
		driver.findElement(By.xpath("//span[@class=\"myntraweb-sprite desktop-iconWishlist sprites-headerWishlist\"]"))
				.click();

		// 16) Close Browser
		driver.close();
	}

}
