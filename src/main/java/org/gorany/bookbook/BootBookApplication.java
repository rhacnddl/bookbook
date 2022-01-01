package org.gorany.bookbook;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class BootBookApplication {

	public static void main(String[] args) {
		SpringApplication.run(BootBookApplication.class, args);
	}

}
