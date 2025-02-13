package com.DiagramToDb.LMDToDB;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class LmdToDbApplication {

	public static void main(String[] args) {
		SpringApplication.run(LmdToDbApplication.class, args);
	}

}
