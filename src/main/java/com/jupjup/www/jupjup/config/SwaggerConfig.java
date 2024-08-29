package com.jupjup.www.jupjup.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("JUP-JUP API")
                        .version("1.0.0")
                        .description("Back-end API Documentation"))
                .servers(List.of(
                        new Server().url("https://jupjup.store").description("Production server"),
                        new Server().url("http://localhost:8080").description("Local server")
                ));


    }

    @Bean
    public GroupedOpenApi userApi() {
        return GroupedOpenApi.builder()
                .group("users")  // 그룹 이름을 "user"로 설정합니다. Swagger UI 에서 이 이름으로 그룹이 표시됩니다.
                .pathsToMatch("/api/v1/user/**")  // "/user/**" 경로에 해당하는 모든 API 경로를 이 그룹에 포함시킵니다.
                .build();  // GroupedOpenApi 객체를 빌드하여 반환합니다.
    }

    @Bean
    public GroupedOpenApi authApi() {
        return GroupedOpenApi.builder()
                .group("auths")
                .pathsToMatch("/api/v1/auth/**")
                .build();
    }

    @Bean
    public GroupedOpenApi myPageApi() {
        return GroupedOpenApi.builder()
                .group("myPage")
                .pathsToMatch("/api/v1/myPage/**")
                .build();
    }

    @Bean
    public GroupedOpenApi chatApi() {
        return GroupedOpenApi.builder()
                .group("chat")
                .pathsToMatch("/api/v1/chat-rooms/**")
                .build();
    }

    @Bean
    public GroupedOpenApi profileApi() {
        return GroupedOpenApi.builder()
                .group("profile")
                .pathsToMatch("/api/v1/profile/**")
                .build();
    }

    @Bean
    public GroupedOpenApi giveawaysApi() {
        return GroupedOpenApi.builder()
                .group("giveaways")
                .pathsToMatch("/api/v1/giveaways/**")
                .build();
    }

}