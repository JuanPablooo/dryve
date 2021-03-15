package br.com.drive;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class DriveApplication {

	public static void main(String[] args) {
		SpringApplication.run(DriveApplication.class, args);
	}

}
