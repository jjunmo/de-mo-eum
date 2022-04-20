package test.hello;

import DTO.TestDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello(){
        return "hello";
    }

    @GetMapping("/hello/dto")
    public TestDTO helloDto(@RequestParam("name") String name,@RequestParam("password") int password){
        return new TestDTO(name,password);
    }
}
