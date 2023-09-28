package DCS.DCSspring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import static DCS.DCSspring.EmailVerification.sendVerificationEmail;

@SpringBootApplication
public class DcsSpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(DcsSpringApplication.class, args);
		sendVerificationEmail("ttotti0903@naver.com","123456");
	}

}

