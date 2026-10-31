package com.courseregistration.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;

@Configuration
public class OpenAIConfig {

    @Value("${openai.api.key:#{null}}")
    private String apiKey;

    @Value("${openai.api.url:https://api.openai.com/v1}")
    private String apiUrl;

    /**
     * Configures and registers a pre-configured RestClient bean for OpenAI API calls.
     * Automatically attaches the base URL, JSON content type, and Authorization header if the API key is present.
     */
    @Bean
    public RestClient openAiRestClient() {
        RestClient.Builder builder = RestClient.builder()
                .baseUrl(apiUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        // Attach Authorization header if API key is configured
        if (apiKey != null && !apiKey.isBlank() && !apiKey.equals("your-api-key-here")) {
            builder.defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + apiKey);
        }

        return builder.build();
    }
}