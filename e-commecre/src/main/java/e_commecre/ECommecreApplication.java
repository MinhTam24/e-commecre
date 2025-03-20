package e_commecre;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "e_commecre.repository")
public class ECommecreApplication {

	public static void main(String[] args) {
		SpringApplication.run(ECommecreApplication.class, args);
	}

}
