import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import junit.framework.AssertionFailedError;
import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import sun.security.krb5.internal.crypto.HmacMd5ArcFourCksumType;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class AutoTraderTests {

    private JavascriptExecutor js;
    private static ExtentReports report;
    private ExtentTest test;
    private SpreadSheetReader spreadSheetReader;
    private WebDriver webDriver;

    private List<String> rowString;

    private Homepage homeSearch;
    private SignupSignin signupSignin;

    @BeforeClass
    public static void init() {

        report = new ExtentReports();
        String fileName = "AutoTraderTestReport" + ".html";
        String filePath = System.getProperty("user.dir")
                + File.separatorChar + fileName;
        report.attachReporter(new ExtentHtmlReporter(filePath));
    }

    @Before
    public void setUp() {

        webDriver = new ChromeDriver();
        spreadSheetReader = new SpreadSheetReader((System.getProperty("user.dir") + File.separatorChar + "properties.xlsx"));

        rowString = new ArrayList<String>();
        rowString = spreadSheetReader.readRow(0, "Sheet1");

    }

    @After
    public void tearDown() {
        // webDriver.quit();
    }

    @AfterClass
    public static void cleanUp() {
        report.flush();
    }


    @Test
    public void testQuickSearchOnHomepage(){
        test = report.createTest("testQuickSearchOnHomepage");
        test.log(Status.INFO, "Test is Startting ");


        homeSearch = PageFactory.initElements(webDriver, Homepage.class);

        webDriver.manage().window().maximize();
        webDriver.navigate().to(rowString.get(1));

        rowString = spreadSheetReader.readRow(2, "Sheet1");
        homeSearch.setPostcode(rowString.get(1));


        WebElement selectDistance = webDriver.findElement(By.cssSelector("#radius"));
        Select dropdown = new Select(selectDistance);
        dropdown.selectByIndex(3);

        homeSearch.setNewCarsOnly();
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        rowString = spreadSheetReader.readRow(3, "Sheet1");
        WebElement selectMake = webDriver.findElement(By.cssSelector("#searchVehiclesMake"));
        Select makeOption = new Select(selectMake);
        makeOption.selectByValue(rowString.get(1));

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        WebElement selectModel = webDriver.findElement(By.cssSelector("#searchVehiclesModel"));
        Select modelOption = new Select(selectModel);
        modelOption.selectByValue(rowString.get(2));

        homeSearch.searchCar();
        rowString = spreadSheetReader.readRow(4, "Sheet1");

        try {
            Assert.assertEquals("Search did not work",rowString.get(1) , webDriver.getCurrentUrl());
            test.log(Status.PASS, "Successfully viewed search results");
        } catch (ComparisonFailure a) {
            test.log(Status.FAIL, "search was not made");
        }

    }

    @Test
    public void testSignUp(){
        test = report.createTest("testSignUp");
        test.log(Status.INFO, "Test is Startting ");

        homeSearch = PageFactory.initElements(webDriver, Homepage.class);
        signupSignin = PageFactory.initElements(webDriver, SignupSignin.class);

        webDriver.manage().window().maximize();
        webDriver.navigate().to(rowString.get(1));

        homeSearch.signinButton();
        rowString = spreadSheetReader.readRow(6, "Sheet1");
        signupSignin.signUp(rowString.get(1),rowString.get(3));
        test.log(Status.DEBUG, "username and password from spreadsheet: " + rowString.get(1) + " " + rowString.get(3));

        rowString = spreadSheetReader.readRow(7, "Sheet1");

        try {
            Assert.assertEquals("Sign up completed",rowString.get(1) , webDriver.getCurrentUrl());
            test.log(Status.PASS, "Successfully signed up");
        } catch (ComparisonFailure a) {
            test.log(Status.FAIL, "sign up failed");
        }
    }

    @Test
    public void TestSignIn(){
        test = report.createTest("TestSignIn");
        test.log(Status.INFO, "Test is Startting ");

        homeSearch = PageFactory.initElements(webDriver, Homepage.class);
        signupSignin = PageFactory.initElements(webDriver, SignupSignin.class);

        webDriver.manage().window().maximize();
        webDriver.navigate().to(rowString.get(1));

        homeSearch.signinButton();
        rowString = spreadSheetReader.readRow(6, "Sheet1");
        signupSignin.signIn(rowString.get(1),rowString.get(3));
        test.log(Status.DEBUG, "username and password from spreadsheet: " + rowString.get(1) + " " + rowString.get(3));

        rowString = spreadSheetReader.readRow(7, "Sheet1");

        try {
            Assert.assertEquals("Sign in completed",rowString.get(1) , webDriver.getCurrentUrl());
            test.log(Status.PASS, "Successfully signed in");
        } catch (ComparisonFailure a) {
            test.log(Status.FAIL, "sign in failed");
            test.log(Status.INFO, "account may exist already with the given email address");

        }
    }

    @Test
    public void testReviewcar(){
        test = report.createTest("testReviewcar");
        test.log(Status.INFO, "Test is Startting ");

        homeSearch = PageFactory.initElements(webDriver, Homepage.class);

        webDriver.manage().window().maximize();
        webDriver.navigate().to(rowString.get(1));

        homeSearch.carOwnerReview();

        rowString = spreadSheetReader.readRow(3, "Sheet1");
        WebElement selectMake = webDriver.findElement(By.cssSelector("#reviewTypeOwnerMake"));
        Select makeOption = new Select(selectMake);
        makeOption.selectByValue(rowString.get(1));

        WebElement selectModel = webDriver.findElement(By.cssSelector("#reviewTypeOwnerModel"));
        Select modelOption = new Select(selectModel);
        modelOption.selectByValue(rowString.get(2));

        WebElement selectBodyType = webDriver.findElement(By.cssSelector("#reviewTypeOwnerBodytype"));
        Select bodyTypeOption = new Select(selectBodyType);
        bodyTypeOption.selectByValue(rowString.get(3));
    }
}
