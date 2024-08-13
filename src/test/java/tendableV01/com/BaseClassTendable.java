package tendableV01.com;

import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class BaseClassTendable {
    WebDriver driver;
    String docFilePath = "BugReport.docx";

    @BeforeClass
    public void setup() {
        // Setup ChromeDriver
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\lenovo\\Desktop\\Drivers\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
       
    }

    @Test
    public void testMenuAccessibilityAndRequestDemoButton() {
        String[] menuNames = {"Home", "Our Story", "Our Solution", "Why Tendable"};
        String[] urls = {"/", "/our-story", "/our-solution", "/why-tendable"};

        for (int i = 0; i < menuNames.length; i++) {
            try {
                // Navigate to each menu page
                driver.get("https://www.tendable.com" + urls[i]);

                // Verify that the menu is present and active
                WebElement menu = driver.findElement(By.xpath("//nav//a[contains(text(), '" + menuNames[i] + "')]"));
                if (menu.isDisplayed()) {
                    System.out.println(menuNames[i] + " menu is displayed.");
                    Assert.assertTrue(menu.isDisplayed(), menuNames[i] + " menu is displayed.");
                } else {
                    System.out.println(menuNames[i] + " menu is not displayed.");
                    Assert.assertFalse(menu.isDisplayed(), menuNames[i] + " menu is not displayed.");
                }

                // Verify that the "Request a Demo" button is present and active
                WebElement requestDemoButton = driver.findElement(By.xpath("//a[contains(text(), 'Request a Demo')]"));
                if (requestDemoButton.isDisplayed() && requestDemoButton.isEnabled()) {
                    System.out.println("'Request a Demo' button is present and active on " + menuNames[i] + " page.");
                    Assert.assertTrue(requestDemoButton.isDisplayed() && requestDemoButton.isEnabled(), "'Request a Demo' button is present or active on " + menuNames[i] + " page.");
                } else {
                    System.out.println("'Request a Demo' button is not present or active on " + menuNames[i] + " page.");
                    Assert.assertFalse(requestDemoButton.isDisplayed() && requestDemoButton.isEnabled(), "'Request a Demo' button is not present or active on " + menuNames[i] + " page.");
                }

            } catch (Exception e) {
                System.out.println("An error occurred while verifying the " + menuNames[i] + " page: " + e.getMessage());
            }
        }
    }

    
    @Test
    public void testMarketingContactFormValidation() {
        try {
            // Navigate to the Contact Us page
            driver.get("https://www.tendable.com/contact-us");
            
            driver.findElement(By.xpath("/html/body/div[3]/div/div/div[2]/div/div/div[1]/div/div[2]/div[2]/button")).click();

            // Fill out the form excluding the "Message" field
            driver.findElement(By.xpath("//*[@id=\"form-input-fullName\"]")).sendKeys("Dhiraj Waghmare");
            driver.findElement(By.xpath("//*[@id=\"form-input-organisationName\"]")).sendKeys("Sagitta");
           
            driver.findElement(By.xpath("//*[@id=\"form-input-cellPhone\"]")).sendKeys("7558293116");
            driver.findElement(By.xpath("//*[@id=\"form-input-email\"]")).sendKeys("dhirajmwaghmare1990@gmail.com");
            Select drp=new Select(driver.findElement(By.xpath("//*[@id=\"form-input-jobRole\"]")));
            drp.selectByVisibleText("Other");
            Thread.sleep(10000);
            driver.findElement(By.xpath("//*[@id=\"form-input-consentAgreed-0\"]")).click();
            Thread.sleep(10000);
            driver.findElement(By.xpath("//*[@id=\"contactMarketingPipedrive-163701\"]/div[10]/div/button")).click();
            
            
            Thread.sleep(10000);


            // Verify if an error message is displayed for the "Message" field
            boolean isErrorDisplayed = driver.findElements(By.xpath("//*[@id=\"contactMarketingPipedrive-163701\"]/div[1]/p")).size() > 0;

            if (isErrorDisplayed) {
                System.out.println("Test PASSED: Error message displayed.");
                Assert.assertTrue(true, "Error message is displayed as expected.");
            } else {
                System.out.println("Test FAILED: Error message not displayed.");
                Assert.fail("Error message not displayed when 'Message' field is left blank.");
            }

        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
           Assert.fail("Test failed due to an unexpected error.");
           draftBugReport();
        }
    }
    
    private void draftBugReport() {
        // This method can include code to create a bug report document
        // Example: Save a Word document with details about the issue
        System.out.println("Drafting bug report. Please check GitHub for the uploaded document.");
        // Code to create and upload the document goes here (manual steps required for document creation)
    }
    
    @AfterMethod
    public void tearDown(ITestResult result) {
        // Generate a Word document if the test fails
        if (ITestResult.FAILURE == result.getStatus()) {
            createWordDocument(result.getThrowable().getMessage());
        }
        // Close the browser
        if (driver != null) {
            driver.quit();
        }
    }
    private void createWordDocument(String errorMessage) {
        try (XWPFDocument document = new XWPFDocument()) {
            // Create a paragraph
            XWPFParagraph paragraph = document.createParagraph();
            XWPFRun run = paragraph.createRun();
            run.setText("Test Failed with the following error:");
            run.addBreak();
            run.setText(errorMessage);

            // Write the document to a file
            try (FileOutputStream out = new FileOutputStream(docFilePath)) {
                document.write(out);
            }
            System.out.println("Word document created: " + docFilePath);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

