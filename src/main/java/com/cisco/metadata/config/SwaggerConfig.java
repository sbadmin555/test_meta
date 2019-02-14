package com.cisco.metadata.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    private String swaggerTitle = "Camera Metadata";
    private String swaggerDescription = "Camera Metadata APIs";

    @Bean
    public Docket productApi() {

        List<Parameter> additionalHeaderList = new ArrayList();

        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("Public Api")
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.regex("/api/v1.*"))
                .build()
                .apiInfo(metaData());
    }

    private ApiInfo metaData() {
        ApiInfo apiInfo = new ApiInfo(
                swaggerTitle,
                swaggerDescription,
                "1.0",
                "Terms of service",
                new Contact("prinaray@cisco.com", null, null),
                "",
                "");
        return apiInfo;
    }

    private ApiKey apiKey() {
        return new ApiKey("Authorization", "Authorization", "header");
    }
}

