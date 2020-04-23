package com.fss.cms.chatbot.assist.prototype;

import java.util.Collections;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("com.fss.cms.chatbot.assist.prototype.controller"))
				.paths(PathSelectors.any()).build().apiInfo(apiInfo());
	}

	private ApiInfo apiInfo() {
		return new ApiInfo("CMS-CHAT-BOT-ASSIST", "API assist the users of CMS Applications, mainly it helps new users of the application to understand and help them to do their daily operations", "1.0", "TermsOfServiceUrl",
				new Contact("FSS", "@FSS", "jonnadulapavanapooja@fss.co.in"), "License of API", "API license URL",
				Collections.emptyList());
	}

}
