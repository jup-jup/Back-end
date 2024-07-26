package com.jupjup.www.jupjup.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;

//@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Spring Security API")
                        .version("1.0.0")
                        .description("소셜 로그인 API Documentation")
                        .contact(new Contact().name("bo ram kim").url("http://example.com/contact").email("boram04415@naver.com")));

    }

    @Bean  // 이 메서드는 Spring 애플리케이션 컨텍스트에 빈으로 등록됩니다.
    public GroupedOpenApi authApi() {
        return GroupedOpenApi.builder()
                .group("auths")  // 그룹 이름을 "auths"로 설정합니다. Swagger UI 에서 이 이름으로 그룹이 표시됩니다.
                .pathsToMatch("/auth/**")  // "/auth/**" 경로에 해당하는 모든 API 경로를 이 그룹에 포함시킵니다.
                .build();  // GroupedOpenApi 객체를 빌드하여 반환합니다.
    }

    @Bean
    public GroupedOpenApi orderApi() {
        return GroupedOpenApi.builder()
                .group("orders")
                .pathsToMatch("/api/orders/**")
                .build();
    }
}