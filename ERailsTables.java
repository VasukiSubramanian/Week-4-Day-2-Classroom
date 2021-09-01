package week4.day2.classroom;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class ERailsTables {

	public static void main(String[] args) {

		WebDriverManager.chromedriver().setup();
		ChromeDriver driver = new ChromeDriver();
		driver.get("https://erail.in/");
		System.out.println(driver.getTitle());
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);

		// 1. Enter the from station as ms
		driver.findElement(By.id("txtStationFrom")).clear();
		driver.findElement(By.id("txtStationFrom")).sendKeys("ms", Keys.ENTER);

		// 2. Enter the To stationa as mdu
		driver.findElement(By.id("txtStationTo")).clear();
		driver.findElement(By.id("txtStationTo")).sendKeys("mdu", Keys.ENTER);

		// 3. Uncheck the Sort By date
		driver.findElement(By.id("chkSelectDateOnly")).click();

		// 4. Count the number of trains available
		WebElement webTable = driver.findElement(By.xpath("//table[@class=\"DataTable TrainList TrainListHeader\"]"));
		List<WebElement> trainRows = webTable.findElements(By.tagName("tr"));
		System.out.println("Number of trains available are : " + trainRows.size());

		// 5. Get the names of all the train and store it in a list
		List<String> trainNames = new ArrayList<String>();
		for (int i = 0; i < trainRows.size(); i++) {
			WebElement singleRow = trainRows.get(i);
			List<WebElement> singleRowData = singleRow.findElements(By.tagName("td"));
			String trainName = singleRowData.get(1).getText();
			trainNames.add(trainName);
		}

		// 6. Sort the train names use Collections.sort()
		Collections.sort(trainNames);
		System.out.println("Sorted Train Names : ");
		for (String trainName : trainNames) {
			System.out.println(trainName);
		}
	}

}
