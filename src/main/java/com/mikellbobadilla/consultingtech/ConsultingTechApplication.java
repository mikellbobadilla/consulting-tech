package com.mikellbobadilla.consultingtech;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class ConsultingTechApplication {

	public static void main(String[] args) {

		ApplicationContext context = SpringApplication.run(ConsultingTechApplication.class, args);
		PasswordEncoder encoder = context.getBean(PasswordEncoder.class);
		System.out.println(encoder.encode("holamundo")+ " pass");
	}

}
