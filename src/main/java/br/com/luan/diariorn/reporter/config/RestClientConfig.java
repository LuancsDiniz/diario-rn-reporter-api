package br.com.luan.diariorn.reporter.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

    @Bean
    public RestClient restClient(RestClient.Builder builder, @Value("${wordpress.base-url}") String baseUrl) {
        return builder.baseUrl(baseUrl).build();
    }
}
