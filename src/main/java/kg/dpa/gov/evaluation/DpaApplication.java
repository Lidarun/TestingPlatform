package kg.dpa.gov.evaluation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class DpaApplication {

	public static void main(String[] args) {
		SpringApplication.run(DpaApplication.class, args);
	}

}
