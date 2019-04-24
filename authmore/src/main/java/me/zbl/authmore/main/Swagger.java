package me.zbl.authmore.main;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

/**
 * @author ZHENG BAO LE
 * @since 2019-04-24
 */
@Configuration
@EnableSwagger2
public class Swagger {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                "Authmore 认证平台",
                "基于 OAuth2.0 协议的跨域认证授权开发套件",
                "0.0.1-SNAPSHOT",
                "",
                new Contact("郑保乐", "github.com/jameszbl", "zhengbaole@gmail.com"),
                "Apache2 Licensed", "", Collections.emptyList());
    }
}

