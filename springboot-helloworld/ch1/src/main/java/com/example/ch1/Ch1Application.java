package com.example.ch1;

import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class Ch1Application {

	public static void main(String[] args) {


		// 关闭banner
		SpringApplicationBuilder b = new SpringApplicationBuilder(Ch1Application.class);
		b.profiles("dev");
		b.bannerMode(Banner.Mode.OFF).run(args);

		//SpringApplication.run(Ch1Application.class, args);
	}

}
