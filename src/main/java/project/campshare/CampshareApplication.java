package project.campshare;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class CampshareApplication {

	public static void main(String[] args) {
		SpringApplication.run(CampshareApplication.class, args);
	}

}
