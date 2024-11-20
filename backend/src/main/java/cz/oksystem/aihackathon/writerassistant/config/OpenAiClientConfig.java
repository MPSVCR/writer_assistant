package cz.oksystem.aihackathon.writerassistant.config;

import com.azure.ai.openai.OpenAIClient;
import com.azure.ai.openai.OpenAIClientBuilder;
import com.azure.core.credential.AzureKeyCredential;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAiClientConfig {

    @Value("${spring.ai.azure.openai.api-key}")
    private String apiKey;

    @Value("${spring.ai.azure.openai.endpoint}")
    private String endpoint;

    @Bean
    public OpenAIClient openAIClient() {
        return new OpenAIClientBuilder().endpoint(endpoint).credential(new AzureKeyCredential(apiKey)).buildClient();
    }


}
