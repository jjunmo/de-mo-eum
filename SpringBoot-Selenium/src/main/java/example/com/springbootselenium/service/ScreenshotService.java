package example.com.springbootselenium.service;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

@Service
public class ScreenshotService {


    public void captureScreenshot(String url, String outputPath) {
        WebDriver driver = null;
        try {
            WebDriverManager.chromedriver();
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--headless");  // 헤드리스 모드로 실행

            driver = new ChromeDriver(options);
            driver.get(url);

            // 페이지 로딩을 기다립니다 (필요에 따라 조정)
            Thread.sleep(5000);

            // 스크린샷을 캡처합니다
            byte[] screenshotBytes = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            BufferedImage image = ImageIO.read(new ByteArrayInputStream(screenshotBytes));

            // 이미지를 파일로 저장합니다
            ImageIO.write(image, "png", new File(outputPath));

            System.out.println("Screenshot saved to: " + outputPath);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (driver != null) {
                driver.quit();
            }
        }
    }
}

