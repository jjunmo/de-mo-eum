package example.com.springbootselenium.controller;

import example.com.springbootselenium.service.ScreenshotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ScreenshotController {

    @Autowired
    private ScreenshotService screenshotService;

    @GetMapping("/capture")
    public ResponseEntity<String> captureWebpage(@RequestParam String url) {
        String outputPath = "/Users/jjunmo/pdf/screenshot.png";
        screenshotService.captureScreenshot(url, outputPath);
        return ResponseEntity.ok("Screenshot captured and saved.");
    }
}
