package com.ncone.newsparserserver;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
// Enable scheduling
public class NewsParserServerApplication  {
	public static void main(String[] args) {

		SpringApplication.run(NewsParserServerApplication.class, args);
	}


}
