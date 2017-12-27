package com.gexf;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication
@MapperScan(basePackages = "com.gexf.dataobject.mapper")
@EnableCaching
@CrossOrigin
public class SellApplication {

	public static void main(String[] args) {

		SpringApplication.run(SellApplication.class, args);
	}
}
