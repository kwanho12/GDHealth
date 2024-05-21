package com.tree.gdhealth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan
@SpringBootApplication
public class GdhealthApplication {

	public static void main(String[] args) {
		SpringApplication.run(GdhealthApplication.class, args);
	}

}
