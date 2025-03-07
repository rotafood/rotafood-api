package br.com.rotafood.api;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.Instant;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class ApiApplication {

	public static void main(String[] args) throws IOException, URISyntaxException {
		SpringApplication.run(ApiApplication.class, args);
		System.out.println("\n\n\n Aplica\u00e7\u00e3o dispon\u00edvel  em: \u001b[34m http://localhost:8080/swagger-ui.html \u001b[0m");
        System.out.println("Timestamp UTC: " + Instant.now());
	}

	// @Bean
    // CommandLineRunner sendTestSms(ApplicationContext ctx) {
    //     return args -> {
    //         SmsService smsService = ctx.getBean(SmsService.class);
    //         String testNumber = "+5519982285952"; 
    //         String message = "🚀 Oi amor, tudo bem?";
    //         smsService.sendSms(testNumber, message);
    //     };
    // }

}
  