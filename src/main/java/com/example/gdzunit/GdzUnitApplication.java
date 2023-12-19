package com.example.gdzunit;

import com.example.gdzunit.Controllers.UploadController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class GdzUnitApplication {

	public static void main(String[] args) {
		SpringApplication.run(GdzUnitApplication.class, args);
	}

}
