package kr.co.promisemomo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class PromisemomoApplication {

	public static void main(String[] args) {
		SpringApplication.run(PromisemomoApplication.class, args);
	}

}
