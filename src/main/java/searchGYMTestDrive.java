import com.gargoylesoftware.htmlunit.javascript.background.JavaScriptExecutor;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.*;
import java.util.List;
import java.util.Properties;


/**
 * Created by albert on 04.11.16.
 */
public class searchGYMTestDrive {

    private static int  listCounter = 0;
    private static int rowCounter = 1;
    //private static String city = "Boca Rotan";
    private static String[] cityArr = {"San Diego","San Francisco", "Portland", "Sacramento", "Las Vegas", "Chicago", "Miami","Boca Rotan"};

    public static void main(String[] args) throws InterruptedException, IOException {

        System.setProperty("webdriver.gecko.driver", "/home/albert/geckodriver");
        //FirefoxBinary binary = new FirefoxBinary(new File("/usr/bin/firefox"));
        //FirefoxProfile profile = new FirefoxProfile( new File("/home/albert/.mozilla/firefox/s4k7s1d0.default"));
        //profile.addExtension(new File("firebug-2.0.18-fx.xpi"));
        //profile.setPreference("extensions.firebug.currentVersion","1.4");


        WebDriver driver = new FirefoxDriver();
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        WebDriverWait wait = new WebDriverWait(driver, 60);


        for (int i = 0; i < cityArr.length; i++) {
            driver.get("https://www.google.ru/maps/");

            String request = "Muay thai GYM " + cityArr[i];

            driver.findElement(By.id("searchboxinput")).sendKeys(request);
            jse.executeScript("arguments[0].click()",
                    driver.findElement(By.id("searchbox-searchbutton")));

            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='section-pagination-container']")));


            while (true) {
                System.out.println("Лист коунтер = " + listCounter);
                if (listCounter == 20) {
                    jse.executeScript("arguments[0].click()",
                            driver.findElement(By.id("section-pagination-button-next")));
                    listCounter = 0;
                } else if (listCounter > 0 && listCounter < 20) {
                    break;
                }
                List<WebElement> rowsCount = driver.findElements(By.xpath("//div[@class='section-result']"));

                for (int i1 = 0; i1 < rowsCount.size(); i1++) {

                    rowsCount = driver.findElements(By.xpath("//div[@class='section-result']"));

                    jse.executeScript("arguments[0].click()",
                            rowsCount.get(i1));

                    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='section-header-title']/h1")));

                    writeToExcel(driver, jse, cityArr[i]);

                    wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='section-pagination-container']")));
                    listCounter++;
                }

            }

            listCounter = 0;
            rowCounter = 1;
        }
    }


    private static void writeToExcel (WebDriver driver, JavascriptExecutor jse, String city) throws IOException {
        rowCounter++;
        File file = new File ("/home/albert/"+"data_" + city + ".xlsx");

        if (!file.exists()) {
            XSSFWorkbook wb = new XSSFWorkbook();
            XSSFSheet sheet = wb.createSheet("Kickboxing");
            XSSFRow writeRow0 = sheet.createRow(0);
            XSSFCell writeCellA0 = writeRow0.createCell(0);
            XSSFCell writeCellB0 = writeRow0.createCell(1);
            XSSFCell writeCellC0 = writeRow0.createCell(2);
            XSSFCell writeCellD0 = writeRow0.createCell(3);
            XSSFCell writeCellE0 = writeRow0.createCell(4);
            XSSFCell writeCellF0 = writeRow0.createCell(5);
            XSSFCell writeCellG0 = writeRow0.createCell(6);

            writeCellA0.setCellValue("Место");
            writeCellB0.setCellValue("Рейтинг");
            writeCellC0.setCellValue("Комментарии");
            writeCellD0.setCellValue("Тип");
            writeCellE0.setCellValue("Сайт");
            writeCellF0.setCellValue("Телефон");
            writeCellG0.setCellValue("Адрес");

            XSSFRow writeRowX = sheet.createRow(rowCounter);
            XSSFCell writeCellAX = writeRowX.createCell(0);
            XSSFCell writeCellBX = writeRowX.createCell(1);
            XSSFCell writeCellCX = writeRowX.createCell(2);
            XSSFCell writeCellDX = writeRowX.createCell(3);
            XSSFCell writeCellEX = writeRowX.createCell(4);
            XSSFCell writeCellFX = writeRowX.createCell(5);
            XSSFCell writeCellGX = writeRowX.createCell(6);

            writeCellAX.setCellValue(driver.findElement(By.xpath("//div[@class='section-header-title']/h1")).getText());

            List<WebElement> ratingLWE = driver.findElements(By.xpath("//div[@class='section-rating']/div[1]/span[1]/span/span"));
            if (ratingLWE.size() != 0) {
                writeCellBX.setCellValue(driver.findElement(By.xpath("//div[@class='section-rating']/div[1]/span[1]/span/span")).getText());
                ratingLWE.clear();
            } else {
                writeCellBX.setCellValue("Рейтинга нет");
                ratingLWE.clear();
            }


            List<WebElement> commentsLWE = driver.findElements(By.xpath("//div[@class='section-rating']/div[1]/span[2]/ul/li[1]/span/span[1]/button"));
            if (commentsLWE.size() != 0) {
                writeCellCX.setCellValue(driver.findElement(By.xpath("//div[@class='section-rating']/div[1]/span[2]/ul/li[1]/span/span[1]/button")).getText());
                commentsLWE.clear();
            } else {
                writeCellCX.setCellValue("Комментариев нет");
                commentsLWE.clear();
            }

            writeCellDX.setCellValue(driver.findElement(By.xpath("(//button[@class='widget-pane-link'])[3]")).getText());

            List<WebElement> siteLWE = driver.findElements(By.xpath("(//a[@class='widget-pane-link'])[6]"));
            if (siteLWE.size() != 0) {
                writeCellEX.setCellValue(driver.findElement(By.xpath("(//a[@class='widget-pane-link'])[6]")).getAttribute("data-attribution-url"));
                siteLWE.clear();
            } else {
                writeCellEX.setCellValue("Сайта нет");
                siteLWE.clear();
            }


            writeCellFX.setCellValue(driver.findElement(By.xpath("(//button[@class='widget-pane-link'])[7]")).getText());
            writeCellGX.setCellValue(driver.findElement(By.xpath("//div[@class='section-info']/div/span[3]")).getText());

            String fname = "data_" + city + ".xlsx";
            FileOutputStream out = new FileOutputStream(new File("/home/albert/"+fname));
            wb.write(out);
            out.close();

            wb.close();

            jse.executeScript("arguments[0].click()",
                    driver.findElement(By.xpath("//button[@class='section-back-to-list-button blue-link noprint']")));
        } else {
            FileInputStream fis = new FileInputStream(file);
            XSSFWorkbook wb = new XSSFWorkbook(fis);
            XSSFSheet sheet = wb.getSheet("Kickboxing");


            XSSFRow writeRowX = sheet.createRow(rowCounter);
            XSSFCell writeCellAX = writeRowX.createCell(0);
            XSSFCell writeCellBX = writeRowX.createCell(1);
            XSSFCell writeCellCX = writeRowX.createCell(2);
            XSSFCell writeCellDX = writeRowX.createCell(3);
            XSSFCell writeCellEX = writeRowX.createCell(4);
            XSSFCell writeCellFX = writeRowX.createCell(5);
            XSSFCell writeCellGX = writeRowX.createCell(6);

            writeCellAX.setCellValue(driver.findElement(By.xpath("//div[@class='section-header-title']/h1")).getText());

            List<WebElement> ratingLWE = driver.findElements(By.xpath("//div[@class='section-rating']/div[1]/span[1]/span/span"));
            if (ratingLWE.size() != 0) {
                writeCellBX.setCellValue(driver.findElement(By.xpath("//div[@class='section-rating']/div[1]/span[1]/span/span")).getText());
                ratingLWE.clear();
            } else {
                writeCellBX.setCellValue("Рейтинга нет");
                ratingLWE.clear();
            }


            List<WebElement> commentsLWE = driver.findElements(By.xpath("//div[@class='section-rating']/div[1]/span[2]/ul/li[1]/span/span[1]/button"));
            if (commentsLWE.size() != 0) {
                writeCellCX.setCellValue(driver.findElement(By.xpath("//div[@class='section-rating']/div[1]/span[2]/ul/li[1]/span/span[1]/button")).getText());
                commentsLWE.clear();
            } else {
                writeCellCX.setCellValue("Комментариев нет");
                commentsLWE.clear();
            }

            writeCellDX.setCellValue(driver.findElement(By.xpath("(//button[@class='widget-pane-link'])[3]")).getText());

            List<WebElement> siteLWE = driver.findElements(By.xpath("(//a[@class='widget-pane-link'])[6]"));
            if (siteLWE.size() != 0) {
                writeCellEX.setCellValue(driver.findElement(By.xpath("(//a[@class='widget-pane-link'])[6]")).getAttribute("data-attribution-url"));
                siteLWE.clear();
            } else {
                writeCellEX.setCellValue("Сайта нет");
                siteLWE.clear();
            }


            writeCellFX.setCellValue(driver.findElement(By.xpath("(//button[@class='widget-pane-link'])[7]")).getText());
            writeCellGX.setCellValue(driver.findElement(By.xpath("//div[@class='section-info']/div/span[3]")).getText());

            String fname = "data_" + city + ".xlsx";
            FileOutputStream out = new FileOutputStream(new File("/home/albert/"+fname));
            wb.write(out);
            out.close();

            wb.close();

            jse.executeScript("arguments[0].click()",
                    driver.findElement(By.xpath("//button[@class='section-back-to-list-button blue-link noprint']")));

        }








    }


}

 /*
        List<WebElement> buttonsLWE = driver.findElements(By.xpath("//a[@class='widget-pane-link']"));
        for (int i = 0; i < buttonsLWE.size(); i++) {
            System.out.println(i+ buttonsLWE.get(i).getText());
        }
*/