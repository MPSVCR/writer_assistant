package cz.oksystem.aihackathon.writerassistant;

import com.azure.ai.openai.OpenAIClient;
import com.azure.ai.openai.models.*;
import cz.oksystem.aihackathon.writerassistant.utils.MessageInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

@SpringBootTest
public class PromptTest {

    @Autowired
    private OpenAIClient client;

    @Test
    public void testHelloPrompt() {
        var requests = new ArrayList<ChatRequestMessage>();

        var request = new ChatRequestUserMessage("Hello, how are you?");
        requests.add(request);
        ChatCompletions chatCompletions = client.getChatCompletions("gpt-4o",
                new ChatCompletionsOptions(requests));

        System.out.printf("Model ID=%s is created at %s.%n", chatCompletions.getId(), chatCompletions.getCreatedAt());
        for (ChatChoice choice : chatCompletions.getChoices()) {
            ChatResponseMessage message = choice.getMessage();
            System.out.printf("Index: %d, Chat Role: %s.%n", choice.getIndex(), message.getRole());
            System.out.println("Message:");
            System.out.println(message.getContent());
        }

    }

    @Test
    public void systemMetadataPromptTest() {
        var requests = new ArrayList<ChatRequestMessage>();

        var request = new ChatRequestUserMessage("Hello, how are you?");
        var metadata = new ChatRequestSystemMessage("Answer in 17th century english.");
        requests.add(request);
        requests.add(metadata);
        ChatCompletions chatCompletions = client.getChatCompletions("gpt-4o",
                new ChatCompletionsOptions(requests));

        MessageInfo.printOpenAiOutput(chatCompletions);
    }

    @Test
    public void testSystemHistory() {
        var systemMetadata = new ChatRequestSystemMessage("Pretend you are a doctor, trying to explain different types of flu treatment. to patient");
        var testAssistantMessage = new ChatRequestAssistantMessage(
                """
                        One of your options is trying drug called codeine, which works as a antitussicum (cough suppressant), however it cannot be taken
                        more than 10 days.
                        """);

        var requests = new ArrayList<ChatRequestMessage>();
        var req1 = new ChatRequestUserMessage("How can I treat fever during my illness?");
        var req2 = new ChatRequestUserMessage("How long will my illness last?");
        requests.add(systemMetadata);
        requests.add(testAssistantMessage);
        requests.add(req1);

        ChatCompletions chatCompletions = client.getChatCompletions("gpt-4o",
                new ChatCompletionsOptions(requests));

        MessageInfo.printOpenAiOutput(chatCompletions);

        requests.add(req2);

        ChatCompletions chatCompletionss = client.getChatCompletions("gpt-4o",
                new ChatCompletionsOptions(requests));

        MessageInfo.printOpenAiOutput(chatCompletionss);
    }

}
