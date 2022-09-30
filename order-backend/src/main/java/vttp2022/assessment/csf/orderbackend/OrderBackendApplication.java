package vttp2022.assessment.csf.orderbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication()
public class OrderBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderBackendApplication.class, args);
	}

}
