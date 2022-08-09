package com.mikellbobadilla.consultingtech.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;

@Configuration
public class SwaggerConfig {

  @Bean
  public Docket apiInfo(){
    return new Docket(DocumentationType.SWAGGER_2)
            .apiInfo(apiDetails())
            .select()
            .apis(RequestHandlerSelectors.any())
            .paths(PathSelectors.any())
            .build();
  }


  private ApiInfo apiDetails(){
    return new ApiInfo(
            "Consulting Tech",
            "Students allows consulting about the IT",
            "version: 1.0",
            "https://google.com",
            contactDev(),
            "https://google.com",
            "",
            new ArrayList<>()
    );
  }

  private Contact contactDev(){
    return new Contact(
            "sting",
            "https://github.com/mikellbobadilla",
            "bobadilla413@gmail.com");
  }
}
