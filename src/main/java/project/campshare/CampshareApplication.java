package project.campshare;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import project.campshare.config.AppProperties;

@EnableJpaAuditing
@SpringBootApplication
@EnableConfigurationProperties(value = {AppProperties.class})
public class CampshareApplication {

	public static void main(String[] args) {
		SpringApplication.run(CampshareApplication.class, args);
	}

}
